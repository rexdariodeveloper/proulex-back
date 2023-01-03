package com.pixvs.main.services;

import com.pixvs.main.models.*;
import com.pixvs.spring.dao.ArchivoDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ArchivoXMLServiceImpl implements ArchivoXMLService {

    @Autowired
    private Environment environment;

    @Autowired
    private ArchivoDao archivoDao;

    private List<String> listadoRFCAddenda = Arrays.asList("CSS160330CP7"); // {CFE}

    public JsonFacturaXML getDatosFacturaXML(Integer archivoId) throws Exception {

        ArchivoProjection archivo = archivoDao.findById(archivoId);

        JsonFacturaXML jsonFacturaXML = getDatosFacturaXML(Paths.get(environment.getProperty("spring.storage.location") + archivo.getRutaFisica()).resolve(archivo.getNombreFisico()).toFile());

        jsonFacturaXML.setId(archivoId);

        return jsonFacturaXML;

    }

    public JsonFacturaXML getDatosFacturaXML(File xml) throws Exception {

        DatosFactura datosFactura = new DatosFactura();
        DatosFacturaProveedor datosProveedor = new DatosFacturaProveedor();
        List<DatosFacturaConcepto> conceptos = new ArrayList<>();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal montoIva = BigDecimal.ZERO;
        BigDecimal montoIeps = BigDecimal.ZERO;
        BigDecimal montoDescuento = BigDecimal.ZERO;
        BigDecimal montoRetenciones = BigDecimal.ZERO;
        if (xml != null) {
            try {
                Document dom;
                DocumentBuilderFactory dbf;
                DocumentBuilder db;
                dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                //obtenemos el archivo del jfilechosser
                dom = db.parse(xml);
                //Para obtener el nodo raiz, en este caso personas, llamamos a:
                Element rootElement = dom.getDocumentElement();
//                NodeList nodeListComprobante = rootElement.getElementsByTagName("cfdi:Comprobante");
                NodeList nodeTimbreFiscalDigital = rootElement.getElementsByTagName("tfd:TimbreFiscalDigital");
                NodeList nodeListEmisor = rootElement.getElementsByTagName("cfdi:Emisor");
                NodeList nodeListConceptos = rootElement.getElementsByTagName("cfdi:Concepto");
                Node impuestosChild = rootElement.getFirstChild();
                NodeList nodeListRetenciones = null;
                while(impuestosChild != null){
                    if(impuestosChild.getNodeName().equals("cfdi:Impuestos")){
                        nodeListRetenciones = ((Element) impuestosChild).getElementsByTagName("cfdi:Retenciones");
                    }
                    impuestosChild = impuestosChild.getNextSibling();
                }

                if (rootElement.hasAttribute("Folio")) {
                    datosFactura.setFolio(rootElement.getAttribute("Folio").toUpperCase());
                }
                if (rootElement.hasAttribute("Serie")) {
                    datosFactura.setSerie(rootElement.getAttribute("Serie").toUpperCase());
                }
                if (rootElement.hasAttribute("Total")) {
                    datosFactura.setTotal(new BigDecimal(rootElement.getAttribute("Total")));
                }
                if (rootElement.hasAttribute("Fecha")) {
                    datosFactura.setFecha(dateFormatter.parse(rootElement.getAttribute("Fecha").toUpperCase()));
                }
                if (rootElement.hasAttribute("Moneda")) {
                    datosFactura.setMonedaCodigo(rootElement.getAttribute("Moneda").toUpperCase());
                }
//                    }
//                }
                if (nodeTimbreFiscalDigital != null && nodeTimbreFiscalDigital.getLength() > 0) {
                    for (int i = 0; i < nodeTimbreFiscalDigital.getLength(); i++) {
                        Element element = (Element) nodeTimbreFiscalDigital.item(i);
                        if (element.hasAttribute("UUID")) {
                            if(!element.getAttribute("UUID").toUpperCase().equals("UUID"))
                                datosFactura.setUuid(UUID.fromString(element.getAttribute("UUID").toUpperCase()));
                        }
                    }
                }
                if (nodeListEmisor != null && nodeListEmisor.getLength() > 0) {
                    for (int i = 0; i < nodeListEmisor.getLength(); i++) {
                        Element element = (Element) nodeListEmisor.item(i);
                        if (element.hasAttribute("Rfc")) {
                            datosProveedor.setRfc(element.getAttribute("Rfc").toUpperCase());
                        }
                        if (element.hasAttribute("Nombre")) {
                            datosProveedor.setNombre(element.getAttribute("Nombre").toUpperCase());
                        }
                    }
                }

                if(datosProveedor.getRfc() != null && listadoRFCAddenda.contains(datosProveedor.getRfc())){
                    NodeList nodeListAddenda = rootElement.getElementsByTagName("cfdi:Addenda");
                    if (nodeListAddenda != null && nodeListAddenda.getLength() > 0){
                        for (int i = 0; i < nodeListAddenda.getLength(); i++) {
                            Element addenda = (Element) nodeListAddenda.item(i);
                            NodeList nodeListImpTotalXML = addenda.getElementsByTagName("IMPTOTALXML");
                            if (nodeListImpTotalXML != null && nodeListImpTotalXML.getLength() > 0){
                                for (int j = 0; j < nodeListImpTotalXML.getLength(); j++) {
                                    Element impTotalXML = (Element) nodeListImpTotalXML.item(j);
                                    datosFactura.setTotal(new BigDecimal(impTotalXML.getFirstChild().getNodeValue()));
                                }
                            }
                        }
                    }
                }

                if (nodeListConceptos != null && nodeListConceptos.getLength() > 0) {
                    for (int i = 0; i < nodeListConceptos.getLength(); i++) {
                        Element element = (Element) nodeListConceptos.item(i);
                        NodeList nodeListImpuestos = element.getElementsByTagName("cfdi:Impuestos");
                        DatosFacturaConcepto concepto = new DatosFacturaConcepto();
                        BigDecimal subtotalDetalle = BigDecimal.ZERO;
                        BigDecimal montoIvaDetalle = BigDecimal.ZERO;
                        BigDecimal montoIepsDetalle = BigDecimal.ZERO;
                        BigDecimal montoDescuentoDetalle = BigDecimal.ZERO;

                        if(nodeListImpuestos != null && nodeListImpuestos.getLength() > 0){
                            for (int j = 0; j < nodeListImpuestos.getLength(); j++) {
                                Element elementImpuesto = (Element) nodeListImpuestos.item(j);
                                NodeList nodeListImpuestoTraslados = element.getElementsByTagName("cfdi:Traslado");
                                if(nodeListImpuestoTraslados != null && nodeListImpuestoTraslados.getLength() > 0){
                                    for (int k = 0; k < nodeListImpuestoTraslados.getLength(); k++) {
                                        Element elementImpuestoTraslado = (Element) nodeListImpuestoTraslados.item(k);
                                        String tipoFactor = elementImpuestoTraslado.getAttribute("TipoFactor");
                                        if(!tipoFactor.equals("Exento")){
                                            if(elementImpuestoTraslado.hasAttribute("Impuesto") && elementImpuestoTraslado.getAttribute("Impuesto").equals("002")){
                                                DatosFacturaImpuesto iva = new DatosFacturaImpuesto();
                                                montoIvaDetalle = montoIvaDetalle.add(new BigDecimal(elementImpuestoTraslado.getAttribute("Importe")));
                                                iva.setImpuesto(elementImpuestoTraslado.getAttribute("Impuesto"));
                                                iva.setTasaOCuota(new BigDecimal(elementImpuestoTraslado.getAttribute("TasaOCuota")));
                                                iva.setImporte(new BigDecimal(elementImpuestoTraslado.getAttribute("Importe")));
                                                concepto.setIva(iva);
                                            }else if(elementImpuestoTraslado.hasAttribute("Impuesto") && elementImpuestoTraslado.getAttribute("Impuesto").equals("003")){
                                                DatosFacturaImpuesto ieps = new DatosFacturaImpuesto();
                                                montoIepsDetalle = montoIepsDetalle.add(new BigDecimal(elementImpuestoTraslado.getAttribute("Importe")));
                                                ieps.setImpuesto(elementImpuestoTraslado.getAttribute("Impuesto"));
                                                ieps.setTasaOCuota(new BigDecimal(elementImpuestoTraslado.getAttribute("TasaOCuota")));
                                                ieps.setImporte(new BigDecimal(elementImpuestoTraslado.getAttribute("Importe")));
                                                concepto.setIeps(ieps);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (element.hasAttribute("Descripcion")) {
                            concepto.setConcepto(element.getAttribute("Descripcion").toUpperCase());
                        }
                        if (element.hasAttribute("Unidad")) {
                            concepto.setUm(element.getAttribute("Unidad").toUpperCase());
                        }
                        if (element.hasAttribute("ClaveUnidad")) {
                            concepto.setUmClave(element.getAttribute("ClaveUnidad").toUpperCase());
                        }
                        if (element.hasAttribute("Cantidad")) {
                            concepto.setCantidad(new BigDecimal(element.getAttribute("Cantidad").toUpperCase()));
                        }
                        if (element.hasAttribute("ValorUnitario")) {
                            concepto.setPrecioUnitario(new BigDecimal(element.getAttribute("ValorUnitario").toUpperCase()));
                        }
                        if (element.hasAttribute("Descuento")) {
                            montoDescuentoDetalle = montoDescuentoDetalle.add(new BigDecimal(element.getAttribute("Descuento").toUpperCase()));
                            concepto.setDescuento(montoDescuentoDetalle);
                        }
                        if (element.hasAttribute("Importe")) {
                            subtotalDetalle = subtotalDetalle.add(new BigDecimal(element.getAttribute("Importe").toUpperCase()));
                            concepto.setImporte(subtotalDetalle);
                        }
                        conceptos.add(concepto);

                        subtotal = subtotal.add(subtotalDetalle);
                        montoIva = montoIva.add(montoIvaDetalle);
                        montoIeps = montoIeps.add(montoIepsDetalle);
                        montoDescuento = montoDescuento.add(montoDescuentoDetalle);
                    }
                }
                if(nodeListRetenciones != null && nodeListRetenciones.getLength() > 0){
                    for (int j = 0; j < nodeListRetenciones.getLength(); j++) {
                        Element elementRetenciones = (Element) nodeListRetenciones.item(j);
                        NodeList retenciones = elementRetenciones.getElementsByTagName("cfdi:Retencion");
                        for (int k = 0; k < retenciones.getLength(); k++) {
                            Element elementRetencion = (Element) retenciones.item(k);
                            if (elementRetencion.hasAttribute("Importe")) {
                                montoRetenciones = montoRetenciones.add(new BigDecimal(elementRetencion.getAttribute("Importe").toUpperCase()));
                            }
                        }}
                }
            } catch (Exception ex) {
                throw new AdvertenciaException("Error al tratar de leer el XML.");
            }
        }

        datosFactura.setSubtotal(subtotal);
        datosFactura.setMontoIva(montoIva);
        datosFactura.setMontoIeps(montoIeps);
        datosFactura.setMontoDescuento(montoDescuento);
        datosFactura.setMontoRetenciones(montoRetenciones);

        JsonFacturaXML jsonFactura = new JsonFacturaXML();
        jsonFactura.setDatosFactura(datosFactura);
        jsonFactura.setProveedor(datosProveedor);
        jsonFactura.setConceptos(conceptos);

        return jsonFactura;
    }

}
