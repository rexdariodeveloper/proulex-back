package com.pixvs.main.services;

import com.pixvs.main.dao.CXCFacturaDao;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.cfdi.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaProjection;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaRelacionadaProjection;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleImpuestoProjection;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleProjection;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacturaService extends FacturacionService {

    @Autowired
    private CXCFacturaDao facturaDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    private Comprobante.Impuestos.Traslados comprobanteTraslados;
    private BigDecimal totalImpuestosTraslados;
    private BigDecimal subTotalComprobante;
    private BigDecimal totalComprobante;
    private BigDecimal descuentoComprobante;

    private CFDIFacturaProjection factura;

    @Override
    public String facturar(int id, boolean timbrar) throws Exception {
        // Obtenemos los datos de la factura
        factura = facturaDao.findCFDIFacturaProjectedById(id);

        // Inicializamos los totales globales
        subTotalComprobante = BigDecimal.ZERO;
        totalComprobante = BigDecimal.ZERO;
        descuentoComprobante = BigDecimal.ZERO;
        totalImpuestosTraslados = BigDecimal.ZERO;
        comprobanteTraslados = new Comprobante.Impuestos.Traslados();

        // Regresamos el XML
        return getXMLTimbrado(id, timbrar);
    }

    @Override
    public Comprobante crearComprobante() throws Exception {
        // Inicializamos el comprobante
        comprobante = new Comprobante();

        // Obtener el tipo de moneda
        CMoneda moneda = CMoneda.MXN;

        if (factura.getMoneda().getCodigo().equals("Euro")) {
            moneda = CMoneda.EUR;
        } else if (factura.getMoneda().getCodigo().equals("USD")) {
            moneda = CMoneda.USD;
        }

        // Construir el comprobante
        comprobante.setTipoDeComprobante(CTipoDeComprobante.I);
        comprobante.setVersion(factura.getVersion());
        comprobante.setFecha(DatatypeFactory.newInstance().newXMLGregorianCalendar(getStringFecha(factura.getFecha())));
        comprobante.setSerie(factura.getSerie());
        comprobante.setFolio(factura.getFolio());
        comprobante.setFormaPago(factura.getFormaPago().getCodigo());
        comprobante.setMoneda(moneda);
        comprobante.setTipoCambio(moneda == CMoneda.MXN ? BigDecimal.ONE : factura.getTipoCambio()); //Sujeto a SAT
        comprobante.setMetodoPago(CMetodoPago.fromValue(factura.getMetodoPago().getReferencia()));
        comprobante.setLugarExpedicion(factura.getEmisorCP());
        comprobante.setEmisor(crearEmisor(factura.getEmisorRFC(), factura.getEmisorRazonSocial(), factura.getEmisorRegimenFiscal().getCodigo()));
        comprobante.setReceptor(crearReceptor(factura.getReceptorRFC(), factura.getReceptorNombre(), !esFacturaGlobal() ? factura.getReceptorCP() : comprobante.getLugarExpedicion(), (factura.getReceptorRegimenFiscal() == null ? null : factura.getReceptorRegimenFiscal().getCodigo()), factura.getReceptorUsoCFDI().getCodigo()));
        comprobante.setConceptos(crearConceptos());
        comprobante.setSubTotal(subTotalComprobante);
        comprobante.setTotal(totalComprobante); //Sujeto a SAT

        if (esVersion4()) {
            comprobante.setExportacion("01"); //No aplica
            //comprobante.setConfirmacion(""); //Sujeto a SAT

            // Si es factura global
            if (esFacturaGlobal()) {
                comprobante.setInformacionGlobal(crearInformacionGlobal());
            }
        }

        if (factura.getCondicionesPago() != null) {
            comprobante.setCondicionesDePago(factura.getCondicionesPago());
        }

        if (descuentoComprobante.compareTo(BigDecimal.ZERO) != 0) {
            comprobante.setDescuento(descuentoComprobante);
        }

        if ((esVersion4() && !comprobanteTraslados.getTraslado().isEmpty()) || totalImpuestosTraslados.compareTo(BigDecimal.ZERO) != 0) {
            Comprobante.Impuestos impuestos = new Comprobante.Impuestos();

            impuestos.setTotalImpuestosTrasladados(totalImpuestosTraslados);
            impuestos.setTraslados(comprobanteTraslados);

            comprobante.setImpuestos(impuestos);
        }

        if (factura.getFacturasRelacionadas().size() > 0) {
            crearCFDIRelacionados();
        }

        return comprobante;
    }

    private Comprobante.InformacionGlobal crearInformacionGlobal() throws Exception {
        Comprobante.InformacionGlobal informacionGlobal = new Comprobante.InformacionGlobal();

        informacionGlobal.setPeriodicidad(factura.getPeriodicidad().getCodigo());
        informacionGlobal.setMeses(factura.getMes().getCodigo());
        informacionGlobal.setAnio(factura.getAnio().shortValue());

        return informacionGlobal;
    }

    @Override
    public Comprobante.Conceptos crearConceptos() throws Exception {
        // Crear la lista de conceptos
        Comprobante.Conceptos conceptos = new Comprobante.Conceptos();

        for (CFDIFacturaDetalleProjection detalle : factura.getDetalles()) {
            conceptos.getConcepto().add(crearConcepto(detalle));
        }

        return conceptos;
    }

    private Comprobante.Conceptos.Concepto crearConcepto(CFDIFacturaDetalleProjection detalle) throws Exception {
        // Añadir los datos de cada concepto
        BigDecimal cantidad = detalle.getCantidad().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal valorUnitario = detalle.getValorUnitario();
        BigDecimal importe = detalle.getImporte().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal descuento = detalle.getDescuento().setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalConcepto = importe.subtract(descuento);

        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();

        concepto.setClaveProdServ(detalle.getClaveProdServ());
        concepto.setNoIdentificacion(detalle.getNoIdentificacion());
        concepto.setDescripcion(detalle.getDescripcion());
        concepto.setClaveUnidad(detalle.getUnidadMedida().getClaveSAT());

        // Si no es factura global
        if (!esFacturaGlobal()) {
            concepto.setUnidad(detalle.getUnidadMedida().getNombre());
        }

        concepto.setCantidad(cantidad);
        concepto.setValorUnitario(valorUnitario);
        concepto.setImporte(importe);

        if (esVersion4()) {
            concepto.setObjetoImp(detalle.getObjetoImpuesto().getReferencia());
        }

        if (descuento.compareTo(BigDecimal.ZERO) != 0) {
            concepto.setDescuento(descuento);
        }

        if (!esVersion4() || detalle.getObjetoImpuesto().getId().equals(ControlesMaestrosMultiples.CMM_SAT_ObjetoImp.SI_OBJETO_IMPUESTO)) {
            if (detalle.getImpuestos().size() > 0) {
                Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
                Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();

                for (CFDIFacturaDetalleImpuestoProjection impuesto : detalle.getImpuestos()) {
                    traslados.getTraslado().add(crearImpuestoConcepto(impuesto));
                }

                impuestos.setTraslados(traslados);
                concepto.setImpuestos(impuestos);
            }

            for (Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado impuesto : concepto.getImpuestos().getTraslados().getTraslado()) {
                totalConcepto = totalConcepto.add(impuesto.getImporte() != null ? impuesto.getImporte() : BigDecimal.ZERO);
            }
        }

        subTotalComprobante = subTotalComprobante.add(importe);
        descuentoComprobante = descuentoComprobante.add(descuento);
        totalComprobante = totalComprobante.add(totalConcepto.setScale(2, BigDecimal.ROUND_HALF_UP));

        return concepto;
    }

    private Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado crearImpuestoConcepto(CFDIFacturaDetalleImpuestoProjection impuesto) throws Exception {
        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();

        traslado.setImpuesto(impuesto.getClave());
        traslado.setTipoFactor(CTipoFactor.fromValue(impuesto.getTipoFactor()));
        traslado.setBase(impuesto.getBase().setScale(2, BigDecimal.ROUND_HALF_UP));

        if (!traslado.getTipoFactor().equals(CTipoFactor.EXENTO)) {
            traslado.setTasaOCuota(impuesto.getTasaOCuota().setScale(6, BigDecimal.ROUND_HALF_UP));
            traslado.setImporte(impuesto.getImporte().setScale(2, BigDecimal.ROUND_HALF_UP));

            // Agregamos el traslado al comprobante
            totalImpuestosTraslados = totalImpuestosTraslados.add(traslado.getImporte());
        }

        Comprobante.Impuestos.Traslados.Traslado comprobanteTraslado = new Comprobante.Impuestos.Traslados.Traslado();

        comprobanteTraslado.setImpuesto(traslado.getImpuesto());
        comprobanteTraslado.setTipoFactor(traslado.getTipoFactor());

        if (esVersion4()) {
            comprobanteTraslado.setBase(traslado.getBase());
        }

        if (!comprobanteTraslado.getTipoFactor().equals(CTipoFactor.EXENTO)) {
            comprobanteTraslado.setTasaOCuota(traslado.getTasaOCuota());
            comprobanteTraslado.setImporte(traslado.getImporte());
        }

        boolean agregarTraslado = true;

        for (Comprobante.Impuestos.Traslados.Traslado temp : comprobanteTraslados.getTraslado()) {
            if (temp.getImpuesto().equals(comprobanteTraslado.getImpuesto())
                    && temp.getTipoFactor().equals(comprobanteTraslado.getTipoFactor())) {
                if (temp.getTipoFactor().equals(CTipoFactor.EXENTO) ||
                        (!temp.getTipoFactor().equals(CTipoFactor.EXENTO)
                                && temp.getTasaOCuota().equals(comprobanteTraslado.getTasaOCuota()))) {
                    if (esVersion4()) {
                        temp.setBase(temp.getBase().add(comprobanteTraslado.getBase()));
                    }

                    if (!temp.getTipoFactor().equals(CTipoFactor.EXENTO)) {
                        temp.setImporte(temp.getImporte().add(comprobanteTraslado.getImporte()));
                    }

                    agregarTraslado = false;
                }
            }
        }

        if (agregarTraslado && (esVersion4() || !comprobanteTraslado.getTipoFactor().equals(CTipoFactor.EXENTO))) {
            comprobanteTraslados.getTraslado().add(comprobanteTraslado);
        }

        return traslado;
    }

    @Override
    public void crearCFDIRelacionados() {
        for (CFDIFacturaRelacionadaProjection facturaRelacionada : factura.getFacturasRelacionadas()) {
            String tipoRelacion = facturaRelacionada.getTipoRelacion() != null ? facturaRelacionada.getTipoRelacion().getReferencia() : cmmDao.findComboSimpleProjectedById(facturaRelacionada.getTipoRelacionId()).getReferencia();

            boolean crearRelacion = true;

            for (Comprobante.CfdiRelacionados cfdiRelacionados : comprobante.getCfdiRelacionados()) {
                if (cfdiRelacionados.getTipoRelacion().equals(tipoRelacion)) {
                    Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado = new Comprobante.CfdiRelacionados.CfdiRelacionado();

                    cfdiRelacionado.setUUID(facturaRelacionada.getUuid());

                    cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado);

                    crearRelacion = false;
                }
            }

            if (crearRelacion) {
                Comprobante.CfdiRelacionados cfdiRelacionados = new Comprobante.CfdiRelacionados();
                Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado = new Comprobante.CfdiRelacionados.CfdiRelacionado();

                cfdiRelacionado.setUUID(facturaRelacionada.getUuid());
                cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado);
                cfdiRelacionados.setTipoRelacion(tipoRelacion);

                comprobante.getCfdiRelacionados().add(cfdiRelacionados);
            }
        }
    }

    @Override
    public void actualizarFacturasRelacionadas(int id, Map<String, Integer> facturasRelacionadas, int usuarioId) throws Exception {
        for (Map.Entry<String, Integer> facturaRelacionar : facturasRelacionadas.entrySet()) {
            CXCFactura cxcFactura = facturaDao.findById(Integer.parseInt(facturaRelacionar.getKey()));
            cxcFactura.setFacturaRelacionadaId(id);
            cxcFactura.setTipoRelacionId(facturaRelacionar.getValue());
            cxcFactura.setModificadoPorId(usuarioId);
            cxcFactura.setFechaModificacion(new Date(System.currentTimeMillis()));

            facturaDao.save(cxcFactura);
        }
    }

    @Override
    public void actualizarUUID_XML(int id, String xmlTimbrado, boolean timbrar) throws Exception {
        CXCFactura cxcFactura = facturaDao.findById(id);
        cxcFactura.setUuid(timbrar ? getUUID(xmlTimbrado) : null);
        cxcFactura.setXml(xmlTimbrado.substring(xmlTimbrado.indexOf("<cfdi:")));

        facturaDao.save(cxcFactura);
    }

    @Override
    public Map setDocumentParams(Document document, JSONObject jsonCFDI) throws Exception {
        Map params = new HashMap();

        try {
            String tipoDeComprobante = document.getFirstChild().getAttributes().getNamedItem("TipoDeComprobante").getNodeValue();
            params.put("TIPO_DE_COMPROBANTE", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("TipoDeComprobante"), "id", "descripcion", tipoDeComprobante));
        } catch (Exception ex) {
            throw new Exception("No se encontró el nodo de 'TipoDeComprobante'");
        }

        try {
            String formaPago = document.getFirstChild().getAttributes().getNamedItem("FormaPago").getNodeValue();
            params.put("FORMA_PAGO", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("FormaPago"), "id", "descripcion", formaPago));
        } catch (Exception ex) {
            throw new Exception("No se encontró el nodo de 'FormaPago'");
        }

        try {
            String metodoPago = document.getFirstChild().getAttributes().getNamedItem("MetodoPago").getNodeValue();
            params.put("METODO_PAGO", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("MetodoPago"), "id", "descripcion", metodoPago));
        } catch (Exception ex) {
            throw new Exception("No se encontró el nodo de 'MetodoPago'");
        }

        try {
            String emisorRegimenFiscal = document.getElementsByTagName("cfdi:Emisor").item(0).getAttributes().getNamedItem("RegimenFiscal").getNodeValue();
            params.put("EMISOR_REGIMEN_FISCAL", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("RegimenFiscal"), "id", "descripcion", emisorRegimenFiscal));
        } catch (Exception ex) {
            throw new Exception("No se encontró el nodo de 'RegimenFiscal'");
        }

        try {
            String receptorRegimenFiscal = document.getElementsByTagName("cfdi:Receptor").item(0).getAttributes().getNamedItem("RegimenFiscal").getNodeValue();
            params.put("RECEPTOR_REGIMEN_FISCAL", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("RegimenFiscal"), "id", "descripcion", receptorRegimenFiscal));
        } catch (Exception ex) {
            //throw new Exception("No se encontró el nodo de 'RegimenFiscal'");
        }

        try {
            String receptorUsoCFDI = document.getElementsByTagName("cfdi:Receptor").item(0).getAttributes().getNamedItem("UsoCFDI").getNodeValue();
            params.put("RECEPTOR_USO_CFDI", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("UsoCFDI"), "id", "descripcion", receptorUsoCFDI));
        } catch (Exception ex) {
            throw new Exception("No se encontró el nodo de 'UsoCFDI'");
        }

        return params;
    }

    private boolean esFacturaGlobal() {
        return factura.getTipoRegistroId() == ControlesMaestrosMultiples.CMM_CXCF_TipoRegistro.FACTURA_GLOBAL;
    }

    public Integer spInsertarFacturaNotaVenta(Integer datosFacturacionId,
                                              int usoCFDIId,
                                              int sucursalId,
                                              String ordenesVentaId,
                                              int usuarioId,
                                              Map<String, Integer> facturasRelacionadas) throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_insertarFacturaNotaVenta")
                .registerStoredProcedureParameter("datosFacturacionId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usoCFDIId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("sucursalId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ordenesVentaId", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("version", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.INOUT);

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        query.setParameter("datosFacturacionId", datosFacturacionId)
                .setParameter("usoCFDIId", usoCFDIId)
                .setParameter("sucursalId", sucursalId)
                .setParameter("ordenesVentaId", ordenesVentaId)
                .setParameter("usuarioId", usuarioId)
                .setParameter("version", environment.getProperty("environments.pixvs.facturacion-version"))
                .setParameter("id", null);

        return getId(facturasRelacionadas, query, usuarioId);
    }

    public Integer spInsertarFacturaGlobalNotaVenta(int sucursalId,
                                                    Integer periodicidadId,
                                                    Integer mesId,
                                                    Integer anio,
                                                    String ordenesVentaId,
                                                    int usuarioId,
                                                    Map<String, Integer> facturasRelacionadas) throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_insertarFacturaGlobalNotaVenta")
                .registerStoredProcedureParameter("sucursalId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("periodicidadId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("mesId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("anio", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ordenesVentaId", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("version", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.INOUT);

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        query.setParameter("sucursalId", sucursalId)
                .setParameter("periodicidadId", periodicidadId)
                .setParameter("mesId", mesId)
                .setParameter("anio", anio)
                .setParameter("ordenesVentaId", ordenesVentaId)
                .setParameter("usuarioId", usuarioId)
                .setParameter("version", environment.getProperty("environments.pixvs.facturacion-version"))
                .setParameter("id", null);

        return getId(facturasRelacionadas, query, usuarioId);
    }

    public Integer spInsertarFacturaMiscelanea(int formaPagoId,
                                               int diasCredito,
                                               int monedaId,
                                               int metodoPagoId,
                                               int usoCFDIId,
                                               Integer datosFacturacionId,
                                               int sucursalId,
                                               int usuarioId,
                                               Map<String, Integer> facturasRelacionadas) throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_insertarFacturaMiscelanea")
                .registerStoredProcedureParameter("formaPagoId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("diasCredito", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("monedaId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("metodoPagoId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usoCFDIId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("datosFacturacionId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("sucursalId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("version", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.INOUT);

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        query.setParameter("formaPagoId", formaPagoId)
                .setParameter("diasCredito", diasCredito)
                .setParameter("monedaId", monedaId)
                .setParameter("metodoPagoId", metodoPagoId)
                .setParameter("usoCFDIId", usoCFDIId)
                .setParameter("datosFacturacionId", datosFacturacionId)
                .setParameter("sucursalId", sucursalId)
                .setParameter("usuarioId", usuarioId)
                .setParameter("version", environment.getProperty("environments.pixvs.facturacion-version"))
                .setParameter("id", null);

        return getId(facturasRelacionadas, query, usuarioId);
    }
}