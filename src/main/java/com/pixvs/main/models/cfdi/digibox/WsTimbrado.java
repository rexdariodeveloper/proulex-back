package com.pixvs.main.models.cfdi.digibox;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

@WebServiceClient(name = "wsTimbrado", targetNamespace = "http://digibox.com/", wsdlLocation = "https://timbrado.digibox.com.mx/Autenticacion/wsAutenticacion.asmx?WSDL")
public class WsTimbrado extends Service {

    // Pruebas
    private final static String wsdlTimbrado = "http://digibox2t.cloudapp.net/Timbrado/wsTimbrado.asmx?WSDL";

    // Producción
    // private final static String wsdlTimbrado = "https://timbrado.digibox.com.mx/Timbrado/wsTimbrado.asmx?WSDL";

    private final static URL WSTIMBRADO_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(WsTimbrado.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = WsTimbrado.class.getResource(".");
            url = new URL(baseUrl, wsdlTimbrado);
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '" + wsdlTimbrado + "', retrying as a local file");
            logger.warning(e.getMessage());
        }
        WSTIMBRADO_WSDL_LOCATION = url;
    }

    public WsTimbrado(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsTimbrado() {
        super(WSTIMBRADO_WSDL_LOCATION, new QName("http://digibox.com/", "wsTimbrado"));
    }

    /**
     * @return returns WsTimbradoSoap
     */
    @WebEndpoint(name = "wsTimbradoSoap")
    public WsTimbradoSoap getWsTimbradoSoap() {
        return super.getPort(new QName("http://digibox.com/", "wsTimbradoSoap"), WsTimbradoSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     *                 configure on the proxy. Supported features not in the
     *                 <code>features</code> parameter will have their default values.
     * @return returns WsTimbradoSoap
     */
    @WebEndpoint(name = "wsTimbradoSoap")
    public WsTimbradoSoap getWsTimbradoSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://digibox.com/", "wsTimbradoSoap"), WsTimbradoSoap.class, features);
    }

    /**
     * @return returns WsTimbradoSoap
     */
    @WebEndpoint(name = "wsTimbradoSoap12")
    public WsTimbradoSoap getWsTimbradoSoap12() {
        return super.getPort(new QName("http://digibox.com/", "wsTimbradoSoap12"), WsTimbradoSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     *                 configure on the proxy. Supported features not in the
     *                 <code>features</code> parameter will have their default values.
     * @return returns WsTimbradoSoap
     */
    @WebEndpoint(name = "wsTimbradoSoap12")
    public WsTimbradoSoap getWsTimbradoSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://digibox.com/", "wsTimbradoSoap12"), WsTimbradoSoap.class, features);
    }
}