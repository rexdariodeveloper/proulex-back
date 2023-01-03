package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ArchivosEstructurasCarpetas;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Alumno.AlumnoEditarProjection;
import com.pixvs.main.models.projections.Alumno.AlumnoListadoProjection;
import com.pixvs.main.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboResponsablesProjection;

import com.pixvs.main.models.projections.Inscripcion.InscripcionProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ControlMaestroMultipleService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 31/05/2021.
 */
@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    // Daos
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ControlMaestroMultipleDatosAdicionalesDao controlMaestroMultipleDatosAdicionalesDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private BecaUDGDao becaUDGDao;
    @Autowired
    private UsuarioDao usuarioDao;

    // Services
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ControlMaestroMultipleService controlMaestroMultipleService;
    @Autowired
    private InscripcionDao inscripcionDao;

    @Autowired
    private SATRegimenFiscalDao regimenFiscalDao;

    // Controllers
    @Autowired
    private ExcelController excelController;

    // Otros
    @Autowired
    private HashId hashId;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    //*********************//
    //***** Consultas *****//
    //*********************//

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlumnos() {
        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("filtros", true);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) {
        String codigo = StringCheck.getValue((String) json.get("codigo"));
        String nombre = StringCheck.getValue((String) json.get("nombre"));
        List<String> listCodigo = codigo != null ? Arrays.asList(codigo.split(" ")) : null;
        List<String> listNombre = nombre != null ? Arrays.asList(nombre.split(" ")) : null;
        String whereCodigo = "";
        String whereNombre = "";

        if (codigo == null && nombre == null) {
            return new JsonResponse(new ArrayList<>(), null, JsonResponse.STATUS_OK);
        }

        if (listCodigo != null) {
            for (String valor : listCodigo) {
                whereCodigo += "    AND codigo COLLATE Latin1_general_CI_AI LIKE '%" + valor.trim() + "%' \n";
            }
        }

        if (listNombre != null) {
            for (String valor : listNombre) {
                whereNombre += "    AND nombreAlternativo COLLATE Latin1_general_CI_AI LIKE '%" + valor.trim() + "%' \n";
            }
        }

        String query =
                "SELECT *\n" +
                "FROM VW_Listado_Alumnos\n" +
                "WHERE 1 = 1 \n" +
                whereCodigo +
                whereNombre +
                "ORDER BY codigo\n" +
                "OPTION(RECOMPILE)";

        List<Object[]> results = em.createNativeQuery(query).getResultList();
        JSONArray listJson = new JSONArray();

        for (Object[] row : results) {
            listJson.add(JSONValue.parse(
                    "{              " +
                        "\"id\": " + row[0] + ", " +
                        "\"codigo\": \"" + row[1] + "\", " +
                        "\"nombre\": \"" + row[2] + "\", " +
                        "\"apellidos\": \"" + row[3] + "\", " +
                        "\"nombreAlternativo\": \"" + row[4] + "\", " +
                        "\"edad\": \"" + row[5] + "\", " +
                        "\"correoElectronico\": \"" + row[6] + "\", " +
                        "\"telefono\": \"" + row[7] + "\", " +
                        "\"sucursalNombre\": \"" + row[8] + "\", " +
                        "\"activo\": " + row[9] + ", " +
                        "\"referencia\": \"" + row[10] + "\" " +
                        "          }"));
        }

        return new JsonResponse(JSONValue.parse(listJson.toString()), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Alumno alumno, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (alumno.getId() == null) {
            alumno.setCreadoPorId(idUsuario);
            alumno.setActivo(true);
            alumno.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PLX"));
            alumno.setReferencia(alumno.getCodigo() + getDigito(alumno.getCodigo()));
        } else {
            Alumno objetoActual = alumnoDao.findById(alumno.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), alumno.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            alumno.setModificadoPorId(idUsuario);
            alumno.setReferencia(alumno.getCodigo() + getDigito(alumno.getCodigo()));

            if (alumno.getImg64() != null && alumno.getFotoId() != null) {
                fileSystemStorageService.borrarArchivo(alumno.getFotoId());
            }
        }

        // Settear id de subpropiedades en cabecera
        alumno.setSucursalId(alumno.getSucursal() != null ? alumno.getSucursal().getId() : null);
        alumno.setPaisNacimientoId(alumno.getPaisNacimiento() != null ? alumno.getPaisNacimiento().getId() : null);
        alumno.setEstadoNacimientoId(alumno.getEstadoNacimiento() != null ? alumno.getEstadoNacimiento().getId() : null);
        alumno.setGeneroId(alumno.getGenero() != null ? alumno.getGenero().getId() : null);
        alumno.setPaisId(alumno.getPais() != null ? alumno.getPais().getId() : null);
        alumno.setEstadoId(alumno.getEstado() != null ? alumno.getEstado().getId() : null);
        if(alumno.getMunicipioNacimiento() != null){
            alumno.setMunicipioNacimientoId(alumno.getMunicipioNacimiento().getId());
            alumno.setCiudadNacimiento(null);
        }else{
            alumno.setMunicipioNacimientoId(null);
        }
        if(alumno.getMunicipio() != null){
            alumno.setMunicipioId(alumno.getMunicipio().getId());
            alumno.setCiudad(null);
        }else{
            alumno.setMunicipioId(null);
        }
        if(alumno.getMedioEnteradoProulex() != null){
            alumno.setMedioEnteradoProulexId(alumno.getMedioEnteradoProulex().getId());
        }else{
            alumno.setMedioEnteradoProulexId(null);
        }
        if(alumno.getRazonEleccionProulex() != null){
            alumno.setRazonEleccionProulexId(alumno.getRazonEleccionProulex().getId());
        }else{
            alumno.setRazonEleccionProulexId(null);
        }
        alumno.setGradoId(alumno.getGrado() != null ? alumno.getGrado().getId() : null);
        alumno.setTurnoId(alumno.getTurno() != null ? alumno.getTurno().getId() : null);
        if(alumno.getProgramaJOBS() != null){
            alumno.setProgramaJOBSId(alumno.getProgramaJOBS().getId());
        }else{
            alumno.setProgramaJOBSId(null);
        }
        if(alumno.getCentroUniversitarioJOBS() != null){
            alumno.setCentroUniversitarioJOBSId(alumno.getCentroUniversitarioJOBS().getId());
        }else{
            alumno.setCentroUniversitarioJOBSId(null);
        }
        if(alumno.getPreparatoriaJOBS() != null){
            alumno.setPreparatoriaJOBSId(alumno.getPreparatoriaJOBS().getId());
        }else{
            alumno.setPreparatoriaJOBSId(null);
        }
        if(alumno.getCarreraJOBS() != null){
            alumno.setCarreraJOBSId(alumno.getCarreraJOBS().getId());
        }else{
            alumno.setCarreraJOBSId(null);
        }
        alumno.setTipoAlumnoId(ControlesMaestrosMultiples.CMM_ALU_TipoAlumno.ALUMNO);

        // Settear id de subpropiedades en contactos
        for(AlumnoContacto contacto : alumno.getContactos()){
            contacto.setParentescoId(contacto.getParentesco().getId());
        }

        for (AlumnoDatosFacturacion facturacion : alumno.getFacturacion()) {
            facturacion.getDatosFacturacion().setTipoPersonaId(facturacion.getDatosFacturacion().getTipoPersona().getId());
            facturacion.getDatosFacturacion().setPaisId(facturacion.getDatosFacturacion().getPais() != null ? facturacion.getDatosFacturacion().getPais().getId() : null);
            facturacion.getDatosFacturacion().setEstadoId(facturacion.getDatosFacturacion().getEstado() != null ? facturacion.getDatosFacturacion().getEstado().getId() : null);

            if (facturacion.getDatosFacturacion().getMunicipio() != null) {
                facturacion.getDatosFacturacion().setMunicipioId(facturacion.getDatosFacturacion().getMunicipio().getId());
                facturacion.getDatosFacturacion().setCiudad(null);
            } else {
                facturacion.getDatosFacturacion().setMunicipioId(null);
            }

            facturacion.getDatosFacturacion().setRegimenFiscalId(facturacion.getDatosFacturacion().getRegimenFiscal() != null ? facturacion.getDatosFacturacion().getRegimenFiscal().getId() : null);
        }

        if (alumno.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(alumno.getImg64(), idUsuario, ArchivosEstructurasCarpetas.ALUMNOS.FOTOS, null, true, true);
            alumno.setFotoId(archivo.getId());
        }

        alumno = alumnoDao.save(alumno);

        return new JsonResponse(alumno.getId(), null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idAlumno}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idAlumno) throws SQLException {

        Alumno alumno = alumnoDao.findById(hashId.decode(idAlumno));
        alumno.setActivo(false);
        alumnoDao.save(alumno);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoAlumnoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();
        AlumnoEditarProjection alumno = null;
        List<EstadoComboProjection> estadosNacimiento = new ArrayList<>();
        List<EstadoComboProjection> estados = estadoDao.findProjectedComboAllByPaisId(1);
        List<MunicipioComboProjection> municipiosNacimiento = new ArrayList<>();
        List<MunicipioComboProjection> municipios = new ArrayList<>();
        List<ControlMaestroMultipleComboProjection> carreras = new ArrayList<>();
        List<InscripcionProjection> inscripciones = new ArrayList<>();

        // Datos solo de edición
        if (id != null) {
            alumno = alumnoDao.findProjectedEditarById(id);
            if(alumno.getPaisNacimiento() != null) {
                estadosNacimiento = estadoDao.findProjectedComboAllByPaisId(alumno.getPaisNacimiento().getId());
            }
            if(alumno.getPais() != null) {
                estados = estadoDao.findProjectedComboAllByPaisId(alumno.getPais().getId());
            }
            if(alumno.getEstadoNacimiento() != null) {
                municipiosNacimiento = municipioDao.findProjectedComboAllByEstadoId(alumno.getEstadoNacimiento().getId());
            }
            if(alumno.getEstado() != null) {
                municipios = municipioDao.findProjectedComboAllByEstadoId(alumno.getEstado().getId());
            }
            if(alumno.getCentroUniversitarioJOBS() != null){
                carreras = controlMaestroMultipleDao.findAllByCmmReferenciaIdAndActivoIsTrueOrderByValor(alumno.getCentroUniversitarioJOBS().getId());
            }
            inscripciones = inscripcionDao.findListadoGrupoByCodigoAlumno(alumno.getCodigo());


        }

        // Listados generales
        List<PaisComboProjection> paises = paisDao.findProjectedComboAllByTieneEstados();
        List<ControlMaestroMultipleComboProjection> generos = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> escolaridades = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_Escolaridad.NOMBRE);
        List<ControlMaestroMultipleComboProjection> parentescos = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_Parentesco.NOMBRE);
        List<ControlMaestroMultipleComboProjection> tiposPersona = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_RFC_TipoPersona.NOMBRE);
        List<ControlMaestroMultipleComboProjection> programasJOBS = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_ProgramaJOBS.NOMBRE);
        List<ControlMaestroMultipleComboProjection> centrosUniversitarios = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_CentrosUniversitarios.NOMBRE);
        List<ControlMaestroMultipleComboProjection> preparatorias = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Preparatorias.NOMBRE);
        List<ControlMaestroMultipleComboProjection> turnos = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Turnos.NOMBRE);
        List<ControlMaestroMultipleComboProjection> grados = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_ALU_Grados.NOMBRE);
        List<ControlMaestroMultipleComboProjection> mediosEnteradoProulex = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_MedioEnteradoProulex.NOMBRE);
        List<ControlMaestroMultipleComboProjection> razonesEleccionProulex = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_RazonEleccionProulex.NOMBRE);

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        boolean permiteEditar = false;
        if (alumno != null && sucursales != null) {
            for (SucursalComboProjection sucursal: sucursales) {
                if (!permiteEditar && alumno.getSucursal().getId().equals(sucursal.getId())) {
                    permiteEditar = true;
                }
            }
        }
        json.put("permiteEditar", permiteEditar);
        if(id != null && !permiteEditar)
            sucursales.add(sucursalDao.findSucursalComboProjectionById(alumno.getSucursal().getId()));


        // Preparar body de la petición
        json.put("alumno", alumno);
        json.put("paises", paises);
        json.put("estadosNacimiento", estadosNacimiento);
        json.put("estados", estados);
        json.put("municipiosNacimiento", municipiosNacimiento);
        json.put("municipios", municipios);
        json.put("generos", generos);
        json.put("escolaridades", escolaridades);
        json.put("parentescos", parentescos);
        json.put("tiposPersona", tiposPersona);
        json.put("sucursales", sucursales);
        json.put("programasJOBS", programasJOBS);
        json.put("centrosUniversitarios", centrosUniversitarios);
        json.put("preparatorias", preparatorias);
        json.put("carreras", carreras);
        json.put("rfcExtranjero", "XEXX010101000");
        json.put("turnos", turnos);
        json.put("grados", grados);
        json.put("puedeBorrar", inscripciones != null && inscripciones.size() > 0 ? false : true);
        json.put("mediosEnteradoProulex", mediosEnteradoProulex);
        json.put("razonesEleccionProulex", razonesEleccionProulex);
        json.put("listRegimenFiscal", regimenFiscalDao.findAllComboProjectedByActivoTrue());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/carreras/{centroUniversitarioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoCarreras(@PathVariable(required = false) Integer centroUniversitarioId, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<ControlMaestroMultipleComboProjection> carreras = controlMaestroMultipleDao.findAllByCmmReferenciaIdAndActivoIsTrueOrderByValor(centroUniversitarioId);

        return new JsonResponse(carreras, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/repetidos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlumnosRepetidos(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        String nombre = requestBody.get("nombre");
        String primerApellido = requestBody.get("primerApellido");
        String segundoApellido = requestBody.get("segundoApellido");
        String fechaNacimientoStr = requestBody.get("fechaNacimiento");
        Date fechaNacimiento = DateUtil.parse(fechaNacimientoStr);

        List<AlumnoEditarProjection> alumnosRepetidos = alumnoDao.findProjectedEditarAllByIdRegistroRepetido(nombre,primerApellido,segundoApellido,fechaNacimiento);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("alumnosRepetidos", alumnosRepetidos);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/medios-enterado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getMediosEnterado(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<ControlMaestroMultipleComboProjection> mediosEnteradoProulex = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_MedioEnteradoProulex.NOMBRE);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("mediosEnteradoProulex", mediosEnteradoProulex);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/razon-eleccion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getRazonesEleccion(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<ControlMaestroMultipleComboProjection> razonesEleccionProulex = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CE_RazonEleccionProulex.NOMBRE);

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("razonesEleccionProulex", razonesEleccionProulex);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/beca/{becaUDGHashId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoCarreras(@PathVariable String becaUDGHashId, ServletRequest req) throws SQLException {

        Integer becaUDGId = hashId.decode(becaUDGHashId);

        BecaUDG becaUDG = becaUDGDao.findById(becaUDGId);

        Alumno alumno = new Alumno();
        alumno.setCodigoUDG(becaUDG.getCodigoEmpleado());
        alumno.setNombre(becaUDG.getNombre());
        alumno.setPrimerApellido(becaUDG.getPrimerApellido());
        alumno.setSegundoApellido(becaUDG.getSegundoApellido());
        alumno.setAlumnoJOBS(false);
        alumno.setActivo(true);

        return new JsonResponse(alumno, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/existe/{codigoAlumno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse existeAlumno(@PathVariable String codigoAlumno, ServletRequest req) throws SQLException {

        Alumno alumno = alumnoDao.findByCodigo(codigoAlumno);

        if (alumno != null) {
            return new JsonResponse(true, null, JsonResponse.STATUS_OK);
        }else{
            return new JsonResponse(false, "No se encontró el alumno", JsonResponse.STATUS_OK);
        }

    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_REPORTE_EXCEL_ALUMNOS]";
        String[] alColumnas = new String[]{"Código alumno", "Nombre", "Apellidos", "Edad", "Correo electrónico", "Teléfono", "Sede", "Estatus"};

        excelController.downloadXlsx(response, "alumnos", query, alColumnas, null,"Alumnos");
    }

    @RequestMapping(value = {"/centros-universitarios/control/{control}","/preparatorias/control/{control}"}, method = RequestMethod.GET)
    public JsonResponse getCentroUniversitarioPreparatoria(@PathVariable String control) {

        List<ControlMaestroMultipleComboResponsablesProjection> controles = controlMaestroMultipleDatosAdicionalesDao.findProjectedComboResponsablesAllByControlAndActivoIsTrueOrderBySistemaDescValorAsc(control);

        List<UsuarioComboProjection> responsables = usuarioDao.findProjectedComboAllByEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);

        JSONObject listados = new JSONObject();
        listados.put("cmmReferencia",new ArrayList<>());
        listados.put("responsables",responsables);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("datos",controles);
        responseBody.put("listados",listados);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/centros-universitarios/save","/preparatorias/save"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarCentroUniversitarioPreparatoria(@RequestBody ControlMaestroMultipleDatosAdicionales cmm, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (cmm.getId() == null) {
            ControlMaestroMultiple cmmNuevo = new ControlMaestroMultiple();
            cmmNuevo.setControl(cmm.getControl());
            cmmNuevo.setValor(cmm.getValor());
            cmmNuevo.setReferencia(cmm.getReferencia());
            cmmNuevo.setCmmReferenciaId(cmm.getCmmReferenciaId());
            cmmNuevo.setCmmReferencia(cmm.getCmmReferencia());
            cmmNuevo.setOrden(cmm.getOrden());
            cmmNuevo.setImagen(cmm.getImagen());
            cmmNuevo.setImagenId(cmm.getImagenId());
            cmmNuevo.setSistema(cmm.isSistema());
            cmmNuevo.setCreadoPorId(idUsuario);
            cmmNuevo.setActivo(true);
            if(cmmNuevo.getReferencia() == null){
                cmmNuevo.setReferencia("");
            }
            cmmNuevo.setCmmReferenciaId(null);
            Integer id = controlMaestroMultipleService.spInsertCMM(cmmNuevo);
            ControlMaestroMultipleDatosAdicionales cmmActualizar = controlMaestroMultipleDatosAdicionalesDao.findById(id);
            cmmActualizar.setResponsables(cmm.getResponsables());
            controlMaestroMultipleDatosAdicionalesDao.save(cmmActualizar);
        } else {
            ControlMaestroMultipleDatosAdicionales objetoActual = controlMaestroMultipleDatosAdicionalesDao.findById(cmm.getId());
            if(objetoActual.isSistema()){
                return new JsonResponse(null, "El registro es de sistema, no se puede editar", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), cmm.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            if(cmm.getCmmReferencia() != null){
                cmm.setCmmReferenciaId(cmm.getCmmReferencia().getId());
            }else{
                cmm.setCmmReferenciaId(null);
            }
            cmm.setModificadoPorId(idUsuario);
            cmm.setActivo(objetoActual.isActivo());
            cmm = controlMaestroMultipleDatosAdicionalesDao.save(cmm);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = {"/centros-universitarios/delete/{idCMM}","/preparatorias/delete/{idCMM}"}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteByIdCentroUniversitarioPreparatoria(@PathVariable String idCMM) throws SQLException {

        ControlMaestroMultiple objetoActual = controlMaestroMultipleDao.findCMMById(hashId.decode(idCMM));
        if(objetoActual.isSistema()){
            return new JsonResponse(null, "El registro es de sistema, no se puede editar", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        int actualizado = controlMaestroMultipleDao.actualizarActivo(objetoActual.getId(), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
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

}
