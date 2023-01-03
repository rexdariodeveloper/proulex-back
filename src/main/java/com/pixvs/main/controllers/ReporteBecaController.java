package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGReporteFiltrosProjection;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGReporteProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/reporte-becas")
public class ReporteBecaController {

    @Autowired
    private EntidadBecaDao entidadBecaDao;

    @Autowired
    private BecaUDGDao becaUDGDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    ProgramaGrupoDao programaGrupoDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ReporteService reporteService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("clientes", entidadBecaDao.findProjectedComboAllByActivoTrueOrderByNombre());
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("permisoExcel", rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.RPT_BECAS_EXPORTAR_EXCEL));
        json.put("permisoDescargar", rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.RPT_BECAS_DESCARGAR_REPORTES));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody HashMap<String, Object> json) throws Exception {
        List<BecaUDGReporteProjection> becas =
                becaUDGDao.getReporteBecas(
                        Utilidades.getListItems(json.get("cliente"), "id"),
                        Utilidades.getListItems(json.get("sede"), "id"),
                        Utilidades.getListItems(json.get("modalidad"), "id"),
                        Utilidades.getListItems(json.get("fechas"), "fecha")
                );

        return new JsonResponse(becas, null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/descargar/reportes", method = RequestMethod.POST)
    public void descargarDocumentos(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        try {
            List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
            List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");

            List<BecaUDGReporteFiltrosProjection> filtros =
                    becaUDGDao.getReporteBecasFiltros(
                            Utilidades.getListItems(json.get("cliente"), "id"),
                            Utilidades.getListItems(json.get("sede"), "id"),
                            modalidadesId,
                            fechas
                    );

            HashMap<String, InputStream> archivos = new HashMap<>();

            for (BecaUDGReporteFiltrosProjection filtro : filtros) {
                Map<String, Object> parameters = new HashMap<>();

                parameters.put("clienteId", filtro.getClienteId());
                parameters.put("sedeId", filtro.getSedeId());
                parameters.put("allModalidades", modalidadesId == null || modalidadesId.isEmpty());
                parameters.put("idsModalidades", modalidadesId);
                parameters.put("allFechas", fechas == null || fechas.isEmpty());
                parameters.put("fechas", fechas);
                parameters.put("esCien", filtro.isCienPorciento());

                archivos.put(filtro.getNombre() + ".pdf", reporteService.generarJasperReport("/modulos/programacion-academica/Becas.jasper", parameters, ReporteServiceImpl.output.PDF, true));
            }

            reporteService.downloadAsZip(response, archivos, "Reportes de Becas");
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, ParseException {
        HashMap<String, Object> params = new HashMap<>();

        params.put("clientesId", Utilidades.getListItems(json.get("cliente"), "id"));
        params.put("sedesId", Utilidades.getListItems(json.get("sede"), "id"));
        params.put("modalidadesId", Utilidades.getListItems(json.get("modalidad"), "id"));
        params.put("fechas", Utilidades.getListItems(json.get("fechas"), "fecha"));

        String query =
                "SELECT codigoPlic,\n" +
                "       codigoBecaUdg,\n" +
                "       codigoEmpleado,\n" +
                "       primerApellido,\n" +
                "       segundoApellido,\n" +
                "       nombre,\n" +
                "       descuentoUDG,\n" +
                "       nivelUDG,\n" +
                "       fechaAlta,\n" +
                "       curso,\n" +
                "       modalidad,\n" +
                "       parentesco,\n" +
                "       sede,\n" +
                "       nivel,\n" +
                "       horaInscripcion,\n" +
                "       numeroGrupo,\n" +
                "       codigoGrupo,\n" +
                "       fechaInicio,\n" +
                "       fechaFin,\n" +
                "       fechaInscripcion,\n" +
                "       notaVenta,\n" +
                "       calificacion,\n" +
                "       estatusBeca,\n" +
                "       cliente,\n" +
                "       descuentoCliente,\n" +
                "       totalCliente,\n" +
                "       comentarios\n" +
                "FROM VW_RPT_BecasUDG\n" +
                "WHERE (COALESCE(:clientesId, 0) = 0 OR clienteId IN(:clientesId))\n" +
                "       AND (COALESCE(:sedesId, 0) = 0 OR sedeId IN(:sedesId))\n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                "       AND (COALESCE(:fechas, '') = '' OR FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas))\n" +
                "ORDER BY codigoPlic, codigoBecaUdg\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{
                "CÓDIGO ALUMNO", "CÓDIGO BECA UDG", "CÓDIGO EMPLEADO", "PRIMER APELLIDO", "SEGUNDO APELLIDO", "NOMBRE",
                "DESCUENTO UDG", "NIVEL UDG", "FECHA ALTA", "CURSO", "MODALIDAD", "PARENTESCO", "SEDE INSCRIBIÓ", "NIVEL",
                "HORA INSCRIPCIÓN", "NUMERO GRUPO", "CÓDIGO GRUPO", "FECHA INICIO", "FECHA FIN", "FECHA INSCRIPCIÓN",
                "NOTA VENTA", "CALIFICACIÓN", "ESTATUS BECA", "CLIENTE", "DESCUENTO CLIENTE", "TOTAL CLIENTE", "COMENTARIOS BECA"
        };
        String[] totales = new String[]{"DESCUENTO CLIENTE", "TOTAL CLIENTE"};
        String[] columnasMoneda = new String[]{"DESCUENTO CLIENTE", "TOTAL CLIENTE"};

        excelController.downloadXlsx(response, "Reporte Becas", query, columnas, totales, columnasMoneda, params, "Becas");
    }
}