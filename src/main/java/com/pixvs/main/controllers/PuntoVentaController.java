package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.*;
import com.pixvs.main.models.projections.Alumno.*;
import com.pixvs.main.models.projections.Articulo.ArticuloCardProjection;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaCardProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaCardProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaCardProjection;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGListadoProjection;
import com.pixvs.main.models.projections.Cliente.ClienteCardProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.InscripcionSinGrupo.InscripcionSinGrupoListadoProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.MedioPagoPV.MedioPagoPVComboProjection;
import com.pixvs.main.models.projections.MontosCalculados.MontosCalculadosProjection;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaHistorialPVProjection;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaHistorialPVResumenProjection;
import com.pixvs.main.models.projections.OrdenVentaDetalle.OrdenVentaDetalleHistorialPVResumenProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadCardProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaCardProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoCardProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeListadoPVProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelComboProjection;
import com.pixvs.main.services.CentroPagosService;
import com.pixvs.main.services.ProcesadorInventariosService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.handler.exceptions.InventarioNegativoException;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleCardProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioLoginVerificarProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.handler.exceptions.UsuarioException;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 30/06/2021.
 */
@RestController
@RequestMapping("/api/v1/punto-venta")
public class PuntoVentaController {

    // Daos
    @Autowired
    private SucursalCorteCajaDao sucursalCorteCajaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private ListadoPrecioDao listadoPrecioDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private ArticuloCategoriaDao articuloCategoriaDao;
    @Autowired
    private ArticuloSubcategoriaDao articuloSubcategoriaDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private OrdenVentaDao ordenVentaDao;
    @Autowired
    private PAModalidadHorarioDao paModalidadHorarioDao;
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private MedioPagoPVDao medioPagoPVDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private InscripcionSinGrupoDao inscripcionSinGrupoDao;
    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private BecaUDGDao becaUDGDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private OrdenVentaDetalleDao ordenVentaDetalleDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private AlumnoExamenCertificacionDao alumnoExamenCertificacionDao;
    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    private PADescuentoDao paDescuentoDao;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ProgramaGrupoIncompanyDao programaGrupoIncompanyDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private AlumnoExamenCalificacionDao alumnoExamenCalificacionDao;
    @Autowired
    private AlumnoConstanciaTutoriaDao alumnoConstanciaTutoriaDao;
    @Autowired
    private MonedaParidadDao monedaParidadDao;
    @Autowired
    private ProgramaIdiomaCertificacionValeDao programaIdiomaCertificacionValeDao;

    // Services
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ProcesadorInventariosService procesadorInventariosService;
    @Autowired
    private CentroPagosService centroPagosService;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ProcesadorAlertasService procesadorAlertasService;

    // Misc
    @Autowired
    private Environment environment;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HashId hashId;

    private enum EstatusPermiteInscripcionPorExamenUbicacion {
        SI_PERMITE_NO_EXAMEN,
        SI_PERMITE_EXAMEN_APLICADO,
        NO_PERMITE_EN_PROCESO,
        NO_PERMITE_NIVEL_SUPERIOR
    }

    @RequestMapping(value = "/turno/estatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getEstatusTurno(ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(usuarioId);
        Boolean turnoAbierto = corteAbierto != null;

        return new JsonResponse(turnoAbierto, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/turno/abrir", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse abrirTurno(@RequestBody HashMap<String,Object> body, ServletRequest req) throws Exception {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer sucursalId = (Integer)body.get("sucursalId");
        Integer plantelId = (Integer)body.get("plantelId");

        Sucursal sucursal = sucursalDao.findById(sucursalId);
        if(sucursal.getListadoPrecioId() == null){
            return new JsonResponse(null, "No se encontró una lista de precios en la sucursal seleccionada", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
        }

        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(usuarioId);

        if(corteAbierto != null){
            return new JsonResponse(null, "Ya existe un punto de venta abierto", JsonResponse.STATUS_OK);
        }

        SucursalCorteCaja nuevoCorte = new SucursalCorteCaja();
        nuevoCorte.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("SCC"));
        nuevoCorte.setSucursalId(sucursalId);
        nuevoCorte.setSucursalPlantelId(plantelId);
        nuevoCorte.setUsuarioAbreId(usuarioId);
        nuevoCorte.setMontoAbrirCaja(BigDecimal.ZERO);
        sucursalCorteCajaDao.save(nuevoCorte);

        return new JsonResponse(null, "Turno abierto exitosamente", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/turno/cerrar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse terminarTurno(ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(usuarioId);

        if(corteAbierto == null){
            return new JsonResponse(null, "No existe ningun punto de venta abierto", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        corteAbierto.setFechaFin(new Date());
        corteAbierto.setMontoCerrarCaja(BigDecimal.ZERO);
        sucursalCorteCajaDao.save(corteAbierto);

        return new JsonResponse(null, "Turno terminado exitosamente", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/inicio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosInicio(ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosIdAndListadoPrecioIdIsNotNull(usuarioId);

        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(usuarioId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("sucursales",sucursales);
        responseBody.put("turnoAbierto",corteAbierto != null);
        if(corteAbierto != null){
            responseBody.put("sucursalTurnoId",corteAbierto.getSucursalId());
            responseBody.put("plantelTurnoId",corteAbierto.getSucursalPlantelId());
        }

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{sucursalId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosPuntoVentaAbierto(@PathVariable(required = false) Integer sucursalId, ServletRequest req) throws SQLException {

        if(sucursalId == null){
            return new JsonResponse(null, "No se encontró sede, favor de indicar una", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
        }

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        // Se valida que el usuario tenga permisos en la sucursal indicada
        Sucursal sucursalPermisoUsuario = sucursalDao.findByUsuarioPermisosIdAndId(usuarioId,sucursalId);
        if(sucursalPermisoUsuario == null){
            return new JsonResponse(null, "No se tiene permisos para operar en sucursal actual", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
        }

        // Se valida que el corte exista
        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(usuarioId);
        if(corteAbierto == null){
            return new JsonResponse(null, "No existe ningún turno abierto para el usuario actual", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
        }

        // Se obtienen los listados
        List<ClienteComboProjection> clientes = clienteDao.findProjectedComboAllByListadoPrecioIdNotNullAndActivoTrue();
        List<ControlMaestroMultipleCardProjection> idiomas = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueAndSistemaIsFalseOrderByOrden(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<ArticuloFamiliaCardProjection> familias = articuloFamiliaDao.findProjectedCardAllByVentaTrue();
        String listaPreciosMapStr = listadoPrecioDao.getListaPreciosMapStr(sucursalPermisoUsuario.getListadoPrecioId(),sucursalId);
        String listaPreciosSinDescuentoMapStr = listadoPrecioDao.getListaPreciosSinDescuentoMapStr(sucursalPermisoUsuario.getListadoPrecioId());
        List<MedioPagoPVComboProjection> mediosPagoPV = medioPagoPVDao.findProjectedComboAllBySucursalId(sucursalId);
        List<LocalidadComboProjection> localidadesSucursal = localidadDao.findProjectedComboAllBySucursalId(sucursalId);

        // Se obtienen los permisos especiales
        Boolean menuInscripcionesBecasSindicato = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_BECAS_SINDICATO);
        Boolean menuInscripcionesBecasProulex = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_BECAS_PROULEX);
        Boolean menuInscripcionesReinscripciones = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_RE_INSCRIPCIONES);
        Boolean menuInscripcionesJOBS = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_JOBS);
        Boolean menuInscripcionesJOBSSEMS = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_JOBS_SEMS);
        Boolean menuInscripcionesCursosPCP = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_CURSOS_PCP);
        Boolean menuInscripcionesHistorial = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_HISTORIAL);
        Boolean menuInscripcionesDescuentos = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_DESCUENTOS);
        Boolean menuInscripcionesAlumnosSinGrupo = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_ALUMNOS_SIN_GRUPO);
        Boolean menuInscripcionesValesCertificacion = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_INSCRIPCIONES_VALES_CERTIFICACION);
        Boolean menuAccionesEntregaLibros = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_ACCIONES_ENTREGA_DE_LIBROS);
        Boolean menuAccionesCancelarInscripcion = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.PV_MENU_ACCIONES_CANCELAR_INSCRIPCION);

        // Se mapean los permisos especiales de acuerdo a la estructura del menú del PV
        HashMap<String,Object> permisosMenu = new HashMap<>();
        HashMap<String,Object> permisosMenuInscripciones = null;
        HashMap<String,Object> permisosMenuAcciones = null;
        if(menuInscripcionesBecasSindicato || menuInscripcionesBecasProulex || menuInscripcionesReinscripciones || menuInscripcionesJOBS || menuInscripcionesJOBSSEMS || menuInscripcionesCursosPCP || menuInscripcionesHistorial || menuInscripcionesDescuentos || menuInscripcionesAlumnosSinGrupo || menuInscripcionesValesCertificacion){
            permisosMenuInscripciones = new HashMap<>();
            HashMap<String,Object> permisosMenuInscripcionesBecas = null;
            if(menuInscripcionesBecasSindicato || menuInscripcionesBecasProulex){
                permisosMenuInscripcionesBecas = new HashMap<>();
                permisosMenuInscripcionesBecas.put("Sindicato",menuInscripcionesBecasSindicato);
                permisosMenuInscripcionesBecas.put("Proulex",menuInscripcionesBecasProulex);
            }
            HashMap<String,Object> permisosMenuInscripcionesJOBS = null;
            if(menuInscripcionesJOBS || menuInscripcionesJOBSSEMS){
                permisosMenuInscripcionesJOBS = new HashMap<>();
                permisosMenuInscripcionesJOBS.put("JOBS",menuInscripcionesJOBS);
                permisosMenuInscripcionesJOBS.put("JOBSSEMS",menuInscripcionesJOBSSEMS);
            }
            permisosMenuInscripciones.put("Becas",permisosMenuInscripcionesBecas);
            permisosMenuInscripciones.put("Reinscripciones",menuInscripcionesReinscripciones);
            permisosMenuInscripciones.put("JOBS",permisosMenuInscripcionesJOBS);
            permisosMenuInscripciones.put("CursosPCP",menuInscripcionesCursosPCP);
            permisosMenuInscripciones.put("Historial",menuInscripcionesHistorial);
            permisosMenuInscripciones.put("Descuentos",menuInscripcionesDescuentos);
            permisosMenuInscripciones.put("AlumnosSinGrupo",menuInscripcionesAlumnosSinGrupo);
            permisosMenuInscripciones.put("ValesCertificacion",menuInscripcionesValesCertificacion);
        }
        if(menuAccionesEntregaLibros || menuAccionesCancelarInscripcion){
            permisosMenuAcciones = new HashMap<>();
            permisosMenuAcciones.put("EntregaLibros",menuAccionesEntregaLibros);
            permisosMenuAcciones.put("CancelarInscripcion",menuAccionesCancelarInscripcion);
        }
        permisosMenu.put("Inscripciones",permisosMenuInscripciones);
        permisosMenu.put("Acciones",permisosMenuAcciones);

        BigDecimal tipoCambio = monedaParidadDao.getTipoCambio(sucursalPermisoUsuario.getListadoPrecio().getMonedaId(),new Date());
        if(tipoCambio == null){
            tipoCambio = BigDecimal.ONE;
        }

        // Se crean cards de uso específico
        List<HashMap<String,Object>> cardsExtra = crearCardsExtra("In Company");

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("nombreSucursal",sucursalPermisoUsuario.getNombre());
        responseBody.put("nombrePlantel",corteAbierto.getSucursalPlantel() == null ? null : corteAbierto.getSucursalPlantel().getNombre());
        responseBody.put("clientes",clientes);
        responseBody.put("idiomas",idiomas);
        responseBody.put("familias",familias);
        responseBody.put("alumnos",new ArrayList<>());
        responseBody.put("listaPreciosMapStr",listaPreciosMapStr);
        responseBody.put("listaPreciosSinDescuentoMapStr",listaPreciosSinDescuentoMapStr);
        responseBody.put("listaPreciosSucursalId",sucursalPermisoUsuario.getListadoPrecioId());
        responseBody.put("mediosPagoPV",mediosPagoPV);
        responseBody.put("localidadesSucursal",localidadesSucursal);
        responseBody.put("permisosMenu",permisosMenu);
        responseBody.put("cardsExtra",cardsExtra);
        responseBody.put("simboloMonedaSucursal",sucursalPermisoUsuario.getListadoPrecio().getMoneda().getSimbolo());
        responseBody.put("prefijoMonedaSucursal",sucursalPermisoUsuario.getListadoPrecio().getMoneda().getCodigo());
        responseBody.put("tipoCambioSucursal",tipoCambio);
        responseBody.put("programaAcademy", programaDao.findFirstByAcademyIsTrueAndActivoIsTrue());

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/programas/{sucursalId}/{idiomaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getProgramas(@PathVariable Integer sucursalId, @PathVariable Integer idiomaId, ServletRequest req) throws SQLException {

        List<ProgramaCardProjection> programas = programaDao.findProjectedCardAllByIdiomaId(idiomaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("programas",programas);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/modalidades/{idiomaId}/{programaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getModalidades(@PathVariable Integer idiomaId, @PathVariable Integer programaId, ServletRequest req) throws SQLException {

        List<PAModalidadCardProjection> paModalidades = paModalidadDao.findProjectedCardAllByActivoTrueAndIdiomaIdAndProgramaId(idiomaId,programaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("paModalidades",paModalidades);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/tipos-grupos/{idiomaId}/{programaId}/{modalidadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getTiposGrupos(@PathVariable Integer idiomaId, @PathVariable Integer programaId, @PathVariable Integer modalidadId, ServletRequest req) throws SQLException {

        List<ControlMaestroMultipleCardProjection> tiposGrupos = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByOrden(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);

        PAModalidad paModalidad = paModalidadDao.findById(modalidadId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("tiposGrupos",tiposGrupos);
        responseBody.put("colorModalidad",paModalidad.getColor());

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/niveles/{idiomaId}/{programaId}/{modalidadId}/{tipoGrupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getNiveles(@PathVariable Integer idiomaId, @PathVariable Integer programaId, @PathVariable Integer modalidadId, @PathVariable Integer tipoGrupoId, ServletRequest req) throws SQLException {

        List<Integer> niveles = new ArrayList<>();
        ProgramaIdioma programaIdioma = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);
        for(int nivel = 1 ; nivel <= programaIdioma.getNumeroNiveles() ; nivel++){
            niveles.add(nivel);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("niveles",niveles);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/grupos/{sucursalId}/{idiomaId}/{programaId}/{modalidadId}/{tipoGrupoId}/{nivel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getGrupos(@PathVariable Integer sucursalId, @PathVariable Integer idiomaId, @PathVariable Integer programaId, @PathVariable Integer modalidadId, @PathVariable Integer tipoGrupoId, @PathVariable Integer nivel, ServletRequest req) throws SQLException {

        ProgramaIdioma curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);

        List<ProgramaGrupoCardProjection> grupos = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndNivelAndFechaFin(sucursalId,idiomaId,programaId,modalidadId,tipoGrupoId,nivel);
        List<ProgramaGrupoCardProjection> gruposMultisede = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndNivelAndMultisedeAndFechaFin(sucursalId,idiomaId,programaId,modalidadId,tipoGrupoId,nivel);

        HashMap<String,HashMap<String,Object>> gruposCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposCabeceras = new ArrayList<>();
        crearCardsGrupos(grupos,gruposCabecerasMap,gruposCabeceras,false,false);

        crearCardsGruposSinRelacionar(gruposCabeceras,programaId,idiomaId,modalidadId,tipoGrupoId);

        HashMap<String,HashMap<String,Object>> gruposMultisedeCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposMultisedeCabeceras = new ArrayList<>();
        crearCardsGrupos(gruposMultisede,gruposMultisedeCabecerasMap,gruposMultisedeCabeceras,true,false);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("gruposCabeceras",gruposCabeceras);
        responseBody.put("gruposMultisedeCabeceras",gruposMultisedeCabeceras);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/categorias/{familiaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getCategorias(@PathVariable Integer familiaId, ServletRequest req) throws SQLException {

        List<ArticuloCategoriaCardProjection> categorias = articuloCategoriaDao.findProjectedCardAllByFamiliaIdAndVentaTrue(familiaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("categorias",categorias);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/subcategoriass/{categoriaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getSubcategorias(@PathVariable Integer categoriaId, ServletRequest req) throws SQLException {

        List<ArticuloSubcategoriaCardProjection> subcategorias = articuloSubcategoriaDao.findProjectedCardAllByCategoriaIdAndVentaTrue(categoriaId);
        List<ArticuloCardProjection> articulos = new ArrayList<>();
        if(subcategorias.size() == 0){
            articulos = articuloDao.findProjectedCardAllByCategoriaIdAndVentaTrue(categoriaId);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("subcategorias",subcategorias);
        responseBody.put("articulos",articulos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/articulos/{subcategoriaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getArticulos(@PathVariable Integer subcategoriaId, ServletRequest req) throws SQLException {

        List<ArticuloCardProjection> articulos = articuloDao.findProjectedCardAllBySubcategoriaIdAndVentaTrue(subcategoriaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("articulos",articulos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/precios/{clienteId}/{sucursalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getListaPreciosCliente(@PathVariable Integer clienteId, @PathVariable Integer sucursalId, ServletRequest req) throws SQLException {

        Cliente cliente = clienteDao.findClienteById(clienteId);
        String listaPreciosMapStr = listadoPrecioDao.getListaPreciosMapStr(cliente.getListadoPrecioId(),sucursalId);
        String listaPreciosSinDescuentoMapStr = listadoPrecioDao.getListaPreciosSinDescuentoMapStr(cliente.getListadoPrecioId());

        BigDecimal tipoCambio = monedaParidadDao.getTipoCambio(cliente.getListadoPrecio().getMonedaId(),new Date());
        if(tipoCambio == null){
            tipoCambio = BigDecimal.ONE;
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("listaPreciosMapStr",listaPreciosMapStr);
        responseBody.put("listaPreciosSinDescuentoMapStr",listaPreciosSinDescuentoMapStr);
        responseBody.put("listaPreciosClienteId",cliente.getListadoPrecioId());
        responseBody.put("simboloMoneda",cliente.getListadoPrecio().getMoneda().getSimbolo());
        responseBody.put("prefijoMoneda",cliente.getListadoPrecio().getMoneda().getCodigo());
        responseBody.put("tipoCambio",tipoCambio);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/alumnos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getListaPreciosCliente(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String nombreBuscar = requestBody.get("busqueda");

        List<AlumnoComboProjection> alumnos;

        if(nombreBuscar.trim().length() == 0){
            alumnos = alumnoDao.findProjectedComboAllByTop25();
        }else{
            alumnos = alumnoDao.findProjectedComboAllByNombreCompletoCoincide(nombreBuscar);
        }


        return new JsonResponse(alumnos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/reinscripciones/modalidades/{idiomaId}/{programaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getReinscripcionesModalidades(@PathVariable Integer idiomaId, @PathVariable Integer programaId, ServletRequest req) throws SQLException {

        List<PAModalidadComboSimpleProjection> paModalidades = paModalidadDao.findProjectedComboSimpleAllByActivoTrueAndIdiomaIdAndProgramaId(idiomaId,programaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("modalidades",paModalidades);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/reinscripciones/horarios/{modalidadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getReinscripcionesHorarios(@PathVariable Integer modalidadId, ServletRequest req) throws SQLException {

        List<PAModalidadHorarioComboProjection> horarios = paModalidadHorarioDao.findProjectedComboAllByModalidadIdOrderByCodigo(modalidadId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("horarios",horarios);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/alumnos-sin-grupo/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getAlumnosSinGrupo(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<Integer> estatusIds = Arrays.asList(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO,ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
        List<InscripcionSinGrupoListadoProjection> inscripcionesSinGrupo = inscripcionSinGrupoDao.findProjectedListadoAllBySucursalIdAndEstatusIdIn(Arrays.asList(sucursalId),estatusIds,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("inscripcionesSinGrupo",inscripcionesSinGrupo);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/planteles/{sucursalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getPlanteles(@PathVariable Integer sucursalId, ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<SucursalPlantelComboProjection> planteles = sucursalPlantelDao.findAllByPermisosUsuarioAndSucursalId(usuarioId,sucursalId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("planteles",planteles);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/alumnos-entrega-libros/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getAlumnosEntregaLibros(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<AlumnoEntregarLibrosProjection> alumnos = alumnoDao.findProjectedEntregarLibrosAllBySucursalId(sucursalId,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumnos",alumnos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/alumnos-inscripciones-pendientes-jobs/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getAlumnosInscripcionesPendientesJOBS(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<AlumnoInscripcionesPendientesJOBSProjection> alumnos = alumnoDao.findProjectedInscripcionesPendientesJOBSAllBySucursalId(sucursalId,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumnos",alumnos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/alumnos-inscripciones-pendientes-jobs-sems/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getAlumnosInscripcionesPendientesJOBSSEMS(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        filtro = "%" + filtro + "%";

        List<AlumnoInscripcionesPendientesJOBSSEMSProjection> alumnos = alumnoDao.findProjectedInscripcionesPendientesJOBSSEMSAllBySucursalId(sucursalId,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumnos",alumnos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/becas/sindicato", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getBecasSindicato(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<Integer> tiposIds = Arrays.asList(ControlesMaestrosMultiples.CMM_BECU_Tipo.STAUDG,ControlesMaestrosMultiples.CMM_BECU_Tipo.SUTUDG);

        List<BecaUDGListadoProjection> becas = becaUDGDao.findProjectedListadoByTipoIdInAndFiltro(tiposIds,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("becas",becas);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/becas/proulex", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getBecasProulex(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<Integer> tiposIds = Arrays.asList(ControlesMaestrosMultiples.CMM_BECU_Tipo.PROULEX);

        List<BecaUDGListadoProjection> becas = becaUDGDao.findProjectedListadoByTipoIdInAndFiltro(tiposIds,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("becas",becas);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/historial", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getHistorial(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        Boolean tienePermisoVisualizarPorPermisoSede = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_ORDENES_DE_VENTA_POR_PERMISOS_DE_SEDE);

        String fecha = requestBody.get("fecha");
        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<OrdenVentaHistorialPVProjection> ordenesVenta;
        if(tienePermisoVisualizarPorPermisoSede){
            List<Integer> sucursalesIds = sucursalDao.findIdsByUsuarioPermisosId(usuarioId);
            ordenesVenta = ordenVentaDao.findProjectedHistorialPVByFechaAndFiltro(sucursalesIds,fecha,filtro,offset,top);
        }else{
            ordenesVenta = ordenVentaDao.findProjectedHistorialPVByFechaAndFiltro(usuarioId,fecha,filtro,offset,top);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenesVenta",ordenesVenta);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/pcp/alumnos/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getAlumnosInscripcionesPendientesPCP(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        filtro = "\"*" + filtro + "*\"";

        List<AlumnoInscripcionPendientePCPProjection> alumnos = alumnoDao.findProjectedInscripcionPendientePCPAllBySucursalId(sucursalId,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumnos",alumnos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/alumno/{idiomaId}/{programaId}/{modalidadId}/{tipoGrupoId}/{alumnoId}/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosAlumno(@PathVariable Integer idiomaId, @PathVariable Integer programaId, @PathVariable Integer modalidadId, @PathVariable Integer tipoGrupoId, @PathVariable Integer alumnoId, @PathVariable Integer grupoId, ServletRequest req) throws SQLException {

        ControlMaestroMultipleCardProjection idioma = controlMaestroMultipleDao.findProjectedCardById(idiomaId);
        ProgramaCardProjection programa = programaDao.findProjectedCardById(programaId);
        PAModalidadCardProjection paModalidad = paModalidadDao.findProjectedCardById(modalidadId);
        AlumnoComboProjection alumno = alumnoDao.findProjectedComboById(alumnoId);
        Integer articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadId(programaId,idiomaId,modalidadId);
        ProgramaGrupoCardProjection programaGrupo = programaGrupoDao.findProjectedCardById(grupoId);
        ControlMaestroMultipleCardProjection tipoGrupo = controlMaestroMultipleDao.findProjectedCardById(tipoGrupoId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("idioma",idioma);
        responseBody.put("programa",programa);
        responseBody.put("paModalidad",paModalidad);
        responseBody.put("articuloId",articuloId);
        responseBody.put("alumno",alumno);
        responseBody.put("programaGrupo",programaGrupo);
        responseBody.put("tipoGrupo",tipoGrupo);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/alumno/certificacion/{certificacionId}/{listaPreciosId}/{localidadId}/{idTmp}/{sucursalId}/{alumnoId}/{cantidadInt}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosAlumnoCertificacion(@PathVariable Integer certificacionId, @PathVariable Integer listaPreciosId, @PathVariable Integer localidadId, @PathVariable Integer idTmp, @PathVariable Integer sucursalId, @PathVariable Integer alumnoId, @PathVariable Integer cantidadInt, ServletRequest req) throws SQLException {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        Integer articuloId = certificacionId;
        BigDecimal cantidad = new BigDecimal(cantidadInt);
        BigDecimal descuentoSinConvertir = BigDecimal.ZERO;
        Integer programaId = null;
        Integer idiomaId = null;
        Integer modalidadId = null;
        Integer grupoId = null;
        Integer numeroGrupo = null;
        Integer horarioId = null;
        String comentarioReinscripcion = null;
        Integer nivel = null;
        Integer becaUDGId = null;
        Integer id = null;

        ProgramaGrupo grupo = null;

        Articulo articulo = articuloDao.findById(articuloId);
        BigDecimal precioVenta = articuloDao.findPrecioVenta(articuloId,articuloId,listaPreciosId);
        BigDecimal precioUnitarioSinConvertir;
        BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
        BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        if(articulo.getIepsCuotaFija() == null){
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
        }else{
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
        }

        Programa programa = null;
        ControlMaestroMultipleComboProjection idioma = null;
        PAModalidad modalidad = null;
        Alumno alumno = null;
        String descripcion = articulo.getNombreArticulo();

        alumno = alumnoDao.findById(alumnoId);
        descripcion = articulo.getNombreArticulo();

        Integer porcentajeDescuento = articuloDao.getArticuloDescuento(articuloId,sucursalId);
        descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply((new BigDecimal(porcentajeDescuento)).divide(new BigDecimal(100))).setScale(6);

        OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articuloId);
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(cantidad);
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(descuentoSinConvertir);
        ordenVentaDetalle.setIva(tasaIva);
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(tasaIeps);
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(descripcion);
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumnoId != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);
        ordenVentaDetalle.setBecaUDGId(becaUDGId);

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(cantidad,precioUnitarioSinConvertir,descuentoSinConvertir,tasaIva,tasaIeps,articulo.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        ordenVentaDetalle.setId(id);

        ordenVentaDetalle.setSucursalId(sucursalId);
        ordenVentaDetalle.setProgramaId(programaId);
        ordenVentaDetalle.setIdiomaId(idiomaId);
        ordenVentaDetalle.setModalidadId(modalidadId);
        ordenVentaDetalle.setHorarioId(horarioId);
        ordenVentaDetalle.setNivel(nivel);
        ordenVentaDetalle.setNumeroGrupo(numeroGrupo);
        ordenVentaDetalle.setComentarioReinscripcion(comentarioReinscripcion);
        detallesResponse.add(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/reinscripciones/{sucursalId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosReinscripciones(@PathVariable Integer sucursalId, @RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        // TODO: averiguar porque se duplican alumnos aprobados
        List<AlumnoReinscripcionProjection> alumnosReinscripcion = alumnoDao.findReinscripcionProjectionAllBySucursalIdAndFiltros(sucursalId,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumnosReinscripcion",alumnosReinscripcion);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/reinscripciones/grupos/{sucursalId}/{idiomaId}/{programaId}/{modalidadId}/{horarioId}/{nivel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getGruposAlumnoReinscripcion(@PathVariable Integer sucursalId, @PathVariable Integer idiomaId, @PathVariable Integer programaId, @PathVariable Integer modalidadId, @PathVariable Integer horarioId, @PathVariable Integer nivel, ServletRequest req) throws SQLException {

        ProgramaIdioma curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);

        List<ProgramaGrupoCardProjection> grupos = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndHorarioIdAndNivelAndFechaFin(sucursalId,idiomaId,programaId,modalidadId,horarioId,nivel);
        List<ProgramaGrupoCardProjection> gruposMultisede = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndHorarioIdAndNivelAndMultisedeAndFechaFin(sucursalId,idiomaId,programaId,modalidadId,horarioId,nivel);

        HashMap<String,HashMap<String,Object>> gruposCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposCabeceras = new ArrayList<>();
        crearCardsGrupos(grupos,gruposCabecerasMap,gruposCabeceras,false,true);

        crearCardsGruposSinRelacionar(gruposCabeceras,programaId,idiomaId,modalidadId);

        HashMap<String,HashMap<String,Object>> gruposMultisedeCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposMultisedeCabeceras = new ArrayList<>();
        crearCardsGrupos(gruposMultisede,gruposMultisedeCabecerasMap,gruposMultisedeCabeceras,true,true);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("gruposCabeceras",gruposCabeceras);
        responseBody.put("gruposMultisedeCabeceras",gruposMultisedeCabeceras);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/alumnos-sin-grupo/{inscripcionSinGrupoIdHash}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosAlumnoSinGrupo(@PathVariable("inscripcionSinGrupoIdHash") String inscripcionSinGrupoIdHash) {

        Integer inscripcionSinGrupoId = hashId.decode(inscripcionSinGrupoIdHash);

        List<ControlMaestroMultipleComboSimpleProjection> tiposGrupo;

        InscripcionSinGrupo inscripcionSinGrupo = inscripcionSinGrupoDao.findById(inscripcionSinGrupoId);
        ProgramaIdioma curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(inscripcionSinGrupo.getProgramaId(),inscripcionSinGrupo.getIdiomaId());
        if(curso.getAgruparListadosPreciosPorTipoGrupo()){
            tiposGrupo = Arrays.asList(controlMaestroMultipleDao.findComboSimpleProjectionById(inscripcionSinGrupo.getTipoGrupoId()));
        }else{
            tiposGrupo = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("tiposGrupo",tiposGrupo);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/alumnos-sin-grupo/{inscripcionSinGrupoIdHash}/{tipoGrupoIdHash}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosAlumnoSinGrupo(@PathVariable("inscripcionSinGrupoIdHash") String inscripcionSinGrupoIdHash, @PathVariable("tipoGrupoIdHash") String tipoGrupoIdHash) {

        Integer inscripcionSinGrupoId = hashId.decode(inscripcionSinGrupoIdHash);
        Integer tipoGrupoId = hashId.decode(tipoGrupoIdHash);

        List<ProgramaGrupoComboProjection> grupos = new ArrayList<>();

        List<Integer> gruposIds = programaGrupoDao.findIdsByInscripcionSinGrupoIdAndTipoGrupoId(inscripcionSinGrupoId,tipoGrupoId);
        if(gruposIds.size() > 0){
            grupos = programaGrupoDao.findCombosByViewAndIdInAndCupoDisponible(gruposIds);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("grupos",grupos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/datos/alumno-beca-sindicato/{sucursalId}/{alumnoId}/{becaUDGId}","/datos/alumno-beca-proulex/{sucursalId}/{alumnoId}/{becaUDGId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getDatosAlumnoBecaSindicato(@PathVariable Integer sucursalId, @PathVariable Integer alumnoId, @PathVariable Integer becaUDGId, ServletRequest req) throws SQLException {

        AlumnoComboProjection alumno = alumnoDao.findProjectedComboById(alumnoId);
        BecaUDG becaUDG = becaUDGDao.findById(becaUDGId);

        List<AlumnoReinscripcionProjection> reinscripciones = alumnoDao.findReinscripcionProjectionByAprobadosGeneral(alumnoId,becaUDG.getProgramaIdioma().getProgramaId(),becaUDG.getProgramaIdioma().getIdiomaId());

        if(reinscripciones.size() == 1){
            AlumnoReinscripcionProjection reinscripcion = reinscripciones.get(0);
            if(reinscripcion.getNivelReinscripcion().intValue() != becaUDG.getNivel().intValue()){
                return new JsonResponse(null,"El alumno cuenta con una reinscripción pendiente a un nivel diferente.",JsonResponse.STATUS_ERROR_DATOS_NO_COINCIDEN);
            }

            HashMap<String,Object> responseBody = new HashMap<>();
            responseBody.put("procesarReinscripcion",true);
            responseBody.put("alumnoId", alumno.getId());
            responseBody.put("grupoId", reinscripcion.getGrupoReinscripcionId());
            responseBody.put("becaId", becaUDG.getId());

            return new JsonResponse(responseBody,"El alumno cuenta con una reinscripción, estamos procesándola.",JsonResponse.STATUS_OK);
        }else if(reinscripciones.size() > 1){
            return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene múltiples reinscripciones pendientes.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,becaUDG.getProgramaIdioma().getIdiomaId(),null,false)){
            return new JsonResponse(null,"No es posible inscribir al alumno " + alumno.getCodigo() + " al idioma " + becaUDG.getProgramaIdioma().getIdioma().getValor() + " debido a que ya está cursando el mismo.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        List<ProgramaGrupoCardProjection> grupos = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndNivelAndFechaFinBeca(sucursalId,becaUDG.getProgramaIdioma().getIdiomaId(),becaUDG.getProgramaIdioma().getProgramaId(),becaUDG.getPaModalidadId(),becaUDG.getNivel());
        List<ProgramaGrupoCardProjection> gruposMultisede = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndMultisedeAndNivelAndFechaFinBeca(sucursalId,becaUDG.getProgramaIdioma().getIdiomaId(),becaUDG.getProgramaIdioma().getProgramaId(),becaUDG.getPaModalidadId(),becaUDG.getNivel());

        HashMap<String,HashMap<String,Object>> gruposCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposCabeceras = new ArrayList<>();
        crearCardsGrupos(grupos,gruposCabecerasMap,gruposCabeceras,false,true);

        HashMap<String,HashMap<String,Object>> gruposMultisedeCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposMultisedeCabeceras = new ArrayList<>();
        crearCardsGrupos(gruposMultisede,gruposMultisedeCabecerasMap,gruposMultisedeCabeceras,true,true);

        crearCardsGruposSinRelacionar(gruposCabeceras,becaUDG.getProgramaIdioma().getProgramaId(),becaUDG.getProgramaIdioma().getIdiomaId(),becaUDG.getPaModalidadId());

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("alumno",alumno);
        responseBody.put("gruposCabeceras",gruposCabeceras);
        responseBody.put("gruposMultisedeCabeceras",gruposMultisedeCabeceras);
        responseBody.put("idioma",becaUDG.getProgramaIdioma().getIdioma().getValor());
        responseBody.put("programa",becaUDG.getProgramaIdioma().getPrograma().getCodigo());
        responseBody.put("modalidad",becaUDG.getPaModalidad().getNombre());

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/historial/{ordenVentaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getHistorial(@PathVariable Integer ordenVentaId, ServletRequest req) throws SQLException {

        OrdenVentaHistorialPVResumenProjection ordenVenta = ordenVentaDao.findProjectedHistorialPVResumenById(ordenVentaId);
        List<OrdenVentaDetalleHistorialPVResumenProjection> detalles = ordenVentaDetalleDao.findProjectedHistorialPVResumenByOrdenVentaId(ordenVentaId);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("ordenVenta", ordenVenta);
        responseBody.put("detalles", detalles);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/crear/detalle-ov", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse crearOrdenVentaDetalle(@RequestBody List<HashMap<String,String>> requestBody, ServletRequest req) throws Exception {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        for(HashMap<String,String> elementoBody : requestBody){
            OrdenVentaDetalle ordenVentaDetalle = null;
            Integer articuloId = null;
            if(elementoBody.get("articuloId") != null){
                articuloId = Integer.parseInt(elementoBody.get("articuloId"));
            }
            BigDecimal cantidad = new BigDecimal(elementoBody.get("cantidad"));
            Integer listaPreciosId = Integer.parseInt(elementoBody.get("listaPreciosId"));
            BigDecimal descuentoSinConvertir = new BigDecimal(elementoBody.get("descuento"));
            Integer porcentajeDescuento = null;
            if(elementoBody.get("porcentajeDescuento") != null){
                porcentajeDescuento = Integer.parseInt(elementoBody.get("porcentajeDescuento"));
            }
            Integer programaId = null;
            Integer idiomaId = null;
            Integer modalidadId = null;
            Integer tipoGrupoId = null;
            Integer grupoId = null;
            Integer numeroGrupo = null;
            Integer horarioId = null;
            String comentarioReinscripcion = null;
            Integer sucursalId = null;
            Integer nivel = null;
            Integer alumnoId = elementoBody.get("alumnoId") != null ? (Integer.parseInt(elementoBody.get("alumnoId"))) : null;
            Integer becaUDGId = elementoBody.get("becaUDGId") != null ? (Integer.parseInt(elementoBody.get("becaUDGId"))) : null;
            Integer programaIdiomaCertificacionValeId = elementoBody.get("programaIdiomaCertificacionValeId") != null ? (Integer.parseInt(elementoBody.get("programaIdiomaCertificacionValeId"))) : null;
            Integer localidadId = elementoBody.get("localidadId") != null ? (Integer.parseInt(elementoBody.get("localidadId"))) : null;
            Integer idTmp = Integer.parseInt(elementoBody.get("idTmp"));
            Integer id = elementoBody.get("id") != null ? (Integer.parseInt(elementoBody.get("id"))) : null;
            Boolean alumnoReprobado = false;

            ProgramaGrupo grupo = null;

            if(elementoBody.get("grupoId") != null){
                grupoId = Integer.parseInt(elementoBody.get("grupoId"));
                grupo = programaGrupoDao.findById(grupoId);
                numeroGrupo = grupo.getGrupo();
                programaId = grupo.getProgramaIdioma().getProgramaId();
                idiomaId = grupo.getProgramaIdioma().getIdiomaId();
                modalidadId = grupo.getPaModalidadId();
                tipoGrupoId = grupo.getTipoGrupoId();

                ProgramaIdioma curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);
                if(curso.getAgruparListadosPreciosPorTipoGrupo()){
                    articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadIdAndTipoGrupoId(programaId,idiomaId,modalidadId,tipoGrupoId);
                }else{
                    articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadId(programaId,idiomaId,modalidadId);
                }
                if(articuloId == null){
                    return new JsonResponse(null,"No hay artículo configurado para el curso " + grupo.getProgramaIdioma().getPrograma().getCodigo() + " " + grupo.getProgramaIdioma().getIdioma().getValor() + " " + grupo.getPaModalidad().getNombre(),JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }

            Articulo articulo = articuloDao.findById(articuloId);
            BigDecimal precioVenta = articuloDao.findPrecioVenta(articuloId,articuloId,listaPreciosId);
            BigDecimal precioUnitarioSinConvertir;
            BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
            BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
            if(articulo.getIepsCuotaFija() == null){
                precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
            }else{
                precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
            }

            Programa programa = null;
            ControlMaestroMultipleComboProjection idioma = null;
            PAModalidad modalidad = null;
            Alumno alumno = null;
            String descripcion = articulo.getNombreArticulo();

            if(alumnoId != null){
                String articuloSubtipoIdStr = elementoBody.get("articuloSubtipoId");
                Integer articuloSubtipoId = 0;
                if(articuloSubtipoIdStr != null){
                    articuloSubtipoId = Integer.parseInt(elementoBody.get("articuloSubtipoId"));
                }
                alumno = alumnoDao.findById(alumnoId);
                List<AlumnoReinscripcionProjection> reinscripcionesAprobado = new ArrayList<>();
                AlumnoReinscripcionProjection reinscripcionAprobado = null;
                if(idiomaId == null && elementoBody.get("idiomaId") != null){
                    idiomaId = Integer.parseInt(elementoBody.get("idiomaId"));
                }
                if(programaId == null && elementoBody.get("programaId") != null){
                    programaId = Integer.parseInt(elementoBody.get("programaId"));
                }
                if(tipoGrupoId == null && elementoBody.get("tipoGrupoId") != null){
                    tipoGrupoId = Integer.parseInt(elementoBody.get("tipoGrupoId"));
                }
                if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idiomaId,null,false)) {
                    reinscripcionesAprobado = alumnoDao.findReinscripcionProjectionByAprobadosGeneral(alumnoId, programaId, idiomaId);
                }
                if(reinscripcionesAprobado.size() > 1){
                    return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene múltiples reinscripciones pendientes a este curso.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }else if(reinscripcionesAprobado.size() > 0){
                    reinscripcionAprobado = reinscripcionesAprobado.get(0);
                    Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndEstatusIdIn(reinscripcionAprobado.getId(),reinscripcionAprobado.getGrupoReinscripcionId(),Arrays.asList(ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO,ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA));
                    if(grupo != null && alumnoDao.getInterferenciaHorarioIgnorandoInscripcion(alumnoId,grupo.getModalidadHorarioId(),inscripcion.getId())){
                        return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                }else{
                    if(grupo != null && alumnoDao.getInterferenciaHorario(alumnoId,grupo.getModalidadHorarioId())){
                        return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                }
                if(articuloSubtipoId.intValue() == ArticulosSubtipos.EXAMEN || articuloSubtipoId.intValue() == ArticulosSubtipos.CERTIFICACION || articuloSubtipoId.intValue() == ArticulosSubtipos.CONSTANCIA || articuloSubtipoId.intValue() == ArticulosSubtipos.TUTORIA){
                    descripcion = articulo.getNombreArticulo();
                }else{

                    Integer nivelInscripcion = grupo != null ? grupo.getNivel() : Integer.parseInt(elementoBody.get("nivel"));
                    EstatusPermiteInscripcionPorExamenUbicacion estatusPermiteInscripcionPorExamenUbicacion = null;

                    if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idiomaId,null,false)){

                        AlumnoReinscripcionProjection reinscripcion = reinscripcionAprobado;

                        if(reinscripcion != null){
                            ProgramaGrupo grupoReinscripcion = programaGrupoDao.findById(reinscripcion.getGrupoReinscripcionId());
                            Boolean cambioGrupo = false;
                            Integer nuevoGrupoId = null;
                            Integer nuevaModalidadId = null;
                            Integer nuevoTipoGrupoId = null;
                            Integer nuevoHorarioId = null;
                            Integer nuevoNivel = null;
                            String comentario = null;
                            if(grupo == null || grupoReinscripcion.getId().intValue() != grupo.getId().intValue()){
                                cambioGrupo = true;
                            }
                            if(cambioGrupo){
                                if(grupo != null){
                                    nuevoGrupoId = grupo.getId();
                                }else{
                                    nuevaModalidadId = Integer.parseInt(elementoBody.get("modalidadId"));
                                    nuevoTipoGrupoId = Integer.parseInt(elementoBody.get("tipoGrupoId"));
                                    if(elementoBody.get("horarioId") != null){
                                        nuevoHorarioId = Integer.parseInt(elementoBody.get("horarioId"));
                                    }
                                    nuevoNivel = Integer.parseInt(elementoBody.get("nivel"));
                                    comentario = elementoBody.get("comentarioReinscripcion");
                                }
                            }

                            HashMap<String,Object> reinscripcionAprobadoRequestBody = new HashMap<>();
                            HashMap<String,Object> alumnoHashMap = new HashMap<>();

                            alumnoHashMap.put("id",reinscripcion.getId());
                            alumnoHashMap.put("grupoId",reinscripcion.getGrupoReinscripcionId());
                            alumnoHashMap.put("localidadId",localidadId);
                            alumnoHashMap.put("idTmp",idTmp);
                            alumnoHashMap.put("becaId",becaUDGId);
                            alumnoHashMap.put("cambioGrupo",cambioGrupo);
                            alumnoHashMap.put("nuevoGrupoId",nuevoGrupoId);
                            alumnoHashMap.put("nuevaModalidadId",nuevaModalidadId);
                            alumnoHashMap.put("nuevoTipoGrupoId",nuevoTipoGrupoId);
                            alumnoHashMap.put("nuevoHorarioId",nuevoHorarioId);
                            alumnoHashMap.put("nuevoNivel",nuevoNivel);
                            alumnoHashMap.put("comentario",comentario);

                            reinscripcionAprobadoRequestBody.put("alumno",alumnoHashMap);

                            JsonResponse jsonResponse = agregarAlumnoAprobadoReinscripciones(reinscripcionAprobadoRequestBody,req);
                            HashMap<String,Object> agregarAlumnoAprobadoResponseBody = (HashMap<String, Object>) jsonResponse.getData();
                            List<OrdenVentaDetalle> detalles = (List<OrdenVentaDetalle>) agregarAlumnoAprobadoResponseBody.get("ordenVentaDetalles");
                            ordenVentaDetalle = detalles.get(0);
                        }else if(grupo != null){
                            idioma = controlMaestroMultipleDao.findById(idiomaId);
                            return new JsonResponse(null,"No es posible inscribir al alumno " + alumno.getCodigo() + " al idioma " + idioma.getValor() + " debido a que ya está cursando el mismo.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                        }
                    }else{
                        estatusPermiteInscripcionPorExamenUbicacion = permiteInscripcionPorExamenUbicacion(alumnoId,programaId,idiomaId,nivelInscripcion);
                        if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.SI_PERMITE_NO_EXAMEN){
                            List<AlumnoReinscripcionProjection> reinscripcionesAprobados = alumnoDao.findReinscripcionProjectionByAprobadosSinProyeccion(alumnoId,programaId,idiomaId,null);
                            if(reinscripcionesAprobados.size() > 0){
                                reinscripcionAprobado = reinscripcionesAprobados.get(0);
                                Integer nivelPermisibleInscripcion;
                                if(grupo != null){
                                    nivelPermisibleInscripcion = grupo.getNivel();
                                }else{
                                    nivelPermisibleInscripcion = Integer.parseInt(elementoBody.get("nivel"));
                                }
                                if(grupo != null && (reinscripcionAprobado.getNivelReinscripcion().intValue()+1) < nivelPermisibleInscripcion.intValue()){
                                    String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                                            .append(alumno.getCodigo())
                                            .append(" al nivel ")
                                            .append(nivel)
                                            .append(". (Nivel máximo: ")
                                            .append(reinscripcionAprobado.getNivelReinscripcion()+1)
                                            .append(")")
                                            .toString();
                                    return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                                }
                            }else{
                                List<AlumnoReinscripcionProjection> reinscripcionesReprobados = alumnoDao.findReinscripcionProjectionByReprobadosAndIdAndProgramaIdAndIdiomaId(alumnoId,programaId,idiomaId);
                                if(reinscripcionesReprobados.size() > 0){
                                    AlumnoReinscripcionProjection reinscripcionReprobado = reinscripcionesReprobados.get(0);
                                    Integer nivelPermisibleInscripcion;
                                    if(grupo != null){
                                        nivelPermisibleInscripcion = grupo.getNivel();
                                    }else{
                                        nivelPermisibleInscripcion = Integer.parseInt(elementoBody.get("nivel"));
                                    }
                                    if(grupo != null && reinscripcionReprobado.getNivelReinscripcion().intValue() < nivelPermisibleInscripcion.intValue()){
                                        String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                                                .append(alumno.getCodigo())
                                                .append(" al nivel ")
                                                .append(nivel)
                                                .append(". (Nivel máximo: ")
                                                .append(reinscripcionReprobado.getNivelReinscripcion())
                                                .append(")")
                                                .toString();
                                        return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                                    }
                                }
                                alumnoReprobado = reinscripcionesReprobados.size() > 0;
                            }
                        }
                    }

                    if(estatusPermiteInscripcionPorExamenUbicacion == null){
                        estatusPermiteInscripcionPorExamenUbicacion = permiteInscripcionPorExamenUbicacion(alumnoId,programaId,idiomaId,nivelInscripcion);
                    }

                    // Si el alumno no tiene exámenes de ubicación en proceso, se valida el nivel de inscripcción por medio de inscripciones anteriores
                    if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.SI_PERMITE_NO_EXAMEN){
                        Integer nivelPermiteInscripcion = alumnoDao.getNivelAlumnoPermiteInscripcion(alumnoId,idiomaId,programaId);
                        // Si el alumno intenta inscribirse a un nivel superior permitido se cancela la inscripción y se informa al usuario
                        // TODO: descomentar cuando se necesite validar nivel
//                        if(nivelPermiteInscripcion < nivelInscripcion){
//                            String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
//                                    .append(alumno.getCodigo())
//                                    .append(" al nivel ")
//                                    .append(nivelInscripcion)
//                                    .append(". (Nivel máximo: ")
//                                    .append(nivelPermiteInscripcion)
//                                    .append(")")
//                                    .toString();
//                            return new JsonResponse(null, mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
//                        }
                    }
                    // Si tiene exámenes de ubicación en proceso, se cancela la inscripción y se informa al usuario
                    else if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_EN_PROCESO){
                        String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                                .append(alumno.getCodigo())
                                .append(" al nivel ")
                                .append(nivelInscripcion)
                                .append(". (Examen de ubicación en proceso)")
                                .toString();
                        return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                    // Si el alumno intenta inscribirse a un nivel superior al indicado en un examen de ubicación se cancela la inscripción y se informa al usuario
                    else if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_NIVEL_SUPERIOR){
                        String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                                .append(alumno.getCodigo())
                                .append(" al nivel ")
                                .append(nivelInscripcion)
                                .append(". (Nivel máximo: ")
                                .append(getNivelPermisibleExamenUbicacion(alumnoId,programaId,idiomaId))
                                .append(")")
                                .toString();
                        return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }

                    if(modalidadId == null){
                        modalidadId = Integer.parseInt(elementoBody.get("modalidadId"));
                    }
                    if(elementoBody.get("grupoId") == null){
                        if(elementoBody.get("numeroGrupo") != null){
                            numeroGrupo = Integer.parseInt(elementoBody.get("numeroGrupo"));
                        }else{
                            numeroGrupo = 1;
                        }
                        comentarioReinscripcion = elementoBody.get("comentarioReinscripcion");
                        sucursalId = Integer.parseInt(elementoBody.get("sucursalId"));
                        nivel = Integer.parseInt(elementoBody.get("nivel"));
                        if(elementoBody.get("horarioId") != null){
                            horarioId = Integer.parseInt(elementoBody.get("horarioId"));
                        }else{
                            horarioId = null;
                        }
                        List<ProgramaGrupo> gruposCoincidencias = programaGrupoDao.findAllByActivoTrueAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndHorarioIdAndNivel(idiomaId,programaId,modalidadId,tipoGrupoId,horarioId,nivel);
                        ProgramaGrupo primerGrupoConCupo = null;
                        for(ProgramaGrupo grupoCoincidencia : gruposCoincidencias){
                            if(programaGrupoDao.getCupoDisponible(grupoCoincidencia.getId()).intValue() > 0){
                                if(grupoCoincidencia.getGrupo().intValue() == numeroGrupo.intValue()){
                                    grupo = grupoCoincidencia;
                                    break;
                                }else if(primerGrupoConCupo == null){
                                    primerGrupoConCupo = grupoCoincidencia;
                                }
                            }
                        }
                        if(grupo == null && primerGrupoConCupo != null){
                            grupo = primerGrupoConCupo;
                            numeroGrupo = grupo.getGrupo();
                        }
                    }

                    programa = programaDao.findById(programaId);
                    idioma = controlMaestroMultipleDao.findById(idiomaId);
                    modalidad = paModalidadDao.findById(modalidadId);
                    if(grupo != null){
                        if (programa.getAcademy())
                            descripcion = grupo.getSucursal().getNombre() + " " + grupo.getProgramaIdioma().getCodigo() + grupo.getProgramaIdioma().getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + numeroGrupo;
                        else
                            descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + numeroGrupo;
                    }else{
                        Sucursal sucursal = sucursalDao.findById(Integer.parseInt(elementoBody.get("sucursalId")));
                        descripcion = sucursal.getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + nivel;
                    }
                }
            }

            if(becaUDGId != null){
                BecaUDG becaUDG = becaUDGDao.findById(becaUDGId);
                descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply(becaUDG.getDescuento()).setScale(6);
            }else if(programaIdiomaCertificacionValeId != null){
                ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = programaIdiomaCertificacionValeDao.findById(programaIdiomaCertificacionValeId);
                descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply(programaIdiomaCertificacionVale.getPorcentajeDescuento().divide(new BigDecimal(100))).setScale(6);
            }else{
                if(alumnoReprobado){
                    if((programa.getJobs() == null || !programa.getJobs()) && (programa.getJobsSems() == null || !programa.getJobsSems())){
                        descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply((new BigDecimal(50)).divide(new BigDecimal(100))).setScale(6);
                    }
                }else{
                    if(porcentajeDescuento == null){
                        porcentajeDescuento = articuloDao.getArticuloDescuento(articuloId,Integer.parseInt(elementoBody.get("sucursalId")));
                    }
                    descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply((new BigDecimal(porcentajeDescuento)).divide(new BigDecimal(100))).setScale(6);
                }
            }

            if(ordenVentaDetalle == null){
                ordenVentaDetalle = new OrdenVentaDetalle();
                ordenVentaDetalle.setArticuloId(articuloId);
                ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
                ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
                ordenVentaDetalle.setCantidad(cantidad);
                ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
                ordenVentaDetalle.setDescuentoSinConvertir(descuentoSinConvertir);
                ordenVentaDetalle.setIva(tasaIva);
                ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
                ordenVentaDetalle.setIeps(tasaIeps);
                ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

                ordenVentaDetalle.setDescripcion(descripcion);
                ordenVentaDetalle.setAlumnoId(alumnoId);
                ordenVentaDetalle.setNombreAlumno(alumnoId != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);
                ordenVentaDetalle.setBecaUDGId(becaUDGId);
                ordenVentaDetalle.setProgramaIdiomaCertificacionValeId(programaIdiomaCertificacionValeId);

                MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(cantidad,precioUnitarioSinConvertir,descuentoSinConvertir,tasaIva,tasaIeps,articulo.getIepsCuotaFija());
                ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
                ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
                ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
                ordenVentaDetalle.setTotal(montosCalculados.getTotal());

                ordenVentaDetalle.setLocalidadId(localidadId);

                ordenVentaDetalle.setIdTmp(idTmp);

                ordenVentaDetalle.setId(id);

                if(grupo != null){
                    ordenVentaDetalle.setGrupoId(grupo.getId());
                    ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());
                }else if(alumno != null){
                    ordenVentaDetalle.setSucursalId(sucursalId);
                    ordenVentaDetalle.setProgramaId(programaId);
                    ordenVentaDetalle.setIdiomaId(idiomaId);
                    ordenVentaDetalle.setModalidadId(modalidadId);
                    ordenVentaDetalle.setTipoGrupoId(tipoGrupoId);
                    ordenVentaDetalle.setHorarioId(horarioId);
                    ordenVentaDetalle.setNivel(nivel);
                    ordenVentaDetalle.setNumeroGrupo(numeroGrupo);
                    ordenVentaDetalle.setComentarioReinscripcion(comentarioReinscripcion);
                }
            }
            detallesResponse.add(ordenVentaDetalle);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/relacionar/alumno-sin-grupo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse relacionarAlumnoSinGrupo(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer inscripcionSinGrupoId = Integer.parseInt(requestBody.get("inscripcionSinGrupoId"));
        Integer grupoId = Integer.parseInt(requestBody.get("grupoId"));

        InscripcionSinGrupo inscripcionSinGrupo = inscripcionSinGrupoDao.findById(inscripcionSinGrupoId);

        if(inscripcionSinGrupo.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA){
            return new JsonResponse(null,"No es posible relacionar el alumno al grupo, la inscripción no ha sido pagada aún.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        Integer alumnoId = inscripcionSinGrupo.getAlumnoId();
        Integer programaId = grupo.getProgramaIdioma().getProgramaId();
        Integer idiomaId = grupo.getProgramaIdioma().getIdiomaId();
        Alumno alumno = alumnoDao.findById(alumnoId);

        // Validar que el grupo aún tenga cupo disponible
        if(programaGrupoDao.getCupoDisponible(grupo.getId()).intValue() <= 0){
            return new JsonResponse(null,"El grupo ya no cuenta con cupo disponible.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        // Validar que el alumno no tenga una inscripción pagada y activa en el mismo idioma
        if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idiomaId,null,true)){
            return new JsonResponse(null,"No es posible inscribir al alumno " + alumno.getCodigo() + " al idioma " + grupo.getProgramaIdioma().getIdioma().getValor() + " debido a que ya está cursando el mismo.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }else if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idiomaId,programaId,false)){
            List<AlumnoReinscripcionProjection> reinscripciones = alumnoDao.findReinscripcionProjectionByAprobadosGeneral(alumnoId,programaId,idiomaId);
            if(reinscripciones.size() > 1){
                return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene múltiples reinscripciones pendientes al curso " + grupo.getProgramaIdioma().getPrograma().getCodigo() + " " + grupo.getProgramaIdioma().getIdioma().getValor() + ".",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }else if(reinscripciones.size() == 1){
                AlumnoReinscripcionProjection reinscripcion = reinscripciones.get(0);
                Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumno.getId(),reinscripcion.getGrupoReinscripcionId());

                // Validar que el alumno no tenga horarios cruzados
                if(grupo != null && alumnoDao.getInterferenciaHorarioIgnorandoInscripcion(alumnoId,grupo.getModalidadHorarioId(),inscripcion.getId())){
                    return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }

                inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
                inscripcion.setModificadoPorId(idUsuario);
                inscripcionDao.saveAndFlush(inscripcion);
            }
        }else{
            // Validar que el alumno no tenga horarios cruzados
            if(grupo != null && alumnoDao.getInterferenciaHorario(alumnoId,grupo.getModalidadHorarioId())){
                return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            // Validar que el nivel de la inscripción no supere al nivel al que el alumno aprobó (en caso de haber aprobado un nivel en el curso)
            List<AlumnoReinscripcionProjection> reinscripcionesAprobados = alumnoDao.findReinscripcionProjectionByAprobadosSinProyeccion(alumnoId,programaId,idiomaId,null);
            if(reinscripcionesAprobados.size() > 0){
                AlumnoReinscripcionProjection reinscripcionAprobado = reinscripcionesAprobados.get(0);
                if((reinscripcionAprobado.getNivelReinscripcion().intValue()+1) < grupo.getNivel().intValue()){
                    String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                            .append(alumno.getCodigo())
                            .append(" al nivel ")
                            .append(grupo.getNivel())
                            .append(". (Nivel máximo: ")
                            .append(reinscripcionAprobado.getNivelReinscripcion()+1)
                            .append(")")
                            .toString();
                    return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }else{
                // Validar que el nivel de la inscripción no supere al nivel al que el alumno reprobó (en caso de haber reprobado un nivel en el curso)
                List<AlumnoReinscripcionProjection> reinscripcionesReprobados = alumnoDao.findReinscripcionProjectionByReprobadosAndIdAndProgramaIdAndIdiomaId(alumnoId,programaId,idiomaId);
                if(reinscripcionesReprobados.size() > 0){
                    AlumnoReinscripcionProjection reinscripcionReprobado = reinscripcionesReprobados.get(0);
                    if(reinscripcionReprobado.getNivelReinscripcion().intValue() < grupo.getNivel().intValue()){
                        String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                                .append(alumno.getCodigo())
                                .append(" al nivel ")
                                .append(grupo.getNivel())
                                .append(". (Nivel máximo: ")
                                .append(reinscripcionReprobado.getNivelReinscripcion())
                                .append(")")
                                .toString();
                        return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                }
            }
        }

        inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.ASIGNADA);
        inscripcionSinGrupo.setModificadoPorId(idUsuario);
        inscripcionSinGrupoDao.save(inscripcionSinGrupo);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("INS"));
        inscripcion.setOrdenVentaDetalleId(inscripcionSinGrupo.getOrdenVentaDetalleId());
        inscripcion.setAlumnoId(inscripcionSinGrupo.getAlumnoId());
        inscripcion.setGrupoId(grupoId);
        inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
        inscripcion.setEntregaLibrosPendiente(false);
        inscripcion.setBecaUDGId(inscripcionSinGrupo.getBecaUDGId());
        inscripcion.setCreadoPorId(idUsuario);
        inscripcion.setOrigenId(ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PUNTO_DE_VENTA);
        inscripcion = inscripcionDao.save(inscripcion);

        OrdenVenta ordenVenta = ordenVentaDao.findByDetalleId(inscripcion.getOrdenVentaDetalleId());

        if (ordenVenta.getSucursalId() != grupo.getSucursalId()){
            //Iniciar alerta
            String mensaje = "Alumno: "+alumno.getCodigo()+"\n Grupo: "+grupo.getCodigoGrupo();
            procesadorAlertasService.validarAutorizacion(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,inscripcion.getId(), inscripcion.getCodigo(), "Inscripción Multisede", grupo.getSucursalId(), idUsuario, mensaje);
        }

        AlumnoGrupo alumnoGrupo = new AlumnoGrupo();

        alumnoGrupo.setAlumnoId(inscripcionSinGrupo.getAlumnoId());
        alumnoGrupo.setGrupoId(grupoId);
        alumnoGrupo.setAsistencias(0);
        alumnoGrupo.setFaltas(0);
        alumnoGrupo.setMinutosRetardo(0);
        alumnoGrupo.setCreadoPorId(1);
        alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REGISTRADO);
        alumnoGrupo.setInscripcionId(inscripcion.getId());

        alumnoGrupoDao.save(alumnoGrupo);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("inscripcionRelacionada",true);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cobrar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cobrar(@RequestBody OrdenVenta ordenVenta, ServletRequest req) throws Exception {
        // Validar inscripción a nivel anterior válida al afectar inventario

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(ordenVenta.getMonto().compareTo(BigDecimal.ZERO) == 0 && (ordenVenta.getMedioPagoPVId() == null || ordenVenta.getMedioPagoPVId().equals(0))){
            ordenVenta.setMedioPagoPVId(MediosPagoPV.EFECTIVO);
        }

        SucursalCorteCaja sucursalCorteCaja = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(idUsuario);
        ListadoPrecio listadoPrecio = listadoPrecioDao.findById(ordenVenta.getListaPreciosId());

        SucursalCorteCaja corteAbierto = sucursalCorteCajaDao.findByUsuarioAbreIdAndFechaFinIsNull(idUsuario);
        if(corteAbierto == null){
            return new JsonResponse(null, "No existe ningún turno abierto para el usuario actual", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
        }

        Date fechaActual = new Date();
        Moneda monedaPredeterminada = monedaDao.findByPredeterminadaTrue();
        SucursalPlantel plantel = null;
        if(ordenVenta.getPlantelId() != null){
            plantel = sucursalPlantelDao.findById(ordenVenta.getPlantelId());
        }

        // Separar detalles de OV que ya están relacionados de los nuevos
        List<OrdenVentaDetalle> detallesOV = new ArrayList<>();
        List<OrdenVenta> ordenesVentaActualizar = new ArrayList<>();
        HashMap<Integer,OrdenVentaDetalle> detallesActualizarMap = new HashMap<>();
        JsonResponse jsonResponsSepararDetallesOVAlCobrar = separarDetallesOVAlCobrar(ordenVenta, detallesOV, ordenesVentaActualizar, detallesActualizarMap,listadoPrecio.getId());
        if(jsonResponsSepararDetallesOVAlCobrar != null){
            return jsonResponsSepararDetallesOVAlCobrar;
        }

        Sucursal sucursal = sucursalDao.findById(ordenVenta.getSucursalId());
        String anio = DateUtil.getFecha(new Date(),"yyyy");
        ordenVenta.setCodigo(ordenVentaDao.getSiguienteCodigoOV(sucursal.getPrefijo()+anio));
        ordenVenta.setFechaOV(fechaActual);
        ordenVenta.setFechaRequerida(fechaActual);
        ordenVenta.setMonedaId(monedaPredeterminada.getId());
        ordenVenta.setMonedaSinConvertirId(listadoPrecio.getMonedaId());
        ordenVenta.setDiazCredito(0);
        ordenVenta.setCreadoPorId(idUsuario);
        if(ordenVenta.getMedioPagoPVId() == MediosPagoPV.EFECTIVO
                || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_CREDITO
                || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_DEBITO
                || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TRANSFERENCIA_BANCARIA
                || ordenVenta.getMedioPagoPVId() == MediosPagoPV.ORDEN_DE_PAGO
                || ordenVenta.getMedioPagoPVId() == MediosPagoPV.PAGO_EN_VENTANILLA
                ){
            ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
            ordenVenta.setMetodoPagoId(ControlesMaestrosMultiples.CMM_OV_MetodoPago.PUE);
            if(ordenVenta.getFechaOV() == null){
                return new JsonResponse(null, "Fecha de pago no encontrada", JsonResponse.STATUS_ERROR_PUNTO_VENTA_SUCURSAL);
            }
        }else{
            ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.ABIERTA);
            ordenVenta.setFechaPago(null);
        }
        ordenVenta.setSucursalCorteCajaId(sucursalCorteCaja.getId());

        OrdenVenta ordenVentaGuardada = null;
        JSONArray etiquetasCentroPagos = null;

        // vvvvv Generar las etiquetas para la liga de pagos en caso de que el método sea Centro de pagos vvvvv
        if(ordenVenta.getDetalles().size() > 0){
            ordenVentaGuardada = desglosarOV(ordenVenta);

            if(ordenVentaGuardada.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS){

                if(detallesOV.size() == 1){
                    OrdenVentaDetalle ovd = detallesOV.get(0);

                    if(ovd.getDetallePadreId() == null){
                        if(ovd.getAlumnoId() != null && ovd.getGrupoId() != null){
                            Alumno alumno = alumnoDao.findById(ovd.getAlumnoId());
                            ProgramaGrupo grupo = programaGrupoDao.findById(ovd.getGrupoId());

                            etiquetasCentroPagos = centroPagosService.crearEtiquetasInscripcion(
                                    ordenVenta.getSucursalId(),
                                    grupo.getProgramaIdioma().getIdiomaId(),
                                    grupo.getProgramaIdioma().getProgramaId(),
                                    grupo.getPaModalidadId(),
                                    grupo.getModalidadHorarioId(),
                                    grupo.getNivel(),
                                    ordenVenta.getMonto(),
                                    alumno.getCodigo(),
                                    alumno.getNombre(),
                                    alumno.getPrimerApellido(),
                                    alumno.getSegundoApellido()
                            );
                        }else if(ovd.getAlumnoId() != null){
                            Articulo articulo = articuloDao.findById(ovd.getArticuloId());
                            Alumno alumno = alumnoDao.findById(ovd.getAlumnoId());

                            if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.EXAMEN || articulo.getArticuloSubtipoId() == ArticulosSubtipos.CERTIFICACION || articulo.getArticuloSubtipoId() == ArticulosSubtipos.CONSTANCIA || articulo.getArticuloSubtipoId() == ArticulosSubtipos.TUTORIA){
                                etiquetasCentroPagos = centroPagosService.crearEtiquetasVentaCertificacion(
                                        ordenVenta.getSucursalId(),
                                        ordenVenta.getMonto(),
                                        alumno.getCodigo(),
                                        alumno.getNombre(),
                                        alumno.getPrimerApellido(),
                                        alumno.getSegundoApellido(),
                                        articulo.getNombreArticulo(),
                                        ovd.getCantidad()
                                );
                            }else{
                                etiquetasCentroPagos = centroPagosService.crearEtiquetasInscripcion(
                                        ordenVenta.getSucursalId(),
                                        ovd.getIdiomaId(),
                                        ovd.getProgramaId(),
                                        ovd.getModalidadId(),
                                        ovd.getHorarioId(),
                                        ovd.getNivel(),
                                        ordenVenta.getMonto(),
                                        alumno.getCodigo(),
                                        alumno.getNombre(),
                                        alumno.getPrimerApellido(),
                                        alumno.getSegundoApellido()
                                );
                            }
                        }else{
                            Articulo libro = articuloDao.findById(ovd.getArticuloId());

                            etiquetasCentroPagos = centroPagosService.crearEtiquetasVentaLibro(
                                    ordenVenta.getSucursalId(),
                                    libro.getIdiomaId(),
                                    ovd.getPrecio(),
                                    libro.getCodigoArticulo(),
                                    libro.getNombreArticulo(),
                                    ovd.getCantidad()
                            );
                        }
                    }
                }else{
                    List<String> productosCentroPagos = new ArrayList<>();
                    for(OrdenVentaDetalle ovd : ordenVenta.getDetalles()){
                        if(ovd.getDetallePadreId() == null){
                            if(ovd.getAlumnoId() != null && ovd.getGrupoId() != null){
                                Alumno alumno = alumnoDao.findById(ovd.getAlumnoId());
                                ProgramaGrupo grupo = programaGrupoDao.findById(ovd.getGrupoId());

                                String curso = grupo.getProgramaIdioma().getPrograma().getCodigo() + " " + grupo.getProgramaIdioma().getIdioma().getValor();
                                String modalidad = grupo.getPaModalidad().getNombre();
                                String nivel = grupo.getNivel().toString();
                                if(nivel.length() == 1){
                                    nivel = "0" + nivel;
                                }
                                String horario = grupo.getModalidadHorario().getNombre();
                                String numeroGrupo = grupo.getGrupo().toString();
                                if(numeroGrupo.length() == 1){
                                    numeroGrupo = "0" + numeroGrupo;
                                }
                                String fechaInicio = DateUtil.getFecha(grupo.getFechaInicio());
                                String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
                                if(alumno.getSegundoApellido() != null){
                                    nombreAlumno += " " + alumno.getSegundoApellido();
                                }

                                productosCentroPagos.add(ovd.getCantidad() + " - " + curso + " " + modalidad + " " + nivel + " " + horario + " " + numeroGrupo + " " + fechaInicio + " " + nombreAlumno);
                            }else if(ovd.getAlumnoId() != null){
                                Articulo articulo = articuloDao.findById(ovd.getArticuloId());
                                Alumno alumno = alumnoDao.findById(ovd.getAlumnoId());
                                if(articulo.getArticuloSubtipoId() != null && (articulo.getArticuloSubtipoId() == ArticulosSubtipos.EXAMEN || articulo.getArticuloSubtipoId() == ArticulosSubtipos.CERTIFICACION || articulo.getArticuloSubtipoId() == ArticulosSubtipos.CONSTANCIA || articulo.getArticuloSubtipoId() == ArticulosSubtipos.TUTORIA)){
                                    productosCentroPagos.add(ovd.getCantidad() + " - " + articulo.getNombreArticulo());
                                }else{
                                    Programa programa = programaDao.findById(ovd.getProgramaId());
                                    ControlMaestroMultiple idioma = controlMaestroMultipleDao.findCMMById(ovd.getIdiomaId());
                                    PAModalidad paModalidad = paModalidadDao.findById(ovd.getModalidadId());

                                    String curso = programa.getCodigo() + " " + idioma.getValor();
                                    String modalidad = paModalidad.getNombre();
                                    String nivel = ovd.getNivel().toString();
                                    if(nivel.length() == 1){
                                        nivel = "0" + nivel;
                                    }
                                    String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
                                    if(alumno.getSegundoApellido() != null){
                                        nombreAlumno += " " + alumno.getSegundoApellido();
                                    }

                                    productosCentroPagos.add(ovd.getCantidad() + " - " + curso + " " + modalidad + " " + nivel + " " + nombreAlumno);
                                }
                            }else{
                                Articulo articulo = articuloDao.findById(ovd.getArticuloId());
                                productosCentroPagos.add(ovd.getCantidad() + " - " + articulo.getNombreArticulo());
                            }
                        }
                    }
                    etiquetasCentroPagos = centroPagosService.crearEtiquetasMultiplesArticulos(
                            ordenVenta.getSucursalId(),
                            productosCentroPagos,
                            ordenVenta.getMonto()
                    );
                }
            }
        }else if(ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS){
            for(OrdenVenta ovActualizar : ordenesVentaActualizar){
                for(OrdenVentaDetalle detalleActualizar : ovActualizar.getDetalles()) {
                    OrdenVentaDetalle detalle = detallesActualizarMap.get(detalleActualizar.getId());
                    if(detalle != null) {
                        Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
                        ProgramaGrupo grupo = programaGrupoDao.findById(detalle.getGrupoId());

                        etiquetasCentroPagos = centroPagosService.crearEtiquetasInscripcion(
                                ordenVenta.getSucursalId(),
                                grupo.getProgramaIdioma().getIdiomaId(),
                                grupo.getProgramaIdioma().getProgramaId(),
                                grupo.getPaModalidadId(),
                                grupo.getModalidadHorarioId(),
                                grupo.getNivel(),
                                ordenVenta.getMonto(),
                                alumno.getCodigo(),
                                alumno.getNombre(),
                                alumno.getPrimerApellido(),
                                alumno.getSegundoApellido()
                        );
                    }
                }
            }
        }
        // ^^^^^ Fin de la generación de las etiquetas para la liga de pagos en caso de que el método sea Centro de pagos ^^^^^

        // vvvvv Procesar la nueva orden de venta vvvvv
        for(OrdenVentaDetalle detalle : ordenVenta.getDetalles()){
            Integer localidadId;
            if(plantel == null){
                localidadId = detalle.getLocalidadId();
            }else{
                localidadId = plantel.getLocalidadId();
            }

            Articulo articulo = articuloDao.findById(detalle.getArticuloId());

            // vvvvv Procesar una venta de un examen, certificación, constancia o tutoría vvvvv
            if(
                    detalle.getDetallePadreId() == null
                    && articulo != null
                    && articulo.getArticuloSubtipoId() != null
                    && Arrays.asList(ArticulosSubtipos.EXAMEN,ArticulosSubtipos.CERTIFICACION,ArticulosSubtipos.CONSTANCIA,ArticulosSubtipos.TUTORIA).contains(articulo.getArticuloSubtipoId().intValue())
            ){
                Integer tipoMovimientoId = null;
                String razonMovimiento = null;

                if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.EXAMEN || articulo.getArticuloSubtipoId() == ArticulosSubtipos.CERTIFICACION){
                    AlumnoExamenCertificacion alumnoExamenCertificacion = new AlumnoExamenCertificacion();
                    alumnoExamenCertificacion.setAlumnoId(detalle.getAlumnoId());
                    alumnoExamenCertificacion.setArticuloId(detalle.getArticuloId());
                    alumnoExamenCertificacion.setOrdenVentaDetalleId(detalle.getId());
                    if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.EXAMEN){
                        alumnoExamenCertificacion.setTipoId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN);
                    }else if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.CERTIFICACION){
                        alumnoExamenCertificacion.setTipoId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.CERTIFICACION);
                    }
                    alumnoExamenCertificacion.setEstatusId(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.EN_PROCESO);
                    alumnoExamenCertificacion.setProgramaIdiomaCertificacionValeId(detalle.getProgramaIdiomaCertificacionValeId());
                    alumnoExamenCertificacionDao.save(alumnoExamenCertificacion);
                    tipoMovimientoId = ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.VENTA_DE_EXAMEN_O_CERTIFICACION;
                    razonMovimiento = "Venta de examen o certificación";
                }else if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.CONSTANCIA || articulo.getArticuloSubtipoId() == ArticulosSubtipos.TUTORIA){
                    AlumnoConstanciaTutoria alumnoConstanciaTutoria = new AlumnoConstanciaTutoria();
                    alumnoConstanciaTutoria.setAlumnoId(detalle.getAlumnoId());
                    alumnoConstanciaTutoria.setArticuloId(detalle.getArticuloId());
                    alumnoConstanciaTutoria.setOrdenVentaDetalleId(detalle.getId());
                    alumnoConstanciaTutoria.setActivo(true);
                    alumnoConstanciaTutoria.setCreadoPorId(idUsuario);
                    alumnoConstanciaTutoriaDao.save(alumnoConstanciaTutoria);
                    if(articulo.getArticuloSubtipoId() == ArticulosSubtipos.CONSTANCIA){
                        tipoMovimientoId = ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.VENTA_DE_CONSTANCIA;
                        razonMovimiento = "Venta de constancia";
                    }else{
                        tipoMovimientoId = ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.VENTA_DE_TUTORIA;
                        razonMovimiento = "Venta de tutoría";
                    }
                }

                if(articulo.getInventariable()){
                    procesadorInventariosService.procesaMovimiento(
                            detalle.getArticuloId(),
                            localidadId,
                            detalle.getCantidad().negate(),
                            razonMovimiento,
                            ordenVentaGuardada.getCodigo(),
                            detalle.getId(),
                            detalle.getPrecio(),
                            tipoMovimientoId,
                            JwtFilter.getUsuarioId((HttpServletRequest) req)
                    );
                    if(ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS){
                        procesadorInventariosService.procesaMovimiento(
                                detalle.getArticuloId(),
                                Localidades.TRANSITO,
                                detalle.getCantidad(),
                                razonMovimiento,
                                ordenVentaGuardada.getCodigo(),
                                detalle.getId(),
                                detalle.getPrecio(),
                                tipoMovimientoId,
                                JwtFilter.getUsuarioId((HttpServletRequest) req)
                        );
                    }
                }

                if(detalle.getProgramaIdiomaCertificacionValeId() != null){
                    ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = programaIdiomaCertificacionValeDao.findById(detalle.getProgramaIdiomaCertificacionValeId());
                    programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.APLICADO);
                    programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);
                }
            }
            // ^^^^^ Fin del procesado de una venta de un examen, certificación, constancia o tutoría ^^^^^
            else if(detalle.getAlumnoId() != null){// Procesar una inscripción
                JsonResponse responseProcesadoInscripcionAlCobrar = procesarInscripcionAlCobrar(ordenVenta,detalle,ordenVentaGuardada.getCodigo(),localidadId,idUsuario);
                if(responseProcesadoInscripcionAlCobrar != null){
                    rollback();
                    return responseProcesadoInscripcionAlCobrar;
                }
            }else if(detalle.getDetallePadreId() == null){// Procesar una venta de libro
                if(articulo.getInventariable()){
                    procesadorInventariosService.procesaMovimiento(
                            detalle.getArticuloId(),
                            localidadId,
                            detalle.getCantidad().negate(),
                            "Venta de libro",
                            ordenVentaGuardada.getCodigo(),
                            detalle.getId(),
                            detalle.getPrecio(),
                            ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.VENTA_DE_LIBRO,
                            JwtFilter.getUsuarioId((HttpServletRequest) req)
                    );
                    if(ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS){
                        procesadorInventariosService.procesaMovimiento(
                                detalle.getArticuloId(),
                                Localidades.TRANSITO,
                                detalle.getCantidad(),
                                "Venta de libro",
                                ordenVentaGuardada.getCodigo(),
                                detalle.getId(),
                                detalle.getPrecio(),
                                ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.VENTA_DE_LIBRO,
                                JwtFilter.getUsuarioId((HttpServletRequest) req)
                        );
                    }
                }
            }

            if(detalle.getBecaUDGId() != null){
                List<Inscripcion> inscripciones = inscripcionDao.findByOrdenVentaDetalleId(detalle.getId());
                if(inscripciones.size() > 0){
                    Inscripcion inscripcion = inscripciones.get(0);
                    aplicarBecaUDG(detalle.getBecaUDGId(), inscripcion, null, ordenVentaGuardada.getCodigo());
                }else{
                    List<InscripcionSinGrupo> inscripcionesSinGrupo = inscripcionSinGrupoDao.findByOrdenVentaDetalleId(detalle.getId());
                    InscripcionSinGrupo inscripcionSinGrupo = inscripcionesSinGrupo.get(0);
                    aplicarBecaUDG(detalle.getBecaUDGId(), null, inscripcionSinGrupo, ordenVentaGuardada.getCodigo());
                }
            }
        }
        // ^^^^^ Fin de procesado de la nueva orden de venta ^^^^^

        JsonResponse responseProcesadoOrdenesVentaExistentes = procesarOrdenesVentaExistentes(ordenesVentaActualizar, detallesActualizarMap, plantel, ordenVenta.getMedioPagoPVId(), ordenVenta.getMarcarEntregaPendienteInscripciones(), idUsuario, ordenVenta.getReferenciaPago(), ordenVenta.getFechaPago(), sucursalCorteCaja.getId(), ordenVenta.getListaPreciosId());
        if(responseProcesadoOrdenesVentaExistentes != null){
            rollback();
            return responseProcesadoOrdenesVentaExistentes;
        }

        if(etiquetasCentroPagos != null){
            String ligaCentroPagos;
            if(ordenVentaGuardada != null){
                ligaCentroPagos = centroPagosService.generarLiga(ordenVentaGuardada.getCodigo(),ordenVenta.getCorreoElectronico(),ordenVenta.getSucursalId(),ordenVenta.getMonto(),etiquetasCentroPagos);
                if(ordenVenta.getDetalles().size() > 0){
                    ordenVentaGuardada.setLigaCentroPagos(ligaCentroPagos);
                    ordenVentaDao.save(ordenVentaGuardada);
                }
            }else{
                for(OrdenVenta ovActualizar : ordenesVentaActualizar){
                    ligaCentroPagos = centroPagosService.generarLiga(ovActualizar.getCodigo(),ordenVenta.getCorreoElectronico(),ordenVenta.getSucursalId(),ordenVenta.getMonto(),etiquetasCentroPagos);
                    ovActualizar.setLigaCentroPagos(ligaCentroPagos);
                    ordenVentaDao.save(ovActualizar);
                }
            }
        }

        List<String> codigosOV = new ArrayList<>();
        List<Integer> ordenesVentaImprimirIds = new ArrayList<>();
        if(ordenVentaGuardada != null){
            codigosOV.add(ordenVentaGuardada.getCodigo());
            ordenesVentaImprimirIds.add(ordenVentaGuardada.getId());
        }
        for(OrdenVenta ordenVentaActualizada : ordenesVentaActualizar){
            codigosOV.add(ordenVentaActualizada.getCodigo());
            ordenesVentaImprimirIds.add(ordenVentaActualizada.getId());
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("codigosOV",codigosOV);
        responseBody.put("ordenesVentaImprimirIds",ordenesVentaImprimirIds);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/centro-pagos/actualizar-pago/{folio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse centroPagosActualizar(@PathVariable String folio, ServletRequest req) {

        OrdenVenta ordenVenta = ordenVentaDao.findByCodigo(folio);

        for(OrdenVentaDetalle ovd : ordenVenta.getDetalles()){
            List<Inscripcion> inscripciones = inscripcionDao.findByOrdenVentaDetalleId(ovd.getId());
            for(Inscripcion inscripcion : inscripciones){
                if(inscripcion.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA && inscripcion.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INS_Estatus.BAJA){
                    inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
                    inscripcionDao.save(inscripcion);
                }
            }
            List<InscripcionSinGrupo> inscripcionesSinGrupo = inscripcionSinGrupoDao.findByOrdenVentaDetalleId(ovd.getId());
            for(InscripcionSinGrupo inscripcionSinGrupo : inscripcionesSinGrupo){
                if(inscripcionSinGrupo.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INSSG_Estatus.CANCELADA){
                    inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
                    inscripcionSinGrupoDao.save(inscripcionSinGrupo);
                }
            }
        }

        ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
        ordenVenta.setMetodoPagoId(ControlesMaestrosMultiples.CMM_OV_MetodoPago.PUE);
        ordenVentaDao.save(ordenVenta);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/centro-pagos/rechazar-pago/{folio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse centroPagosRechazar(@PathVariable String folio, ServletRequest req) {

        OrdenVenta ordenVenta = ordenVentaDao.findByCodigo(folio);

        if(ordenVenta.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA && ordenVenta.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_OV_Estatus.CANCELADA){
            for(OrdenVentaDetalle ovd : ordenVenta.getDetalles()){
                List<Inscripcion> inscripciones = inscripcionDao.findByOrdenVentaDetalleId(ovd.getId());
                for(Inscripcion inscripcion : inscripciones){
                    inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
                    inscripcionDao.save(inscripcion);
                }
                List<InscripcionSinGrupo> inscripcionesSinGrupo = inscripcionSinGrupoDao.findByOrdenVentaDetalleId(ovd.getId());
                for(InscripcionSinGrupo inscripcionSinGrupo : inscripcionesSinGrupo){
                    inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.CANCELADA);
                    inscripcionSinGrupoDao.save(inscripcionSinGrupo);
                }
            }

            ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.CANCELADA);
            ordenVentaDao.save(ordenVenta);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/centro-pagos/response", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse centroPagosResponse(@RequestBody JSONObject requestBody, ServletRequest req) throws Exception {
        HashMap<String, Object> request  = (HashMap<String, Object>) requestBody.get("CENTEROFPAYMENTS");
        String folio = request.get("foliocpagos").toString();
        String tipo  = request.get("cc_type").toString();
        String fecha = request.get("date").toString() +" "+request.get("time").toString();
        String response = request.get("response").toString();
        String folioOV = request.get("reference").toString();

        if (response.equals("approved")){
            OrdenVenta ordenVenta = ordenVentaDao.findByCodigo(folioOV);
            ordenVenta.setReferenciaPago(folio);
            if (tipo.contains("CREDITO"))
                ordenVenta.setMedioPagoPVId(MediosPagoPV.TARJETA_DE_CREDITO);
            else
                ordenVenta.setMedioPagoPVId(MediosPagoPV.TARJETA_DE_DEBITO);
            for(OrdenVentaDetalle ovd : ordenVenta.getDetalles()){
                List<Inscripcion> inscripciones = inscripcionDao.findByOrdenVentaDetalleId(ovd.getId());
                for(Inscripcion inscripcion : inscripciones){
                    if(inscripcion.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA && inscripcion.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INS_Estatus.BAJA){
                        inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
                        inscripcion = inscripcionDao.save(inscripcion);
                        ProgramaGrupo grupo = programaGrupoDao.findById(inscripcion.getGrupoId());
                        if (grupo.getMultisede()){
                            if (ordenVenta.getSucursalId() != grupo.getSucursalId()){
                                Alumno alumno = alumnoDao.findById(inscripcion.getAlumnoId());

                                //Iniciar alerta
                                String mensaje = "Alumno: "+alumno.getCodigo()+"\n Grupo: "+grupo.getCodigoGrupo();
                                procesadorAlertasService.validarAutorizacion(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,inscripcion.getId(), inscripcion.getCodigo(), "Inscripción Multisede", grupo.getSucursalId(), ordenVenta.getCreadoPorId(), mensaje);
                            }
                        }
                    }
                }
                List<InscripcionSinGrupo> inscripcionesSinGrupo = inscripcionSinGrupoDao.findByOrdenVentaDetalleId(ovd.getId());
                for(InscripcionSinGrupo inscripcionSinGrupo : inscripcionesSinGrupo){
                    if(inscripcionSinGrupo.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INSSG_Estatus.CANCELADA){
                        inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
                        inscripcionSinGrupoDao.save(inscripcionSinGrupo);
                    }
                }
            }

            ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
            ordenVenta.setMetodoPagoId(ControlesMaestrosMultiples.CMM_OV_MetodoPago.PUE);
            ordenVenta.setFechaPago(new Date());
            ordenVentaDao.save(ordenVenta);
        }



        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-entrega-libros/entregar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse entregarLibros(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        List<HashMap<String,Object>> alumnos = (ArrayList)requestBody.get("alumnos");
        Integer localidadId = (Integer)requestBody.get("localidadId");

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        for(HashMap<String,Object> alumno : alumnos){
            Integer alumnoId = (Integer) alumno.get("id");
            Integer inscripcionId = alumno.get("inscripcionId") != null ? ((Integer) alumno.get("inscripcionId")) : null;
            if(inscripcionId != null){
                Inscripcion inscripcion = inscripcionDao.findById(inscripcionId);
                if(inscripcion.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA || !inscripcion.getEntregaLibrosPendiente()){
                    return new JsonResponse(null, "Uno de los registros no cumple las condiciones para entrega de libros", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }else{
                Integer inscripcionSinGrupoId = (Integer) alumno.get("inscripcionSinGrupoId");
                InscripcionSinGrupo inscripcionSinGrupo = inscripcionSinGrupoDao.findById(inscripcionSinGrupoId);
                if(inscripcionSinGrupo.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA || !inscripcionSinGrupo.getEntregaLibrosPendiente()){
                    return new JsonResponse(null, "Uno de los registros no cumple las condiciones para entrega de libros", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }
        }

        for(HashMap<String,Object> alumno : alumnos){
            Integer alumnoId = (Integer) alumno.get("id");
            Integer inscripcionId = alumno.get("inscripcionId") != null ? (Integer) alumno.get("inscripcionId") : null;
            Integer inscripcionSinGrupoId = alumno.get("inscripcionSinGrupoId") != null ? (Integer) alumno.get("inscripcionSinGrupoId") : null;
//            ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);

            Integer programaId;
            Integer idiomaId;
            Integer nivel;
            Integer ovId;
            String ovCodigo;
            Integer ovListadoPreciosId;
            Integer ovDetalleId;
            Integer ovDetalleArticuloId;

            if(inscripcionId != null){
                Inscripcion inscripcion = inscripcionDao.findById(inscripcionId);
                programaId = inscripcion.getGrupo().getProgramaIdioma().getProgramaId();
                idiomaId = inscripcion.getGrupo().getProgramaIdioma().getIdiomaId();
                nivel = inscripcion.getGrupo().getNivel();
                ovId = inscripcion.getOrdenVentaDetalle().getOrdenVenta().getId();
                ovCodigo = inscripcion.getOrdenVentaDetalle().getOrdenVenta().getCodigo();
                ovListadoPreciosId = inscripcion.getOrdenVentaDetalle().getOrdenVenta().getListaPreciosId();
                ovDetalleId = inscripcion.getOrdenVentaDetalle().getId();
                ovDetalleArticuloId = inscripcion.getOrdenVentaDetalle().getArticuloId();

                inscripcion.setEntregaLibrosPendiente(false);
                inscripcionDao.save(inscripcion);
            }else{
                InscripcionSinGrupo inscripcionSinGrupo = inscripcionSinGrupoDao.findById(inscripcionSinGrupoId);
                programaId = inscripcionSinGrupo.getProgramaId();
                idiomaId = inscripcionSinGrupo.getIdiomaId();
                nivel = inscripcionSinGrupo.getNivel();
                ovId = inscripcionSinGrupo.getOrdenVentaDetalle().getOrdenVenta().getId();
                ovCodigo = inscripcionSinGrupo.getOrdenVentaDetalle().getOrdenVenta().getCodigo();
                ovListadoPreciosId = inscripcionSinGrupo.getOrdenVentaDetalle().getOrdenVenta().getListaPreciosId();
                ovDetalleId = inscripcionSinGrupo.getOrdenVentaDetalle().getId();
                ovDetalleArticuloId = inscripcionSinGrupo.getOrdenVentaDetalle().getArticuloId();

                inscripcionSinGrupo.setEntregaLibrosPendiente(false);
                inscripcionSinGrupoDao.save(inscripcionSinGrupo);
            }
            Alumno alumnoDatosCompletos = alumnoDao.findById(alumnoId);
            List<Articulo> articulos = articuloDao.findLibrosByCurso(programaId,idiomaId,nivel,alumnoDatosCompletos.getCarreraJOBSId());
            afectarInventarioInscripcion(alumnoDao.findById(alumnoId),programaId,idiomaId,nivel,ovDetalleArticuloId,ovListadoPreciosId,localidadId,ovCodigo,ovDetalleId,idUsuario);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-inscripciones-pendientes-jobs/agregar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse agregarAlumnosJOBS(@RequestBody List<HashMap<String,Object>> alumnos, ServletRequest req) throws SQLException {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        for(HashMap<String,Object> alumnoHashMap : alumnos){

            Integer alumnoId = (Integer)alumnoHashMap.get("id");
            Integer grupoId = (Integer)alumnoHashMap.get("grupoId");
            Integer localidadId = (Integer)alumnoHashMap.get("localidadId");
            Integer idTmp = (Integer)alumnoHashMap.get("idTmp");

            Alumno alumno = alumnoDao.findById(alumnoId);
            ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
            Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumnoId,grupoId);
            OrdenVentaDetalle ordenVentaDetalleBD = inscripcion.getOrdenVentaDetalle();
            Programa programa = grupo.getProgramaIdioma().getPrograma();
            ControlMaestroMultiple idioma = grupo.getProgramaIdioma().getIdioma();
            PAModalidad modalidad = grupo.getPaModalidad();

            BigDecimal precioUnitarioSinConvertir = ordenVentaDetalleDao.getPrecioUnitarioAcumuladoSinConvertir(ordenVentaDetalleBD.getId());
            String descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();

            OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
            ordenVentaDetalle.setArticuloId(ordenVentaDetalleBD.getArticuloId());
            ordenVentaDetalle.setUnidadMedidaId(ordenVentaDetalleBD.getUnidadMedidaId());
            ordenVentaDetalle.setFactorConversion(ordenVentaDetalleBD.getFactorConversion());
            ordenVentaDetalle.setCantidad(ordenVentaDetalleBD.getCantidad());
            ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
            ordenVentaDetalle.setDescuentoSinConvertir(ordenVentaDetalleBD.getDescuentoSinConvertir());
            ordenVentaDetalle.setIva(ordenVentaDetalleBD.getIva());
            ordenVentaDetalle.setIvaExento(ordenVentaDetalleBD.getIvaExento());
            ordenVentaDetalle.setIeps(ordenVentaDetalleBD.getIeps());
            ordenVentaDetalle.setIepsCuotaFija(ordenVentaDetalleBD.getIepsCuotaFija());

            ordenVentaDetalle.setDescripcion(descripcion);
            ordenVentaDetalle.setAlumnoId(alumnoId);
            ordenVentaDetalle.setNombreAlumno(alumnoId != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);
            ordenVentaDetalle.setGrupoId(grupo.getId());
            ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());

            MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(ordenVentaDetalleBD.getCantidad(),precioUnitarioSinConvertir,ordenVentaDetalleBD.getDescuentoSinConvertir(),ordenVentaDetalleBD.getIva(),ordenVentaDetalleBD.getIeps(),ordenVentaDetalleBD.getIepsCuotaFija());
            ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
            ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
            ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
            ordenVentaDetalle.setTotal(montosCalculados.getTotal());

            ordenVentaDetalle.setLocalidadId(localidadId);

            ordenVentaDetalle.setIdTmp(idTmp);
            ordenVentaDetalle.setId(ordenVentaDetalleBD.getId());

            detallesResponse.add(ordenVentaDetalle);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-reinscripciones/agregar/aprobado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse agregarAlumnoAprobadoReinscripciones(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        HashMap<String,Object> alumnoHashMap = (HashMap<String,Object>) requestBody.get("alumno");

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();


        Integer alumnoId = (Integer)alumnoHashMap.get("id");
        Integer grupoId = (Integer)alumnoHashMap.get("grupoId");
        Integer localidadId = (Integer)alumnoHashMap.get("localidadId");
        Integer idTmp = (Integer)alumnoHashMap.get("idTmp");
        Integer becaId = (Integer)alumnoHashMap.get("becaId");
        Boolean cambioGrupo = (Boolean)alumnoHashMap.get("cambioGrupo");
        Integer nuevoGrupoId = (Integer)alumnoHashMap.get("nuevoGrupoId");
        Integer nuevaModalidadId = (Integer)alumnoHashMap.get("nuevaModalidadId");
        Integer nuevoTipoGrupoId = (Integer)alumnoHashMap.get("nuevoTipoGrupoId");
        Integer nuevoHorarioId = (Integer)alumnoHashMap.get("nuevoHorarioId");
        Integer nuevoNivel = (Integer)alumnoHashMap.get("nuevoNivel");
        String comentario = (String)alumnoHashMap.get("comentario");
        Integer listaPreciosId = (Integer)alumnoHashMap.get("listaPreciosId");

        Alumno alumno = alumnoDao.findById(alumnoId);
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        ProgramaGrupo nuevoGrupo = programaGrupoDao.findById(nuevoGrupoId);
        Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumnoId,grupoId);
        OrdenVentaDetalle ordenVentaDetalleBD = inscripcion.getOrdenVentaDetalle();
        Programa programa = grupo.getProgramaIdioma().getPrograma();
        ControlMaestroMultiple idioma = grupo.getProgramaIdioma().getIdioma();
        PAModalidad modalidad = grupo.getPaModalidad();
        BecaUDG beca = null;
//        BigDecimal montoDescuentoSinConvertir = ordenVentaDetalleBD.getDescuentoSinConvertir();
        if(cambioGrupo == null){
            cambioGrupo = false;
        }
        if (!cambioGrupo){
            beca = becaId == null ? null : becaUDGDao.findById(becaId);
        } else{
            BecaUDGListadoProjection becaPj = null;
            if(nuevoGrupoId != null){
                becaPj = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoSindicato(alumnoId,grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),nuevoGrupo.getPaModalidadId(),nuevoGrupo.getNivel());
                if(becaPj == null){
                    becaPj = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoProulex(alumnoId,grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),nuevoGrupo.getPaModalidadId(),nuevoGrupo.getNivel());
                }
            }else{
                becaPj = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoSindicato(alumnoId,grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),nuevaModalidadId,nuevoNivel);
                if(becaPj == null){
                    becaPj = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoProulex(alumnoId,grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId(),nuevaModalidadId,nuevoNivel);
                }
            }
            if(becaPj != null){
                beca = becaUDGDao.findById(becaPj.getId());
            }
        }

//        BigDecimal precioUnitarioSinConvertir = ordenVentaDetalleDao.getPrecioUnitarioAcumuladoSinConvertir(ordenVentaDetalleBD.getId());

        Articulo articulo = articuloDao.findById(ordenVentaDetalleBD.getArticuloId());
        BigDecimal precioVenta = articuloDao.findPrecioVenta(ordenVentaDetalleBD.getArticuloId(),ordenVentaDetalleBD.getArticuloId(),listaPreciosId);
        BigDecimal precioUnitarioSinConvertir;
        BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
        BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        if(articulo.getIepsCuotaFija() == null){
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
        }else{
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
        }

        BigDecimal montoDescuentoSinConvertir = BigDecimal.ZERO;

        if(beca != null){
            montoDescuentoSinConvertir = ordenVentaDetalleBD.getCantidad().multiply(precioUnitarioSinConvertir).setScale(6).multiply(beca.getDescuento()).setScale(6);
        }else{
            Integer porcentajeDescuentoAutomatico = null;
            if(!cambioGrupo){
                porcentajeDescuentoAutomatico = paDescuentoDao.getPorcentajeDescuentoCurso(grupoId,ordenVentaDetalleBD.getOrdenVenta().getSucursalId(),new Date());
            }else if(nuevoGrupo != null){
                porcentajeDescuentoAutomatico = paDescuentoDao.getPorcentajeDescuentoCurso(nuevoGrupoId,ordenVentaDetalleBD.getOrdenVenta().getSucursalId(),new Date());
            }
            if(porcentajeDescuentoAutomatico != null && porcentajeDescuentoAutomatico.intValue() > 0){
                BigDecimal descuento = new BigDecimal(porcentajeDescuentoAutomatico).divide(new BigDecimal(100));
                montoDescuentoSinConvertir = ordenVentaDetalleBD.getCantidad().multiply(precioUnitarioSinConvertir).setScale(6).multiply(descuento).setScale(6);
            }
        }

        String descripcion = "";
        if(!cambioGrupo){
            descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();
        }else if(nuevoGrupo != null){
            descripcion = nuevoGrupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + nuevoGrupo.getPaModalidad().getNombre() + " Nivel " + nuevoGrupo.getNivel() + " " + nuevoGrupo.getModalidadHorario().getNombre() + " " + nuevoGrupo.getGrupo();
        }else{
            PAModalidad nuevaModalidad = paModalidadDao.findById(nuevaModalidadId);
            descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + nuevaModalidad.getNombre() + " Nivel " + nuevoNivel;
        }

        OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(ordenVentaDetalleBD.getArticuloId());
        ordenVentaDetalle.setUnidadMedidaId(ordenVentaDetalleBD.getUnidadMedidaId());
        ordenVentaDetalle.setFactorConversion(ordenVentaDetalleBD.getFactorConversion());
        ordenVentaDetalle.setCantidad(ordenVentaDetalleBD.getCantidad());
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(montoDescuentoSinConvertir);
        ordenVentaDetalle.setIva(ordenVentaDetalleBD.getIva());
        ordenVentaDetalle.setIvaExento(ordenVentaDetalleBD.getIvaExento());
        ordenVentaDetalle.setIeps(ordenVentaDetalleBD.getIeps());
        ordenVentaDetalle.setIepsCuotaFija(ordenVentaDetalleBD.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(descripcion);
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumnoId != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);
        ordenVentaDetalle.setGrupoId(grupo.getId());
        ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());
        ordenVentaDetalle.setBecaUDGId(becaId);

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(ordenVentaDetalleBD.getCantidad(),precioUnitarioSinConvertir,montoDescuentoSinConvertir,ordenVentaDetalleBD.getIva(),ordenVentaDetalleBD.getIeps(),ordenVentaDetalleBD.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);
        ordenVentaDetalle.setId(ordenVentaDetalleBD.getId());

        if(cambioGrupo){
            ordenVentaDetalle.setCambioGrupo(true);
            if(nuevoGrupo != null){
                ordenVentaDetalle.setNuevoGrupoId(nuevoGrupoId);
            }else{
                ordenVentaDetalle.setModalidadId(nuevaModalidadId);
                ordenVentaDetalle.setTipoGrupoId(nuevoTipoGrupoId);
                ordenVentaDetalle.setHorarioId(nuevoHorarioId);
                ordenVentaDetalle.setNivel(nuevoNivel);
                ordenVentaDetalle.setComentarioReinscripcion(comentario);
            }
        }

        detallesResponse.add(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-reinscripciones/agregar/reprobado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse agregarAlumnoReprobadoReinscripciones(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        // Datos generales
        Integer alumnoId = (Integer) requestBody.get("alumnoId");
        Integer localidadId = (Integer) requestBody.get("localidadId");
        Integer sucursalId = (Integer) requestBody.get("sucursalId");
        Integer articuloId = (Integer) requestBody.get("articuloId");
        Integer listaPreciosId = (Integer) requestBody.get("listaPreciosId");
        String comentarioReinscripcion = (String) requestBody.get("comentarioReinscripcion");
        Integer idTmp = (Integer) requestBody.get("idTmp");

        // Datos para inscripción con grupo
        Integer grupoId = (Integer) requestBody.get("grupoId");

        // Datos para inscripción sin grupo
        Integer programaId;
        Integer idiomaId;
        Integer nivel;
        Integer modalidadId;
        Integer tipoGrupoId;
        Integer horarioId;

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        Alumno alumno = alumnoDao.findById(alumnoId);
        ProgramaGrupo grupo = null;
        ProgramaIdioma curso;
        PAModalidad modalidad;
        if(grupoId != null){
            grupo = programaGrupoDao.findById(grupoId);
            curso = grupo.getProgramaIdioma();
            modalidad = grupo.getPaModalidad();

            programaId = grupo.getProgramaIdioma().getProgramaId();
            idiomaId = grupo.getProgramaIdioma().getIdiomaId();
            nivel = grupo.getNivel();
            modalidadId = grupo.getPaModalidadId();
            tipoGrupoId = grupo.getTipoGrupoId();
            horarioId = grupo.getModalidadHorarioId();
        }else{
            programaId = (Integer) requestBody.get("programaId");
            idiomaId = (Integer) requestBody.get("idiomaId");
            nivel = (Integer) requestBody.get("nivel");
            modalidadId = (Integer) requestBody.get("modalidadId");
            tipoGrupoId = (Integer) requestBody.get("tipoGrupoId");
            horarioId = (Integer) requestBody.get("horarioId");

            curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);
            modalidad = paModalidadDao.findById(modalidadId);
        }
        Programa programa = curso.getPrograma();
        ControlMaestroMultiple idioma = curso.getIdioma();
        Articulo articulo = articuloDao.findById(articuloId);
        Sucursal sucursal = sucursalDao.findById(sucursalId);

        // Validar que el alumno esté reprobado y que el nivel sea el correcto
        AlumnoReinscripcionProjection alumnoReprobado = alumnoDao.findReinscripcionProjectionByReprobado(sucursalId,alumnoId,programaId,idiomaId);
        if(alumnoReprobado == null){
            return new JsonResponse(null,"El alumno " + alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "") + " no se encuentra reprobado.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        } else if(alumnoReprobado.getNivelReinscripcion() < nivel){
            return new JsonResponse(null,"No es posible aplicar la reinscripción. (Nivel máximo: " + alumnoReprobado.getNivelReinscripcion() + ")",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        // Validar conflicto de horario
        if(grupo != null && alumnoDao.getInterferenciaHorario(alumnoId,grupo.getModalidadHorarioId())){
            return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        // Validar si el alumno tiene beca (primero se valida si tiene beca sindicato y si no tiene se valida si tiene beca proulex)
        BecaUDGListadoProjection beca = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoSindicato(alumnoId,programaId,idiomaId,modalidadId,nivel);
        if(beca == null){
            beca = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoProulex(alumnoId,programaId,idiomaId,modalidadId,nivel);
        }

        boolean esJobs = false;
        if((programa.getJobs() != null && programa.getJobs()) || (programa.getJobsSems() != null && programa.getJobsSems())){
            esJobs = true;
        }

        // Se obtiene el porcentaje de descuento
        BigDecimal porcentajeDescuento = BigDecimal.ZERO;
        if(beca != null){ // Si tiene beca se aplica el respectivo descuento
            porcentajeDescuento = beca.getDescuento();
        }else if(!esJobs){ // Si no tiene beca se aplica el 50% de descuento siempre y cuando no sea una reinscripción a JOBS o SEMS
            porcentajeDescuento = new BigDecimal(0.50f);
        }

        // Se calcula el precio del curso
        BigDecimal precioUnitarioSinConvertir = calcularPrecioUnitarioSinConvertirArticulo(articulo,listaPreciosId);

        // Se calcula el monto de descuento
        BigDecimal montoDescuentoSinConvertir = calcularMontoDescuento(BigDecimal.ONE,precioUnitarioSinConvertir,porcentajeDescuento);

        // Se arma la descripción del detalle de OV
        String descripcion = programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + nivel;
        if(grupo != null){
            descripcion = grupo.getSucursal().getNombre() + " " + descripcion + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();
        }else{
            descripcion = sucursal.getNombre() + " " + descripcion;
        }

        // Se arma el bosquejo del detalle de OV
        OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articuloId);
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(BigDecimal.ONE);
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(montoDescuentoSinConvertir);
        ordenVentaDetalle.setIva(((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva());
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps());
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(descripcion);
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumnoId != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);
        ordenVentaDetalle.setGrupoId(grupoId);
        ordenVentaDetalle.setIdiomaId(idiomaId);
        ordenVentaDetalle.setBecaUDGId(beca != null ? beca.getId() : null);

        if(grupo == null){
            ordenVentaDetalle.setSucursalId(sucursalId);
            ordenVentaDetalle.setProgramaId(programaId);
            ordenVentaDetalle.setIdiomaId(idiomaId);
            ordenVentaDetalle.setModalidadId(modalidadId);
            ordenVentaDetalle.setTipoGrupoId(tipoGrupoId);
            ordenVentaDetalle.setHorarioId(horarioId);
            ordenVentaDetalle.setNivel(nivel);
            ordenVentaDetalle.setComentarioReinscripcion(comentarioReinscripcion);
        }

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(BigDecimal.ONE,precioUnitarioSinConvertir,montoDescuentoSinConvertir,ordenVentaDetalle.getIva(),ordenVentaDetalle.getIeps(),ordenVentaDetalle.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        detallesResponse.add(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-inscripciones-pendientes-jobs-sems/agregar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse agregarAlumnosJOBSSEMS(@RequestBody List<HashMap<String,Object>> alumnos, ServletRequest req) throws SQLException {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        for(HashMap<String,Object> alumnoHashMap : alumnos){

            Integer alumnoId = (Integer)alumnoHashMap.get("id");
            Integer grupoId = (Integer)alumnoHashMap.get("grupoId");
            Integer localidadId = (Integer)alumnoHashMap.get("localidadId");
            Integer idTmp = (Integer)alumnoHashMap.get("idTmp");

            Alumno alumno = alumnoDao.findById(alumnoId);
            ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
            Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumnoId,grupoId);
            OrdenVentaDetalle ordenVentaDetalleBD = inscripcion.getOrdenVentaDetalle();

            BigDecimal precioUnitarioSinConvertir = ordenVentaDetalleDao.getPrecioUnitarioAcumuladoSinConvertir(ordenVentaDetalleBD.getId());
            String descripcion = "";

            Programa programa = grupo.getProgramaIdioma().getPrograma();
            ControlMaestroMultiple idioma = grupo.getProgramaIdioma().getIdioma();
            PAModalidad modalidad = grupo.getPaModalidad();

            descripcion = programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();

            switch(grupo.getNivel()){
                case 1:
                    descripcion += " Libro Interac 1, Parcialidad (1 de 2)";
                    break;
                case 2:
                    descripcion += " Libro Interac 1, Parcialidad (2 de 2)";
                    break;
                case 3:
                    descripcion += " Libro Interac 2, Parcialidad (1 de 2)";
                    break;
                case 4:
                    descripcion += " Libro Interac 2, Parcialidad (2 de 2)";
                    break;
                case 5:
                    descripcion += " Libro Interac 3, Parcialidad (1 de 2)";
                    break;
                case 6:
                    descripcion += " Libro Interac 3, Parcialidad (2 de 2)";
                    break;
            }

            OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
            ordenVentaDetalle.setArticuloId(ordenVentaDetalleBD.getArticuloId());
            ordenVentaDetalle.setUnidadMedidaId(ordenVentaDetalleBD.getUnidadMedidaId());
            ordenVentaDetalle.setFactorConversion(ordenVentaDetalleBD.getFactorConversion());
            ordenVentaDetalle.setCantidad(ordenVentaDetalleBD.getCantidad());
            ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
            ordenVentaDetalle.setDescuentoSinConvertir(ordenVentaDetalleBD.getDescuentoSinConvertir());
            ordenVentaDetalle.setIva(ordenVentaDetalleBD.getIva());
            ordenVentaDetalle.setIvaExento(ordenVentaDetalleBD.getIvaExento());
            ordenVentaDetalle.setIeps(ordenVentaDetalleBD.getIeps());
            ordenVentaDetalle.setIepsCuotaFija(ordenVentaDetalleBD.getIepsCuotaFija());

            ordenVentaDetalle.setDescripcion(descripcion);
            ordenVentaDetalle.setAlumnoId(alumnoId);
            ordenVentaDetalle.setNombreAlumno(alumno.getCodigoAlumnoUDG() + " " + alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : ""));
            ordenVentaDetalle.setGrupoId(grupo.getId());
            ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());

            MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(ordenVentaDetalleBD.getCantidad(),precioUnitarioSinConvertir,ordenVentaDetalleBD.getDescuentoSinConvertir(),ordenVentaDetalleBD.getIva(),ordenVentaDetalleBD.getIeps(),ordenVentaDetalleBD.getIepsCuotaFija());
            ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
            ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
            ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
            ordenVentaDetalle.setTotal(montosCalculados.getTotal());

            ordenVentaDetalle.setLocalidadId(localidadId);

            ordenVentaDetalle.setIdTmp(idTmp);
            ordenVentaDetalle.setId(ordenVentaDetalleBD.getId());

            detallesResponse.add(ordenVentaDetalle);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/alumnos-inscripciones-pendientes-pcp/agregar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse agregarAlumnosPCP(@RequestBody List<HashMap<String,Object>> alumnos, ServletRequest req) throws SQLException {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        for(HashMap<String,Object> alumnoHashMap : alumnos){

            Integer alumnoId = (Integer)alumnoHashMap.get("id");
            Integer grupoId = (Integer)alumnoHashMap.get("grupoId");
            Integer localidadId = (Integer)alumnoHashMap.get("localidadId");
            Integer idTmp = (Integer)alumnoHashMap.get("idTmp");

            Alumno alumno = alumnoDao.findById(alumnoId);
            ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
            Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumnoId,grupoId);
            OrdenVentaDetalle ordenVentaDetalleBD = inscripcion.getOrdenVentaDetalle();

            BigDecimal precioUnitarioSinConvertir = ordenVentaDetalleDao.getPrecioUnitarioAcumuladoSinConvertir(ordenVentaDetalleBD.getId());
            String descripcion = "";

            Programa programa = grupo.getProgramaIdioma().getPrograma();
            ControlMaestroMultiple idioma = grupo.getProgramaIdioma().getIdioma();
            PAModalidad modalidad = grupo.getPaModalidad();

            descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();

            OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
            ordenVentaDetalle.setArticuloId(ordenVentaDetalleBD.getArticuloId());
            ordenVentaDetalle.setUnidadMedidaId(ordenVentaDetalleBD.getUnidadMedidaId());
            ordenVentaDetalle.setFactorConversion(ordenVentaDetalleBD.getFactorConversion());
            ordenVentaDetalle.setCantidad(ordenVentaDetalleBD.getCantidad());
            ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
            ordenVentaDetalle.setDescuentoSinConvertir(ordenVentaDetalleBD.getDescuentoSinConvertir());
            ordenVentaDetalle.setIva(ordenVentaDetalleBD.getIva());
            ordenVentaDetalle.setIvaExento(ordenVentaDetalleBD.getIvaExento());
            ordenVentaDetalle.setIeps(ordenVentaDetalleBD.getIeps());
            ordenVentaDetalle.setIepsCuotaFija(ordenVentaDetalleBD.getIepsCuotaFija());

            ordenVentaDetalle.setDescripcion(descripcion);
            ordenVentaDetalle.setAlumnoId(alumnoId);
            ordenVentaDetalle.setNombreAlumno(alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : ""));
            ordenVentaDetalle.setGrupoId(grupo.getId());
            ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());

            MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(ordenVentaDetalleBD.getCantidad(),precioUnitarioSinConvertir,ordenVentaDetalleBD.getDescuentoSinConvertir(),ordenVentaDetalleBD.getIva(),ordenVentaDetalleBD.getIeps(),ordenVentaDetalleBD.getIepsCuotaFija());
            ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
            ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
            ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
            ordenVentaDetalle.setTotal(montosCalculados.getTotal());

            ordenVentaDetalle.setLocalidadId(localidadId);

            ordenVentaDetalle.setIdTmp(idTmp);
            ordenVentaDetalle.setId(ordenVentaDetalleBD.getId());

            detallesResponse.add(ordenVentaDetalle);
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/becas-sindicato/aplicar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse crearOVDBecaSindicato(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        Integer alumnoId = (Integer)requestBody.get("alumnoId");
        Integer becaId = (Integer)requestBody.get("becaId");
        Integer grupoId = (Integer)requestBody.get("grupoId");
        Integer tipoGrupoId = (Integer)requestBody.get("tipoGrupoId");
        Integer listaPreciosId = (Integer)requestBody.get("listaPreciosId");
        Integer localidadId = (Integer)requestBody.get("localidadId");
        Integer idTmp = (Integer)requestBody.get("idTmp");
        Integer sucursalId = (Integer)requestBody.get("sucursalId");

        Alumno alumno = alumnoDao.findById(alumnoId);
        BecaUDG becaUDG = becaUDGDao.findById(becaId);
        ProgramaGrupo grupo = null;
        if(grupoId != null){
            grupo = programaGrupoDao.findById(grupoId);
        }

        // Si el alumno no está activo o la beca tiene estatus diferente a pendiente, informar del error
        if(!alumno.getActivo()){
            return new JsonResponse(null,"Alumno inactivo",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }
        if(becaUDG.getEstatusId().compareTo(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR) != 0){
            return new JsonResponse(null,"La beca ya fue aplicada o cancelada",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }
        if(grupo != null && alumnoDao.getInterferenciaHorario(alumnoId,grupo.getModalidadHorarioId())){
            return new JsonResponse(null,"El alumno " + alumno.getCodigo() + " tiene conflicto de horario.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }
        // Validar que el grupo aún tenga cupo disponible
        if(grupo != null && programaGrupoDao.getCupoDisponible(grupo.getId()).intValue() <= 0){
            return new JsonResponse(null,"El grupo ya no cuenta con cupo disponible.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }
        String alumnoCodigoUDG = "";
        String alumnoCodigoUDGAlterno = "";
        if(alumno.getCodigoUDG() != null){
            alumnoCodigoUDG = alumno.getCodigoUDG();
        }
        if(alumno.getCodigoUDGAlterno() != null){
            alumnoCodigoUDGAlterno = alumno.getCodigoUDGAlterno();
        }
        String becaCodigoUDG = "";
        if(becaUDG.getCodigoEmpleado() != null){
            becaCodigoUDG = becaUDG.getCodigoEmpleado();
        }
        if( // Si es beca staudg o sutudg y no coinciden los campos de código, nombre y apellidos entre el alumno y la beca informar del error
            (
                becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.SUTUDG
                || becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.STAUDG
            ) && (
                (
                    alumnoCodigoUDG.trim().compareToIgnoreCase(becaCodigoUDG.trim()) != 0
                    && alumnoCodigoUDGAlterno.trim().compareToIgnoreCase(becaCodigoUDG.trim()) != 0
                )
                || alumno.getNombre().trim().compareToIgnoreCase(becaUDG.getNombre().trim()) != 0
                || alumno.getPrimerApellido().trim().compareToIgnoreCase(becaUDG.getPrimerApellido().trim()) != 0
                || (alumno.getSegundoApellido() == null && becaUDG.getSegundoApellido() != null)
                || (alumno.getSegundoApellido() != null && becaUDG.getSegundoApellido() == null)
                || (alumno.getSegundoApellido() != null && becaUDG.getSegundoApellido() != null && alumno.getSegundoApellido().trim().compareToIgnoreCase(becaUDG.getSegundoApellido().trim()) != 0)
            )
        ){
            return new JsonResponse(null,"Los datos de la beca no coinciden con los del alumno",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }else if( // Si es beca proulex y no coincide el código del alumno en la beca
            becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.PROULEX
            && alumno.getCodigo().compareToIgnoreCase(becaUDG.getCodigoAlumno()) != 0
        ){
            return new JsonResponse(null,"Los datos de la beca no coinciden con los del alumno",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        Integer programaId = becaUDG.getProgramaIdioma().getProgramaId();
        Programa programa = becaUDG.getProgramaIdioma().getPrograma();
        Integer idiomaId = becaUDG.getProgramaIdioma().getIdiomaId();
        ControlMaestroMultiple idioma = becaUDG.getProgramaIdioma().getIdioma();
        Integer modalidadId = becaUDG.getPaModalidadId();
        PAModalidad modalidad = becaUDG.getPaModalidad();

        if( // Si no coinciden los campos de programa, idioma, modalidad y nivel entre el beca y el grupo seleccionado informar del error
                grupo != null && (
                        programaId.compareTo(grupo.getProgramaIdioma().getProgramaId()) != 0
                        || idiomaId.compareTo(grupo.getProgramaIdioma().getIdiomaId()) != 0
                        || modalidadId.compareTo(grupo.getPaModalidadId()) != 0
                        || becaUDG.getNivel().compareTo(grupo.getNivel()) != 0
                        )
                ){
            return new JsonResponse(null,"Los datos de la beca no coinciden con los del grupo seleccionado",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        List<AlumnoReinscripcionProjection> reinscripcionesAprobados = alumnoDao.findReinscripcionProjectionByAprobadosSinProyeccion(alumnoId,programaId,idiomaId,null);
        if(reinscripcionesAprobados.size() > 0){
            AlumnoReinscripcionProjection reinscripcionAprobado = reinscripcionesAprobados.get(0);
            Integer nivelPermisibleInscripcion;
            if(grupo != null){
                nivelPermisibleInscripcion = grupo.getNivel();
            }else{
                nivelPermisibleInscripcion = becaUDG.getNivel();
            }
            if((reinscripcionAprobado.getNivelReinscripcion().intValue()+1) < nivelPermisibleInscripcion.intValue()){
                String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                        .append(alumno.getCodigo())
                        .append(" al nivel ")
                        .append(becaUDG.getNivel())
                        .append(". (Nivel máximo: ")
                        .append(reinscripcionAprobado.getNivelReinscripcion()+1)
                        .append(")")
                        .toString();
                return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
        }
        List<AlumnoReinscripcionProjection> alumnosReprobados = alumnoDao.findReinscripcionProjectionByReprobadosAndIdAndProgramaIdAndIdiomaId(alumnoId,programaId,idiomaId);
        if(alumnosReprobados.size() > 0){
            AlumnoReinscripcionProjection alumnoReprobado = alumnosReprobados.get(0);
            if(becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.STAUDG || becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.SUTUDG){
                return new JsonResponse(null,"No es posible aplicar la beca debido a que el alumno reprobó.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }else if (becaUDG.getTipoId() == ControlesMaestrosMultiples.CMM_BECU_Tipo.PROULEX && alumnoReprobado.getNivelReinscripcion() < becaUDG.getNivel()){
                return new JsonResponse(null,"No es posible aplicar la beca debido a que el alumno reprobó. (Nivel máximo: " + alumnoReprobado.getNivelReinscripcion() + ")",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
        }

        Integer articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadId(programaId,idiomaId,modalidadId);
        Articulo articulo = articuloDao.findById(articuloId);

        BigDecimal cantidad = BigDecimal.ONE;
        BigDecimal precioUnitarioSinConvertir;
        BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
        BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();

        BigDecimal precioVenta = articuloDao.findPrecioVenta(articuloId,articuloId,listaPreciosId);
        if(articulo.getIepsCuotaFija() == null){
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
        }else{
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
        }

        BigDecimal descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply(becaUDG.getDescuento()).setScale(6);

        String descripcion;
        if(grupo != null){
            descripcion = grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo();
        }else{
            descripcion = "Sin grupo";
        }

        OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articuloId);
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(cantidad);
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(descuentoSinConvertir);
        ordenVentaDetalle.setIva(tasaIva);
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(tasaIeps);
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(descripcion);
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : ""));

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(cantidad,precioUnitarioSinConvertir,descuentoSinConvertir,tasaIva,tasaIeps,articulo.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        if(grupo != null){
            ordenVentaDetalle.setGrupoId(grupo.getId());
            ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());
        }else if(alumno != null){
            ordenVentaDetalle.setSucursalId(sucursalId);
            ordenVentaDetalle.setProgramaId(programaId);
            ordenVentaDetalle.setIdiomaId(idiomaId);
            ordenVentaDetalle.setModalidadId(modalidadId);
            ordenVentaDetalle.setTipoGrupoId(tipoGrupoId);
            ordenVentaDetalle.setHorarioId(null);
            ordenVentaDetalle.setNivel(becaUDG.getNivel());
            ordenVentaDetalle.setNumeroGrupo(null);
            ordenVentaDetalle.setComentarioReinscripcion(null);
        }

        ordenVentaDetalle.setBecaUDGId(becaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalle",ordenVentaDetalle);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/becas-sindicato/alumno/{alumnoId}/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getBecaAlumno(@PathVariable Integer alumnoId, @PathVariable Integer grupoId, ServletRequest req) throws Exception {

        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);

        BecaUDGListadoProjection beca = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoSindicato(
                alumnoId,
                grupo.getProgramaIdioma().getProgramaId(),
                grupo.getProgramaIdioma().getIdiomaId(),
                grupo.getPaModalidadId(),
                grupo.getNivel()
        );

        if(beca == null){
            beca = becaUDGDao.findProjectedListadoByAlumnoIdAndProgramaIdiomaIdAndModalidadIdAndNivelAndTipoProulex(
                    alumnoId,
                    grupo.getProgramaIdioma().getProgramaId(),
                    grupo.getProgramaIdioma().getIdiomaId(),
                    grupo.getPaModalidadId(),
                    grupo.getNivel()
            );
        }

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("beca",beca);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/imprimir/nota-venta", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public void descargarPdf(@RequestBody HashMap<String, Object> filtros, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {

        String reportPath = "/modulos/ventas/NotaVenta.jasper";
        String ordenesVentaIdsStr = (String)filtros.get("ordenesVentaIdsStr");
        Integer sucursalId = (Integer)filtros.get("sucursalId");
        Boolean esJobs = false;
        Boolean esJobsSems = false;

        ControlMaestroMultipleComboProjection cmmSucursalJobsId = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSId.CMM_SUC_SucursalJOBSId);
        if(Integer.parseInt(cmmSucursalJobsId.getValor()) == sucursalId.intValue()){
            esJobs = true;
        }else{
            ControlMaestroMultipleComboProjection cmmSucursalJobsSemsId = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSSEMSId.CMM_SUC_SucursalJOBSSEMSId);
            if(Integer.parseInt(cmmSucursalJobsSemsId.getValor()) == sucursalId.intValue()){
                esJobsSems = true;
            }
        }

        String[] ordenesVentaIdsStrArray = ordenesVentaIdsStr.split(",");
        List<Integer> ordenesVentaIds = new ArrayList<>();
        for(int i=0 ; i<ordenesVentaIdsStrArray.length ; i++){
            ordenesVentaIds.add(Integer.parseInt(ordenesVentaIdsStrArray[i]));
        }

        String nombreArchivo = ordenesVentaIds.size() == 1 ? ordenVentaDao.findCodigoById(ordenesVentaIds.get(0)) : "NotaVenta";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.reportes.location"));
        parameters.put("frontUrl", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("ids", ordenesVentaIds);
        parameters.put("esJobs", esJobs);
        parameters.put("esJobsSems", esJobsSems);
        parameters.put("webId", ordenesVentaIds.size() == 1 ? hashId.encode(ordenesVentaIds.get(0)) : null);

        InputStream reporte = reporteService.generarJasperReport(reportPath,parameters, ReporteServiceImpl.output.PDF, true);

        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo + ".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        org.apache.commons.io.IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();

        reporte.close();
    }

    @RequestMapping(value = "/descuentos/validar/usuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse validarDescuentoUsuario(@RequestBody HashMap<String,Object> requestBody) throws UsuarioException {

        String correo = (String)requestBody.get("correo");
        String contrasenia = (String)requestBody.get("contrasenia");
        BigDecimal montoDescuento = new BigDecimal((String)requestBody.get("montoDescuento"));

        //Validamos que el dato usuario y contraseña no vengan NULL
        if (correo == null || contrasenia == null) {
            return new JsonResponse(false, "Usuario o Contraseña no pueden ir vacios.", JsonResponse.STATUS_ERROR_NULL);
        }

        //Buscamos al AbstractUsuario por Nombre de AbstractUsuario y Contraseña
        UsuarioLoginVerificarProjection usuarioLogin = usuarioDao.findProjectedLoginByCorreoElectronico(correo);

        //Si no encontramos alguno, mandamos error
        if (usuarioLogin == null) {
            return new JsonResponse(false, "Usuario no encontrado.", JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
        }

        //Verificamos la contraseña
        if (!passwordEncoder.matches(contrasenia, usuarioLogin.getContrasenia())) {
            return new JsonResponse(false, "Contraseña incorrecta.", JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
        }

        //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
        if (usuarioLogin.getEstatusId().intValue() != com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
            return new JsonResponse(false, "Usuario no activo.", JsonResponse.STATUS_OK_USUARIO_INACTIVO);
        }

        boolean usuarioRegistrado = paDescuentoDao.validarDescuentoUsuarioEstaConfigurado(usuarioLogin.getId());

        if(!usuarioRegistrado) {
            return new JsonResponse(false, "Usuario no autorizado para aplicar descuentos.", JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
        }

        boolean montoValido = paDescuentoDao.validarDescuentoUsuario(usuarioLogin.getId(),montoDescuento.intValue());

        if(!montoValido) {
            return new JsonResponse(false, "Monto supera al configurado.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        return new JsonResponse(montoValido, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/in-company/clientes/{sucursalId}")
    public JsonResponse getClientesInCompany(@PathVariable Integer sucursalId, ServletRequest req) {

        List<ClienteCardProjection> clientes = clienteDao.findProjectedCardAllBySucursalInCompany(sucursalId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("clientes",clientes);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/in-company/grupos/{sucursalId}/{clienteId}")
    public JsonResponse getGruposInCompany(@PathVariable Integer sucursalId, @PathVariable Integer clienteId, ServletRequest req) {

        List<Integer> programasInCompanyIds = programaGrupoIncompanyDao.getIdsBySucursalIdAndClienteId(sucursalId,clienteId);
        List<ProgramaGrupoCardProjection> grupos = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndProgramaInCompanyIdInAndFechaFin(sucursalId,programasInCompanyIds);

        HashMap<String,HashMap<String,Object>> gruposCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposCabeceras = new ArrayList<>();
        crearCardsGrupos(grupos,gruposCabecerasMap,gruposCabeceras,false,false);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("gruposCabeceras",gruposCabeceras);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/in-company/crear/detalle-ov", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse crearOrdenVentaDetalleInCompany(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws Exception {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        OrdenVentaDetalle ordenVentaDetalle;
        Integer localidadId = Integer.parseInt(requestBody.get("localidadId"));
        Integer alumnoId = Integer.parseInt(requestBody.get("alumnoId"));
        Integer grupoId = Integer.parseInt(requestBody.get("grupoId"));
        Integer idTmp = Integer.parseInt(requestBody.get("idTmp"));

        Alumno alumno = alumnoDao.findById(alumnoId);
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        ProgramaIdioma curso = grupo.getProgramaIdioma();
        Programa programa = curso.getPrograma();
        ControlMaestroMultiple idioma = curso.getIdioma();
        PAModalidad modalidad = grupo.getPaModalidad();
        Integer articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadId(programa.getId(),idioma.getId(),modalidad.getId());
        Articulo articulo = articuloDao.findById(articuloId);

        BigDecimal precioVentaCurso = grupo.getPrecioVentaCurso() != null ? grupo.getPrecioVentaCurso() : new BigDecimal(0);
        BigDecimal precioVentaLibro = grupo.getPrecioVentaLibro() != null ? grupo.getPrecioVentaLibro() : new BigDecimal(0);
        BigDecimal precioVentaCertificacion = grupo.getPrecioVentaCertificacion() != null ? grupo.getPrecioVentaCertificacion() : new BigDecimal(0);
        BigDecimal precioUnitarioSinConvertir = precioVentaCurso.add(precioVentaLibro).add(precioVentaCertificacion);

        if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idioma.getId(),null,false)){
            return new JsonResponse(null,"No es posible inscribir al alumno " + alumno.getCodigo() + " al idioma " + idioma.getValor() + " debido a que ya está cursando el mismo.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        EstatusPermiteInscripcionPorExamenUbicacion estatusPermiteInscripcionPorExamenUbicacion = permiteInscripcionPorExamenUbicacion(alumnoId,programa.getId(),idioma.getId(),grupo.getNivel());

        // Si el alumno no tiene exámenes de ubicación en proceso, se valida el nivel de inscripcción por medio de inscripciones anteriores
        if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.SI_PERMITE_NO_EXAMEN){
            // TODO: descomentar cuando se necesite validar nivel
//                Integer nivelPermiteInscripcion = alumnoDao.getNivelAlumnoPermiteInscripcion(alumnoId,idioma.getId(),programa.getId());
//                // Si el alumno intenta inscribirse a un nivel superior permitido se cancela la inscripción y se informa al usuario
//                if(nivelPermiteInscripcion < grupo.getNivel()){
//                    String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
//                            .append(alumno.getCodigo())
//                            .append(" al nivel ")
//                            .append(grupo.getNivel())
//                            .append(". (Nivel máximo: ")
//                            .append(nivelPermiteInscripcion)
//                            .append(")")
//                            .toString();
//                    return new JsonResponse(null, mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
//                }
        }
        // Si tiene exámenes de ubicación en proceso, se cancela la inscripción y se informa al usuario
        else if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_EN_PROCESO){
            String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                    .append(alumno.getCodigo())
                    .append(" al nivel ")
                    .append(grupo.getNivel())
                    .append(". (Examen de ubicación en proceso)")
                    .toString();
            return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }
        // Si el alumno intenta inscribirse a un nivel superior al indicado en un examen de ubicación se cancela la inscripción y se informa al usuario
        else if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_NIVEL_SUPERIOR){
            String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                    .append(alumno.getCodigo())
                    .append(" al nivel ")
                    .append(grupo.getNivel())
                    .append(". (Nivel máximo: ")
                    .append(getNivelPermisibleExamenUbicacion(alumnoId,programa.getId(),idioma.getId()))
                    .append(")")
                    .toString();
            return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        BigDecimal tasaIVA = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        BigDecimal tasaIEPS = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();

        ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articuloId);
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(new BigDecimal(1));
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(new BigDecimal(0));
        ordenVentaDetalle.setIva(tasaIVA);
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(tasaIEPS);
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(grupo.getSucursal().getNombre() + " " + programa.getCodigo() + " " + idioma.getValor() + " " + modalidad.getNombre() + " Nivel " + grupo.getNivel() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo());
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : ""));

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(new BigDecimal(1),precioUnitarioSinConvertir,new BigDecimal(0),tasaIVA,tasaIEPS,articulo.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        ordenVentaDetalle.setGrupoId(grupo.getId());
        ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());

        ordenVentaDetalle.setNoAplicaDescuentos(true);

        detallesResponse.add(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);



        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/vales/certificacion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValesCertificacion(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        String filtro = requestBody.get("filtro");
        Integer offset = Integer.parseInt(requestBody.get("offset"));
        Integer top = Integer.parseInt(requestBody.get("top"));

        List<Integer> estatusIds = Arrays.asList(ControlesMaestrosMultiples.CMM_VC_Estatus.GENERADO);

        List<ProgramaIdiomaCertificacionValeListadoPVProjection> valesCertificaciones = programaIdiomaCertificacionValeDao.findListadoPVProjectedAllByEstatusIdInAndFiltro(estatusIds,filtro,offset,top);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("valesCertificaciones",valesCertificaciones);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/vales/certificacion/aplicar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse aplicarValeCertificacion(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        Integer programaIdiomaCertificacionValeId = Integer.parseInt(requestBody.get("programaIdiomaCertificacionValeId"));
        Integer listaPreciosId = Integer.parseInt(requestBody.get("listaPreciosId"));
        Integer localidadId = Integer.parseInt(requestBody.get("localidadId"));
        Integer idTmp = Integer.parseInt(requestBody.get("idTmp"));
        Integer sucursalId = Integer.parseInt(requestBody.get("sucursalId"));
        
        ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = programaIdiomaCertificacionValeDao.findById(programaIdiomaCertificacionValeId);

        Articulo articulo = programaIdiomaCertificacionVale.getProgramaIdiomaCertificacionDescuento().getProgramaIdiomaCertificacion().getCertificacion();
        Alumno alumno = programaIdiomaCertificacionVale.getAlumnoGrupo().getAlumno();

        BigDecimal precioVenta = articuloDao.findPrecioVenta(articulo.getId(),articulo.getId(),listaPreciosId);
        BigDecimal precioUnitarioSinConvertir;
        BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
        BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        if(articulo.getIepsCuotaFija() == null){
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
        }else{
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
        }
        
        BigDecimal cantidad = BigDecimal.ONE;
        String descripcion = articulo.getNombreArticulo();
        BigDecimal porcentajeDescuento = programaIdiomaCertificacionVale.getPorcentajeDescuento();
        BigDecimal descuentoSinConvertir = precioUnitarioSinConvertir.multiply(cantidad).setScale(6).multiply((porcentajeDescuento).divide(new BigDecimal(100))).setScale(6);
        
        OrdenVentaDetalle ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articulo.getId());
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(cantidad);
        ordenVentaDetalle.setPrecioSinConvertir(precioUnitarioSinConvertir);
        ordenVentaDetalle.setDescuentoSinConvertir(descuentoSinConvertir);
        ordenVentaDetalle.setIva(tasaIva);
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(tasaIeps);
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(descripcion);
        ordenVentaDetalle.setAlumnoId(alumno.getId());
        ordenVentaDetalle.setNombreAlumno(alumno.getId() != null ? (alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : "")) : null);

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(cantidad,precioUnitarioSinConvertir,descuentoSinConvertir,tasaIva,tasaIeps,articulo.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        ordenVentaDetalle.setSucursalId(sucursalId);
        ordenVentaDetalle.setProgramaIdiomaCertificacionValeId(programaIdiomaCertificacionValeId);
        
        List<OrdenVentaDetalle> detallesResponse = Arrays.asList(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/vales/certificacion/alumno/{alumnoId}/{certificacionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionAlumno(@PathVariable Integer alumnoId, @PathVariable Integer certificacionId, ServletRequest req) throws Exception {

        List<Integer> estatusIds = Arrays.asList(ControlesMaestrosMultiples.CMM_VC_Estatus.GENERADO);

        ProgramaIdiomaCertificacionValeListadoPVProjection programaIdiomaCertificacionVale = programaIdiomaCertificacionValeDao.findListadoPVProjectedByAlumnoIdAndCertificacionIdAndEstatusIdIn(alumnoId,certificacionId,estatusIds);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("programaIdiomaCertificacionVale",programaIdiomaCertificacionVale);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/academy/modalidades/{programaId}/{idiomaId}")
    public JsonResponse getModalidadesAcademy(@PathVariable Integer programaId, @PathVariable Integer idiomaId, ServletRequest req){
        List<PAModalidadCardProjection> paModalidades = paModalidadDao.findProjectedCardAllByActivoTrueAndIdiomaIdAndProgramaId(idiomaId,programaId);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("paModalidades",paModalidades);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/academy/tipos-workshop/{modalidadId}")
    public JsonResponse getTiposAcademy(@PathVariable Integer modalidadId, ServletRequest req){

        List<ControlMaestroMultipleCardProjection> tipos = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueAndSistemaIsFalseOrderByOrden("CMM_WKS_TipoWorkshop");

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("tipos",tipos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/academy/workshops/{sucursalId}/{modalidadId}/{tipoWorkshopId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getWorkshops(@PathVariable Integer sucursalId, @PathVariable Integer modalidadId, @PathVariable Integer tipoWorkshopId, ServletRequest req) throws SQLException {

        List<ProgramaGrupoCardProjection> grupos = programaGrupoDao.findProjectedCardAllByActivoTrueAndSucursalIdAndPaModalidadIdAndTipoIdAndFechaFin(sucursalId, modalidadId, tipoWorkshopId);
        ProgramaComboProjection programa = programaDao.findFirstByAcademyIsTrueAndActivoIsTrue();

        HashMap<String,HashMap<String,Object>> gruposCabecerasMap = new HashMap<>();
        List<HashMap<String,Object>> gruposCabeceras = new ArrayList<>();
        crearCardsGrupos(grupos,gruposCabecerasMap,gruposCabeceras,false, false);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("gruposCabeceras",gruposCabeceras);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/academy/crear/detalle-ov", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse crearOrdenVentaDetalleAcademy(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws Exception {

        List<OrdenVentaDetalle> detallesResponse = new ArrayList<>();

        OrdenVentaDetalle ordenVentaDetalle;
        Integer localidadId = Integer.parseInt(requestBody.get("localidadId"));
        Integer alumnoId = Integer.parseInt(requestBody.get("alumnoId"));
        Integer grupoId = Integer.parseInt(requestBody.get("grupoId"));
        Integer idTmp = Integer.parseInt(requestBody.get("idTmp"));

        Alumno alumno = alumnoDao.findById(alumnoId);
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        ProgramaIdioma curso = grupo.getProgramaIdioma();
        Programa programa = curso.getPrograma();
        ControlMaestroMultiple idioma = curso.getIdioma();
        PAModalidad modalidad = grupo.getPaModalidad();
        Articulo articulo = articuloDao.findFirstByProgramaIdiomaIdAndModalidadId(curso.getId(), modalidad.getId());
        Integer articuloId = articulo.getId();

        BigDecimal precioVentaCurso = grupo.getPrecioVentaCurso() != null ? grupo.getPrecioVentaCurso() : new BigDecimal(0);
        BigDecimal precioVentaLibro = grupo.getPrecioVentaLibro() != null ? grupo.getPrecioVentaLibro() : new BigDecimal(0);
        BigDecimal precioVentaCertificacion = grupo.getPrecioVentaCertificacion() != null ? grupo.getPrecioVentaCertificacion() : new BigDecimal(0);
        BigDecimal precioUnitario = precioVentaCurso.add(precioVentaLibro).add(precioVentaCertificacion);

        if(alumnoDao.getAlumnoCursandoIdioma(alumnoId,idioma.getId(),null,false)){
            return new JsonResponse(null,"No es posible inscribir al alumno " + alumno.getCodigo() + " al idioma " + idioma.getValor() + " debido a que ya está cursando el mismo.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        BigDecimal tasaIVA = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        BigDecimal tasaIEPS = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();

        ordenVentaDetalle = new OrdenVentaDetalle();
        ordenVentaDetalle.setArticuloId(articuloId);
        ordenVentaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
        ordenVentaDetalle.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
        ordenVentaDetalle.setCantidad(new BigDecimal(1));
        ordenVentaDetalle.setPrecio(precioUnitario);
        ordenVentaDetalle.setDescuento(new BigDecimal(0));
        ordenVentaDetalle.setIva(tasaIVA);
        ordenVentaDetalle.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
        ordenVentaDetalle.setIeps(tasaIEPS);
        ordenVentaDetalle.setIepsCuotaFija(articulo.getIepsCuotaFija());

        ordenVentaDetalle.setDescripcion(grupo.getSucursal().getNombre() + " " + curso.getCodigo() + " " + curso.getNombre() + " " + modalidad.getNombre() + " " + grupo.getModalidadHorario().getNombre() + " " + grupo.getGrupo());
        ordenVentaDetalle.setAlumnoId(alumnoId);
        ordenVentaDetalle.setNombreAlumno(alumno.getNombre() + " " + alumno.getPrimerApellido() + (alumno.getSegundoApellido() != null ? (" " + alumno.getSegundoApellido()) : ""));

        MontosCalculadosProjection montosCalculados = articuloDao.getMontosCalculados(new BigDecimal(1),precioUnitario,new BigDecimal(0),tasaIVA,tasaIEPS,articulo.getIepsCuotaFija());
        ordenVentaDetalle.setMontoSubtotal(montosCalculados.getSubtotal());
        ordenVentaDetalle.setMontoIva(montosCalculados.getIva());
        ordenVentaDetalle.setMontoIeps(montosCalculados.getIeps());
        ordenVentaDetalle.setTotal(montosCalculados.getTotal());

        ordenVentaDetalle.setLocalidadId(localidadId);

        ordenVentaDetalle.setIdTmp(idTmp);

        ordenVentaDetalle.setGrupoId(grupo.getId());
        ordenVentaDetalle.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());

        ordenVentaDetalle.setNoAplicaDescuentos(true);

        detallesResponse.add(ordenVentaDetalle);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("ordenVentaDetalles",detallesResponse);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    private void rollback(){
        TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
        transactionStatus.setRollbackOnly();
        transactionStatus.flush();
    }

    private boolean aplicarBecaUDG(Integer becaUDGId, Inscripcion inscripcion, InscripcionSinGrupo inscripcionSinGrupo, String codigoOV) throws Exception {
        String sede;
        String nivel;
        String horario = "";
        String grupo = "";
        String codigo = "";
        String fechaInicio = "";
        String fechaFin = "";
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (inscripcion != null) {
            ProgramaGrupo programaGrupo = programaGrupoDao.findById(inscripcion.getGrupoId());
            sede = programaGrupo.getSucursal().getCodigoSucursal();
            nivel = programaGrupo.getNivel() > 9 ? "" + programaGrupo.getNivel() : "0" + programaGrupo.getNivel();
            horario = (programaGrupo.getHorario() != null ? programaGrupo.getHorario() : "").replace(" ", "");
            grupo = "" + programaGrupo.getGrupo();
            codigo = programaGrupo.getCodigoGrupo();
            fechaInicio = new SimpleDateFormat("yyyy-MM-dd").format(programaGrupo.getFechaInicio());
            fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(programaGrupo.getFechaFin());
        } else {
            Sucursal sucursal = sucursalDao.findById(inscripcionSinGrupo.getSucursalId());
            sede = sucursal.getCodigoSucursal();
            nivel = inscripcionSinGrupo.getNivel() > 9 ? inscripcionSinGrupo.getNivel().toString() : ("0" + inscripcionSinGrupo.getNivel());
        }

        BecaUDG becaUDG = becaUDGDao.findById(becaUDGId);

        if (!becaUDG.getEstatusId().equals(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR)) {
            return false;
        }

        becaUDG.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.APLICADA);
        becaUDGDao.save(becaUDG);
        becaUDGDao.actualizarBecaSIAP("AP", sede, nivel, horario, grupo, codigo, fechaInicio, fechaFin, fecha, codigoOV, becaUDG.getSiapId(), inscripcion == null);

        return true;
    }

    private void afectarInventarioInscripcion(Alumno alumno, Integer programaId, Integer idiomaId, Integer nivel, Integer articuloCursoId, Integer listaPreciosId, Integer localidadId, String codigoOV, Integer ovdId, Integer usuarioId) throws Exception {
        // Mapear detalles de OV por artículo
        OrdenVenta ov = ordenVentaDao.findByCodigo(codigoOV);
        HashMap<Integer,Integer> dictArticuloIdDetalleId = new HashMap<>();
        for(OrdenVentaDetalle detalleOV : ov.getDetalles()){
            if(detalleOV.getDetallePadreId() != null && detalleOV.getDetallePadreId().intValue() == ovdId.intValue()){
                dictArticuloIdDetalleId.put(detalleOV.getArticuloId(), detalleOV.getId());
            }
        }
        List<Inscripcion> inscripcionesOVD = inscripcionDao.findByOrdenVentaDetalleId(ovdId);
        List<Integer> inscripcionesActualesIds = new ArrayList<>();
        for(Inscripcion inscripcion : inscripcionesOVD){
            if(inscripcion.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA || inscripcion.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO){
                inscripcionesActualesIds.add(inscripcion.getId());
            }
        }

        // Se obtienen los libros necesarios para la inscripción actual
        List<Articulo> articulos = articuloDao.findLibrosByCurso(programaId,idiomaId,nivel,alumno.getCarreraJOBSId());

        // Se obtiene el plazo de dias para el vencimiento de una reinscripción
        Integer plazoDiasReinscripcion = 0;
        ControlMaestro cmaPlazoDiasReinscripcion = controlMaestroDao.findCMByNombre("CMA_Inscripciones_PlazoDiasReinscripcion");
        if(cmaPlazoDiasReinscripcion != null){
            plazoDiasReinscripcion = Integer.parseInt(cmaPlazoDiasReinscripcion.getValor());
        }

        List<Articulo> librosNivelAnterior = new ArrayList<>();
        HashMap<Integer,Articulo> librosNivelAnteriorMap = new HashMap<>();

        // Se obtienen los libros de la inscripción al nivel anterior (en caso de que exista)
        List<Inscripcion> inscripcionesNivelAnterior = inscripcionDao.findAllByAlumnoIdAndIdiomaIdAndNivelAndNotCancelada(alumno.getId(),programaId,idiomaId,nivel-1);
        for(Inscripcion inscripcionNivelAnterior : inscripcionesNivelAnterior){
            if(inscripcionNivelAnterior != null){ // Se valida que la reinscripción siga vigente
                Date fechaVigencia = DateUtil.fechaSumar(inscripcionNivelAnterior.getGrupo().getFechaFin(),plazoDiasReinscripcion);
                Date fechaActual = new Date();
                if(fechaActual.compareTo(fechaVigencia) <= 0){
                    librosNivelAnterior.addAll(articuloDao.findLibrosByCurso(programaId,idiomaId,nivel-1,alumno.getCarreraJOBSId()));
                }
            }
        }
        // En caso de que el alumno se haya inscrito previamente al nivel actual y no haya vencido el plazo de reinscripción entonces no se entregan libros
        List<Inscripcion> inscripcionesPreviasNivelActual;
        if(inscripcionesActualesIds.size() > 0){
            inscripcionesPreviasNivelActual = inscripcionDao.findAllByAlumnoIdAndIdiomaIdAndNivelAndNotCancelada(alumno.getId(),programaId,idiomaId,nivel,inscripcionesActualesIds);
        }else{
            inscripcionesPreviasNivelActual = inscripcionDao.findAllByAlumnoIdAndIdiomaIdAndNivelAndNotCancelada(alumno.getId(),programaId,idiomaId,nivel);
        }
        if(inscripcionesPreviasNivelActual.size() > 0){ // Se valida que la reinscripción siga vigente
            for(Inscripcion inscripcionPreviaNivelActual : inscripcionesPreviasNivelActual){
                if(inscripcionPreviaNivelActual.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA) { // Se valida que la reinscripción siga vigente
                    Date fechaVigencia = DateUtil.fechaSumar(inscripcionPreviaNivelActual.getGrupo().getFechaFin(),plazoDiasReinscripcion);
                    Date fechaActual = new Date();
                    if(fechaActual.compareTo(fechaVigencia) <= 0){
                        librosNivelAnterior.addAll(articuloDao.findLibrosByCurso(programaId,idiomaId,nivel,alumno.getCarreraJOBSId()));
                    }
                }
            }
        }

        for(Articulo libro : librosNivelAnterior){
            librosNivelAnteriorMap.put(libro.getId(),libro);
        }

        for(Articulo articulo : articulos){
            if(articulo.getInventariable()){
                Boolean entregarLibro = true;

                if(librosNivelAnteriorMap.get(articulo.getId()) != null){
                    entregarLibro = false;
                }

                if(entregarLibro){
                    BigDecimal precioVenta = articuloDao.findPrecioVenta(articulo.getId(),articuloCursoId,listaPreciosId);
                    if(precioVenta == null){
                        precioVenta = BigDecimal.ZERO;
                    }

                    procesadorInventariosService.procesaMovimiento(
                            articulo.getId(),
                            localidadId,
                            BigDecimal.ONE.negate(),
                            "Inscripción: " + alumno.getCodigo(),
                            codigoOV,
                            dictArticuloIdDetalleId.get(articulo.getId()) != null ? dictArticuloIdDetalleId.get(articulo.getId()) : ovdId,
                            precioVenta,
                            ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.INSCRIPCION,
                            usuarioId
                    );
                }
            }
        }
    }

    private OrdenVenta desglosarOV(OrdenVenta ov) throws Exception {
        Integer listaPreciosId = ov.getListaPreciosId();
        OrdenVenta ovGuardada = ordenVentaDao.save(ov);

        List<OrdenVentaDetalle> detallesDesglosados = new ArrayList<>();
        for(OrdenVentaDetalle ovd : ovGuardada.getDetalles()){
            if(ovd.getAlumnoId() != null){
                Alumno alumno = alumnoDao.findById(ovd.getAlumnoId());
                Integer programaId = ovd.getProgramaId();
                Integer idiomaId = ovd.getIdiomaId();
                Integer nivel = ovd.getNivel();
                if(ovd.getGrupoId() != null){
                    ProgramaGrupo grupo = programaGrupoDao.findById(ovd.getGrupoId());
                    programaId = grupo.getProgramaIdioma().getProgramaId();
                    idiomaId = grupo.getProgramaIdioma().getIdiomaId();
                    nivel = grupo.getNivel();
                }

                Articulo articuloOVD = articuloDao.findById(ovd.getArticuloId());

                BigDecimal totalDescontar = BigDecimal.ZERO;
                List<Articulo> articulos = articuloDao.findLibrosByCurso(programaId,idiomaId,nivel,alumno.getCarreraJOBSId());
                articulos.addAll(articuloDao.findCertificacionesByCurso(programaId,idiomaId));
                BigDecimal porcentajeDescuento = BigDecimal.ZERO;
                if(ovd.getDescuentoSinConvertir() != null && ovd.getDescuentoSinConvertir().compareTo(BigDecimal.ZERO) != 0 && ovd.getPrecioSinConvertir().compareTo(BigDecimal.ZERO) != 0){
                    porcentajeDescuento = ovd.getDescuentoSinConvertir().divide(ovd.getPrecioSinConvertir().multiply(ovd.getCantidad()),10, RoundingMode.HALF_UP);
                }
                for(Articulo articulo : articulos){

                    BigDecimal precioVenta = articuloDao.findPrecioVenta(articulo.getId(),articuloOVD.getId(),listaPreciosId);
                    if(precioVenta == null){
                        precioVenta = BigDecimal.ZERO;
                    }
                    totalDescontar = totalDescontar.add(precioVenta);
                    BigDecimal precioUnitarioSinConvertir;
                    BigDecimal precioUnitarioConvertido;
                    BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
                    BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
                    if(articulo.getIepsCuotaFija() == null){
                        precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
                    }else{
                        precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
                    }
                    precioUnitarioConvertido = precioUnitarioSinConvertir.multiply(ovGuardada.getTipoCambio()).setScale(6);

                    OrdenVentaDetalle ovdDesglosado = new OrdenVentaDetalle();
                    ovdDesglosado.setArticuloId(articulo.getId());
                    ovdDesglosado.setUnidadMedidaId(articulo.getUnidadMedidaConversionVentasId() != null ? articulo.getUnidadMedidaConversionVentasId() : articulo.getUnidadMedidaInventarioId());
                    ovdDesglosado.setFactorConversion((articulo.getUnidadMedidaConversionVentasId() != null && articulo.getFactorConversionVentas() != null) ? articulo.getFactorConversionVentas() : BigDecimal.ONE);
                    ovdDesglosado.setCantidad(BigDecimal.ONE);
                    ovdDesglosado.setPrecio(precioUnitarioConvertido);
                    ovdDesglosado.setPrecioSinConvertir(precioUnitarioSinConvertir);
                    ovdDesglosado.setDescuento(precioUnitarioConvertido.multiply(porcentajeDescuento));
                    ovdDesglosado.setDescuentoSinConvertir(precioUnitarioSinConvertir.multiply(porcentajeDescuento));
                    ovdDesglosado.setIva(tasaIva);
                    ovdDesglosado.setIvaExento(articulo.getIvaExento() != null ? articulo.getIvaExento() : false);
                    ovdDesglosado.setIeps(tasaIeps);
                    ovdDesglosado.setIepsCuotaFija(articulo.getIepsCuotaFija());
                    ovdDesglosado.setDetallePadreId(ovd.getId());

                    detallesDesglosados.add(ovdDesglosado);
                }

                BigDecimal precioUnitarioOVDSinConvertir = null;
                BigDecimal precioUnitarioOVDConvertido = null;
                ProgramaGrupo grupo = null;
                if(ovd.getGrupoId() != null){
                    grupo = programaGrupoDao.findById(ovd.getGrupoId());
                }
                // Revisar si es inscripción in company
                if(grupo != null && grupo.getGrupoId() != null){ // Si es inscripción in company el precio unitario es la suma del precio de venta del curso, libro y certificación del grupo
                    BigDecimal precioVentaCurso = grupo.getPrecioVentaCurso() != null ? grupo.getPrecioVentaCurso() : BigDecimal.ZERO;
                    BigDecimal precioVentaLibro = grupo.getPrecioVentaLibro() != null ? grupo.getPrecioVentaLibro() : BigDecimal.ZERO;
                    BigDecimal precioVentaCertificacion = grupo.getPrecioVentaCertificacion() != null ? grupo.getPrecioVentaCertificacion() : BigDecimal.ZERO;
                    precioUnitarioOVDSinConvertir = precioVentaCurso.add(precioVentaLibro).add(precioVentaCertificacion);
                }else{ // Si no es inscripción in company, el precio unitario se obtiene del listado de precios
                    BigDecimal precioVentaOVD = articuloDao.findPrecioVenta(articuloOVD.getId(),articuloOVD.getId(),listaPreciosId);
                    if(precioVentaOVD == null){
                        precioVentaOVD = BigDecimal.ZERO;
                    }
                    precioVentaOVD = precioVentaOVD.subtract(totalDescontar);
                    BigDecimal tasaIvaOVD = ((articuloOVD.getIvaExento() != null && articuloOVD.getIvaExento()) || articuloOVD.getIva() == null) ? BigDecimal.ZERO : articuloOVD.getIva();
                    BigDecimal tasaIepsOVD = ((articuloOVD.getIepsCuotaFija() != null && articuloOVD.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articuloOVD.getIeps() == null) ? BigDecimal.ZERO : articuloOVD.getIeps();
                    if(articuloOVD.getIepsCuotaFija() == null){
                        precioUnitarioOVDSinConvertir = precioVentaOVD.divide(BigDecimal.ONE.add(tasaIvaOVD).add(tasaIepsOVD));
                    }else{
                        precioUnitarioOVDSinConvertir = precioVentaOVD.divide(BigDecimal.ONE.add(tasaIvaOVD)).subtract(articuloOVD.getIepsCuotaFija());
                    }
                }
                precioUnitarioOVDConvertido = precioUnitarioOVDSinConvertir.multiply(ovGuardada.getTipoCambio()).setScale(6);
                ovd.setPrecio(precioUnitarioOVDConvertido);
                ovd.setPrecioSinConvertir(precioUnitarioOVDSinConvertir);
                ovd.setDescuento(ovd.getPrecio().multiply(ovd.getCantidad()).multiply(porcentajeDescuento));
                ovd.setDescuentoSinConvertir(ovd.getPrecioSinConvertir().multiply(ovd.getCantidad()).multiply(porcentajeDescuento));
            }
        }
        ovGuardada.getDetalles().addAll(detallesDesglosados);
        return ordenVentaDao.save(ov);
    }

    private EstatusPermiteInscripcionPorExamenUbicacion permiteInscripcionPorExamenUbicacion(Integer alumnoId, Integer programaId, Integer idiomaId, Integer nivel){
        List<AlumnoExamenCertificacion> examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaIdAndNoRelacionado(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,alumnoId,programaId,idiomaId);
        for(AlumnoExamenCertificacion examen : examenesUbicacion){
            if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.EN_PROCESO){
                return EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_EN_PROCESO;
            }
        }
        examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,alumnoId,programaId,idiomaId);
        for(AlumnoExamenCertificacion examen : examenesUbicacion){
            if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO){
                if(nivel > examen.getNivel()){
                    return EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_NIVEL_SUPERIOR;
                }else{
                    return EstatusPermiteInscripcionPorExamenUbicacion.SI_PERMITE_EXAMEN_APLICADO;
                }
            }
        }
        return EstatusPermiteInscripcionPorExamenUbicacion.SI_PERMITE_NO_EXAMEN;

    }

    private Integer getNivelPermisibleExamenUbicacion(Integer alumnoId, Integer programaId, Integer idiomaId){
        List<AlumnoExamenCertificacion> examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,alumnoId,programaId,idiomaId);
        for(AlumnoExamenCertificacion examen : examenesUbicacion){
            if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO){
                return examen.getNivel();
            }
        }
        return null;

    }

    private List<HashMap<String,Object>> crearCardsExtra(String... nombres){
        List<HashMap<String,Object>> cards = new ArrayList<>();
        for(String nombre : nombres){
            HashMap<String,Object> card = new HashMap<>();
            card.put("id",nombre.replaceAll(" ","_").toUpperCase());
            card.put("esExtra",true);
            card.put("nombre",nombre);
            cards.add(card);
        }
        return cards;
    }

    private void crearCardsGrupos(List<ProgramaGrupoCardProjection> grupos, HashMap<String,HashMap<String,Object>> gruposCabecerasMap, List<HashMap<String,Object>> gruposCabeceras, Boolean esMultisede, Boolean agruparPorTipoGrupo){
        for(ProgramaGrupoCardProjection grupo : grupos){
            HashMap<String,Object> grupoCabecera;
            String nombreCabecera = grupo.getNombre();
            if(esMultisede){
                nombreCabecera = grupo.getSucursalId().toString() + nombreCabecera;
            }
            if(agruparPorTipoGrupo){
                nombreCabecera += grupo.getTipoGrupo();
            }
            grupoCabecera = gruposCabecerasMap.get(nombreCabecera);
            if(grupoCabecera == null){
                grupoCabecera = new HashMap<String,Object>();
                grupoCabecera.put("esMultisede",grupo.getEsMultisede());
                grupoCabecera.put("sucursal",grupo.getSucursalNombre());
                grupoCabecera.put("nombre",grupo.getNombre());
                grupoCabecera.put("color",grupo.getColor());
                grupoCabecera.put("fechaInicio",grupo.getFechaInicio());
                grupoCabecera.put("fechaFin",grupo.getFechaFin());
                grupoCabecera.put("articuloId",grupo.getArticuloId());
                grupoCabecera.put("precioVentaInCompany",grupo.getPrecioVentaInCompany());
                grupoCabecera.put("grupos",new ArrayList<>());
                if(agruparPorTipoGrupo){
                    grupoCabecera.put("tipoGrupo",grupo.getTipoGrupo());
                }
                gruposCabecerasMap.put(nombreCabecera,grupoCabecera);
                gruposCabeceras.add(grupoCabecera);
            }
            List<ProgramaGrupoCardProjection> subgrupos = (ArrayList)grupoCabecera.get("grupos");
            subgrupos.add(grupo);
            grupoCabecera.put("grupos",subgrupos);
        }
    }

    private void crearCardsGruposSinRelacionar(List<HashMap<String,Object>> gruposCabeceras, Integer programaId, Integer idiomaId, Integer modalidadId){
        crearCardsGruposSinRelacionar(gruposCabeceras,programaId,idiomaId,modalidadId,null);
    }

    private void crearCardsGruposSinRelacionar(List<HashMap<String,Object>> gruposCabeceras, Integer programaId, Integer idiomaId, Integer modalidadId, Integer tipoGrupoId){
        ProgramaIdioma curso = programaIdiomaDao.findByProgramaIdAndIdiomaIdAndActivoIsTrue(programaId,idiomaId);
        PAModalidad paModalidad = paModalidadDao.findById(modalidadId);

        Integer articuloId = null;
        if(!curso.getAgruparListadosPreciosPorTipoGrupo()){
            articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadId(programaId,idiomaId,modalidadId);
        }

        if(tipoGrupoId == null){
            List<ControlMaestroMultipleComboProjection> tiposGrupo = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
            for(ControlMaestroMultipleComboProjection tipoGrupo : tiposGrupo){
                if(curso.getAgruparListadosPreciosPorTipoGrupo()){
                    articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadIdAndTipoGrupoId(programaId,idiomaId,modalidadId,tipoGrupo.getId());
                }

                HashMap<String,Object> grupoCabecera = new HashMap<String,Object>();
                grupoCabecera.put("esSinRelacionar",true);
                grupoCabecera.put("esMultisede",false);
                grupoCabecera.put("sucursal","");
                grupoCabecera.put("nombre","Sin relacionar a grupo");
                grupoCabecera.put("color",paModalidad.getColor());
                grupoCabecera.put("fechaInicio",null);
                grupoCabecera.put("fechaFin",null);
                grupoCabecera.put("articuloId",articuloId);
                grupoCabecera.put("grupos",new ArrayList<>());
                grupoCabecera.put("tipoGrupo",tipoGrupo.getValor());
                grupoCabecera.put("tipoGrupoId",tipoGrupo.getId());
                gruposCabeceras.add(grupoCabecera);
            }
        }else{
            if(curso.getAgruparListadosPreciosPorTipoGrupo()){
                articuloId = articuloDao.findIdByProgramaIdAndIdiomaIdAndPaModalidadIdAndTipoGrupoId(programaId,idiomaId,modalidadId,tipoGrupoId);
            }

            HashMap<String,Object> grupoCabecera = new HashMap<String,Object>();
            grupoCabecera.put("esSinRelacionar",true);
            grupoCabecera.put("esMultisede",false);
            grupoCabecera.put("sucursal","");
            grupoCabecera.put("nombre","Sin relacionar a grupo");
            grupoCabecera.put("color",paModalidad.getColor());
            grupoCabecera.put("fechaInicio",null);
            grupoCabecera.put("fechaFin",null);
            grupoCabecera.put("articuloId",articuloId);
            grupoCabecera.put("grupos",new ArrayList<>());
            grupoCabecera.put("tipoGrupoId",tipoGrupoId);
            gruposCabeceras.add(grupoCabecera);
        }
    }

//    private BigDecimal calcularPrecioUnitarioArticulo(Integer articuloId, Integer listaPreciosId){
//        Articulo articulo = articuloDao.findById(articuloId);
//        return calcularPrecioUnitarioArticulo(articulo,listaPreciosId);
//    }

    private BigDecimal calcularPrecioUnitarioSinConvertirArticulo(Articulo articulo, Integer listaPreciosId){
        BigDecimal precioVenta = articuloDao.findPrecioVenta(articulo.getId(),articulo.getId(),listaPreciosId);
        BigDecimal precioUnitarioSinConvertir;
        BigDecimal tasaIva = ((articulo.getIvaExento() != null && articulo.getIvaExento()) || articulo.getIva() == null) ? BigDecimal.ZERO : articulo.getIva();
        BigDecimal tasaIeps = ((articulo.getIepsCuotaFija() != null && articulo.getIepsCuotaFija().compareTo(BigDecimal.ZERO) != 0) || articulo.getIeps() == null) ? BigDecimal.ZERO : articulo.getIeps();
        if(articulo.getIepsCuotaFija() == null){
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva).add(tasaIeps));
        }else{
            precioUnitarioSinConvertir = precioVenta.divide(BigDecimal.ONE.add(tasaIva)).subtract(articulo.getIepsCuotaFija());
        }

        return precioUnitarioSinConvertir;
    }

    private BigDecimal calcularMontoDescuento(BigDecimal cantidad, BigDecimal precioUnitario, BigDecimal porcentajeDescuento){
        return cantidad.multiply(precioUnitario).setScale(6).multiply(porcentajeDescuento).setScale(6);
    }

    private JsonResponse procesarOrdenesVentaExistentes(List<OrdenVenta> ordenesVentaActualizar, HashMap<Integer,OrdenVentaDetalle> detallesActualizarMap, SucursalPlantel plantel, Integer medioPagoPVId, Boolean marcarEntregaPendienteInscripciones, Integer usuarioId, String referenciaPago, Date fechaPago, Integer sucursalCorteCajaId, Integer listaPreciosId) throws Exception{
        SucursalCorteCaja sucursalCorteCaja = sucursalCorteCajaDao.findById(sucursalCorteCajaId);
        for(OrdenVenta ovActualizar : ordenesVentaActualizar){
            if(ovActualizar.getSucursalId().intValue() != sucursalCorteCaja.getSucursalId().intValue()){
                ovActualizar.setSucursal(sucursalCorteCaja.getSucursal());
                ovActualizar.setSucursalId(sucursalCorteCaja.getSucursalId());
            }
            if(ovActualizar.getMedioPagoPV() == null){

                for(OrdenVentaDetalle detalleActualizar : ovActualizar.getDetalles()) {
                    OrdenVentaDetalle detalle = detallesActualizarMap.get(detalleActualizar.getId());
                    if (detalle != null && detalle.getDescuentoSinConvertir() != null) {
                        detalleActualizar.setPrecioSinConvertir(detalle.getPrecioSinConvertir());
                        detalleActualizar.setDescuentoSinConvertir(detalle.getDescuentoSinConvertir());
                        BigDecimal porcentajeDescuento = detalle.getDescuentoSinConvertir().divide(detalle.getPrecioSinConvertir());
                        for(OrdenVentaDetalle subDetalle : ovActualizar.getDetalles()){
                            if(subDetalle.getDetallePadreId() != null && subDetalle.getDetallePadreId().intValue() == detalle.getId()){
                                subDetalle.setDescuentoSinConvertir(subDetalle.getPrecioSinConvertir().multiply(porcentajeDescuento).setScale(6));
                            }
                        }
                    }
                }

                for(OrdenVentaDetalle detalleActualizar : ovActualizar.getDetalles()){

                    detalleActualizar.setPrecio(detalleActualizar.getPrecioSinConvertir().multiply(ovActualizar.getTipoCambio()));
                    if(detalleActualizar.getDescuentoSinConvertir() != null){
                        detalleActualizar.setDescuento(detalleActualizar.getDescuentoSinConvertir().multiply(ovActualizar.getTipoCambio()));
                    }

                    OrdenVentaDetalle detalle = detallesActualizarMap.get(detalleActualizar.getId());
                    if(detalle != null){
                        Integer localidadId;
                        if(plantel == null){
                            localidadId = detalle.getLocalidadId();
                        }else{
                            localidadId = plantel.getLocalidadId();
                        }
                        Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
                        ProgramaGrupo grupo = programaGrupoDao.findById(detalle.getGrupoId());
                        Boolean cambioGrupo = false;
                        Inscripcion inscripcion = inscripcionDao.findByAlumnoIdAndGrupoIdAndNotCancelada(alumno.getId(),grupo.getId());
                        ProgramaGrupo grupoAnterior = programaGrupoDao.findById(grupo.getId());

                        if(detalle.getCambioGrupo() != null && detalle.getCambioGrupo()){
                            cambioGrupo = true;
                            if(detalle.getNuevoGrupoId() != null){
                                grupo = programaGrupoDao.findById(detalle.getNuevoGrupoId());
                            }
                        }
                        if(inscripcion.getOrigenId() != null && (
                                inscripcion.getOrigenId().intValue() == ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PROYECCION
                                        || inscripcion.getOrigenId().intValue() == ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PLANTILLA
                        )){
                            String anio = DateUtil.getFecha(new Date(),"yyyy");
                            ovActualizar.setCodigo(ordenVentaDao.getSiguienteCodigoOV(ovActualizar.getSucursal().getPrefijo()+anio));
                        }

                        if(medioPagoPVId.intValue() != MediosPagoPV.CENTRO_DE_PAGOS.intValue()){
                            if(!marcarEntregaPendienteInscripciones){
                                try {
                                    Integer nivel;
                                    if(!cambioGrupo || detalle.getNuevoGrupoId() != null){
                                        nivel = grupo.getNivel();
                                    }else{
                                        nivel = detalle.getNivel();
                                    }
                                    afectarInventarioInscripcion(alumno, grupo.getProgramaIdioma().getProgramaId(), grupo.getProgramaIdioma().getIdiomaId(), nivel, detalle.getArticuloId(), listaPreciosId, localidadId, ovActualizar.getCodigo(), detalle.getId(), usuarioId);
                                }catch(InventarioNegativoException e){
                                    return new JsonResponse(null,null,JsonResponse.STATUS_ERROR_PUNTO_VENTA_INSCRIPCION_INVENTARIO_NEGATIVO);
                                }
                            }
                        }

                        inscripcion.setEntregaLibrosPendiente(false);
                        if(detalle.getBecaUDGId() != null){
                            inscripcion.setBecaUDGId(detalle.getBecaUDGId());
                        }

                        if(detalle.getCambioGrupo() != null && detalle.getCambioGrupo()){
                            AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoId(alumno.getId(),grupoAnterior.getId());
                            alumnoAsistenciaDao.deleteAllByAlumnoIdAndGrupoId(alumno.getId(),grupoAnterior.getId());
                            alumnoExamenCalificacionDao.deleteAllByAlumnoIdAndGrupoId(alumno.getId(),grupoAnterior.getId());
                            if(detalle.getNuevoGrupoId() != null){
                                alumnoGrupo.setGrupoId(detalle.getNuevoGrupoId());
                                inscripcion.setGrupoId(detalle.getNuevoGrupoId());
                                inscripcion.setGrupo(grupo);
                            }else{
                                alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
                                alumnoGrupo.setModificadoPorId(usuarioId);

                                InscripcionSinGrupo inscripcionSinGrupo = new InscripcionSinGrupo();
                                inscripcionSinGrupo.setOrdenVentaDetalleId(inscripcion.getOrdenVentaDetalleId());
                                inscripcionSinGrupo.setAlumnoId(inscripcion.getAlumnoId());
                                inscripcionSinGrupo.setSucursalId(grupo.getSucursalId());
                                inscripcionSinGrupo.setProgramaId(grupo.getProgramaIdioma().getProgramaId());
                                inscripcionSinGrupo.setIdiomaId(grupo.getProgramaIdioma().getIdiomaId());
                                inscripcionSinGrupo.setPaModalidadId(detalle.getModalidadId());
                                inscripcionSinGrupo.setPaModalidadHorarioId(detalle.getHorarioId());
                                inscripcionSinGrupo.setNivel(detalle.getNivel());
                                inscripcionSinGrupo.setGrupo(grupo.getGrupo());
                                if(medioPagoPVId == MediosPagoPV.EFECTIVO
                                        || medioPagoPVId == MediosPagoPV.TARJETA_DE_CREDITO
                                        || medioPagoPVId == MediosPagoPV.TARJETA_DE_DEBITO
                                        || medioPagoPVId == MediosPagoPV.TRANSFERENCIA_BANCARIA
                                        || medioPagoPVId == MediosPagoPV.ORDEN_DE_PAGO
                                        || medioPagoPVId == MediosPagoPV.PAGO_EN_VENTANILLA
                                        ){
                                    inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
                                    inscripcionSinGrupo.setEntregaLibrosPendiente(false);
                                }else{
                                    inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO);
                                    inscripcionSinGrupo.setEntregaLibrosPendiente(true);
                                }
                                inscripcionSinGrupo.setTipoGrupoId(grupo.getTipoGrupoId());
                                inscripcionSinGrupo.setCreadoPorId(usuarioId);
                                inscripcionSinGrupo = inscripcionSinGrupoDao.save(inscripcionSinGrupo);

                                if(detalle.getBecaUDGId() != null){
                                    aplicarBecaUDG(detalle.getBecaUDGId(),null,inscripcionSinGrupo,ovActualizar.getCodigo());
                                }

                                inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA);
                            }
                            alumnoGrupoDao.save(alumnoGrupo);
                        }

                        if(inscripcion.getOrigenId() != null && (
                                inscripcion.getOrigenId().intValue() == ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PROYECCION
                                        || inscripcion.getOrigenId().intValue() == ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PLANTILLA
                        )){
                            ovActualizar.setFechaCreacion(new Date());
                            ovActualizar.setCreadoPorId(usuarioId);
                            ovActualizar.setFechaOV(new Date());
                        }

                        if(medioPagoPVId.intValue() != MediosPagoPV.CENTRO_DE_PAGOS.intValue()){
                            if(marcarEntregaPendienteInscripciones){
                                if(!cambioGrupo || detalle.getNuevoGrupoId() != null){
                                    inscripcion.setEntregaLibrosPendiente(true);
                                }
                            }
                        }

                        if(!cambioGrupo || detalle.getNuevoGrupoId() != null){
                            if(medioPagoPVId == MediosPagoPV.EFECTIVO
                                    || medioPagoPVId == MediosPagoPV.TARJETA_DE_CREDITO
                                    || medioPagoPVId == MediosPagoPV.TARJETA_DE_DEBITO
                                    || medioPagoPVId == MediosPagoPV.TRANSFERENCIA_BANCARIA
                                    || medioPagoPVId == MediosPagoPV.ORDEN_DE_PAGO
                                    || medioPagoPVId == MediosPagoPV.PAGO_EN_VENTANILLA
                                    ){
                                inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
                            }else{
                                inscripcion.setEntregaLibrosPendiente(true);
                            }
                        }
                        inscripcion.setModificadoPorId(usuarioId);
                        inscripcion.setCreadoPorId(usuarioId);
                        inscripcion.setFechaCreacion(new Date());
                        inscripcionDao.save(inscripcion);

                        if(detalle.getBecaUDGId() != null && (!cambioGrupo || detalle.getNuevoGrupoId() != null)){
                            aplicarBecaUDG(detalle.getBecaUDGId(),inscripcion,null,ovActualizar.getCodigo());
                        }

                        List<AlumnoExamenCertificacion> examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,inscripcion.getAlumnoId(),inscripcion.getGrupo().getProgramaIdioma().getProgramaId(),inscripcion.getGrupo().getProgramaIdioma().getIdiomaId());
                        for(AlumnoExamenCertificacion examen : examenesUbicacion){
                            if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO){
                                examen.setEstatusId(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.RELACIONADO);
                                alumnoExamenCertificacionDao.save(examen);
                            }
                        }
                    }
                }
                ovActualizar.setMedioPagoPVId(medioPagoPVId);
                ovActualizar.setReferenciaPago(referenciaPago);
                if(ovActualizar.getMedioPagoPVId() == MediosPagoPV.EFECTIVO
                        || ovActualizar.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_CREDITO
                        || ovActualizar.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_DEBITO
                        || ovActualizar.getMedioPagoPVId() == MediosPagoPV.TRANSFERENCIA_BANCARIA
                        || ovActualizar.getMedioPagoPVId() == MediosPagoPV.ORDEN_DE_PAGO
                        || ovActualizar.getMedioPagoPVId() == MediosPagoPV.PAGO_EN_VENTANILLA
                        ){
                    ovActualizar.setFechaPago(fechaPago);
                    ovActualizar.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
                    ovActualizar.setMetodoPagoId(ControlesMaestrosMultiples.CMM_OV_MetodoPago.PUE);
                }
                ovActualizar.setModificadoPorId(usuarioId);
                ovActualizar.setSucursalCorteCajaId(sucursalCorteCajaId);
                ordenVentaDao.save(ovActualizar);
            }
        }

        return null;
    }

    /**
     * Al cobrar en el PV, se mandan todos los artículos como detalles de una sola OV, pero algunos artículos
     * son detalles de otras OVs a los que se les actualizan algunos campos al ser cobradas. Esta función separa los
     * artículos e inscripciones que no tienen asignada una OV aún de los que ya lo tienen.
     *
     * @param ordenVenta - La OV que se construye y manda desde el front.
     * @param detallesOV - Un arreglo donde se van a guardar los nuevos detalles de OV.
     * @param ordenesVentaActualizar - Un arreglo donde se van a guardar las OV de los detalles que ya estaban previamente relacionados a OVs.
     * @param detallesActualizarMap - Un diccionario donde se van a mapear los detalles que ya tienen una OV previamente relacionada (Para un facil acceso).
     * @return JsonResponse - En caso de encontrar un problema, se regresa un JsonResponse con el error descrito.
     */
    private JsonResponse separarDetallesOVAlCobrar(OrdenVenta ordenVenta, List<OrdenVentaDetalle> detallesOV, List<OrdenVenta> ordenesVentaActualizar, HashMap<Integer,OrdenVentaDetalle> detallesActualizarMap, Integer listadoPrecioId){

        Moneda monedaPredeterminada = monedaDao.findByPredeterminadaTrue();
        ListadoPrecio listadoPrecio = listadoPrecioDao.findById(listadoPrecioId);

        List<OrdenVentaDetalle> detallesActualizar = new ArrayList<>();
        HashMap<Integer,Boolean> ordenesVentaActualizarAgregadas = new HashMap<>();
        for(OrdenVentaDetalle detalle : ordenVenta.getDetalles()){
            // Si el detalle no tiene un id asignado entonces es un nuevo detalle
            if(detalle.getId() == null){
                detallesOV.add(detalle);
            }
            // Si el detalle ya tiene un id asignado entonces es un detalle que ya está relacionado a una OV
            else{
                detallesActualizar.add(detalle);
                OrdenVenta ordenVentaActualizar = ordenVentaDao.findByDetalleId(detalle.getId());
                if(ordenVentaActualizar.getMedioPagoPV() != null){
                    Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
                    String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
                    if(alumno.getSegundoApellido() != null){
                        nombreAlumno += " " + alumno.getSegundoApellido();
                    }
                    return new JsonResponse(null, "El medio de pago para el alumno " + nombreAlumno + " ya fue asignado", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
                if(ordenesVentaActualizarAgregadas.get(ordenVentaActualizar.getId()) == null){
                    ordenVentaActualizar.setMonedaId(monedaPredeterminada.getId());
                    ordenVentaActualizar.setMonedaSinConvertirId(listadoPrecio.getMonedaId());
                    ordenVentaActualizar.setTipoCambio(ordenVenta.getTipoCambio());

                    ordenesVentaActualizarAgregadas.put(ordenVentaActualizar.getId(),true);
                    ordenesVentaActualizar.add(ordenVentaActualizar);
                }
                detallesActualizarMap.put(detalle.getId(),detalle);
            }
        }

        if(ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS && ((detallesOV.size() > 0 && detallesActualizar.size() > 0) || detallesActualizar.size() > 1)){
            return new JsonResponse(null, "El método de pago no acepta múltiples artículos", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        ordenVenta.setDetalles(detallesOV);

        return null;
    }

    /**
     * Procesa un detalle de la OV de tipo inscipción.
     *
     * @param ordenVenta - La OV que se construye y manda desde el front.
     * @param detalle - El detalle de OV relacionado a la inscripción
     * @param codigoOV - El código de la OV que se construye y manda desde el front.
     * @param localidadId - La localidad donde se va a afectar el inventario.
     * @param usuarioId - El usuario que realizó la venta.
     * @return JsonResponse - En caso de encontrar un problema, se regresa un JsonResponse con el error descrito.
     */
    private JsonResponse procesarInscripcionAlCobrar(OrdenVenta ordenVenta, OrdenVentaDetalle detalle, String codigoOV, Integer localidadId, Integer usuarioId) throws Exception{
        List<Inscripcion> inscripcionesInterferenciaGrupo = inscripcionDao.findAllByInterferenciaGrupo(detalle.getGrupoId(),detalle.getAlumnoId());
        if(inscripcionesInterferenciaGrupo.size() > 0){
            Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
            String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
            if(alumno.getSegundoApellido() != null){
                nombreAlumno += " " + alumno.getSegundoApellido();
            }

            return new JsonResponse(null, "El alumno " + nombreAlumno + " tiene interferencia de horario con el grupo al que se desea inscribir.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }else{
            List<InscripcionSinGrupo> inscripcionesSinGrupoInterferenciaGrupo = inscripcionSinGrupoDao.findAllByInterferenciaGrupo(detalle.getGrupoId(),detalle.getAlumnoId());
            if(inscripcionesSinGrupoInterferenciaGrupo.size() > 0){
                Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
                String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
                if(alumno.getSegundoApellido() != null){
                    nombreAlumno += " " + alumno.getSegundoApellido();
                }

                return new JsonResponse(null, "El alumno " + nombreAlumno + " tiene una inscripción sin grupo pendiente.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
        }

        Integer alumnoId = detalle.getAlumnoId();
        Integer programaId, idiomaId, nivelInscripcion;
        ProgramaGrupo grupo = null;
        if(detalle.getGrupoId() != null){
            grupo = programaGrupoDao.findById(detalle.getGrupoId());
            programaId = grupo.getProgramaIdioma().getProgramaId();
            idiomaId = grupo.getProgramaIdioma().getIdiomaId();
            nivelInscripcion = grupo.getNivel();
        }else{
            programaId = detalle.getProgramaId();
            idiomaId = detalle.getIdiomaId();
            nivelInscripcion = detalle.getNivel();
        }

        EstatusPermiteInscripcionPorExamenUbicacion estatusPermiteInscripcionPorExamenUbicacion = permiteInscripcionPorExamenUbicacion(alumnoId,programaId,idiomaId,nivelInscripcion);
        if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_EN_PROCESO){
            Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
            String nombreAlumno = alumno.getNombre() + " " + alumno.getPrimerApellido();
            if(alumno.getSegundoApellido() != null){
                nombreAlumno += " " + alumno.getSegundoApellido();
            }
            return new JsonResponse(null, "El alumno " + nombreAlumno + " tiene un examen de ubicación pendiente.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }else if(estatusPermiteInscripcionPorExamenUbicacion == EstatusPermiteInscripcionPorExamenUbicacion.NO_PERMITE_NIVEL_SUPERIOR){
            Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
            String mensajeError = new StringBuilder("No es posible inscribir al alumno ")
                    .append(alumno.getCodigo())
                    .append(" al nivel ")
                    .append(nivelInscripcion)
                    .append(". (Nivel máximo: ")
                    .append(getNivelPermisibleExamenUbicacion(alumnoId,programaId,idiomaId))
                    .append(")")
                    .toString();
            return new JsonResponse(null,mensajeError,JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        if(detalle.getGrupoId() != null){

            Integer cupoDisponible = programaGrupoDao.getCupoDisponible(detalle.getGrupoId());
            if(cupoDisponible.intValue() == 0){
                return new JsonResponse(null, "El grupo no cuenta con cupo disponible", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            Inscripcion inscripcion = new Inscripcion();

            inscripcion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("INS"));
            inscripcion.setOrdenVentaDetalleId(detalle.getId());
            inscripcion.setAlumnoId(detalle.getAlumnoId());
            inscripcion.setGrupoId(detalle.getGrupoId());
            inscripcion.setBecaUDGId(detalle.getBecaUDGId());
            inscripcion.setCreadoPorId(usuarioId);
            inscripcion.setOrigenId(ControlesMaestrosMultiples.CMM_INO_InscripcionOrigen.PUNTO_DE_VENTA);

            Alumno alumno = null;

            if (ordenVenta.getMedioPagoPVId() == MediosPagoPV.EFECTIVO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_CREDITO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_DEBITO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TRANSFERENCIA_BANCARIA
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.ORDEN_DE_PAGO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.PAGO_EN_VENTANILLA
                    || (ordenVenta.getMonto().compareTo(BigDecimal.ZERO) == 0 && ordenVenta.getMedioPagoPVId() == MediosPagoPV.EFECTIVO)
                    ) {
                inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA);
                inscripcion.setEntregaLibrosPendiente(false);

                alumno = alumnoDao.findById(detalle.getAlumnoId());
                if(ordenVenta.getMarcarEntregaPendienteInscripciones()){
                    inscripcion.setEntregaLibrosPendiente(true);
                }else{
                    try {
                        afectarInventarioInscripcion(alumno, grupo.getProgramaIdioma().getProgramaId(), grupo.getProgramaIdioma().getIdiomaId(), grupo.getNivel(), detalle.getArticuloId(), ordenVenta.getListaPreciosId(), localidadId, codigoOV, detalle.getId(), usuarioId);
                    }catch(InventarioNegativoException e){
                        return new JsonResponse(null,null,JsonResponse.STATUS_ERROR_PUNTO_VENTA_INSCRIPCION_INVENTARIO_NEGATIVO);
                    }
                }
            } else if (ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS) {
                inscripcion.setEstatusId(ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO);
                inscripcion.setEntregaLibrosPendiente(true);

                alumno = alumnoDao.findById(detalle.getAlumnoId());
            }

            inscripcion = inscripcionDao.save(inscripcion);

            if (inscripcion.getEstatusId().equals(ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA) && grupo.getMultisede()){
                if (ordenVenta.getSucursalId() != grupo.getSucursalId()){
                    //Iniciar alerta
                    String mensaje = "Alumno: "+alumno.getCodigo()+"\n Grupo: "+grupo.getCodigoGrupo();
                    procesadorAlertasService.validarAutorizacion(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE,inscripcion.getId(), inscripcion.getCodigo(), "Inscripción Multisede", grupo.getSucursalId(), usuarioId, mensaje);
                }
            }

            AlumnoGrupo alumnoGrupo = new AlumnoGrupo();

            alumnoGrupo.setAlumnoId(detalle.getAlumnoId());
            alumnoGrupo.setGrupoId(detalle.getGrupoId());
            alumnoGrupo.setAsistencias(0);
            alumnoGrupo.setFaltas(0);
            alumnoGrupo.setMinutosRetardo(0);
            alumnoGrupo.setCreadoPorId(1);
            alumnoGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REGISTRADO);
            alumnoGrupo.setInscripcionId(inscripcion.getId());

            alumnoGrupoDao.save(alumnoGrupo);

            List<AlumnoExamenCertificacion> examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,alumno.getId(),grupo.getProgramaIdioma().getProgramaId(),grupo.getProgramaIdioma().getIdiomaId());
            for(AlumnoExamenCertificacion examen : examenesUbicacion){
                if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO){
                    examen.setEstatusId(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.RELACIONADO);
                    alumnoExamenCertificacionDao.save(examen);
                }
            }
        }else{
            InscripcionSinGrupo inscripcionSinGrupo = new InscripcionSinGrupo();

            inscripcionSinGrupo.setOrdenVentaDetalleId(detalle.getId());
            inscripcionSinGrupo.setAlumnoId(detalle.getAlumnoId());
            inscripcionSinGrupo.setSucursalId(detalle.getSucursalId());
            inscripcionSinGrupo.setProgramaId(detalle.getProgramaId());
            inscripcionSinGrupo.setIdiomaId(detalle.getIdiomaId());
            inscripcionSinGrupo.setPaModalidadId(detalle.getModalidadId());
            inscripcionSinGrupo.setPaModalidadHorarioId(detalle.getHorarioId());
            inscripcionSinGrupo.setNivel(detalle.getNivel());
            inscripcionSinGrupo.setGrupo(detalle.getNumeroGrupo());
            inscripcionSinGrupo.setComentario(detalle.getComentarioReinscripcion());
            inscripcionSinGrupo.setBecaUDGId(detalle.getBecaUDGId());
            inscripcionSinGrupo.setTipoGrupoId(detalle.getTipoGrupoId());
            inscripcionSinGrupo.setCreadoPorId(usuarioId);

            if (ordenVenta.getMedioPagoPVId() == MediosPagoPV.EFECTIVO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_CREDITO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TARJETA_DE_DEBITO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.TRANSFERENCIA_BANCARIA
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.ORDEN_DE_PAGO
                    || ordenVenta.getMedioPagoPVId() == MediosPagoPV.PAGO_EN_VENTANILLA
                    || (ordenVenta.getMonto().compareTo(BigDecimal.ZERO) == 0 && ordenVenta.getMedioPagoPVId() == MediosPagoPV.EFECTIVO)
                    ) {

                inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA);
                inscripcionSinGrupo.setEntregaLibrosPendiente(false);

                Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
                if(ordenVenta.getMarcarEntregaPendienteInscripciones()){
                    inscripcionSinGrupo.setEntregaLibrosPendiente(true);
                }else{
                    try {
                        afectarInventarioInscripcion(alumno, detalle.getProgramaId(), detalle.getIdiomaId(), detalle.getNivel(), detalle.getArticuloId(), ordenVenta.getListaPreciosId(), localidadId, codigoOV, detalle.getId(), usuarioId);
                    }catch(InventarioNegativoException e){
                        return new JsonResponse(null,null,JsonResponse.STATUS_ERROR_PUNTO_VENTA_INSCRIPCION_INVENTARIO_NEGATIVO);
                    }
                }
            } else if (ordenVenta.getMedioPagoPVId() == MediosPagoPV.CENTRO_DE_PAGOS) {

                inscripcionSinGrupo.setEstatusId(ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO);
                inscripcionSinGrupo.setEntregaLibrosPendiente(true);

                Alumno alumno = alumnoDao.findById(detalle.getAlumnoId());
            }

            inscripcionSinGrupoDao.save(inscripcionSinGrupo);

            List<AlumnoExamenCertificacion> examenesUbicacion = alumnoExamenCertificacionDao.findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(ControlesMaestrosMultiples.CMM_ALUEC_Tipo.EXAMEN,detalle.getAlumnoId(),detalle.getProgramaId(),detalle.getIdiomaId());
            for(AlumnoExamenCertificacion examen : examenesUbicacion){
                if(examen.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_ALUEC_Estatus.FINALIZADO){
                    examen.setEstatusId(ControlesMaestrosMultiples.CMM_ALUEC_Estatus.RELACIONADO);
                    alumnoExamenCertificacionDao.save(examen);
                }
            }
        }

        return null;
    }

}
