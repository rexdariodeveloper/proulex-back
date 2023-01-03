package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardDao dashboardDao;
    @Autowired
    private SucursalDao sucursalDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDashboard(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", dashboardDao.getBannerData());
        datos.put("sedes", sucursalDao.findProjectedComboAllByActivoTrue());

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/horario-idioma", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getHorarioIdioma(@RequestBody JSONObject json, ServletRequest req) throws Exception {
        HashMap<String, Object> filtros = getFiltros(json);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", dashboardDao.getHorarioIdiomaData((String) filtros.get("year"),
                                                             (String) filtros.get("month"),
                                                             (String) filtros.get("sedes")));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/programa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPrograma(@RequestBody JSONObject json, ServletRequest req) throws Exception {
        HashMap<String, Object> filtros = getFiltros(json);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", dashboardDao.getPrograma((String) filtros.get("year"),
                                                    (String) filtros.get("month"),
                                                    (String) filtros.get("sedes")));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/descuentos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDescuentos(@RequestBody JSONObject json, ServletRequest req) throws Exception {
        HashMap<String, Object> filtros = getFiltros(json);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", dashboardDao.getDescuentos((String) filtros.get("year"),
                                                      (String) filtros.get("month"),
                                                      (String) filtros.get("sedes")));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/sede", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSede(@RequestBody JSONObject json, ServletRequest req) throws Exception {
        HashMap<String, Object> filtros = getFiltros(json);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", dashboardDao.getSede((String) filtros.get("year"),
                                                (String) filtros.get("month"),
                                                (String) filtros.get("sedes")));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        ArrayList<HashMap<String, Object>> allSedes = (ArrayList<HashMap<String, Object>>) json.get("sedes");
        String sedes = arrayToSring(allSedes);

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("year", (String) json.get("year"));
        filtros.put("month", json.get("month") != null ? ((HashMap<String, String>) json.get("month")).get("id") : null);
        filtros.put("sedes",sedes);

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
