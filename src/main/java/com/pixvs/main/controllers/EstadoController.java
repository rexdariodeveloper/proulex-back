package com.pixvs.main.controllers;


import com.pixvs.spring.dao.EstadoDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@RestController
@RequestMapping("/api/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoDao estadoDao;

    @RequestMapping(value = "/listado/combo/{paisId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable(required = false) Integer paisId) throws SQLException {

        List<EstadoComboProjection> estados = estadoDao.findProjectedComboAllByPaisId(paisId);

        return new JsonResponse(estados, null, JsonResponse.STATUS_OK);
    }

}
