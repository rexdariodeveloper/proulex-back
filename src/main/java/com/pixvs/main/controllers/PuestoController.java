package com.pixvs.main.controllers;

import com.pixvs.main.dao.PuestoDao;
import com.pixvs.main.models.Puesto;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Puesto.PuestoEditarProjection;
import com.pixvs.main.models.projections.Puesto.PuestoListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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
 * Created by Benjamin Osorio on 26/09/2022.
 */
@RestController
@RequestMapping("/api/v1/puestos")
public class PuestoController {

    @Autowired
    private PuestoDao puestoDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPuestos() throws SQLException {

        List<PuestoListadoProjection> puestos = puestoDao.findProjectedListadoAllBy();

        HashMap<String,Object> data = new HashMap<>();
        data.put("datos",puestos);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id) throws SQLException {

        List<ControlMaestroMultipleComboProjection> listaEstatus = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PUESTO_Estatus.NOMBRE);
        HashMap<String,Object> datos = new HashMap<>();
        datos.put("listaEstatus",listaEstatus);

        if(id != null){
            PuestoEditarProjection puesto = puestoDao.findProjectedEditarAllById(id);
            datos.put("puesto",puesto);
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Puesto puesto, ServletRequest req) throws Exception {
        boolean esNuevo = false;
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        puesto.setEstadoPuestoId(puesto.getEstadoPuesto().getId());

        if(puesto.getId() == null){
            puesto.setCreadoPorId(idUsuario);
        }else{
            puesto.setModificadoPorId(idUsuario);
        }

        puesto = puestoDao.save(puesto);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);

    }


    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "SELECT * from [VW_LISTADO_PUESTOS]";
        String[] alColumnas = new String[]{"Nombre", "Descripción", "Propósito", "Observaciones", "Estatus"};

        excelController.downloadXlsx(response, "Puestos", query, alColumnas, null, "Puestos");
    }

}
