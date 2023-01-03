package com.pixvs.spring.handler;

import com.pixvs.PixvsApplication;
import com.pixvs.spring.handler.exceptions.*;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Email;
import com.pixvs.spring.util.SqlNullException;
import org.apache.catalina.connector.ClientAbortException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Created by PixvsChevy on 3/30/2017.
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Environment environment;

    @Autowired
    private Email email;

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        String bodyOfResponse = "";
        JsonResponse json = new JsonResponse(null, "Error", JsonResponse.STATUS_ERROR);
        Boolean excepcionSinCorreo=false;
        try {

            //ClientAbortException
            if (ex != null && ex.getClass() == ClientAbortException.class) {

                excepcionSinCorreo=true;

                json.setStatus(JsonResponse.STATUS_ERROR_CLIENT_ABORT);
                json.setMessage("Petición Abortada por el Cliente");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //IllegalStateException
            if (ex != null && ex.getClass() == IllegalStateException.class) {

                excepcionSinCorreo=true;

                json.setStatus(JsonResponse.STATUS_ERROR_CONEXION_SERVIDOR);
                json.setMessage("Error de Conexión");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //InventarioNegativoException
            if (ex != null && ex.getClass() == InventarioNegativoException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_INVENTARIO_NEGATIVO);
                json.setMessage("");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //DataIntegrityViolationException
            else if (ex != null && ex.getClass() == DataIntegrityViolationException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_SQL_INTEGRIDAD);

                json.setMessage(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //ConstraintViolationException
            else if (ex != null && ex.getClass() == ConstraintViolationException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_SQL_NULL);
                json.setMessage("");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //SqlNullException
            else if (ex != null && ex.getClass() == SqlNullException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_SQL_NULL);
                json.setMessage("");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //SqlDeleteFkException
            else if (ex != null && ex.getClass() == SqlDeleteFkException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_SQL_DELETE_FK);
                json.setMessage("");

                return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.OK, request);

            }
            //SQLGrammarException
            else if (ex != null && ex.getCause() != null && ex.getCause().getClass() == org.hibernate.exception.SQLGrammarException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_SQL);
                json.setMessage(org.hibernate.exception.SQLGrammarException.class.getSimpleName() + ", " + ex.getCause().getCause().getMessage());

            }
            // SQLTransientConnectionException: HikariPool-1 - Connection is not available
            else if(ex != null && ex.getMessage() != null && ex.getClass() == java.sql.SQLTransientConnectionException.class){
                json.setStatus(JsonResponse.STATUS_ERROR_CONEXION_SQL_SERVIDOR);
                json.setMessage(ex.getMessage());

                PixvsApplication.restart();
            }
            //JDBCConnectionException
            else if (ex != null && ex.getMessage() != null && ex.getClass() == JDBCConnectionException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_CONEXION_SERVIDOR);
                json.setMessage(ex.getMessage());

                PixvsApplication.restart();

            }
            //UsuarioException
            else if (ex != null && ex.getMessage() != null && ex.getClass() == UsuarioException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
                json.setMessage(ex.getMessage());

            }
            //InvalidMediaTypeException
            else if (ex != null && ex.getMessage() != null && ex.getClass() == InvalidMediaTypeException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_MEDIA_TYPE);
                json.setMessage(ex.getMessage());

            }
            //DropzoneException
            else if (ex != null && ex.getMessage() != null && ex.getClass() == DropzoneException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_DROPZONE);
                json.setTitle("Dropzone");
                json.setMessage(ex.getMessage());
                excepcionSinCorreo=true;

            }
            //ConcurrenciaException
            else if (ex != null && ex.getMessage() != null && ex.getClass() == ConcurrenciaException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_CONCURRENCIA);
                json.setTitle("Concurrencia");
                json.setMessage(ex.getMessage());
                excepcionSinCorreo=true;

            }
            //NullPointerException
            else if (ex != null && ex.getClass() == NullPointerException.class) {

                json.setStatus(JsonResponse.STATUS_ERROR_NULL);
                json.setMessage("Valor Nulo no permitido");

            }
            //AdvertenciaException
            else if (ex != null && ex.getClass() == AdvertenciaException.class) {
                json.setStatus(JsonResponse.STATUS_ERROR_ADVERTENCIA);
                json.setMessage(ex.getCause() != null ? (ex.getCause().getCause() != null ? ex.getCause().getCause().getMessage() : ex.getCause().getMessage()) : ex.getMessage());
            }
            //ErrorConDatosException
            else if (ex != null && ex.getClass() == ErrorConDatosException.class) {
                json.setStatus(JsonResponse.STATUS_ERROR_CON_DATOS);
                json.setMessage(ex.getCause() != null ? (ex.getCause().getCause() != null ? ex.getCause().getCause().getMessage() : ex.getCause().getMessage()) : ex.getMessage());
            }
            else if(ex != null && ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getMessage() != null){
                json.setStatus(JsonResponse.STATUS_ERROR);
                json.setMessage(ex.getCause().getClass().getSimpleName() + ", " + ex.getCause().getCause().getMessage());

            }else if(ex != null && ex.getMessage() != null){
                json.setStatus(JsonResponse.STATUS_ERROR);
                json.setMessage(ex.getClass().getSimpleName() + ", " + ex.getMessage());

            }else{
                json.setStatus(JsonResponse.STATUS_ERROR);
                json.setMessage("Error, Error Desconocido");

            }


        }catch(Exception e){
            json.setStatus(JsonResponse.STATUS_ERROR_PROBLEMA);
            json.setMessage("Problemas de conexión\n" + e.getCause().getClass().getSimpleName() + ", " + e.getCause().getCause().getMessage());

        }finally {

            String stackTrace = "";
            if(ex != null && ex.getStackTrace()!= null){
                for (StackTraceElement stack : ex.getStackTrace()){
                    stackTrace += stack+"\n";
                }
            }

            //Enviar correo si esta en Producción
            if(json.getStatus() != JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES
                    && json.getStatus() != JsonResponse.STATUS_ERROR_MEDIA_TYPE
                    && json.getStatus() != JsonResponse.STATUS_ERROR_ADVERTENCIA
                    && json.getStatus() != JsonResponse.STATUS_ERROR_INVENTARIO_NEGATIVO
                    && json.getStatus() != JsonResponse.STATUS_ERROR_CON_DATOS
            ) {
                if (environment.getActiveProfiles() != null && "production".equals(environment.getActiveProfiles()[0])) {
                    if(!excepcionSinCorreo){
                        email.sendEmail(environment.getProperty("enviroment.mail.correo_errores"), environment.getProperty("environments.pixvs.empresa")+" (" + environment.getActiveProfiles()[0] + ") Error 500", json.getMessage() + "\n\nStack:\n" + stackTrace);
                    }
                } else {
                    //COMENTAR
                    //email.sendEmail(environment.getProperty("enviroment.mail.correo_errores"), environment.getProperty("environments.pixvs.empresa")+" ("+environment.getActiveProfiles()[0]+") Error 500", json.getMessage() + "\n\nStack:\n"+stackTrace);
                    ex.printStackTrace();
                }
            }

            return handleExceptionInternal(ex, json, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

}
