package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlumnoDao;
import com.pixvs.main.dao.OrdenVentaDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaReporteProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-ventas")
public class ReporteVentaController {

    @Autowired
    private OrdenVentaDao ordenVentaDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private HashId hashId;

    @Autowired
    AlumnoDao alumnoDao;

    @Autowired
    ExcelController excelController;

    @Autowired
    private PuntoVentaController puntoVentaController;

    @Autowired
    private CapturaCalificacionesController capturaCalificacionesController;

    @Autowired
    private KardexAlumnoController kardexAlumnoController;

    @Autowired
    private OrdenVentaCancelacionController ovCancelacionController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("sucursales", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody HashMap<String, Object> json) throws SQLException, ParseException {
        List<OrdenVentaReporteProjection> reporte =
                ordenVentaDao.findReporteVentas(
                        StringCheck.getValue((String) json.get("fechaInicio")),
                        StringCheck.getValue((String) json.get("fechaFin")),
                        Utilidades.getListItems(json.get("sede"), "id"),
                        StringCheck.getValue((String) json.get("ordenVenta"))
                );

        return new JsonResponse(reporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/imprimir/archivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public void imprimirArchivos(@RequestBody HashMap<String, Object> body, HttpServletResponse response) throws Exception {
        int detalleId = hashId.decode((String) body.get("detalleId"));
        int archivoId = (Integer) body.get("archivoId");

        OrdenVentaReporteProjection detalle = ordenVentaDao.findOVReporteProjectedById(detalleId);
        HashMap<String, Object> filtros;

        switch (archivoId) {
            case 1:
                filtros = new HashMap<>();
                filtros.put("ordenesVentaIdsStr", String.valueOf(detalle.getOrdenVentaId()));
                filtros.put("sucursalId", detalle.getSucursalId());

                puntoVentaController.descargarPdf(filtros, response);
                break;

            case 2:
                JSONObject json = new JSONObject();
                json.put("alumnoId", detalle.getAlumnoId());
                json.put("grupoId", detalle.getGrupoId());

                capturaCalificacionesController.downloadBoleta(json, response);
                break;

            case 3:
                kardexAlumnoController.descargarKardex(alumnoDao.findById(detalle.getAlumnoId()), false, response);
                break;

            case 5:
                filtros = new HashMap<>();
                filtros.put("ordenVentaCancelacionId", detalle.getCancelacionId());

                ovCancelacionController.imprimirFormato(filtros, response);
                break;
        }
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, ParseException {
        HashMap<String, Object> params = new HashMap<>();

        params.put("fechaInicio", StringCheck.getValue((String) json.get("fechaInicio")));
        params.put("fechaFin", StringCheck.getValue((String) json.get("fechaFin")));
        params.put("sedesId", Utilidades.getListItems(json.get("sede"), "id"));
        params.put("ordenVenta", StringCheck.getValue((String) json.get("ordenVenta")));

        String query =
                "SELECT sede,\n" +
                "       plantel,\n" +
                "       nombreArticulo,\n" +
                "       inscripcion,\n" +
                "       fechaOV,\n" +
                "       notaVenta,\n" +
                "       estatusOV,\n" +
                "       folioFiscal,\n" +
                "       usuarioOV,\n" +
                "       nombre,\n" +
                "       grupoSucursal,\n" +
                "       codigoGrupo,\n" +
                "       curso,\n" +
                "       modalidad,\n" +
                "       nivel,\n" +
                "       idioma,\n" +
                "       horario,\n" +
                "       fechaInicio,\n" +
                "       fechaFin,\n" +
                "       subTotal,\n" +
                "       descuento,\n" +
                "       impuestos,\n" +
                "       total,\n" +
                "       porcentajeBeca,\n" +
                "       razonDescuento,\n" +
                "       estatusInscripcion,\n" +
                "       entregaLibro\n" +
                "FROM VW_RPT_VENTAS\n" +
                "WHERE (COALESCE(:sedesId, 0) = 0 OR sucursalId IN(:sedesId))\n" +
                "       AND fechaOV BETWEEN ISNULL(CONVERT(NVARCHAR(4000), :fechaInicio), '1900-01-01') AND ISNULL(CONVERT(NVARCHAR(4000), :fechaFin), '2100-12-31')\n" +
                "       AND CASE WHEN :ordenVenta IS NULL THEN 1 ELSE CASE WHEN notaVenta = :ordenVenta THEN 1 ELSE 0 END END = 1\n" +
                "ORDER BY ordenVentaId, cancelacionId\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{
                "SEDE", "PLANTEL VENTA", "ARTÍCULO", "INSCRIPCIÓN", "FECHA", "NOTA DE VENTA", "ESTATUS NOTA DE VENTA", "FOLIO FISCAL",
                "USUARIO VENTA", "NOMBRE", "SEDE GRUPO", "CÓDIGO DE GRUPO", "CURSO", "MODALIDAD", "NIVEL", "IDIOMA",
                "HORARIO", "FECHA INICIO", "FECHA FIN", "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL", "PORCENTAJE DE BECA",
                "MOTIVO DESCUENTO", "ESTATUS DE LA INSCRIPCIÓN", "ENTREGA LIBRO"
        };

        String[] totales = new String[]{ "TOTAL" };
        String[] columnasMoneda = new String[]{ "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL" };

        excelController.downloadXlsx(response, "Reporte_Ventas", query, columnas, totales, columnasMoneda, params, "Ventas");
    }
}