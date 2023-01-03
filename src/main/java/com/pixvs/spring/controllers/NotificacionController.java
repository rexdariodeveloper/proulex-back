package com.pixvs.spring.controllers;

import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.Mensaje;
import com.pixvs.spring.models.NotificacionGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    public enum TipoNotificacion{
        AlertaNotificacion(1),
        NotificacionSistema(2),
        ActualizacionSistema(3),
        ActualizacionNotificaciones(4);

        private final int tipo;

        private TipoNotificacion(int tipo) {
            this.tipo = tipo;
        }
    }

    @Autowired
    private SimpMessagingTemplate template;


    @RequestMapping(value="/alerta-notificacion", method = RequestMethod.POST)
    public JsonResponse nuevaAlertaNotificacion(@RequestBody HashMap<String, Object> json) throws Exception {

        json.put("tipo",TipoNotificacion.AlertaNotificacion.tipo);

        return generalAlertas(json);
    }

    public JsonResponse nuevaAlertaNotificacion(ArrayList<Integer> usuarios, String mensaje) throws Exception {

        HashMap<String, Object> notificacion = new HashMap<>();
        notificacion.put("usuarios", usuarios);
        notificacion.put("tipo",TipoNotificacion.AlertaNotificacion.tipo);
        notificacion.put("mensaje",mensaje);

        return generalAlertas(notificacion);
    }

    @RequestMapping(value="/notificaciones", method = RequestMethod.POST)
    public JsonResponse nuevaNotificacion(@RequestBody HashMap<String, Object> json) throws Exception {

        json.put("tipo",TipoNotificacion.NotificacionSistema.tipo);
        return generalAlertas(json);
    }

    public JsonResponse nuevaNotificacion(ArrayList<Integer> usuarios, String mensaje) throws Exception {

        HashMap<String, Object> notificacion = new HashMap<>();
        notificacion.put("usuarios", usuarios);
        notificacion.put("tipo",TipoNotificacion.NotificacionSistema.tipo);
        notificacion.put("mensaje",mensaje);

        return generalAlertas(notificacion);
    }

    @RequestMapping(value="/actualizaciones", method = RequestMethod.POST)
    public JsonResponse nuevaActualizacion(@RequestBody HashMap<String, Object> mensaje) throws Exception {

        mensaje.put("tipo",TipoNotificacion.ActualizacionSistema.tipo);
        return generalAlertas(mensaje);

    }

    public JsonResponse nuevaActualizacionNotificaciones(ArrayList<Integer> usuarios) throws Exception {

        HashMap<String, Object> notificacion = new HashMap<>();
        notificacion.put("usuarios", usuarios);
        notificacion.put("tipo",TipoNotificacion.ActualizacionNotificaciones.tipo);

        return generalAlertas(notificacion);

    }

    /*
    @MessageMapping("/notificaciones")
    @SendTo("/topic/general")
    public NotificacionGeneral general(Mensaje mensaje) throws Exception {
        Thread.sleep(1000); // simulated delay

        this.template.convertAndSend("/topic/general", new NotificacionGeneral(mensaje.getMensaje()));

        return new NotificacionGeneral("Hello, " + HtmlUtils.htmlEscape(mensaje.getMensaje()) + "!");
    }
    */


    @MessageMapping("/autorizaciones")
    @SendTo("/topic/general")
    public JsonResponse generalAlertas(Object datos) throws Exception {
        Thread.sleep(1000); // simulated delay

        this.template.convertAndSend("/topic/general", datos);

        return new JsonResponse(datos, "Mensaje enviado con éxito", "Notificación");
    }

}
