package com.pixvs.main.controllers;

import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.SolicitudBajaContratacion.SolicitudBajaContratacionEditarProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rene Carrillo on 13/09/2022.
 */
@RestController
@RequestMapping("/api/v1/reapertura-grupo")
public class ReaperturaGrupoController {

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @RequestMapping(value = "/getListaGrupo/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaGrupo(@PathVariable("codigo") String codigo) {
        return new JsonResponse(programaGrupoDao.findAllProgramaGrupoReaperturaGrupoProjectionBy(codigo), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaGrupo[] listaProgramaGrupo, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        try{
            for (ProgramaGrupo programaGrupo : listaProgramaGrupo) {

                ProgramaGrupo _programaGrupo = programaGrupoDao.findById(programaGrupo.getId());

                if(_programaGrupo.getEstatusId() != ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO)
                    throw new Exception("El grupo ha sido modificado por otro usuario. Favor de recargar la vista antes de guardar.");

                _programaGrupo.setFechaFinInscripciones(programaGrupo.getFechaFinInscripciones());
                _programaGrupo.setFechaFinInscripcionesBecas(programaGrupo.getFechaFinInscripcionesBecas());

                _programaGrupo.setModificadoPorId(idUsuario);
                _programaGrupo.setFechaModificacion(new Date());

                programaGrupoDao.save(_programaGrupo);
            }
        }catch (Exception ex){
            return new JsonResponse(ex, null, JsonResponse.STATUS_ERROR);
        }

        return new JsonResponse("", null, JsonResponse.STATUS_OK);
    }
}
