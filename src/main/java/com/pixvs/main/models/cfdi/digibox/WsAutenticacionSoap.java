package com.pixvs.main.models.cfdi.digibox;

import com.pixvs.main.models.cfdi.ObjectFactory;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "wsAutenticacionSoap", targetNamespace = "http://digibox.com/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface WsAutenticacionSoap {

    /**
     * @param usuario
     * @param password
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "AutenticarBasico", action = "http://digibox.com/AutenticarBasico")
    @WebResult(name = "AutenticarBasicoResult", targetNamespace = "http://digibox.com/")
    @RequestWrapper(localName = "AutenticarBasico", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.AutenticarBasico")
    @ResponseWrapper(localName = "AutenticarBasicoResponse", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.AutenticarBasicoResponse")
    public String autenticarBasico(
            @WebParam(name = "usuario", targetNamespace = "http://digibox.com/") String usuario,
            @WebParam(name = "password", targetNamespace = "http://digibox.com/") String password);

    /**
     * @param email
     * @param apiKey
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "TokenCambioContrase\u00f1a", action = "http://digibox.com/TokenCambioContrase\u00f1a")
    @WebResult(name = "TokenCambioContrase\u00f1aResult", targetNamespace = "http://digibox.com/")
    @RequestWrapper(localName = "TokenCambioContrase\u00f1a", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.TokenCambioContrase\u00f1a")
    @ResponseWrapper(localName = "TokenCambioContrase\u00f1aResponse", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.TokenCambioContrase\u00f1aResponse")
    public String tokenCambioContrasenia(
            @WebParam(name = "apiKey", targetNamespace = "http://digibox.com/") String apiKey,
            @WebParam(name = "email", targetNamespace = "http://digibox.com/") String email);

    /**
     * @param token
     * @param newPassword
     */
    @WebMethod(operationName = "CambioContrase\u00f1a", action = "http://digibox.com/CambioContrase\u00f1a")
    @RequestWrapper(localName = "CambioContrase\u00f1a", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.CambioContrase\u00f1a")
    @ResponseWrapper(localName = "CambioContrase\u00f1aResponse", targetNamespace = "http://digibox.com/", className = "digibox.autenticacion.ws.CambioContrase\u00f1aResponse")
    public void cambioContrasenia(
            @WebParam(name = "token", targetNamespace = "http://digibox.com/") String token,
            @WebParam(name = "newPassword", targetNamespace = "http://digibox.com/") String newPassword);
}