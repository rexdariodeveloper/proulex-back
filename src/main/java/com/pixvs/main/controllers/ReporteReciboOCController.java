package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
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
import org.jfree.chart.util.TextUtils;
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
@RequestMapping("/api/v1/reporte_oc")
public class ReporteReciboOCController {

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private ProveedorDao proveedorDao;

    @Autowired
    private OrdenCompraDao ordenCompraDao;

    @Autowired
    private ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> filtros = new HashMap<>();

        filtros.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
        filtros.put("almacenes", almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));
        filtros.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());

        data.put("filtros", filtros);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        HashMap<String, Object> data = new HashMap<>();

        data.put("tipo", filtros.get("tipo"));

        if((Integer) filtros.get("tipo") == 0){ //Por recibir
            data.put("reporte", ordenCompraDao.getRecibosPendientes((String) filtros.get("fechaInicio"),
                                                                    (String) filtros.get("fechaFin"),
                                                                    (String) filtros.get("codigo"),
                                                                    (Integer) filtros.get("articulo"),
                                                                    (Integer) filtros.get("proveedor"),
                                                                    (Integer) filtros.get("almacen")));
            return new JsonResponse(data,null,JsonResponse.STATUS_OK);
        }
        else{ //Recibidos
            data.put("reporte", ordenCompraDao.getRecibos((String) filtros.get("fechaInicio"),
                                                          (String) filtros.get("fechaFin"),
                                                          (String) filtros.get("codigo"),
                                                          (Integer) filtros.get("articulo"),
                                                          (Integer) filtros.get("proveedor"),
                                                          (Integer) filtros.get("almacen")));
            return new JsonResponse(data,null,JsonResponse.STATUS_OK);
        }
    }

    @RequestMapping(value = "/download/excel", method = RequestMethod.POST)
    public void downloadXlsx(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        Object fechaInicio = filtros.get("fechaInicio");
        Object fechaFin    = filtros.get("fechaFin");
        String codigo      = (String) filtros.get("codigo");
        Integer articulo   = (Integer) filtros.get("articulo");
        Integer proveedor  = (Integer) filtros.get("proveedor");
        Integer almacen    = (Integer) filtros.get("almacen");

        Date fecha0 = new SimpleDateFormat("yyyy-MM-dd").parse((String) filtros.get("fechaInicio"));
        Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse((String) filtros.get("fechaFin"));

        if((Integer) filtros.get("tipo") == 0){
            String consulta = "SELECT " +
                    "codigoOC AS N'OC', fechaOC AS N'Fecha OC', proveedor AS N'Proveedor',articulo AS N'Artículo',um AS N'UM', " +
                    "cantidadRequerida AS N'Cantidad requerida', cantidadRecibida AS N'Cantidad recibida', " +
                    "cantidadPendiente AS N'Cantidad por recibir' " +
                    "FROM [dbo].[VW_REPORTE_OC_RECIBOS_PENDIENTES] WHERE " +
                    "CAST(fechaOC AS DATE) BETWEEN CAST('"+fechaInicio+"' AS DATE) AND CAST('"+fechaFin+"' AS DATE) " +
                    ((codigo == null || codigo.isEmpty())? "" : ("AND codigoOC = "+codigo+" ")) +
                    (articulo == null? "" : ("AND articuloId = "+articulo+" ")) +
                    (proveedor == null? "" : ("AND proveedorId = "+proveedor+" ")) +
                    (almacen == null? "" : ("AND almacenId = "+almacen+" "));

            String[] columnas = new String[]{ "OC","Fecha OC","Proveedor","Artículo","UM","Cantidad requerida","Cantidad recibida","Cantidad por recibir"};
            excelController.downloadXlsx(response,"Recibo OC",consulta,columnas,null,"Por recibir");
        } else {
            String consulta = "SELECT " +
                    "codigoOC AS N'OC', fechaOC AS N'Fecha OC', proveedor AS N'Proveedor', tipoMovimiento AS N'Tipo de movimiento', " +
                    "articulo AS N'Artículo' ,um AS N'UM', cantidadRequerida AS N'Cantidad requerida', fechaMovimiento AS N'Fecha del movimiento', " +
                    "cantidadMovimiento AS N'Cantidad del movimiento', costoPrevio AS N'Costo previo', costoRecibir AS N'Costo al recibir', "+
                    "almacen AS N'Almacén', localidad AS N'Localidad' "+
                    "FROM [dbo].[VW_REPORTE_OC_RECIBOS] WHERE " +
                    "CAST(fechaOC AS DATE) BETWEEN CAST('"+fechaInicio+"' AS DATE) AND CAST('"+fechaFin+"' AS DATE) " +
                    ((codigo == null || codigo.isEmpty())? "" : ("AND codigoOC = "+codigo+" ")) +
                    (articulo == null? "" : ("AND articuloId = "+articulo+" ")) +
                    (proveedor == null? "" : ("AND proveedorId = "+proveedor+" ")) +
                    (almacen == null? "" : ("AND almacenId = "+almacen+" "));

            String[] columnas = new String[]{ "OC","Fecha OC","Proveedor","Tipo de movimiento","Artículo","UM","Cantidad requerida","Fecha del movimiento","Cantidad del movimiento","Costo previo","Costo al recibir","Almacén","Localidad" };
            excelController.downloadXlsx(response,"Recibo OC",consulta,columnas,null,"Recibidos");
        }
    }

    public HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        Object fechaInicio = json.get("fechaInicio");
        Object fechaFin    = json.get("fechaFin");
        String codigo      = (String) json.get("oc");

        HashMap<String, Object> articulo  = (HashMap<String, Object>) json.get("articulo");
        HashMap<String, Object> almacen   = (HashMap<String, Object>) json.get("almacenes");
        HashMap<String, Object> proveedor = (HashMap<String, Object>) json.get("proveedor");
        HashMap<String, Object> tipo      = (HashMap<String, Object>) json.get("tipo");

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("fechaInicio", fechaInicio);
        filtros.put("fechaFin", fechaFin);
        filtros.put("codigo", (codigo == null || codigo.isEmpty())? null : codigo);
        filtros.put("articulo", articulo != null? (Integer) articulo.get("id") : null);
        filtros.put("almacen", almacen != null? (Integer) almacen.get("id") : null);
        filtros.put("proveedor", proveedor != null? (Integer) proveedor.get("id") : null);
        filtros.put("tipo", tipo != null? (Integer) tipo.get("id") : null);

        return filtros;
    }

}
