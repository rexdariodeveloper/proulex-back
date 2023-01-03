package com.pixvs.main.controllers;


import com.pixvs.main.dao.*;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.Alumno.AlumnoPagoProjection;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboFiltroProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.RolMenuPermiso;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pagos-alumnos")
public class ReportePagosAlumnosController {

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
    AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    AlumnoDao alumnoDao;
    @Autowired
    ExcelController excelController;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();
        Integer[] validos = new Integer[]{2000510,2000511};
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("estatus", controlMaestroMultipleDao.findAllByIdInOrderByValor(validos));
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        //Validate keys sended using json.containsKey function
        List<HashMap<String,Object>> sedes = json.containsKey("sede") ? (List<HashMap<String,Object>>) json.get("sede") : new ArrayList<>();
        List<HashMap<String,Object>> estatus = json.containsKey("estatus") ? (List<HashMap<String,Object>>) json.get("estatus") : new ArrayList<>();
        HashMap<String,Object> ciclos = json.containsKey("ciclos") ? (HashMap<String,Object>) json.get("ciclos") : null;
        HashMap<String,Object> pa = json.containsKey("pa") ? (HashMap<String,Object>) json.get("pa") : null;
        HashMap<String,Object> fechas = json.containsKey("fechas") ? (HashMap<String,Object>) json.get("fechas") : null;
        //Cast lists and HashMaps to primitive data types
        List<Integer> sedesId = new ArrayList<>();
        for (HashMap<String, Object> sede : sedes){ sedesId.add((Integer) sede.get("id")); }
        List<Integer> estatusId = new ArrayList<>();
        if ( estatus != null)
            for (HashMap<String, Object> item : estatus){ estatusId.add((Integer) item.get("id")); }
        Integer cicloId = ciclos != null ? (Integer) ciclos.get("id") : null;
        Integer paId = pa != null ? (Integer) pa.get("id") : null;
        String fecha = fechas != null ? (String) fechas.get("fecha") : null;

        List<AlumnoPagoProjection> reporte = alumnoDao.getReporteAlumnosPagos(
                sedesId.size() > 0 ? sedesId : null,
                cicloId,
                paId,
                fecha,
                estatusId.size() > 0 ? estatusId : null
        );

        return new JsonResponse(reporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getCiclos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCiclosBySede(@RequestBody JSONObject json) throws SQLException {
        List<HashMap<String, Object>> sedes = (List<HashMap<String, Object>>) json.get("sedes");
        List<Integer> sedesIds = new ArrayList<>();
        for (HashMap<String, Object> sede : sedes){ sedesIds.add((Integer) sede.get("id")); }
        List<Integer> estatus = new ArrayList<>();
        estatus.add(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
        estatus.add(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO);

        JSONObject response = new JSONObject();
        response.put("anios", programaGrupoDao.findAniosBySedeInAndEstatusIn(sedesIds, estatus));
        return new JsonResponse(response, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) throws SQLException {
        HashMap<String, Object> res = new HashMap<>();
        if (json.containsKey("pa")){
            Integer paId = (Integer) ((HashMap<String, Object>) json.get("pa")).get("id");
            List<String> fechas = programaGrupoDao.findDistinctFechaInicioByProgramacionAcademicaComercialIdOrPaCicloId(paId, 0);
            res.put("fechas", fechas);
        }
        if (json.containsKey("ciclo")){
            Integer cicloId = (Integer) ((HashMap<String, Object>) json.get("ciclo")).get("id");
            List<String> fechas = programaGrupoDao.findDistinctFechaInicioByProgramacionAcademicaComercialIdOrPaCicloId(0, cicloId);
            res.put("fechas", fechas);
        }
        if (json.containsKey("anio")){
            Integer anio = (Integer) ((HashMap<String, Object>) json.get("anio")).get("id");
            List<String> fechas = programaGrupoDao.findDistinctFechaInicioByAnio(anio.toString());
            res.put("fechas", fechas);
        }
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<HashMap<String,Object>> sedes = json.containsKey("sede") ? (List<HashMap<String,Object>>) json.get("sede") : new ArrayList<>();
        List<HashMap<String,Object>> estatus = json.containsKey("estatus") ? (List<HashMap<String,Object>>) json.get("estatus") : new ArrayList<>();
        HashMap<String,Object> ciclos = json.containsKey("ciclos") ? (HashMap<String,Object>) json.get("ciclos") : null;
        HashMap<String,Object> pa = json.containsKey("pa") ? (HashMap<String,Object>) json.get("pa") : null;
        HashMap<String,Object> fechas = json.containsKey("fechas") ? (HashMap<String,Object>) json.get("fechas") : null;
        //Cast lists and HashMaps to primitive data types
        List<Integer> sedesId = new ArrayList<>();
        for (HashMap<String, Object> sede : sedes){ sedesId.add((Integer) sede.get("id")); }
        List<Integer> estatusId = new ArrayList<>();
        if ( estatus != null){ for (HashMap<String, Object> item : estatus){ estatusId.add((Integer) item.get("id")); } }
        Integer cicloId = ciclos != null ? (Integer) ciclos.get("id") : null;
        Integer paId = pa != null ? (Integer) pa.get("id") : null;
        String fecha = fechas != null ? (String) fechas.get("fecha") : null;
        //Set params for query
        HashMap<String, Object> params = new HashMap<>();
        params.put("sedesId", sedesId.size() > 0 ? sedesId : null);
        params.put("cicloId", cicloId);
        params.put("paId", paId);
        params.put("fecha", fecha);
        params.put("estatusId", estatusId.size() > 0 ? estatusId : null);
        //Describe query for excel report
        String query = "SELECT codigo, primerApellido, segundoApellido, nombre, plantel, clave, grupo, horario, " +
                "sede, escuela, referencia, poliza, FORMAT(precio,'$00.00') precio, estatus " +
                "FROM [dbo].[VW_RPT_ALUMNOS_PAGOS] " +
                "WHERE sedeId IN (:sedesId) " +
                "AND (:cicloId IS NULL OR cicloId = :cicloId) " +
                "AND (:paId IS NULL OR paId = :paId) " +
                "AND FORMAT(fechaInicio,'dd/MM/yyyy') = :fecha " +
                "AND (COALESCE(:estatusId, 0) = 0 OR estatusId IN (:estatusId)) " +
                "ORDER BY primerApellido, segundoApellido, nombre";
        //Add column names
        String[] alColumnas = new String[]{"Código UDG", "Primer apellido", "Segundo apellido", "Nombre", "Plantel",
                "Clave curso", "Grupo", "Horario", "Sede", "Escuela", "Ref. Bancaria ", "Poliza o nota", "Total", "Estatus"};
        excelController.downloadXlsx(response, "Reporte alumnos pagos", query, alColumnas, params, "Reporte");
    }
}
