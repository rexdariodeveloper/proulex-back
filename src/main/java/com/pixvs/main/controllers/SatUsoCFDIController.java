package com.pixvs.main.controllers;

import com.pixvs.main.dao.ServicioDao;
import com.pixvs.main.models.projections.Servicio.ServicioEditarProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.SATUsoCFDIDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.SATUsoCFDI;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import com.pixvs.spring.services.AutonumericoService;
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

@RestController
@RequestMapping("/api/v1/satUsoCfdi")
public class SatUsoCFDIController {

    @Autowired
    private ServicioDao servicioDao;
    @Autowired
    private SATUsoCFDIDao usoCFDIDao;

    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getServicios() throws SQLException {

        List<SATUsoCFDIComboProjection> listUsoCFDI = usoCFDIDao.findAllComboProjectedByActivoTrueOrderByCodigo();

        return new JsonResponse(listUsoCFDI, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody SATUsoCFDI cfdi, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (cfdi.getId() == null) {
            cfdi.setActivo(true);
        } else {
            cfdi.setActivo(cfdi.isActivo());
        }

        cfdi = usoCFDIDao.save(cfdi);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer id) throws SQLException {

        ServicioEditarProjection servicio = servicioDao.findProjectedById(id);

        return new JsonResponse(servicio, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        Integer servicioId = null;
        servicioId = hashId.decode(id);

        usoCFDIDao.actualizarActivo(servicioId,false);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoOrdenesCompraById(@PathVariable(required = false) Integer id) throws SQLException {

        ServicioEditarProjection servicio = null;
        if (id != null)
            servicio = servicioDao.findProjectedById(id);

        HashMap<String, Object> json = new HashMap<>();
        json.put("servicio", servicio);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        //TODO: Crear la función VW_LISTADO_SERVICIOS
        String query = "SELECT * from [VW_LISTADO_SERVICIOS]";
        //TODO: Definir columnas del excel
        String[] alColumnas = new String[]{"Código", "Fecha O C", "Fecha O C", "Estatus Id"};

        excelController.downloadXlsx(response, "servicios", query, alColumnas, null);
    }

}


