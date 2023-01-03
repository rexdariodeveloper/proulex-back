package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Temporada;
import com.pixvs.main.models.projections.Temporada.TemporadaEditarProjection;
import com.pixvs.main.models.projections.Temporada.TemporadaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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

/*
    Controller for the management of Temporadas.
    Methods Summary:
    ---------------------------------------------------------------------------------------------------------------------
    | Method            | Route             | Description                                                               |
    |-------------------------------------------------------------------------------------------------------------------|
    | getTemporadas     | /all              | Returns a list of Temporadas                                              |
    | getTemporada      | /detail[/{id}]    | Returns an instance of Temporada by the given id and a list of criterios, |
    |                   |                   |   if id is ommited just returns a list of criterios                       |
    | saveTemporada     | /save             | Creates or update an instance of Temporada                                |
    | deleteTemporada   | /delete/{id}      | Deletes ("logical") an instance of Temporada by the given id              |
    | exportTemporadas  | /download/excel   | Builds an excel report of all Temporadas stored                           |
    ---------------------------------------------------------------------------------------------------------------------
*/

@RestController
@RequestMapping("/api/v1/temporadsa")
public class TemporadaController {

    @Autowired
    private TemporadaDao temporadaDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTemporadas() throws SQLException {

        List<TemporadaListadoProjection> temporadas = temporadaDao.findAllTemporadaBy();

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", temporadas);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detail", "/detail/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTemporada(@PathVariable(required = false) Integer id) throws SQLException {

        List<ControlMaestroMultipleComboProjection> criterios = cmmDao.findAllByControl("CMM_TEM_Criterio");

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("criterios", criterios);

        if (id != null) {
            TemporadaEditarProjection temporada = temporadaDao.findTemporadaById(id);
            datos.put("temporada", temporada);
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse saveTemporada(@RequestBody Temporada temporada, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (temporada.getId() == null) {
            temporada.setCreadoPorId(idUsuario);
            temporada.setActivo(true);
        } else {
            Temporada actual = temporadaDao.findById(temporada.getId().intValue()).get();
            try {
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(), temporada.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            temporada.setModificadoPorId(idUsuario);
        }

        temporada = temporadaDao.save(temporada);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteTemporada(@PathVariable String id) throws SQLException {

        int actualizado = temporadaDao.actualizarActivo(hashId.decode(id), false);
        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void exportTemporadas(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_TEMPORADAS]";
        String[] alColumnas = new String[]{"Código Artículo", "Nombre Artículo", "Código Alterno", "Nombre Alterno", "Descripción Corta", "Activo", "Fecha Creación"};

        excelController.downloadXlsx(response, "temporadas", query, alColumnas, null);
    }
}

