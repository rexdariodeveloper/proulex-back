package com.pixvs.spring.controllers;

import com.pixvs.spring.models.EstadisticaPagina;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.EmailService;
import com.pixvs.spring.services.EstadisticaPaginaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/v1/estadistica-pagina")
public class EstadisticaPaginaController {

    @Autowired
    private EstadisticaPaginaService estadisticaPaginaService;

    @Autowired
    EmailService emailService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private Environment environment;


    @RequestMapping(value = "/registro", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse registro(@RequestBody EstadisticaPagina estadisticaPagina, ServletRequest req) {

        if ("true".equals(environment.getProperty("environments.pixvs.estadisticas")) ) {
            estadisticaPaginaService.incrementaContadorNavegador(estadisticaPagina);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Scheduled(cron = "0 0 8 * * *")
    @RequestMapping(value = "/reporte", method = RequestMethod.GET)
    public void enviarCorreo() {

        if ("true".equals(environment.getProperty("environments.pixvs.estadisticas")) ) {

            String query = "SELECT * from [VW_REPORTE_ESTADISTICAS_PAGINAS]";
            String[] alColumnas = new String[]{"Tipo", "URL_SECCION", "URL", "IP", "Dispositivo", "OS", "OS Version", "Browser", "Fecha"};

            ByteArrayInputStream reporte = excelController.createXlsx("reporte", query, alColumnas, null);


            String correoDestino = "pixvs.server@gmail.com";
            String asunto = "Estadísticas del Sitio";
            //String correoOrigen = environment.getProperty("enviroment.mail.from");
            String mensaje = "Reporte Estadísticas Economía Jalisco Covid-19";

            emailService.sendMessageWithAttachment(correoDestino, asunto, mensaje, "reporte.xlsx", reporte);
            System.out.println("Correo enviado con éxito");

        }
    }


}
