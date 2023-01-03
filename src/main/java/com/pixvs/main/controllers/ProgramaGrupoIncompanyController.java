package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionListadoGrupoProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionProjection;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoActividadProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoMetricaProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyListadoProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionListadoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoListadoClase.ProgramaGrupoListadoClaseEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenModalidad.ProgramaIdiomaExamenModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboIncompanyProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/incompany/grupos")
public class ProgramaGrupoIncompanyController {

    @Autowired
    private ProgramaGrupoIncompanyDao programaGrupoIncompanyDao;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    /*@Autowired
    private ProgramaIdiomaExamenDao programaIdiomaExamenDao;*/
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private PAModalidadHorarioDao paModalidadHorarioDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private ProgramaGrupoIncompanyGrupoMaterialDao programaGrupoIncompanyGrupoMaterialDao;
    @Autowired
    private ProgramaGrupoIncompanyGrupoDao programaGrupoIncompanyGrupoDao;
    @Autowired
    private ProgramaGrupoIncompanyArchivoDao programaGrupoIncompanyArchivoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private PAActividadEvaluacionDao paActividadEvaluacionDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ProgramaIdiomaExamenDetalleDao programaIdiomaExamenDetalleDao;
    @Autowired
    private ProgramaGrupoIncompanyCriterioEvaluacionDao programaGrupoIncompanyCriterioEvaluacionDao;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private ProgramaGrupoHistorialDao programaGrupoHistorialDao;
    @Autowired
    private LogController logController;
    @Autowired
    private AlumnoExamenCalificacionDao alumnoExamenCalificacionDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private ProgramaGrupoProfesorDao programaGrupoProfesorDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private PrecioIncompanyDao precioIncompanyDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<ProgramaGrupoIncompanyListadoProjection> grupos = programaGrupoIncompanyDao.getAllListadosGrupos();

        return new JsonResponse(grupos, null, JsonResponse.STATUS_OK);
    }



    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaGrupoIncompany grupo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        DateFormat formatter = new SimpleDateFormat("HH:mm");

        if(grupo.getId() == null){
            grupo.setCreadoPorId(idUsuario);
            grupo.setCodigo("INC"+grupo.getSucursal().getPrefijo()+"-"+cal.get(Calendar.YEAR)+"-"+autonumericoService.getSiguienteAutonumericoByPrefijo("INC").replace("INC",""));
            grupo.setActivo(true);
        }else {
            grupo.setModificadoPorId(idUsuario);
            grupo.setFechaModificacion(new Date(System.currentTimeMillis()));
            borrarArchivo(grupo.getId());
        }
        if(grupo.getSucursal() != null){
            grupo.setSucursalId(grupo.getSucursal().getId());
            grupo.setSucursal(null);
        }
        if(grupo.getCliente() != null){
            grupo.setClienteId(grupo.getCliente().getId());
            grupo.setCliente(null);
        }

        for(ProgramaGrupo grupoIncompany: grupo.getGrupos()){
            grupoIncompany.setEstatusId(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
            grupoIncompany.setMultisede(false);
            grupoIncompany.setGrupo(1);
            if(grupoIncompany.getId() == null){
                grupoIncompany.setCreadoPorId(idUsuario);
                grupoIncompany.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            }
            if(grupo.getSucursalId() != null){
                grupoIncompany.setSucursalId(grupo.getSucursalId());
            }
            if(grupoIncompany.getProgramaIdioma() != null){
                grupoIncompany.setProgramaIdiomaId(grupoIncompany.getProgramaIdioma().getId());
                grupoIncompany.setProgramaIdioma(null);
            }
            if(grupoIncompany.getPaModalidad() != null){
                grupoIncompany.setPaModalidadId(grupoIncompany.getPaModalidad().getId());
                grupoIncompany.setPaModalidad(null);
            }
            if(grupoIncompany.getHorario() != null){
                PAModalidadHorarioComboProjection temp = paModalidadHorarioDao.findByModalidadIdAndNombre(grupoIncompany.getPaModalidadId(),grupoIncompany.getHorario());
                if(temp !=null){
                    grupoIncompany.setModalidadHorarioId(temp.getId());
                } else{
                    grupoIncompany.setModalidadHorarioId(2);
                }

            }
            if(grupoIncompany.getPrecioIncompany() != null){
                grupoIncompany.setPrecioIncompanyId(grupoIncompany.getPrecioIncompany().getId());
            }
            if(grupoIncompany.getTipoGrupo() != null){
                grupoIncompany.setTipoGrupoId(grupoIncompany.getTipoGrupo().getId());
                grupoIncompany.setTipoGrupo(null);
            }
            if(grupoIncompany.getPlataforma() != null){
                grupoIncompany.setPlataformaId(grupoIncompany.getPlataforma().getId());
                grupoIncompany.setPlataforma(null);
            }
            if(grupoIncompany.getEmpleado() != null){
                grupoIncompany.setEmpleadoId(grupoIncompany.getEmpleado().getId());
                //grupoIncompany.setEmpleado(null);
            }
            if(grupoIncompany.getEstatus() != null){
                grupoIncompany.setEstatusId(grupoIncompany.getEstatus().getId());
            }
            for(ProgramaGrupoIncompanyCriterioEvaluacion criterio: grupoIncompany.getCriteriosEvaluacion()){
                if(criterio.getActividadEvaluacion() != null){
                    criterio.setActividadEvaluacionId(criterio.getActividadEvaluacion().getId());
                    criterio.setActividadEvaluacion(null);
                }
                if(criterio.getModalidad() != null){
                    criterio.setModalidadId(criterio.getModalidad().getId());
                    criterio.setModalidad(null);
                }
                if(criterio.getTestFormat() != null){
                    criterio.setTestFormatId(criterio.getTestFormat().getId());
                    criterio.setTestFormat(null);
                }
            }
            for(ProgramaGrupoIncompanyComision programaGrupoIncompanyComision : grupoIncompany.getListaComision()){
                if(programaGrupoIncompanyComision.getId() < 0){
                    programaGrupoIncompanyComision.setId(null);
                    programaGrupoIncompanyComision.setCreadoPorId(idUsuario);
                }else {
                    programaGrupoIncompanyComision.setModificadoPorId(idUsuario);
                    programaGrupoIncompanyComision.setFechaModificacion(new Timestamp(new Date().getTime()));
                }

                if(programaGrupoIncompanyComision.getUsuario() != null){
                    programaGrupoIncompanyComision.setUsuarioId(programaGrupoIncompanyComision.getUsuario().getId());
                    programaGrupoIncompanyComision.setUsuario(null);
                }
            }
            for(ProgramaGrupoIncompanyMaterial material: grupoIncompany.getMateriales()){
                if(material.getArticulo() != null){
                    material.setArticuloId(material.getArticulo().getId());
                    material.setArticulo(null);
                }
            }
            if(grupoIncompany.getHorarios()!=null && grupoIncompany.getHorarios().size() >0){
                grupoIncompany.setPaModalidadId(paModalidadDao.findByCodigo("PER").getId());
                grupoIncompany.setModalidadHorarioId(2);
                grupoIncompany.setFechaFinInscripcionesBecas(grupoIncompany.getFechaFin());
                grupoIncompany.setFechaFinInscripciones(grupoIncompany.getFechaFin());
                for(ProgramaGrupoIncompanyHorario horario: grupoIncompany.getHorarios()){
                    if(horario.getHoraInicioString()!=null){
                        Time horaInicio = new Time(formatter.parse(horario.getHoraInicioString()).getTime());
                        horario.setHoraInicio(horaInicio);
                    }
                    if(horario.getHoraFinString()!=null){
                        Time horaFin = new Time(formatter.parse(horario.getHoraFinString()).getTime());
                        horario.setHoraFin(horaFin);
                    }
                }
            }

            if(grupoIncompany.getClasesReprogramadas() !=null){
                for(ProgramaGrupoIncompanyClaseReprogramada clase: grupoIncompany.getClasesReprogramadas()){
                    //clase.setProgramaIncompanyGrupoId(grupoIncompany.getId());
                    //clase.setFechaNueva(addDays(clase.getFechaNueva(),1));
                    //clase.setFechaReprogramar(addDays(clase.getFechaReprogramar(),1));
                    if(clase.getHoraInicioString()!=null){
                        Time horaInicio = new Time(formatter.parse(clase.getHoraInicioString()).getTime());
                        clase.setHoraInicio(horaInicio);
                    }
                    if(clase.getHoraFinString()!=null){
                        Time horaFin = new Time(formatter.parse(clase.getHoraFinString()).getTime());
                        clase.setHoraFin(horaFin);
                    }
                }
            }

            if(grupoIncompany.getId() == null){
                ProgramaIdioma programaIdioma = programaIdiomaDao.findById(grupoIncompany.getProgramaIdiomaId());
                Integer horarioId = null;
                if(grupoIncompany.getHorarios()!=null && grupoIncompany.getHorarios().size() >0){
                    horarioId = null;
                }else{
                    horarioId = grupoIncompany.getModalidadHorarioId();
                }
                grupoIncompany.setCodigoGrupo(getCodigo(grupoIncompany.getSucursalId(), grupoIncompany.getSucursalPlantelId(), programaIdioma.getProgramaId(), programaIdioma.getIdiomaId(), grupoIncompany.getPaModalidadId(), grupoIncompany.getNivel(), horarioId, grupoIncompany.getGrupo()));
            }

            List<ProgramaGrupoProfesor> profesores = new ArrayList<>();
            if(grupoIncompany.getProfesoresTitulares() == null){
                grupoIncompany.setProfesoresTitulares(new ArrayList<>());
            }
            profesores.addAll(grupoIncompany.getProfesoresTitulares());
            ProgramaGrupo grupoBD = programaGrupoDao.findById(grupoIncompany.getId());

            // Validar cambio de profesor mediante combo de profesores
            boolean profesorDiferente = grupoIncompany.getId() != null
                    && (
                    (grupoIncompany.getEmpleado() == null && grupoBD.getEmpleadoId() != null)
                            || (grupoIncompany.getEmpleado() != null && grupoBD.getEmpleadoId() != null && grupoIncompany.getEmpleado().getId().intValue() != grupoBD.getEmpleadoId().intValue())
            );
            if(profesorDiferente){
                Date fechaActual = new Date();
                if(grupoIncompany.getFechaInicio().getTime() < fechaActual.getTime()){
                    return new JsonResponse(null,"No es posible cambiar el profesor titular de este modo pasando la fecha de inicio", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
                Boolean tienePreenomina = programaGrupoDao.getGrupoTienePrenomina(grupoIncompany.getId());
                if(tienePreenomina){
                    return new JsonResponse(null,"No es posible cambiar el profesor titular de este modo debido a que ya se generó prenómina del grupo", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }
            List<ProgramaGrupoListadoClase> temp = grupoIncompany.getListadoClases();
            if(grupoIncompany.getNuevoProfesorTitular() != null){
                grupoIncompany.setEmpleadoId(grupoIncompany.getNuevoProfesorTitular().getId());
            }
            if(grupoBD != null) {
                profesorDiferente = profesorDiferente || (grupoBD.getEmpleadoId() == null && grupoIncompany.getEmpleadoId() != null);
                grupoIncompany.setProfesoresTitulares(grupoBD.getProfesoresTitulares());
            }


            if(profesorDiferente){
                List<ProgramaGrupoProfesor> profesoresAnteriores = programaGrupoProfesorDao.findAllByGrupoIdAndActivoTrue(grupo.getId());
                for(ProgramaGrupoProfesor profesorTitular : profesoresAnteriores){
                    profesorTitular.setActivo(false);
                    programaGrupoProfesorDao.save(profesorTitular);
                }

                if(grupoIncompany.getEmpleadoId() != null){
                    ProgramaGrupoProfesor profesorTitular = new ProgramaGrupoProfesor();
                    profesorTitular.setGrupoId(grupoIncompany.getId());
                    profesorTitular.setEmpleadoId(grupoIncompany.getEmpleadoId());
                    profesorTitular.setFechaInicio(grupoIncompany.getFechaInicio());
                    profesorTitular.setSueldo(grupoIncompany.getSueldoProfesor());
                    profesorTitular.setActivo(true);

                    programaGrupoProfesorDao.save(profesorTitular);
                }

            }

            if(temp!=null && temp.size()>0){
                for(Integer i=0;i<temp.size();i++){
                    if(temp.get(i).getId() == null){
                        logController.insertaLogUsuario(
                                new Log("Cambio de profesor a: "+empleadoDao.findComboById(grupoIncompany.getListadoClases().get(i).getEmpleadoId()).getNombreCompleto(),
                                        LogTipo.MODIFICADO,
                                        LogProceso.CAMBIO_PROFESOR,
                                        grupoIncompany.getListadoClases().get(i).getId()
                                ),
                                idUsuario
                        );
                    }else if(temp.get(i).getId() != null && temp.get(i).getChangeDetected()){

                        logController.insertaLogUsuario(
                                new Log("Cambio de profesor a: "+empleadoDao.findComboById(grupoIncompany.getListadoClases().get(i).getEmpleadoId()).getNombreCompleto(),
                                        LogTipo.MODIFICADO,
                                        LogProceso.CAMBIO_PROFESOR,
                                        grupoIncompany.getListadoClases().get(i).getId()
                                ),
                                idUsuario
                        );
                    }
                }
            }

            if(grupo.getId() != null){
                for(ProgramaGrupoProfesor profesor : grupoIncompany.getProfesoresTitulares()){
                    if(profesor.getActivo()){
                        profesores.add(profesor);
                    }
                }
                actualizarProfesoresTitulares(grupo.getId(),profesores);
            }

        }


        //grupo = programaGrupoIncompanyDao.save(grupo);

        for(ProgramaGrupo grupoIncompany : grupo.getGrupos()){
            if(grupoIncompany.getHorarios() == null || grupoIncompany.getHorarios().size() == 0){
                String pattern = "yyyy-MM-dd";
                DateFormat df = new SimpleDateFormat(pattern);
//                if(programaGrupoDao.getfechaFinInscripciones(grupoIncompany.getId()) != null && grupoIncompany.getFechaFinInscripciones() == null) {
//                    grupoIncompany.setFechaFinInscripciones(programaGrupoDao.getfechaFinInscripcionesNuevo(grupoIncompany.getPaModalidadId(),df.format(grupoIncompany.getFechaInicio())));
//                }
//                if(programaGrupoDao.getfechaFinInscripcionesBecas(grupoIncompany.getId()) != null && grupoIncompany.getFechaFinInscripcionesBecas() == null) {
//                    grupoIncompany.setFechaFinInscripcionesBecas(programaGrupoDao.getfechaFinInscripcionesBecasNuevo(grupoIncompany.getPaModalidadId(),df.format(grupoIncompany.getFechaInicio())));
//                }
                if(grupoIncompany.getFechaFinInscripciones() == null) {
                    grupoIncompany.setFechaFinInscripciones(programaGrupoDao.getfechaFinInscripcionesNuevo(grupoIncompany.getPaModalidadId(),df.format(grupoIncompany.getFechaInicio())));
                }
                if(grupoIncompany.getFechaFinInscripcionesBecas() == null) {
                    grupoIncompany.setFechaFinInscripcionesBecas(programaGrupoDao.getfechaFinInscripcionesBecasNuevo(grupoIncompany.getPaModalidadId(),df.format(grupoIncompany.getFechaInicio())));
                }
            }
        }

        grupo = programaGrupoIncompanyDao.save(grupo);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idCurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idCurso) throws SQLException {

        ProgramaGrupoIncompanyEditarProjection grupo = programaGrupoIncompanyDao.findProjectedEditarById(idCurso);

        return new JsonResponse(grupo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/asistenciasCalificaciones/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAsistenciasCalificacionesById(@PathVariable Integer id) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();
        ProgramaGrupoEditarProjection grupo = programaGrupoDao.findProjectedEditarById(id);
        Integer programaId = grupo.getProgramaIdioma().getProgramaId();
        Integer modalidadId = grupo.getPaModalidad().getId();
        Integer idiomaId = grupo.getProgramaIdioma().getIdiomaId();
        PAModalidadComboProjection modalidad = paModalidadDao.findProjectedComboById(grupo.getPaModalidad().getId());
        List<InscripcionListadoGrupoProjection> inscripciones = inscripcionDao.findListadoGrupoByGrupoIdAndEstatusIdIsNotOrderByAlumnoGrupo(id,ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA,ControlesMaestrosMultiples.CMM_INS_Estatus.BAJA);
        List<ProgramaGrupoActividadProjection> actividades = programaGrupoDao.getGrupoActividades(programaId, grupo.getId(), modalidadId, idiomaId);
        json.put("actividades", actividades);
        List<AlumnoExamenCalificacionProjection> calificaciones = alumnoExamenCalificacionDao.findAllProjectedByGrupoId(grupo.getId());
        json.put("calificaciones", calificaciones);
        List<ProgramaGrupoMetricaProjection> metricas = programaGrupoDao.getGrupoMetricas(programaId, grupo.getId(), modalidadId, idiomaId);
        json.put("metricas", metricas);
        List<InscripcionProjection> alumnos = inscripcionDao.findAllProjectedByGrupoIdAndEstatusIdIsNot(grupo.getId(), 2000512);
        json.put("alumnos", alumnos);
        json.put("faltas", grupo.getFaltasPermitidas());
        List<AlumnoAsistenciaEditarProjection> asistencias = alumnoAsistenciaDao.findAllEditarProjetedByGrupoId(grupo.getId());
        json.put("asistencias", asistencias);
        List<Log> historial = new ArrayList<>();
        for(ProgramaGrupoListadoClaseEditarProjection listado: grupo.getListadoClases()){
            historial.addAll(logController.getHistorial(listado.getId(), LogProceso.CAMBIO_PROFESOR));
        }
        json.put("historial",historial);
        json.put("inscripciones", inscripciones);
        List<ProgramaIdiomaExamenDetalleListadoProjection> tests = programaIdiomaExamenDetalleDao.getTestByProgramaId(grupo.getProgramaIdioma().getId(),grupo.getPaModalidad().getId());
        json.put("tests",tests);
        json.put("modalidad",modalidad);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getModalidades/{idCurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getModalidadesByIdCurso(@PathVariable Integer idCurso) throws SQLException {

        ProgramaIdiomaEditarProjection programa = programaIdiomaDao.findProjectedEditarById(idCurso);
        List<PAModalidadComboProjection> modalidades = new ArrayList<>();
        for(ProgramaIdiomaModalidadEditarProjection modalidad: programa.getModalidades()){
            modalidades.add(modalidad.getModalidad());
        }

        return new JsonResponse(modalidades, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/getTest/{idPrograma}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTestByIdCurso(@PathVariable Integer idPrograma) throws SQLException {

        //List<ProgramaIdiomaListadoTestProjection> tests = programaIdiomaExamenDao.findProjectedComboAllByPrograma(idPrograma);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idPrograma}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idPrograma) throws SQLException {

        int actualizado = programaGrupoIncompanyDao.actualizarActivo(hashId.decode(idPrograma), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramasById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ProgramaGrupoIncompanyEditarProjection grupo = null;
        HashMap<Integer,Boolean> permisos = new HashMap<>();
        if (id != null) {
            grupo = programaGrupoIncompanyDao.findProjectedEditarById(id);
            for(ProgramaGrupoEditarProjection grupoEditar : grupo.getGrupos()){
                Boolean mostrarBtnCambioProfesor = false;
                if(grupoEditar != null){
                    Date fechaActual = new Date();
                    mostrarBtnCambioProfesor = grupoEditar.getFechaInicio().getTime() < fechaActual.getTime() && grupoEditar.getSueldoProfesor() != null;
                    if(!mostrarBtnCambioProfesor){
                        mostrarBtnCambioProfesor = programaGrupoDao.getGrupoTienePrenomina(grupo.getId());
                    }
                }
                permisos.put(grupoEditar.getId(),mostrarBtnCambioProfesor);
            }
        }

        List<SucursalComboIncompanyProjection> sucursales = sucursalDao.findProjectedComboIncompanyAllByActivoTrue();
        List<ClienteComboProjection> clientes = clienteDao.findAllByActivoIsTrue();
        //List<ProgramaIdiomaComboProjection> cursos = programaIdiomaDao.findAllByActivoIsTrueOrderByCodigo();
        //List<PAModalidadComboProjection> modalidades = paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre();
        List<ControlMaestroMultipleComboProjection> tipoGrupo = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
        List<ArticuloComboSimpleProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndFamiliaIdAndTipoArticuloIdNot(17,4);
        List<ControlMaestroMultipleComboProjection> plataformas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_Plataforma.NOMBRE);
        List<EmpleadoComboProjection> profesores = empleadoDao.findAllByTipoEmpleadoId(2000294);
        List<ControlMaestroMultipleComboProjection> testFormat = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_TestFormat.NOMBRE);
        List<ControlMaestroMultipleComboProjection> zonas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_INC_Zona.NOMBRE);

        Usuario usuario = usuarioDao.findById(idUsuario);
        HashMap<String, Object> json = new HashMap<>();

        json.put("grupo", grupo);
        json.put("sucursales", sucursales);
        json.put("clientes", clientes);
        //json.put("precios",precios);
        //json.put("modalidades", modalidades);
        json.put("plataformas", plataformas);
        json.put("tipoGrupo", tipoGrupo);
        json.put("articulos",articulos);
        json.put("profesores",profesores);
        json.put("testFormat",testFormat);
        json.put("zonas",zonas);
        json.put("permisos",permisos);
        json.put("listaUsuario", usuarioDao.findProjectedComboAllByEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
        json.put("listaPrecio", precioIncompanyDao.findPrecioIncompanyComisionProjectionByEstatusId(ControlesMaestrosMultiples.CMM_PREINC_Estatus.ACTIVO));
        json.put("permisoSueldo",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_MODIFICAR_SUELDO_INCOMPANY));
        json.put("permisoPrecio",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_MODIFICAR_PRECIO_GRUPO));
        json.put("permisoPorcentaje",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_MODIFICAR_PORCENTAJE_TRANSPORTE));
        json.put("permisoComision",rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PERMITIR_MODIFICAR_PORCENTAJE_COMISION));
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_LISTADO_PROGRAMAS_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Numero de idiomas"};

        excelController.downloadXlsx(response, "programas", query, alColumnas, null,"Empleados");
    }

    @RequestMapping(value = "/getCriteriosEvaluacion", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCriteriosEvaluacionByIdCurso(@RequestBody JsonDiaFechaAplicacion jsonDiaFechaAplicacion) throws SQLException {
        List<ProgramaGrupoIncompanyCriterioEvaluacionListadoEditarProjection> criteriosEvaluacion = programaGrupoIncompanyCriterioEvaluacionDao.getTestByProgramaId(jsonDiaFechaAplicacion.getProgramaIdiomaId(),jsonDiaFechaAplicacion.getModalidadId(),jsonDiaFechaAplicacion.getNivel());
        return new JsonResponse(criteriosEvaluacion, null, JsonResponse.STATUS_OK);
    }

    Date fechaAplicacion(String modalidad, Date fechaInicio, Integer diasAplica){
        Integer modalidadNumber =0;
        switch (modalidad){
            case "Intensivo":
                modalidadNumber = 1;
                break;
            case "Semi-Intensivo":
                modalidadNumber = 2;
                break;
            case "Sabatino":
            case "Sabatino A":
            case "Sabatino B":
                modalidadNumber = 3;
                break;
            case "Semi-Intensivo A":
                modalidadNumber = 4;
                break;
            case "Semi-Intensivo B":
                modalidadNumber = 5;
                break;
            case "Semi-Intensivo C":
                modalidadNumber = 6;
                break;
            case "Semi-Intensivo D":
                modalidadNumber = 7;
                break;
            case "Intensivo PETTD":
                modalidadNumber = 8;
                break;
            case "Intensivo A":
                modalidadNumber = 9;
                break;
        }
        Date fechaAplicacion = programaGrupoIncompanyDao.getFechaAplicacion(fechaInicio,diasAplica,modalidadNumber);
        return  fechaAplicacion;
    }

    public void limpiarDatos(Integer idGrupoInc){
        programaGrupoIncompanyGrupoMaterialDao.deleteByGrupoId(idGrupoInc);
    }

    public void borrarArchivo(Integer idIncompany){
        programaGrupoIncompanyArchivoDao.deleteByProgramaIncompanyId(idIncompany);
    }

    private String getCodigo(Integer sucursalId, Integer plantelId, Integer programaId, Integer idiomaId, Integer modalidadId, Integer nivel, Integer horarioId, Integer grupo){
        String codigo = "";

        Sucursal sucursal = sucursalDao.findById(sucursalId);
        if(sucursal.getCodigoSucursal().length() > 3){
            codigo += sucursal.getCodigoSucursal().substring(0,3);
        }else{
            codigo += sucursal.getCodigoSucursal();
        }

        codigo += "ICP";

        Programa programa = programaDao.findById(programaId);
        if(programa.getCodigo().length() > 3){
            codigo += programa.getCodigo().substring(0,3);
        }else{
            codigo += programa.getCodigo();
        }

        ControlMaestroMultiple idioma = controlMaestroMultipleDao.findCMMById(idiomaId);
        if(idioma.getReferencia().length() > 3){
            codigo += idioma.getReferencia().substring(0,3);
        }else{
            codigo += idioma.getReferencia();
        }

        PAModalidad modalidad = paModalidadDao.findById(modalidadId);
        if(modalidad.getCodigo().length() > 3){
            codigo += modalidad.getCodigo().substring(0,3);
        }else{
            codigo += modalidad.getCodigo();
        }

        String nivelStr = "00" + nivel.toString();
        codigo += nivelStr.substring(nivelStr.length()-2,nivelStr.length());

        if(horarioId != null) {
            PAModalidadHorario horario = paModalidadHorarioDao.findById(horarioId);
            codigo += horario.getCodigo();
        } else {
            codigo += "99";
        }

        String grupoStr = "00" + grupo.toString();
        codigo += grupoStr.substring(grupoStr.length()-2,grupoStr.length());

        return codigo;
    }

    @RequestMapping(value = "/getCriteriosEvaluacionPersonalizada", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCriteriosEvaluacionPersonalizadaByIdCurso(@RequestBody JsonDiaFechaAplicacion jsonDiaFechaAplicacion) throws SQLException, ParseException {
        List<ProgramaGrupoIncompanyCriterioEvaluacion> criteriosEvaluacion = new ArrayList<>();
        ProgramaGrupoIncompanyCriterioEvaluacion criterioTemp;
        /*List<ProgramaIdiomaExamen> tests = programaIdiomaExamenDao.findExamenAllByProgramaIdiomaId(jsonDiaFechaAplicacion.getProgramaIdiomaId());
        for(ProgramaIdiomaExamen examen: tests){
            criterioTemp = new ProgramaGrupoIncompanyCriterioEvaluacion();
            criterioTemp.setActividadEvaluacion(examen.getActividadEvaluacion());
            criterioTemp.getActividadEvaluacion().setCreadoPor(null);
            criterioTemp.getActividadEvaluacion().setModificadoPor(null);
            criterioTemp.setTime(examen.getTime());
            criterioTemp.setScore(examen.getScore());
            criterioTemp.setActivo(true);
            criterioTemp.setTestFormat(examen.getTest());
            Integer diasAplica = 5;
            criterioTemp.setFechaAplica(fechaAplicacionPersonalizada(jsonDiaFechaAplicacion.getHorarios(),jsonDiaFechaAplicacion.getFechaInicio(),diasAplica));
            criteriosEvaluacion.add(criterioTemp);
        }*/


        return new JsonResponse(criteriosEvaluacion, null, JsonResponse.STATUS_OK);
    }

    Date fechaAplicacionPersonalizada(List<ProgramaGrupoIncompanyHorario> horario, Date fechaInicio, Integer diasAplica) throws ParseException {
        Boolean lunes = false;
        Boolean martes = false;
        Boolean miercoles = false;
        Boolean jueves = false;
        Boolean viernes = false;
        Boolean sabado = false;
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        for(ProgramaGrupoIncompanyHorario dia: horario){
            if(dia.getHoraInicioString()!=null) {
                Time horaInicio = new Time(formatter.parse(dia.getHoraInicioString()).getTime());
                dia.setHoraInicio(horaInicio);
            }
            if(dia.getHoraFinString()!=null) {
                Time horaFin = new Time(formatter.parse(dia.getHoraFinString()).getTime());
                dia.setHoraFin(horaFin);
            }
            if(dia.getDia().equals(new String("Lunes")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                lunes = true;
            }
            if(dia.getDia().equals(new String("Martes")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                martes = true;
            }
            if(dia.getDia().equals(new String("Miercoles")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                miercoles = true;
            }
            if(dia.getDia().equals(new String("Jueves")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                jueves = true;
            }
            if(dia.getDia().equals(new String("Viernes")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                viernes = true;
            }
            if(dia.getDia().equals(new String("Sabado")) && dia.getHoraInicio()!=null && dia.getHoraFin()!=null){
                sabado = true;
            }

        }

        Date fechaAplicacion = programaGrupoIncompanyDao.getFechaAplicacionPersonalizado(fechaInicio,diasAplica,lunes,martes,miercoles,jueves,viernes,sabado);
        return  fechaAplicacion;
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
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
}

