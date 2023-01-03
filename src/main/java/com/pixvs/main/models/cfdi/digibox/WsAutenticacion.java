package com.pixvs.main.models.cfdi.digibox;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

@WebServiceClient(name = "wsAutenticacion", targetNamespace = "http://digibox.com/", wsdlLocation = "https://timbrado.digibox.com.mx/Autenticacion/wsAutenticacion.asmx?WSDL")
public class WsAutenticacion extends Service {

    // Pruebas
    private final static String wsdlAutenticacion = "http://digibox2t.cloudapp.net/Autenticacion/wsAutenticacion.asmx?WSDL";

    // Producción
    // private final static String wsdlAutenticacion = "https://timbrado.digibox.com.mx/Autenticacion/wsAutenticacion.asmx?WSDL";

    private final static URL WSAUTENTICACION_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(WsAutenticacion.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = WsAutenticacion.class.getResource(".");
            url = new URL(baseUrl, wsdlAutenticacion);
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '" + wsdlAutenticacion + "', retrying as a local file");
            logger.warning(e.getMessage());
        }
        WSAUTENTICACION_WSDL_LOCATION = url;
    }

    public WsAutenticacion(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsAutenticacion() {
        super(WSAUTENTICACION_WSDL_LOCATION, new QName("http://digibox.com/", "wsAutenticacion"));
    }

    /**
     * @return returns WsAutenticacionSoap
     */
    @WebEndpoint(name = "wsAutenticacionSoap")
    public WsAutenticacionSoap getWsAutenticacionSoap() {
        return super.getPort(new QName("http://digibox.com/", "wsAutenticacionSoap"), WsAutenticacionSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     *                 configure on the proxy. Supported features not in the
     *                 <code>features</code> parameter will have their default values.
     * @return returns WsAutenticacionSoap
     */
    @WebEndpoint(name = "wsAutenticacionSoap")
    public WsAutenticacionSoap getWsAutenticacionSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://digibox.com/", "wsAutenticacionSoap"), WsAutenticacionSoap.class, features);
    }

    /**
     * @return returns WsAutenticacionSoap
     */
    @WebEndpoint(name = "wsAutenticacionSoap12")
    public WsAutenticacionSoap getWsAutenticacionSoap12() {
        return super.getPort(new QName("http://digibox.com/", "wsAutenticacionSoap12"), WsAutenticacionSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     *                 configure on the proxy. Supported features not in the
     *                 <code>features</code> parameter will have their default values.
     * @return returns WsAutenticacionSoap
     */
    @WebEndpoint(name = "wsAutenticacionSoap12")
    public WsAutenticacionSoap getWsAutenticacionSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://digibox.com/", "wsAutenticacionSoap12"), WsAutenticacionSoap.class, features);
    }
}