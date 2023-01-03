package com.pixvs.main.controllers;

import com.pixvs.main.dao.InscripcionSinGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.CXCFactura.ReporteFacturasProjection;
import com.pixvs.main.models.projections.InscripcionSinGrupo.InscripcionSinGrupoListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Utilidades;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rene Carrillo on 22/09/2022.
 */
@RestController
@RequestMapping("/api/v1/reporte-alumnos-sin-grupo")
public class ReporteAlumnosSinGrupoController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private InscripcionSinGrupoDao inscripcionSinGrupoDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @Autowired
    ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        HashMap<String, Object> json = new HashMap<>();
        HashMap<String, Boolean> listaPermiso = new HashMap<>();

        json.put("listaSede", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_ALUMNOS_SIN_GRUPO) != null)
            listaPermiso.put("EXPORTAR_EXCEL_ALUMNOS_SIN_GRUPO", Boolean.TRUE);
        json.put("listaPermiso", listaPermiso);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        List<Integer> listaSedeId = Utilidades.getListItems(json.get("listaSede"), "id");

        List<Integer> listaEstatusId = Arrays.asList(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO,ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);

        List<InscripcionSinGrupoListadoProjection> listaReporte = inscripcionSinGrupoDao.findProjectedListadoAllBySucursalIdAndEstatusIdIn(listaSedeId,listaEstatusId,"",0,null);

        return new JsonResponse(listaReporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> listaSedeId = Utilidades.getListItems(json.get("listaSede"), "id");

        List<Integer> listaEstatusId = Arrays.asList(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO,ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);

        HashMap<String, Object> params = new HashMap<>();

        params.put("listaSedeId", listaSedeId);
        params.put("listaEstatusId", listaEstatusId);

        String query =
                "SELECT sede, \n" +
                        "alumnoCodigo, \n" +
                        "alumnoCodigoUDG, \n" +
                        "alumnoPrimerApellido, \n" +
                        "alumnoSegundoApellido, \n" +
                        "alumnoNombre, \n" +
                        "curso, \n" +
                        "nivel, \n" +
                        "paModalidadNombre, \n" +
                        "FORMAT(montoPago,'C'), \n" +
                        "medioPago, \n" +
                        "ovCodigo, \n" +
                        "estatus \n" +
                        "FROM VW_ListadoProjection_InscripcionesSinGrupo \n" +
                        "WHERE sucursalId IN :listaSedeId \n" +
                        "       AND estatusId IN :listaEstatusId \n" +
                        "       AND CONCAT(COALESCE(alumnoCodigo,''),'|',COALESCE(alumnoCodigoUDG,''),'|',alumnoNombre,' ',alumnoPrimerApellido,' ' + alumnoSegundoApellido,' ',alumnoNombre,'|',curso,'|',paModalidadNombre,'|',medioPago,'|',ovCodigo ,'|',estatus) LIKE CONCAT('%','','%') \n" +
                        "ORDER BY id \n" +
                        "OPTION(RECOMPILE)";

        String[] allColumnas = new String[]{
                "SEDE", "CÓDIGO ALUMNO", "CÓDIGO UDG", "PRIMER APELLIDO", "SEGUNDO APELLIDO", "NOMBRE", "CURSO", "NIVEL", "MODALIDAD", "MONTO PAGO", "MEDIO DE PAGO", "NOTA DE VENTA", "ESTATUS"
        };

        excelController.downloadXlsxSXSSF(response, "Reporte de Alumnos Sin Grupo", query, allColumnas, params, "Reporte");
    }
}
