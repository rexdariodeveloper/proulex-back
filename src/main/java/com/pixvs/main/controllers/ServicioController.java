package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Servicio;
import com.pixvs.main.models.projections.Servicio.ServicioEditarProjection;
import com.pixvs.main.models.projections.Servicio.ServicioListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
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
@RequestMapping("/api/v1/servicio")
public class ServicioController {

    @Autowired
    private ServicioDao servicioDao;

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

        List<ServicioListadoProjection> ordenesCompra = servicioDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(ordenesCompra, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Servicio servicio, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (servicio.getId() == null) {
            servicio.setCreadoPorId(idUsuario);
            servicio.setActivo(true);
        } else {
            Servicio actual = servicioDao.findById(servicio.getId().intValue());
			 try{
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(),servicio.getFechaModificacion());
			 }catch (Exception e){
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
			 }
            servicio.setModificadoPorId(idUsuario);
            servicio.setActivo(actual.getActivo());
        }

        servicio = servicioDao.save(servicio);

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

        servicioDao.actualizarActivo(servicioId,false);

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


