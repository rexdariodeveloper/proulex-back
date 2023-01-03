package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHBecarioDocumento.CXPSolicitudPagoRHBecarioDocumentoEditarProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionadoProjection;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaEditarProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaListadoProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCXPFacturaListadoProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCargarFacturaProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboRelacionarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorRelacionarProjection;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ArchivoController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.main.models.JsonFacturaXML;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.main.services.ArchivoXMLService;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

/**
 * Created by Angel Daniel Hernández Silva on 08/09/2020.
 */
@RestController
@RequestMapping("/api/v1/gestion-facturas")
public class GestionFacturasController {

    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private OrdenCompraDetalleDao ordenCompraDetalleDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private MonedaParidadDao monedaParidadDao;
    @Autowired
    private OrdenCompraReciboDao ordenCompraReciboDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private  CXPSolicitudPagoRHDao cxpSolicitudPagoRHDao;
    @Autowired
    private CXPSolicitudPagoRHBecarioDocumentoDao cxpSolicitudPagoRHBecarioDocumentoDao;
    @Autowired
    private CXPSolicitudPagoServicioDao cxpSolicitudPagoServicioDao;

    @Autowired
    private ArchivoXMLService archivoXMLService;
    @Autowired
    private final StorageService storageService;
    @Autowired
    private SATService satService;

    @Autowired
    private ArchivoController archivoController;
    @Autowired
    private LogController logController;

    @Autowired
    private HashId hashId;

    @Autowired
    public GestionFacturasController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas() throws SQLException {

        HashMap<String, Object> json = new HashMap<>();

        List<CXPFacturaListadoProjection> facturasRelacionadas = cxpFacturaDao.findProjectedListadoAllByEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.ABIERTA);

        List<OrdenCompraReciboCXPFacturaListadoProjection> recibos = ordenCompraReciboDao.findProjectedCXPFacturaListadoAllByOrderByCodigoRecibo();

        List<Object> facturas = new ArrayList<>();
        facturas.addAll(facturasRelacionadas);
        facturas.addAll(recibos);

        json.put("datos", facturas);
        json.put("proveedores", proveedorDao.findProjectedComboVistaAllBy());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas(@RequestBody JSONObject json) throws SQLException, ParseException {

        ArrayList<HashMap<String, Integer>> alProveedores = (ArrayList<HashMap<String, Integer>>) json.get("proveedores");

        ArrayList<Integer> proveedores = new ArrayList<>();
        for (HashMap<String, Integer> proveedor : alProveedores) {
            proveedores.add(proveedor.get("id"));
        }

        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");

        Date dateFechaInicio = isNullorEmpty(fechaInicio) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio);
        Date dateFechaFin = isNullorEmpty(fechaFin) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin);

        List<CXPFacturaListadoProjection> facturasRelacionadas = cxpFacturaDao.findProjectedListadoAllByEstatusIdAndProveedorIdInAndFechaRegistroGreaterThanAndFechaRegistroLessThan(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.ABIERTA,proveedores,dateFechaInicio,dateFechaFin);

        List<OrdenCompraReciboCXPFacturaListadoProjection> recibos = ordenCompraReciboDao.findProjectedCXPFacturaListadoAllByProveedorIdInAndFechaReciboGreaterThanAndFechaReciboLessThanOrderByCodigoRecibo(proveedores,dateFechaInicio,dateFechaFin);

        List<Object> facturas = new ArrayList<>();
        facturas.addAll(facturasRelacionadas);
        facturas.addAll(recibos);

        return new JsonResponse(facturas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{facturaId}", "/listados/extra/{reciboId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoFacturaById(@PathVariable(required = false) Integer facturaId, @PathVariable(required = false) Integer reciboId) throws Exception {

        CXPFacturaEditarProjection factura = null;
        JsonFacturaXML jsonFacturaXML = null;
        List<OrdenCompraRelacionarProjection> ordenesCompra = null;
        List<OrdenCompraDetalleRelacionadoProjection> ordenesCompraDetalles = null;
        if (facturaId != null && facturaId.intValue() > 0) {
            factura = cxpFacturaDao.findProjectedEditarById(facturaId);
            ordenesCompra = ordenCompraDao.fnGetOrdenesCompraPendientesRelacionarPorCXPFactura(facturaId);
            List<Integer> ocIds = new ArrayList<>();
            for(OrdenCompraRelacionarProjection oc : ordenesCompra){
                ocIds.add(oc.getId());
            }
            ordenesCompraDetalles = ordenCompraDetalleDao.findProjectedRelacionarAllByFacturaIdIn(factura.getId());
            jsonFacturaXML = archivoXMLService.getDatosFacturaXML(factura.getFacturaXML().getId());
        }

        OrdenCompraReciboCargarFacturaProjection recibo = null;
        if(reciboId != null){
            recibo = ordenCompraReciboDao.findProjectedCargarFacturaById(reciboId);
            if(recibo.getFacturaXML() != null){
                jsonFacturaXML = archivoXMLService.getDatosFacturaXML(recibo.getFacturaXML().getId());
            }
        }

        List<ProveedorRelacionarProjection> proveedores = proveedorDao.findProjectedRelacionarAllBy();
        List<ControlMaestroMultipleComboProjection> tiposPago = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_CCXP_TipoPago.NOMBRE);
        List<ControlMaestroMultipleComboProjection> tiposRetencion = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_CXPF_TipoRetencion.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("factura", factura);
        json.put("ordenesCompra", ordenesCompra);
        json.put("ordenesCompraDetalles", ordenesCompraDetalles);
        json.put("recibo", recibo);
        json.put("jsonFacturaXML", jsonFacturaXML);
        json.put("proveedores", proveedores);
        json.put("tiposPago", tiposPago);
        json.put("tiposRetencion", tiposRetencion);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping("/oc/relacionar/{proveedorHashId}")
    public JsonResponse buscarDatosProveedor(@PathVariable String proveedorHashId) throws Exception {

        List<Integer> estatusOCRecibidasIds = Arrays.asList(
                ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBO_PARCIAL,
                ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBIDA
        );

        Integer proveedorId = hashId.decode(proveedorHashId);

        Proveedor proveedor = proveedorDao.findById(proveedorId);
        if(satService.proveedorEnListaNegra(proveedor.getRfc())){
            return new JsonResponse(null, "Proveedor en lista negra", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        List<OrdenCompraRelacionarProjection> ordenesCompra = ordenCompraDao.fnGetOrdenesCompraPendientesRelacionar(proveedorId);

        return new JsonResponse(ordenesCompra,"","",JsonResponse.STATUS_OK);
    }

    @RequestMapping("/oc/relacionar/{ordenCompraId}/detalles")
    public JsonResponse buscarOrdenCompraDetalles(@PathVariable String ordenCompraId) throws Exception {

        List<OrdenCompraDetalleRelacionarProjection> detalles = ordenCompraDetalleDao.findProjectedRelacionarAllByOrdenCompraId(hashId.decode(ordenCompraId));

        return new JsonResponse(detalles,"","",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CXPFactura cxpFactura, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(cxpFactura.getId() != null){
            Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotInAndIdNot(cxpFactura.getUuid(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA),cxpFactura.getId());
            if(existeUUID){
                return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
            CXPFactura facturaActualizar = cxpFacturaDao.findById(cxpFactura.getId());
            facturaActualizar.setTipoPagoId(cxpFactura.getTipoPago().getId());
            facturaActualizar.setModificadoPorId(idUsuario);
            cxpFacturaDao.save(facturaActualizar);
            return new JsonResponse(null, null, JsonResponse.STATUS_OK);
        }
        Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotIn(cxpFactura.getUuid(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA));
        if(existeUUID){
            return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        Moneda moneda = monedaDao.findByCodigo(cxpFactura.getMonedaCodigo());
        if(moneda == null){
            return new JsonResponse(null,"Moneda no registrada (" + cxpFactura.getMonedaCodigo() + ")",JsonResponse.STATUS_ERROR_NULL);
        }
        MonedaParidad monedaParidad = monedaParidadDao.findByMonedaIdAndFecha(moneda.getId(),cxpFactura.getFechaRegistro());

        cxpFactura.setMonedaId(moneda.getId());
        if(monedaParidad == null){
            cxpFactura.setParidadOficial(BigDecimal.ONE);
        }else{
            cxpFactura.setParidadOficial(monedaParidad.getTipoCambioOficial());
        }
        cxpFactura.setParidadUsuario(BigDecimal.ONE);
        if(cxpFactura.getTipoPago() != null){
            cxpFactura.setTipoPagoId(cxpFactura.getTipoPago().getId());
        }
        if(cxpFactura.getProveedor() != null){
            cxpFactura.setProveedorId(cxpFactura.getProveedor().getId());
        }
        cxpFactura.setTipoRegistroId(ControlesMaestrosMultiples.CMM_CXPF_TipoRegistro.FACTURA_CXP);

        int numeroLinea = 0;
        List<CXPFacturaDetalle> nuevosDetalles = new ArrayList<>();
        for(CXPFacturaDetalle cxpFacturaDetalle : cxpFactura.getDetalles()){
            if(cxpFacturaDetalle.getOrdenCompraDetalleId() != null){
                List<OrdenCompraReciboRelacionarProjection> recibosRelacionar = ordenCompraReciboDao.findProjectedRelacionarAllByOrdenCompraDetalleId(cxpFacturaDetalle.getOrdenCompraDetalleId());
                BigDecimal cantidadRestanteRelacionar = cxpFacturaDetalle.getCantidadRelacionar();
                Boolean utilizarDetalleActual = true;

                for(OrdenCompraReciboRelacionarProjection recibo : recibosRelacionar){
                    if(recibo.getCantidadPendienteRelacionar().compareTo(BigDecimal.ZERO) > 0){
                        numeroLinea++;
                        BigDecimal cantidadRelacionar;
                        if(cantidadRestanteRelacionar.compareTo(recibo.getCantidadPendienteRelacionar()) >= 0){
                            cantidadRelacionar = recibo.getCantidadPendienteRelacionar();
                        }else{
                            cantidadRelacionar = cantidadRestanteRelacionar;
                        }

                        if(utilizarDetalleActual){
                            cxpFacturaDetalle.setNumeroLinea(numeroLinea);
                            cxpFacturaDetalle.setCantidad(cantidadRelacionar);
                            cxpFacturaDetalle.setReciboId(recibo.getId());
                            if(cxpFacturaDetalle.getIva() != null){
                                cxpFacturaDetalle.setIva(cxpFacturaDetalle.getIva().divide(new BigDecimal(100)));
                            }
                            if(cxpFacturaDetalle.getIeps() != null){
                                cxpFacturaDetalle.setIeps(cxpFacturaDetalle.getIeps().divide(new BigDecimal(100)));
                            }
                            if(cxpFacturaDetalle.getDescuento() == null){
                                cxpFacturaDetalle.setDescuento(BigDecimal.ZERO);
                            }
                            utilizarDetalleActual = false;
                        }else{
                            CXPFacturaDetalle nuevoDetalle = new CXPFacturaDetalle();
                            nuevoDetalle.setNumeroLinea(numeroLinea);
                            nuevoDetalle.setDescripcion(cxpFacturaDetalle.getDescripcion());
                            nuevoDetalle.setCantidad(cantidadRelacionar);
                            nuevoDetalle.setPrecioUnitario(cxpFacturaDetalle.getPrecioUnitario());
                            nuevoDetalle.setIva(cxpFacturaDetalle.getIva());
                            nuevoDetalle.setIvaExento(cxpFacturaDetalle.getIvaExento());
                            nuevoDetalle.setIeps(cxpFacturaDetalle.getIeps());
                            nuevoDetalle.setIepsCuotaFija(cxpFacturaDetalle.getIepsCuotaFija());
                            nuevoDetalle.setDescuento(cxpFacturaDetalle.getDescuento());
                            nuevoDetalle.setReciboId(recibo.getId());

                            nuevosDetalles.add(nuevoDetalle);
                        }

                        cantidadRestanteRelacionar = cantidadRestanteRelacionar.subtract(cantidadRelacionar);
                        if(cantidadRestanteRelacionar.compareTo(BigDecimal.ZERO) <= 0){
                            break;
                        }
                    }
                }
                if(cantidadRestanteRelacionar.compareTo(BigDecimal.ZERO) > 0){
                    return new JsonResponse(null,"Las cantidades por relacionar no coinciden con las cantidades de recibos",JsonResponse.STATUS_ERROR_NULL);
                }
            }else{
                numeroLinea++;
                cxpFacturaDetalle.setNumeroLinea(numeroLinea);
            }
        }

        cxpFactura.getDetalles().addAll(nuevosDetalles);

        cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.ABIERTA);
        cxpFacturaDao.save(cxpFactura);

        List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
        for( Integer oc: ordenesCompra ){
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.RELACIONADO,
                            LogProceso.ORDENES_COMPRA,
                            oc
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);

    }

    @RequestMapping(value = "/enviar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse programar(@RequestBody List<Integer> facturasProgramarIds, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<CXPFactura> facturasProgramar = cxpFacturaDao.findByIdIn(facturasProgramarIds);

        for(CXPFactura factura : facturasProgramar){
            factura.setModificadoPorId(idUsuario);
            factura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO);

            cxpFacturaDao.save(factura);

            List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(factura.getId());
            for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){
                logController.insertaLogUsuario(
                        new Log(null,
                                LogTipo.ENVIADO_PROGRAMACION_PAGO,
                                LogProceso.SOLICITUD_PAGO,
                                solicitud.getId()
                        ),
                        idUsuario
                );
            }
            // Obtenemos  de la factura
            List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( factura.getId() );
            for( Integer oc: ordenesCompra ){
                logController.insertaLogUsuario(
                        new Log(null,
                                LogTipo.PROGRAMADO,
                                LogProceso.ORDENES_COMPRA,
                                oc
                        ),
                        idUsuario
                );
            }
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/descargar/evidencia", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadEvidencia(@RequestBody HashMap<String,Object> body, Boolean publico) throws Exception {

        String facturaHashId = (String)body.get("facturaId");
        Integer facturaId = hashId.decode(facturaHashId);

        CXPFactura factura = cxpFacturaDao.findById(facturaId);

        List<String> evidenciaHashIds = new ArrayList<>();

        for(CXPFacturaDetalle detalle : factura.getDetalles()){
            if(detalle.getRecibo() != null){
                for(Archivo archivoEvidencia : detalle.getRecibo().getEvidencia()){
                    evidenciaHashIds.add(hashId.encode(archivoEvidencia.getId()));
                }
            }
        }

        Integer solicitudPagoRHId = cxpSolicitudPagoRHDao.getIdByFactura(facturaId);
        List<CXPSolicitudPagoRHBecarioDocumento> documentos = cxpSolicitudPagoRHBecarioDocumentoDao.findAllByCpxSolicitudPagoRhId(solicitudPagoRHId);
        for(CXPSolicitudPagoRHBecarioDocumento documento: documentos){
            evidenciaHashIds.add(hashId.encode(documento.getArchivo().getId()));
        }

        HashMap<String,Object> requestBodyArchivos = new HashMap<>();
        requestBodyArchivos.put("idsArchivos",evidenciaHashIds);
        requestBodyArchivos.put("nombreZip","evidencia.zip");

        return archivoController.downloadFiles(requestBodyArchivos,null);
    }

    @ResponseBody
    @RequestMapping(value = "/descargar/factura", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFactura(@RequestBody HashMap<String,Object> body, Boolean publico) throws Exception {

        String facturaHashId = (String)body.get("facturaId");
        Integer facturaId = hashId.decode(facturaHashId);

        CXPFactura factura = cxpFacturaDao.findById(facturaId);

        List<String> archivosFacturaHashIds = new ArrayList<>();

        if(factura.getFacturaPDFId() != null){
            archivosFacturaHashIds.add(hashId.encode(factura.getFacturaPDFId()));
        }
        if(factura.getFacturaXMLId() != null){
            archivosFacturaHashIds.add(hashId.encode(factura.getFacturaXMLId()));
        }

        HashMap<String,Object> requestBodyArchivos = new HashMap<>();
        requestBodyArchivos.put("idsArchivos",archivosFacturaHashIds);
        requestBodyArchivos.put("nombreZip","factura.zip");

        return archivoController.downloadFiles(requestBodyArchivos,null);
    }

    @Transactional
    @RequestMapping(value = "/delete/{facturaHashId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String facturaHashId, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        CXPFactura cxpFactura = cxpFacturaDao.findById(hashId.decode(facturaHashId));

        cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO);

        cxpFacturaDao.save(cxpFactura);

        // Obtenemos  de la factura
        List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
        for( Integer oc: ordenesCompra ){
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.RELACION_BORRADA,
                            LogProceso.ORDENES_COMPRA,
                            oc
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/upload/xml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse fileUploadXML(@RequestParam("file") MultipartFile file, @RequestParam("idEstructuraArchivo") Integer idEstructuraArchivo, @RequestParam("subcarpeta") String subcarpeta, @RequestParam("idTipo") Integer idTipo, @RequestParam("publico") Boolean publico, @RequestParam("activo") Boolean activo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        idTipo = idTipo > 0 ? idTipo : null;
        Archivo archivo = storageService.store(file, idUsuario, idEstructuraArchivo, subcarpeta, idTipo, publico, activo);

        JsonFacturaXML jsonFacturaXML = archivoXMLService.getDatosFacturaXML(archivo.getId());

        Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotIn(jsonFacturaXML.getDatosFactura().getUuid().toString(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA));
        if(existeUUID){
            return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        return new JsonResponse(jsonFacturaXML,null,JsonResponse.STATUS_OK);
    }

}
