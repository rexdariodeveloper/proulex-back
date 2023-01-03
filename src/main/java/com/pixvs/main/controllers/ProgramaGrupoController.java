package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.Alumno.AlumnoComboProjection;
import com.pixvs.main.models.projections.Alumno.AlumnoOrdenPagoProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionResumenProjection;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoCalificacionesProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionBorrarProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionListadoGrupoProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionProjection;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.*;
import com.pixvs.main.models.projections.ProgramaGrupoExamenDetalle.ListadoProgramaGrupoExamenDetalleProjection;
import com.pixvs.main.models.projections.ProgramaGrupoListadoClase.ProgramaGrupoListadoClaseEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleListadoProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboPlantelFiltrosProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import com.pixvs.main.services.ProgramasGruposAlertaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.Alerta;
import com.pixvs.spring.models.AlertaDetalle;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import com.pixvs.spring.services.*;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.ss.util.CellUtil.createCell;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/grupos")
public class ProgramaGrupoController {

    @Autowired
    private Environment environment;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private ProgramaGrupoHistorialDao programaGrupoHistorialDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private PACicloDao paCicloDao;
    @Autowired
    private LogController logController;
    @Autowired
    private TabuladorDetalleDao tabuladorDetalleDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private HashId hashId;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ProgramaGrupoListadoClaseDao programaGrupoListadoClaseDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private AlumnoExamenCalificacionDao alumnoExamenCalificacionDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private ProgramaIdiomaExamenDetalleDao programaIdiomaExamenDetalleDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private ListadoPrecioDetalleDao listadoPrecioDetalleDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private OrdenVentaDao ordenVentaDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private PAModalidadHorarioDao paModalidadHorarioDao;
    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;
    @Autowired
    private ProgramaGrupoProfesorDao programaGrupoProfesorDao;
    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;
    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    private AlumnoExamenCertificacionDao alumnoExamenCertificacionDao;
    @Autowired
    private ProcesadorAlertasService procesadorAlertasService;
    @Autowired
    private AlertasDao alertasDao;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private InscripcionSinGrupoDao inscripcionSinGrupoDao;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private OrdenVentaDetalleDao ordenVentaDetalleDao;
    @Autowired
    private PAModalidadDao modalidadDao;
    @Autowired
    private BecaUDGDao becaUDGDao;
    @Autowired
    private ProgramaGrupoExamenDetalleDao programaGrupoExamenDetalleDao;
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        List<SucursalComboPlantelFiltrosProjection> sucursales = sucursalDao.findProjectedComboPlantelAllByUsuarioFiltros(usuarioId);
        Integer allSucursales = 1;
        List<String> sucursalesPermisos = new ArrayList<>();

        if (sucursales.size() > 0) {
            allSucursales = 0;

            for (SucursalComboPlantelFiltrosProjection sucursal : sucursales) {
                sucursalesPermisos.add(sucursal.getNombre());
            }
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", programaGrupoDao.findAllByView("activo", sucursalesPermisos, allSucursales));
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("tipoGrupo", controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE));

        HashMap<String, Boolean> permisos = new HashMap<>();
        
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_GRUPOS) != null)
            permisos.put("EXPORTAR_EXCEL_GRUPOS", Boolean.TRUE);

        json.put("permisos", permisos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody JSONObject json, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> tiposGrupoId = Utilidades.getListItems(json.get("tiposGrupo"), "id");

        if (sedesId == null || sedesId.size() == 0) {
            List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);
            sedesId = new ArrayList<>();
            for (SucursalComboProjection sucursal : sucursales) {
                sedesId.add(sucursal.getId());
            }
        }

        HashMap<String, String> estatus = (HashMap<String, String>) json.get("estatus");
        String estatusBuscar = estatus != null ? estatus.get("nombre") : null;

        String codigoAlumno = (String) json.get("codigoAlumno");
        List<InscripcionProjection> inscripciones = inscripcionDao.findListadoGrupoByCodigoAlumno(codigoAlumno != null ? codigoAlumno : "");
        List<Integer> gruposIds = inscripciones.stream().map(item -> item.getGrupoId()).collect(Collectors.toList());
        gruposIds = !gruposIds.isEmpty() ? gruposIds : null;

        List<ProgramaGrupoListadoProjection> grupos =
                programaGrupoDao.findAllByViewFiltros(
                        sedesId,
                        fechas,
                        modalidadesId,
                        tiposGrupoId,
                        estatusBuscar,
                        gruposIds
                );

        return new JsonResponse(grupos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaGrupo grupo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (grupo.getId() != null) {
            //grupo.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            ProgramaGrupo objetoActual = programaGrupoDao.findById(grupo.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), grupo.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            grupo.setModificadoPorId(idUsuario);
        }

        List<ProgramaGrupoProfesor> profesores = new ArrayList<>();
        profesores.addAll(grupo.getProfesoresTitulares());
        ProgramaGrupo grupoBD = programaGrupoDao.findById(grupo.getId());

        // Validar cambio de profesor mediante combo de profesores
        boolean profesorDiferente = grupo.getId() != null
                && (
                        (grupo.getEmpleado() == null && grupoBD.getEmpleadoId() != null)
                        || (grupo.getEmpleado() != null && grupoBD.getEmpleadoId() != null && grupo.getEmpleado().getId().intValue() != grupoBD.getEmpleadoId().intValue())
                );
        if(profesorDiferente){
            Date fechaActual = new Date();
            if(grupo.getFechaInicio().getTime() < fechaActual.getTime()){
                return new JsonResponse(null,"No es posible cambiar el profesor titular de este modo pasando la fecha de inicio", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
            Boolean tienePreenomina = programaGrupoDao.getGrupoTienePrenomina(grupo.getId());
            if(tienePreenomina){
                return new JsonResponse(null,"No es posible cambiar el profesor titular de este modo debido a que ya se generó prenómina del grupo", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
        }

        grupo.setFechaCancelacion(grupoBD.getFechaCancelacion());
        grupo.setModificadoPorId(idUsuario);
        if(grupo.getModalidadHorario() !=null){
            grupo.setModalidadHorarioId(grupo.getModalidadHorario().getId());
            grupo.setModalidadHorario(null);
        }
        if(grupo.getPaModalidad()!=null){
            grupo.setPaModalidadId(grupo.getPaModalidad().getId());
            grupo.setPaModalidad(null);
        }
        if(grupo.getPlataforma()!=null){
            grupo.setPlataformaId(grupo.getPlataforma().getId());
            grupo.setPlataforma(null);
        }
        if(grupo.getEstatus()!=null){
            grupo.setEstatusId(grupo.getEstatus().getId());
            grupo.setEstatus(null);
        }
        if(grupo.getSucursalPlantel()!=null){
            grupo.setSucursalPlantelId(grupo.getSucursalPlantel().getId());
            grupo.setSucursalPlantel(null);
        }
        if(grupo.getProgramacionAcademicaComercial()!=null){
            grupo.setProgramacionAcademicaComercialId(grupo.getProgramacionAcademicaComercial().getId());
            grupo.setProgramacionAcademicaComercial(null);
        }
        if(grupo.getProgramaIdioma()!=null){
            grupo.setProgramaIdiomaId(grupo.getProgramaIdioma().getId());
            grupo.setProgramaIdioma(null);
        }
        if(grupo.getSucursal()!=null){
            grupo.setSucursalId(grupo.getSucursal().getId());
            grupo.setSucursal(null);
        }
        if(grupo.getTipoGrupo()!=null){
            grupo.setTipoGrupoId(grupo.getTipoGrupo().getId());
            grupo.setTipoGrupo(null);
        }
        if(grupo.getPaCiclo()!=null){
            grupo.setPaCicloId(grupo.getPaCiclo().getId());
            grupo.setPaCiclo(null);
        }
        if(grupo.getEmpleado()!=null){
            grupo.setEmpleadoId(grupo.getEmpleado().getId());
            grupo.setEmpleado(null);
        }
        if(grupo.getListadoClases()!=null){
            for(ProgramaGrupoListadoClase clase : grupo.getListadoClases()) {
                ProgramaGrupoListadoClase temp = programaGrupoListadoClaseDao.findById(clase.getId());
                if(clase.getId() == null){
                    clase.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                }
                if(temp==null || !clase.getEmpleado().getId().equals(temp.getEmpleadoId())){
                    clase.setChangeDetected(true);
                }else {
                    clase.setChangeDetected(false);
                }
                if (clase.getEmpleado() != null) {
                    clase.setEmpleadoId(clase.getEmpleado().getId());
                    clase.setEmpleado(null);
                }
                if(clase.getFormaPago() != null){
                    clase.setFormaPagoId(clase.getFormaPago().getId());
                    clase.setFormaPago(null);
                }
            }
        }
        List<ProgramaGrupoListadoClase> temp = grupo.getListadoClases();
        if(grupo.getNuevoProfesorTitular() != null){
            grupo.setEmpleadoId(grupo.getNuevoProfesorTitular().getId());
        }

        if(grupo.getId() == null){
            ProgramaIdioma programaIdioma = programaIdiomaDao.findById(grupo.getProgramaIdiomaId());
            grupo.setCodigoGrupo(getCodigo(grupo.getSucursalId(),grupo.getSucursalPlantelId(),programaIdioma,grupo.getPaModalidadId(),grupo.getNivel(),grupo.getModalidadHorarioId(),grupo.getGrupo()));
        }

        profesorDiferente = profesorDiferente || (grupoBD.getEmpleadoId() == null && grupo.getEmpleadoId() != null);

        grupo.setProfesoresTitulares(grupoBD.getProfesoresTitulares());
        if(programaGrupoDao.getfechaFinInscripciones(grupo.getId()) != null && grupo.getFechaFinInscripciones() == null) {
            grupo.setFechaFinInscripciones(programaGrupoDao.getfechaFinInscripciones(grupo.getId()));
        }
        if(programaGrupoDao.getfechaFinInscripcionesBecas(grupo.getId()) != null && grupo.getFechaFinInscripcionesBecas() == null) {
            grupo.setFechaFinInscripcionesBecas(programaGrupoDao.getfechaFinInscripcionesBecas(grupo.getId()));
        }
        if(grupo.getId() != null) {
            grupo.setGrupoReferenciaId(programaGrupoDao.findById(grupo.getId()).getGrupoReferenciaId());
        }
        /*if(grupo.getFechaFin().before(grupo.getFechaFinInscripciones())){
            grupo.setFechaFinInscripciones(grupo.getFechaFin());
        }
        if(grupo.getFechaFin().before(grupo.getFechaFinInscripcionesBecas())){
            grupo.setFechaFinInscripcionesBecas(grupo.getFechaFin());
        }*/

        programaGrupoDao.actualizarEvidencias(grupo.getId());

        grupo = programaGrupoDao.save(grupo);

        if(profesorDiferente){
            List<ProgramaGrupoProfesor> profesoresAnteriores = programaGrupoProfesorDao.findAllByGrupoIdAndActivoTrue(grupo.getId());
            for(ProgramaGrupoProfesor profesorTitular : profesoresAnteriores){
                profesorTitular.setActivo(false);
                programaGrupoProfesorDao.save(profesorTitular);
            }

            if(grupo.getEmpleadoId() != null){
                ProgramaGrupoProfesor profesorTitular = new ProgramaGrupoProfesor();
                profesorTitular.setGrupoId(grupo.getId());
                profesorTitular.setEmpleadoId(grupo.getEmpleadoId());
                profesorTitular.setFechaInicio(grupo.getFechaInicio());
                profesorTitular.setSueldo(grupo.getSueldoProfesor());
                profesorTitular.setActivo(true);

                programaGrupoProfesorDao.save(profesorTitular);
            }

        }

        if(temp!=null && temp.size()>0){
            for(Integer i=0;i<temp.size();i++){
                if(temp.get(i).getId() == null){
                    logController.insertaLogUsuario(
                            new Log("Cambio de profesor a: "+empleadoDao.findComboById(grupo.getListadoClases().get(i).getEmpleadoId()).getNombreCompleto(),
                                    LogTipo.MODIFICADO,
                                    LogProceso.CAMBIO_PROFESOR,
                                    grupo.getListadoClases().get(i).getId()
                            ),
                            idUsuario
                    );
                }else if(temp.get(i).getId() != null && temp.get(i).getChangeDetected()){

                    logController.insertaLogUsuario(
                            new Log("Cambio de profesor a: "+empleadoDao.findComboById(grupo.getListadoClases().get(i).getEmpleadoId()).getNombreCompleto(),
                                    LogTipo.MODIFICADO,
                                    LogProceso.CAMBIO_PROFESOR,
                                    grupo.getListadoClases().get(i).getId()
                            ),
                            idUsuario
                    );
                }
            }
        }

        if(grupo.getId() != null){
            for(ProgramaGrupoProfesor profesor : grupo.getProfesoresTitulares()){
                if(profesor.getActivo()){
                    profesores.add(profesor);
                }
            }
            actualizarProfesoresTitulares(grupo.getId(),profesores);
        }

        if(grupo.getId() != null && grupo.getEmpleadoId() != null){
            Integer profesorActualizarId = programaGrupoProfesorDao.getIdByGrupoIdAndEmpleadoIdAndActivoTrueAndActual(grupo.getId(), grupo.getEmpleadoId());
            ProgramaGrupoProfesor profesorActualizar = programaGrupoProfesorDao.findById(profesorActualizarId);
            profesorActualizar.setSueldo(grupo.getSueldoProfesor());
            programaGrupoProfesorDao.save(profesorActualizar);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/multiple", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarMultiple(@RequestBody List<ProgramaGrupo> grupos, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<String> cambios = new ArrayList<>();
        for(ProgramaGrupo grupo: grupos){
            grupo.setFechaCreacion(new Time(System.currentTimeMillis()));
            try {
                if (grupo.getProgramaIdioma() != null && programaIdiomaDao.findComboById(grupo.getProgramaIdioma().getId()).getEsJobs() || programaIdiomaDao.findComboById(grupo.getProgramaIdioma().getId()).getEsJobs()) {
                    grupo.setMultisede(false);
                } else {
                    grupo.setMultisede(true);
                }
            }
            catch (Exception e){
                grupo.setMultisede(true);
            }
            grupo.setEstatusId(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
            grupo.setCreadoPorId(idUsuario);
            if(grupo.getModalidadHorario() !=null){
                grupo.setModalidadHorarioId(grupo.getModalidadHorario().getId());
                grupo.setModalidadHorario(null);
            }
            if(grupo.getPaModalidad()!=null){
                grupo.setPaModalidadId(grupo.getPaModalidad().getId());
                grupo.setPaModalidad(null);
            }
            if(grupo.getPlataforma()!=null){
                grupo.setPlataformaId(grupo.getPlataforma().getId());
                grupo.setPlataforma(null);
            }
            if(grupo.getSucursalPlantel()!=null){
                grupo.setSucursalPlantelId(grupo.getSucursalPlantel().getId());
                grupo.setSucursalPlantel(null);
            }
            if(grupo.getProgramacionAcademicaComercial()!=null){
                grupo.setProgramacionAcademicaComercialId(grupo.getProgramacionAcademicaComercial().getId());
                grupo.setProgramacionAcademicaComercial(null);
            }
            if(grupo.getProgramaIdioma()!=null){
                grupo.setProgramaIdiomaId(grupo.getProgramaIdioma().getId());
                grupo.setProgramaIdioma(null);
            }
            if(grupo.getSucursal()!=null){
                grupo.setSucursalId(grupo.getSucursal().getId());
                grupo.setSucursal(null);
            }
            if(grupo.getTipoGrupo()!=null){
                grupo.setTipoGrupoId(grupo.getTipoGrupo().getId());
                grupo.setTipoGrupo(null);
            }
            if(grupo.getPaCiclo()!=null){
                grupo.setPaCicloId(grupo.getPaCiclo().getId());
                grupo.setPaCiclo(null);
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fechaInicio = df.format(grupo.getFechaInicio());
            Integer ultimoGrupo = programaGrupoDao.getConsecutivo(grupo.getSucursalId(),grupo.getProgramaIdiomaId(),grupo.getPaModalidadId(),grupo.getNivel(),grupo.getSucursalPlantelId() == null ? null : grupo.getSucursalPlantelId(),grupo.getModalidadHorarioId(),fechaInicio);
            if(ultimoGrupo != null && ultimoGrupo >= grupo.getGrupo()){
                cambios.add("El grupo "+grupo.getCodigoGrupo()+", ha sido cambiado del grupo "+grupo.getGrupo()+" al grupo "+Integer.valueOf(ultimoGrupo+1));
                grupo.setGrupo(ultimoGrupo+1);
            }

            if(grupo.getId() == null) {
                ProgramaIdioma programaIdioma = programaIdiomaDao.findById(grupo.getProgramaIdiomaId());
                grupo.setCodigoGrupo(getCodigo(grupo.getSucursalId(), grupo.getSucursalPlantelId(), programaIdioma, grupo.getPaModalidadId(), grupo.getNivel(), grupo.getModalidadHorarioId(), grupo.getGrupo()));
            }
            programaGrupoDao.save(grupo);
            ProgramaIdioma programaIdioma = programaIdiomaDao.findById(grupo.getProgramaIdiomaId());
            if(programaIdioma.getPrograma().getJobs() != null || programaIdioma.getPrograma().getJobsSems() != null || programaIdioma.getPrograma().getPcp() != null){
                grupo.setFechaFinInscripciones(grupo.getFechaFin());
                grupo.setFechaFinInscripcionesBecas(grupo.getFechaFin());
            }
            else{
                grupo.setFechaFinInscripciones(programaGrupoDao.getfechaFinInscripciones(grupo.getId()));
                grupo.setFechaFinInscripcionesBecas(programaGrupoDao.getfechaFinInscripcionesBecas(grupo.getId()));
            }
            grupo = programaGrupoDao.save(grupo);
            if(grupo.getFechaFinInscripcionesBecas() == null){
                grupo.setFechaFinInscripcionesBecas(grupo.getFechaFin());
            }
            if(grupo.getFechaFinInscripciones() == null){
                grupo.setFechaFinInscripciones(grupo.getFechaFin());
            }
            grupo = programaGrupoDao.save(grupo);
        }

        //grupo = programaGrupoDao.save(grupo);

        return new JsonResponse(cambios, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/actualizarFechaFinInscripciones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse actualizarFechaFinInscripciones() throws SQLException {
        int actualizarFechaInscripciones = programaGrupoDao.actualizarFechaFinInscripciones();
        int actualizarFechaInscripcionesBecas = programaGrupoDao.actualizarFechaFinInscripcionesBecas();
        return new JsonResponse(actualizarFechaInscripciones, "Fechas de grupos actualizadas", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getGrupoConsecutivo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupoConsecutivo(@RequestBody HashMap<String,Object> json) throws SQLException {
        Integer sucursalId = Integer.valueOf(json.get("sucursalId").toString());
        Integer cursoId = Integer.valueOf(json.get("cursoId").toString());
        Integer modalidadId = Integer.valueOf(json.get("modalidadId").toString());
        Integer nivel = Integer.valueOf(json.get("nivel").toString());
        Integer plantelId = json.get("plantelId") == null ? null : Integer.valueOf(json.get("plantelId").toString());
        Integer horarioId = Integer.valueOf(json.get("horarioId").toString());
        String fechaInicio = json.get("fechaInicio").toString();
        Integer ultimoGrupo = programaGrupoDao.getConsecutivo(sucursalId,cursoId,modalidadId,nivel,plantelId,horarioId,fechaInicio);
        if(ultimoGrupo == null){
            ultimoGrupo = 0;
        }
        return new JsonResponse(ultimoGrupo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idGrupo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idGrupo) throws SQLException {

        ProgramaGrupoEditarProjection programa = programaGrupoDao.findProjectedEditarById(idGrupo);

        return new JsonResponse(programa, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idCurso}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idCurso, @RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        String fechaCancelacionStr = (String)requestBody.get("fechaCancelacion");

        List<InscripcionBorrarProjection> inscripciones = inscripcionDao.findListadoByGrupoIdAndNoPagadas(hashId.decode(idCurso));
        if(inscripciones !=null && inscripciones.size() !=0){
            for(InscripcionBorrarProjection inscripcion : inscripciones){
                eliminarProyeccion(inscripcion.getCodigoOv(),inscripcion.getCodigoAlumno());
            }
            //return new JsonResponse(null, "No es posible cancelar el grupo ya que cuenta con alumnos inscritos", JsonResponse.STATUS_ERROR);
        }
        ProgramaGrupo grupo = programaGrupoDao.findById(hashId.decode(idCurso));
        grupo.setEstatusId(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.CANCELADO);
        grupo.setFechaCancelacion(DateUtil.parse(fechaCancelacionStr + " 00:00:00.0000000"));
        grupo.setModificadoPorId(idUsuario);
        programaGrupoDao.save(grupo);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramasById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        HashMap<String, Object> json = new HashMap<>();
        List<Log> historial = new ArrayList<>();

        ProgramaGrupoEditarProjection grupo = null;
        String nombrePrograma = "";
        List<InscripcionListadoGrupoProjection> inscripciones = null;
        PAModalidadComboProjection modalidad = null;
        if (id != null) {
            ProgramaGrupo grupoModelo = programaGrupoDao.findById(id);
            grupo = programaGrupoDao.findProjectedEditarById(id);
            modalidad = paModalidadDao.findProjectedComboById(grupo.getPaModalidad().getId());
            Integer programaId = grupo.getProgramaIdioma().getProgramaId();
            Integer modalidadId = grupo.getPaModalidad().getId();
            Integer idiomaId = grupo.getProgramaIdioma().getIdiomaId();
            nombrePrograma = grupo.getProgramaIdioma().getCodigo()+"-"+programaDao.findProjectedEditarById(grupo.getProgramaIdioma().getProgramaId()).getNombre();
            List<ProgramaIdiomaComboProjection> cursos = programaIdiomaDao.findAllByActivoIsTrue();
            json.put("cursos", cursos);
            for(ProgramaGrupoListadoClaseEditarProjection listado: grupo.getListadoClases()){
                historial.addAll(logController.getHistorial(listado.getId(),LogProceso.CAMBIO_PROFESOR));
            }
            inscripciones = inscripcionDao.findInscripcionListadoGrupoProjectionByGrupoIdAndEstatusIdIsNotOrderByAlumnoGrupo(id,ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
            
            List<ControlMaestroMultipleComboSimpleProjection> tiposGrupo;
            if(grupoModelo.getProgramaIdioma().getAgruparListadosPreciosPorTipoGrupo()){
                tiposGrupo = Arrays.asList(controlMaestroMultipleDao.findComboSimpleProjectionById(grupoModelo.getTipoGrupoId()));
            }else{
                tiposGrupo = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
            }
            HashMap<String,List<ProgramaGrupoComboProjection>> gruposParaCambio = new HashMap<>();
            HashMap<String,List<ProgramaGrupoComboProjection>> gruposMultisede = new HashMap<>();

            for(ControlMaestroMultipleComboSimpleProjection tipoGrupo : tiposGrupo){
                gruposParaCambio.put(tipoGrupo.getId().toString(),programaGrupoDao.getPosiblesGruposParaCambio(grupo.getSucursal().getId(),grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),grupo.getPaModalidad().getId(),grupo.getNivel(),tipoGrupo.getId(),grupo.getId()));
                gruposMultisede.put(tipoGrupo.getId().toString(),programaGrupoDao.getPosiblesGruposMultisede(grupo.getSucursal().getId(),grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),grupo.getPaModalidad().getId(),grupo.getNivel(),tipoGrupo.getId(),grupo.getId()));
            }
            
            json.put("fechas", programaGrupoDao.getFechasClaseByGrupoId(grupo.getId()));
            List<ProgramaGrupoActividadProjection> actividades = programaGrupoDao.getGrupoActividades(programaId, grupo.getId(), modalidadId, idiomaId);
            List<InscripcionProjection> alumnos = inscripcionDao.findAllProjectedByGrupoIdAndEstatusIdIsNot(grupo.getId(), 2000512);
            json.put("alumnos", alumnos);
            json.put("faltas", grupo.getFaltasPermitidas());
            json.put("historial",historial);
            json.put("inscripciones", inscripciones);
            json.put("tiposGrupo",tiposGrupo);
            json.put("gruposParaCambio",gruposParaCambio);
            json.put("gruposMultisede",gruposMultisede);
            json.put("modalidad",modalidad);
            List<ListadoProgramaGrupoExamenDetalleProjection> listaExamenDetalle = programaGrupoExamenDetalleDao.findListadoProgramaGrupoExamenDetalleProjection(grupo.getId());
            json.put("listaExamenDetalle",listaExamenDetalle);
            json.put("estatus",programaGrupoDao.getEstatusGrupoById(grupo.getId()));
        }else{
            List<ControlMaestroMultipleComboProjection> plataformas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_Plataforma.NOMBRE);
            List<PAModalidadComboProjection> modalidades = paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre();
            List<PACicloFechaProjection> paCiclos = paCicloDao.findProjectedFechaAllByActivoTrue();
            json.put("plataformas", plataformas);

            json.put("modalidades",modalidades);

            json.put("paCiclos",paCiclos);
        }
        List<ControlMaestroMultipleComboProjection> tipoGrupo = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
        json.put("tipoGrupo",tipoGrupo);
        List<ControlMaestroMultipleComboProjection> formasPago = controlMaestroMultipleDao.findAllByControl("CMM_PROGRULC_FormaPago");
        json.put("formasPago",formasPago);
        List<Integer> empleadosIds = new ArrayList<>();
        empleadosIds.add(2000293);
        empleadosIds.add(2000294);
        List<EmpleadoComboProjection> profesores = empleadoDao.findAllByTipoEmpleadoIdIn(empleadosIds);
        json.put("profesores", profesores);
        json.put("grupo", grupo);
        json.put("permisoMultisede",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_GRUPO_MULTISEDE));
        json.put("permisoSueldo",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_MODIFICAR_SUELDO));
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        json.put("sucursales", sucursales);
        Boolean mostrarBtnCambioProfesor = false;
        if(grupo != null){
            Date fechaActual = new Date();
            mostrarBtnCambioProfesor = grupo.getFechaInicio().getTime() < fechaActual.getTime() && grupo.getSueldoProfesor() != null;
            if(!mostrarBtnCambioProfesor){
                mostrarBtnCambioProfesor = programaGrupoDao.getGrupoTienePrenomina(grupo.getId());
            }
        }
        json.put("mostrarBtnCambioProfesor", mostrarBtnCambioProfesor);
        json.put("permisoBajaGrupo",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_BAJA_GRUPO_JOBS_SEMS));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_LISTADO_GRUPOS_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Programación", "Sucursal","Nombre del Grupo","Nombre del profesor","Horario","Fecha de inicio","Fecha de fin","Idioma","Tipo de grupo","Modalidad","Nivel","Programa","Estatus"};

        excelController.downloadXlsx(response, "grupos", query, alColumnas, null,"Grupos");
    }

    @RequestMapping(value = "/getInscripciones/{idGrupo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getInscripcionesByGrupoId(@PathVariable Integer idGrupo) throws SQLException {
        List<InscripcionListadoGrupoProjection> inscripciones = inscripcionDao.findInscripcionListadoGrupoProjectionByGrupoIdAndEstatusIdIsNotOrderByAlumnoGrupo(idGrupo,ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
        return new JsonResponse(inscripciones, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getSucursales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAllSucursalesGrupos() throws SQLException {
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        return new JsonResponse(sucursales, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosSueldoEmpleado/{idCurso}/{idEmpleado}/{idGrupo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosSueldoEmpleado(@PathVariable Integer idCurso, @PathVariable Integer idEmpleado, @PathVariable Integer idGrupo) throws SQLException {
        TabuladorDetalleEmpleadoProjection datosSueldo = tabuladorDetalleDao.findDatosEmpleadoSueldo(idCurso,idEmpleado,idGrupo);
        return new JsonResponse(datosSueldo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosSueldoEmpleadoIncompany/{idCurso}/{idEmpleado}/{idHorario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosSueldoEmpleadoIncompany(@PathVariable Integer idCurso, @PathVariable Integer idEmpleado, @PathVariable Integer idHorario) throws SQLException {
        TabuladorDetalleEmpleadoProjection datosSueldo = tabuladorDetalleDao.findDatosEmpleadoSueldoIncompany(idCurso,idEmpleado,idHorario);
        return new JsonResponse(datosSueldo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosSueldoEmpleadoDeduccionPercepcion/{idEmpleado}/{idDeduccionPercepcion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosSueldoEmpleadoByDeduccion(@PathVariable Integer idEmpleado, @PathVariable Integer idDeduccionPercepcion) throws SQLException {
        TabuladorDetalleEmpleadoProjection datosSueldo = tabuladorDetalleDao.findDatosEmpleadoSueldoByDeduccionPercepcion(idEmpleado,idDeduccionPercepcion);
        return new JsonResponse(datosSueldo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosAlumnoExamenUbicacion/{idAlumno}/{idGrupo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosAlumnoExamenUbicacion(@PathVariable Integer idAlumno, @PathVariable Integer idGrupo) throws SQLException {
        AlumnoComboProjection alumno = alumnoDao.findAlumnoExamenCertificacion(idAlumno,idGrupo);
        if(alumno != null){
            ProgramaGrupoEditarProjection grupo = programaGrupoDao.findProjectedEditarById(idGrupo);
            String nombrePrograma = grupo.getProgramaIdioma().getCodigo()+"-"+programaDao.findProjectedEditarById(grupo.getProgramaIdioma().getProgramaId()).getNombre();
            List<ProgramaGrupoComboProjection> gruposParaCambio = programaGrupoDao.getPosiblesGruposParaCambioExamenUbicacion(grupo.getSucursal().getNombre(),nombrePrograma,grupo.getProgramaIdioma().getIdioma().getValor(),grupo.getPaModalidad().getNombre(),grupo.getNivel(),grupo.getId());
            List<ProgramaGrupoComboProjection> gruposMultisede = programaGrupoDao.getPosiblesGruposMultisedeExamenUbicacion(nombrePrograma,grupo.getProgramaIdioma().getIdioma().getValor(),grupo.getPaModalidad().getNombre(),grupo.getNivel(),grupo.getId());
            HashMap<String, Object> json = new HashMap<>();
            json.put("gruposParaCambio",gruposParaCambio);
            json.put("gruposMultisede",gruposMultisede);
            return new JsonResponse(json, null, JsonResponse.STATUS_OK);
        }
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/bajaGrupo/{idInscripcion}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse eliminarGrupoByIdInscripcion(@PathVariable Integer idInscripcion, ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Inscripcion inscripcion = inscripcionDao.findById(idInscripcion);

        if(inscripcion.getBecaUDGId() != null){
            return new JsonResponse(null,"No se permite el movimiento en inscripciónes con beca",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.BAJA);
        inscripcion.setModificadoPorId(usuarioId);
        inscripcionDao.save(inscripcion);

        List<Integer> idsEstatus = new ArrayList<>();
        idsEstatus.add(2000674);
        idsEstatus.add(2000675);
        idsEstatus.add(2000676);
        idsEstatus.add(2000677);

        AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoIdAndEstatusIdIsNotIn(inscripcion.getAlumnoId(), inscripcion.getGrupoId(),idsEstatus);
        if(alumnoGrupo != null) {
            alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
            alumnoGrupo.setModificadoPorId(usuarioId);
            alumnoGrupoDao.save(alumnoGrupo);
        }
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/posponerGrupo/{idInscripcion}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse posponerGrupoByIdInscripcion(@PathVariable Integer idInscripcion, ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Inscripcion inscripcion = inscripcionDao.findById(idInscripcion);

        if(inscripcion.getBecaUDGId() != null){
            return new JsonResponse(null,"No se permite el movimiento en inscripciónes con beca",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        ProgramaGrupo grupo = inscripcion.getGrupo();

        InscripcionSinGrupo inscripcionSinGrupo = new InscripcionSinGrupo();
        inscripcionSinGrupo.setOrdenVentaDetalleId(inscripcion.getOrdenVentaDetalleId());
        inscripcionSinGrupo.setAlumnoId(inscripcion.getAlumnoId());
        inscripcionSinGrupo.setSucursalId(grupo.getSucursalId());
        inscripcionSinGrupo.setProgramaId(grupo.getProgramaIdioma().getProgramaId());
        inscripcionSinGrupo.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());
        inscripcionSinGrupo.setPaModalidadId(grupo.getPaModalidadId());
        inscripcionSinGrupo.setPaModalidadHorarioId(grupo.getModalidadHorarioId());
        inscripcionSinGrupo.setNivel(grupo.getNivel());
        inscripcionSinGrupo.setGrupo(grupo.getGrupo());
        inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
        inscripcionSinGrupo.setCreadoPorId(usuarioId);
        inscripcionSinGrupo.setEntregaLibrosPendiente(false);
        inscripcionSinGrupo.setTipoGrupoId(grupo.getTipoGrupoId());
        inscripcionSinGrupo.setBecaUDGId(inscripcion.getBecaUDGId());
        inscripcionSinGrupoDao.save(inscripcionSinGrupo);

        inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
        inscripcion.setModificadoPorId(usuarioId);
        inscripcion.setBecaUDGId(null);
        inscripcionDao.save(inscripcion);

        List<Integer> idsEstatus = new ArrayList<>();
        idsEstatus.add(2000674);
        idsEstatus.add(2000675);
        idsEstatus.add(2000676);
        idsEstatus.add(2000677);

        AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoIdAndEstatusIdIsNotIn(inscripcion.getAlumnoId(), inscripcion.getGrupoId(),idsEstatus);
        alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
        alumnoGrupo.setModificadoPorId(usuarioId);
        alumnoGrupoDao.save(alumnoGrupo);
        
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getProgramacionCiclo/{idSucursal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramacionCiclo(@PathVariable Integer idSucursal) throws SQLException {
        Boolean esJobs = sucursalDao.sucursalEsJobs(idSucursal);
        HashMap<String, Object> json = new HashMap<>();
        json.put("programas",programaIdiomaDao.findCursosBySucursales(idSucursal));
        if(esJobs != null && esJobs){
            json.put("ciclos",paCicloDao.findProjectedComboAllByActivoTrue());
            json.put("fechasGrupos",programaGrupoDao.findDistinctBySucursalId(idSucursal));
            json.put("programacion",new ArrayList<>());
        }else {
            List<ProgramacionAcademicaComercialComboProjection> temp = programacionAcademicaComercialDao.findProjectedComboAllBySucursalId(idSucursal);
            json.put("ciclos",new ArrayList<>());
            json.put("fechasGrupos",new ArrayList<>());
            json.put("programacion",temp);
        }
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechasInicioByCiclo/{idCiclo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasInicioByCiclo(@PathVariable Integer idCiclo) throws SQLException {
        List<ProgramaGrupoFechaProjection> fechas = programaGrupoDao.findAllByPaCicloId(idCiclo);
        return new JsonResponse(fechas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cambiarGrupo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getInscripcionesByGrupoId(@RequestBody HashMap<String,String> json, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer idInscripcion = Integer.valueOf(json.get("idInscripcion"));
        Integer idNuevoGrupo = Integer.valueOf(json.get("idNuevoGrupo"));
        Integer codigoGrupo = Integer.valueOf(json.get("grupoActual"));
        Alerta alerta = alertasDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,idInscripcion, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        if (alerta != null)
            throw new AdvertenciaException("El alumno cuenta con una aprobacion multisede pendiente.");
        ProgramaGrupoEditarProjection grupoNuevo = programaGrupoDao.findProjectedEditarById(idNuevoGrupo);
        List<InscripcionListadoGrupoProjection> inscripciones = inscripcionDao.findListadoGrupoByGrupoIdAndEstatusIdIsNot(idNuevoGrupo,ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
        if(grupoNuevo.getCupo() > inscripciones.size()){
            ProgramaGrupoHistorial historial = new ProgramaGrupoHistorial();
            Inscripcion inscripcion = inscripcionDao.findById(idInscripcion);
            List<AlumnoExamenCalificacionProjection> calificaciones = alumnoExamenCalificacionDao.findAllByGrupoIdAndAlumnoId(inscripcion.getGrupoId(),inscripcion.getAlumnoId());
            List<AlumnoAsistenciaEditarProjection> asistencias = alumnoAsistenciaDao.findAllByGrupoIdAndAlumnoId(inscripcion.getGrupoId(),inscripcion.getAlumnoId());
            for(AlumnoExamenCalificacionProjection calificacion: calificaciones){
                AlumnoExamenCalificacion temp = alumnoExamenCalificacionDao.findById(calificacion.getId());
                temp.setGrupoId(idNuevoGrupo);
                alumnoExamenCalificacionDao.save(temp);
            }
            for(AlumnoAsistenciaEditarProjection asistencia : asistencias){
                AlumnoAsistencia temp= alumnoAsistenciaDao.findById(asistencia.getId());
                temp.setGrupoId(idNuevoGrupo);
                alumnoAsistenciaDao.save(temp);
            }
            historial.setGrupoAnteriorId(inscripcion.getGrupoId());
            //Actualizar grupo id del modelo Alumno Grupo
            List<Integer> idsEstatus = new ArrayList<>();
            idsEstatus.add(2000674);
            idsEstatus.add(2000675);
            idsEstatus.add(2000676);
            idsEstatus.add(2000677);
            AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoIdAndEstatusIdIsNotIn(inscripcion.getAlumnoId(),inscripcion.getGrupoId(),idsEstatus);
            alumnoGrupo.setGrupoId(idNuevoGrupo);
            alumnoGrupoDao.save(alumnoGrupo);
            inscripcion.setGrupoId(idNuevoGrupo);
            inscripcion = inscripcionDao.save(inscripcion);
            //Se asume que si hubo un cambio de grupo es por que es multisede
            if (inscripcion.getEstatusId().equals(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA)){
                ProgramaGrupo grupoAnterior = programaGrupoDao.findById(codigoGrupo);
                ProgramaGrupo grupoCambio = programaGrupoDao.findById(idNuevoGrupo);
                if (grupoAnterior.getSucursalId() != grupoCambio.getSucursalId()){
                    Alumno alumno = alumnoDao.findById(inscripcion.getAlumnoId());
                    //Iniciar alerta
                    String mensaje = "Alumno: "+alumno.getCodigo()+"\n Grupo: "+grupoCambio.getCodigoGrupo();
                    procesadorAlertasService.validarAutorizacion(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,inscripcion.getId(), inscripcion.getCodigo(), "Inscripción Multisede", grupoCambio.getSucursalId(), idUsuario, mensaje);
                }
            }
            historial.setActivo(true);
            historial.setComentario(json.get("comentario"));
            historial.setCreadoPorId(idUsuario);
            historial.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            historial.setInscripcionId(idInscripcion);
            historial.setGrupoNuevoId(idNuevoGrupo);
            programaGrupoHistorialDao.save(historial);
            //Boolean seCreoAlerta = programasGruposAlertaService.createAlerta(idInscripcion,idNuevoGrupo,idUsuario,codigoGrupo);
            return new JsonResponse(null, null, JsonResponse.STATUS_OK);
        }else
            throw new AdvertenciaException("El grupo ya tiene cupo completo");
    }

    /** Template **/
    @GetMapping("/template/download/{tipo}")
    public void templateDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo) throws IOException, NoSuchFieldException, IllegalAccessException, Exception {
        Usuario usuario = usuarioDao.findById(JwtFilter.getUsuarioId((HttpServletRequest) req));
        List<Integer> sedes = usuario.getAlmacenes().stream().map((almacen) -> { return almacen.getSucursalId();}).collect(Collectors.toList());
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
        //Query general data
        List<ProgramaGrupoListadoProjection> grupos = new ArrayList<>();
        if(tipo.equals("JOBS"))
            grupos.addAll(programaGrupoDao.findProjectedAllBySucursalInAndEsJobs(sedes));
        else if(tipo.equals("SEMS"))
            grupos.addAll(programaGrupoDao.findProjectedAllBySucursalInAndEsJobsSems(sedes));
        else if(tipo.equals("PCP"))
            grupos.addAll(programaGrupoDao.findProjectedAllBySucursalInAndEsPCP(sedes));

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
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999),IgnoredErrorType.NUMBER_STORED_AS_TEXT );

        //Create hidden sheet for lists
        SXSSFSheet hidden = workbook.createSheet("hidden");
        List<List<String>> listados = new ArrayList<>();
        if (tipo.equals("JOBS") || tipo.equals("SEMS")){
            List<String> centros = new ArrayList<>();
            List<String> carreras = new ArrayList<>();
            if (tipo.equals("JOBS")){
                centros.addAll(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_CentrosUniversitarios.NOMBRE, true));
                //carreras.addAll(CMMtoList(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE, false));
                List<ControlMaestroMultipleComboProjection> carr = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE);
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
            sheet.setColumnWidth(i,4000);
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

    @RequestMapping(value = "/template/apertura/download", method = RequestMethod.POST)
    public void getCustomTemplate(ServletRequest req, HttpServletResponse response, @RequestBody JSONObject json) throws IOException, NoSuchFieldException, IllegalAccessException, Exception {
        if (json.getAsString("pass").equals("dGhlY2FrZWlzYWxpZQ==")){
            List<Integer> gruposIds = ((ArrayList<Integer>) json.get("grupos"));
            //Create worbook
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            //Create styles
            CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
            CellStyle detail = createStyle(workbook, null, null, false, false);
            //Create main sheet
            SXSSFSheet sheet = workbook.createSheet("dGhlY2FrZWlzYWxpZQ==");
            String[] ht = {"ID","PRECIO","CODIGO_GRUPO","CICLO_ESCOLAR","PLANTEL","NIVEL","GRUPO","MODALIDAD","HORARIO",
                    "CODIGO_PLX","PRIMER_APELLIDO","SEGUNDO_APELLIDO","NOMBRE","TELEFONO_CASA","TELEFONO_CELULAR",
                    "CORREO_ELECTRÓNICO","FECHA_NACIMIENTO","GENERO"};
            //Create header row
            createRow(sheet, 0, ht, header);
            //Query general data
            List<ProgramaGrupoListadoProjection> grupos = programaGrupoDao.findProjectedAllByIdIn(gruposIds);
            Moneda moneda = monedaDao.findByPredeterminadaTrue();
            //Sets initial row as 1 to avoid header
            Integer index = 1;
            for (ProgramaGrupoListadoProjection grupo : grupos){
                ProgramaGrupo programaGrupo = programaGrupoDao.findById(grupo.getId());
                List<InscripcionListadoGrupoProjection> inscripciones = inscripcionDao.findListadoGrupoByGrupoIdAndEstatusIdIsNot(grupo.getId(), ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
                Sucursal sucursal = programaGrupo.getSucursal();
                SucursalPlantel plantel = programaGrupo.getSucursalPlantel();
                ArticuloEditarProjection articulo = articuloDao.findProjectedByProgramaIdiomaIdAndPaModalidadIdAndTipoGrupoId(programaGrupo.getProgramaIdiomaId(), programaGrupo.getPaModalidadId(),programaGrupo.getTipoGrupoId());
                List<ProgramaGrupoCustomProjection> alumnos = programaGrupoDao.findAllProjectedCustomByCodigoGrupo(grupo.getCodigo());
                if (articulo != null){
                    if (sucursal.getListadoPrecio() == null){
                        PrintWriter out = response.getWriter();
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.print("La sede "+sucursal.getCodigoSucursal()+" no cuenta con lista de precios asociada.");
                        out.flush();
                        return;
                    }
                    ListadoPrecioDetalleEditarProjection precio = listadoPrecioDetalleDao.findFirstProjectedByArticuloIdAndListadoPrecioId(articulo.getId(), sucursal.getListadoPrecio().getId());
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

                    if(alumnos.isEmpty()){
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

                            index++;
                        }
                    } else {
                        for (ProgramaGrupoCustomProjection alumno : alumnos){
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
                            createCell(row,9,alumno.getCodigoAlumno(),detail);
                            index++;
                        }
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
            xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999),IgnoredErrorType.NUMBER_STORED_AS_TEXT );

            //Create hidden sheet for lists
            SXSSFSheet hidden = workbook.createSheet("hidden");
            List<List<String>> listados = new ArrayList<>();
            listados.add(CMMtoList(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE, false));//Generos

            Integer max = Collections.max(listados.stream().map(item -> item.size()).collect(Collectors.toList()));

            for (Integer j = 0; j < max; j++){//Rows
                SXSSFRow row = hidden.createRow(j);
                for (Integer i = 0; i < listados.size(); i++){//Columns
                    if ( j < listados.get(i).size()) { createCell(row, i, listados.get(i).get(j)); }
                }
            }
            //Add Datavalidations to simulate dropdowns
            Integer v = 0;
            for (Integer col : new Integer[]{17}){
                if (listados.get(v).size() > 0){

                    XSSFDataValidation dropdown = createDropdown(workbook,new Integer[] {v,listados.get(v).size()},col);
                    sheet.addValidationData(dropdown);
                }
                v++;
            }

            for (Integer i = 0; i < ht.length; i++){
                sheet.setColumnWidth(i,4000);
            }

            workbook.setSheetHidden(1, true);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

            String fecha = new SimpleDateFormat("yyyy-MM-dd_HHmm").format(new Date());

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Apertura_"+fecha+".xlsx");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            IOUtils.copy(stream, response.getOutputStream());
        }
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

    @GetMapping("/alumnos/download/{tipo}")
    public void templateAlumnosDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo ) throws IOException, Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        //Recuperar todos los alumnos con inscripción pendiente de pago
        List<AlumnoOrdenPagoProjection> alumnos = new ArrayList<>();
        if(tipo.equals("SEMS"))
            alumnos = alumnoDao.findAllProjectedOrdenPagoBy();
        if(tipo.equals("JOBS"))
            alumnos = alumnoDao.findAllProjectedOrdenPagoJOBSBy();

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);

        SXSSFSheet sheet = workbook.createSheet("Alumnos");
        if (tipo.equals("SEMS")){
            String[] ht = {"CODALU","DIGVER","CODALM2","NOMALU","PATERNO","MATERNO","GENERO","TELAL","FECHANAC","EMAIL","CODCUR","FECHINI","FECHFIN","NIVEL","HORARIO"};
            createRow(sheet, 0, ht, header);
        }
        if (tipo.equals("JOBS")){
            String[] ht = {"CODALU","DIGVER","CODALM2","NOMALU","PATERNO","MATERNO","GENERO","TELAL","FECHANAC","EMAIL","PLANTEL","CODCUR","DESCRIPCION","FECHINI","FECHFIN","NIVEL","HORARIO"};
            createRow(sheet, 0, ht, header);
        }

        Integer index = 1;
        for (AlumnoOrdenPagoProjection alumno : alumnos){

            SXSSFRow row = sheet.createRow(index);
            createCell(row,0 , alumno.getCodigoAlumno(), detail);
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
            }


            index++;
        }

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999),IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < 16; i++){ sheet.setColumnWidth(i,4000); }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Alumnos "+tipo+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/profesores/download/{tipo}")
    public void templateProfesoresDownload(ServletRequest req, HttpServletResponse response, @PathVariable("tipo") String tipo) throws IOException, IllegalAccessException, NoSuchFieldException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        List<Integer> sedes = new ArrayList<>();
        for (Almacen almacen : usuario.getAlmacenes()){ sedes.add(almacen.getSucursalId()); }
        //Create worbook
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //Create styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        //Create main sheet
        SXSSFSheet sheet = workbook.createSheet("Profesores");
        String[] ht = {"ID","CODIGO_GRUPO","CICLO_ESCOLAR","PLANTEL","NIVEL","GRUPO","MODALIDAD","HORARIO","PROFESOR"};
        //Create header row
        createRow(sheet, 0, ht, header);
        //Query general data
        List<ProgramaGrupoListadoProjection> grupos = new ArrayList<>();
        if(tipo.equals("JOBS"))
            grupos.addAll(programaGrupoDao.findProjectedAllBySucursalInAndEsJobs(sedes));
        else if(tipo.equals("SEMS"))
            grupos.addAll(programaGrupoDao.findProjectedAllBySucursalInAndEsJobsSems(sedes));
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
            sheet.setColumnWidth(i,4000);
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

    /*@Scheduled(cron = "0 0 0,5 * * *")*/
    @RequestMapping(value = "/actualizar-estatus", method = RequestMethod.GET)
    public void actualizarEstatus() {

        List<Integer> idsActualizar = programaGrupoDao.findIdAllByActivoAndFechaFinToleranciaExcedida();
        List<ProgramaGrupo> gruposActualizar = programaGrupoDao.findAllByIdIn(idsActualizar);
        List<Integer> estatus = new ArrayList<>();
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.DESERTOR);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.SIN_DERECHO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);

        for(ProgramaGrupo grupo : gruposActualizar){
            grupo.setEstatusId(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO);
            programaGrupoDao.save(grupo);
            //Get all students that can be evaluated by group
            List<AlumnoGrupo> alumnos = alumnoGrupoDao.findAllByGrupoIdAndEstatusIdNotIn(grupo.getId(), estatus);
            for (AlumnoGrupo alumno : alumnos){
                AlumnoExamenCalificacionResumenProjection reg = alumnoExamenCalificacionDao.getCalificacionFinalByAlumnoIdAndGrupoId(alumno.getAlumnoId(), alumno.getGrupoId());
                if(reg != null && reg.getCalificacion().compareTo(new BigDecimal(grupo.getCalificacionMinima())) >= 0)
                    alumno.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO);
                else
                    alumno.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO);
                alumno.setCalificacionFinal(reg.getCalificacion());
                Boolean esSEMS = grupo.getProgramaIdioma().getPrograma().getJobsSems();
                if (esSEMS != null && esSEMS)
                    alumno.setCalificacionConvertida(getCalificacionConvertida(reg.getCalificacion()));
                alumnoGrupoDao.save(alumno);
            }
        }

    }

    @Scheduled(cron = "0 0 4 * * *")
    @RequestMapping(value = "/alumnos-referencias", method = RequestMethod.GET)
    public void setReferenciasAlumnos(){
        List<Alumno> alumnos = new ArrayList<>();
        alumnos = (List<Alumno>) alumnoDao.findAll();
        for(Alumno alumno : alumnos){
            if (alumno.getReferencia() == null || alumno.getReferencia().trim().length() == 0){
                String codigoAlumno = alumno.getCodigo();
                try {
                    alumno.setReferencia(codigoAlumno + getDigito(codigoAlumno).toString());
                } catch (Exception e) { e.printStackTrace(); }
                alumnoDao.save(alumno);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping(value = "/cerrar/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse cerrarGrupo(@PathVariable("grupoId") Integer grupoId, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        //Buscar el grupo a finalizar
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        //Si el grupo existe y no ha finalizado
        if (grupo != null && grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO)){
            throw new AdvertenciaException("El grupo ya ha finalizado");
        } else {
            Integer estatusAnterior = grupo.getEstatusId();
            try{
                grupo.setEstatusId(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO);
                //Buscar todos los alumnos 'activos' del grupo
                List<Integer> estatusFinales = new ArrayList<>();
                estatusFinales.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
                List<AlumnoGrupo> alumnos = alumnoGrupoDao.findAllByGrupoIdAndEstatusIdNotIn(grupo.getId(), estatusFinales);
                //Evaluar a cada alumno
                for (AlumnoGrupo alumno : alumnos){
                    //Obtener su calificación final
                    //AlumnoExamenCalificacionResumenProjection reg = alumnoExamenCalificacionDao.getCalificacionFinalByAlumnoIdAndGrupoId(alumno.getAlumnoId(), alumno.getGrupoId());
                    alumno.setCalificacionFinal(alumno.getCalificacionFinal() != null? alumno.getCalificacionFinal() : BigDecimal.ZERO);
                    Integer nuevoEstatus = evaluarAlumnoGrupo(alumno, grupo);
                    alumno.setEstatusId(nuevoEstatus);
                    alumno.setModificadoPorId(usuarioId);
                    becaUDGDao.finalizarBecaRHByInscripcionId(alumno.getInscripcionId());
                }
                alumnoGrupoDao.saveAll(alumnos);
            }catch(Exception e){
                grupo.setEstatusId(estatusAnterior);
                throw new AdvertenciaException(e.getMessage());
            }
        }
        grupo.setModificadoPorId(usuarioId);
        grupo = programaGrupoDao.save(grupo);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/filtros/cursos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltrosIdiomas(@RequestBody HashMap<String,List<Integer>> requestBody, ServletRequest req) throws SQLException {
        List<Integer> sucursalesIds = requestBody.get("sucursalesIds");

        List<ProgramaIdiomaComboSimpleProjection> cursos = programaIdiomaDao.findProjectedComboSimpleAllBySucursalIdIn(sucursalesIds);

        HashMap<String, Object> json = new HashMap<>();
        json.put("cursos",cursos);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/filtros/modalidades/niveles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltrosModalidadesNiveles(@RequestBody HashMap<String,List<Integer>> requestBody, ServletRequest req) throws SQLException {
        List<Integer> cursosIds = requestBody.get("cursosIds");

        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findProjectedComboSimpleAllByProgramaIdiomaIdIn(cursosIds);
        List<HashMap<String,Integer>> niveles = new ArrayList<>();
        Integer nivelesMax = programaIdiomaDao.getMaxNivelByIdIn(cursosIds);
        for(int nivel = 1 ; nivel <= nivelesMax ; nivel++){
            HashMap<String,Integer> nivelJson = new HashMap<>();
            nivelJson.put("id",nivel);
            nivelJson.put("nombre",nivel);
            niveles.add(nivelJson);
        }

        HashMap<String, Object> json = new HashMap<>();
        json.put("modalidades",modalidades);
        json.put("niveles",niveles);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/filtros/horarios/{modalidadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltrosHorarios(@PathVariable Integer modalidadId, ServletRequest req) throws SQLException {
        List<PAModalidadHorarioComboSimpleProjection> horarios = paModalidadHorarioDao.findProjectedComboSimpleAllByModalidadIdOrderByCodigo(modalidadId);

        HashMap<String, Object> json = new HashMap<>();
        json.put("horarios",horarios);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    /** Alertas **/
    @RequestMapping(value = "/alerta/{inscripcionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDetalleAlertaInscripcion(@PathVariable Integer inscripcionId, ServletRequest req) throws  SQLException {
        HashMap<String, Object> json = new HashMap<>();
        InscripcionProjection inscripcion = inscripcionDao.findProjectedById(inscripcionId);
        OrdenVenta ov = ordenVentaDao.findByDetalleId(inscripcion.getOrdenVentaDetalleId());
        ProgramaGrupoCapturaEditarProjection grupo = programaGrupoDao.findProjectedCapturaEditarById(inscripcion.getGrupoId());
        json.put("inscripcion", inscripcion);
        json.put("grupo", grupo);
        json.put("nota", ov.getCodigo());
        json.put("fecha", ov.getFechaOV());
        json.put("sede", ov.getSucursal().getNombre());
        json.put("usuario", ov.getCreadoPor().getNombreCompleto());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/multisede/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse setAlertaMutisedeAprobada(@RequestBody Integer id, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Alerta alerta = alertasDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,id, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                    procesadorAlertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, true, "");
                    break;
                }
            }
        } else
            return new JsonResponse(null, "No se ha encontrado una alerta relacionada", JsonResponse.STATUS_ERROR);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/multisede/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse setAlertaMutisedeRechazada(@RequestBody HashMap<String, Object> body, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");

        Inscripcion inscripcion = inscripcionDao.findById(id);

        if (inscripcion.getEstatusId().equals(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA)){
            inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
            inscripcionDao.save(inscripcion);
            AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findAllByInscripcionId(id);
            alumnoGrupoDao.delete(alumnoGrupo);

            Alerta alerta = alertasDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,id, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
            if(alerta != null){
                for ( AlertaDetalle detalle : alerta.getDetalles() ){
                    if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                        procesadorAlertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, false, comentario);
                        break;
                    }
                }
            } else
                return new JsonResponse(null, "No se ha encontrado una alerta relacionada", JsonResponse.STATUS_ERROR);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
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

    private String getCodigo(Integer sucursalId, Integer plantelId, ProgramaIdioma programaIdioma, Integer modalidadId, Integer nivel, Integer horarioId, Integer grupo){

        Sucursal sucursal = sucursalDao.findById(sucursalId);
        SucursalPlantel sucursalPlantel = plantelId != null ? sucursalPlantelDao.findById(plantelId) : null;
        Programa programa = programaIdioma.getPrograma();
        ControlMaestroMultiple idioma = programaIdioma.getIdioma();
        PAModalidad modalidad = paModalidadDao.findById(modalidadId);
        PAModalidadHorario horario = paModalidadHorarioDao.findById(horarioId);

        String codigo = sucursal.getCodigoSucursal().length() >= 3 ? sucursal.getCodigoSucursal().substring(0,3) : sucursal.getCodigoSucursal();
        codigo += (sucursalPlantel != null ? sucursalPlantel.getCodigoSucursal() : "");
        if (programa.getAcademy())
            codigo += programaIdioma.getCodigo();
        else {
            codigo += (programa.getCodigo().length() >= 3 ? programa.getCodigo().substring(0,3) : programa.getCodigo());
            codigo += (idioma.getReferencia().length() >= 3 ? idioma.getReferencia().substring(0,3) : idioma.getReferencia());
        }
        codigo += (modalidad.getCodigo().length() >= 3 ? modalidad.getCodigo().substring(0,3) : modalidad.getCodigo());
        String n = "00" + nivel.toString();
        codigo += (n.substring(n.length() - 2, n.length()));
        codigo += horario.getCodigo();
        String g = "00" + grupo.toString();
        codigo += (g.substring(g.length() - 2, g.length()));

        return codigo;
    }

    private void actualizarProfesoresTitulares(Integer grupoId, List<ProgramaGrupoProfesor> profesores){
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);

        List<ProgramaGrupoProfesor> profesoresNuevos = new ArrayList<>();

        for(ProgramaGrupoProfesor profesor : profesores){
            if(profesor.getId() == null){
                profesoresNuevos.add(profesor);
            }
        }
        for(ProgramaGrupoProfesor profesor : profesoresNuevos){
            List<ProgramaGrupoProfesor> profesoresBorrar = programaGrupoProfesorDao.findAllByGrupoIdAndFechaInicioGreaterThanEqualAndActivoTrue(grupoId,profesor.getFechaInicio());
            for(ProgramaGrupoProfesor profesorBorrar : profesoresBorrar){
                profesorBorrar.setActivo(false);
                programaGrupoProfesorDao.save(profesorBorrar);
            }
        }

        for(ProgramaGrupoProfesor profesorNuevo : profesoresNuevos){
            profesorNuevo.setGrupoId(grupoId);
            profesorNuevo.setEmpleadoId(profesorNuevo.getEmpleado().getId());
            profesorNuevo.setActivo(true);
            programaGrupoProfesorDao.save(profesorNuevo);
        }
    }

    private BigDecimal getCalificacionConvertida(BigDecimal calificacion){
        if(calificacion == null)
            return new BigDecimal(10);
        if(calificacion.compareTo(new BigDecimal(55)) == 1)
            return new BigDecimal(100).subtract(new BigDecimal(2).multiply(new BigDecimal(100).subtract(calificacion))).setScale(2, BigDecimal.ROUND_HALF_UP);
        else
            return new BigDecimal(10);
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
    /*
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void relacionarAlumnoGrupo(Alumno alumno, ProgramaGrupo grupo, Integer origenId, Boolean isApertura) throws Exception {
        Integer usuarioId = alumno.getModificadoPorId() != null? alumno.getModificadoPorId() : alumno.getCreadoPorId();
        Integer estatusId = grupo.getEstatusId() == null ? grupo.getEstatus().getId() : grupo.getEstatusId();
        if(!estatusId.equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO))
            throw new Exception("El grupo " + grupo.getCodigoGrupo() + " no se encuentra activo.");
        Moneda moneda = monedaDao.findByPredeterminadaTrue();
        ArticuloEditarProjection articulo = articuloDao.findProjectedByIdiomaIdAndPaModalidadId(grupo.getProgramaIdiomaId(), grupo.getPaModalidadId());
        Sucursal sucursal = grupo.getSucursal();
        if (articulo == null)
            throw new Exception("El grupo " + grupo.getCodigoGrupo() + " no cuenta con un artículo relacionado.");

        ListadoPrecioDetalleEditarProjection lpv = listadoPrecioDetalleDao.findProjectedByArticuloIdAndListadoPrecioId(articulo.getId(), sucursal.getListadoPrecio().getId());
        if (lpv == null)
            throw new Exception("El grupo " + grupo.getCodigoGrupo() + " no cuenta con una relación en la lista de precios.");

        Integer programaId = grupo.getProgramaIdioma().getProgramaId();
        Integer idiomaId = grupo.getProgramaIdioma().getIdiomaId();
        Integer nivel = grupo.getNivel();

        Integer cupoDisponible = programaGrupoDao.getCupoDisponible(grupo.getId());
        if(cupoDisponible.intValue() == 0)
            throw new Exception("El grupo" + grupo.getCodigoGrupo() + "no cuenta con cupo disponible");

        String codigoAlumno = alumno.getCodigo();
        if(codigoAlumno != null){
            alumno.setReferencia(codigoAlumno + getDigito(codigoAlumno).toString());
        }
        alumno = alumnoDao.save(alumno);

        //Validate last course taken
        //Integer nivelPermiteInscripcion = alumnoDao.getNivelAlumnoPermiteInscripcion(alumno.getId(),idiomaId,programaId);
        //if(grupo.getNivel() > nivelPermiteInscripcion)
        //    throw new Exception("No es posible inscribir al alumno " + alumno.getCodigo() + " al nivel " + grupo.getNivel() + ". (Nivel máximo: " + nivelPermiteInscripcion.toString() + ")");
        //Validate duplicates on language and schedule

        List<Inscripcion> colisiones = inscripcionDao.findAllByInterferenciaGrupo(grupo.getId(), alumno.getId());
        if (colisiones != null && colisiones.size() > 0)
            throw new Exception("El alumno " + alumno.getCodigo() + " ya cuenta con una inscripción para el mismo Idioma u horario.");
        //Validate requirements of location exam
        AlumnoExamenCertificacion aec = alumnoExamenCertificacionDao.findByAlumnoIdAndEstatusIdAndTipoId(alumno.getId(),
                ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO,
                ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN);
        if (aec != null && aec.getNivel() < grupo.getNivel())
            throw new Exception("El alumno " + alumno.getCodigo() + " no es candidato para el nivel actual.");
        //When all validations are accepted, create OV's array
        OrdenVenta ov = new OrdenVenta();
        String anio = DateUtil.getFecha(new Date(),"yyyy");
        //ov.setCodigo(ordenVentaDao.getSiguienteCodigoOV(sucursal.getPrefijo()+anio));
        //ov.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("OV"));
        ov.setCodigo(UUID.randomUUID().toString());
        ov.setSucursalId(sucursal.getId());
        ov.setFechaOV(new Date());
        ov.setFechaRequerida(new Date());
        ov.setMonedaId(moneda.getId());
        ov.setDiazCredito(0);

        ov.setCreadoPorId(usuarioId);

        if (isApertura){
            ov.setMedioPagoPVId(1);
            ov.setMetodoPagoId(ControlesMaestrosMultiples.CMM_OV_MetodoPago.PUE);
            ov.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
            ov.setMonto(BigDecimal.ZERO);
        } else {
            ov.setMedioPagoPVId(null);
            ov.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.ABIERTA);
        }

        List<OrdenVentaDetalle> detalles = new ArrayList<>();

        OrdenVentaDetalle parent = new OrdenVentaDetalle();
        parent.setArticuloId(articulo.getId());
        parent.setUnidadMedidaId(articulo.getUnidadMedidaInventarioId());
        parent.setFactorConversion(BigDecimal.ONE);
        parent.setCantidad(BigDecimal.ONE);
        parent.setPrecio(isApertura ? BigDecimal.ZERO : lpv.getPrecio());
        parent.setAlumnoId(alumno.getId());
        parent.setCreadoPorId(usuarioId);
        detalles.add(parent);

        ov.setDetalles(detalles);
        ov = ordenVentaDao.save(ov);

        //Find and add extra details related to main product
        //TODO: Optimize this code to get details easier and faster
        //Hint: All ov details related to same grupoId are similar
        List<OrdenVentaDetalle> det = new ArrayList<>();
        det.addAll(ov.getDetalles());
        List<OrdenVentaDetalle> desglosados = ov.getDetalles();
        for (OrdenVentaDetalle ovd : det) {
            if (ovd.getAlumnoId() != null) {
                Articulo articuloOVD = articuloDao.findById(ovd.getArticuloId());

                BigDecimal totalDescontar = BigDecimal.ZERO;
                List<Articulo> articulos = articuloDao.findLibrosByCurso(programaId, idiomaId, nivel, alumno.getCarreraJOBSId());
                articulos.addAll(articuloDao.findCertificacionesByCurso(programaId, idiomaId));
                for (Articulo art : articulos) {
                    BigDecimal precioVenta = articuloDao.findPrecioVenta(art.getId(), articuloOVD.getId(), lpv.getListadoPrecioId());
                    precioVenta = precioVenta == null ? BigDecimal.ZERO : precioVenta;
                    totalDescontar = totalDescontar.add(precioVenta);
                    BigDecimal tasaIva = ((art.getIvaExento() != null && art.getIvaExento()) || art.getIva() == null) ? BigDecimal.ZERO : art.getIva();
                    BigDecimal tasaIeps = ((art.getIepsCuotaFija() != null && art.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || art.getIeps() == null) ? BigDecimal.ZERO : art.getIeps();

                    OrdenVentaDetalle children = new OrdenVentaDetalle();
                    children.setArticuloId(art.getId());
                    children.setUnidadMedidaId(art.getUnidadMedidaConversionVentasId() != null ? art.getUnidadMedidaConversionVentasId() : art.getUnidadMedidaInventarioId());
                    children.setFactorConversion((art.getUnidadMedidaConversionVentasId() != null && art.getFactorConversionVentas() != null) ? art.getFactorConversionVentas() : BigDecimal.ONE);
                    children.setCantidad(BigDecimal.ONE);
                    children.setPrecio(isApertura? BigDecimal.ZERO : getPrecioUnitario(precioVenta, art));
                    children.setDescuento(ovd.getDescuento());
                    children.setIva(tasaIva);
                    children.setIvaExento(art.getIvaExento() != null ? art.getIvaExento() : false);
                    children.setIeps(tasaIeps);
                    children.setIepsCuotaFija(art.getIepsCuotaFija());
                    children.setDetallePadreId(ovd.getId());
                    children.setCreadoPorId(usuarioId);

                    desglosados.add(children);
                }

                BigDecimal precioVentaOVD = articuloDao.findPrecioVenta(articuloOVD.getId(), articuloOVD.getId(), lpv.getListadoPrecioId());
                precioVentaOVD = precioVentaOVD == null ? BigDecimal.ZERO : precioVentaOVD;
                precioVentaOVD = precioVentaOVD.subtract(totalDescontar);
                ovd.setPrecio(getPrecioUnitario(precioVentaOVD, articuloOVD));
            }
        }
        ov.setDetalles(desglosados);
        ov = ordenVentaDao.save(ov);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("INS"));
        inscripcion.setOrdenVentaDetalleId(ov.getDetalles().get(0).getId());
        inscripcion.setAlumnoId(alumno.getId());
        inscripcion.setGrupoId(grupo.getId());
        inscripcion.setOrigenId(origenId);
        if (isApertura)
            inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
        else
            inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO);
        inscripcion.setEntregaLibrosPendiente(false);
        if (alumno.getCentroUniversitarioJOBSId() != null) {
            inscripcion.setInstitucionAcademicaId(alumno.getCentroUniversitarioJOBSId());
            inscripcion.setCarreraId(alumno.getCarreraJOBSId());
        } else {
            inscripcion.setInstitucionAcademicaId(alumno.getPreparatoriaJOBSId());
            inscripcion.setCarreraTexto(alumno.getBachilleratoTecnologico());
            inscripcion.setTurnoId(alumno.getTurnoId());
            inscripcion.setGradoId(alumno.getGradoId());
            inscripcion.setGrupoTexto(alumno.getGrupo());
        }
        inscripcion.setCreadoPorId(usuarioId);

        try {
            inscripcion = inscripcionDao.save(inscripcion);
        } catch (Exception e) {
            throw new Exception("El alumno " + alumno.getCodigo() + " cuenta con una inscripción pendiente de pago.");
        }

        AlumnoGrupo alumnoGrupo = new AlumnoGrupo();

        alumnoGrupo.setAlumnoId(alumno.getId());
        alumnoGrupo.setGrupoId(grupo.getId());
        alumnoGrupo.setAsistencias(0);
        alumnoGrupo.setFaltas(0);
        alumnoGrupo.setMinutosRetardo(0);
        alumnoGrupo.setCreadoPorId(alumno.getCreadoPorId());
        alumnoGrupo.setCreadoPorId(usuarioId);
        alumnoGrupo.setInscripcionId(inscripcion.getId());

        if (isApertura){
            alumnoGrupo.setCalificacionFinal(new BigDecimal(90.00));
            alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO);
        } else {
            alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REGISTRADO);
        }

        alumnoGrupoDao.save(alumnoGrupo);
    }
    */
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

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void eliminarProyeccion(String codigoOV, String codigoAlumno) throws Exception {
        //Integer usuarioId = alumno.getModificadoPorId() != null? alumno.getModificadoPorId() : alumno.getCreadoPorId();
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_EliminarProyeccionGrupoCancelado");
        query.registerStoredProcedureParameter( 1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 2, String.class, ParameterMode.IN);
        query.setParameter(1,codigoOV);
        query.setParameter(2,codigoAlumno);
        query.executeUpdate();
        /*String feedback = (String) query.getOutputParameterValue(3);
        if (feedback != null)
            throw new Exception(feedback);*/
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse test(@RequestBody HashMap<String,Integer> requestBody, ServletRequest req) {
        Integer alumnoId = (Integer) requestBody.get("alumnoId");
        Integer grupoId  = (Integer) requestBody.get("grupoId");

        Alumno alumno = alumnoDao.findById(alumnoId);
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        try{
            relacionarAlumnoGrupo(alumno,grupo,ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PLANTILLA);
        } catch (Exception e){
            return new JsonResponse(null, e.getMessage(), JsonResponse.STATUS_OK);
        }
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/exportar/resumen/pdf/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public JsonResponse exportarResumenPDF(@PathVariable("id") Integer id, HttpServletResponse response, ServletRequest req) throws Exception {

        String path = environment.getProperty("environments.pixvs.front.url");
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        UsuarioEditarProjection usuario = usuarioDao.findProjectedById(usuarioId);
        ProgramaGrupo grupo = programaGrupoDao.findById(id);

        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        params.put("grupoId", id);
        params.put("usuario", usuario.getNombreCompleto());

        InputStream reporte = reporteService.generarJasperReport("/modulos/programacion-academica/Grupos.jasper", params, ReporteServiceImpl.output.PDF, true);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+grupo.getCodigoGrupo()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        org.apache.tomcat.util.http.fileupload.IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/cancelar-profesor-sustituto", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse cancelarProfesorSustituto(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer programaGrupoListadoClaseId = (Integer)requestBody.get("programaGrupoListadoClaseId");

        ProgramaGrupoListadoClase programaGrupoListadoClase = programaGrupoListadoClaseDao.findById(programaGrupoListadoClaseId);

        if(programaGrupoListadoClase.getFechaDeduccion() != null || programaGrupoListadoClase.getFechaPago() != null){
            return new JsonResponse(null, "No es posible cancelar sustituciones que ya fueron procesadas en una prenómina", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        programaGrupoListadoClaseDao.deleteById(programaGrupoListadoClaseId);

        return new JsonResponse(null, "Sustitución cancelada", JsonResponse.STATUS_OK);
    }

    private BigDecimal getPrecioUnitario(BigDecimal precioVenta, Articulo art){
        BigDecimal tasaIVA  = ((art.getIvaExento() != null && art.getIvaExento()) || art.getIva() == null) ? BigDecimal.ZERO : art.getIva();
        BigDecimal tasaIEPS = ((art.getIepsCuotaFija() != null && art.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || art.getIeps() == null) ? BigDecimal.ZERO : art.getIeps();
        if (art.getIepsCuotaFija() == null)
            return precioVenta.divide(BigDecimal.ONE.add(tasaIVA).add(tasaIEPS),4, RoundingMode.HALF_UP);
        else
            return precioVenta.divide(BigDecimal.ONE.add(tasaIVA)).subtract(art.getIepsCuotaFija()).setScale(4, RoundingMode.HALF_UP);
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

    public void eliminarInscripcionProyectada(Integer alumnoId, Integer grupoId) throws Exception {
        //Buscar si el grupo tiene proyecciones
        List<ProgramaGrupoComboProjection> grupos = programaGrupoDao.findAllByGrupoReferenciaId(grupoId);
        for (ProgramaGrupoComboProjection grupo : grupos){
            eliminarInscripcion(grupo.getId(), alumnoId);
        }
    }

    public boolean esEliminarInscripcionProyectada(Integer alumnoId, Integer grupoId) throws Exception {
        //Buscar si el grupo tiene proyecciones
        List<ProgramaGrupoComboProjection> grupos = programaGrupoDao.findAllByGrupoReferenciaId(grupoId);
        for (ProgramaGrupoComboProjection grupo : grupos){
            eliminarInscripcion(grupo.getId(), alumnoId);
        }

        if(grupos.size() == 0){
            eliminarInscripcion(grupoId, alumnoId);
        }

        return true;
    }

    public void eliminarInscripcion(Integer grupoId, Integer alumnoId){
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_BorrarInscripcion");
        query.registerStoredProcedureParameter( 1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter( 2, Integer.class, ParameterMode.IN);
        query.setParameter(1,alumnoId);
        query.setParameter(2,grupoId);
        query.execute();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Integer evaluarAlumnoGrupo(AlumnoGrupo alumnoGrupo, ProgramaGrupo grupo) throws Exception {

        Boolean activo = grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
        Integer alumnoId = alumnoGrupo.getAlumnoId();
        if (!activo){//Si el grupo está activo, omitir la validación
            //Obtener las inscripciones vigentes
            List<Inscripcion> inscripciones = inscripcionDao.findAllInscripcionesVigentes(alumnoId,
                    grupo.getProgramaIdioma().getProgramaId(),
                    grupo.getProgramaIdioma().getIdiomaId());
            for (Inscripcion inscripcion : inscripciones) {
                //Si tiene una inscripcion vigente que no corresponde al grupo actual, enviar un error
                if (!grupo.getId().equals(inscripcion.getGrupoId()))
                    throw new Exception(String.format("El alumno cuenta con una inscripcion pagada (%s).", inscripcion.getCodigo()));
            }
        }
        Integer nuevoEstatus;
        //Evaluar asistencias
        nuevoEstatus = alumnoGrupoDao.getEstatusAlumnoGrupo(alumnoId, grupo.getId());
        alumnoGrupo.setEstatusId(nuevoEstatus);
        alumnoGrupoDao.save(alumnoGrupo);
        //Si el grupo está finalizado, evaluar calificaciones y editar proyecciones
        if(!activo){
            List<Integer> reprobado = new ArrayList<>();
            reprobado.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.SIN_DERECHO);
            reprobado.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
            reprobado.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.DESERTOR);
            reprobado.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO);
            //Evaluar calificaciones siempre que el nuevo estatus no sea un estatus final
            if(!reprobado.contains(nuevoEstatus) || nuevoEstatus.equals(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO)){
                nuevoEstatus = alumnoGrupo.getCalificacionFinal().compareTo(new BigDecimal(grupo.getCalificacionMinima())) >= 0 ? ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO : ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO;
                alumnoGrupo.setEstatusId(nuevoEstatus);
                alumnoGrupoDao.save(alumnoGrupo);
            }
            //Si hubo un cambio de aprobado a reprobado...
            if (alumnoGrupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO) && reprobado.contains(nuevoEstatus)){
                //Buscar la inscripción proyectada y eliminarla
                eliminarInscripcionProyectada(alumnoId, grupo.getId());
            }
            //Si hubo un cambio de reprobado a aprobado...
            if (reprobado.contains(alumnoGrupo.getEstatusId()) && nuevoEstatus.equals(ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO)){
                //Buscar la proyección y crear una inscripción
                List<ProgramaGrupo> proyecciones = programaGrupoDao.findAllByGrupoReferenciaIdAndEstatusId(grupo.getId(), ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
                ProgramaGrupo proyeccion = proyecciones.size() > 0 ? proyecciones.get(0) : null;
                Alumno alumno = alumnoDao.findById(alumnoId);
                if (alumno != null && proyeccion != null){
                    Integer sedeOriginal = proyeccion.getSucursalId();
                    Integer sedeOV = inscripcionDao.findSucursalIdByInscripcionId(alumnoGrupo.getInscripcionId());
                    if(sedeOV != null)
                        proyeccion.setSucursalId(sedeOV);
                    relacionarAlumnoGrupo(alumno, proyeccion);
                    proyeccion.setSucursalId(sedeOriginal);
                }
            }
            //Si el nuevo estatus es un estatus de reprobado, verificar la eliminación de la inscripción
            if (reprobado.contains(nuevoEstatus)){
                try{ eliminarInscripcionProyectada(alumnoId,grupo.getId()); } catch (Exception e ){};
            }

        }
        return nuevoEstatus;
    }

    @Scheduled(cron = "0 0 3 * * *")
    @RequestMapping(value = "/proyecciones-caducas", method = RequestMethod.GET)
    public void deleteProyeccionesCaducas() throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_BorradoInscripcionesCaducas");
        query.execute();
    }

    @RequestMapping(value = "/getFechasInicioByAnioAndSucursalAndModalidad/{anio}/{idSucursal}/{idModalidad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasInicioByAnioAndSucursalAndModalidad(@PathVariable Integer anio, @PathVariable Integer idSucursal, @PathVariable Integer idModalidad) throws SQLException {
        Boolean esJobs = sucursalDao.sucursalEsJobs(idSucursal);
        HashMap<String, Object> json = new HashMap<>();
        json.put("esJobs",esJobs);
        json.put("programas",programaIdiomaDao.findCursosBySucursales(idSucursal));
        json.put("fechas",programaGrupoDao.findProjectedFechasByAnioAndSucursalIdAndModalidadId(anio, idSucursal, idModalidad));
        if(esJobs != null && esJobs){
            json.put("ciclos",paCicloDao.findProjectedComboAllByActivoTrue());
            json.put("fechasGrupos",programaGrupoDao.findDistinctBySucursalId(idSucursal));
            json.put("programacion",new ArrayList<>());
        }else {
            List<ProgramacionAcademicaComercialComboProjection> temp = programacionAcademicaComercialDao.findProjectedComboAllBySucursalId(idSucursal);
            json.put("ciclos",new ArrayList<>());
            json.put("fechasGrupos",new ArrayList<>());
            json.put("programacion",temp);
        }

        return new JsonResponse(json, "", JsonResponse.STATUS_OK);
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

    @RequestMapping(value = "/borrar-inscripcion", method = RequestMethod.POST)
    public JsonResponse borrarInscripcion(@RequestBody JSONObject json,ServletRequest req) throws AdvertenciaException {
        Integer grupoId = (Integer) json.get("grupoId");
        Integer alumnoId = (Integer) json.get("alumnoId");
        try{
            if(esEliminarInscripcionProyectada(alumnoId, grupoId))
                return new JsonResponse(null,null,JsonResponse.STATUS_OK);
            else
                return new JsonResponse(null,null,JsonResponse.STATUS_ERROR);
        } catch (Exception e){
            return new JsonResponse("error",null,JsonResponse.STATUS_ERROR);
        }
    }
}

