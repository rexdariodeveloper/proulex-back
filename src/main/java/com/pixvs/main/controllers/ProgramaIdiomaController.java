package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSucursalesProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial.ProgramaIdiomaLibroMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialCursoProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.handler.exceptions.UsuarioException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/cursos")
public class ProgramaIdiomaController {

    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private ProgramaIdiomaModalidadDao programaIdiomaModalidadDao;
    @Autowired
    private ProgramaIdiomaLibrosMaterialesDao programaIdiomaLibrosMaterialesDao;
    @Autowired
    private ProgramaIdiomaCertificacionDao programaIdiomaCertificacionDao;
    @Autowired
    private ProgramaIdiomaSucursalDao programaIdiomaSucursalDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ControlMaestroMultipleMainDao controlMaestroMultipleMainDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private TabuladorDao tabuladorDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private ProgramaIdiomaExamenModalidadDao programaIdiomaExamenModalidadDao;
    @Autowired
    private ProgramaIdiomaExamenUnidadDao programaIdiomaExamenUnidadDao;
    @Autowired
    private PAActividadEvaluacionDao paActividadEvaluacionDao;
    @Autowired
    private ProgramaIdiomaExamenDetalleDao programaIdiomaExamenDetalleDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ListadoPrecioDao listadoPrecioDao;
    @Autowired
    private ListadoPrecioDetalleDao listadoPrecioDetalleDao;
    @Autowired
    private ListadoPrecioDetalleCursoDao listadoPrecioDetalleCursoDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<ProgramaIdiomaListadoProjection> cursos = programaIdiomaDao.findListadoOrderByCodigo();

        return new JsonResponse(cursos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/existente-idioma/{idPrograma}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(@PathVariable Integer idPrograma) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        ProgramaEditarProjection programa = programaDao.findProjectedEditarById(idPrograma);
        for(ProgramaIdiomaEditarProjection idioma: programa.getIdiomas()){
            ids.add(idioma.getIdioma().getId());
        }
        /*List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findCMMNotIn("CMM_ART_Idioma",ids);
        HashMap<String, Object> json = new HashMap<>();
        json.put("idiomas",idiomas);*/

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaIdioma curso, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        DecimalFormat df = new DecimalFormat("####0.00");
        Boolean nuevoRegistro;
        if(curso.getId() == null){
            List<ProgramaIdiomaEditarProjection> temps;
            if (curso.getEsAcademy())
                temps = programaIdiomaDao.findProjectedEditarAllByProgramaIdAndTipoWorkshopIdAndActivoIsTrue(curso.getPrograma().getId(), curso.getTipoWorkshop().getId());
            else
                temps = programaIdiomaDao.findProjectedEditarAllByProgramaIdAndIdiomaIdAndActivoIsTrue(curso.getPrograma().getId(),curso.getIdioma().getId());
            if(temps != null && temps.size() >0){
                String message;
                if(curso.getEsAcademy())
                    message = "Ya existe un curso ligado al programa "+curso.getPrograma().getNombre()+" y al workshop "+curso.getTipoWorkshop().getValor();
                else
                    message = "Ya existe un curso ligado al programa "+curso.getPrograma().getNombre()+" y al idioma "+curso.getIdioma().getValor();
                return new JsonResponse(null, message, JsonResponse.STATUS_ERROR);
            }
            curso.setActivo(true);
            nuevoRegistro=true;
        }
        else {
            // Validar concurrencia de JOBS y PCP
            Programa programaBD = programaDao.findById(curso.getPrograma().getId());
            if(programaBD.getPcp() != null && programaBD.getPcp()){
                ControlMaestroMultipleComboProjection cmmSucursalJOBS = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSId.CMM_SUC_SucursalJOBSId);
                ControlMaestroMultipleComboProjection cmmSucursalJOBSSEMS = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSSEMSId.CMM_SUC_SucursalJOBSSEMSId);
                for(ProgramaIdiomaSucursal sucursal : curso.getSucursales()){
                    if(sucursal.getSucursal().getId().intValue() == Integer.parseInt(cmmSucursalJOBS.getValor()) || sucursal.getSucursal().getId().intValue() == Integer.parseInt(cmmSucursalJOBSSEMS.getValor())){
                        return new JsonResponse(null,"No se permite crear programas PCP con sucursales JOBS o JOBS SEMS",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                }
            }
            limpiarPrograma(curso.getId());
            curso.setModificadoPorId(idUsuario);
            nuevoRegistro=false;
            List<Integer> ids = new ArrayList<>();

            if(curso.getActualizarGrupos() != null && curso.getActualizarGrupos()){
                List<ProgramaGrupo> gruposActualizar = programaGrupoDao.findAllByProgramaIdiomaIdAndEstatusId(curso.getId(),ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
                for(ProgramaGrupo grupo: gruposActualizar){
                    grupo.setCalificacionMinima(curso.getCalificacionMinima().intValue());
                    grupo.setFaltasPermitidas(curso.getFaltasPermitidas());
                    programaGrupoDao.save(grupo);
                }
            }
            List<Articulo> articulosInactivar = articuloDao.findAllByProgramaIdiomaIdAndModalidadIdNotIn(curso.getId(),ids);
            if(articulosInactivar != null && articulosInactivar.size() > 0){
                for(Articulo articulo:articulosInactivar){
                    articulo.setActivo(false);
                    articuloDao.save(articulo);
                }
            }
        }
        if(curso.getIdioma() != null){
            curso.setIdiomaId(curso.getIdioma().getId());
        }
        if(curso.getObjetoImpuesto() != null){
            curso.setObjetoImpuestoId(curso.getObjetoImpuesto().getId());
        }
        if(curso.getUnidadMedida() != null){
            curso.setUnidadMedidaId(curso.getUnidadMedida().getId());
            curso.setUnidadMedida(null);
        }
        if(curso.getPlataforma()!=null){
            curso.setPlataformaId(curso.getPlataforma().getId());
            curso.setPlataforma(null);
        }
        for(ProgramaIdiomaLibroMaterial libro: curso.getLibrosMateriales()){
            if(libro.getArticulo() != null){
                libro.setArticuloId(libro.getArticulo().getId());
                libro.setArticulo(null);
            }
            if(libro.getReglas()!= null && libro.getReglas().size() > 0){
                for(ProgramaIdiomaLibroMaterialRegla regla: libro.getReglas()){
                    if(regla.getCarrera()!=null){
                        regla.setCarreraId(regla.getCarrera().getId());
                        regla.setCarrera(null);
                    }
                }
            }
        }
        for(ProgramaIdiomaModalidad modalidad: curso.getModalidades()){
            if(modalidad.getModalidad() != null){
                modalidad.setModalidadId(modalidad.getModalidad().getId());
            }
        }
        for(ProgramaIdiomaSucursal sucursal: curso.getSucursales()){
            if(sucursal.getSucursal() != null){
                sucursal.setSucursalId(sucursal.getSucursal().getId());
                sucursal.setSucursal(null);
            }
        }

        for(ProgramaIdiomaCertificacion certificacion: curso.getCertificaciones()){
            if(certificacion.getCertificacion()!=null){
                certificacion.setCertificacionId(certificacion.getCertificacion().getId());
                certificacion.setCertificacion(null);
            }
        }

        if(curso.getId() != null) {
            List<Articulo> articulos = articuloDao.findAllByProgramaIdiomaId(curso.getId());
            if(articulos != null){
                for(Articulo articulo: articulos) {
                    BigDecimal oneHoundred = new BigDecimal(100);
                    articulo.setCodigoAlterno(curso.getClave());
                    articulo.setClaveProductoSAT(curso.getClave());
                    articulo.setUnidadMedidaInventarioId(curso.getUnidadMedidaId());
                    articulo.setIva(curso.getIva() == null ? BigDecimal.ZERO : new BigDecimal(curso.getIva()).divide(oneHoundred));
                    articulo.setIeps(curso.getIeps() == null ? BigDecimal.ZERO : new BigDecimal(curso.getIeps()).divide(oneHoundred));
                    articulo.setIvaExento(curso.getIvaExento());
                    articulo.setObjetoImpuestoId(curso.getObjetoImpuesto() == null ? null : curso.getObjetoImpuesto().getId());
                    articuloDao.save(articulo);
                }
            }
        }

        Programa programa = null;
        if(curso.getId() != null) {
            for (ProgramaIdiomaNivel nivel : curso.getNiveles()) {
                for (ProgramaIdiomaExamen examen : nivel.getExamenes()) {
                    for (ProgramaIdiomaExamenDetalle detalle : examen.getDetalles()) {
                        if (detalle.getId() != null) {
                            limpiarExamenDetalle(detalle.getId());
                        }
                        if (detalle.getActividadEvaluacion() != null) {
                            detalle.setActividadEvaluacionId(detalle.getActividadEvaluacion().getId());
                            detalle.setActividadEvaluacion(null);
                        }
                        if (detalle.getTest() != null) {
                            detalle.setTestId(detalle.getTest().getId());
                            detalle.setTest(null);
                        }
                        for (ProgramaIdiomaExamenModalidad modalidad : detalle.getModalidades()) {
                            if (modalidad.getModalidad() != null) {
                                modalidad.setModalidadlId(modalidad.getModalidad().getId());
                                modalidad.setModalidad(null);
                            }
                        }
                        for (ProgramaIdiomaExamenUnidad unidad : detalle.getUnidades()) {
                            if (unidad.getLibroMaterial() != null) {
                                unidad.setLibroMaterialId(unidad.getLibroMaterial().getId());
                                unidad.setLibroMaterial(null);
                            }
                        }
                    }
                }
            }

            curso = programaIdiomaDao.save(curso);
        }else {
            List<ProgramaIdiomaNivel> nivelesTemp = curso.getNiveles();
            curso.setNiveles(null);
            programa = programaDao.findById(curso.getPrograma().getId());
            if (programa.getIdiomas() == null && curso.getId() == null) {
                programa.setIdiomas(new ArrayList<>());
                programa.getIdiomas().add(curso);
                programaDao.save(programa);
            } else if (programa.getIdiomas() != null && curso.getId() == null) {
                programa.getIdiomas().add(curso);
                programa = programaDao.save(programa);
            }
            for (ProgramaIdiomaNivel nivel : nivelesTemp) {
                for (ProgramaIdiomaExamen examen : nivel.getExamenes()) {
                    for (ProgramaIdiomaExamenDetalle detalle : examen.getDetalles()) {
                        if (detalle.getId() != null) {
                            limpiarExamenDetalle(detalle.getId());
                        }
                        if (detalle.getActividadEvaluacion() != null) {
                            detalle.setActividadEvaluacionId(detalle.getActividadEvaluacion().getId());
                            detalle.setActividadEvaluacion(null);
                        }
                        if (detalle.getTest() != null) {
                            detalle.setTestId(detalle.getTest().getId());
                            detalle.setTest(null);
                        }
                        for (ProgramaIdiomaExamenModalidad modalidad : detalle.getModalidades()) {
                            if (modalidad.getModalidad() != null) {
                                modalidad.setModalidadlId(modalidad.getModalidad().getId());
                                modalidad.setModalidad(null);
                            }
                        }
                        for (ProgramaIdiomaExamenUnidad unidad : detalle.getUnidades()) {
                            if (unidad.getLibroMaterial() != null) {
                                unidad.setLibroMaterialId(findIdLibroByNivelAndArticulo(unidad.getLibroMaterial().getNivel(),unidad.getLibroMaterial().getArticulo().getId(),programa.getIdiomas().get(programa.getIdiomas().size() -1).getLibrosMateriales()));
                                unidad.setLibroMaterial(null);
                                System.out.println();
                            }
                        }
                    }
                }
            }
            ProgramaIdioma cursoTemp = programa.getIdiomas().get(programa.getIdiomas().size() -1);
            cursoTemp.setNiveles(nivelesTemp);
            curso = programaIdiomaDao.save(cursoTemp);
        }

        if(programa == null)
            programa = programaDao.findById(curso.getPrograma().getId());

        for(ProgramaIdiomaModalidad modalidad: curso.getModalidades()) {
            Articulo temp = articuloDao.findByCodigoArticulo(programa.getCodigo()+" "+ curso.getIdioma().getReferencia()+" " + modalidad.getModalidad().getCodigo());
            if(temp == null) {
                crearArticuloCurso(programa,curso,modalidad,null);
            }else{
                temp.setIva(BigDecimal.valueOf(curso.getIva() / Double.valueOf(100)));
                temp.setIeps(BigDecimal.valueOf(curso.getIeps() / Double.valueOf(100)));
                temp.setObjetoImpuestoId(curso.getObjetoImpuesto() == null ? null : curso.getObjetoImpuesto().getId());
                articuloDao.save(temp);
            }
            List<ControlMaestroMultipleComboProjection> tiposGrupos = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
            for(ControlMaestroMultipleComboProjection tipoGrupo : tiposGrupos){
                Articulo temp2 = articuloDao.findByCodigoArticulo(programa.getCodigo()+" "+ curso.getIdioma().getReferencia()+" " + modalidad.getModalidad().getCodigo() + " " + tipoGrupo.getValor());
                if(temp2 == null) {
                    crearArticuloCurso(programa,curso,modalidad,tipoGrupo);
                }else{
                    temp2.setIva(BigDecimal.valueOf(curso.getIva() / Double.valueOf(100)));
                    temp2.setIeps(BigDecimal.valueOf(curso.getIeps() / Double.valueOf(100)));
                    temp2.setObjetoImpuestoId(curso.getObjetoImpuesto() == null ? null : curso.getObjetoImpuesto().getId());
                    articuloDao.save(temp2);
                }
            }
        }

        // Nota: está comentado porque probablemente no se necesite, pero en caso de que se llegue a necesitar solo hay que descomentarlo
//        if(!nuevoRegistro){
//            limpiarListadoPrecios(curso.getId(),curso.getAgruparListadosPreciosPorTipoGrupo());
//        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idCurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idCurso) throws SQLException {

        ProgramaIdiomaEditarProjection programa = programaIdiomaDao.findProjectedEditarById(idCurso);

        return new JsonResponse(programa, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getModalidades/{idCurso}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getModalidadesByIdCurso(@PathVariable Integer idCurso) throws SQLException {

        ProgramaIdiomaEditarProjection programa = programaIdiomaDao.findProjectedEditarById(idCurso);
        List<PAModalidadComboProjection> modalidades = new ArrayList<>();
        for(ProgramaIdiomaModalidadEditarProjection modalidad: programa.getModalidades()){
            modalidades.add(modalidad.getModalidad());
        }
        Set<PAModalidadComboProjection> set = new HashSet<>(modalidades);
        modalidades.clear();
        modalidades.addAll(set);
        return new JsonResponse(modalidades, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getProgramacionAcademica/{idPrograma}/{idModalidad}/{idIdioma}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramacionByIdCurso(@PathVariable Integer idPrograma, @PathVariable Integer idModalidad, @PathVariable Integer idIdioma) throws SQLException {

        List<ProgramacionAcademicaComercialComboProjection>  programacion = new ArrayList<>();
        programacion = programacionAcademicaComercialDao.findProjectedComboAllByProgramaIdAndModalidad(idPrograma, idModalidad,idIdioma);
        return new JsonResponse(programacion, null, JsonResponse.STATUS_OK);
    }

    /*@RequestMapping(value = "/getTest/{idPrograma}/{idModalidad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTestByIdCurso(@PathVariable Integer idPrograma, @PathVariable Integer idModalidad) throws SQLException {
        List<ProgramaIdiomaExamenDetalleListadoProjection> tests = programaIdiomaExamenDetalleDao.getTestByProgramaId(idPrograma,idModalidad);
        return new JsonResponse(tests, null, JsonResponse.STATUS_OK);
    }*/

    @RequestMapping(value = "/getMateriales/{idPrograma}/{nivel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getMaterialesByIdProgramaAndNivel(@PathVariable Integer idPrograma, @PathVariable Integer nivel) throws SQLException {

        List<ProgramaIdiomaLibroMaterialEditarProjection> materiales = programaIdiomaLibrosMaterialesDao.findAllByProgramaIdiomaId(idPrograma);
        List<ArticuloComboProjection> articulos = new ArrayList<>();
        Integer selected = null;
        for(ProgramaIdiomaLibroMaterialEditarProjection material: materiales){
            articulos.add(articuloDao.findProjectedComboById(material.getArticulo().getId()));
            if(material.getNivel() == nivel){
                selected = material.getArticulo().getId();
            }
        }
        HashMap<String, Object> json = new HashMap<>();
        json.put("articulos",articulos);
        json.put("selectedArticulo",selected == null ? null : articuloDao.findProjectedComboById(selected));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete/{idCurso}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idCurso) throws SQLException {
        List<Articulo> articuloCurso = articuloDao.findByProgramaIdiomaIdAndActivoIsTrue(hashId.decode(idCurso));
        for(Articulo articulo : articuloCurso) {
            articulo.setActivo(false);
            articuloDao.save(articulo);
        }
        int actualizado = programaIdiomaDao.actualizarActivo(hashId.decode(idCurso), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getSucursales/{idSucursal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCursosBySucursal(@PathVariable Integer idSucursal) throws SQLException {

        List<ProgramaIdiomaComboSucursalesProjection> programas = programaIdiomaDao.findCursosBySucursales(idSucursal);

        return new JsonResponse(programas, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramasById(@PathVariable(required = false) Integer id) throws SQLException {

        ProgramaIdiomaEditarProjection curso = null;
        if (id != null) {
            curso = programaIdiomaDao.findProjectedEditarById(id);
        }

        List<ProgramaComboProjection> programas =  programaDao.findComboListadoAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<ControlMaestroMultipleComboProjection> plataformas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_Plataforma.NOMBRE);
        List<PAModalidadComboProjection> modalidades = paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre();
        List<UnidadMedidaComboProjection> unidadesMedidas = unidadMedidaDao.findProjectedComboAllByActivoTrue();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        List<PAActividadEvaluacionComboProjection> test = paActividadEvaluacionDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> testFormat = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_TestFormat.NOMBRE);
        List<ArticuloComboSimpleProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndFamiliaIdAndTipoArticuloIdNot(17,4);
        List<ArticuloComboSimpleProjection> certificaciones = articuloDao.findCertificaciones();
        List<ArticuloComboSimpleProjection> examenes = articuloDao.findExamenes();
        List<ProgramacionAcademicaComercialCursoProjection> programacionAcademicaComercial = programacionAcademicaComercialDao.findProjectedCursoAllBy();
        List<ControlMaestroMultipleComboProjection> carreras = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE);
        List<TabuladorComboProjection> tabuladores = tabuladorDao.findComboAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> tipos = controlMaestroMultipleDao.findAllByControl("CMM_WKS_TipoWorkshop");

        HashMap<String, Object> json = new HashMap<>();

        json.put("curso", curso);
        json.put("objetosImpuestoSAT", controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_ObjetoImp"));
        json.put("idiomas", idiomas);
        json.put("modalidades", modalidades);
        json.put("unidadesMedidas",unidadesMedidas);
        json.put("programas", programas);
        json.put("plataformas", plataformas);
        json.put("sucursales", sucursales);
        json.put("test",test);
        json.put("testFormat",testFormat);
        json.put("articulos",articulos);
        json.put("certificaciones",certificaciones);
        json.put("examenes",examenes);
        json.put("programacionAcademicaComercial",programacionAcademicaComercial);
        json.put("carreras",carreras);
        json.put("tabuladores",tabuladores);
        json.put("tipos", tipos);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_LISTADO_PROGRAMAS_IDIOMAS_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Horas totales","Número de niveles","Sucursales totales","Modalidades totales","Estatus"};

        excelController.downloadXlsx(response, "cursos", query, alColumnas, null,"Cursos");
    }

    public void limpiarExamenDetalle(Integer idDetalle){
        programaIdiomaExamenUnidadDao.deleteByExamenDetalleId(idDetalle);
        programaIdiomaExamenModalidadDao.deleteByExamenDetalleId(idDetalle);
    }

    public void limpiarPrograma(Integer idProgramaIdioma){
        programaIdiomaSucursalDao.deleteByProgramaIdiomaId(idProgramaIdioma);
        programaIdiomaModalidadDao.deleteByProgramaIdiomaId(idProgramaIdioma);
    }

    public Integer findIdLibroByNivelAndArticulo(Integer nivel, Integer articulo, List<ProgramaIdiomaLibroMaterial> libros){
        for(ProgramaIdiomaLibroMaterial libro: libros){
            if(libro.getNivel().equals(nivel) && libro.getArticuloId().equals(articulo)){
                return libro.getId();
            }
        }
        return null;
    }

    public void crearArticuloCurso(Programa programa, ProgramaIdioma curso, ProgramaIdiomaModalidad modalidad, ControlMaestroMultipleComboProjection tipoGrupo){
        Articulo cursoArticulo = new Articulo();
        String codigoGrupo = programa.getCodigo() + " " + curso.getIdioma().getReferencia() + " " + modalidad.getModalidad().getCodigo();
        String nombreGrupo = programa.getCodigo() + " " + curso.getIdioma().getValor() + " " + modalidad.getModalidad().getNombre();
        if(tipoGrupo != null){
            codigoGrupo += " " + tipoGrupo.getValor();
            nombreGrupo += " " + tipoGrupo.getValor();
        }
        cursoArticulo.setCodigoArticulo(codigoGrupo);
        cursoArticulo.setNombreArticulo(nombreGrupo);
        cursoArticulo.setDescripcion(curso.getDescripcion());
        cursoArticulo.setClaveProductoSAT(curso.getClave());
        cursoArticulo.setDescripcionCorta("");
        cursoArticulo.setPermitirCambioAlmacen(false);
        cursoArticulo.setCodigoAlterno(curso.getClave());
        cursoArticulo.setPlaneacionTemporadas(false);
        cursoArticulo.setFamiliaId(9);
        cursoArticulo.setCategoriaId(37);
        cursoArticulo.setTipoArticuloId(5);
        cursoArticulo.setUnidadMedidaInventarioId(1);
        cursoArticulo.setTipoCostoId(2000041);
        cursoArticulo.setIva(curso.getIva() == null ? null : BigDecimal.valueOf(curso.getIva() / Double.valueOf(100)));
        cursoArticulo.setIeps(curso.getIeps() == null ? null : BigDecimal.valueOf(curso.getIeps() / Double.valueOf(100)));
        cursoArticulo.setCostoUltimo(BigDecimal.ZERO);
        cursoArticulo.setCostoPromedio(BigDecimal.ZERO);
        cursoArticulo.setCostoEstandar(BigDecimal.ZERO);
        cursoArticulo.setActivo(true);
        cursoArticulo.setInventariable(true);
        cursoArticulo.setArticuloParaVenta(true);
        cursoArticulo.setProgramaIdiomaId(curso.getId());
        cursoArticulo.setModalidadId(modalidad.getModalidadId());
        if(tipoGrupo != null){
            cursoArticulo.setTipoGrupoId(tipoGrupo.getId());
        }
        cursoArticulo.setObjetoImpuestoId(curso.getObjetoImpuesto() == null ? null : curso.getObjetoImpuesto().getId());
        cursoArticulo.setPedirCantidadPV(false);
        articuloDao.save(cursoArticulo);
    }

    public void limpiarListadoPrecios(int cursoId, boolean agruparListadosPreciosPorTipoGrupo){
        List<ListadoPrecioComboProjection> listadosPrecios = listadoPrecioDao.findAllByActivoIsTrueOrderByCodigo();

        List<Integer> articulosBorrarPrecioIds;
        if(agruparListadosPreciosPorTipoGrupo){
            articulosBorrarPrecioIds = articuloDao.getIdAllByCursoSinTipoGrupo();
        }else{
            articulosBorrarPrecioIds = articuloDao.getIdAllByCursoConTipoGrupo();
        }

        if(articulosBorrarPrecioIds.size() > 0){
            for(ListadoPrecioComboProjection listadoPrecio : listadosPrecios){
                List<Integer> listadoPrecioDetallesIdsBorrar = listadoPrecioDetalleDao.getIdAllByListadoPrecioIdAndArticuloIdIn(listadoPrecio.getId(),articulosBorrarPrecioIds);
                listadoPrecioDetalleCursoDao.deleteAllByListadoPrecioDetalleIdIn(listadoPrecioDetallesIdsBorrar);
                listadoPrecioDetalleDao.deleteAllByIdIn(listadoPrecioDetallesIdsBorrar);
            }
        }
    }

    @RequestMapping(value = "/grupos-afectados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGruposAfectados(@RequestBody JSONArray array, ServletRequest req){
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/afectar-grupos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResponse afectarGrupos(@RequestBody JSONObject json, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuarioLogin = usuarioDao.findById(usuarioId);
        String pass = (String) json.get("password");
        if (!passwordEncoder.matches(pass, usuarioLogin.getContrasenia()))
            throw new UsuarioException("Contraseña incorrecta.");
        String grupos = (String) json.get("grupos");

        return new JsonResponse(null, "Grupos afectados.", JsonResponse.STATUS_OK);
    }

}

