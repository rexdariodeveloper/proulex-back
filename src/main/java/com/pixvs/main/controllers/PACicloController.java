package com.pixvs.main.controllers;

import com.pixvs.main.dao.FormaPagoDao;
import com.pixvs.main.dao.PACicloDao;
import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.projections.FormaPago.FormaPagoListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
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
@RequestMapping("/api/v1/ciclos")
public class PACicloController {

    @Autowired
    private PACicloDao paCicloDao;

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

        List<PACicloListadoProjection> ciclos = paCicloDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(ciclos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PACiclo paCiclo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (paCiclo.getId() == null) {
            paCiclo.setCreadoPorId(idUsuario);
            paCiclo.setActivo(true);
        } else {
            PACiclo objetoActual = paCicloDao.findById(paCiclo.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), paCiclo.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            paCiclo.setModificadoPorId(idUsuario);
            paCiclo.setActivo(objetoActual.getActivo());
        }


        paCiclo = paCicloDao.save(paCiclo);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        int actualizado = paCicloDao.actualizarActivo(hashId.decode(id), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
