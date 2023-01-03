package com.pixvs.main.models.cfdi.digibox;

import com.pixvs.main.models.cfdi.Comprobante;
import com.pixvs.main.models.cfdi.ObjectFactory;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "wsTimbradoSoap", targetNamespace = "http://digibox.com/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface WsTimbradoSoap {

    /**
     * @param xmlComprobante
     * @param tokenAutenticacion
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "TimbrarXML", action = "http://digibox.com/TimbrarXML")
    @WebResult(name = "TimbrarXMLResult", targetNamespace = "http://digibox.com/")
    @RequestWrapper(localName = "TimbrarXML", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarXML")
    @ResponseWrapper(localName = "TimbrarXMLResponse", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarXMLResponse")
    public String timbrarXML(
            @WebParam(name = "xmlComprobante", targetNamespace = "http://digibox.com/") String xmlComprobante,
            @WebParam(name = "tokenAutenticacion", targetNamespace = "http://digibox.com/") String tokenAutenticacion);

    /**
     * @param xmlComprobante
     * @param tokenAutenticacion
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "TimbrarXMLV2", action = "http://digibox.com/TimbrarXMLV2")
    @WebResult(name = "TimbrarXMLV2Result", targetNamespace = "http://digibox.com/")
    @RequestWrapper(localName = "TimbrarXMLV2", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarXMLV2")
    @ResponseWrapper(localName = "TimbrarXMLV2Response", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarXMLV2Response")
    public String timbrarXMLV2(
            @WebParam(name = "xmlComprobante", targetNamespace = "http://digibox.com/") String xmlComprobante,
            @WebParam(name = "tokenAutenticacion", targetNamespace = "http://digibox.com/") String tokenAutenticacion);

    /**
     * @param comprobante
     * @param tokenAutenticacion
     * @return returns digibox.timbrado.ws.TimbrarCFDIResult
     */
    @WebMethod(operationName = "TimbrarCFDI", action = "http://digibox.com/TimbrarCFDI")
    @WebResult(name = "TimbrarCFDIResult", targetNamespace = "http://www.sat.gob.mx/TimbreFiscalDigital")
    @RequestWrapper(localName = "TimbrarCFDI", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarCFDI")
    @ResponseWrapper(localName = "TimbrarCFDIResponse", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbrarCFDIResponse")
    public TimbrarCFDIResult timbrarCFDI(
            @WebParam(name = "comprobante", targetNamespace = "http://www.sat.gob.mx/cfd/3") Comprobante comprobante,
            @WebParam(name = "tokenAutenticacion", targetNamespace = "http://digibox.com/") String tokenAutenticacion);

    /**
     * Regresa un arreglo de string con 2 elementos: el xml de la factura
     * timbrada y el dato en base64 del QR en formato PNG
     *
     * @param xmlComprobante
     * @param tokenAutenticacion
     * @return returns digibox.timbrado.ws.ArrayOfString
     */
    @WebMethod(operationName = "TimbarYObtenerQR", action = "http://digibox.com/TimbarYObtenerQR")
    @WebResult(name = "TimbarYObtenerQRResult", targetNamespace = "http://digibox.com/")
    @RequestWrapper(localName = "TimbarYObtenerQR", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbarYObtenerQR")
    @ResponseWrapper(localName = "TimbarYObtenerQRResponse", targetNamespace = "http://digibox.com/", className = "digibox.timbrado.ws.TimbarYObtenerQRResponse")
    public ArrayOfString timbarYObtenerQR(
            @WebParam(name = "xmlComprobante", targetNamespace = "http://digibox.com/") String xmlComprobante,
            @WebParam(name = "tokenAutenticacion", targetNamespace = "http://digibox.com/") String tokenAutenticacion);

}