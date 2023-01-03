package com.pixvs.spring.util;

import com.pixvs.spring.security.PrivateKeyLoader;
import com.pixvs.spring.security.PublicKeyLoader;
import org.apache.commons.ssl.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.mail.Multipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.X509Certificate;

public class DocumentoUtil {

    final static String ALGORITHM_SIGN = "SHA256withRSA";

    public static String getCadenaOriginal(InputStream cadenaOriginalXslt, Document documento) throws Exception {

        // Aplicar las reglas del XSLT y retornar la cadena original
        Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(cadenaOriginalXslt));
        StringWriter stringWriter = new StringWriter();

        transformer.transform(new DOMSource(documento), new StreamResult(stringWriter));

        return stringWriter.toString();
    }

    public static Document convertStringToXMLDocument(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;

        //Create DocumentBuilder with default configuration
        builder = factory.newDocumentBuilder();

        //Parse the content to Document object
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

        return doc;
    }

    public static Document convertXmlObjectToDocument(Object xmlObject, Class clase) throws Exception {
        // Crear contexto JAXB e instanciar marshaller
        JAXBContext context = JAXBContext.newInstance(clase);
        Marshaller marshaller = context.createMarshaller();

        // Agregamos los prefíjos
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        marshaller.marshal(xmlObject, document);

        return document;
    }

    public static String convertJaxbObjectToXML(Object objeto, Class clase) throws Exception {

        //Create JAXB Context
        JAXBContext jaxbContext = JAXBContext.newInstance(clase);

        //Create Marshaller
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        //Required formatting??
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        //Print XML String to Console
        StringWriter sw = new StringWriter();

        //Write XML to StringWriter
        jaxbMarshaller.marshal(objeto, sw);

        //Verify XML Content
        String xmlContent = sw.toString();
        return xmlContent;

    }

    public static String firmarDigitalmente(String xml, String pathKey, String pass) throws Exception {
        return firmarDigitalmente(xml, pathKey, pass, null);
    }

    public static String firmarDigitalmente(String xml, String pathKey, String pass, String algorithmSign) throws Exception {
        byte xmlBytes[] = xml.getBytes();

        Signature signature = Signature.getInstance(algorithmSign == null ? ALGORITHM_SIGN : algorithmSign);
        PrivateKey privateKey = new PrivateKeyLoader(pathKey, pass).getKey();
        signature.initSign(privateKey);

        signature.update(xmlBytes);

        byte[] digitalSignature = signature.sign();

        //Files.write(Paths.get("digital_signature"), digitalSignature);

        return Base64.encodeBase64String(digitalSignature);

    }

    public static String firmarDigitalmente(String xml, MultipartFile pathKey, String pass, String algorithmSign) throws Exception {
        byte xmlBytes[] = xml.getBytes();

        Signature signature = Signature.getInstance(algorithmSign == null ? ALGORITHM_SIGN : algorithmSign);
        PrivateKey privateKey = new PrivateKeyLoader(pathKey.getInputStream(),pass).getKey();
        signature.initSign(privateKey);

        signature.update(xmlBytes);

        byte[] digitalSignature = signature.sign();

        //Files.write(Paths.get("digital_signature"), digitalSignature);

        return Base64.encodeBase64String(digitalSignature);

    }

    public static boolean verificaFirma(String xml, String pathCer, String firmaBase64, String algorithmSign) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {

        byte xmlBytes[] = xml.getBytes();

        Signature signature = Signature.getInstance(algorithmSign == null ? ALGORITHM_SIGN : algorithmSign);
        X509Certificate publicKey = new PublicKeyLoader(pathCer).getKey();
        signature.initVerify(publicKey);

        //byte[] xmlBytes = Files.readAllBytes(Paths.get(pathDocumento));
        signature.update(xmlBytes);

        return signature.verify(Base64.decodeBase64(firmaBase64.getBytes("UTF-8")));

    }

}