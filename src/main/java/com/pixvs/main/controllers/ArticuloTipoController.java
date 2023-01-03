package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloTipoDao;
import com.pixvs.main.models.ArticuloTipo;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoEditarProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoListadoProjection;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@RestController
@RequestMapping("/api/v1/articulos-tipos")
public class ArticuloTipoController {

    @Autowired
    private ArticuloTipoDao articuloTipoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArticulosTipos() throws SQLException {

        List<ArticuloTipoListadoProjection> articulosTipos = articuloTipoDao.findProjectedListadoAllBy();

        return new JsonResponse(articulosTipos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ArticuloTipo articuloTipo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (articuloTipo.getId() == null) {
            articuloTipo.setActivo(true);
        } else {
            ArticuloTipo objetoActual =articuloTipoDao.findById(articuloTipo.getId().intValue());
        }

        articuloTipo = articuloTipoDao.save(articuloTipo);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idArticuloTipo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idArticuloTipo) throws SQLException {

        ArticuloTipoEditarProjection articuloTipo = articuloTipoDao.findProjectedEditarById(idArticuloTipo);

        return new JsonResponse(articuloTipo, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticuloTipo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticuloTipo) throws SQLException {

        int actualizado = articuloTipoDao.actualizarActivo(hashId.decode(idArticuloTipo), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoArticulosTiposById(@PathVariable(required = false) Integer id) throws SQLException {

        ArticuloTipoEditarProjection articuloTipo = null;
        if (id != null) {
            articuloTipo = articuloTipoDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("articuloTipo", articuloTipo);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ARTICULOS_TIPOS]";
        String[] alColumnas = new String[]{"Descripcion"};

        excelController.downloadXlsx(response, "articulosTipos", query, alColumnas, null);
    }


}

