package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoEditarProjection;
import com.pixvs.main.models.projections.Puesto.PuestoComboContratosProjection;
import com.pixvs.main.models.projections.Puesto.PuestoListadoProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionListadoProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacionDetalle.SolicitudNuevaContratacionDetalleEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboResponsabilidadProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rene Carrillo on 30/03/2022.
 */
@RestController
@RequestMapping("/api/v1/nuevas-contrataciones")
public class NuevaContratacionController {

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private SolicitudNuevaContratacionDao solicitudNuevaContratacionDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private DepartamentoDao departamentoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    Jackson2ObjectMapperBuilder jsonBuilder;

    @Autowired
    private PuestoDao puestoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<SolicitudNuevaContratacionListadoProjection> listaSolicitudListado = solicitudNuevaContratacionDao.findProjectedListadoAllBy();

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        SolicitudNuevaContratacionEditarProjection solicitudNuevaContratacion = null;

        List<SolicitudNuevaContratacionDetalle> listaSolicitud = new ArrayList<>();

        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        //Mapper Convert Object to Model
        ObjectMapper mapper = jsonBuilder.build();

        if (id != null) {
            SolicitudNuevaContratacion _solicitudNuevaContratacion = new SolicitudNuevaContratacion();

            SolicitudNuevaContratacionEditarProjection __solicitudNuevaContratacion = solicitudNuevaContratacionDao.findProjectedEditarById(id);
            _solicitudNuevaContratacion.setId(__solicitudNuevaContratacion.getId());
            _solicitudNuevaContratacion.setCodigo(__solicitudNuevaContratacion.getCodigo());
            _solicitudNuevaContratacion.setEstatusId(__solicitudNuevaContratacion.getEstatusId());
            _solicitudNuevaContratacion.setFechaCreacion(__solicitudNuevaContratacion.getFechaCreacion());

            if(__solicitudNuevaContratacion.getListaSolicitudNuevaContratacionDetalle() != null){
                for(SolicitudNuevaContratacionDetalleEditarProjection detalle : __solicitudNuevaContratacion.getListaSolicitudNuevaContratacionDetalle()){
                    SolicitudNuevaContratacionDetalle solicitudNuevaContratacionDetalle = new SolicitudNuevaContratacionDetalle();
                    solicitudNuevaContratacionDetalle.setId(detalle.getId());
                    solicitudNuevaContratacionDetalle.setSolicitudNuevaContratacionId(detalle.getSolicitudNuevaContratacionId());
                    solicitudNuevaContratacionDetalle.setEstatusId(detalle.getEstatusId());

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

                    //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    Empleado _empleado = mapper.convertValue(empleado, Empleado.class);

                    EmpleadoContratoEditarProjection empleadoContrato = empleadoContratoDao.findByEmpleadoIdAndEstatusId(empleado.getId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO);

                    _empleado.setEmpleadoContrato(mapper.convertValue(empleadoContrato, EmpleadoContrato.class));

                    // Establecer empleado al solicitud (detalle)
                    solicitudNuevaContratacionDetalle.setEmpleado(_empleado);

                    // Agregamos lista de Solicitud
                    listaSolicitud.add(solicitudNuevaContratacionDetalle);
                }

                // Establecer lista de solicitud (detalle) al solicitud principal
                _solicitudNuevaContratacion.setListaSolicitudNuevaContratacionDetalle(listaSolicitud);
            }
            // Convertir el modelo a projection
            solicitudNuevaContratacion = pf.createProjection(SolicitudNuevaContratacionEditarProjection.class, _solicitudNuevaContratacion);
        }

        List<SucursalComboProjection> listaSede = sucursalDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> listaGenero = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaEstadoCivil = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_EstadoCivilId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaGradoEstudio = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_GradoEstudios.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaNacionalidad = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Nacionalidad.NOMBRE);
        List<PaisComboProjection> listaPais = paisDao.findProjectedComboAllBy();
        List<ControlMaestroMultipleComboProjection> listaTipoSangre = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_TipoSangre.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaJustificacion = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ENT_Justificacion.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoContrato = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ENT_TipoContrato.NOMBRE);
        List<DepartamentoComboResponsabilidadProjection> listaDepartamento = departamentoDao.findProjectedComboResponsabilidadAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> listaTipoHorario = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ENT_TipoHorario.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoDocumento = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_GEN_TipoDocumento.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaParentesco = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CE_Parentesco.NOMBRE);
        List<PuestoComboContratosProjection> puestos = puestoDao.findProjectedListadoComboContratoAllBy();
        HashMap<String, Object> json = new HashMap<>();

        json.put("solicitudNuevaContratacion", solicitudNuevaContratacion);
        json.put("listaSede", listaSede);
        json.put("listaPais", listaPais);
        json.put("listaGenero", listaGenero);
        json.put("listaGradoEstudio", listaGradoEstudio);
        json.put("listaNacionalidad", listaNacionalidad);
        json.put("listaParentesco", listaParentesco);
        json.put("listaEstadoCivil", listaEstadoCivil);
        json.put("listaTipoSangre", listaTipoSangre);
        json.put("listaJustificacion", listaJustificacion);
        json.put("listaTipoContrato", listaTipoContrato);
        json.put("listaDepartamento", listaDepartamento);
        json.put("listaTipoHorario", listaTipoHorario);
        json.put("listaTipoDocumento", listaTipoDocumento);
        json.put("listaPuestos", puestos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody SolicitudNuevaContratacion solicitudNuevaContratacion, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer anio = LocalDate.now().getYear();

        for (SolicitudNuevaContratacionDetalle solicitudNuevaContratacionDetalle : solicitudNuevaContratacion.getListaSolicitudNuevaContratacionDetalle()){
            if(solicitudNuevaContratacionDetalle.getId() < 0){
                solicitudNuevaContratacionDetalle.setId(null);
            }
            Empleado empleado = solicitudNuevaContratacionDetalle.getEmpleado();

            List<EmpleadoEditarProjection> empleadosExistentes;
            empleadosExistentes = empleadoDao.findAllByCorreoElectronico(empleado.getCorreoElectronico());

            if(empleado.getId()==null && empleadosExistentes!=null & empleadosExistentes.size()>0){
                return new JsonResponse(null,"Empleado con correo electrónico existente",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }

            empleadosExistentes = empleadoDao.findAllByCodigoEmpleado(empleado.getCodigoEmpleado());
            if(empleado.getId()==null && empleadosExistentes!=null & empleadosExistentes.size()>0){
                return new JsonResponse(null,"Empleado con código de empleado existente",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }

            if(empleado.getSucursal() != null){
                empleado.setSucursalId(empleado.getSucursal().getId());
            }
            if(empleado.getPaisNacimiento() != null){
                empleado.setPaisNacimientoId(empleado.getPaisNacimiento().getId());
            }
            if(empleado.getEstadoNacimiento() != null){
                empleado.setEstadoNacimientoId(empleado.getEstadoNacimiento().getId());
            }
            if(empleado.getEstadoCivil() != null){
                empleado.setEstadoCivilId(empleado.getEstadoCivil().getId());
            }
            if(empleado.getGenero() != null){
                empleado.setGeneroId(empleado.getGenero().getId());
            }
            if(empleado.getGradoEstudios() != null){
                empleado.setGradoEstudiosId(empleado.getGradoEstudios().getId());
            }
            if(empleado.getNacionalidad() != null){
                empleado.setNacionalidadId(empleado.getNacionalidad().getId());
            }
            if(empleado.getPais() != null){
                empleado.setPaisId(empleado.getPais().getId());
            }
            if(empleado.getEstado() != null){
                empleado.setEstadoId(empleado.getEstado().getId());
            }

            // Dato Salud
            for(EmpleadoDatoSalud datoSalud: empleado.getDatosSalud()){
                if(datoSalud.getId() < 0){
                    datoSalud.setId(null);
                }
                if(datoSalud.getTipoSangre() != null){
                    datoSalud.setTipoSangreId(datoSalud.getTipoSangre().getId());
                }
            }

            // Empleado Contrato
            EmpleadoContrato empleadoContrato = empleado.getEmpleadoContrato();
            empleado.setEmpleadoContrato(null);
            if(empleadoContrato.getId() < 0){
                empleadoContrato.setId(null);
                empleadoContrato.setCreadoPorId(idUsuario);
                empleadoContrato.setFechaContrato(new Timestamp(new Date().getTime()));
                empleadoContrato.setFechaInicio(new Timestamp(empleadoContrato.getFechaInicio().getTime()));
                empleadoContrato.setFechaFin(new Timestamp(empleadoContrato.getFechaFin().getTime()));

                String codigoContrato = autonumericoService.getSiguienteAutonumericoByPrefijo("PIXVS");
                codigoContrato = codigoContrato.replace("PIXVS","PIXVS" + anio + "-");

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

            // Empleado Horario
            for (EmpleadoHorario empleadoHorario : empleado.getListaEmpleadoHorario()){
                if(empleadoHorario.getId() < 0){
                    empleadoHorario.setId(null);
                }
            }

            // Beneficiario
            for(EmpleadoBeneficiario beneficiario: empleado.getBeneficiarios()){
                if(beneficiario.getId() < 0){
                    beneficiario.setId(null);
                }
                if(beneficiario.getParentesco() != null){
                    beneficiario.setParentescoId(beneficiario.getParentesco().getId());
                }
            }

            // Contacto
            for(EmpleadoContacto contacto : empleado.getContactos()){
                if(contacto.getId() < 0){
                    contacto.setId(null);
                    contacto.setCreadoPorId(idUsuario);
                    contacto.setBorrado(false);
                }
                else {
                    contacto.setModificadoPorId(idUsuario);
                }
            }

            // Archivos
            for(EmpleadoDocumento empleadoDocumento: empleado.getListaEmpleadoDocumento()){
                if(empleadoDocumento.getId() < 0){
                    empleadoDocumento.setId(null);
                    empleadoDocumento.setCreadoPorId(idUsuario);
                    empleadoDocumento.setFechaCreacion(new Date());
                }else{
                    empleadoDocumento.setModificadoPorId(idUsuario);
                    empleadoDocumento.setFechaUltimaModificacion(new Date());
                }
                if(empleadoDocumento.getTipoDocumento() != null){
                    empleadoDocumento.setTipoDocumentoId(empleadoDocumento.getTipoDocumento().getId());
                }
            }

            if (empleado.getId() < 0) {
                empleado.setId(null);
                empleado.setCreadoPorId(idUsuario);
            }
            else {
                empleado.setModificadoPorId(idUsuario);
            }

            if (empleado.getImg64() != null) {
                Archivo archivo = fileSystemStorageService.storeBase64(empleado.getImg64(), idUsuario, 2, null, true, true);
                //empleado.getUsuario().setArchivoId(archivo.getId());
                empleado.setFotoId(archivo.getId());
                if(empleado.getUsuarioId() != null){
                    //empleado.getUsuario().setArchivoId(archivo.getId());
                    Usuario temp = usuarioDao.findById(empleado.getUsuarioId());
                    temp.setArchivoId(archivo.getId());
                    temp = usuarioDao.save(temp);
                }
            }

            empleado = empleadoDao.save(empleado);

            solicitudNuevaContratacionDetalle.setEmpleadoId(empleado.getId());

            // Guarda EmpleadoContrato
            empleadoContrato.setEmpleadoId(empleado.getId());
            empleadoContrato = empleadoContratoDao.save(empleadoContrato);
        }

        if (solicitudNuevaContratacion.getId() < 0) {
            solicitudNuevaContratacion.setId(null);
            String codigoContrato = autonumericoService.getSiguienteAutonumericoByPrefijo("PIX");
            codigoContrato = codigoContrato.replace("PIX","PIX" + anio + "-");
            solicitudNuevaContratacion.setCodigo(codigoContrato);
            solicitudNuevaContratacion.setCreadoPorId(idUsuario);
        }
        else {
            solicitudNuevaContratacion.setModificadoPorId(idUsuario);
        }

        solicitudNuevaContratacion = solicitudNuevaContratacionDao.save(solicitudNuevaContratacion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/subir-foto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse subirFoto(@RequestBody String img64, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Archivo archivo = fileSystemStorageService.storeBase64(img64, idUsuario, 2, null, true, true);

        return new JsonResponse(archivo, null, JsonResponse.STATUS_OK);
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
