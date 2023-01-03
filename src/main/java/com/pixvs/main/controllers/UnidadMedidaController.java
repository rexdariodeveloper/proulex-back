package com.pixvs.main.controllers;

import com.pixvs.main.dao.UnidadMedidaDao;
import com.pixvs.main.models.UnidadMedida;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 25/06/2020.
 */
@RestController
@RequestMapping("/api/v1/unidades-medidas")
public class UnidadMedidaController {

    @Autowired
    private UnidadMedidaDao unidadMedidaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<UnidadMedidaListadoProjection> unidadesMedida = unidadMedidaDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(unidadesMedida, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody UnidadMedida unidadMedida, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (unidadMedida.getId() == null) {
            unidadMedida.setCreadoPorId(idUsuario);
            unidadMedida.setActivo(true);
        } else {
            UnidadMedida objetoActual = unidadMedidaDao.findById(unidadMedida.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), unidadMedida.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            unidadMedida.setModificadoPorId(idUsuario);
            unidadMedida.setActivo(objetoActual.getActivo());
        }

        unidadMedida = unidadMedidaDao.save(unidadMedida);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idUnidadMedida}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idUnidadMedida) throws SQLException {

        int actualizado = unidadMedidaDao.actualizarActivo(hashId.decode(idUnidadMedida), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
