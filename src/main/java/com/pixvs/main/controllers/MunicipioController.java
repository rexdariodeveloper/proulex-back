package com.pixvs.main.controllers;


import com.pixvs.spring.dao.MunicipioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 02/09/2021.
 */
@RestController
@RequestMapping("/api/v1/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioDao municipioDao;

    @RequestMapping(value = "/listado/combo/{estadoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable Integer estadoId) {

        List<MunicipioComboProjection> municipios = municipioDao.findProjectedComboAllByEstadoId(estadoId);

        return new JsonResponse(municipios, null, JsonResponse.STATUS_OK);
    }

}
