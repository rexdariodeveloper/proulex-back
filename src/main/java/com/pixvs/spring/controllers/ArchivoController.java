package com.pixvs.spring.controllers;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ArchivoDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.Archivo.ArchivoDescargarProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.storage.StorageFileNotFoundException;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ArchivoController {

    @Autowired
    private final StorageService storageService;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    public ArchivoController(StorageService storageService) {
        this.storageService = storageService;
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/archivo/{idArchivo}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String idArchivo, Boolean publico) throws Exception {

        if (hashId.decode(idArchivo) == null) {
            throw new Exception("Id Incorrecto");
        }
        ArchivoProjection archivo = archivoDao.findById(hashId.decode(idArchivo));
        if ((publico != null && !archivo.getPublico()) || !archivo.getActivo()) {
            return ResponseEntity.status(401).header(null).body(null);
        }
        Resource file = storageService.loadAsResource(archivo.getRutaFisica(), archivo.getNombreFisico());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + archivo.getNombreOriginal() + "\"")
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
            .body(file);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/archivo/projection/{idArchivo}", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> getProjection(@PathVariable String idArchivo, Boolean publico) throws Exception {

        if (hashId.decode(idArchivo) == null) {
            throw new Exception("Id Incorrecto");
        }
        ArchivoProjection archivo = archivoDao.findById(hashId.decode(idArchivo));
        if ((publico != null && !archivo.getPublico()) || !archivo.getActivo()) {
            return ResponseEntity.status(401).header(null).body(null);
        }

        return ResponseEntity.ok()
                .body(new JsonResponse(archivo,null,JsonResponse.STATUS_OK));
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/archivos", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFiles(@RequestBody HashMap<String,Object> body, Boolean publico) throws Exception {

        List<String> idsArchivos = (List<String>)body.get("idsArchivos");

        if(idsArchivos.size() == 0){
            throw new Exception("No se encontraron archivos");
        }
        if(idsArchivos.size() == 1){
            return downloadFile(idsArchivos.get(0),publico);
        }

        String nombreZip = (String)body.get("nombreZip");

        List<HashMap<String,String>> datosArchivos = new ArrayList<>();

        for(String idArchivo : idsArchivos){
            Integer idArchivoDecoded = hashId.decode(idArchivo);
            if (idArchivoDecoded == null) {
                throw new Exception("Id Incorrecto");
            }

            ArchivoProjection archivo = archivoDao.findById(idArchivoDecoded);
            if ((publico != null && !archivo.getPublico()) || !archivo.getActivo()) {
                return ResponseEntity.status(401).header(null).body(null);
            }
            if ((publico != null && !archivo.getPublico()) || !archivo.getActivo()) {
                return ResponseEntity.status(401).header(null).body(null);
            }

            HashMap<String,String> datoArchivo = new HashMap<>();
            datoArchivo .put("estructuraArchivoPath",archivo.getRutaFisica());
            datoArchivo .put("filename",archivo.getNombreFisico());
            datoArchivo .put("nombreOriginal",archivo.getNombreOriginal());
            datosArchivos.add(datoArchivo);
        }


        Resource file = storageService.loadMultipleAsResource(datosArchivos,nombreZip);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + nombreZip + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .body(file);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/archivos/zip", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFilesZip(@RequestBody HashMap<String, Object> body, Boolean publico) throws Exception {
        String nombreZip = (String) body.get("nombreZip");

        Resource file = storeFilesZip(body, publico);

        if (file == null) {
            return ResponseEntity.status(401).header(null).body(null);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreZip + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .body(file);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/archivos/zip/save", method = RequestMethod.POST)
    public Resource storeFilesZip(@RequestBody HashMap<String, Object> body, Boolean publico) throws Exception {
        Map<String, String> archivos = (Map<String, String>) body.get("archivos");
        String rutaGuardar = (String) body.get("rutaGuardar");
        String nombreZip = (String) body.get("nombreZip");
        List<HashMap<String, String>> datosArchivos = new ArrayList<>();

        if (archivos.size() == 0) {
            throw new AdvertenciaException("No se encontraron archivos.");
        }

        for (Map.Entry<String, String> archivo : archivos.entrySet()) {
            Integer idArchivoDecoded = hashId.decode(archivo.getKey());

            ArchivoDescargarProjection archivoTemp = archivoDao.findDescargarProjectedById(idArchivoDecoded);

            if (!archivoTemp.getActivo()) {
                return null;
            }

            HashMap<String, String> datoArchivo = new HashMap<>();

            datoArchivo.put("estructuraArchivoPath", archivoTemp.getRutaFisica());
            datoArchivo.put("filename", archivoTemp.getNombreFisico());
            datoArchivo.put("nombreOriginal", archivo.getValue() != null && !archivo.getValue().trim().equals("") ? archivo.getValue().trim() : archivoTemp.getNombreOriginal());

            datosArchivos.add(datoArchivo);
        }

        Resource archivoZip = storageService.loadMultipleAsResource(datosArchivos, nombreZip);

        if (rutaGuardar != null) {
            Files.copy(archivoZip.getInputStream(), Paths.get(rutaGuardar).resolve(nombreZip), StandardCopyOption.REPLACE_EXISTING);
        }

        return archivoZip;
    }

    @ResponseBody
    @RequestMapping(value = "/v1/archivo/{idArchivo}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadPublicFile(@PathVariable String idArchivo) throws Exception {

        return downloadFile(idArchivo, true);
    }

    @Transactional
    @RequestMapping(value = "/api/v1/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("idEstructuraArchivo") Integer idEstructuraArchivo, @RequestParam("subcarpeta") String subcarpeta, @RequestParam("idTipo") Integer idTipo, @RequestParam("publico") Boolean publico, @RequestParam("activo") Boolean activo, ServletRequest req) {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        idTipo = idTipo > 0 ? idTipo : null;
        Archivo archivo = storageService.store(file, idUsuario, idEstructuraArchivo, subcarpeta, idTipo, publico, activo);

        return new JsonResponse(archivo.getId(), null, JsonResponse.STATUS_OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
