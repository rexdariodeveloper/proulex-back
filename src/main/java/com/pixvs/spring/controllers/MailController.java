package com.pixvs.spring.controllers;

import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/enviar-correo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuarios(@RequestBody HashMap<String, String> datos) throws SQLException {
        String correoDestino = datos.get("correoDestino");
        String asunto = datos.get("asunto");
        String cuerpo = datos.get("cuerpo");
        String correoOrigen = datos.get("correoOrigen");
        String nombre = datos.get("nombre");
        String mensaje = nombre + " ha enviado el siguiente mensaje a la plataforma \n" + "" +
                cuerpo + "\n" + "Correo adjunto del usuario: " + correoOrigen;
        //email.sendHtmlEmail(correoDestino,asunto,cuerpo+". Correo adjunto:"+correoOrigen);
        emailService.sendSimpleMessage(correoDestino, asunto, mensaje);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

}
