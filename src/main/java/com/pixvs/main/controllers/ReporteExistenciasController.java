package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlmacenDao;
import com.pixvs.main.dao.ArticuloDao;
import com.pixvs.main.dao.InventarioMovimientoDao;
import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.InventarioMovimiento.ReporteExistenciasComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.RolMenuPermiso;
import com.pixvs.spring.models.projections.ControlMaestro.ControlMaestroProjectionDatosEmpresa;
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
@RequestMapping("/api/v1/existencias")
public class ReporteExistenciasController {

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
    public JsonResponse getExistencias(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
        datos.put("almacenes", almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));
        datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getExistencias(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        Usuario usuario = usuarioDao.findById(idUsuario);
        RolMenuPermiso permiso = rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), 1);

        if(permiso != null){
            return new JsonResponse(
                    inventarioMovimientoDao.fn_reporteExistencias(
                            filtros.get("fechaFin"),
                            filtros.get("articulos"),
                            filtros.get("localidades"),
                            filtros.get("ceros"),
                            idUsuario), null, JsonResponse.STATUS_OK);
        }
        else {
            return new JsonResponse(
                     inventarioMovimientoDao.fn_reporteExistenciasSinPermisos(
                            filtros.get("fechaFin"),
                            filtros.get("articulos"),
                            filtros.get("localidades"),
                            filtros.get("ceros"),
                            idUsuario), null, JsonResponse.STATUS_OK);
        }
    }

    @RequestMapping(value = "/download/excel", method = RequestMethod.POST)
    public void downloadXlsxExistencias(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        Usuario usuario = usuarioDao.findById(idUsuario);
        RolMenuPermiso permiso = rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), 1);

        Object fechaFin = filtros.get("fechaFin");

        Object articulos = filtros.get("articulos");
        Object localidades = filtros.get("localidades") == "" ? null : filtros.get("localidades");
        Boolean ceros = (Boolean) filtros.get("ceros");

        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse((String) filtros.get("fechaFin"));

        HashMap<String, Object> cabecera = new HashMap<>();
        cabecera.put("Fecha reporte", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        cabecera.put("Moneda", "Pesos Mexicanos");
        cabecera.put("Fecha existencia", new SimpleDateFormat("dd/MM/yyyy").format(fecha));

        List<HashMap<String, Object> > arts = (ArrayList)json.get("articulos");
        Object articulosCab = null;
        if(arts != null && arts.size() == 1){
            ArticuloComboProjection art = articuloDao.findProjectedComboById((Integer) arts.get(0).get("id"));
            articulosCab = art.getNombreArticulo();
        }else{
            articulosCab = filtros.get("articulos");
        }
        cabecera.put("Artículos", articulosCab );

        List<HashMap<String, Object> > locs = (ArrayList)json.get("localidades");
        Object localidadesCab = null;
        if(locs != null && locs.size() == 1){
            LocalidadComboProjection loc = localidadDao.findProjectedComboById( (Integer) locs.get(0).get("id") );
            localidadesCab = loc.getNombre();
        }else{
            localidadesCab = filtros.get("localidades");
        }
        cabecera.put("Localidades", localidadesCab);

        ControlMaestro nombreEmpresa = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        String consulta = "SELECT " +
                "codigo N'Código', isbn N'ISBN', editorial N'Editorial', nombre N'Nombre', um N'UM', almacen N'Almacén', " +
                "localidad N'Localidad', existencia N'Existencia almacén', transito N'En tránsito', totalExistencia N'Total', costo N'Costo', total N'Costo total' " +
                "FROM fn_reporteExistencias(" +
                (fechaFin != null ? "'" + fechaFin + "'" : "NULL") + ", " +
                (articulos != null ? "'" + articulos + "'" : "NULL") + ", " +
                (localidades != null ? "'" + localidades + "'" : "NULL") + ", " +
                (ceros? "1" : "0") + ", " +
                idUsuario +
                ") ORDER BY " +
                "CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN LEFT(nombre,PATINDEX('%[0-9]',nombre)-1) ELSE nombre END ,\n" +
                "    CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN\n" +
                "        CAST(SUBSTRING(nombre,PATINDEX('%[0-9]',nombre),LEN(nombre)) as float) ELSE NULL END" +
                ", almacen, localidad";

        String[] titulo = new String[]{nombreEmpresa.getValor()};

        String[] totales = new String[]{};

        if(permiso != null){
            String[] columnas = new String[]{ "Código", "ISBN", "Editorial", "Nombre", "UM", "Almacén", "Localidad", "Existencia almacén", "En tránsito", "Total", "Costo", "Costo total" };
            if(locs == null || locs.size() > 1){
                totales = new String[]{ "Existencia almacén", "En tránsito", "Total" , "Costo", "Costo total" };
            }

            excelController.downloadDetailedXlsx(response,
                    "existencias",
                    consulta,
                    titulo,
                    cabecera,
                    null,
                    columnas,
                    null,
                    "Existencias",
                    "Nombre",
                    null,
                    totales);
        }
        else {

            String[] columnas = new String[]{"Código", "ISBN", "Editorial", "Nombre", "UM", "Almacén", "Localidad", "Existencia almacén", "En tránsito", "Total"};
            if(locs == null || locs.size() > 1){
                totales = new String[]{"Existencia almacén", "En tránsito", "Total"};
            }

            excelController.downloadDetailedXlsx(response,
                    "existencias",
                    consulta,
                    titulo,
                    cabecera,
                    null,
                    columnas,
                    null,
                    "Existencias",
                    "Nombre",
                    null,
                    totales);
        }
    }

    public HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        Object fechaFin = json.get("fechaFin");

        ArrayList<HashMap<String, Integer>> allArticulos = (ArrayList<HashMap<String, Integer>>) json.get("articulos");
        String articulos = null;
        if (allArticulos != null) {
            articulos = "";
            for (HashMap<String, Integer> registro : allArticulos) {
                articulos += "|" + registro.get("id") + "|";
            }
        }

        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");
        String localidades = null;
        if (allLocalidades != null) {
            localidades = "";
            for (HashMap<String, Integer> registro : allLocalidades) {
                localidades += "|" + registro.get("id") + "|";
            }
        }

        Boolean ceros = json.get("ceros") != null ? (Boolean) json.get("ceros") : false;

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("fechaFin", fechaFin);
        filtros.put("articulos", articulos);
        filtros.put("localidades", localidades);
        filtros.put("ceros", ceros);

        return filtros;
    }

}
