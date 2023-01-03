package com.pixvs.main.services;

import com.forsedi.cfdi.invoice.Invoice;
import com.forsedi.cfdi.ws.timbrado.AcuseCFDI;
import com.forsedi.util.common.Constants;
import com.forsedi.util.common.UtilSignature;
import com.pixvs.main.models.cfdi.*;
import com.pixvs.main.models.cfdi.digibox.WsAutenticacion;
import com.pixvs.main.models.cfdi.digibox.WsTimbrado;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.persistence.StoredProcedureQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public abstract class FacturacionService {

    @Autowired
    public Environment environment;

    @Autowired
    public ReporteService reporteService;

    public Comprobante comprobante;
    public String usuario;
    public String password;
    public String KEY_PASS;
    public String LOCATION_KEY;
    public String LOCATION_CER;
    private String UTF_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public boolean timbrarForsedi;
    public String version;

    public abstract String facturar(int id, boolean timbrar) throws Exception;

    public abstract Comprobante crearComprobante() throws Exception;

    public abstract Comprobante.Conceptos crearConceptos() throws Exception;

    public abstract void crearCFDIRelacionados();

    public abstract void actualizarFacturasRelacionadas(int id, Map<String, Integer> facturasRelacionadas, int usuarioId) throws Exception;

    public abstract void actualizarUUID_XML(int id, String xmlTimbrado, boolean timbrar) throws Exception;

    public abstract Map setDocumentParams(Document document, JSONObject jsonCFDI) throws Exception;

    public String facturar(int id) throws Exception {
        return facturar(id, true);
    }

    public Comprobante.Emisor crearEmisor(String rfc, String nombre, String regimenFiscal) {
        // Añadir los factura del emisor
        Comprobante.Emisor emisor = new Comprobante.Emisor();

        emisor.setRfc(rfc);

        if (esVersion4() || !StringCheck.isNullorEmpty(nombre)) {
            emisor.setNombre(nombre);
        }

        emisor.setRegimenFiscal(regimenFiscal);

        return emisor;
    }

    public Comprobante.Receptor crearReceptor(String rfc, String nombre, String domicilioFiscal, String regimenFiscal, String usoCFDI) {
        // Añadir los factura del receptor
        Comprobante.Receptor receptor = new Comprobante.Receptor();

        receptor.setRfc(rfc);

        if (esVersion4() || !StringCheck.isNullorEmpty(nombre)) {
            receptor.setNombre(nombre);
        }

        if (esVersion4()) {
            receptor.setDomicilioFiscalReceptor(domicilioFiscal);
            receptor.setRegimenFiscalReceptor(regimenFiscal);
        }

        receptor.setUsoCFDI(CUsoCFDI.fromValue(usoCFDI));

        return receptor;
    }

    public String getXMLTimbrado(int id, boolean timbrar) throws Exception {
        // Obtener las propiedades
        getDataProperties();

        // Construimos el comprobante
        comprobante = crearComprobante();

        // Obtenemos el XML
        String xml;

        if (timbrar) {
            // Timbramos el XML
            xml = timbrar();
        } else {
            xml = getFormattedXML(getStringXML());
        }

        // Actualizamos el UUID y el XML en la base
        actualizarUUID_XML(id, xml, timbrar);

        // Regresamos el XML
        return xml;
    }

    public void getDataProperties() {
        // Obtenemos la versión y el PAC con el que se timbrará
        version = environment.getProperty("environments.pixvs.facturacion-version");
        timbrarForsedi = Boolean.valueOf(environment.getProperty("environments.pixvs.facturacion-timbrarForsedi"));
        String pac = timbrarForsedi ? "forsedi" : "digibox";

        // Obtener rutas del archivo .cer y .key
        usuario = environment.getProperty("environments.pixvs.facturacion-usuario-" + pac);
        password = environment.getProperty("environments.pixvs.facturacion-password-" + pac);
        KEY_PASS = environment.getProperty("environments.pixvs.facturacion-keypass");
        LOCATION_KEY = environment.getProperty("environments.pixvs.facturacion-keyfile");
        LOCATION_CER = environment.getProperty("environments.pixvs.facturacion-certfile");
    }

    public String timbrar() throws Exception {
        // Obtenemos el XML sellado
        String xml = UtilSignature.signInvoice(getStringXML(), new File(LOCATION_CER), new File(LOCATION_KEY), KEY_PASS);

        if (timbrarForsedi) {
            // Timbrar el comprobante sellado
            AcuseCFDI acuseCFDI = Invoice.send(xml, usuario, password, Constants.WS_URL_TIMBRADO_SANDOBX);

            if (acuseCFDI.getXmlTimbrado() == null) {
                String error = MatrizErrores.getError(acuseCFDI.getCodigoError());

                throw new Exception(error != null ? error : "Error no identificado.");
            }

            // Obtenemos el XML timbrado
            xml = acuseCFDI.getXmlTimbrado();
        } else {
            try {
                // Obtener token de autenticacón
                String token = new WsAutenticacion().getWsAutenticacionSoap().autenticarBasico(usuario, password);

                // Obtenemos el XML timbrado
                xml = new WsTimbrado().getWsTimbradoSoap().timbrarXMLV2(xml, token);
            } catch (Exception ex) {
                String message = ex.getMessage();

                if (message == null) {
                    throw new Exception(ex);
                } else {
                    String error = MatrizErrores.getError(message, true);

                    throw new Exception(error != null ? error : "Error no identificado.");
                }
            }
        }

        // Regresamos el XML formateado
        return getFormattedXML(xml);
    }

    public String getUUID(String xmlTimbrado) throws Exception {
        return getStringToDocument(xmlTimbrado).getElementsByTagName("tfd:TimbreFiscalDigital").item(0).getAttributes().getNamedItem("UUID").getNodeValue().toUpperCase();
    }

    public Document getComprobanteToDocument() throws Exception {
        // Crear contexto JAXB e instanciar marshaller
        JAXBContext context = JAXBContext.newInstance(Comprobante.class);
        Marshaller marshaller = context.createMarshaller();

        // Agregamos los prefíjos
        String SCHEMA_LOCATION = esVersion4() ? "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd" : "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd";
        String SCHEMA_LOCATION_PAGOS = esVersion4() ? "http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd" : "http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd";
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION + (comprobante.getTipoDeComprobante().equals(CTipoDeComprobante.P) ? " " + SCHEMA_LOCATION_PAGOS : ""));
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperImpl());
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty("com.sun.xml.bind.xmlHeaders", UTF_HEADER);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        marshaller.marshal(comprobante, document);

        return document;
    }

    public String getStringXML() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();

        trans.transform(new DOMSource(getComprobanteToDocument()), new StreamResult(os));

        return (esVersion4() ? os.toString() : (((os.toString()).replace("http://www.sat.gob.mx/cfd/4", "http://www.sat.gob.mx/cfd/3")).replace("http://www.sat.gob.mx/Pagos20", "http://www.sat.gob.mx/Pagos")).replace("pago20", "pago10"));
    }

    public String getFormattedXML(String unformattedXml) throws Exception {
        Document document = getStringToDocument(unformattedXml);

        OutputFormat format = new OutputFormat(document);
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);

        return out.toString().replace("xmlns:xsi=\"http://www.sat.gob.mx/TimbreFiscalDigital\" ", "");
    }

    public Document getStringToDocument(String in) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(in));

        return db.parse(is);
    }

    public int getId(Map<String, Integer> facturasRelacionadas, StoredProcedureQuery query, int usuarioId) throws Exception {
        query.execute();

        int id = (Integer) query.getOutputParameterValue("id");

        if (facturasRelacionadas != null) {
            actualizarFacturasRelacionadas(id, facturasRelacionadas, usuarioId);
        }

        return id;
    }

    public InputStream formatoPDF(String reporte, String xml, Map params) throws Exception {
        String reportesLocation = environment.getProperty("environments.pixvs.reportes.location") + File.separator + "assets" + File.separator;
        Locale locale = new Locale("es", "MX");
        Document document = Utilidades.StringToXml(xml, false);
        FileReader reader = new FileReader(reportesLocation + "cfdi_catalogos.json");
        JSONObject jsonCFDI = (JSONObject) new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(reader);

        if (params == null) {
            params = new HashMap();
        }

        Map documentParams = setDocumentParams(document, jsonCFDI);

        if (documentParams != null) {
            params.putAll(documentParams);
        }

        params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, locale);
        params.put(JRParameter.REPORT_LOCALE, locale);
        params.put("URL_LOGO", reportesLocation + "logo.png");

        return reporteService.generarJasperReport(reporte, params, ReporteServiceImpl.output.PDF, false);
    }

    public String getStringFecha(Date fecha) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(fecha);
    }

    public String getHeaderUTF() {
        return UTF_HEADER;
    }

    public boolean esVersion4() {
        return (version != null ? version : environment.getProperty("environments.pixvs.facturacion-version")).equals("4.0");
    }
}