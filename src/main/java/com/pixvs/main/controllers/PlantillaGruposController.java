package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.Alumno.AlumnoOrdenPagoProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionListadoGrupoProjection;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoListadoProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoPlantillaGruposListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboPlantelFiltrosProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboPlantelProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import com.pixvs.spring.util.HashId;
import org.springframework.web.multipart.MultipartFile;

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * Created by Rene Carrillo on 25/07/2022.
 */
@RestController
@RequestMapping("/api/v1/plantilla-grupos")
public class PlantillaGruposController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private PAModalidadDao modalidadDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;

    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private ListadoPrecioDetalleDao listadoPrecioDetalleDao;

    @Autowired
    private InscripcionDao inscripcionDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private AlumnoDao alumnoDao;

    @Autowired
    private TabuladorDetalleDao tabuladorDetalleDao;

    @Autowired
    private ProgramaGrupoProfesorDao programaGrupoProfesorDao;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    @Autowired
    private HashId hashId;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        List<SucursalComboProjection> listaSede = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);
//        Integer esTodasSedes = 1;
//        List<Integer> listaSedePermiso = new ArrayList<>();
//
//        if (listaSede.size() > 0) {
//            esTodasSedes = 0;
//
//            for (SucursalComboProjection sucursal : listaSede) {
//                listaSedePermiso.add(sucursal.getId());
//            }
//        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("listaSede", listaSede);
        json.put("listaAnio", programaGrupoDao.findAniosFechaInicio());
        //json.put("listaCurso", programaIdiomaDao.findAllByActivoIsTrueOrderByCodigo());

        HashMap<String, Boolean> permisos = new HashMap<>();

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_PLANTILLA_ALUMNOS_SEMS) != null)
            permisos.put("DESCARGAR_PLANTILLA_ALUMNOS_SEMS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_PLANTILLA_PROFESORES_SEMS) != null)
            permisos.put("DESCARGAR_PLANTILLA_PROFESORES_SEMS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_LISTADO_ALUMNOS_SEMS) != null)
            permisos.put("DESCARGAR_LISTADO_ALUMNOS_SEMS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_IMPORTAR_PLANTILLA_ALUMNOS_SEMS) != null)
            permisos.put("IMPORTAR_PLANTILLA_ALUMNOS_SEMS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_IMPORTAR_PLANTILLA_PROFESORES_SEMS) != null)
            permisos.put("IMPORTAR_PLANTILLA_PROFESORES_SEMS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_PLANTILLA_ALUMNOS_JOBS) != null)
            permisos.put("DESCARGAR_PLANTILLA_ALUMNOS_JOBS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_PLANTILLA_PROFESORES_JOBS) != null)
            permisos.put("DESCARGAR_PLANTILLA_PROFESORES_JOBS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_LISTADO_ALUMNOS_JOBS) != null)
            permisos.put("DESCARGAR_LISTADO_ALUMNOS_JOBS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_IMPORTAR_PLANTILLA_ALUMNOS_JOBS) != null)
            permisos.put("IMPORTAR_PLANTILLA_ALUMNOS_JOBS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_IMPORTAR_PLANTILLA_PROFESORES_JOBS) != null)
            permisos.put("IMPORTAR_PLANTILLA_PROFESORES_JOBS", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_DESCARGAR_PLANTILLA_ALUMNOS_PCP) != null)
            permisos.put("DESCARGAR_PLANTILLA_ALUMNOS_PCP", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_IMPORTAR_PLANTILLA_ALUMNOS_PCP) != null)
            permisos.put("IMPORTAR_PLANTILLA_ALUMNOS_PCP", Boolean.TRUE);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.PG_EXPORTAR_EXCEL_GRUPOS) != null)
            permisos.put("EXPORTAR_EXCEL_GRUPOS", Boolean.TRUE);

        json.put("permisos", permisos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = (Integer) sede.get("id");
        List<Integer> listaPlantelId = Utilidades.getListItems(json.get("listaPlantel"), "id");
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");
        List<String> listaFecha = Utilidades.getListItems(json.get("listaFecha"), "fecha");

        List<ProgramaGrupoPlantillaGruposListadoProjection> listaPlantillaGrupo = new ArrayList<>();

        try{
            listaPlantillaGrupo = programaGrupoDao.vw_listado_plantillaGrupos(
                    sedeId,
                    listaPlantelId,
                    listaCursoId,
                    listaModalidadId,
                    listaFecha
            );
        }catch (Exception ex){
            return new JsonResponse("", "Algo está mal con los datos, por favor verificar los datos.", JsonResponse.STATUS_ERROR);
        }


        return new JsonResponse(listaPlantillaGrupo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getListaPlantel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaPlantel(@RequestBody Integer sedeId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> _json = new HashMap<>();

        _json.put("listaPlantel", sucursalPlantelDao.findAllByPermisosUsuarioAndSucursalId(usuarioId, sedeId));

        return new JsonResponse(_json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getListaModalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaModalidadWithCurso(@RequestBody JSONObject json) {
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");

        List<Integer> listaModalidadId = programaGrupoDao.buscaModalidadConCurso(listaCursoId);

        HashMap<String, Object> _json = new HashMap<>();

        _json.put("listaModalidad", modalidadDao.findComboAllByActivoTrueAndIdInOrderByNombre(listaModalidadId));

        return new JsonResponse(_json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getListaFecha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaFechaByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");

        HashMap<String, Object> _json = new HashMap<>();

        _json.put("listaFecha", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, listaModalidadId));

        return new JsonResponse(_json, null, JsonResponse.STATUS_OK);
    }

    /** Template **/
    @PostMapping("/template/download/{tipo}")
    public void templateDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo, @RequestBody JSONObject json) throws IOException, NoSuchFieldException, IllegalAccessException, Exception {
        Usuario usuario = usuarioDao.findById(JwtFilter.getUsuarioId((HttpServletRequest) req));

        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = (Integer) sede.get("id");
        List<Integer> listaPlantelId = Utilidades.getListItems(json.get("listaPlantel"), "id");
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");
        List<String> listaFecha = Utilidades.getListItems(json.get("listaFecha"), "fecha");

        List<Integer> listaSedeId = new ArrayList<>();
        for (Almacen almacen : usuario.getAlmacenes()){ if(almacen.getSucursalId() == sedeId) listaSedeId.add(almacen.getSucursalId()); }

        //Create worbook
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //Create styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        //Create main sheet
        SXSSFSheet sheet = workbook.createSheet(tipo);
        String[] ht = null;
        if (tipo.equals("JOBS") || tipo.equals("SEMS")){
            ht = new String[]{"ID","PRECIO","CODIGO_GRUPO","CICLO_ESCOLAR","PLANTEL","NIVEL","GRUPO","MODALIDAD","HORARIO",
                    "CODIGO_ALUMNO","PRIMER_APELLIDO","SEGUNDO_APELLIDO","NOMBRE","TELEFONO_CASA","TELEFONO_CELULAR",
                    "CORREO_ELECTRÓNICO","FECHA_NACIMIENTO","INSTITUCION_EDUCATIVA","CARRERA","GENERO","TURNO","GRADO","GRUPO"};
        }
        if (tipo.equals("PCP")){
            ht = new String[]{"ID","PRECIO","CODIGO_GRUPO","CICLO_ESCOLAR","PLANTEL","NIVEL","GRUPO","MODALIDAD","HORARIO",
                    "FOLIO","PRIMER_APELLIDO","SEGUNDO_APELLIDO","NOMBRE","TELEFONO_CASA","TELEFONO_CELULAR",
                    "CORREO_ELECTRÓNICO","FECHA_NACIMIENTO","GENERO","DEPENDENCIA","PRIMER_APELLIDO","SEGUNDO_APELLIDO",
                    "NOMBRE","PARENTESCO","TELEFONO_CASA","TELEFONO_CELULAR","CORREO_ELECTRÓNICO"};
        }
        //Create header row
        createRow(sheet, 0, ht, header);
        // Width las columnas
        int[] listaWidthColumna = new int[ht.length];
        for (int i = 0; i < ht.length; i++) {
            // Obtener el valor cuantas letras tiene.
            int wColumna = listaWidthColumna[i];
            // Si el valor (letras) es mayor que el valor anterior o posterior
            if(ht[i].length() > wColumna)
                listaWidthColumna[i] = ht[i].length();
        }
        //Query general data
        List<ProgramaGrupoListadoProjection> grupos = new ArrayList<>();
        if(tipo.equals("JOBS"))
            grupos.addAll(programaGrupoDao.findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobs(listaSedeId, listaPlantelId, listaCursoId, listaModalidadId, listaFecha));
        else if(tipo.equals("SEMS"))
            grupos.addAll(programaGrupoDao.findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobsSems(listaSedeId, listaPlantelId, listaCursoId, listaModalidadId, listaFecha));
        else if(tipo.equals("PCP"))
            grupos.addAll(programaGrupoDao.findProgramaGrupoListadoProjectionAllBySucursalInAndEsPCP(listaSedeId, listaPlantelId, listaCursoId, listaModalidadId, listaFecha));

        Moneda moneda = monedaDao.findByPredeterminadaTrue();
        //Sets initial row as 1 to avoid header
        Integer index = 1;
        for (ProgramaGrupoListadoProjection grupo : grupos){
            ProgramaGrupo programaGrupo = programaGrupoDao.findById(grupo.getId());
            List<InscripcionListadoGrupoProjection> inscripciones = inscripcionDao.findListadoGrupoByGrupoIdAndEstatusIdIsNot(grupo.getId(), ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
            Sucursal sucursal = programaGrupo.getSucursal();
            SucursalPlantel plantel = programaGrupo.getSucursalPlantel();
            ArticuloEditarProjection articulo = articuloDao.findProjectedByProgramaIdiomaIdAndPaModalidadIdAndTipoGrupoId(programaGrupo.getProgramaIdiomaId(), programaGrupo.getPaModalidadId(),programaGrupo.getTipoGrupoId());
            if (articulo != null){
                if (sucursal.getListadoPrecio() == null){
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print("La sede "+sucursal.getCodigoSucursal()+" no cuenta con lista de precios asociada.");
                    out.flush();
                    return;
                }
                ListadoPrecioDetalleEditarProjection precio = listadoPrecioDetalleDao.findProjectedByArticuloIdAndListadoPrecioId(articulo.getId(), sucursal.getListadoPrecio().getId());
                if (precio == null){
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.print("El articulo "+articulo.getCodigoArticulo()+" no cuenta con precio en "+ sucursal.getListadoPrecio().getCodigo());
                    out.flush();
                    return;
                }
                String[] ids = {
                        hashId.encode(programaGrupo.getId()),//grupoId
                        hashId.encode(programaGrupo.getSucursalId()),//sucursalId
                        hashId.encode(moneda.getId()),//monedaId
                        hashId.encode(precio.getListadoPrecioId()),//Lista de precios
                        hashId.encode(articulo.getId()),//articuloId
                        hashId.encode(articulo.getUnidadMedidaInventarioId())};//Unidad de medida
                String compositeId = String.join("-",ids);

                for (Integer i = inscripciones.size(); i < programaGrupo.getCupo(); i++){
                    SXSSFRow row = sheet.createRow(index);
                    createCell(row,0, compositeId, detail);
                    createCell(row,1 ,precio.getPrecio().toString(), detail);//Precio
                    createCell(row,2 ,grupo.getCodigo(), detail);//Código
                    createCell(row,3 ,grupo.getProgramacion(), detail);//Ciclo escolar
                    createCell(row,4 ,plantel != null? plantel.getNombre() : "", detail);//Plantel
                    createCell(row,5 ,grupo.getNivel(),detail);//Nivel
                    createCell(row,6 ,grupo.getNombreGrupo(), detail);//Grupo
                    createCell(row,7 ,grupo.getModalidad(), detail);//Modalidad
                    createCell(row,8 ,grupo.getHorario(), detail);//Horario

                    // Obtener el valor cuantas letras tiene.
                    // Si el valor (letras) es mayor que el valor anterior o posterior
                    int wColumna = listaWidthColumna[0];
                    if(compositeId.length() > wColumna)
                        listaWidthColumna[0] = compositeId.length();

                    wColumna = listaWidthColumna[1];
                    if(precio.getPrecio().toString().length() > wColumna)
                        listaWidthColumna[1] = precio.getPrecio().toString().length();

                    wColumna = listaWidthColumna[2];
                    if(grupo.getCodigo().length() > wColumna)
                        listaWidthColumna[2] = grupo.getCodigo().length();

                    wColumna = listaWidthColumna[3];
                    if(grupo.getProgramacion().length() > wColumna)
                        listaWidthColumna[3] = grupo.getProgramacion().length();

                    wColumna = listaWidthColumna[4];
                    int countPlantel = plantel != null ? plantel.getNombre().length() : 0;
                    if(countPlantel > wColumna)
                        listaWidthColumna[4] = countPlantel;

                    wColumna = listaWidthColumna[5];
                    if(grupo.getNivel().length() > wColumna)
                        listaWidthColumna[5] = grupo.getNivel().length();

                    wColumna = listaWidthColumna[6];
                    if(grupo.getNombreGrupo().length() > wColumna)
                        listaWidthColumna[6] = grupo.getNombreGrupo().length();

                    wColumna = listaWidthColumna[7];
                    if(grupo.getModalidad().length() > wColumna)
                        listaWidthColumna[7] = grupo.getModalidad().length();

                    wColumna = listaWidthColumna[8];
                    if(grupo.getHorario().length() > wColumna)
                        listaWidthColumna[8] = grupo.getHorario().length();

                    index++;
                }
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("El grupo "+grupo.getCodigo()+" no cuenta con un articulo asociado.");
                out.flush();
                return;
            }
        }

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );

        //Create hidden sheet for lists
        SXSSFSheet hidden = workbook.createSheet("hidden");
        List<List<String>> listados = new ArrayList<>();
        if (tipo.equals("JOBS") || tipo.equals("SEMS")){
            List<String> centros = new ArrayList<>();
            List<String> carreras = new ArrayList<>();
            if (tipo.equals("JOBS")){
                centros.addAll(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_CentrosUniversitarios.NOMBRE, true));
                //carreras.addAll(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE, false));
                List<ControlMaestroMultipleComboProjection> carr = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE);
                for(ControlMaestroMultipleComboProjection c : carr){
                    ControlMaestroMultipleComboProjection ref = c.getCmmReferencia();
                    if(ref != null)
                        carreras.add(c.getValor() + " - " + ref.getReferencia());
                }
            } else if(tipo.equals("SEMS"))
                centros.addAll(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_Preparatorias.NOMBRE, false));
            listados.add(centros);//Centros
            listados.add(carreras);//Carreras
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE, false));//Generos
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_Turnos.NOMBRE, false));//Turnos
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_Grados.NOMBRE, false));//Grados
        }
        if (tipo.equals("PCP")){
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE, false));//Generos
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_CE_Parentesco.NOMBRE, false));//Parentescos
        }

        Integer max = Collections.max(listados.stream().map(item -> item.size()).collect(Collectors.toList()));

        for (Integer j = 0; j < max; j++){//Rows
            SXSSFRow row = hidden.createRow(j);
            for (Integer i = 0; i < listados.size(); i++){//Columns
                if ( j < listados.get(i).size()) { createCell(row, i, listados.get(i).get(j)); }
            }
        }

        //Add Datavalidations to simulate dropdowns
        if(tipo.equals("JOBS") || tipo.equals("SEMS")){
            for (Integer i = 0; i < listados.size(); i++){
                if (listados.get(i).size() > 0){
                    XSSFDataValidation dropdown = createDropdown(workbook,new Integer[] {i,listados.get(i).size()},17 + i);
                    sheet.addValidationData(dropdown);
                }
            }
        }
        if (tipo.equals("PCP")){
            Integer i = 0;
            for (Integer col : new Integer[]{17,22}){
                if (listados.get(i).size() > 0){

                    XSSFDataValidation dropdown = createDropdown(workbook,new Integer[] {i,listados.get(i).size()},col);
                    sheet.addValidationData(dropdown);
                }
                i++;
            }
        }

        for (Integer i = 0; i < ht.length; i++){
            int width = 0;

            if (ht[i] == "CARRERA") {
                width = 6000;
            }else{
                if(listaWidthColumna[i] > 9)
                    width = ((int)(listaWidthColumna[i] * 1.14388)) * 256;
                else
                    width = ((int)(listaWidthColumna[i] * 1.54388)) * 256;
            }

            sheet.setColumnWidth(i, width);
        }

        workbook.setSheetHidden(1, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        String fecha = new SimpleDateFormat("yyyy-MM-dd HHmm").format(new Date());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+tipo+" "+fecha+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @RequestMapping(value = "template/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse templateUpload(@RequestParam("file") MultipartFile file, ServletRequest req, HttpServletResponse response) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        InputStream stream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        HashMap<String, List<ControlMaestroMultipleComboProjection>> listados = new HashMap<>();
        String tipo = sheet.getSheetName();

        if (tipo.equals("SEMS")){
            listados.put("prepas"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Preparatorias.NOMBRE));
            listados.put("generos" ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE));
            listados.put("turnos"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Turnos.NOMBRE));
            listados.put("grados"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Grados.NOMBRE));
        }
        else if (tipo.equals("JOBS")){
            listados.put("centros" ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_CentrosUniversitarios.NOMBRE));
            listados.put("carreras",controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE));
            listados.put("generos" ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE));
            listados.put("turnos"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Turnos.NOMBRE));
            listados.put("grados"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Grados.NOMBRE));
        }
        else if (tipo.equals("PCP") || tipo.equals("dGhlY2FrZWlzYWxpZQ==")){
            listados.put("generos" ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE));
            listados.put("parentescos", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_Parentesco.NOMBRE));
        }
        else
            return new JsonResponse(null, "Nombre de hoja no listado", JsonResponse.STATUS_ERROR);

        Iterator rows = sheet.iterator();
        rows.next();

        List<Row> batch = new ArrayList<>();
        JSONArray feedback = new JSONArray();
        long start = System.currentTimeMillis();
        String previous = null;
        String current = null;
        while(rows.hasNext()){
            Row row = (Row) rows.next();

            String temp = getCellValue(row, 0, false);
            String userInput = null;
            if (tipo.equals("JOBS") || tipo.equals("SEMS") || tipo.equals("dGhlY2FrZWlzYWxpZQ=="))
                userInput = getCellValue(row, 9, false);
            if (tipo.equals("PCP"))
                userInput = getCellValue(row, 10, false);
            //Is a valid row?
            if (temp != null && userInput != null) {
                current = temp;
                if (previous != null) {
                    //If prev id and current id are equals, add to same batch
                    if (current.equals(previous))
                        batch.add(row);
                    else {
                        feedback.addAll(processBatch(batch, listados, usuarioId, tipo));
                        batch.clear();
                        batch.add(row);
                    }
                } else
                    batch.add(row);
                previous = current;
            }
        }
        if (batch.size() > 0)
            feedback.addAll(processBatch(batch, listados, usuarioId, tipo));

        long end = System.currentTimeMillis();
        getDuration(start,end);

        if(feedback.size() > 0)
            return new JsonResponse(feedback, "Plantilla procesada con errores", JsonResponse.STATUS_OK);
        else
            return new JsonResponse(null, "Plantilla procesada exitosamente", JsonResponse.STATUS_OK);
    }

    @PostMapping("/profesores/download/{tipo}")
    public void templateProfesoresDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo, @RequestBody JSONObject json) throws IOException, IllegalAccessException, NoSuchFieldException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = (Integer) sede.get("id");
        List<Integer> listaPlantelId = Utilidades.getListItems(json.get("listaPlantel"), "id");
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");
        List<String> listaFecha = Utilidades.getListItems(json.get("listaFecha"), "fecha");

        List<Integer> listaSede = new ArrayList<>();
        for (Almacen almacen : usuario.getAlmacenes()){ if(almacen.getSucursalId() == sedeId) listaSede.add(almacen.getSucursalId()); }

        //Create worbook
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //Create styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        //Create main sheet
        SXSSFSheet sheet = workbook.createSheet("Profesores");
        String[] ht = {"ID","CODIGO_GRUPO","CICLO_ESCOLAR","PLANTEL","NIVEL","GRUPO","MODALIDAD","HORARIO","PROFESOR"};
        // Width las columnas
        int[] listaWidthColumna = new int[ht.length];
        for (int i = 0; i < ht.length; i++) {
            // Obtener el valor cuantas letras tiene.
            int wColumna = listaWidthColumna[i];
            // Si el valor (letras) es mayor que el valor anterior o posterior
            if(ht[i].length() > wColumna)
                listaWidthColumna[i] = ht[i].length();
        }
        //Create header row
        createRow(sheet, 0, ht, header);
        //Query general data
        List<ProgramaGrupoListadoProjection> grupos = new ArrayList<>();
        if(tipo.equals("JOBS"))
            grupos.addAll(programaGrupoDao.findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobs(listaSede, listaPlantelId, listaCursoId, listaModalidadId, listaFecha));
        else if(tipo.equals("SEMS"))
            grupos.addAll(programaGrupoDao.findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobsSems(listaSede, listaPlantelId, listaCursoId, listaModalidadId, listaFecha));
        //Sets initial row as 1 to avoid header
        Integer index = 1;
        for (ProgramaGrupoListadoProjection grupo : grupos){
            if(grupo.getNombreProfesor().trim().equals("")){
                ProgramaGrupo programaGrupo = programaGrupoDao.findById(grupo.getId());
                SucursalPlantel plantel = programaGrupo.getSucursalPlantel();
                String id = hashId.encode(programaGrupo.getId());
                SXSSFRow row = sheet.createRow(index);
                createCell(row,0, id, detail);//Id
                createCell(row,1 ,grupo.getCodigo(), detail);//Código
                createCell(row,2 ,grupo.getProgramacion(), detail);//Ciclo escolar
                createCell(row,3 ,plantel != null? plantel.getNombre() : "", detail);//Plantel
                createCell(row,4 ,grupo.getNivel(),detail);//Nivel
                createCell(row,5 ,grupo.getNombreGrupo(), detail);//Grupo
                createCell(row,6 ,grupo.getModalidad(), detail);//Modalidad
                createCell(row,7 ,grupo.getHorario(), detail);//Horario
                index++;

                // Obtener el valor cuantas letras tiene.
                // Si el valor (letras) es mayor que el valor anterior o posterior
                int wColumna = listaWidthColumna[0];
                if(id.length() > wColumna)
                    listaWidthColumna[0] = id.length();

                wColumna = listaWidthColumna[1];
                if(grupo.getCodigo().length() > wColumna)
                    listaWidthColumna[1] = grupo.getCodigo().length();

                wColumna = listaWidthColumna[2];
                if(grupo.getProgramacion().length() > wColumna)
                    listaWidthColumna[2] = grupo.getProgramacion().length();

                wColumna = listaWidthColumna[3];
                int countPlantel = plantel != null ? plantel.getNombre().length() : 0;
                if(countPlantel > wColumna)
                    listaWidthColumna[3] = countPlantel;

                wColumna = listaWidthColumna[4];
                if(grupo.getNivel().length() > wColumna)
                    listaWidthColumna[4] = grupo.getNivel().length();

                wColumna = listaWidthColumna[5];
                if(grupo.getNombreGrupo().length() > wColumna)
                    listaWidthColumna[5] = grupo.getNombreGrupo().length();

                wColumna = listaWidthColumna[6];
                if(grupo.getModalidad().length() > wColumna)
                    listaWidthColumna[6] = grupo.getModalidad().length();

                wColumna = listaWidthColumna[7];
                if(grupo.getHorario().length() > wColumna)
                    listaWidthColumna[7] = grupo.getHorario().length();
            }
        }

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999),IgnoredErrorType.NUMBER_STORED_AS_TEXT );

        //Create hidden sheet for lists
        SXSSFSheet hidden = workbook.createSheet("hidden");

        List<List<String>> listados = new ArrayList<>();
        List<EmpleadoComboProjection> empleados = empleadoDao.findAllByTipoEmpleadoIdAndEstatusIdNotInOrderByPrimerApellidoAscSegundoApellidoAscNombreAsc(ControlesMaestrosMultiples.CMM_EMP_TipoEmpleadoId.ACADEMICO, Arrays.asList(ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO));
        if(empleados != null && empleados.size() > 0){
            listados.add(empleados.stream().map( item -> item.getNombreCompletoASC()).collect(Collectors.toList()));
        }
        Integer max = Collections.max(listados.stream().map(item -> item.size()).collect(Collectors.toList()));

        for (Integer j = 0; j < max; j++){//Rows
            SXSSFRow row = hidden.createRow(j);
            for (Integer i = 0; i < listados.size(); i++){//Columns
                if ( j < listados.get(i).size()) { createCell(row, i, listados.get(i).get(j)); }
            }
        }

        //Add Datavalidations to simulate dropdowns
        for (Integer i = 0; i < listados.size(); i++){
            if (listados.get(i).size() > 0){
                XSSFDataValidation dropdown = createDropdown(workbook,new Integer[] {i,listados.get(i).size()},8 + i);
                sheet.addValidationData(dropdown);
            }
        }

        for (Integer i = 0; i < ht.length; i++){
            int width = 0;

            // Profesor
            if (ht[i] == "PROFESOR") {
                width = 10000;
            }else{
                if(listaWidthColumna[i] > 9)
                    width = ((int)(listaWidthColumna[i] * 1.14388)) * 256;
                else
                    width = ((int)(listaWidthColumna[i] * 1.54388)) * 256;
            }

            sheet.setColumnWidth(i, width);
        }

        workbook.setSheetHidden(1, true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Profesores "+tipo+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @PostMapping("/alumnos/download/{tipo}")
    public void templateAlumnosDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo, @RequestBody JSONObject json ) throws IOException, Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = (Integer) sede.get("id");
        List<Integer> listaPlantelId = Utilidades.getListItems(json.get("listaPlantel"), "id");
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");
        List<String> listaFecha = Utilidades.getListItems(json.get("listaFecha"), "fecha");

        List<Integer> listaSede = new ArrayList<>();

        //Recuperar todos los alumnos con inscripción pendiente de pago
        List<AlumnoOrdenPagoProjection> alumnos = new ArrayList<>();
        if(tipo.equals("SEMS"))
            alumnos = alumnoDao.findAlumnoOrdenPagoProjectionAllByEsJobsSems(sedeId, listaPlantelId, listaCursoId, listaModalidadId, listaFecha);
        if(tipo.equals("JOBS"))
            alumnos = alumnoDao.findAlumnoOrdenPagoProjectionAllByEsJobs(sedeId, listaPlantelId, listaCursoId, listaModalidadId, listaFecha);

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);

        SXSSFSheet sheet = workbook.createSheet("Alumnos");
        String[] ht = new String[]{};
        if (tipo.equals("SEMS")){
            ht = new String[]{"CODALU","DIGVER","CODALM2","NOMALU","PATERNO","MATERNO","GENERO","TELAL","FECHANAC","EMAIL","CODCUR","FECHINI","FECHFIN","NIVEL","HORARIO"};
            createRow(sheet, 0, ht, header);
        }
        if (tipo.equals("JOBS")){
            ht = new String[]{"CODALU","DIGVER","CODALM2","NOMALU","PATERNO","MATERNO","GENERO","TELAL","FECHANAC","EMAIL","PLANTEL","CODCUR","DESCRIPCION","FECHINI","FECHFIN","NIVEL","HORARIO"};
            createRow(sheet, 0, ht, header);
        }

        // Width las columnas
        int[] listaWidthColumna = new int[ht.length];
        for (int i = 0; i < ht.length; i++) {
            // Obtener el valor cuantas letras tiene.
            int wColumna = listaWidthColumna[i];
            // Si el valor (letras) es mayor que el valor anterior o posterior
            if(ht[i].length() > wColumna)
                listaWidthColumna[i] = ht[i].length();
        }

        Integer index = 1;
        for (AlumnoOrdenPagoProjection alumno : alumnos){

            SXSSFRow row = sheet.createRow(index);
            createCell(row,0 , alumno.getCodigoAlumno(), detail);

            // Obtener el valor cuantas letras tiene.
            // Si el valor (letras) es mayor que el valor anterior o posterior
            int wColumna = listaWidthColumna[0];
            if(alumno.getCodigoAlumno().length() > wColumna)
                listaWidthColumna[0] = alumno.getCodigoAlumno().length();

            if(tipo.equals("SEMS")){
                Integer digito = getDigito(alumno.getCodigoAlumno());
                createCell(row,1 , digito.toString(), detail);
                createCell(row,2 , alumno.getCodigoUDG(), detail);
                createCell(row,3 , alumno.getNombre(), detail);
                createCell(row,4 , alumno.getPrimerApellido(), detail);
                createCell(row,5 , alumno.getSegundoApellido(), detail);
                createCell(row,6 , alumno.getGenero(), detail);
                createCell(row,7 , alumno.getTelefono(), detail);
                createCell(row,8 , alumno.getFechaNacimiento(), detail);
                createCell(row,9 , alumno.getCorreoElectronico(), detail);
                createCell(row,10 , alumno.getCodigoGrupo(), detail);
                createCell(row,11, alumno.getFechaInicio(), detail);
                createCell(row,12, alumno.getFechaFin(), detail);
                createCell(row,13, alumno.getNivel(), detail);
                createCell(row,14, alumno.getHorario(), detail);

                wColumna = listaWidthColumna[1];
                if(digito.toString().length() > wColumna)
                    listaWidthColumna[1] = digito.toString().length();

                wColumna = listaWidthColumna[2];
                if(alumno.getCodigoUDG().length() > wColumna)
                    listaWidthColumna[2] = alumno.getCodigoUDG().length();

                wColumna = listaWidthColumna[3];
                if(alumno.getNombre().length() > wColumna)
                    listaWidthColumna[3] = alumno.getNombre().length();

                wColumna = listaWidthColumna[4];
                if(alumno.getPrimerApellido().length() > wColumna)
                    listaWidthColumna[4] = alumno.getPrimerApellido().length();

                wColumna = listaWidthColumna[5];
                if(alumno.getSegundoApellido() != null){
                    if(alumno.getSegundoApellido().length() > wColumna)
                        listaWidthColumna[5] = alumno.getSegundoApellido().length();
                }


                wColumna = listaWidthColumna[6];
                if(alumno.getGenero().length() > wColumna)
                    listaWidthColumna[6] = alumno.getGenero().length();

                wColumna = listaWidthColumna[7];
                if(alumno.getTelefono().length() > wColumna)
                    listaWidthColumna[7] = alumno.getTelefono().length();

                wColumna = listaWidthColumna[8];
                if(alumno.getFechaNacimiento().length() > wColumna)
                    listaWidthColumna[8] = alumno.getFechaNacimiento().length();

                wColumna = listaWidthColumna[9];
                if(alumno.getCorreoElectronico().length() > wColumna)
                listaWidthColumna[9] = alumno.getCorreoElectronico().length();

                wColumna = listaWidthColumna[10];
                if(alumno.getCodigoGrupo().length() > wColumna)
                listaWidthColumna[10] = alumno.getCodigoGrupo().length();

                wColumna = listaWidthColumna[11];
                if(alumno.getFechaInicio().length() > wColumna)
                listaWidthColumna[11] = alumno.getFechaInicio().length();

                wColumna = listaWidthColumna[12];
                if(alumno.getFechaFin().length() > wColumna)
                listaWidthColumna[12] = alumno.getFechaFin().length();

                wColumna = listaWidthColumna[13];
                if(alumno.getNivel().length() > wColumna)
                listaWidthColumna[13] = alumno.getNivel().length();

                wColumna = listaWidthColumna[14];
                if(alumno.getHorario().length() > wColumna)
                listaWidthColumna[14] = alumno.getHorario().length();


            } else if(tipo.equals("JOBS")){
                ProgramaGrupo grupo = programaGrupoDao.findByCodigoGrupoAndFechaInicio(alumno.getCodigoGrupo(), new SimpleDateFormat("yyyy-MM-dd").parse(alumno.getFechaInicio()));
                ProgramaIdioma programaIdioma = grupo.getProgramaIdioma();
                ControlMaestroMultiple idioma = programaIdioma.getIdioma();
                Programa programa = programaIdioma.getPrograma();
                SucursalPlantel sucursalPlantel = grupo.getSucursalPlantel();
                sucursalPlantel.getNombre();

                String descripcion = programa.getCodigo() + " " + idioma.getValor();
                String plantel = sucursalPlantel != null ? sucursalPlantel.getNombre() : "";

                Integer digito = getDigito(alumno.getCodigoAlumno());
                createCell(row,1 , digito.toString(), detail);
                createCell(row,2 , alumno.getCodigoUDG(), detail);
                createCell(row,3 , alumno.getNombre(), detail);
                createCell(row,4 , alumno.getPrimerApellido(), detail);
                createCell(row,5 , alumno.getSegundoApellido(), detail);
                createCell(row,6 , alumno.getGenero(), detail);
                createCell(row,7 , alumno.getTelefono(), detail);
                createCell(row,8 , alumno.getFechaNacimiento(), detail);
                createCell(row,9 , alumno.getCorreoElectronico(), detail);
                createCell(row,10, plantel,detail);
                createCell(row,11, alumno.getCodigoGrupo(), detail);
                createCell(row,12, descripcion,detail);
                createCell(row,13, alumno.getFechaInicio(), detail);
                createCell(row,14, alumno.getFechaFin(), detail);
                createCell(row,15, alumno.getNivel(), detail);
                createCell(row,16, alumno.getHorario(), detail);

                wColumna = listaWidthColumna[1];
                if(digito.toString().length() > wColumna)
                    listaWidthColumna[1] = digito.toString().length();

                wColumna = listaWidthColumna[2];
                if(alumno.getCodigoUDG() != null)
                    if(alumno.getCodigoUDG().length() > wColumna)
                        listaWidthColumna[2] = alumno.getCodigoUDG().length();

                wColumna = listaWidthColumna[3];
                if(alumno.getNombre().length() > wColumna)
                    listaWidthColumna[3] = alumno.getNombre().length();

                wColumna = listaWidthColumna[4];
                if(alumno.getPrimerApellido().length() > wColumna)
                    listaWidthColumna[4] = alumno.getPrimerApellido().length();

                wColumna = listaWidthColumna[5];
                if(alumno.getSegundoApellido() != null)
                    if(alumno.getSegundoApellido().length() > wColumna)
                        listaWidthColumna[5] = alumno.getSegundoApellido().length();

                wColumna = listaWidthColumna[6];
                if(alumno.getGenero().length() > wColumna)
                    listaWidthColumna[6] = alumno.getGenero().length();

                wColumna = listaWidthColumna[7];
                if(alumno.getTelefono() != null)
                    if(alumno.getTelefono().length() > wColumna)
                        listaWidthColumna[7] = alumno.getTelefono().length();

                wColumna = listaWidthColumna[8];
                if(alumno.getFechaNacimiento() != null)
                    if(alumno.getFechaNacimiento().length() > wColumna)
                        listaWidthColumna[8] = alumno.getFechaNacimiento().length();

                wColumna = listaWidthColumna[9];
                if (alumno.getCorreoElectronico() != null)
                    if(alumno.getCorreoElectronico().length() > wColumna)
                        listaWidthColumna[9] = alumno.getCorreoElectronico().length();

                wColumna = listaWidthColumna[10];
                int countPlantel = plantel != "" ? plantel.length() : 0;
                if(countPlantel > wColumna)
                    listaWidthColumna[10] = countPlantel;

                wColumna = listaWidthColumna[11];
                if(alumno.getCodigoGrupo().length() > wColumna)
                    listaWidthColumna[11] = alumno.getCodigoGrupo().length();

                wColumna = listaWidthColumna[12];
                int countDescripcion = descripcion.length();
                if(countDescripcion > wColumna)
                    listaWidthColumna[12] = countDescripcion;

                wColumna = listaWidthColumna[13];
                if(alumno.getFechaInicio().length() > wColumna)
                    listaWidthColumna[13] = alumno.getFechaInicio().length();

                wColumna = listaWidthColumna[14];
                if(alumno.getFechaFin().length() > wColumna)
                    listaWidthColumna[14] = alumno.getFechaFin().length();

                wColumna = listaWidthColumna[15];
                if(alumno.getNivel().length() > wColumna)
                    listaWidthColumna[15] = alumno.getNivel().length();

                wColumna = listaWidthColumna[16];
                if(alumno.getHorario().length() > wColumna)
                    listaWidthColumna[16] = alumno.getHorario().length();
            }

            index++;
        }

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999),IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < ht.length; i++){
            int width = 0;
            if(listaWidthColumna[i] > 9)
                width = ((int)(listaWidthColumna[i] * 1.14388)) * 256;
            else
                width = ((int)(listaWidthColumna[i] * 1.54388)) * 256;

            sheet.setColumnWidth(i, width);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Alumnos "+tipo+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @Transactional
    @RequestMapping(value = "profesores/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse templateProfesoresUpload(@RequestParam("file") MultipartFile file, ServletRequest req, HttpServletResponse response) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<EmpleadoComboProjection> empleados = empleadoDao.findAllByEstatusIdNotIn(Arrays.asList(ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO));
        List<List<String>> errors = new ArrayList<>();
        InputStream stream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator rows = sheet.iterator();
        rows.next();
        Integer index = 1;
        while (rows.hasNext()){
            Row row = (Row) rows.next();
            index ++;
            //Validate ID and PROFESOR Columns
            String id = getCellValue(row, 0, false);
            String empleado = getCellValue(row, 8, false);

            if (id != null && empleado != null){

                EmpleadoComboProjection profesor = null;
                profesor = empleados.stream().filter(item -> item.getNombreCompleto().toUpperCase().equals(empleado.toUpperCase())).findAny().orElse(null);

                Integer grupoId = hashId.decode(id);
                if (grupoId == null){
                    List<String> err = new ArrayList<>();
                    err.add(index.toString());
                    err.add("");
                    err.add("ID corrupto");
                    errors.add(err);
                }
                else if (profesor == null){
                    List<String> err = new ArrayList<>();
                    err.add(index.toString());
                    err.add("");
                    err.add("PROFESOR No se encuentra en el catálogo");
                    errors.add(err);
                }
                else {
                    ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
                    grupo.setEmpleadoId(profesor.getId());
                    grupo.setModificadoPorId(usuarioId);
                    grupo = programaGrupoDao.save(grupo);

                    //Actualizar salario del profesor
                    TabuladorDetalleEmpleadoProjection datosSueldo = tabuladorDetalleDao.findDatosEmpleadoSueldo(grupo.getProgramaIdiomaId(),grupo.getEmpleadoId(),grupo.getId());
                    ProgramaGrupo temp = null;
                    if(grupo.getId() != 0){
                        temp = programaGrupoDao.findById(grupoId);
                    }
                    if(temp != null && (temp.getSueldoProfesor() == null || temp.getCategoriaProfesor() == null) ){
                        temp.setSueldoProfesor(datosSueldo.getSueldo());
                        temp.setCategoriaProfesor(datosSueldo.getCategoria());
                        temp.setEmpleadoId(grupo.getEmpleadoId());
                        temp = programaGrupoDao.save(temp);
                    }

                    //Crear registro nuevo en la tabla de ProgramasGruposProfesores con la fecha de inicio del curso
                    ProgramaGrupoProfesor programaGrupoProfesor = new ProgramaGrupoProfesor();
                    programaGrupoProfesor.setEmpleadoId(temp.getEmpleadoId());
                    programaGrupoProfesor.setFechaInicio(temp.getFechaInicio());
                    programaGrupoProfesor.setGrupoId(temp.getId());
                    programaGrupoProfesor.setSueldo(temp.getSueldoProfesor());
                    programaGrupoProfesor.setActivo(true);

                    programaGrupoProfesorDao.save(programaGrupoProfesor);
                }
            }
        }
        return new JsonResponse(errors, "Plantilla procesada exitosamente", JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM VW_Listado_PlantillaGrupos_Excel ORDER BY NombreProfesor ASC";
        String[] alColumnas = new String[]{"Código", "Programación", "Sucursal","Nombre del Grupo","Nombre del profesor","Horario","Fecha de inicio","Fecha de fin","Idioma","Tipo de grupo","Modalidad","Nivel","Programa","Estatus"};

        excelController.downloadXlsxSXSSF(response, "grupos", query, alColumnas, null,"PlantillaGrupos");
    }

    private CellStyle createStyle(SXSSFWorkbook workbook, Short background, Short fontColor, boolean bold, boolean locked){
        CellStyle style = workbook.createCellStyle();
        if (background != null) {
            style.setFillForegroundColor(background);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        Font font = workbook.createFont();
        if (fontColor != null) { font.setColor(fontColor); }
        if (bold) { font.setBold(true); }
        style.setLocked(locked);
        style.setFont(font);
        return style;
    }

    private void createRow(SXSSFSheet sheet, Integer rowNumber, String[] values, CellStyle style) {
        SXSSFRow row = sheet.createRow(rowNumber);
        Integer index = 0;
        for (String value : values) {
            createCell(row, index, value, style);
            index++;
        }
    }

    private XSSFDataValidation createDropdown(SXSSFWorkbook workbook, Integer[] reference, Integer target) throws NoSuchFieldException, IllegalAccessException {
        String name = "list"+target;
        Name namedCell = workbook.createName();
        String refName = CellReference.convertNumToColString(reference[0]);
        namedCell.setNameName(name);
        namedCell.setRefersToFormula("hidden!$"+refName+"$1:$"+refName+"$" + reference[1]);

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        SXSSFSheet sheet = workbook.getSheet("hidden");
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);

        XSSFDataValidationHelper helper = new XSSFDataValidationHelper(xssfsheet);
        XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) helper.createFormulaListConstraint(name);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 20000, target, target);
        XSSFDataValidation validation = (XSSFDataValidation) helper.createValidation(constraint,addressList);

        return validation;
    }

    private List<String> CMMtoList(String control, Boolean referencia){
        if (referencia){
            return controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(control).stream()
                    .map(item -> item.getReferencia()).distinct().collect(Collectors.toList());
        } else {
            return controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(control).stream()
                    .map(item -> item.getValor()).distinct().collect(Collectors.toList());
        }

    }

    /** Módulo 10 alfanumérico **/
    private Integer getDigito(Integer tipoSuma, String referencia) throws Exception {
        if (referencia == null)
            throw new Exception("Referencia nula");
        if (referencia.length() < 2 || referencia.length() > 30)
            throw new Exception("Longitud de la referencia no válida");
        if (!referencia.toUpperCase().matches("^[A-Z0-9]*$"))
            throw new Exception("La referencia contiene caracteres no válidos");

        Map<String, Integer> map = new HashMap<String, Integer>(){{
            put("A",1); put("B",2); put("C",3); put("D",4); put("E",5); put("F",6); put("G",7); put("H",8); put("I",9);
            put("J",1); put("K",2); put("L",3); put("M",4); put("N",5); put("O",6); put("P",7); put("Q",8); put("R",9);
            put("S",1); put("T",2); put("U",3); put("V",4); put("W",5); put("X",6); put("Y",7); put("Z",8);
        }};

        //Converir la referencia a mayusculas e invertirla para leer de derecha a izquierda
        StringBuilder sb = new StringBuilder();
        sb.append(referencia.toUpperCase());
        String reversed = sb.reverse().toString();

        Integer total = 0;
        for (Integer i = 0; i < reversed.length(); i++){
            String c = Character.toString(reversed.charAt(i));
            Integer multiplier = (2 - (i % 2));
            Integer digit;
            if (map.get(c) != null)
                digit = map.get(c);
            else
                digit = Character.getNumericValue(reversed.charAt(i));

            if (tipoSuma == 0)
                total += (digit * multiplier);
            else{
                Integer suma = (((digit * multiplier) / 10) + ((digit * multiplier) % 10));
                total += suma;
            }
        }
        Integer digito = (((total / 10) + 1) * 10) - total;
        return digito > 9 ? 0 : digito;
    }

    private Integer getDigito(String referencia) throws Exception {
        return getDigito(1, referencia);
    }

    private String getCellValue(Row row, Integer index, Boolean required) throws Exception {
        String message = "La celda " + CellReference.convertNumToColString(index)+(row.getRowNum() + 1)+" es requerida.";
        return  getCellValue(row,index,required, message);
    }

    private String getCellValue(Row row, Integer index, Boolean required, String message) throws Exception {
        try{
            Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            //When cell index is required, then validate nullish values
            if (required){
                if (cell == null) throw new Exception(message);
                String response = new DataFormatter().formatCellValue(cell);
                if (response.trim().length() == 0) throw new Exception(message);
                if (response.trim().toUpperCase().equals("N/A")) throw new Exception(message);
                return response;
            } else {
                if (cell == null) return null;
                String response = new DataFormatter().formatCellValue(cell);
                if (response.trim().length() == 0) return null;
                if (response.trim().toUpperCase().equals("N/A")) return null;
                return response;
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private Integer getCMMIdByString(List<ControlMaestroMultipleComboProjection> listado, String valor, Boolean referencia){
        if (valor == null)
            return null;
        ControlMaestroMultipleComboProjection control = null;
        if (referencia)
            control = listado.stream().filter(item -> valor.trim().toUpperCase().equals(item.getReferencia().toUpperCase())).findAny().orElse(null);
        else
            control = listado.stream().filter(item -> valor.trim().toUpperCase().equals(item.getValor().toUpperCase())).findAny().orElse(null);
        return control != null? control.getId() : null;
    }

    @Async
    public JSONArray processBatch(List<Row> rows,
                                  HashMap<String, List<ControlMaestroMultipleComboProjection>> listados,
                                  Integer usuarioId, String tipo) {
        ProgramaGrupo grupo = null;
        JSONArray feedback = new JSONArray();
        for (Row row : rows){
            try {
                //Validate all required fields
                String composedId   = getCellValue(row,0,true);
                if (composedId != null){
                    //Decompose and decode 'ID'
                    String[] ids            = composedId.split("-");
                    Integer grupoId         = hashId.decode(ids[0]);
                    Integer sucursalId      = hashId.decode(ids[1]);
                    Integer monedaId        = hashId.decode(ids[2]);
                    Integer listaPreciosId  = hashId.decode(ids[3]);
                    Integer articuloId      = hashId.decode(ids[4]);
                    Integer unidadMedidaId  = hashId.decode(ids[5]);

                    //Validate 'ID'
                    if (grupoId == null || sucursalId == null || monedaId == null || listaPreciosId == null || articuloId == null || unidadMedidaId == null)
                        throw new Exception("ID corrupto");

                    Alumno alumno = null;
                    if (tipo.equals("JOBS")) {
                        alumno = preprocesarRegistroJOBS(row, listados, usuarioId);
                    }
                    else if (tipo.equals("SEMS")) {
                        alumno = preprocesarRegistroSEMS(row, listados, usuarioId);
                    }
                    else if (tipo.equals("PCP")) {
                        alumno = preprocesarRegistroPCP(row, listados, usuarioId);
                    }
                    else if (tipo.equals("dGhlY2FrZWlzYWxpZQ==")){
                        alumno = preprocesarRegistroApertura(row, listados, usuarioId);
                    }
                    //Find grupo only once
                    if (grupo == null)
                        grupo = programaGrupoDao.findById(grupoId);
                    if (alumno != null){
                        if (alumno.getId() != null)
                            alumno.setModificadoPorId(usuarioId);
                        else
                            alumno.setCreadoPorId(usuarioId);
                        if (tipo.equals("dGhlY2FrZWlzYWxpZQ=="))
                            relacionarAlumnoGrupo(alumno, grupo, ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PLANTILLA,true);
                        else
                            relacionarAlumnoGrupo(alumno, grupo);
                    }
                }
            } catch (Exception e) {
                JSONObject obj = new JSONObject();
                String codigo = null;
                try {
                    codigo = getCellValue(row, 9, false);
                } catch (Exception ee){
                }
                obj.appendField("row", row.getRowNum() + 1);
                obj.appendField("ref", codigo);
                obj.appendField("message", e.getMessage());
                feedback.add(obj);
            }
        }
        return feedback;
    }

    //Execute validations over row (parameter) for JOBS groups
    private Alumno preprocesarRegistroJOBS(Row row, HashMap<String, List<ControlMaestroMultipleComboProjection>> listados, Integer usuarioId) throws Exception {
        //Get all required fields
        String composedId   = getCellValue(row,0,true);
        String codigo       = getCellValue(row,9,true);
        String apellido1    = getCellValue(row,10,true);
        String nombre       = getCellValue(row,12,true);
        String fecha        = getCellValue(row,16,false);
        String centro       = getCellValue(row,17,true);
        String carrera      = getCellValue(row,18,true);
        String genero       = getCellValue(row,19,true);

        String[] ids            = composedId.split("-");
        Integer grupoId         = hashId.decode(ids[0]);
        Integer sucursalId      = hashId.decode(ids[1]);
        Integer monedaId        = hashId.decode(ids[2]);
        Integer listaPreciosId  = hashId.decode(ids[3]);
        Integer articuloId      = hashId.decode(ids[4]);
        Integer unidadMedidaId  = hashId.decode(ids[5]);

        //Validate 'ID'
        if (grupoId == null || sucursalId == null || monedaId == null || listaPreciosId == null || articuloId == null || unidadMedidaId == null)
            throw new Exception("ID corrupto");

        if (codigo.length() < 9)
            throw new Exception("Código de alumno no válido");

        String apellido2    = getCellValue(row, 11, false, null);
        String telefono     = getCellValue(row, 13, false, null);
        String celular      = getCellValue(row, 14, false, null);
        String correo       = getCellValue(row, 15, false, null);
        String turno        = getCellValue(row, 20, false, null);
        String grado        = getCellValue(row, 21, false, null);
        String grupo        = getCellValue(row, 22, false, null);;

        Integer centroId = getCMMIdByString(listados.get("centros"),centro,true);
        Integer carreraId = getCMMIdByString(listados.get("carreras"),carrera,false);
        Integer generoId = getCMMIdByString(listados.get("generos"),genero,false);
        Integer turnoId = getCMMIdByString(listados.get("turnos"),turno,false);
        Integer gradoId = getCMMIdByString(listados.get("grados"),grado,false);

        ControlMaestroMultipleComboProjection c = null;
        for (ControlMaestroMultipleComboProjection item : listados.get("carreras")){
            ControlMaestroMultipleComboProjection referencia = item.getCmmReferencia();
            if(referencia != null){
                String fullName = item.getValor() + " - " + referencia.getReferencia();
                fullName = fullName.toUpperCase().trim();
                if (fullName.equals(carrera.toUpperCase().trim()) && referencia.getId().equals(centroId))
                    c = item;
            }
        }
        carreraId = c != null ? c.getId() : null;

        //Validate dropdown fiels
        if (centroId == null)
            throw new Exception("INSTITUCIÓN_EDUCATIVA No se encuentra en el catálogo");
        if (generoId == null)
            throw new Exception("GENERO No se encuentra en el catálogo");
        if (carrera != null && carreraId == null)
            throw new Exception("CARRERA No se encuentra en el catálogo");
        ControlMaestroMultiple control = controlMaestroMultipleDao.findCMMById(carreraId);
        if(!control.getCmmReferenciaId().equals(centroId))
            throw new Exception("CARRERA No corresponde a la INSTITUCIÓN_EDUCATIVA");

        fecha = validarFecha(row, 16);

        //Find alumno by codigo_udg; if exists update it else create it
        Alumno alumno = alumnoDao.findByCodigoAlumnoUDG(codigo);
        if (alumno == null) {
            alumno = new Alumno();
            alumno.setCodigoAlumnoUDG(codigo);
            alumno.setPrimerApellido(apellido1);
            alumno.setSegundoApellido(apellido2);
            alumno.setNombre(nombre);
            alumno.setTelefonoFijo(telefono);
            alumno.setTelefonoMovil(celular);
            alumno.setCorreoElectronico(correo);
            alumno.setFechaNacimiento((fecha != null && fecha.length() > 0)? new SimpleDateFormat("dd/MM/yyyy").parse(fecha) : null);

            alumno.setGeneroId(generoId);
            alumno.setCreadoPorId(usuarioId);
            alumno.setAlumnoJOBS(true);
            alumno.setSucursalId(sucursalId);
            alumno.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PLX"));
            alumno.setActivo(true);
        }

        alumno.setProgramaJOBSId(ControlesMaestrosMultiples.CMM_ALU_ProgramaJOBS.JOBS);
        alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.ALUMNO);
        alumno.setCentroUniversitarioJOBSId(centroId);
        alumno.setCarreraJOBSId(carreraId);

        alumno = alumnoDao.save(alumno);
        return alumno;
    }

    //Execute validations over row (parameter) for SEMS groups
    private Alumno preprocesarRegistroSEMS(Row row, HashMap<String, List<ControlMaestroMultipleComboProjection>> listados, Integer usuarioId) throws Exception {
        //Get all required fields
        String composedId   = getCellValue(row,0,true);
        String codigo       = getCellValue(row,9,true);
        String apellido1    = getCellValue(row,10,true);
        String nombre       = getCellValue(row,12,true);
        String fecha        = getCellValue(row,16,true);
        String prepa        = getCellValue(row,17,true);
        String carrera      = getCellValue(row,18,true);
        String genero       = getCellValue(row,19,true);

        String[] ids            = composedId.split("-");
        Integer grupoId         = hashId.decode(ids[0]);
        Integer sucursalId      = hashId.decode(ids[1]);
        Integer monedaId        = hashId.decode(ids[2]);
        Integer listaPreciosId  = hashId.decode(ids[3]);
        Integer articuloId      = hashId.decode(ids[4]);
        Integer unidadMedidaId  = hashId.decode(ids[5]);

        //Validate 'ID'
        if (grupoId == null || sucursalId == null || monedaId == null || listaPreciosId == null || articuloId == null || unidadMedidaId == null)
            throw new Exception("ID corrupto");

        if (codigo.length() < 9)
            throw new Exception("Código de alumno no válido");

        String apellido2    = getCellValue(row, 11, false, null);
        String telefono     = getCellValue(row, 13, false, null);
        String celular      = getCellValue(row, 14, false, null);
        String correo       = getCellValue(row, 15, false, null);
        String turno        = getCellValue(row, 20, false, null);
        String grado        = getCellValue(row, 21, false, null);
        String grupo        = getCellValue(row, 22, false, null);;

        Integer prepaId = getCMMIdByString(listados.get("prepas"),prepa,false);
        Integer generoId = getCMMIdByString(listados.get("generos"),genero,false);
        Integer turnoId = getCMMIdByString(listados.get("turnos"),turno,false);
        Integer gradoId = getCMMIdByString(listados.get("grados"),grado,false);

        //Validate dropdown fiels
        if (prepaId == null)
            throw new Exception("INSTITUCIÓN_EDUCATIVA "+ prepa == null ? "" : ("("+prepa+")")+" No se encuentra en el catálogo");
        if (generoId == null)
            throw new Exception("GENERO "+ genero == null? "" : ("("+genero+")")+ " No se encuentra en el catálogo");
        if (turno != null && turnoId == null)
            throw new Exception("TURNO "+ turno == null? "" : ("("+turno+")")+" No se encuentra en el catálogo");
        if (grado != null && gradoId == null)
            throw new Exception("GRADO No se encuentra en el catálogo");

        fecha = validarFecha(row,16);

        //Find alumno by codigo_udg; if exists update it else create it
        Alumno alumno = alumnoDao.findByCodigoAlumnoUDG(codigo);
        if (alumno == null) {
            alumno = new Alumno();
            alumno.setCodigoAlumnoUDG(codigo);
            alumno.setPrimerApellido(apellido1);
            alumno.setSegundoApellido(apellido2);
            alumno.setNombre(nombre);
            alumno.setTelefonoFijo(telefono);
            alumno.setTelefonoMovil(celular);
            alumno.setCorreoElectronico(correo);
            alumno.setFechaNacimiento((fecha != null && fecha.length() > 0)? new SimpleDateFormat("dd/MM/yyyy").parse(fecha) : null);

            alumno.setGeneroId(generoId);
            alumno.setCreadoPorId(usuarioId);
            alumno.setAlumnoJOBS(true);
            alumno.setSucursalId(sucursalId);
            alumno.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PLX"));
            alumno.setActivo(true);
        }

        alumno.setProgramaJOBSId(ControlesMaestrosMultiples.CMM_ALU_ProgramaJOBS.JOBS_SEMS);
        alumno.setPreparatoriaJOBSId(prepaId);
        alumno.setBachilleratoTecnologico(carrera);
        alumno.setTurnoId(turnoId);
        alumno.setGradoId(gradoId);
        alumno.setGrupo(grupo);
        if (alumno.getId() == null)
            alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.ALUMNO);
        alumno = alumnoDao.save(alumno);

        return alumno;
    }

    //Execute validations over row (parameter) for PCP groups
    private Alumno preprocesarRegistroPCP(Row row, HashMap<String, List<ControlMaestroMultipleComboProjection>> listados, Integer usuarioId) throws Exception {
        //Get all required fields
        String composedId   = getCellValue(row,0,true);
        String apellido1    = getCellValue(row,10,true);
        String nombre       = getCellValue(row,12,true);
        String fecha        = getCellValue(row,16,true);
        String genero       = getCellValue(row,17,true);

        String[] ids            = composedId.split("-");
        Integer grupoId         = hashId.decode(ids[0]);
        Integer sucursalId      = hashId.decode(ids[1]);
        Integer monedaId        = hashId.decode(ids[2]);
        Integer listaPreciosId  = hashId.decode(ids[3]);
        Integer articuloId      = hashId.decode(ids[4]);
        Integer unidadMedidaId  = hashId.decode(ids[5]);

        //Validate 'ID'
        if (grupoId == null || sucursalId == null || monedaId == null || listaPreciosId == null || articuloId == null || unidadMedidaId == null)
            throw new Exception("ID corrupto");

        String folio        = getCellValue(row,9,false);
        String apellido2    = getCellValue(row, 11, false);
        String telefono     = getCellValue(row, 13, false);
        String celular      = getCellValue(row, 14, false);
        String correo       = getCellValue(row, 15, false);
        String dependencia  = getCellValue(row,18,false);

        if (celular == null && telefono == null)
            throw new Exception("Se requiere un telefono de contacto");

        Integer generoId = getCMMIdByString(listados.get("generos"),genero,false);

        //Validate dropdown fiels
        if (generoId == null)
            throw new Exception("GENERO No se encuentra en el catálogo");

        fecha = validarFecha(row, 16);
        nombre = nombre != null ? nombre.trim().toUpperCase() : nombre;
        apellido1 = apellido1 != null ? apellido1.trim().toUpperCase() : apellido1;
        apellido2 = apellido2 != null ? apellido2.trim().toUpperCase() : apellido2;

        //Find alumno by full_name; if exists update it else create it
        Alumno alumno = alumnoDao.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, apellido1, apellido2);
        if (alumno == null) {
            alumno = new Alumno();
            alumno.setFolio(folio);
            alumno.setDependencia(dependencia);
            alumno.setPrimerApellido(apellido1);
            alumno.setSegundoApellido(apellido2);
            alumno.setNombre(nombre);
            alumno.setTelefonoFijo(telefono);
            alumno.setTelefonoMovil(celular);
            alumno.setCorreoElectronico(correo);
            alumno.setFechaNacimiento((fecha != null && fecha.length() > 0)? new SimpleDateFormat("dd/MM/yyyy").parse(fecha) : null);

            alumno.setGeneroId(generoId);
            alumno.setCreadoPorId(usuarioId);
            alumno.setAlumnoJOBS(false);
            alumno.setSucursalId(sucursalId);
            alumno.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PLX"));
            alumno.setActivo(true);

            //Validate Contacto
            try{
                String contactoApellido1 = getCellValue(row,19,true);
                String contactoApellido2 = getCellValue(row,20, false);
                String contactoNombre = getCellValue(row,21, true);
                String parentesco = getCellValue(row, 22, false);
                String contactoTelefono = getCellValue(row, 23, false);
                String contactoCelular = getCellValue(row, 24, false);
                String contactoCorreo = getCellValue(row, 25, true);

                Integer parentescoId = getCMMIdByString(listados.get("parentescos"),parentesco,false);

                //Validate dropdown fiels
                if (parentescoId == null)
                    throw new Exception("GENERO No se encuentra en el catálogo");

                if (contactoCelular == null && contactoTelefono == null)
                    throw new Exception("Se requiere un telefono de contacto");

                AlumnoContacto contacto = new AlumnoContacto();
                contacto.setPrimerApellido(contactoApellido1);
                contacto.setSegundoApellido(contactoApellido2);
                contacto.setNombre(contactoNombre);
                contacto.setParentescoId(parentescoId);
                contacto.setTelefonoFijo(contactoTelefono);
                contacto.setTelefonoMovil(contactoCelular);
                contacto.setCorreoElectronico(contactoCorreo);

                List<AlumnoContacto> contactos = new ArrayList<>();
                contactos.add(contacto);

                alumno.setContactos(contactos);
            } catch (Exception e){
                //Ignore
            }
        }

        LocalDate start = alumno.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = LocalDate.now();
        long edad = ChronoUnit.YEARS.between(start, end);
        if (edad >= 18)
            alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.ALUMNO);
        else
            alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.CANDIDATO);
        alumno = alumnoDao.save(alumno);
        return alumno;
    }

    //Execute validations over row (parameter) for custom groups
    private Alumno preprocesarRegistroApertura(Row row, HashMap<String, List<ControlMaestroMultipleComboProjection>> listados, Integer usuarioId) throws Exception {
        //Get all required fields
        String composedId   = getCellValue(row,0,true);
        String apellido1    = getCellValue(row,10,false);
        String nombre       = getCellValue(row,12,false);
        String fecha        = getCellValue(row,16,false);
        String genero       = getCellValue(row,17,false);

        String[] ids            = composedId.split("-");
        Integer grupoId         = hashId.decode(ids[0]);
        Integer sucursalId      = hashId.decode(ids[1]);
        Integer monedaId        = hashId.decode(ids[2]);
        Integer listaPreciosId  = hashId.decode(ids[3]);
        Integer articuloId      = hashId.decode(ids[4]);
        Integer unidadMedidaId  = hashId.decode(ids[5]);

        //Validate 'ID'
        if (grupoId == null || sucursalId == null || monedaId == null || listaPreciosId == null || articuloId == null || unidadMedidaId == null)
            throw new Exception("ID corrupto");

        String codigo       = getCellValue(row,9,false);
        String apellido2    = getCellValue(row, 11, false);
        String telefono     = getCellValue(row, 13, false);
        String celular      = getCellValue(row, 14, false);
        String correo       = getCellValue(row, 15, false);
        String dependencia  = getCellValue(row,18,false);

        Integer generoId = getCMMIdByString(listados.get("generos"),genero,false);

        fecha = validarFecha(row, 16);
        nombre = nombre != null ? nombre.trim().toUpperCase() : nombre;
        apellido1 = apellido1 != null ? apellido1.trim().toUpperCase() : apellido1;
        apellido2 = apellido2 != null ? apellido2.trim().toUpperCase() : apellido2;

        //Find alumno by proulex code; if exists update it else create it
        Alumno alumno = alumnoDao.findByCodigo(codigo);;
        if (alumno == null) {
            alumno = new Alumno();
            alumno.setPrimerApellido(apellido1);
            alumno.setSegundoApellido(apellido2);
            alumno.setNombre(nombre);
            alumno.setTelefonoFijo(telefono);
            alumno.setTelefonoMovil(celular);
            alumno.setCorreoElectronico(correo);
            alumno.setFechaNacimiento((fecha != null && fecha.length() > 0)? new SimpleDateFormat("dd/MM/yyyy").parse(fecha) : null);

            alumno.setGeneroId(generoId);
            alumno.setAlumnoJOBS(false);
            alumno.setCreadoPorId(usuarioId);
            alumno.setSucursalId(sucursalId);
            alumno.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PLX"));
            alumno.setActivo(true);
        } else {
            alumno.setPrimerApellido(apellido1 != null? apellido1 : alumno.getPrimerApellido());
            alumno.setSegundoApellido(apellido2 != null? apellido2 : alumno.getSegundoApellido());
            alumno.setNombre(nombre != null ? nombre : alumno.getNombre());
            alumno.setTelefonoFijo(telefono != null ? telefono : alumno.getTelefonoFijo());
            alumno.setTelefonoMovil(celular != null ? celular : alumno.getTelefonoMovil());
            alumno.setCorreoElectronico(correo != null ? correo : alumno.getCorreoElectronico());
            alumno.setFechaNacimiento((fecha != null && fecha.length() > 0)? new SimpleDateFormat("dd/MM/yyyy").parse(fecha) : alumno.getFechaNacimiento());

            alumno.setGeneroId(generoId != null ? generoId : alumno.getGeneroId());
        }

        alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.ALUMNO);
        return alumno;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void relacionarAlumnosGrupos(List<Alumno> alumnos, ProgramaGrupo grupo) throws Exception{
        for (Alumno alumno : alumnos){
            relacionarAlumnoGrupo(alumno, grupo);
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void relacionarAlumnoGrupo(Alumno alumno, ProgramaGrupo grupo) throws Exception {
        relacionarAlumnoGrupo(alumno, grupo, ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PLANTILLA,false);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void relacionarAlumnoGrupo(Alumno alumno, ProgramaGrupo grupo, Integer origenId) throws Exception {
        relacionarAlumnoGrupo(alumno, grupo, origenId,false);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void relacionarAlumnoGrupo(Alumno alumno, ProgramaGrupo grupo, Integer origenId, Boolean isApertura) throws Exception {
        Integer usuarioId = alumno.getModificadoPorId() != null? alumno.getModificadoPorId() : alumno.getCreadoPorId();
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_relacionarAlumnoGrupo");
        query.registerStoredProcedureParameter( 1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 2, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 3, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 4, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 5, Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 6, String.class, ParameterMode.OUT);
        query.setParameter(1,alumno.getId());
        query.setParameter(2,grupo.getId());
        query.setParameter(3,origenId);
        query.setParameter(4,usuarioId);
        query.setParameter(5,isApertura);
        String feedback = (String) query.getOutputParameterValue(6);
        if (feedback != null)
            throw new Exception(feedback);
    }

    private String validarFecha(Row row, Integer columna) throws Exception {
        String fecha = getCellValue(row, columna, false);
        if (fecha == null)
            return null;
        //Validate birthdate matches with format
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            //When date cannot be readed as string, try to read it as date.
            Date tempDate = null;
            try {
                tempDate = row.getCell(columna).getDateCellValue();
            } catch ( Exception e){
                throw new Exception("FECHA_NACIMIENTO no contiene un valor de fecha valido.");
            }
            if (tempDate != null){
                fecha = new SimpleDateFormat("dd/MM/yyyy").format(tempDate);
            } else
                throw new Exception("FECHA_NACIMIENTO no coincide con el formato requerido. DD/MM/YYYY");
        }
        return fecha;
    }

    public void getDuration(long start, long end){
        long duration = end - start;
        long ms = duration % 1000;
        duration = (duration - ms) / 1000;//Transform to seconds
        long s = duration % 60;
        duration = (duration - s) / 60;//Transform to minutes
        long m = duration % 60;
        duration = (duration - m) / 60;//Transform to hours
        long h = duration % 24;
        System.out.println("Elapsed time: "+(h<10?"0"+h:h)+":"+(m<10? "0"+m:m)+":"+(s<10?"0"+s:s)+"."+(ms<10?"00"+ms:(ms<100?"0"+ms:ms)));
        System.out.println(end - start);
    }
}
