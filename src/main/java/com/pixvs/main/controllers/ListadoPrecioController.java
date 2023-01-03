package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloListadoPrecioMaterialProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloListadoPrecioProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioEditarProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioListadoProjection;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/listado-precio")
public class ListadoPrecioController {

    @Autowired
    private ListadoPrecioDao listadoPrecioDao;
    @Autowired
    private ListadoPrecioDetalleDao listadoPrecioDetalleDao;
    @Autowired
    private ControlMaestroDao cmDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private ListadoPrecioDetalleCursoDao listadoPrecioDetalleCursoDao;
    @Autowired
    private MonedaDao monedaDao;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<ListadoPrecioListadoProjection> listados = listadoPrecioDao.findProjectedComboAllBy();

        return new JsonResponse(listados, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ListadoPrecio listado, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        // Validar duplicidad de código
        ListadoPrecioEditarProjection temp = listadoPrecioDao.findByCodigo(listado.getCodigo());
        if(temp != null && listado.getId() == null) {
            return new JsonResponse(null, "El código asignado ya existe", JsonResponse.STATUS_ERROR_PROBLEMA);
        }

        // Settear datos de control
        if (listado.getId() == null) {
            listado.setCreadoPorId(idUsuario);
        } else {
            listado.setMonedaId(listado.getMoneda().getId());
            listado.setModificadoPorId(idUsuario);
            listado.setFechaModificacion(new Date(System.currentTimeMillis()));
        }

        // Iterar detalles
        for (ListadoPrecioDetalle detalle : listado.getDetalles()) {

            // Actualizar artículo
            if (detalle.getArticulo() != null) {
                detalle.setArticuloId(detalle.getArticulo().getId());
                detalle.setArticulo(null);
            }

            if(detalle.getId() != null){
                List<Integer> articulosActualizadosIds = new ArrayList<>();

                // Iterar sobre libros y certificaciones del curso
                List<ListadoPrecioDetalleCurso> materiales = new ArrayList<>();
                for (ListadoPrecioDetalleCurso detalleCurso : detalle.getDetallesCurso()) {
                    ListadoPrecioDetalleCurso detalleCursoExistente = listadoPrecioDetalleCursoDao.findByListadoPrecioDetalleIdAndArticuloId(detalle.getId(),detalleCurso.getArticuloId());
                    if(detalleCursoExistente != null){
                        detalleCursoExistente.setPrecio(detalleCurso.getPrecio());
                        materiales.add(detalleCursoExistente);
                    }else{
                        materiales.add(detalleCurso);
                    }
                    articulosActualizadosIds.add(detalleCurso.getArticuloId());
                }
                List<ListadoPrecioDetalleCurso> detallesCursoPrecioCero = listadoPrecioDetalleCursoDao.findAllByListadoPrecioDetalleIdAndArticuloIdNotIn(detalle.getId(),articulosActualizadosIds);
                for(ListadoPrecioDetalleCurso detalleCursoPrecioCero : detallesCursoPrecioCero){
                    detalleCursoPrecioCero.setPrecio(BigDecimal.ZERO);
                    materiales.add(detalleCursoPrecioCero);
                }
                detalle.setDetallesCurso(materiales);
            }
        }

        listado = listadoPrecioDao.save(listado);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idListado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idListado) throws SQLException {

        ListadoPrecioEditarProjection listado = listadoPrecioDao.findProjectedEditarById(idListado);

        return new JsonResponse(listado, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idListado}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idListado) throws SQLException {

        int actualizado = listadoPrecioDao.actualizarActivo(hashId.decode(idListado), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        ListadoPrecioEditarProjection listado = null;
        if (id != null) {
            listado = listadoPrecioDao.findProjectedEditarById(id);
        }
//        List<ArticuloListadoPrecioProjection> articulos = articuloDao.findAllByArticuloParaVentaIsTrueAndActivoIsTrueOrderByNombreArticulo();
        List<ArticuloListadoPrecioProjection> articulos = new ArrayList<>();
        articulos.addAll(articuloDao.findProjectedListadoPrecioAllBySinCursos());
        articulos.addAll(articuloDao.findProjectedListadoPrecioAllBySoloCursos());
        ControlMaestro precioTotal = cmDao.findCMByNombre("CM_CALCULO_PRECIO_TOTAL");
        ControlMaestro precioUnitario = cmDao.findCMByNombre("CM_CALCULO_PRECIO_UNITARIO");
        Boolean precioVentaBandera = precioTotal.getValor().equals("1") ? true : false;
        Boolean precioUnitarioBandera = precioUnitario.getValor().equals("1") ? true : false;
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();

        HashMap<String, Object> json = new HashMap<>();

        json.put("listado", listado);
        json.put("articulos", articulos);
        json.put("precioVentaBandera",precioVentaBandera);
        json.put("precioUnitarioBandera",precioUnitarioBandera);
        json.put("monedas",monedas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/materiales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getMateriales(@RequestBody HashMap<String,Integer> requestBody) {

        Integer articuloId = requestBody.get("articuloId");
        Integer listadoPreciosId = requestBody.get("listadoPreciosId");

        Articulo articulo = articuloDao.findById(articuloId);
        List<ArticuloListadoPrecioMaterialProjection> libros = articuloDao.findLibrosMaterialesCurso(articuloId,listadoPreciosId);
        List<ArticuloListadoPrecioMaterialProjection> certificaciones = articuloDao.findCertificacionesMaterialesCurso(articuloId,listadoPreciosId);

        HashMap<String, Object> json = new HashMap<>();

        json.put("articuloId", articulo.getId());
        json.put("libros", libros);
        json.put("certificaciones", certificaciones);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [dbo].[VW_LISTADO_PRECIO_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Fecha", "Asignado","Estatus"};

        excelController.downloadXlsx(response, "lista_precios", query, alColumnas, null,"Lista de precios");
    }

    public void eliminarDetalles(Integer listadoId){
        List<ListadoPrecioDetalleEditarProjection> detalles = listadoPrecioDetalleDao.findAllByListadoPrecioId(listadoId);
        for(ListadoPrecioDetalleEditarProjection detalle: detalles){
            if(detalle.getHijos()!=null){
                for(ListadoPrecioDetalleEditarProjection hijo: detalle.getHijos()){
                    listadoPrecioDetalleDao.deleteById(hijo.getId());
                }
            }
            listadoPrecioDetalleDao.deleteById(detalle.getId());
        }
    }

    @GetMapping("/download/plantilla")
    public void downloadPlantilla(HttpServletResponse response) throws IOException {

        String query = "" +
                "SELECT * FROM [VW_LISTADO_PRECIOS_EXPORTAR] " +
                "ORDER BY CODIGO_ARTICULO";
        String[] alColumnas = new String[]{"CODIGO_ARTICULO", "NOMBRE_ARTICULO", "DESCRIPCION", "PRECIO"};

        excelController.downloadXlsx(response, "listado_precios_plantilla", query, alColumnas, null,"Listado de precios plantilla");
    }

    @Transactional
    @RequestMapping(value = "/upload/xml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse fileUploadXML(@RequestParam("file") MultipartFile file, ServletRequest req) throws IOException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        JSONArray articulosActualizar = new JSONArray();
        try {
            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);
                float precioVenta = (float) row.getCell(3).getNumericCellValue();
                if (precioVenta > 0) {
                    JSONObject articuloVenta = new JSONObject();
                    String codigoArticulo = row.getCell(0).getStringCellValue();
                    articuloVenta.put("codigoArticulo", codigoArticulo);
                    articuloVenta.put("precio", precioVenta);
                    articulosActualizar.appendElement(articuloVenta);
                }
            }
            return new JsonResponse(articulosActualizar, null, JsonResponse.STATUS_OK);
        }
        catch (Exception e){
            return new JsonResponse(null, "El archivo no es correcto", JsonResponse.STATUS_ERROR);
        }
    }

    @GetMapping("/download/excel/{listadoPrecioId}")
    public void downloadXlsx(@PathVariable Integer listadoPrecioId, HttpServletResponse response) throws IOException {

        ListadoPrecio listadoPrecio = listadoPrecioDao.findById(listadoPrecioId);

        String query = "" +
                "Select \n" +
                "ART_CodigoArticulo,\n" +
                "ART_NombreArticulo,\n" +
                "ART_Descripcion,\n" +
                "CASE\n" +
                "WHEN dbo.getListadoPrecioDetalleHijosTotal(LIPRED_ListadoPrecioDetalleId) is not null THEN (Select dbo.getListadoPrecioDetalleHijosTotal(LIPRED_ListadoPrecioDetalleId))+LIPRED_Precio\n" +
                "ELSE LIPRED_Precio\n" +
                "END as precio\n" +
                "from ListadosPreciosDetalles\n" +
                "LEFT JOIN ListadosPrecios ON LIPRED_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId\n" +
                "INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId\n" +
                "WHERE LIPRE_ListadoPrecioId="+listadoPrecioId.toString()+"";
        String[] alColumnas = new String[]{"CÓDIGO_ARTICULO", "NOMBRE_ARTICULO", "DESCRIPCION", "PRECIO_VENTA"};

        excelController.downloadXlsx(response, listadoPrecio.getCodigo(), query, alColumnas, null,"Lista de precios " + listadoPrecio.getCodigo());
    }
}

