package com.pixvs.main.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.dao.EmpleadoContratoDao;
import com.pixvs.main.dao.EmpleadoDao;
import com.pixvs.main.dao.EmpleadoDocumentoDao;
import com.pixvs.main.dao.SolicitudBajaContratacionDao;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoComboEmpleadoProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoDocumento.EmpleadoDocumentoEditarProjection;
import com.pixvs.main.models.projections.SolicitudBajaContratacion.SolicitudBajaContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudBajaContratacion.SolicitudBajaContratacionListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import com.pixvs.spring.services.AutonumericoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
 * Created by Rene Carrillo on 26/08/2022.
 */
@RestController
@RequestMapping("/api/v1/bajas")
public class BajaController {

    @Autowired
    private SolicitudBajaContratacionDao solicitudBajaContratacionDao;

    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private EmpleadoDocumentoDao empleadoDocumentoDao;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    Jackson2ObjectMapperBuilder jsonBuilder;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<SolicitudBajaContratacionListadoProjection> listaSolicitudListado = solicitudBajaContratacionDao.findProjectedListadoAllBy();

        return new JsonResponse(listaSolicitudListado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadosById(@PathVariable(required = false) Integer id) throws SQLException {

        SolicitudBajaContratacionEditarProjection solicitudBajaContratacion = null;
        List<EmpleadoDocumentoEditarProjection> listaEmpleadoDocumento = new ArrayList<>();

        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        List<EmpleadoContratoComboEmpleadoProjection> listaEmpleado = new ArrayList<>();

        if (id != null) {
            //Mapper Convert Object to Model
            ObjectMapper mapper = jsonBuilder.build();

            //SolicitudBajaContratacion _solicitudBajaContratacion = new SolicitudNuevaContratacion();
            solicitudBajaContratacion = solicitudBajaContratacionDao.findById(id);

            //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SolicitudBajaContratacion _solicitudBajaContratacion = mapper.convertValue(solicitudBajaContratacion, SolicitudBajaContratacion.class);

            EmpleadoContratoEditarProjection empleadoContrato = empleadoContratoDao.findByIdAndEstatusId(solicitudBajaContratacion.getEmpleadoContratoId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.BAJA);

            _solicitudBajaContratacion.setEmpleadoContrato(mapper.convertValue(empleadoContrato, EmpleadoContrato.class));

            listaEmpleadoDocumento = empleadoDocumentoDao.findEmpleadoDocumentoEditarProjectionByEmpleadoIdAndTipoProcesoRHIdAndActivoIsTrue(solicitudBajaContratacion.getEmpleadoContrato().getEmpleadoId(), ControlesMaestrosMultiples.CMM_RH_TipoProcesoRH.BAJA);

            // Convertir el modelo a projection
            solicitudBajaContratacion = pf.createProjection(SolicitudBajaContratacionEditarProjection.class, _solicitudBajaContratacion);

            listaEmpleado = empleadoContratoDao.findEmpleadoContratoComboEmpleadoProjectionByEmpleadoIdAndEstatusId(solicitudBajaContratacion.getEmpleadoContrato().getEmpleadoId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.BAJA);
        }else{
            listaEmpleado = empleadoContratoDao.findEmpleadoContratoComboEmpleadoProjectionByEstatusId(ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO);
        }

        HashMap<String, Object> json = new HashMap<>();

        List<ControlMaestroMultipleComboProjection> listaTipoMotivo = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_RH_TipoMotivo.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoDocumento = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_GEN_TipoDocumento.NOMBRE);

        json.put("solicitudBajaContratacion", solicitudBajaContratacion);
        json.put("listaEmpleadoDocumento", listaEmpleadoDocumento);
        json.put("listaEmpleado", listaEmpleado);
        json.put("listaTipoMotivo", listaTipoMotivo);
        json.put("listaTipoDocumento", listaTipoDocumento);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody JSONObject json, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer anio = LocalDate.now().getYear();

        //Mapper Convert Object to Model
        ObjectMapper mapper = jsonBuilder.build();

        // Solicitud Baja Contratacion
        SolicitudBajaContratacion solicitudBajaContratacion = mapper.convertValue(json.get("solicitudBajaContratacion"), SolicitudBajaContratacion.class);

        // Empleado Contrato
        EmpleadoContrato empleadoContrato = solicitudBajaContratacion.getEmpleadoContrato();

        empleadoContrato.setEstatusId(ControlesMaestrosMultiples.CMM_EMP_Estatus.BAJA);
        empleadoContrato.setModificadoPorId(idUsuario);
        empleadoContrato.setFechaUltimaModificacion(new Timestamp(new Date().getTime()));

        empleadoContratoDao.save(empleadoContrato);

        // Empleado Documento
        Empleado empleado = empleadoDao.findById(empleadoContrato.getEmpleadoId());

        List<EmpleadoDocumento> listaEmpleadoDocumento = mapper.convertValue(json.get("listaEmpleadoDocumento"), new TypeReference<List<EmpleadoDocumento>>(){});

        for(EmpleadoDocumento empleadoDocumento: listaEmpleadoDocumento){
            if(empleadoDocumento.getId() < 0){
                empleadoDocumento.setId(null);
                empleadoDocumento.setCreadoPorId(idUsuario);
                empleadoDocumento.setFechaCreacion(new Timestamp(new Date().getTime()));
            }else{
                empleadoDocumento.setModificadoPorId(idUsuario);
                empleadoDocumento.setFechaUltimaModificacion(new Date());
            }
            if(empleadoDocumento.getTipoDocumento() != null){
                empleadoDocumento.setTipoDocumentoId(empleadoDocumento.getTipoDocumento().getId());
            }
        }
        empleado.setListaEmpleadoDocumento(listaEmpleadoDocumento);

        empleado.setModificadoPorId(idUsuario);
        empleado.setFechaUltimaModificacion(new Timestamp(new Date().getTime()));

        empleadoDao.save(empleado);

        // Solicitud Baja Contratacion
        if (solicitudBajaContratacion.getId() < 0) {
            solicitudBajaContratacion.setId(null);
            solicitudBajaContratacion.setCreadoPorId(idUsuario);
            solicitudBajaContratacion.setFechaCreacion(new Timestamp(new Date().getTime()));
            String codigoContrato = autonumericoService.getSiguienteAutonumericoByPrefijo("BPIX");
            codigoContrato = codigoContrato.replace("BPIX","BPIX" + anio + "-");
            solicitudBajaContratacion.setCodigo(codigoContrato);
        }

        if(solicitudBajaContratacion.getEmpleadoContrato() != null)
            solicitudBajaContratacion.setEmpleadoContratoId(solicitudBajaContratacion.getEmpleadoContrato().getId());

        if(solicitudBajaContratacion.getTipoMotivo() != null)
            solicitudBajaContratacion.setTipoMotivoId(solicitudBajaContratacion.getTipoMotivo().getId());


        solicitudBajaContratacionDao.save(solicitudBajaContratacion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosEmpleado/{solicitudBajaContratoId}/{empleadoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosEmpleado(@PathVariable("solicitudBajaContratoId") Integer solicitudBajaContratoId,@PathVariable("empleadoId") Integer empleadoId) {
        HashMap<String, Object> json = new HashMap<>();

        json.put("empleado", empleadoDao.findEmpleadoBajaProjectionById(empleadoId));
        if(solicitudBajaContratoId > 0){
            SolicitudBajaContratacionEditarProjection solicitudBajaContratacionEditarProjection = solicitudBajaContratacionDao.findById(solicitudBajaContratoId);
            json.put("listaEmpleadoContrato", empleadoContratoDao.findEmpleadoContratoEditarProjectionByIdAndEstatusId(solicitudBajaContratacionEditarProjection.getEmpleadoContratoId(), ControlesMaestrosMultiples.CMM_EMP_Estatus.BAJA));
        }else{
            json.put("listaEmpleadoContrato", empleadoContratoDao.findEmpleadoContratoEditarProjectionByEmpleadoIdAndEstatusId(empleadoId, ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO));
        }

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }
}
