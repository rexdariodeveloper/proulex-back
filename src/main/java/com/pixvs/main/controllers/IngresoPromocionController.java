package com.pixvs.main.controllers;

import com.pixvs.spring.dao.AlertaConfigDao;
import com.pixvs.spring.dao.AlertaConfigEtapaDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.Json;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ingreso-promocion")
public class IngresoPromocionController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private AlertaConfigDao alertaConfigDao;

    @Autowired
    private AlertaConfigEtapaDao alertaConfigEtapaDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @RequestMapping(value = "/empleados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(){
        JSONArray listado = new JSONArray();
        JSONObject data1 = new JSONObject();
        data1.put("id", 1);
        data1.put("codigoEmpleado", "9798991");
        data1.put("nombre", "Juan Manuel Soto Arévalo");
        data1.put("fechaRegistro", new Date());
        data1.put("tipoContrato", "Determinado");
        data1.put("puesto", "Auxiliar contable");
        data1.put("tipoActividad", "Administrativa");
        listado.add(data1);

        HashMap<String, Object> json = new HashMap<>();
        json.put("datos", listado);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/contrataciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getcontratos() {
        JSONArray datos = new JSONArray();
        JSONObject data1 = new JSONObject();
        // 'folioSolicitud','trabajadores','fechaCreacion','tiposContrato','estatus'
        data1.put("folioSolicitud", "AT0099767");
        data1.put("trabajadores", 4);
        data1.put("fechaCreacion", new Date());
        data1.put("tiposContrato", "3 determinados, 1 temporal");
        data1.put("estatus", "En proceso");
        datos.add(data1);
        HashMap<String, Object> json = new HashMap<>();
        json.put("datos", datos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/reporte-alertas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlertas() {

        JSONArray datos = new JSONArray();
        JSONObject data1 = new JSONObject();
        data1.put("id", 1);
        data1.put("folio", "PIX90876");
        data1.put("empresa", "PIXVS");
        data1.put("fecha", new Date());
        data1.put("tipo", "Autorización");
        data1.put("iniciadaPor", "Marisol Medina");
        data1.put("estatus", "En proceso");
        data1.put("estatus", "En proceso");
        datos.add(data1);

        HashMap<String, Object> json = new HashMap<>();
        json.put("sedes", alertaConfigDao.getSucursales());
        json.put("tipos", cmmDao.findAllByControl("CMM_CALRD_TipoAlerta"));
        json.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
        json.put("estatus", cmmDao.findAllByControl("CMM_CALE_EstatusAlerta"));
        json.put("alertas", datos);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alerta-detalle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlertaDetalle() {
        HashMap<String, Object> json = new HashMap<>();
        JSONArray data = new JSONArray();
        json.put("datos", data);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }
}
