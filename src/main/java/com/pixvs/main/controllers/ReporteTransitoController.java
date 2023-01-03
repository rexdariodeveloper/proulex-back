package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlmacenDao;
import com.pixvs.main.dao.ArticuloDao;
import com.pixvs.main.dao.InventarioMovimientoDao;
import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.RolMenuPermiso;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transito")
public class ReporteTransitoController {

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private InventarioMovimientoDao inventarioMovimientoDao;

    @Autowired
    private ControlMaestroDao controlMaestroDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("almacenes", almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));
        datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        return new JsonResponse(
                inventarioMovimientoDao.reporteArticulosTransito((String) filtros.get("localidades")),
                null,
                JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        ArrayList<HashMap<String, Object>> allLocalidades = (ArrayList<HashMap<String, Object>>) json.get("localidades");
        String localidades = arrayToSring(allLocalidades);

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("localidades",localidades);

        return filtros;
    }

    private String arrayToSring(ArrayList<HashMap<String, Object>> data){
        return arrayToString(data,"id", "|");
    }

    private String arrayToString(ArrayList<HashMap<String, Object>> data, String property, String glue){
        String joined = null;
        if (data == null) return joined;
        joined = "";
        for (HashMap<String, Object> reg : data) {
            joined += glue + reg.get(property).toString() + glue;
        }
        return joined;
    }

}
