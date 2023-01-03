package com.pixvs.main.controllers;


import com.pixvs.main.dao.*;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboFiltroProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inasistencias")
public class ReporteInasistenciasController {

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
    ExcelController excelController;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);
        List<ControlMaestroMultipleComboProjection> estatus = controlMaestroMultipleDao.findAllByControl("CMM_ALUG_Estatus");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        RolMenuPermiso permiso = rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_A_EXCEL);
        json.put("sucursales",sucursales);
        json.put("estatus", estatus);
        json.put("permiso", permiso != null? true : false);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        //Validate keys sended using json.containsKey function
        List<HashMap<String,Object>> sedes = json.containsKey("sede") ? (List<HashMap<String,Object>>) json.get("sede") : new ArrayList<>();
        List<HashMap<String,Object>> programas = json.containsKey("programa") ? (List<HashMap<String,Object>>) json.get("programa") : new ArrayList<>();
        List<HashMap<String,Object>> estatus = json.containsKey("estatus") ? (List<HashMap<String,Object>>) json.get("estatus") : new ArrayList<>();
        HashMap<String,Object> ciclos = json.containsKey("ciclos") ? (HashMap<String,Object>) json.get("ciclos") : null;
        HashMap<String,Object> pa = json.containsKey("pa") ? (HashMap<String,Object>) json.get("pa") : null;
        HashMap<String,Object> fechas = json.containsKey("fechas") ? (HashMap<String,Object>) json.get("fechas") : null;
        //Cast lists and HashMaps to primitive data types
        List<Integer> sedesId = new ArrayList<>();
        for (HashMap<String, Object> sede : sedes){ sedesId.add((Integer) sede.get("id")); }
        List<Integer> programasId = new ArrayList<>();
        for (HashMap<String, Object> programa : programas){ programasId.add((Integer) programa.get("id")); }
        List<Integer> estatusId = new ArrayList<>();
        if ( estatus != null)
            for (HashMap<String, Object> item : estatus){ estatusId.add((Integer) item.get("id")); }
        Integer cicloId = ciclos != null ? (Integer) ciclos.get("id") : null;
        Integer paId = pa != null ? (Integer) pa.get("id") : null;
        String fecha = fechas != null ? (String) fechas.get("fecha") : null;

        List<AlumnoGrupoListadoProjection> reporte = alumnoGrupoDao.getReporteInasistenciasResumen(
                sedesId.size() > 0 ? sedesId : null,
                cicloId,
                paId,
                programasId.size() > 0 ? programasId : null,
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
        HashMap<String, Object> res = new HashMap<>();
        //TODO: Improve this query to work with distinct over JPA
        List<ProgramacionAcademicaComercialComboFiltroProjection> progs = programacionAcademicaComercialDao.findProjectedComboAllBySucursalIdIn(sedesIds);
        List<ProgramacionAcademicaComercialComboFiltroProjection> programaciones = new ArrayList<>();
        for (ProgramacionAcademicaComercialComboFiltroProjection prog : progs){
            if(programaciones.stream().filter( item -> item.getId().equals(prog.getId())).collect(Collectors.toList()).size() == 0){
                programaciones.add(prog);
            }
        }
        List<PACicloComboProjection> ciclos = paCicloDao.findProjectedComboAllByActivoTrue();
        res.put("programaciones", programaciones);
        res.put("ciclos", ciclos);
        res.put("programas", programaDao.findProgramasBySedeIdIn(sedesIds));
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
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
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<HashMap<String,Object>> sedes = json.containsKey("sede") ? (List<HashMap<String,Object>>) json.get("sede") : new ArrayList<>();
        List<HashMap<String,Object>> programas = json.containsKey("programa") ? (List<HashMap<String,Object>>) json.get("programa") : new ArrayList<>();
        List<HashMap<String,Object>> estatus = json.containsKey("estatus") ? (List<HashMap<String,Object>>) json.get("estatus") : new ArrayList<>();
        HashMap<String,Object> ciclos = json.containsKey("ciclos") ? (HashMap<String,Object>) json.get("ciclos") : null;
        HashMap<String,Object> pa = json.containsKey("pa") ? (HashMap<String,Object>) json.get("pa") : null;
        HashMap<String,Object> fechas = json.containsKey("fechas") ? (HashMap<String,Object>) json.get("fechas") : null;
        //Cast lists and HashMaps to primitive data types
        List<Integer> sedesId = new ArrayList<>();
        for (HashMap<String, Object> sede : sedes){ sedesId.add((Integer) sede.get("id")); }
        List<Integer> programasId = new ArrayList<>();
        for (HashMap<String, Object> programa : programas){ programasId.add((Integer) programa.get("id")); }
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
        params.put("programasId", programasId.size() > 0 ? programasId : null);
        params.put("fecha", fecha);
        params.put("estatusId", estatusId.size() > 0 ? estatusId : null);
        //Describe query for excel report
        String query = "SELECT id, nombre, grados,	grupo,	turnos,	nivel, plantel, grupo_,	maestro, horario, escuela, " +
                "carrera, cohorte, regular, cartaCompromiso, codigo, estatus, faltas, asistencias " +
                "FROM [dbo].[VW_RPT_INASISTENCIAS] " +
                "WHERE sedeId IN (:sedesId) " +
                "AND (:cicloId IS NULL OR cicloId = :cicloId) " +
                "AND (:paId IS NULL OR paId = :paId) " +
                "AND programaId IN (:programasId) " +
                "AND FORMAT(fechaInicio,'dd/MM/yyyy') = :fecha " +
                "AND (:estatusId IS NULL OR estatusId IN (:estatusId))";
        //Add column names
        String[] alColumnas = new String[]{"Código UDG","Nombre","Grado","Grupo","Turno","Nivel","Plantel","Grupo",
                "Maestro","Horario","Escuela","Carrera","Cohorte","¿Alumno regular?","¿Entregó carta compromiso?",
                "Código","Estatus","Faltas","Asistencias"};
        excelController.downloadXlsx(response, "Reporte inasistencias", query, alColumnas, params, "Detalles");
    }
}
