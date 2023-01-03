package com.pixvs.main.services;

import com.pixvs.main.dao.CXCFacturaDao;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.cfdi.*;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaPagoProjection;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaProjection;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleProjection;
import com.pixvs.main.models.projections.CXCPago.CFDIPagoProjection;
import com.pixvs.main.models.projections.CXCPagoDetalle.CFDIPagoDetalleProjection;
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
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComplementoPagoService extends FacturacionService {

    @Autowired
    private CXCFacturaDao facturaDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    private CFDIFacturaPagoProjection factura;
    private CFDIPagoProjection pagoFactura;

    @Override
    public String facturar(int id, boolean timbrar) throws Exception {
        // Obtenemos los datos de la factura
        factura = facturaDao.findCFDIFacturaPagoProjectedById(id);
        pagoFactura = factura.getPago();

        // Regresamos el XML
        return getXMLTimbrado(id, timbrar);
    }

    @Override
    public Comprobante crearComprobante() throws Exception {
        // Inicializamos el comprobante
        comprobante = new Comprobante();

        // Construir el comprobante
        comprobante.setTipoDeComprobante(CTipoDeComprobante.P);
        comprobante.setVersion(factura.getVersion());
        comprobante.setSerie(factura.getSerie());
        comprobante.setFolio(factura.getFolio());
        comprobante.setFecha(DatatypeFactory.newInstance().newXMLGregorianCalendar(getStringFecha(factura.getFecha())));
        comprobante.setMoneda(CMoneda.XXX);
        comprobante.setLugarExpedicion(factura.getEmisorCP());
        comprobante.setSubTotal(BigDecimal.ZERO);
        comprobante.setTotal(BigDecimal.ZERO);
        comprobante.setEmisor(crearEmisor(factura.getEmisorRFC(), factura.getEmisorRazonSocial(), factura.getEmisorRegimenFiscal().getCodigo()));
        comprobante.setReceptor(crearReceptor(factura.getReceptorRFC(), factura.getReceptorNombre(), factura.getReceptorCP(), (factura.getReceptorRegimenFiscal() == null ? null : factura.getReceptorRegimenFiscal().getCodigo()), factura.getReceptorUsoCFDI().getCodigo()));
        comprobante.setConceptos(crearConceptos());
        comprobante.setComplemento(setComplemento());

        if (esVersion4()) {
            comprobante.setExportacion("01"); //No aplica
            //comprobante.setConfirmacion(""); //Sujeto a SAT
        }

        return comprobante;
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
        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();

        concepto.setClaveProdServ(detalle.getClaveProdServ());
        concepto.setDescripcion(detalle.getDescripcion());
        concepto.setClaveUnidad(detalle.getUnidadMedida().getClaveSAT());

        concepto.setCantidad(BigDecimal.ONE);
        concepto.setValorUnitario(BigDecimal.ZERO);
        concepto.setImporte(BigDecimal.ZERO);

        if (esVersion4()) {
            concepto.setObjetoImp(detalle.getObjetoImpuesto().getReferencia());
        }

        return concepto;
    }

    @Override
    public void crearCFDIRelacionados() {

    }

    @Override
    public void actualizarFacturasRelacionadas(int id, Map<String, Integer> facturasRelacionadas, int usuarioId) throws Exception {

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
            String formaPago = document.getElementsByTagName("pago20:Pago").item(0).getAttributes().getNamedItem("FormaDePagoP").getNodeValue();
            params.put("P_FORMA_PAGO", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("FormaPago"), "id", "descripcion", formaPago));
        } catch (Exception ex) {
            try {
                String formaPago = document.getElementsByTagName("pago10:Pago").item(0).getAttributes().getNamedItem("FormaDePagoP").getNodeValue();
                params.put("P_FORMA_PAGO", Utilidades.buscarValorJSONArray((JSONArray) jsonCFDI.get("FormaPago"), "id", "descripcion", formaPago));
            } catch (Exception ex2) {
                throw new Exception("No se encontró el nodo de 'FormaPago'");
            }
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

    private Comprobante.Complemento setComplemento() throws Exception {
        // Crear complemento para agrear al comprobante
        Comprobante.Complemento complemento = new Comprobante.Complemento();

        // Creamos el nodo para los Pagos
        Pagos pagos = new Pagos();
        pagos.setVersion(pagoFactura.getVersion());

        // Creamos el nodo para los Totales
        Pagos.Totales totales = new Pagos.Totales();

        if (esVersion4()) {
            totales.setMontoTotalPagos(BigDecimal.ZERO);
        }

        // Agregamos el Pago
        Pagos.Pago pago = new Pagos.Pago();

        // Obtener el tipo de moneda
        CMoneda moneda = CMoneda.MXN;

        if (pagoFactura.getMoneda().getCodigo().equals("Euro")) {
            moneda = CMoneda.EUR;
        } else if (pagoFactura.getMoneda().getCodigo().equals("USD")) {
            moneda = CMoneda.USD;
        }

        pago.setFechaPago(DatatypeFactory.newInstance().newXMLGregorianCalendar(getStringFecha(pagoFactura.getFecha())));
        pago.setMonedaP(moneda);

        if (esVersion4() || !moneda.equals(CMoneda.MXN)) {
            pago.setTipoCambioP(moneda.equals(CMoneda.MXN) ? BigDecimal.ONE : pagoFactura.getTipoCambio()); //Sujeto a SAT
        }

        pago.setFormaDePagoP(pagoFactura.getFormaPago().getCodigo());
        pago.setNumOperacion(pagoFactura.getNoOperacion());
        pago.setMonto(BigDecimal.ZERO);

        // Añadimos los datos de las cuentas si es necesario
        if ((Arrays.asList("02", "03", "04", "05", "06", "28", "29")).contains(pago.getFormaDePagoP())) {
            String cuentaOrdenante = pagoFactura.getCuentaOrdenante();
            String cuentaOrdenanteEmisorRFC = pagoFactura.getCuentaOrdenanteEmisorRFC();
            String cuentaOrdenanteNombreBanco = pagoFactura.getCuentaOrdenanteNombreBanco();

            String cuentaBeneficiario = pagoFactura.getCuentaBeneficiario();
            String cuentaBeneficiarioEmisorRFC = pagoFactura.getCuentaBeneficiarioEmisorRFC();

            if (cuentaOrdenante != null) {
                pago.setCtaOrdenante(cuentaOrdenante);
            }

            if (cuentaOrdenanteEmisorRFC != null) {
                pago.setRfcEmisorCtaOrd(cuentaOrdenanteEmisorRFC);
            }

            if (cuentaOrdenanteNombreBanco != null) {
                pago.setNomBancoOrdExt(cuentaOrdenanteNombreBanco);
            }

            if (cuentaBeneficiario != null) {
                pago.setCtaBeneficiario(cuentaBeneficiario);
            }

            if (cuentaBeneficiarioEmisorRFC != null) {
                pago.setRfcEmisorCtaBen(cuentaBeneficiarioEmisorRFC);
            }
        }

        for (CFDIPagoDetalleProjection pagoDetalle : pagoFactura.getDetalles()) {
            // Obtenemos la factura a pagar
            CFDIFacturaProjection facturaPagar = facturaDao.findCFDIFacturaProjectedById(pagoDetalle.getDoctoRelacionadoId());

            Pagos.Pago.DoctoRelacionado doctoRelacionado = new Pagos.Pago.DoctoRelacionado();

            doctoRelacionado.setIdDocumento(facturaPagar.getUuid());
            doctoRelacionado.setSerie(facturaPagar.getSerie());
            doctoRelacionado.setFolio(facturaPagar.getFolio());

            // Obtener el tipo de moneda
            CMoneda monedaDR = CMoneda.MXN;

            if (facturaPagar.getMoneda().getCodigo().equals("Euro")) {
                monedaDR = CMoneda.EUR;
            } else if (pagoFactura.getMoneda().getCodigo().equals("USD")) {
                monedaDR = CMoneda.USD;
            }

            if (esVersion4()) {
                doctoRelacionado.setEquivalenciaDR(moneda.equals(monedaDR) ? BigDecimal.ONE : pago.getTipoCambioP());
            }

            doctoRelacionado.setMonedaDR(monedaDR);
            doctoRelacionado.setNumParcialidad(BigInteger.valueOf(pagoDetalle.getNoParcialidad()));
            doctoRelacionado.setImpSaldoAnt(pagoDetalle.getImporteSaldoAnterior().setScale(2, BigDecimal.ROUND_HALF_UP));
            doctoRelacionado.setImpPagado(pagoDetalle.getImportePagado().setScale(2, BigDecimal.ROUND_HALF_UP));
            doctoRelacionado.setImpSaldoInsoluto(pagoDetalle.getImporteSaldoInsoluto().setScale(2, BigDecimal.ROUND_HALF_UP));

            // Sumamos el importe pagado a los totales
            pago.setMonto(pago.getMonto().add(doctoRelacionado.getImpPagado()));

            if (esVersion4()) {
                doctoRelacionado.setObjetoImpDR("01"); //No objeto de impuesto

                totales.setMontoTotalPagos(totales.getMontoTotalPagos().add(doctoRelacionado.getImpPagado()));
            } else {
                doctoRelacionado.setMetodoDePagoDR(CMetodoPago.fromValue(facturaPagar.getMetodoPago().getReferencia()));
            }

            // Añadimos el docto relacionado
            pago.getDoctoRelacionado().add(doctoRelacionado);
        }

        // Añadimos el Pago
        pagos.getPago().add(pago);

        if (esVersion4()) {
            // Añadimos los totales de los pagos
            pagos.setTotales(totales);
        }

        // Añadimos el Complemento al Comprobante
        complemento.getAny().add(pagos);
        comprobante.setComplemento(complemento);

        return complemento;
    }

    public Integer spInsertarComplementoPago(Integer datosFacturacionId,
                                             int sucursalId,
                                             String doctosRelacionadosId,
                                             Date fecha,
                                             int usuarioId) throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_insertarComplementoPago")
                .registerStoredProcedureParameter("datosFacturacionId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("sucursalId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("doctosRelacionadosId", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("version", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("fecha", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.INOUT);

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        query.setParameter("datosFacturacionId", datosFacturacionId)
                .setParameter("sucursalId", sucursalId)
                .setParameter("doctosRelacionadosId", doctosRelacionadosId)
                .setParameter("usuarioId", usuarioId)
                .setParameter("version", environment.getProperty("environments.pixvs.facturacion-version"))
                .setParameter("fecha", fecha)
                .setParameter("id", null);

        return getId(null, query, usuarioId);
    }

    public void spActualizarEstatusPagoFactura(int facturaId,
                                               int usuarioId) throws Exception {
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_actualizarEstatusPagoFactura")
                .registerStoredProcedureParameter("facturaId", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("usuarioId", Integer.class, ParameterMode.IN);

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        query.setParameter("facturaId", facturaId)
                .setParameter("usuarioId", usuarioId);

        query.execute();
    }
}