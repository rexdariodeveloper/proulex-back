package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloSubtipoDao;
import com.pixvs.main.models.ArticuloSubtipo;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoComboProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoEditarProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoListadoProjection;
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
@RequestMapping("/api/v1/articulos-subtipos")
public class ArticuloSubtipoController {

    @Autowired
    private ArticuloSubtipoDao articuloSubtipoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArticulosSubtipos() throws SQLException {

        List<ArticuloSubtipoListadoProjection> articulosSubtipos = articuloSubtipoDao.findProjectedListadoAllBy();

        return new JsonResponse(articulosSubtipos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ArticuloSubtipo articuloSubtipo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (articuloSubtipo.getId() == null) {
            articuloSubtipo.setActivo(true);
        } else {
            ArticuloSubtipo objetoActual =articuloSubtipoDao.findById(articuloSubtipo.getId().intValue());
        }

        articuloSubtipo = articuloSubtipoDao.save(articuloSubtipo);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idArticuloSubtipo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idArticuloSubtipo) throws SQLException {

        ArticuloSubtipoEditarProjection articuloSubtipo = articuloSubtipoDao.findProjectedEditarById(idArticuloSubtipo);

        return new JsonResponse(articuloSubtipo, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticuloSubtipo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticuloSubtipo) throws SQLException {

        int actualizado = articuloSubtipoDao.actualizarActivo(hashId.decode(idArticuloSubtipo), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoArticulosSubtiposById(@PathVariable(required = false) Integer id) throws SQLException {

        ArticuloSubtipoEditarProjection articuloSubtipo = null;
        if (id != null) {
            articuloSubtipo = articuloSubtipoDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("articuloSubtipo", articuloSubtipo);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ARTICULOS_SUBTIPOS]";
        String[] alColumnas = new String[]{"Descripcion"};

        excelController.downloadXlsx(response, "articulosSubtipos", query, alColumnas, null);
    }

    @RequestMapping(value = "/listado/combo/{articuloTipoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable(required = false) Integer articuloTipoId) throws SQLException {

        List<ArticuloSubtipoComboProjection> subtipos = articuloSubtipoDao.findProjectedComboAllByArticuloTipoIdAndActivoTrue(articuloTipoId);

        return new JsonResponse(subtipos, null, JsonResponse.STATUS_OK);
    }


}

