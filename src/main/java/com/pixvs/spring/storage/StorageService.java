package com.pixvs.spring.storage;

import com.pixvs.spring.models.Archivo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    Archivo store(MultipartFile file, int idUsuario, Integer idEstructuraArchivo, String subcarpeta, Integer idTipo, boolean publico, Boolean activo);

    Stream<Path> loadAll();

    Path load(String estructuraArchivoPath, String filename);

    Resource loadAsResource(String estructuraArchivoPath, String filename);

    Resource loadMultipleAsResource(List<HashMap<String,String>> datosArchivos, String nombreArchivo) throws Exception;

    //void deleteAll();

    Archivo storeBase64(String img64, int idUsuario, Integer idEstructuraArchivo, Integer idTipo, boolean publico, Boolean activo);

    Archivo store(String stringFile, String nombreOriginal, int usuarioId, Integer estructuraArchivoId) throws Exception;

    Archivo store(String stringFile, String nombreOriginal, int usuarioId, Integer estructuraArchivoId, String subcarpeta, Integer tipoId, boolean publico, boolean activo) throws Exception;
}
