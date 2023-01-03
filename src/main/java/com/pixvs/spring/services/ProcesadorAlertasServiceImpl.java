package com.pixvs.spring.services;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.spring.controllers.NotificacionController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.*;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.AlertaConfigEtapa.AlertaConfigEtapaComboProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcesadorAlertasServiceImpl implements ProcesadorAlertasService {

    @Autowired
    private AlertasDao alertasDao;
    @Autowired
    private AlertasDetalleDao alertasDetalleDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private AlertaConfigEtapaDao alertaConfigEtapaDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private AlertaConfigEtapaDao configEtapaDao;

    @Autowired
    private LogController logController;
    @Autowired
    private NotificacionController notificacionController;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    @Autowired
    private Environment environment;

    public void getProcedimiento(Integer alertaConfigId, Integer referenciaId, Integer estatusId) {
        getProcedimiento(alertaConfigId,referenciaId,estatusId,null,null,null);
    }

    public void getProcedimiento(Integer alertaConfigId, Integer referenciaId, Integer estatusId, Integer logTipoId, Integer usuarioId, String textoLog){
        AlertaConfig alertaConfig = alertaConfigDao.findById(alertaConfigId);
        Integer nuevoEstatusProceso = null;
        Integer spFinalEstatus = null;
        if(estatusId.equals(CMM_CALE_EstatusAlerta.AUTORIZADA)){
            nuevoEstatusProceso = alertaConfig.getEstadoAutorizadoId();
            if(alertaConfig.getSpFinal() != null){
                spFinalEstatus = alertaConfig.getEstadoAutorizadoId();
            }
        }else if(estatusId.equals(CMM_CALE_EstatusAlerta.RECHAZADA)){
            nuevoEstatusProceso = alertaConfig.getEstadoRechazadoId();
            if(alertaConfig.getSpFinal() != null){
                spFinalEstatus = alertaConfig.getEstadoRechazadoId();
            }
        }

        if(spFinalEstatus != null){
            StoredProcedureQuery query = em.createStoredProcedureQuery(alertaConfig.getSpFinal());
            query.registerStoredProcedureParameter( 1, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter( 2, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter( 3, Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter( 4, Integer.class, ParameterMode.OUT);
            query.setParameter(1,referenciaId);
            query.setParameter(2,spFinalEstatus);
            Integer procesoId = (Integer)query.getOutputParameterValue(3);
            Integer logId = (Integer)query.getOutputParameterValue(4);
            if(procesoId != null){
                nuevoEstatusProceso = (Integer)(procesoId);
            }
            if(logId != null){
                logTipoId = (Integer)(logId);
            }
        }
        if(nuevoEstatusProceso != null){
            em.createNativeQuery("" +
                    "UPDATE " + alertaConfig.getTablaReferencia() + " " +
                    "SET " + alertaConfig.getCampoEstado() + " = " + nuevoEstatusProceso + " " +
                    "WHERE " + alertaConfig.getCampoId() + " = " + referenciaId + " " +
                    "").executeUpdate();
        }
        if(logTipoId != null){
            registrarLog(alertaConfigId, referenciaId, logTipoId, usuarioId, textoLog);
        }
    }

    public void registrarLog(Integer alertaConfigId, Integer referenciaId, Integer logTipoId, Integer usuarioId, String texto){
        AlertaConfig alertaConfig = alertaConfigDao.findById(alertaConfigId);

        if(logTipoId != null && alertaConfig.getLogProcesoId() != null){
            logController.insertaLogUsuario(
                    new Log(texto,
                            logTipoId,
                            alertaConfig.getLogProcesoId(),
                            referenciaId
                    ),
                    usuarioId
            );
        }
    }

    @Transactional
    public boolean validarAutorizacion(Integer alertaConfigId, Integer referenciaId, String referenciaCodigo, String referenciaNombre, Integer sucursalId, Integer idUsuario, String textoOrigen) throws Exception {
        //Omitir las alertas si el sistema no las soporta
        if(!environment.getProperty("environments.pixvs.alertas-notificaciones").equals("true"))
            return false;
        //Obtener la configuración de la alerta
        AlertaConfig alertaConfig = alertaConfigDao.findById(alertaConfigId);
        //Obtener la primera etapa de la alerta
        AlertaConfigEtapa etapa = getSiguienteEtapa(alertaConfig, sucursalId, 0);
        //Si no tiene primera etapa, omitir las alertas
        if (etapa == null)
            return false;
        Integer estatusAlertaId = CMM_CALE_EstatusAlerta.EN_PROCESO;
        String textoRepresentativo = referenciaNombre+' '+referenciaCodigo;
        //Crear la cabecera de la alerta
        Alerta alerta = alertasDao.save(this.createAlerta(alertaConfig.getId(),referenciaId,referenciaCodigo,estatusAlertaId,textoRepresentativo,idUsuario,etapa.getId(),textoOrigen));
        List<AlertaDetalle> detalles = new ArrayList<>();
        HashSet<Integer> usuariosNotificarIds = new HashSet<>();
        //Extraer y mapear los estatus del proceso
        HashMap<String, Integer> estatus = new HashMap<>();
        estatus.put("EN_PROCESO" , alertaConfig.getEstadoEnProcesoId());
        estatus.put("AUTORIZADO" , alertaConfig.getEstadoAutorizadoId());
        estatus.put("EN_REVISION", alertaConfig.getEstadoEnRevisionId());
        estatus.put("RECHAZADO"  , alertaConfig.getEstadoRechazadoId());
        //Controla si el ciclo debe continuar o detenerse en una etapa de la alerta
        Boolean continuar;
        do {
            alerta.setAlertaConfigEtapaId(etapa.getId());
            //Reiniciar la variable de control
            continuar = true;
            //Verificar si existe una etapa siguiente
            AlertaConfigEtapa siguiente = getSiguienteEtapa(alertaConfig, sucursalId, etapa.getOrden());
            //Crear notificaciones iniciales
            List<AlertaConfigEtapaNotificadores> iniciales = etapa.getNotificadores()
                    .stream()
                    .filter( item -> item.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.INICIAL))
                    .collect(Collectors.toList());
            for (AlertaConfigEtapaNotificadores notificador : iniciales){
                AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),estatus.get("EN_PROCESO"),notificador.getUsuarioNotificacionId(),idUsuario, CMM_CALRD_TipoAlerta.NOTIFICACION);
                detalles.add(detalle);
                usuariosNotificarIds.add(notificador.getUsuarioNotificacionId());
            }
            //Notificar al creador
            if (etapa.getNotificarCreador()){
                AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),estatus.get("EN_PROCESO"),idUsuario,idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                detalles.add(detalle);
                usuariosNotificarIds.add(idUsuario);
            }
            //Solo procesar alertas del tipo AUTORIZACION
            if (etapa.getTipoAlertaId().equals(CMM_CALRD_TipoAlerta.AUTORIZACION)){
                Boolean requiereAutorizacion = true;
                //Validar si aplica alerta por criterios económicos
                if (etapa.getCriteriosEconomicos())
                    requiereAutorizacion = alertaConfigDao.getAplicaCriteriosEconomicos(etapa.getTipoMontoId(),alertaConfig.getId(),etapa.getMontoMaximo(),etapa.getMontoMinimo(),referenciaId);
                if (requiereAutorizacion){
                    if(etapa.getTipoOrdenId().equals(CMM_ACE_TipoOrden.SECUENCIAL)){
                        //Ordenar los aprobadores
                        List<AlertaConfigEtapaAprobadores> aprobadores = etapa.getDetalles()
                                .stream()
                                .sorted( Comparator.comparing(AlertaConfigEtapaAprobadores::getOrden))
                                .collect(Collectors.toList());
                        if(etapa.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.USUARIO)){
                            //Iterar por los aprobadores
                            for (AlertaConfigEtapaAprobadores aprobador : aprobadores){
                                //Si el aprobador es el usuario que inicio la alerta, autorizar automaticamente
                                if (aprobador.getAprobadorId().equals(idUsuario)){
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.AUTORIZADA,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                } else {
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                    continuar = false;
                                    break;
                                }
                            }
                        }
                        else if(etapa.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.DEPARTAMENTO)){
                            for (AlertaConfigEtapaAprobadores aprobador : aprobadores){
                                Departamento depto = departamentoDao.findById(aprobador.getDepartamentoId());
                                if(depto.getResponsableId().equals(idUsuario)){
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.AUTORIZADA,depto.getResponsableId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                } else {
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,depto.getResponsableId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                    continuar = false;
                                    break;
                                }
                            }
                        }
                    } else if (etapa.getTipoOrdenId().equals(CMM_ACE_TipoOrden.PARALELA)){
                        List<AlertaDetalle> temp = new ArrayList<>();
                        if(etapa.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.USUARIO)){
                            //Iterar por los aprobadores
                            for (AlertaConfigEtapaAprobadores aprobador : etapa.getDetalles()){
                                //Si el aprobador es el usuario que inicio la alerta, autorizar automaticamente
                                if (aprobador.getAprobadorId().equals(idUsuario)){
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.AUTORIZADA,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                    if(etapa.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.UNA_APROBACION)){
                                        temp.clear();
                                        break;
                                    }
                                } else {
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    temp.add(detalle);
                                }
                            }
                        }
                        else if(etapa.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.DEPARTAMENTO)){
                            for (AlertaConfigEtapaAprobadores aprobador : etapa.getDetalles()){
                                Departamento depto = departamentoDao.findById(aprobador.getDepartamentoId());
                                if(depto.getResponsableId().equals(idUsuario)){
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.AUTORIZADA,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    detalles.add(detalle);
                                    if(etapa.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.UNA_APROBACION)){
                                        temp.clear();
                                        break;
                                    }
                                } else {
                                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,aprobador.getAprobadorId(),idUsuario,etapa.getTipoAlertaId());
                                    temp.add(detalle);
                                }
                            }
                        }
                        //Si se crearon detalles pendientes de aprobación
                        if (temp.size() > 0){
                            detalles.addAll(temp);
                            //Agregar las solicitudes de aprobación al arreglo de notificaciones
                            for (AlertaDetalle detalle : temp){ usuariosNotificarIds.add(detalle.getUsuarioId());}
                            continuar = false;
                        }
                    }
                }
            }
            //Si debemos continuar a la siguiente etapa
            if(continuar){
                //Crear notificaciones finales
                List<AlertaConfigEtapaNotificadores> finales = etapa.getNotificadores()
                        .stream()
                        .filter( item -> item.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.FINAL))
                        .collect(Collectors.toList());
                for (AlertaConfigEtapaNotificadores notificador : finales){
                    AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapa.getId(),estatus.get("EN_PROCESO"),notificador.getUsuarioNotificacionId(),idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                    detalles.add(detalle);
                    usuariosNotificarIds.add(notificador.getUsuarioNotificacionId());
                }
                etapa = siguiente;
            } else
                etapa = null;
        }while (etapa != null);
        //Guardar la ultima etapa en la que quedó la alerta
        alerta = alertasDao.save(alerta);
        //Asignar los detalles de autorización y notificación
        if (detalles.size() > 0)
            alertasDetalleDao.saveAll(detalles);
        //Crear las notificaciones
        if (usuariosNotificarIds.size() > 0)
            notificacionController.nuevaAlertaNotificacion(new ArrayList<>(usuariosNotificarIds), "Tienes (1) nueva Notificación");
        //Si el proceso se quedó en una etapa de autorización, entonces retornar true
        if (continuar)
            return false;
        else
            return true;
    }

    @Transactional
    public void actualizaEstatusAlerta(Integer idDetalleAlerta, Integer idUsuario, Boolean autorizar, String comentario) throws Exception{

        AlertaDetalle detalleActual = alertasDetalleDao.findById(idDetalleAlerta).get();
        if(detalleActual.getEstatusDetalleId() != CMM_CALE_EstatusAlerta.EN_PROCESO || detalleActual.getAlerta().getEstatusAlertaId() != CMM_CALE_EstatusAlerta.EN_PROCESO){
            throw new Exception("La solicitud ya fue procesada");
        }
        if(autorizar){
            detalleActual.setEstatusDetalleId(CMM_CALE_EstatusAlerta.AUTORIZADA);
        }else{
            detalleActual.setEstatusDetalleId(CMM_CALE_EstatusAlerta.RECHAZADA);
            detalleActual.setComentario(comentario);
        }
        alertasDetalleDao.save(detalleActual);

        if(!autorizar){
            rechazarAlerta(idDetalleAlerta,idUsuario);
            return;
        }

        ArrayList<Integer> usuariosNotificarIds = new ArrayList<Integer>();
        ArrayList<Integer> usuariosActualizarNotificacionesIds = new ArrayList<Integer>();

        AlertaConfigEtapa etapaActual = detalleActual.getEtapa();
        do{
            Alerta alerta = detalleActual.getAlerta();
            Integer estatusNotificacionesId = alerta.getConfig().getEstadoEnProcesoId();
            if ( etapaActual.getTipoAlertaId().equals(CMM_CALRD_TipoAlerta.AUTORIZACION)){
                List<AlertaDetalle> detalles = new ArrayList<>();

                Boolean autorizacionParalelaEnProceso = false;
                Boolean cumpleCriterios = true;
                //Verificar si aplica criterios económicos
                if (etapaActual.getCriteriosEconomicos()) { //TODO: revisar proceso de criterios
                    Integer tipoMontoId = etapaActual.getTipoMontoId();
                    cumpleCriterios = alertaConfigDao.getAplicaCriteriosEconomicos(tipoMontoId, etapaActual.getAlertaConfigId(), etapaActual.getMontoMaximo(), etapaActual.getMontoMinimo(), alerta.getReferenciaProcesoId());
                }

                if(!cumpleCriterios){
                    alerta.setEstatusAlertaId(CMM_CALE_EstatusAlerta.RECHAZADA);
                    getProcedimiento(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),CMM_CALE_EstatusAlerta.RECHAZADA);
                    break;
                }

                AlertaConfigEtapa etapaSiguiente = alertaConfigEtapaDao.findByAlertaConfigIdAndSucursalIdAndOrden(etapaActual.getAlertaConfigId(),etapaActual.getSucursalId(),etapaActual.getOrden()+1);
                if ( etapaActual.getTipoOrdenId().equals(CMM_ACE_TipoOrden.SECUENCIAL)){
                    if ( etapaActual.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.DEPARTAMENTO)){
                        int orden = 0;
                        for (AlertaConfigEtapaAprobadores aprobador : etapaActual.getDetalles()){
                            Departamento depto = departamentoDao.findById(aprobador.getDepartamentoId());
                            if (depto.getResponsableId().equals(detalleActual.getUsuarioId())) {
                                orden = aprobador.getOrden();
                                break;
                            }
                        }

                        if(etapaActual.getDetalles().size() > orden){
                            for (AlertaConfigEtapaAprobadores aprobador : etapaActual.getDetalles()){
                                if(aprobador.getOrden().equals(orden + 1)){
                                    Departamento depto = departamentoDao.findById(aprobador.getDepartamentoId());
                                    AlertaDetalle detalle = createDetalle(alerta.getId(),etapaActual.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,depto.getResponsableId(),idUsuario,etapaActual.getTipoAlertaId());
                                    detalles.add(detalle);
                                    if(!usuariosNotificarIds.contains(depto.getResponsableId())) {
                                        usuariosNotificarIds.add(depto.getResponsableId());
                                    }
                                }
                            }
                            etapaSiguiente = etapaActual;
                        }
                    }
                    else if ( etapaActual.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.USUARIO)){
                        int orden = 0;
                        for (AlertaConfigEtapaAprobadores aprobador : etapaActual.getDetalles()){
                            if (aprobador.getAprobadorId().equals(detalleActual.getUsuarioId())) {
                                orden = aprobador.getOrden();
                            }
                        }

                        if(etapaActual.getDetalles().size() > orden){
                            for (AlertaConfigEtapaAprobadores aprobador : etapaActual.getDetalles()){
                                if(aprobador.getOrden().equals(orden + 1)){
                                    AlertaDetalle detalle = createDetalle(alerta.getId(),etapaActual.getId(),CMM_CALE_EstatusAlerta.EN_PROCESO,aprobador.getAprobadorId(),idUsuario,etapaActual.getTipoAlertaId());
                                    detalles.add(detalle);
                                    if(!usuariosNotificarIds.contains(aprobador.getAprobadorId())) {
                                        usuariosNotificarIds.add(aprobador.getAprobadorId());
                                    }
                                }
                            }
                            etapaSiguiente = etapaActual;
                        }
                    }
                }
                else if ( etapaActual.getTipoOrdenId().equals(CMM_ACE_TipoOrden.PARALELA)){
                    Integer estado = getEstadoAprobacionParalela(etapaActual,alerta,autorizar);
                    if(estado.equals(0)){
                        alerta.setEstatusAlertaId(CMM_CALE_EstatusAlerta.RECHAZADA);
                        getProcedimiento(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),CMM_CALE_EstatusAlerta.RECHAZADA,LogTipo.RECHAZADO,idUsuario,null);
                        break;
                    }

                    if(etapaActual.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.UNA_APROBACION)){
                        for(AlertaDetalle alertaDetalle : alerta.getDetalles()){
                            if(alertaDetalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO)){
                                usuariosActualizarNotificacionesIds.add(alertaDetalle.getUsuarioId());
                                alertasDetalleDao.actualizarEstatus(alertaDetalle.getId(), CMM_CALE_EstatusAlerta.AUTORIZADA);
                            }
                        }
                    }else if(etapaActual.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.TODAS_APROBACIONES)){
                        for(AlertaDetalle alertaDetalle : alerta.getDetalles()){
                            if(alertaDetalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO)){
                                autorizacionParalelaEnProceso = true;
                                etapaSiguiente = etapaActual;
                                break;
                            }
                        }
                    }
                }

                if(detalles.size() == 0 && !autorizacionParalelaEnProceso){
                    if(autorizar){
                        registrarLog(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),LogTipo.AUTORIZADO,idUsuario, "Se autorizó la etapa " + etapaActual.getOrden());
                    }
                    if(etapaSiguiente != null){
                        if(etapaSiguiente.getTipoAlertaId().equals(CMM_CALRD_TipoAlerta.AUTORIZACION)){
                            List<AlertaDetalle> d = crearEtapasTipoAutorizacion(etapaSiguiente,CMM_CALE_EstatusAlerta.EN_PROCESO,idUsuario,alerta.getReferenciaProcesoId(),estatusNotificacionesId);
                            if(d != null && d.size() > 0){
                                for(AlertaConfigEtapaNotificadores notificador : etapaSiguiente.getNotificadores()){
                                    if(notificador.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.INICIAL)){
                                        Integer notificadorId = notificador.getUsuarioNotificacionId();
                                        AlertaDetalle detalle = createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,notificadorId,idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                                        detalles.add(detalle);
                                        if(!usuariosNotificarIds.contains(notificadorId)) {
                                            usuariosNotificarIds.add(notificadorId);
                                        }
                                    }
                                }
                                detalles.addAll(d);
                            }
                        }
                        else {
                            List<AlertaDetalle> d = crearEtapasTipoNotificacion(etapaSiguiente, estatusNotificacionesId, idUsuario, alerta.getReferenciaProcesoId());
                            detalles.addAll(d);
                            for(AlertaDetalle detalle : d){
                                if(!usuariosNotificarIds.contains(detalle.getUsuarioId())) {
                                    usuariosNotificarIds.add(detalle.getUsuarioId());
                                }
                            }
                        }
                        for(AlertaDetalle detalle : detalles){
                            detalle.setAlertaId(alerta.getId());
                        }
                        alerta.setAlertaConfigEtapaId(etapaSiguiente.getId());
                    }else{ // if(etapaSiguiente == null)
                        Integer estatus = autorizar? CMM_CALE_EstatusAlerta.AUTORIZADA : CMM_CALE_EstatusAlerta.RECHAZADA;
                        estatusNotificacionesId = autorizar ? alerta.getConfig().getEstadoAutorizadoId() : alerta.getConfig().getEstadoRechazadoId();
                        alerta.setEstatusAlertaId(estatus);
                        getProcedimiento(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),estatus,LogTipo.AUTORIZADO,idUsuario, "Proceso de alertas finalizado");

                        for(AlertaConfigEtapaNotificadores notificador : etapaActual.getNotificadores()){
                            if(notificador.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.FINAL) && (!etapaActual.getNotificarCreador() || !notificador.getUsuarioNotificacionId().equals(alerta.getCreadoPorId()))){
                                Integer notificadorId = notificador.getUsuarioNotificacionId();
                                AlertaDetalle detalle = createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,notificadorId,idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                                detalles.add(detalle);
                                if(!usuariosNotificarIds.contains(detalle.getUsuarioId())) {
                                    usuariosNotificarIds.add(detalle.getUsuarioId());
                                }
                            }
                        }

                        if ( etapaActual.getNotificarCreador() ) {
                            AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,alerta.getCreadoPorId(),idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                            detalles.add(detalle);
                            if(!usuariosNotificarIds.contains(detalle.getUsuarioId())) {
                                usuariosNotificarIds.add(detalle.getUsuarioId());
                            }
                        }

                        alertasDetalleDao.saveAll(detalles);

                        break;
                    }
                }

                if(!etapaActual.getId().equals(etapaSiguiente.getId())){
                    for(AlertaConfigEtapaNotificadores notificador : etapaActual.getNotificadores()){
                        if(notificador.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.FINAL) && (!etapaActual.getNotificarCreador() || !notificador.getUsuarioNotificacionId().equals(idUsuario))){
                            Integer notificadorId = notificador.getUsuarioNotificacionId();
                            AlertaDetalle detalle = createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,notificadorId,idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                            detalles.add(detalle);
                            if(!usuariosNotificarIds.contains(notificadorId)) {
                                usuariosNotificarIds.add(notificadorId);
                            }
                        }
                    }
                    etapaActual = etapaSiguiente;
                }else{
                    registrarLog(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),LogTipo.AUTORIZADO,idUsuario, null);
                    etapaActual = null;
                }

                alertasDetalleDao.saveAll(detalles);
            }
            else {//NOTIFICACION

                List<AlertaDetalle> detalles;
                AlertaConfigEtapa etapaSiguiente = alertaConfigEtapaDao.findByAlertaConfigIdAndSucursalIdAndOrden(etapaActual.getAlertaConfigId(),etapaActual.getSucursalId(),etapaActual.getOrden()+1);
                registrarLog(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),LogTipo.AUTORIZADO,idUsuario, "Notificación etapa " + etapaActual.getOrden());
                if(etapaSiguiente != null){
                    if(etapaSiguiente.getTipoAlertaId().equals(CMM_CALRD_TipoAlerta.AUTORIZACION)) {
                        detalles = crearEtapasTipoAutorizacion(etapaSiguiente, CMM_CALE_EstatusAlerta.EN_PROCESO, idUsuario, alerta.getReferenciaProcesoId(),estatusNotificacionesId);
                    }
                    else {
                        detalles = crearEtapasTipoNotificacion(etapaSiguiente, estatusNotificacionesId, idUsuario, alerta.getReferenciaProcesoId());
                    }
                    for(AlertaDetalle detalle : detalles){
                        detalle.setAlertaId(alerta.getId());
                        alertasDetalleDao.save(detalle);
                        if(!usuariosNotificarIds.contains(detalle.getUsuarioId())) {
                            usuariosNotificarIds.add(detalle.getUsuarioId());
                        }
                    }
                    alerta.setAlertaConfigEtapaId(etapaSiguiente.getId());
                }
                else {
                    alerta.setEstatusAlertaId(CMM_CALE_EstatusAlerta.AUTORIZADA);
                    getProcedimiento(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),CMM_CALE_EstatusAlerta.AUTORIZADA,LogTipo.AUTORIZADO,idUsuario, "Proceso de alertas finalizado");
                }
                etapaActual = etapaSiguiente;
            }

        }while(etapaActual != null && etapaActual.getTipoAlertaId().equals(CMM_CALRD_TipoAlerta.NOTIFICACION)); //Sea notificación
        if(usuariosNotificarIds.size() > 0) {
            notificacionController.nuevaAlertaNotificacion(usuariosNotificarIds, "Tienes (1) nueva Notificación");
        }
        if(usuariosActualizarNotificacionesIds.size() > 0) {
            notificacionController.nuevaActualizacionNotificaciones(usuariosActualizarNotificacionesIds);
        }
    }

    private Alerta createAlerta(Integer configAlertaId, Integer referenciaId, String codigo, Integer estatusAlertaId, String texto, Integer usuarioId, Integer configEtapaId, String textoOrigen){
        Alerta alerta = new Alerta();

        alerta.setAlertaCId(configAlertaId);
        alerta.setReferenciaProcesoId(referenciaId);
        alerta.setCodigoTramite(codigo);
        alerta.setEstatusAlertaId(estatusAlertaId);
        alerta.setTextoRepresentativo(texto);
        alerta.setCreadoPorId(usuarioId);/* debe ser enviado por el usuario*/
        alerta.setAlertaConfigEtapaId(configEtapaId);
        alerta.setOrigen(textoOrigen);

        return alerta;
    }

    private AlertaDetalle createDetalle(Integer alertaId, Integer etapaId, Integer estatusId, Integer usuarioId, Integer creadoPorId, Integer tipoAlertaId){
        AlertaDetalle detalle = new AlertaDetalle();

        detalle.setAlertaId(alertaId);
        detalle.setEtapaId(etapaId);
        detalle.setEstatusDetalleId(estatusId);
        detalle.setUsuarioId(usuarioId);
        detalle.setMostrar(Boolean.TRUE);
        detalle.setArchivado(Boolean.FALSE);
        detalle.setVisto(Boolean.FALSE);
        detalle.setCreadoPorId(creadoPorId);
        detalle.setTipoAlertaId(tipoAlertaId);

        return detalle;
    }

    private List<AlertaDetalle> crearEtapasTipoNotificacion(AlertaConfigEtapa etapaNueva, Integer estatusId, Integer idUsuario, Integer idReferencia){
        List<AlertaDetalle> detalles = new ArrayList<>();
        Boolean cumpleCriterios = false;

        //Verificar si aplica criterios económicos
        if (etapaNueva.getCriteriosEconomicos()) {
            Integer tipoMontoId = etapaNueva.getTipoMontoId();
            cumpleCriterios = alertaConfigDao.getAplicaCriteriosEconomicos(tipoMontoId, etapaNueva.getAlertaConfigId(), etapaNueva.getMontoMaximo(), etapaNueva.getMontoMinimo(), idReferencia);
        } else
            cumpleCriterios = true;

        if (cumpleCriterios){
            for ( AlertaConfigEtapaNotificadores notificadores : etapaNueva.getNotificadores()){
                Integer aprobadorId = notificadores.getUsuarioNotificacionId();
                AlertaDetalle detalle = this.createDetalle(null,etapaNueva.getId(),estatusId,aprobadorId,idUsuario,etapaNueva.getTipoAlertaId());
                detalles.add(detalle);
            }
        }

        return detalles;
    }

    private List<AlertaDetalle> crearEtapasTipoAutorizacion(AlertaConfigEtapa etapaNueva, Integer estatusId, Integer idUsuario, Integer idReferencia, Integer estatusNotificacionesId){
        List<AlertaDetalle> detalles = new ArrayList<>();

        Boolean cumpleCriterios = false;

        //Verificar si aplica criterios económicos
        if (etapaNueva.getCriteriosEconomicos()) { //TODO: revisar proceso de criterios
            Integer tipoMontoId = etapaNueva.getTipoMontoId();
            cumpleCriterios = alertaConfigDao.getAplicaCriteriosEconomicos(tipoMontoId, etapaNueva.getAlertaConfigId(), etapaNueva.getMontoMaximo(), etapaNueva.getMontoMinimo(), idReferencia);
        } else {
            cumpleCriterios = true;
        }

        if(!cumpleCriterios) {
            return detalles;
        }

        if ( etapaNueva.getTipoOrdenId().equals(CMM_ACE_TipoOrden.SECUENCIAL) ) {
            if (etapaNueva.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.USUARIO)) {
                Integer aprobadorId = etapaNueva.getDetalles().get(0).getAprobadorId();
                AlertaDetalle detalle = this.createDetalle(null, etapaNueva.getId(),estatusId, aprobadorId, idUsuario, etapaNueva.getTipoAlertaId());
                detalles.add(detalle);
            }else if (etapaNueva.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.DEPARTAMENTO)) {
                Integer departamentoId = etapaNueva.getDetalles().get(0).getDepartamentoId();
                Departamento depto = departamentoDao.findById(departamentoId);
                Integer aprobadorId = depto.getResponsableId();
                AlertaDetalle detalle = this.createDetalle(null, etapaNueva.getId(),estatusId, aprobadorId, idUsuario, etapaNueva.getTipoAlertaId());
                detalles.add(detalle);
            }
        }else if ( etapaNueva.getTipoOrdenId().equals(CMM_ACE_TipoOrden.PARALELA) ) {
            if ( etapaNueva.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.USUARIO) ) {
                for ( AlertaConfigEtapaAprobadores aprobador : etapaNueva.getDetalles() ) {
                    Integer aprobadorId = aprobador.getAprobadorId();
                    AlertaDetalle detalle = this.createDetalle(null, etapaNueva.getId(), estatusId, aprobadorId, idUsuario, etapaNueva.getTipoAlertaId());
                    detalles.add(detalle);
                }
            }
            else if ( etapaNueva.getTipoAprobacionId().equals(CMM_ACE_TipoAprobacion.DEPARTAMENTO) ) {
                for ( AlertaConfigEtapaAprobadores aprobador : etapaNueva.getDetalles() ) {
                    Integer departamentoId = aprobador.getDepartamentoId();
                    Departamento depto = departamentoDao.findById(departamentoId);
                    Integer aprobadorId = depto.getResponsableId();
                    AlertaDetalle detalle = this.createDetalle(null, etapaNueva.getId(), estatusId, aprobadorId, idUsuario, etapaNueva.getTipoAlertaId());
                    detalles.add(detalle);
                }
            }
        }

        for ( AlertaConfigEtapaNotificadores notificador : etapaNueva.getNotificadores() ) {
            if(notificador.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.INICIAL)){
                Integer notificadorId = notificador.getUsuarioNotificacionId();
                AlertaDetalle detalle = this.createDetalle(null,etapaNueva.getId(),estatusNotificacionesId,notificadorId,idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                detalles.add(detalle);
            }
        }
        return  detalles;
    }

    private Integer getEstadoAprobacionParalela(AlertaConfigEtapa etapa, Alerta alerta, Boolean autoriza){
        Integer estado = -1;

        if (etapa.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.UNA_APROBACION)){
            if( autoriza ) {
                estado = 1;
            }
            else {
                Integer pendientes = 0;
                for (AlertaDetalle detalle : alerta.getDetalles()){
                    if(detalle.getEtapaId().equals(etapa.getId()) && detalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO)){
                        pendientes++;
                    }
                }
                if(pendientes == 1) {
                    estado = 0;
                }
            }
        } else if(etapa.getCondicionAprobacionId().equals(CMM_ACE_TipoCondicionAprobacion.TODAS_APROBACIONES)){
            if(!autoriza) {
                estado = 0;
            }else {
                Integer pendientes = 0;
                for (AlertaDetalle detalle : alerta.getDetalles()){
                    if(detalle.getEtapaId().equals(etapa.getId()) && detalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO)){
                        pendientes++;
                    }
                }
                if(pendientes == 1)
                    estado = 1;
            }
        }

        return estado;
    }

    public void rechazarAlerta(Integer idDetalleAlerta, Integer idUsuario) throws Exception{
        AlertaDetalle detalleActual = alertasDetalleDao.findById(idDetalleAlerta).get();
        AlertaConfigEtapa etapaActual = detalleActual.getEtapa();
        Alerta alerta = detalleActual.getAlerta();
        ArrayList<Integer> usuarios = new ArrayList<Integer>();
        Integer estatusNotificacionesId = alerta.getConfig().getEstadoRechazadoId();

        getProcedimiento(etapaActual.getAlertaConfigId(),alerta.getReferenciaProcesoId(),CMM_CALE_EstatusAlerta.RECHAZADA,LogTipo.RECHAZADO,idUsuario,null);

        if(etapaActual.getTipoOrdenId().equals(CMM_ACE_TipoOrden.PARALELA)){
            for(AlertaDetalle alertaDetalle : alerta.getDetalles()){
                if(alertaDetalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO)){
                    alertasDetalleDao.actualizarEstatus(alertaDetalle.getId(), CMM_CALE_EstatusAlerta.RECHAZADA);
                }
            }
        }else{
            alertasDetalleDao.actualizarEstatus(detalleActual.getId(),ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.RECHAZADA);
        }

        List<AlertaDetalle> detalles = new ArrayList<>();

        //Si la etapa tiene notificadores, crear los detalles de notificación
        for ( AlertaConfigEtapaNotificadores notificador : etapaActual.getNotificadores()) {
            if (notificador.getTipoNotificacionAlerta().equals(CMM_ACEN_TipoNotificacion.FINAL) && (!etapaActual.getNotificarCreador() || !notificador.getUsuarioNotificacionId().equals(idUsuario))) {
                AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,notificador.getUsuarioNotificacionId(),idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
                detalles.add(detalle);
                if(!usuarios.contains(detalle.getUsuarioId())){
                    usuarios.add(detalle.getUsuarioId());
                }
            }
        }

        //Si se requiere notificar al creador
        if ( etapaActual.getNotificarCreador() ) {
            AlertaDetalle detalle = this.createDetalle(alerta.getId(),etapaActual.getId(),estatusNotificacionesId,alerta.getCreadoPorId(),idUsuario,CMM_CALRD_TipoAlerta.NOTIFICACION);
            detalles.add(detalle);
            if(!usuarios.contains(detalle.getUsuarioId())){
                usuarios.add(detalle.getUsuarioId());
            }
        }

        if(detalles.size() > 0){
            alertasDetalleDao.saveAll(detalles);
        }

        alerta.setEstatusAlertaId(CMM_CALE_EstatusAlerta.RECHAZADA);
        alertasDao.save(alerta);

        if(usuarios.size() > 0) {
            notificacionController.nuevaAlertaNotificacion(usuarios, "Tienes (1) nueva Notificación");
        }
    }

    private AlertaConfigEtapa getSiguienteEtapa(AlertaConfig alertaConfig, Integer sucursalId, Integer orden){
        if (alertaConfig == null)
            return null;
        if(alertaConfig.getAplicaSucursales()){
            if (environment.getProperty("environments.pixvs.requiere-entidades").equals("true"))
                return alertaConfigEtapaDao.findByAlertaConfigIdAndOrden(alertaConfig.getId(),orden + 1);
            else
                return alertaConfigEtapaDao.findByAlertaConfigIdAndSucursalIdAndOrden(alertaConfig.getId(),sucursalId,orden + 1);
        }
        else
            return alertaConfigEtapaDao.findByAlertaConfigIdAndOrden(alertaConfig.getId(),orden + 1);
    }
}