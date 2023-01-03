package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlmacenDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.dao.UsuarioDatosAdicionalesDao;
import com.pixvs.spring.models.projections.Departamento.DepartamentoNodoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.JsonResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 28/07/2020.
 */
@RestController
@RequestMapping("/api/v1/usuario-datos-adicionales")
public class UsuarioDatosAdicionalesController {

    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;
    @Autowired
    private DepartamentoDao departamentoDao;

    @RequestMapping(value = "/datos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(@RequestBody HashMap<String,String> body) throws SQLException {

        Integer usuarioId = null;
        if(body.get("usuarioId") != null){
            usuarioId = Integer.parseInt(body.get("usuarioId"));
        }

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        List<DepartamentoNodoProjection> departamentosTree = departamentoDao.findProjectedNodoAllByActivoTrueAndDepartamentoPadreIdIsNull();

        JSONObject almacenesMap = new JSONObject();
        for(SucursalComboProjection sucursal : sucursales){
            almacenesMap.put(sucursal.getId().toString(),almacenDao.findProjectedComboAllBySucursalIdAndActivoTrue(sucursal.getId()));
        }

        HashMap<String,Object> datos = new HashMap<>();
        datos.put("sucursales",sucursales);
        datos.put("almacenesMap",almacenesMap);
        datos.put("departamentosTree",departamentosTree);

        if(usuarioId != null){
            datos.put("usuarioDatosAdicionales",usuarioDatosAdicionalesDao.findProjectedDatosAdicionalesById(usuarioId));
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

}
