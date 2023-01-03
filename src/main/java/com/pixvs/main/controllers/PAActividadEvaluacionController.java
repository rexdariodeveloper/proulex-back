package com.pixvs.main.controllers;

import com.pixvs.main.dao.PAActividadEvaluacionDao;
import com.pixvs.main.dao.PACicloDao;
import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.models.PAActividadEvaluacion;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades-evaluacion")
public class PAActividadEvaluacionController {

    @Autowired
    private PAActividadEvaluacionDao paActividadEvaluacionDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<PAActividadEvaluacionListadoProjection> actividades = paActividadEvaluacionDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(actividades, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PAActividadEvaluacion paActividadEvaluacion, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (paActividadEvaluacion.getId() == null) {
            paActividadEvaluacion.setCreadoPorId(idUsuario);
            paActividadEvaluacion.setActivo(true);
        } else {
            PAActividadEvaluacion objetoActual = paActividadEvaluacionDao.findById(paActividadEvaluacion.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), paActividadEvaluacion.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            paActividadEvaluacion.setModificadoPorId(idUsuario);
            paActividadEvaluacion.setActivo(objetoActual.getActivo());
        }


        paActividadEvaluacion = paActividadEvaluacionDao.save(paActividadEvaluacion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        int actualizado = paActividadEvaluacionDao.actualizarActivo(hashId.decode(id), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
