package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoEditarProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacionDetalle.SolicitudNuevaContratacionDetalleEditarProjection;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacion.SolicitudRenovacionContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacion.SolicitudRenovacionContratacionListadoProjection;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacionDetalle.SolicitudRenovacionContratacionDetalleEditarProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboResponsabilidadProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Rene Carrillo on 30/03/2022.
 */
@RestController
@RequestMapping("/api/v1/renovaciones")
public class RenovacionController {

    @Autowired
    private SolicitudRenovacionContratacionDao solicitudRenovacionContratacionDao;
    @Autowired
    private SolicitudRenovacionContratacionDetalleDao solicitudRenovacionContratacionDetalleDao;
    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    Jackson2ObjectMapperBuilder jsonBuilder;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<SolicitudRenovacionContratacionListadoProjection> listaSolicitudListado = solicitudRenovacionContratacionDao.findProjectedListadoAllBy();

        return new JsonResponse(listaSolicitudListado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        SolicitudRenovacionContratacionEditarProjection solicitudRenovacionContratacion = null;

        List<SolicitudRenovacionContratacionDetalle> listaSolicitud = new ArrayList<>();

        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        //Mapper Convert Object to Model
        ObjectMapper mapper = jsonBuilder.build();

        if (id != null) {
            SolicitudRenovacionContratacion _solicitudRenovacionContratacion = new SolicitudRenovacionContratacion();

            SolicitudRenovacionContratacionEditarProjection __solicitudRenovacionContratacion = solicitudRenovacionContratacionDao.findProjectedEditarById(id);
            _solicitudRenovacionContratacion.setId(__solicitudRenovacionContratacion.getId());
            _solicitudRenovacionContratacion.setCodigo(__solicitudRenovacionContratacion.getCodigo());
            _solicitudRenovacionContratacion.setFechaInicio(__solicitudRenovacionContratacion.getFechaInicio());
            _solicitudRenovacionContratacion.setFechaFin(__solicitudRenovacionContratacion.getFechaFin());
            _solicitudRenovacionContratacion.setEstatusId(__solicitudRenovacionContratacion.getEstatusId());
            _solicitudRenovacionContratacion.setFechaCreacion(__solicitudRenovacionContratacion.getFechaCreacion());

            if(__solicitudRenovacionContratacion.getListaSolicitudRenovacionContratacionDetalle() != null){
                for(SolicitudRenovacionContratacionDetalleEditarProjection detalle : __solicitudRenovacionContratacion.getListaSolicitudRenovacionContratacionDetalle()){
                    SolicitudRenovacionContratacionDetalle solicitudRenovacionContratacionDetalle = new SolicitudRenovacionContratacionDetalle();
                    solicitudRenovacionContratacionDetalle.setId(detalle.getId());
                    solicitudRenovacionContratacionDetalle.setSolicitudRenovacionContratacionId(detalle.getSolicitudRenovacionContratacionId());
                    solicitudRenovacionContratacionDetalle.setEstatusId(detalle.getEstatusId());

                    EmpleadoEditarProjection empleado = detalle.getEmpleado();
                    if(empleado.getUsuario()!=null){
                        Usuario temp = usuarioDao.findById(empleado.getUsuario().getId());
                        //Revisar si se actualizó la foto del usuario, actualizarla al empleado
                        if(temp != null && temp.getArchivoId() != empleado.getFotoId()){
                            homologarFotografías(empleado.getId(),temp.getArchivoId());
                            empleado = empleadoDao.findProjectedEditarById(id);
                        }
                        //Revisar si se actualizó el nombre y apellidos
                        homologarDatos(empleado.getId(),temp);
                        empleado = empleadoDao.findProjectedEditarById(id);
                        /*if(temp != null && temp.getNombre() != empleado.getNombre() ){
                            homologarDatos(empleado.getId(),temp.getNombre(),null,null,null);
                        }
                        if(temp != null && temp.getPrimerApellido() != empleado.getPrimerApellido()){
                            homologarDatos(empleado.getId(),null,temp.getPrimerApellido(),null,null);
                        }
                        if(temp != null && temp.getSegundoApellido() != empleado.getSegundoApellido())*/
                        //Revisar si se actualizó el correo
                    }

                    Empleado _empleado =  mapper.convertValue(empleado, Empleado.class);

                    EmpleadoContratoEditarProjection empleadoContrato = empleadoContratoDao.findByEmpleadoIdAndEstatusId(empleado.getId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.RENOVADO);

                    _empleado.setEmpleadoContrato(mapper.convertValue(empleadoContrato, EmpleadoContrato.class));

                    // Establecer empleado al solicitud (detalle)
                    solicitudRenovacionContratacionDetalle.setEmpleado(_empleado);

                    // Agregamos lista de Solicitud
                    listaSolicitud.add(solicitudRenovacionContratacionDetalle);
                }

                // Establecer lista de solicitud (detalle) al solicitud principal
                _solicitudRenovacionContratacion.setListaSolicitudRenovacionContratacionDetalle(listaSolicitud);
            }
            // Convertir el modelo a projection
            solicitudRenovacionContratacion = pf.createProjection(SolicitudRenovacionContratacionEditarProjection.class, _solicitudRenovacionContratacion);
        }

        List<DepartamentoComboResponsabilidadProjection> listaDepartamento = departamentoDao.findProjectedComboResponsabilidadAllByActivoTrue();

        HashMap<String, Object> json = new HashMap<>();

        json.put("solicitudRenovacionContratacion", solicitudRenovacionContratacion);
        json.put("listaDepartamento", listaDepartamento);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse imprimirFormato(@RequestBody SolicitudRenovacionContratacion solicitudRenovacionContratacion) throws SQLException {
        //String fechaInicio = json.get("fechaInicio").toString();
        //String fechaFin = json.get("fechaFin").toString();

        List<Empleado> listaEmpleado = new ArrayList<>();

        // Contador
        int contadorRegistrosNuevos = -1;

        //Mapper Convert Object to Model
        ObjectMapper mapper = jsonBuilder.build();

        List<EmpleadoEditarProjection> _listaEmpleado = solicitudRenovacionContratacionDao.getRenovacionesConFiltros(solicitudRenovacionContratacion.getFechaInicio(), solicitudRenovacionContratacion.getFechaFin());
        for (EmpleadoEditarProjection empleadoEditarProjection : _listaEmpleado){
            if(!solicitudRenovacionContratacionDetalleDao.existsByEmpleadoIdAndEstatusId(empleadoEditarProjection.getId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO)){
                EmpleadoContratoEditarProjection empleadoContratoEditarProjection = empleadoContratoDao.findByEmpleadoIdAndEstatusId(empleadoEditarProjection.getId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO);
                Date fechaFinDiciembre = new Date(empleadoContratoEditarProjection.getFechaFin().getYear(),11,31);
                if(empleadoContratoEditarProjection.getFechaFin().getTime() == fechaFinDiciembre.getTime()){
                    EmpleadoContrato empleadoContrato =  mapper.convertValue(empleadoContratoEditarProjection, EmpleadoContrato.class);
                    empleadoContrato.setId(contadorRegistrosNuevos);
                    //empleadoContrato.setCodigo("");
                    empleadoContrato.setFechaInicio(new Date(empleadoContratoEditarProjection.getFechaInicio().getYear() + 1, 0, 1));
                    empleadoContrato.setFechaFin(new Date(empleadoContratoEditarProjection.getFechaFin().getYear() + 1, 11, 31));
                    empleadoContrato.setEstatusId(ControlesMaestrosMultiples.CMM_EMP_Estatus.RENOVADO);

                    Empleado empleado = mapper.convertValue(empleadoEditarProjection, Empleado.class);
                    empleado.setEmpleadoContrato(empleadoContrato);

                    listaEmpleado.add(empleado);

                    // Descontador
                    contadorRegistrosNuevos--;
                }else{
                    Empleado empleado = mapper.convertValue(empleadoEditarProjection, Empleado.class);
                    empleado.setEmpleadoContrato(mapper.convertValue(empleadoContratoEditarProjection, EmpleadoContrato.class));

                    listaEmpleado.add(empleado);
                }
            }else{
                //EmpleadoContratoEditarProjection empleadoContratoEditarProjection = empleadoContratoDao.findByEmpleadoId(empleadoEditarProjection.getId());
                //EmpleadoContratoEditarProjection empleadoContratoEditarProjection = empleadoContratoDao.findByEmpleadoId(empleadoEditarProjection.getId());
                //EmpleadoContrato empleadoContrato =  mapper.convertValue(empleadoContratoEditarProjection, EmpleadoContrato.class);
            }
        }

        return new JsonResponse(listaEmpleado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody SolicitudRenovacionContratacion solicitudRenovacionContratacion, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer anio = LocalDate.now().getYear();

        for (SolicitudRenovacionContratacionDetalle solicitudRenovacionContratacionDetalle : solicitudRenovacionContratacion.getListaSolicitudRenovacionContratacionDetalle()){
            if(solicitudRenovacionContratacionDetalle.getId() < 0){
                solicitudRenovacionContratacionDetalle.setId(null);
            }
            Empleado empleado = solicitudRenovacionContratacionDetalle.getEmpleado();

            // Asignamos el ID de Empleado al Solicitud Detalle
            solicitudRenovacionContratacionDetalle.setEmpleadoId(empleado.getId());

            // Empleado Contrato
            EmpleadoContrato empleadoContrato = empleado.getEmpleadoContrato();
            empleado.setEmpleadoContrato(null);
            if(empleadoContrato.getId() < 0){
                empleadoContrato.setId(null);
                empleadoContrato.setCreadoPorId(idUsuario);
                empleadoContrato.setFechaContrato(new Timestamp(new Date().getTime()));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(empleadoContrato.getFechaInicio());

                String codigoContrato = autonumericoService.getSiguienteAutonumericoByPrefijo("PIXVS");
                codigoContrato = codigoContrato.replace("PIXVS","PIXVS" + calendar.get(Calendar.YEAR) + "-");

                empleadoContrato.setCodigo(codigoContrato);
            }
            else {
                empleadoContrato.setModificadoPorId(idUsuario);
            }

            if(empleadoContrato.getJustificacion() != null){
                empleadoContrato.setJustificacionId(empleadoContrato.getJustificacion().getId());
            }
            if(empleadoContrato.getTipoContrato() != null){
                empleadoContrato.setTipoContratoId(empleadoContrato.getTipoContrato().getId());
            }
            if(empleadoContrato.getPuesto() != null){
                empleadoContrato.setPuestoId(empleadoContrato.getPuesto().getId());
            }
            if(empleadoContrato.getTipoHorario() != null){
                empleadoContrato.setTipoHorarioId(empleadoContrato.getTipoHorario().getId());
            }
            if(empleadoContrato.getPais() != null){
                empleadoContrato.setPaisId(empleadoContrato.getPais().getId());
            }
            if(empleadoContrato.getEstado() != null){
                empleadoContrato.setEstadoId(empleadoContrato.getEstado().getId());
            }
            if(empleadoContrato.getMunicipio() != null){
                empleadoContrato.setMunicipioId(empleadoContrato.getMunicipio().getId());
            }

            empleadoContrato = empleadoContratoDao.save(empleadoContrato);
        }

        if (solicitudRenovacionContratacion.getId() < 0) {
            solicitudRenovacionContratacion.setId(null);
            String codigoContrato = autonumericoService.getSiguienteAutonumericoByPrefijo("RPIX");
            codigoContrato = codigoContrato.replace("RPIX","RPIX" + anio + "-");
            solicitudRenovacionContratacion.setCodigo(codigoContrato);
            solicitudRenovacionContratacion.setCreadoPorId(idUsuario);
        }
        else {
            solicitudRenovacionContratacion.setModificadoPorId(idUsuario);

            // Verificar si todo esta borrado los solicitudes Renovaciones Contrataciones Detalles
            if(!solicitudRenovacionContratacion.getListaSolicitudRenovacionContratacionDetalle().stream().allMatch(detalle -> detalle.getEstatusId() == ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO)){
                solicitudRenovacionContratacion.setEstatusId(ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO);
            }
        }

        solicitudRenovacionContratacion = solicitudRenovacionContratacionDao.save(solicitudRenovacionContratacion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    public void homologarFotografías(Integer empleadoId, Integer archivoId){
        Empleado empleado = empleadoDao.findById(empleadoId);
        empleado.setFotoId(archivoId);
        empleadoDao.save(empleado);
    }

    public void homologarDatos(Integer empleadoId, Usuario usuario){
        Empleado empleado = empleadoDao.findById(empleadoId);
        if(!usuario.getNombre().equals(empleado.getNombre())){
            empleado.setNombre(usuario.getNombre());
        }
        if(!usuario.getPrimerApellido().equals(empleado.getPrimerApellido())){
            empleado.setPrimerApellido(usuario.getPrimerApellido());
        }
        if(!usuario.getSegundoApellido().equals(empleado.getSegundoApellido())){
            empleado.setSegundoApellido(usuario.getSegundoApellido());
        }
        if(!usuario.getCorreoElectronico().equals(empleado.getCorreoElectronico())){
            empleado.setCorreoElectronico(usuario.getCorreoElectronico());
        }
        empleado = empleadoDao.save(empleado);
    }
}
