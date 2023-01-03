package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import com.pixvs.main.models.projections.PADescuentoDetalle.PADescuentoDetalleEditarProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.Programa.ProgramaCalcularDiasProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
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
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/programas")
public class ProgramaController {

    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
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
    private PAModalidadDao paModalidadDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private DescuentoProgramasDao descuentoProgramasDao;
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
    private ProgramaIdiomaController programaIdiomaController;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<ProgramaListadoProjection> programas = programaDao.findProjectedListadoAllByOrderByCodigo();

        return new JsonResponse(programas, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Programa programa, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Boolean nuevoRegistro;
        if(programa.getId() == null){
            nuevoRegistro=true;
            programa.setActivo(true);
            programa.setCreadoPorId(idUsuario);
            ProgramaEditarProjection temp = programaDao.findProjectedEditarByCodigoAndActivoIsTrue(programa.getCodigo());
            if(temp != null){
                return new JsonResponse(null,"Ya existe un programa activo con dicho código",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
            //Crear cursos a articulos
            for (ProgramaIdioma curso: programa.getIdiomas()){

            }
        }
        else {
            //limpiarPrograma(programa.getId());
            programa.setModificadoPorId(idUsuario);
            nuevoRegistro=false;
            // Validar concurrencia de JOBS y PCP
            Programa programaBD = programaDao.findById(programa.getId());
            if(programaBD.getPcp() != null && programaBD.getPcp()){
                ControlMaestroMultipleComboProjection cmmSucursalJOBS = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSId.CMM_SUC_SucursalJOBSId);
                ControlMaestroMultipleComboProjection cmmSucursalJOBSSEMS = controlMaestroMultipleDao.findById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSSEMSId.CMM_SUC_SucursalJOBSSEMSId);
                for(ProgramaIdioma idioma : programa.getIdiomas()){
                    for(ProgramaIdiomaSucursal sucursal : idioma.getSucursales()){
                        if(sucursal.getSucursal().getId().intValue() == Integer.parseInt(cmmSucursalJOBS.getValor()) || sucursal.getSucursal().getId().intValue() == Integer.parseInt(cmmSucursalJOBSSEMS.getValor())){
                            return new JsonResponse(null,"No se permite crear programas PCP con sucursales JOBS o JOBS SEMS",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                        }
                    }
                }
            }
        }
        for(ProgramaIdioma idioma: programa.getIdiomas()){
            if(idioma.getActivo() == null){
                idioma.setActivo(true);
            }
            if(idioma.getId() != null){
                limpiarPrograma(idioma.getId());
                idioma.setModificadoPorId(idUsuario);
                idioma.setEsNuevo(true);
                List<Integer> ids = new ArrayList<>();

                if(programa.getActualizarGrupos()){
                    List<ProgramaGrupo> gruposActualizar = programaGrupoDao.findAllByProgramaIdiomaIdAndEstatusId(idioma.getId(),ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
                    for(ProgramaGrupo grupo: gruposActualizar){
                        grupo.setCalificacionMinima(idioma.getCalificacionMinima().intValue());
                        grupo.setFaltasPermitidas(idioma.getFaltasPermitidas());
                        programaGrupoDao.save(grupo);
                    }
                }

                for(ProgramaIdiomaModalidad modalidad: idioma.getModalidades()){
                    modalidad.setModalidadId(modalidad.getModalidad().getId());
                    ids.add(modalidad.getModalidad().getId());
                    List<ArticuloComboSimpleProjection> temps = articuloDao.findAllByProgramaIdiomaIdAndModalidadId(idioma.getId(),modalidad.getModalidad().getId());
                    if(temps != null && temps.size() == 0){
                        programaIdiomaController.crearArticuloCurso(programa,idioma,modalidad,null);
                    }
                    List<ControlMaestroMultipleComboProjection> tiposGrupos = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
                    for(ControlMaestroMultipleComboProjection tipoGrupo : tiposGrupos){
                        Articulo temp2 = articuloDao.findByCodigoArticulo(programa.getCodigo()+" "+ idioma.getIdioma().getReferencia()+" " + modalidad.getModalidad().getCodigo() + " " + tipoGrupo.getValor());
                        if(temp2 == null) {
                            programaIdiomaController.crearArticuloCurso(programa,idioma,modalidad,tipoGrupo);
                        }else{
                            temp2.setIva(BigDecimal.valueOf(idioma.getIva() / Double.valueOf(100)));
                            temp2.setIeps(BigDecimal.valueOf(idioma.getIeps() / Double.valueOf(100)));
                            temp2.setObjetoImpuestoId(idioma.getObjetoImpuesto() == null ? null : idioma.getObjetoImpuesto().getId());
                            articuloDao.save(temp2);
                        }
                    }
                }
                List<Articulo> articulosInactivar = articuloDao.findAllByProgramaIdiomaIdAndModalidadIdNotIn(idioma.getId(),ids);
                if(articulosInactivar != null && articulosInactivar.size() > 0){
                    for(Articulo articulo:articulosInactivar){
                        articulo.setActivo(false);
                        articuloDao.save(articulo);
                    }
                }

                // Nota: está comentado porque probablemente no se necesite, pero en caso de que se llegue a necesitar solo hay que descomentarlo
//                programaIdiomaController.limpiarListadoPrecios(idioma.getId(),idioma.getAgruparListadosPreciosPorTipoGrupo());
            }

            if(idioma.getIdioma() != null){
                idioma.setIdiomaId(idioma.getIdioma().getId());
            }
            if(idioma.getObjetoImpuesto() != null){
                idioma.setObjetoImpuestoId(idioma.getObjetoImpuesto().getId());
                //curso.setObjetoImpuesto(null);
            }
            if(idioma.getPlataforma() !=null){
                idioma.setPlataformaId(idioma.getPlataforma().getId());
                idioma.setPlataforma(null);
            }
            if(idioma.getUnidadMedida() != null){
                idioma.setUnidadMedidaId(idioma.getUnidadMedida().getId());
                idioma.setUnidadMedida(null);
            }
            for(ProgramaIdiomaLibroMaterial libro: idioma.getLibrosMateriales()){
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
            for(ProgramaIdiomaModalidad modalidad: idioma.getModalidades()){
                if(modalidad.getModalidad() != null){
                    modalidad.setModalidadId(modalidad.getModalidad().getId());
                    //modalidad.setModalidad(null);
                }
            }
            for(ProgramaIdiomaSucursal sucursal: idioma.getSucursales()){
                if(sucursal.getSucursal() != null){
                    sucursal.setSucursalId(sucursal.getSucursal().getId());
                    sucursal.setSucursal(null);
                }
            }

            for(ProgramaIdiomaCertificacion certificacion: idioma.getCertificaciones()){
                if(certificacion.getCertificacion()!=null){
                    certificacion.setCertificacionId(certificacion.getCertificacion().getId());
                    certificacion.setCertificacion(null);
                }
            }

            if(idioma.getId() != null) {
                List<Articulo> articulos = articuloDao.findAllByProgramaIdiomaId(idioma.getId());
                if(articulos != null){
                    for(Articulo articulo: articulos) {
                        articulo.setCodigoAlterno(idioma.getClave());
                        articulo.setClaveProductoSAT(idioma.getClave());
                        //articulo.setIva(idioma.getIva() == null ? BigDecimal.ZERO : BigDecimal.valueOf(idioma.getIva()));
                        articulo.setIeps(idioma.getIeps() == null ? BigDecimal.ZERO : BigDecimal.valueOf(idioma.getIeps()/100));
                        articulo.setIvaExento(idioma.getIvaExento());
                        articulo.setObjetoImpuestoId(idioma.getObjetoImpuesto() == null ? null : idioma.getObjetoImpuesto().getId());
                        articuloDao.save(articulo);
                    }
                }
            }

        }

        List<ProgramaIdioma> idiomasTemp = programa.getIdiomas();
        programa = programaDao.save(programa);

        for(Integer i=0;i<programa.getIdiomas().size();i++){
            if(idiomasTemp.get(i).getId() == null){
                for (ProgramaIdiomaModalidad modalidad : programa.getIdiomas().get(i).getModalidades()) {
                    programaIdiomaController.crearArticuloCurso(programa,programa.getIdiomas().get(i),modalidad,null);

                    List<ControlMaestroMultipleComboProjection> tiposGrupos = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGRU_TipoGrupo.NOMBRE);
                    for(ControlMaestroMultipleComboProjection tipoGrupo : tiposGrupos){
                        programaIdiomaController.crearArticuloCurso(programa,programa.getIdiomas().get(i),modalidad,tipoGrupo);
                    }
                }
            }
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idEmpleado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idEmpleado) throws SQLException {

        ProgramaEditarProjection programa = programaDao.findProjectedEditarById(idEmpleado);

        return new JsonResponse(programa, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cursos/{programaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCursos(@PathVariable Integer programaId) throws SQLException {

        List<ProgramaIdiomaComboProjection> cursos = programaIdiomaDao.findAllComboByProgramaId(programaId);

        return new JsonResponse(cursos, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idPrograma}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idPrograma) throws SQLException {

        int actualizado = programaDao.actualizarActivo(hashId.decode(idPrograma), false);
        List<ProgramaIdioma> idiomas = programaIdiomaDao.findAllByProgramaId(hashId.decode(idPrograma));
        for(ProgramaIdioma idioma: idiomas){
            programaIdiomaDao.actualizarActivo(idioma.getId(),false);
        }

        List<PADescuentoDetalleEditarProjection> descuentos = descuentoProgramasDao.findDescuentoDetallessByProgramaId(hashId.decode(idPrograma));
        for(PADescuentoDetalleEditarProjection detalle: descuentos){
            descuentoProgramasDao.deleteById(detalle.getId());
        }


        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramasById(@PathVariable(required = false) Integer id) throws SQLException {

        ProgramaEditarProjection programa = null;
        if (id != null) {
            programa = programaDao.findProjectedEditarById(id);
        }

        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findComboAllByActivoTrueOrderByNombre();
        List<UnidadMedidaComboProjection> unidadesMedidas = unidadMedidaDao.findProjectedComboAllByActivoTrue();
        List<ArticuloComboSimpleProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndFamiliaIdAndTipoArticuloIdNot(17,4);
        List<ArticuloComboSimpleProjection> certificaciones = articuloDao.findCertificaciones();
        List<ArticuloComboSimpleProjection> examenes = articuloDao.findExamenes();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        //List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> plataformas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_Plataforma.NOMBRE);
        List<ControlMaestroMultipleComboProjection> carreras = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ALU_Carreras.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("programa", programa);
        json.put("idiomas", idiomas);
        json.put("modalidades", modalidades);
        json.put("unidadesMedidas",unidadesMedidas);
        json.put("articulos", articulos);
        json.put("sucursales", sucursales);
        json.put("plataformas",plataformas);
        json.put("certificaciones", certificaciones);
        json.put("examenes",examenes);
        json.put("carreras",carreras);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_LISTADO_PROGRAMAS_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Numero de idiomas"};

        excelController.downloadXlsx(response, "programas", query, alColumnas, null,"Programas");
    }

    @RequestMapping(value = "/calculos/idioma/{idiomaId}/modalidad/{modalidadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramasCalcularDias(@PathVariable Integer idiomaId, @PathVariable Integer modalidadId) throws SQLException {

        List<ProgramaCalcularDiasProjection> programas = programaDao.findProjectedCalcularDiasAllByIdiomaIdAndModalidadId(idiomaId,modalidadId);

        return new JsonResponse(programas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/buscarPrograma/{idiomaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramasByIdioma(@PathVariable Integer idiomaId) throws SQLException {

        List<ProgramaComboProjection> programas = programaDao.findProgramasByIdioma(idiomaId);

        return new JsonResponse(programas, null, JsonResponse.STATUS_OK);
    }

    public void limpiarPrograma(Integer idProgramaIdioma){
        programaIdiomaSucursalDao.deleteByProgramaIdiomaId(idProgramaIdioma);
        programaIdiomaModalidadDao.deleteByProgramaIdiomaId(idProgramaIdioma);
    }

//    private void crearArticuloCurso(Programa programa, ProgramaIdioma curso, ProgramaIdiomaModalidad modalidad, ControlMaestroMultipleComboProjection tipoGrupo){
//        Articulo cursoArticulo = new Articulo();
//        String codigoGrupo = programa.getCodigo() + " " + curso.getIdioma().getReferencia() + " " + modalidad.getModalidad().getCodigo();
//        String nombreGrupo = programa.getCodigo() + " " + curso.getIdioma().getValor() + " " + modalidad.getModalidad().getNombre();
//        if(tipoGrupo != null){
//            codigoGrupo += " " + tipoGrupo.getValor();
//            nombreGrupo += " " + tipoGrupo.getValor();
//        }
//        cursoArticulo.setCodigoArticulo(codigoGrupo);
//        cursoArticulo.setNombreArticulo(nombreGrupo);
//        cursoArticulo.setDescripcion(curso.getDescripcion());
//        cursoArticulo.setClaveProductoSAT(curso.getClave());
//        cursoArticulo.setDescripcionCorta("");
//        cursoArticulo.setPermitirCambioAlmacen(false);
//        cursoArticulo.setCodigoAlterno(curso.getClave());
//        cursoArticulo.setPlaneacionTemporadas(false);
//        cursoArticulo.setFamiliaId(9);
//        cursoArticulo.setCategoriaId(37);
//        cursoArticulo.setTipoArticuloId(5);
//        cursoArticulo.setUnidadMedidaInventarioId(1);
//        cursoArticulo.setTipoCostoId(2000041);
//        cursoArticulo.setIva(curso.getIva() == null ? null : BigDecimal.valueOf(curso.getIva() / Double.valueOf(100)));
//        cursoArticulo.setIeps(curso.getIeps() == null ? null : BigDecimal.valueOf(curso.getIeps() / Double.valueOf(100)));
//        cursoArticulo.setCostoUltimo(BigDecimal.ZERO);
//        cursoArticulo.setCostoPromedio(BigDecimal.ZERO);
//        cursoArticulo.setCostoEstandar(BigDecimal.ZERO);
//        cursoArticulo.setActivo(true);
//        cursoArticulo.setInventariable(true);
//        cursoArticulo.setArticuloParaVenta(true);
//        cursoArticulo.setProgramaIdiomaId(curso.getId());
//        cursoArticulo.setModalidadId(modalidad.getModalidadId());
//        if(tipoGrupo != null){
//            cursoArticulo.setTipoGrupoId(tipoGrupo.getId());
//        }
//        cursoArticulo.setObjetoImpuestoId(curso.getObjetoImpuesto() == null ? null : curso.getObjetoImpuesto().getId());
//        articuloDao.save(cursoArticulo);
//    }

}

