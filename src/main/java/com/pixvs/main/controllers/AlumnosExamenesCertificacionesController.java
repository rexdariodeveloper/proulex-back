package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlumnoDao;
import com.pixvs.main.dao.AlumnoExamenCertificacionDao;
import com.pixvs.main.dao.ProgramaIdiomaDao;
import com.pixvs.main.models.AlumnoExamenCertificacion;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.AlumnoExamenCertificacion.AlumnoExamenCertificacionEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCertificacion.AlumnoExamenCertificacionProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.Json;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/examenes-certificaciones")
public class AlumnosExamenesCertificacionesController {

    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private AlumnoExamenCertificacionDao alumnoExamenCertificacionDao;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getExamenesCertificaciones(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> data = new HashMap<>();
        List<AlumnoExamenCertificacionProjection> listado = alumnoExamenCertificacionDao.getListadoExamenesCertificaciones(null,null,null);
        data.put("datos", listado);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getExamenesCertificacionesFiltros(@RequestBody JSONObject json) throws SQLException, ParseException {

        //HashMap<String, Object> alumno = (HashMap<String,Object>) json.get("alumno");
        String codigo = (String) json.get("codigo");
        HashMap<String, Object> estatus = (HashMap<String,Object>) json.get("estatus");
        HashMap<String, Object> tipo = (HashMap<String,Object>) json.get("tipo");

        Integer estatusId = estatus != null ? (Integer) estatus.get("id") : null;
        Integer tipoId = tipo != null ? (Integer) tipo.get("id") : null;

        List<AlumnoExamenCertificacionProjection> listado = alumnoExamenCertificacionDao.getListadoExamenesCertificaciones(codigo,tipoId,estatusId);

        return new JsonResponse(listado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(ServletRequest req) throws SQLException {
        HashMap<String, Object> data = new HashMap<>();
        //data.put("alumnos", alumnoDao.findProjectedComboAllBy());
        data.put("tipos", controlMaestroMultipleDao.findAllByControl("CMM_ALUEC_Tipo"));
        data.put("estatus", controlMaestroMultipleDao.findAllByControl("CMM_ALUEC_Estatus"));

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/detalles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getExamenesCertificaciones(@PathVariable("id") Integer id, ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> data = new HashMap<>();
        AlumnoExamenCertificacionEditarProjection aec = alumnoExamenCertificacionDao.findProjectedEditarById(id);
        data.put("cursos", programaIdiomaDao.findProjectedComboSimpleByExamenCertificacionId(aec.getArticuloId()));
        data.put("detalle", aec);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse guardar(@RequestBody AlumnoExamenCertificacion aec, ServletRequest req) {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> estatusEdicion = new ArrayList<>();
        estatusEdicion.add(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.EN_PROCESO);
        estatusEdicion.add(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO);

        if(!estatusEdicion.contains(aec.getEstatusId()))
            return new JsonResponse(null, "El registro no permite modificaciones.", JsonResponse.STATUS_ERROR);

        AlumnoExamenCertificacion objetoActual = alumnoExamenCertificacionDao.findById(aec.getId().intValue());
        try {
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), aec.getFechaModificacion());
        } catch (Exception e) {
            return new JsonResponse("", null, JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }

        aec.setEstatusId(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO);
        aec.setProgramaIdiomaCertificacionValeId(objetoActual.getProgramaIdiomaCertificacionValeId());
        alumnoExamenCertificacionDao.save(aec);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

}
