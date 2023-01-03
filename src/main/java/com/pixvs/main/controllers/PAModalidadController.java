package com.pixvs.main.controllers;

import com.pixvs.main.dao.PACicloDao;
import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAModalidad;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadListadoProjection;
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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/modalidades")
public class PAModalidadController {

    @Autowired
    private PAModalidadDao paModalidadDao;

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

        List<PAModalidadListadoProjection> modalidades = paModalidadDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(modalidades, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PAModalidad paModalidad, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (paModalidad.getId() == null) {
            paModalidad.setCreadoPorId(idUsuario);
            paModalidad.setActivo(true);
        } else {
            PAModalidad objetoActual = paModalidadDao.findById(paModalidad.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), paModalidad.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            paModalidad.setModificadoPorId(idUsuario);
            paModalidad.setActivo(objetoActual.getActivo());
        }


        paModalidad = paModalidadDao.save(paModalidad);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        int actualizado = paModalidadDao.actualizarActivo(hashId.decode(id), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idiomaId}/{programaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getListaModalidad(@PathVariable Integer idiomaId, @PathVariable Integer programaId, ServletRequest req) throws SQLException {

        List<PAModalidadComboSimpleProjection> listaModalidad = paModalidadDao.getListaModalidad(idiomaId, programaId);

        return new JsonResponse(listaModalidad, null, JsonResponse.STATUS_OK);
    }
}
