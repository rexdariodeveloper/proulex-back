package com.pixvs.main.controllers;

import com.pixvs.main.dao.ProveedorFormaPagoDao;
import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoEditarProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoListadoProjection;
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
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@RestController
@RequestMapping("/api/v1/proveedores-formas-pagos")
public class ProveedorFormaPagoController {

    @Autowired
    private ProveedorFormaPagoDao proveedorFormaPagoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedoresFormasPagos() throws SQLException {

        List<ProveedorFormaPagoListadoProjection> proveedoresFormasPagos = proveedorFormaPagoDao.findProjectedListadoAllBy();

        return new JsonResponse(proveedoresFormasPagos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProveedorFormaPago proveedorFormaPago, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (proveedorFormaPago.getId() == null) {
            proveedorFormaPago.setCreadoPorId(idUsuario);
            proveedorFormaPago.setActivo(true);
        } else {
        ProveedorFormaPago objetoActual =proveedorFormaPagoDao.findById(proveedorFormaPago.getId().intValue());
			 try{
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),proveedorFormaPago.getFechaModificacion());
			 }catch (Exception e){
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
			 }
            proveedorFormaPago.setModificadoPorId(idUsuario);
        }

        proveedorFormaPago = proveedorFormaPagoDao.save(proveedorFormaPago);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idProveedorFormaPago}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idProveedorFormaPago) throws SQLException {

        ProveedorFormaPagoEditarProjection proveedorFormaPago = proveedorFormaPagoDao.findProjectedEditarById(idProveedorFormaPago);

        return new JsonResponse(proveedorFormaPago, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idProveedorFormaPago}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idProveedorFormaPago) throws SQLException {

        int actualizado = proveedorFormaPagoDao.actualizarActivo(hashId.decode(idProveedorFormaPago), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresFormasPagosById(@PathVariable(required = false) Integer id) throws SQLException {

        ProveedorFormaPagoEditarProjection proveedorFormaPago = null;
        if (id != null) {
            proveedorFormaPago = proveedorFormaPagoDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("proveedorFormaPago", proveedorFormaPago);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_PROVEEDORES_FORMAS_PAGOS]";
        String[] alColumnas = new String[]{"Activo", "Referencia"};

        excelController.downloadXlsx(response, "proveedoresFormasPagos", query, alColumnas, null);
    }


}

