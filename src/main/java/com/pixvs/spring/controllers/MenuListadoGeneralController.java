package com.pixvs.spring.controllers;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.MenuListadoGeneralDao;
import com.pixvs.spring.dao.MenuListadoGeneralDetalleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.MenuListadoGeneral;
import com.pixvs.spring.models.projections.MenuListadoGeneral.MenuListadoGeneralEditarProjection;
import com.pixvs.spring.models.projections.MenuListadoGeneralDetalle.MenuListadoGeneralDetalleEditarProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/06/2020.
 */
@RestController
@RequestMapping("/api/v1/menu-listados-generales")
public class MenuListadoGeneralController {

    @Autowired
    private MenuListadoGeneralDao menuListadoGeneralDao;
    @Autowired
    private MenuListadoGeneralDetalleDao menuListadoGeneralDetalleDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = {"/all", "/all/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getMenuListadosGenerales(@PathVariable(required = false) Integer id) throws SQLException {

        JSONObject menu = getMenu(null);
        List<MenuListadoGeneralDetalleEditarProjection> campos = new ArrayList<>();
        if(id != null){
            campos = menuListadoGeneralDetalleDao.findAllProjectedEditarByListadoGeneralMenuId(id);
        }

        HashMap<String,Object> datos = new HashMap<>();
        datos.put("menu",menu);
        datos.put("campos",campos);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody MenuListadoGeneral menuListadoGeneral, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (menuListadoGeneral.getId() == null) {
            menuListadoGeneral.setCreadoPorId(idUsuario);
            menuListadoGeneral.setActivo(true);
        } else {
            MenuListadoGeneral objetoActual = menuListadoGeneralDao.findById(menuListadoGeneral.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), menuListadoGeneral.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            menuListadoGeneral.setModificadoPorId(idUsuario);
        }

        menuListadoGeneral = menuListadoGeneralDao.save(menuListadoGeneral);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idMenuListadoGeneral}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idMenuListadoGeneral) throws SQLException {

        MenuListadoGeneralEditarProjection menuListadoGeneral = menuListadoGeneralDao.findProjectedEditarById(idMenuListadoGeneral);

        return new JsonResponse(menuListadoGeneral , null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idMenuListadoGeneral}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idMenuListadoGeneral) throws SQLException {

        int actualizado = menuListadoGeneralDao.actualizarActivo(hashId.decode(idMenuListadoGeneral), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoMenuListadosGeneralesById(@PathVariable(required = false) Integer id) throws SQLException {

        MenuListadoGeneralEditarProjection menuListadoGeneral = null;
        if (id != null) {
            menuListadoGeneral = menuListadoGeneralDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("menuListadoGeneral", menuListadoGeneral);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_MENU_LISTADOS_GENERALES]";
        String[] alColumnas = new String[]{"Nodo Padre Id", "Título", "Título EN", "Activo", "Icono", "Orden", "Tipo Nodo", "Fecha Creación"};

        excelController.downloadXlsx(response, "menuListadosGenerales", query, alColumnas, null);
    }

    private JSONObject getMenu(List<MenuListadoGeneral> alMenu) {

        if (alMenu == null) {
            alMenu = menuListadoGeneralDao.findAllModelsByNodoPadreIdOrderByOrdenAsc(-1);
        }


        JSONObject jsonExtras = new JSONObject();
        JSONObject jsonEN = new JSONObject();
        JSONObject jsonES = new JSONObject();
        JSONObject jsonTraducciones = new JSONObject();

        jsonEN.put("lang", "en");
        jsonEN.put("data", new JSONObject());

        jsonES.put("lang", "es");
        jsonES.put("data", new JSONObject());

        jsonTraducciones.put("EN", jsonEN);
        jsonTraducciones.put("ES", jsonES);
        jsonExtras.put("traducciones", jsonTraducciones);

        jsonExtras = getChildren(alMenu, jsonExtras);


        JSONObject jsonMenu = new JSONObject();
        jsonMenu.put("navigation", alMenu);
        jsonMenu.put("traducciones", jsonExtras.get("traducciones"));

        return jsonMenu;
    }

    private JSONObject getChildren(List<MenuListadoGeneral> alMenu, JSONObject jsonExtras) {
        for (MenuListadoGeneral menu : alMenu) {
            ((JSONObject) ((JSONObject) ((JSONObject) jsonExtras.get("traducciones")).get("ES")).get("data")).put("M" + menu.getId().toString(), menu.getTitulo());
            ((JSONObject) ((JSONObject) ((JSONObject) jsonExtras.get("traducciones")).get("EN")).get("data")).put("M" + menu.getId().toString(), menu.getTituloEN());

            List<MenuListadoGeneral> alChildren;
            alChildren = menuListadoGeneralDao.findAllModelsByNodoPadreIdOrderByOrdenAsc(menu.getId());

            if (alChildren.size() > 0) {
                menu.getChildren().addAll(alChildren);
                getChildren(alChildren, jsonExtras);
            }
        }

        return jsonExtras;
    }

}
