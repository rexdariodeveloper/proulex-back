package com.pixvs.spring.controllers;

import java.io.IOException;
import java.util.HashMap;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

@RestController
public class InfoController {

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public JsonResponse getApiInfo() {


        return new JsonResponse(null, environment.getProperty("environments.pixvs.empresa"), "Pixvs", JsonResponse.STATUS_OK);
    }

    @GetMapping("/memory-status")
    public JsonResponse getMemoryStatistics() {

        HashMap<String, Object> json = new HashMap<>();
        json.put("heapSize", Utilidades.bytesToString(Runtime.getRuntime().totalMemory()));
        json.put("heapMaxSize", Utilidades.bytesToString(Runtime.getRuntime().maxMemory()));
        json.put("heapFreeSize",Utilidades.bytesToString(Runtime.getRuntime().freeMemory()));

        return new JsonResponse(json, null, null, JsonResponse.STATUS_OK);
    }
}