package com.pixvs.main.controllers;

import com.pixvs.main.dao.ProveedorContactoDao;
import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoEditarProjection;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoListadoProjection;
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
@RequestMapping("/api/v1/proveedores-contactos")
public class ProveedorContactoController {

    @Autowired
    private ProveedorContactoDao proveedorContactoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedoresContactos() throws SQLException {

        List<ProveedorContactoListadoProjection> proveedoresContactos = proveedorContactoDao.findProjectedListadoAllBy();

        return new JsonResponse(proveedoresContactos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProveedorContacto proveedorContacto, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (proveedorContacto.getId() == null) {
            proveedorContacto.setCreadoPorId(idUsuario);
            proveedorContacto.setActivo(true);
        } else {
        ProveedorContacto objetoActual =proveedorContactoDao.findById(proveedorContacto.getId().intValue());
			 try{
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),proveedorContacto.getFechaModificacion());
			 }catch (Exception e){
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
			 }
            proveedorContacto.setModificadoPorId(idUsuario);
        }

        proveedorContacto = proveedorContactoDao.save(proveedorContacto);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idProveedorContacto}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idProveedorContacto) throws SQLException {

        ProveedorContactoEditarProjection proveedorContacto = proveedorContactoDao.findProjectedEditarById(idProveedorContacto);

        return new JsonResponse(proveedorContacto, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idProveedorContacto}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idProveedorContacto) throws SQLException {

        int actualizado = proveedorContactoDao.actualizarActivo(hashId.decode(idProveedorContacto), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresContactosById(@PathVariable(required = false) Integer id) throws SQLException {

        ProveedorContactoEditarProjection proveedorContacto = null;
        if (id != null) {
            proveedorContacto = proveedorContactoDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("proveedorContacto", proveedorContacto);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_PROVEEDORES_CONTACTOS]";
        String[] alColumnas = new String[]{"Activo", "Nombre", "Primer Apellido", "Segundo Apellido"};

        excelController.downloadXlsx(response, "proveedoresContactos", query, alColumnas, null);
    }


}

