package com.pixvs.main.controllers;

import com.pixvs.main.dao.ProgramaIdiomaCertificacionDescuentoDao;
import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaIdiomaCertificacionDao;
import com.pixvs.main.dao.ProgramaIdiomaDao;
import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuento;
import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuentoDetalle;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboDescuentoCertificacionProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento.ProgramaIdiomaCertificacionDescuentoEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento.ProgramaIdiomaCertificacionDescuentoListadoProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rene Carrillo on 25/11/2022.
 */
@RestController
@RequestMapping("/api/v1/descuentos-certificaciones")
public class DescuentoCertificacionController {

    @Autowired
    private ProgramaIdiomaCertificacionDescuentoDao programaIdiomaCertificacionDescuentoDao;

    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;

    @Autowired
    private PAModalidadDao paModalidadDao;

    @Autowired
    private ProgramaIdiomaCertificacionDao programaIdiomaCertificacionDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListado() throws SQLException {

        List<ProgramaIdiomaCertificacionDescuentoListadoProjection> listado = programaIdiomaCertificacionDescuentoDao.findProjectedListadoAllBy();

        return new JsonResponse(listado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        ProgramaIdiomaCertificacionDescuentoEditarProjection programaIdiomaCertificacionDescuento = null;
        ProgramaIdiomaComboDescuentoCertificacionProjection programaIdioma = null;
        List<ProgramaIdiomaCertificacionComboProjection> listaCertificacion = null;
        List<ProgramaIdiomaComboDescuentoCertificacionProjection> listaCurso = programaIdiomaDao.findProgramaIdiomaComboDescuentoCertificacionProjection();

        if (id != null) {
            programaIdiomaCertificacionDescuento = programaIdiomaCertificacionDescuentoDao.findById(id);
            programaIdioma = programaIdiomaDao.findProgramaIdiomaComboDescuentoCertificacionProjection(programaIdiomaCertificacionDescuento.getProgramaIdiomaCertificacion().getProgramaIdiomaId());
            listaCertificacion = programaIdiomaCertificacionDao.findCertificacionComboProjection(programaIdiomaCertificacionDescuento.getProgramaIdiomaCertificacion().getProgramaIdiomaId());

            Integer programaIdiomaId = programaIdioma.getId();
            if(listaCurso.stream().anyMatch(curso -> curso.getId() != programaIdiomaId))
                listaCurso.add(programaIdioma);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("programaIdiomaCertificacionDescuento", programaIdiomaCertificacionDescuento);
        json.put("programaIdioma", programaIdioma);
        json.put("listaCurso", listaCurso);
        json.put("listaCertificacion", listaCertificacion);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaIdiomaCertificacionDescuento programaIdiomaCertificacionDescuento, ServletRequest req) throws Exception {



        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(programaIdiomaCertificacionDescuento.getId() < 0){
            // Vertificar si el programa idioma son igual y regresa al error
            ProgramaIdiomaCertificacionDescuentoEditarProjection _programaIdiomaCertificacionDescuento = programaIdiomaCertificacionDescuentoDao.findByProgramaIdiomaCertificacionId(programaIdiomaCertificacionDescuento.getProgramaIdiomaCertificacion().getId());

            if(_programaIdiomaCertificacionDescuento != null)
                throw new Exception("La certificación ha sido modificado por otro usuario. Favor de recargar la vista antes de guardar.");

            programaIdiomaCertificacionDescuento.setId(null);
            programaIdiomaCertificacionDescuento.setCreadoPorId(idUsuario);
        }else{
            programaIdiomaCertificacionDescuento.setModificadoPorId(idUsuario);
            programaIdiomaCertificacionDescuento.setFechaUltimaModificacion(new Date());
        }

        if(programaIdiomaCertificacionDescuento.getProgramaIdiomaCertificacion() != null){
            programaIdiomaCertificacionDescuento.setProgramaIdiomaCertificacionId(programaIdiomaCertificacionDescuento.getProgramaIdiomaCertificacion().getId());
            programaIdiomaCertificacionDescuento.setProgramaIdiomaCertificacion(null);
        }


        for(ProgramaIdiomaCertificacionDescuentoDetalle programaIdiomaCertificacionDescuentoDetalle : programaIdiomaCertificacionDescuento.getListaDescuento()){
            if(programaIdiomaCertificacionDescuentoDetalle.getId() < 0){
                programaIdiomaCertificacionDescuentoDetalle.setId(null);
            }
        }

        programaIdiomaCertificacionDescuento = programaIdiomaCertificacionDescuentoDao.save(programaIdiomaCertificacionDescuento);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/certificacion/{programaIdiomaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getListaCertificacion(@PathVariable Integer programaIdiomaId, ServletRequest req) throws SQLException {

        List<ProgramaIdiomaCertificacionComboProjection> listaCertificacion = programaIdiomaCertificacionDao.findCertificacionComboProjection(programaIdiomaId);

        return new JsonResponse(listaCertificacion, null, JsonResponse.STATUS_OK);
    }
}
