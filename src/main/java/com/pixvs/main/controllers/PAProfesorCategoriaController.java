package com.pixvs.main.controllers;

import com.pixvs.main.dao.PACicloDao;
import com.pixvs.main.dao.PAProfesorCategoriaDao;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAProfesorCategoria;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorCategoriaListadoProjection;
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
@RequestMapping("/api/v1/profesores-categorias")
public class PAProfesorCategoriaController {

    @Autowired
    private PAProfesorCategoriaDao paProfesorCategoriaDao;

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

        List<PAProfesorCategoriaListadoProjection> categorias = paProfesorCategoriaDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(categorias, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PAProfesorCategoria paProfesorCategoria, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (paProfesorCategoria.getId() == null) {
            paProfesorCategoria.setCreadoPorId(idUsuario);
            paProfesorCategoria.setActivo(true);
        } else {
            PAProfesorCategoria objetoActual = paProfesorCategoriaDao.findById(paProfesorCategoria.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), paProfesorCategoria.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            paProfesorCategoria.setModificadoPorId(idUsuario);
            paProfesorCategoria.setActivo(objetoActual.getActivo());
        }


        paProfesorCategoria = paProfesorCategoriaDao.save(paProfesorCategoria);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        int actualizado = paProfesorCategoriaDao.actualizarActivo(hashId.decode(id), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
