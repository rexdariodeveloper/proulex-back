package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.ArticuloCategoria;
import com.pixvs.main.models.ArticuloComponente;
import com.pixvs.main.models.ArticuloFamilia;
import com.pixvs.main.models.mapeos.ArticulosFamilias;
import com.pixvs.main.models.mapeos.ArticulosSubtipos;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.*;
import com.pixvs.main.models.projections.Articulo.*;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoComboProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoComboProjection;
import com.pixvs.main.models.projections.PADescuentoArticulo.PADescuentoArticuloEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@RestController
@RequestMapping("/api/v1/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private ArticuloCategoriaDao articuloCategoriaDao;
    @Autowired
    private ArticuloSubcategoriaDao articuloSubcategoriaDao;
    @Autowired
    private ControlMaestroMultipleDao cmmDao;
    @Autowired
    private ProgramaDao programaDao;

    @Autowired
    private ArticuloTipoDao articuloTipoDao;

    @Autowired
    private ArticuloSubtipoDao articuloSubtipoDao;

    @Autowired
    private DescuentoArticulosDao descuentoArticulosDao;

    @Autowired
    private UnidadMedidaDao unidadMedidaDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArticulos() throws SQLException {

        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloNoSistema();

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", articulos);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id) throws SQLException {

        List<ArticuloFamiliaComboProjection> familias = articuloFamiliaDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> clasificaciones1 = cmmDao.findAllByControl("CMM_ART_Clasificacion1");
        List<ControlMaestroMultipleComboProjection> clasificaciones2 = cmmDao.findAllByControl("CMM_ART_Clasificacion2");
        List<ControlMaestroMultipleComboProjection> clasificaciones3 = cmmDao.findAllByControl("CMM_ART_Clasificacion3");
        List<ControlMaestroMultipleComboProjection> clasificaciones4 = cmmDao.findAllByControl("CMM_ART_Clasificacion4");
        List<ControlMaestroMultipleComboProjection> clasificaciones5 = cmmDao.findAllByControl("CMM_ART_Clasificacion5");
        List<ControlMaestroMultipleComboProjection> clasificaciones6 = cmmDao.findAllByControl("CMM_ART_Clasificacion6");
        List<ArticuloTipoComboProjection> articulosTipos = articuloTipoDao.findProjectedComboAllByActivoTrueAndTipoArticuloIdNot(CMM_ARTT_TipoArticulo.SISTEMA);
        List<ArticuloSubtipoComboProjection> articulosSubtipos = articuloSubtipoDao.findProjectedComboAllByActivoTrue();
        List<UnidadMedidaComboProjection> unidadesMedida = unidadMedidaDao.findProjectedComboAllByActivoTrue();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        List<ArticuloCategoriaComboProjection> categorias = articuloCategoriaDao.findProjectedComboAllByActivoTrue();
        List<ArticuloSubcategoriaComboProjection> subcategorias = articuloSubcategoriaDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> tiposCosto = cmmDao.findAllByControl(CMM_ART_TipoCosto.NOMBRE);
        List<ArticuloComboProjection> componentesListado = articuloDao.findProjectedComboAllByActivoTrueAndArticuloSubtipoIdIn(Arrays.asList(ArticulosSubtipos.LIBRO,ArticulosSubtipos.PAQUETE_DE_LIBROS));


        List<ControlMaestroMultipleComboProjection> idiomas = cmmDao.findAllByControl(CMM_ART_Idioma.NOMBRE);
        List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> editoriales = cmmDao.findAllByControl(CMM_ART_Editorial.NOMBRE);

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("familias", familias);
        datos.put("categorias", categorias);
        datos.put("subcategorias", subcategorias);
        datos.put("clasificaciones1", clasificaciones1);
        datos.put("clasificaciones2", clasificaciones2);
        datos.put("clasificaciones3", clasificaciones3);
        datos.put("clasificaciones4", clasificaciones4);
        datos.put("clasificaciones5", clasificaciones5);
        datos.put("clasificaciones6", clasificaciones6);
        datos.put("articulosTipos", articulosTipos);
        datos.put("articulosSubtipos", articulosSubtipos);
        datos.put("unidadesMedida", unidadesMedida);
        datos.put("sucursales", sucursales);
        datos.put("tiposCosto", tiposCosto);

        datos.put("idiomas", idiomas);
        datos.put("programas", programas);
        datos.put("editoriales", editoriales);

        datos.put("componentesListado", componentesListado);

        datos.put("objetosImpuestoSAT", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_ObjetoImp"));

        if (id != null) {
            ArticuloEditarProjection articulo = articuloDao.findProjectedEditarById(id);
            datos.put("articulo", articulo);
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Articulo articulo, ServletRequest req) throws Exception {

        Articulo articuloCodigoExistente;
        if(articulo.getId() != null){
            articuloCodigoExistente = articuloDao.findByIdNotAndCodigoArticulo(articulo.getId(),articulo.getCodigoArticulo());
        }else{
            articuloCodigoExistente = articuloDao.findByCodigoArticulo(articulo.getCodigoArticulo());
        }

        if(articuloCodigoExistente != null){
            return new JsonResponse(null,"Ya existe un artículo con el código ingresado",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (articulo.getFamilia() != null) {
            articulo.setFamiliaId(articulo.getFamilia().getId());
        } else {
            articulo.setFamiliaId(null);
        }
        if (articulo.getCategoria() != null) {
            articulo.setCategoriaId(articulo.getCategoria().getId());
        } else {
            articulo.setCategoriaId(null);
        }
        if (articulo.getSubcategoria() != null) {
            articulo.setSubcategoriaId(articulo.getSubcategoria().getId());
        } else {
            articulo.setSubcategoriaId(null);
        }
        if (articulo.getClasificacion1() != null) {
            articulo.setClasificacion1Id(articulo.getClasificacion1().getId());
        } else {
            articulo.setClasificacion1Id(null);
        }
        if (articulo.getClasificacion2() != null) {
            articulo.setClasificacion2Id(articulo.getClasificacion2().getId());
        } else {
            articulo.setClasificacion2Id(null);
        }
        if (articulo.getClasificacion3() != null) {
            articulo.setClasificacion3Id(articulo.getClasificacion3().getId());
        } else {
            articulo.setClasificacion3Id(null);
        }
        if (articulo.getClasificacion4() != null) {
            articulo.setClasificacion4Id(articulo.getClasificacion4().getId());
        } else {
            articulo.setClasificacion4Id(null);
        }
        if (articulo.getClasificacion5() != null) {
            articulo.setClasificacion5Id(articulo.getClasificacion5().getId());
        } else {
            articulo.setClasificacion5Id(null);
        }
        if (articulo.getClasificacion6() != null) {
            articulo.setClasificacion6Id(articulo.getClasificacion6().getId());
        } else {
            articulo.setClasificacion6Id(null);
        }

        if (articulo.getTipoArticulo() != null) {
            articulo.setTipoArticuloId(articulo.getTipoArticulo().getId());
        } else {
            articulo.setTipoArticuloId(null);
        }
        if (articulo.getArticuloSubtipo() != null) {
            articulo.setArticuloSubtipoId(articulo.getArticuloSubtipo().getId());
        } else {
            articulo.setArticuloSubtipoId(null);
        }

        if (articulo.getUnidadMedidaInventario() != null) {
            articulo.setUnidadMedidaInventarioId(articulo.getUnidadMedidaInventario().getId());
        } else {
            articulo.setUnidadMedidaInventarioId(null);
        }
        if (articulo.getUnidadMedidaConversionVentas() != null) {
            articulo.setUnidadMedidaConversionVentasId(articulo.getUnidadMedidaConversionVentas().getId());
        } else {
            articulo.setUnidadMedidaConversionVentasId(null);
        }
        if (articulo.getUnidadMedidaConversionCompras() != null) {
            articulo.setUnidadMedidaConversionComprasId(articulo.getUnidadMedidaConversionCompras().getId());
        } else {
            articulo.setUnidadMedidaConversionComprasId(null);
        }

        if (articulo.getTipoCosto() != null) {
            articulo.setTipoCostoId(articulo.getTipoCosto().getId());
        } else {
            articulo.setTipoCostoId(null);
        }

        if(articulo.getIva() != null){
            articulo.setIva(articulo.getIva().divide(new BigDecimal(100)));
        }

        if(articulo.getIeps() != null){
            articulo.setIeps(articulo.getIeps().divide(new BigDecimal(100)));
        }

        if (articulo.getIdioma() != null) {
            articulo.setIdiomaId(articulo.getIdioma().getId());
        } else {
            articulo.setIdiomaId(null);
        }
        if (articulo.getPrograma() != null) {
            articulo.setProgramaId(articulo.getPrograma().getId());
            articulo.setPrograma(null);
        }
        if (articulo.getEditorial() != null) {
            articulo.setEditorialId(articulo.getEditorial().getId());
        } else {
            articulo.setEditorialId(null);
        }

        articulo.setObjetoImpuestoId(articulo.getObjetoImpuesto() != null ? articulo.getObjetoImpuesto().getId() : null);

        if(articulo.getArticuloSubtipoId() != null && articulo.getArticuloSubtipoId().intValue() == ArticulosSubtipos.PAQUETE_DE_LIBROS){
            articulo.setTipoCostoId(CMM_ART_TipoCosto.ULTIMO);
            articulo.setCostoEstandar(BigDecimal.ZERO);
            articulo.setCostoPromedio(BigDecimal.ZERO);
            articulo.setCostoUltimo(BigDecimal.ZERO);
        }
        for(ArticuloComponente componente : articulo.getComponentes()){
            componente.setComponenteId(componente.getComponente().getId());
            if(componente.getId() == null){
                componente.setCreadoPorId(idUsuario);
            }else{
                componente.setModificadoPorId(idUsuario);
            }
        }
        if(articulo.getId() != null && !articulo.getActivo()){
            List<PADescuentoArticuloEditarProjection> articulos = descuentoArticulosDao.findPADescuentoArticuloByArticuloId(articulo.getId());
            for(PADescuentoArticuloEditarProjection descuento: articulos){
                descuentoArticulosDao.deleteById(descuento.getId());
            }
        }

        if (articulo.getId() == null) {
            articulo.setCreadoPorId(idUsuario);
            articulo.setActivo(true);

            ArticuloFamilia familia = articuloFamiliaDao.findById(articulo.getFamiliaId());
            ArticuloCategoria categoria = articuloCategoriaDao.findById(articulo.getCategoriaId());
            String prefijo = familia.getPrefijo() + categoria.getPrefijo();
            Integer ceros = 7;

            if(articulo.getArticuloSubtipoId() != null && articulo.getArticuloSubtipoId() == ArticulosSubtipos.LIBRO){
                ControlMaestroMultiple idioma = cmmDao.findCMMById(articulo.getIdiomaId());
                prefijo += idioma.getReferencia();
                ceros = 4;
            }

            articulo.setCodigoArticulo(articuloDao.findSiguienteCodigo(prefijo,ceros));
        } else {
            Articulo objetoActual = articuloDao.findById(articulo.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), articulo.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            articulo.setModificadoPorId(idUsuario);
            articulo.setCuentaCompras(objetoActual.getCuentaCompras());

            if (articulo.getImg64() != null && articulo.getImagenId() != null) {
                fileSystemStorageService.borrarArchivo(articulo.getImagenId());
            }
        }

        for(ArticuloComponente componente : articulo.getComponentes()){
            if(componente.getId() == null){
                componente.setCreadoPorId(idUsuario);
            }else{
                componente.setModificadoPorId(idUsuario);
            }
        }

        if (articulo.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(articulo.getImg64(), idUsuario, 4, null, true, true);
            articulo.setImagenId(archivo.getId());
        }

        articulo = articuloDao.save(articulo);


        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idArticulo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idArticulo) throws SQLException {

        ArticuloEditarProjection articulo = articuloDao.findProjectedEditarById(idArticulo);

        return new JsonResponse(articulo, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticulo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticulo) throws SQLException {

        int actualizado = articuloDao.actualizarActivo(hashId.decode(idArticulo), false);

        List<PADescuentoArticuloEditarProjection> articulos = descuentoArticulosDao.findPADescuentoArticuloByArticuloId(hashId.decode(idArticulo));
        for(PADescuentoArticuloEditarProjection articulo: articulos){
            descuentoArticulosDao.deleteById(articulo.getId());
        }

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoArticulosById(@PathVariable(required = false) Integer id) throws SQLException {

        ArticuloEditarProjection articulo = null;
        if (id != null) {
            articulo = articuloDao.findProjectedEditarById(id);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("articulo", articulo);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ARTICULOS]";
        String[] alColumnas = new String[]{"Código Artículo", "Nombre Artículo", "Nombre Alterno", "ISBN"
                , "Descripción Larga", "Descripción Corta", "Clave SAT", "Tipo", "Subtipo", "Familia", "Serie"
                , "Unidad de Medida Inventario", "Unidad de Medida Venta", "Factor de Conversion Venta"
                , "Unidad de Medida Compra", "Factor de Conversion Compra", "Categoria", "Subcategoria", "Idioma"
                , "Programa", "Editorial", "Marco Certificación", "IVA", "IVA Exento", "IEPS", "IEPS Cuota Fija"
                , "Planeación por Temporada", "Maximo por Almacén", "Minimo por Almacén", "Multiplo para pedido"
                , "Permitir Cambio de Almacén", "Tipo Costo", "Costo Promedio", "Costo Estandar", "Costo Último"
                , "Activo", "Inventariable", "Para Venta", "Fecha Creación"};

        excelController.downloadXlsx(response, "articulos", query, alColumnas, null);
    }

    @RequestMapping(value = "/datos/precargar/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosPrecargar(@PathVariable(required = false) Integer id) throws SQLException {

        ArticuloPrecargarProjection articulo = articuloDao.findProjectedPrecargarById(id);

        return new JsonResponse(articulo, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/ultimas-compras/{articuloId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArticuloUltimasCompras(@PathVariable Integer articuloId) throws SQLException {

        List<ArticuloUltimasComprasProjection> articulos = articuloDao.findProjectedUltimasComprasAllById(articuloId);
        if(articulos.size() > 5){
            articulos = articulos.subList(0,5);
        }

        return new JsonResponse(articulos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/articulosByCategoria/{categoriaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse articulosByCategoria(@PathVariable Integer categoriaId) throws SQLException {
        List<ArticuloComboProjection> articulos = articuloDao.findAllByCategoriaId(categoriaId);
        return new JsonResponse(articulos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/arbol/componentes/{articuloId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArbolComponentes(@PathVariable Integer articuloId) throws SQLException {

        ArticuloArbolComponentesProjection articulo = articuloDao.findProjectedArbolComponentesById(articuloId);

        return new JsonResponse(articulo, null, JsonResponse.STATUS_OK);
    }
}

