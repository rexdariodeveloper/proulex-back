package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXPFacturaDao;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaDescargarProjection;
import com.pixvs.spring.controllers.ArchivoController;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cxpfacturas")
public class CXPFacturaController {

    @Autowired
    private Environment environment;

    @Autowired
    private HashId hashId;

    @Autowired
    private ArchivoController archivoController;

    @Autowired
    private CXPFacturaDao cxpFacturaDao;

    @ResponseBody
    @RequestMapping(value = "/descargar-archivos", method = RequestMethod.GET)
    public JsonResponse descargarDocumentos() throws Exception {
        Map<String, String> archivos = new HashMap<>();
        List<CXPFacturaDescargarProjection> facturas = cxpFacturaDao.getFacturasDescargar();

        for (CXPFacturaDescargarProjection factura : facturas) {
            if (factura.getXMLId() != null) {
                archivos.put(hashId.encode(factura.getXMLId()), factura.getPathArchivo().replace("/", File.separator) + ".xml");
            }

            if (factura.getPDFId() != null) {
                archivos.put(hashId.encode(factura.getPDFId()), factura.getPathArchivo().replace("/", File.separator) + ".pdf");
            }
        }

        try {
            HashMap<String, Object> requestBodyArchivos = new HashMap<>();
            requestBodyArchivos.put("archivos", archivos);
            requestBodyArchivos.put("nombreZip", "FacturasCXP_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".zip");
            requestBodyArchivos.put("rutaGuardar", environment.getProperty("spring.storage.location"));

            archivoController.storeFilesZip(requestBodyArchivos, null);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }
}