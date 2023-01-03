package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Inscripcion.ReporteInscripcionesProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class ReporteInscripcionesController {

    @Autowired
    SucursalDao sucursalDao;

    @Autowired
    ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;

    @Autowired
    PACicloDao paCicloDao;

    @Autowired
    ProgramaDao programaDao;

    @Autowired
    ProgramaGrupoDao programaGrupoDao;

    @Autowired
    AlumnoDao alumnoDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @Autowired
    InscripcionDao inscripcionDao;

    @Autowired
    private HashId hashId;

    @Autowired
    ExcelController excelController;

    @Autowired
    private PuntoVentaController puntoVentaController;

    @Autowired
    private CapturaCalificacionesController capturaCalificacionesController;

    @Autowired
    private KardexAlumnoController kardexAlumnoController;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer[] estatusIds = new Integer[]{
                ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA,
                ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO,
                ControlesMaestrosMultiples.CMM_INS_Estatus.BAJA
        };

        HashMap<String, Object> json = new HashMap<>();

        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("estatus", controlMaestroMultipleDao.findAllByIdInOrderByValor(estatusIds));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        String notaVenta = (String) json.get("notaVenta");
        String alumno = (String) json.get("alumno");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> estatusId = Utilidades.getListItems(json.get("estatus"), "id");

        List<ReporteInscripcionesProjection> reporte =
                inscripcionDao.fn_reporteInscripciones(
                        sedesId,
                        fechas,
                        !StringCheck.isNullorEmpty(notaVenta) ? notaVenta : null,
                        !StringCheck.isNullorEmpty(alumno) ? alumno : null,
                        modalidadesId,
                        estatusId
                );

        return new JsonResponse(reporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/imprimir/archivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public void imprimirArchivos(@RequestBody HashMap<String, Object> body, HttpServletResponse response) throws Exception {
        int inscripcionId = hashId.decode((String) body.get("inscripcionId"));
        int archivoId = (Integer) body.get("archivoId");

        ReporteInscripcionesProjection inscripcion = inscripcionDao.findInscripcionReporteProjectedById(inscripcionId);

        switch (archivoId) {
            case 1:
                HashMap<String, Object> filtros = new HashMap<>();
                filtros.put("ordenesVentaIdsStr", String.valueOf(inscripcion.getNotaVentaId()));
                filtros.put("sucursalId", inscripcion.getSedeInscripcionId());

                puntoVentaController.descargarPdf(filtros, response);
                break;

            case 2:
                JSONObject json = new JSONObject();
                json.put("alumnoId", inscripcion.getAlumnoId());
                json.put("grupoId", inscripcion.getGrupoId());

                capturaCalificacionesController.downloadBoleta(json, response);
                break;

            case 3:
                kardexAlumnoController.descargarKardex(alumnoDao.findById(inscripcion.getAlumnoId()), false, response);
                break;
        }
    }

    @RequestMapping(value = "/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        String notaVenta = (String) json.get("notaVenta");
        String alumno = (String) json.get("alumno");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> estatusId = Utilidades.getListItems(json.get("estatus"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("fechas", fechas);
        params.put("notaVenta", !StringCheck.isNullorEmpty(notaVenta) ? notaVenta : null);
        params.put("alumno", !StringCheck.isNullorEmpty(alumno) ? alumno : null);
        params.put("modalidadesId", modalidadesId);
        params.put("estatusId", estatusId);

        String query =
                "SELECT sedeInscripcion,\n" +
                        "       inscripcion,\n" +
                        "       CONVERT(DATE, fechaInscripcion) AS fechaInscripcion,\n" +
                        "       notaVenta,\n" +
                        "       estatusNotaVenta,\n" +
                        "       CASE WHEN sedeInscripcionId = sedeGrupoId THEN 'LOCAL' ELSE CASE WHEN sedeInscripcionId IN(:sedesId) THEN 'MULTISEDE ENVÍA' ELSE 'MULTISEDE RECIBE' END END AS tipoInscripcion,\n" +
                        "       primerApellido,\n" +
                        "       segundoApellido,\n" +
                        "       nombre,\n" +
                        "       sedeGrupo,\n" +
                        "       grupo,\n" +
                        "       tipoGrupo,\n" +
                        "       curso,\n" +
                        "       modalidad,\n" +
                        "       nivel,\n" +
                        "       idioma,\n" +
                        "       horario,\n" +
                        "       fechaInicio,\n" +
                        "       fechaFin,\n" +
                        "       porcentajeDescuento,\n" +
                        "       subtotal,\n" +
                        "       descuento,\n" +
                        "       impuestos,\n" +
                        "       total,\n" +
                        "       entidadBeca,\n" +
                        "       codigoBeca,\n" +
                        "       porcentajeBeca,\n" +
                        "       estatusInscripcion\n" +
                        "FROM VW_RPT_INSCRIPCIONES\n" +
                        "WHERE (COALESCE(:sedesId, 0) = 0 OR (sedeInscripcionId IN(:sedesId) OR sedeGrupoId IN(:sedesId)))\n" +
                        "       AND (COALESCE(:fechas, '') = '' OR FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas))\n" +
                        "       AND CASE WHEN :notaVenta IS NULL THEN 1 ELSE CASE WHEN notaVenta = :notaVenta THEN 1 ELSE 0 END END = 1\n" +
                        "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas(alumno, :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
                        "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                        "       AND (COALESCE(:estatusId, 0) = 0 OR estatusId IN (:estatusId))\n" +
                        "ORDER BY inscripcion\n" +
                        "OPTION(RECOMPILE)";

        String[] columnas = new String[]{
                "SEDE INSCRIPCIÓN", "INSCRIPCIÓN", "FECHA DE INSCRIPCION", "NOTA DE VENTA", "ESTATUS NOTA DE VENTA",
                "TIPO INSCRIPCIÓN", "PRIMER APELLIDO", "SEGUNDO APELLIDO", "NOMBRE", "SEDE GRUPO", "CÓDIGO DE GRUPO",
                "TIPO DE GRUPO", "CURSO", "MODALIDAD", "NIVEL", "IDIOMA", "HORARIO", "FECHA INICIO", "FECHA FIN",
                "PORCENTAJE DESCUENTO", "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL", "ENTIDAD BECA", "CÓDIGO BECA",
                "PORCENTAJE BECA", "ESTATUS DE LA INSCRIPCIÓN"
        };

        String[] totales = new String[]{ "TOTAL" };
        String[] columnasMoneda = new String[]{ "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL" };

        excelController.downloadXlsx(response, "Reporte de Inscripciones", query, columnas, totales, columnasMoneda, params, "Reporte");
    }
}