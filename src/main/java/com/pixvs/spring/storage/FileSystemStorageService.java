package com.pixvs.spring.storage;

import com.pixvs.spring.dao.ArchivoDao;
import com.pixvs.spring.dao.ArchivoEstructuraCarpetaDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ArchivoEstructuraCarpeta.ArchivoEstructuraCarpetaProjection;
import com.pixvs.spring.util.BASE64DecodedMultipartFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileSystemStorageService implements StorageService {

    @Autowired
    private Environment environment;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private ArchivoEstructuraCarpetaDao archivoEstructuraCarpetaDao;

    public Archivo storeBase64(String img64, int idUsuario, Integer idEstructuraArchivo, Integer idTipo, boolean publico, Boolean activo) {

        MultipartFile file = base64ToMultipart(img64);

        return store(file, idUsuario, idEstructuraArchivo, null, idTipo, publico, activo);
    }

    public void borrarArchivo(int idArchivo) {
        try {
            String rutaFisica = environment.getProperty("spring.storage.location");
            ArchivoProjection archivo = archivoDao.findById(idArchivo);
            FileSystemUtils.deleteRecursively(new File(rutaFisica + archivo.getRutaFisica() + File.separator + archivo.getNombreFisico()));

        } catch (Exception ex) {
            System.out.println("Error al eliminar archivo : " + idArchivo);
        }
    }


    @Override
    public Archivo store(MultipartFile file, int idUsuario, Integer idEstructuraArchivo, String subcarpeta, Integer idTipo, boolean publico, Boolean activo) {
        String nombreOriginal = StringUtils.cleanPath(file.getOriginalFilename());


        UUID uuid = UUID.randomUUID();

        if (nombreOriginal == null || nombreOriginal.isEmpty()) {
            nombreOriginal = uuid.toString() + ".png";
        }

        String filename = uuid.toString() + '.' + FilenameUtils.getExtension(nombreOriginal);
        String rutaFisica = environment.getProperty("spring.storage.location");

        String rutaEstructuraArchivos = "";
        try {
            ArchivoEstructuraCarpetaProjection aec = archivoEstructuraCarpetaDao.findById(idEstructuraArchivo);
            rutaEstructuraArchivos = getRutaEstructuraArticulo(aec);

            if(subcarpeta != null && !subcarpeta.equalsIgnoreCase("null")){
                rutaEstructuraArchivos += File.separator + subcarpeta;
            }

            File directory = new File(rutaFisica + rutaEstructuraArchivos);
            if (!directory.exists()) {
                directory.mkdirs();
            }

        } catch (Exception ex) {
            throw ex;
        }

        try {
            if (!file.getClass().equals(BASE64DecodedMultipartFile.class) && file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(rutaFisica + rutaEstructuraArchivos).resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        try {
            Archivo archivo = new Archivo(nombreOriginal, filename, idEstructuraArchivo, idTipo, rutaEstructuraArchivos, publico, idUsuario, activo);
            return archivoDao.save(archivo);
        } catch (Exception ex) {
            FileSystemUtils.deleteRecursively(new File(rutaFisica + rutaEstructuraArchivos + File.separator + filename));
            throw ex;
        }
    }

    @Override
    public Archivo store(String stringFile, String nombreOriginal, int usuarioId, Integer estructuraArchivoId) throws Exception {
        return store(stringFile, nombreOriginal, usuarioId, estructuraArchivoId, null, null, false, true);
    }

    @Override
    public Archivo store(String stringFile, String nombreOriginal, int usuarioId, Integer estructuraArchivoId, String subcarpeta, Integer tipoId, boolean publico, boolean activo) throws Exception {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + '.' + FilenameUtils.getExtension(nombreOriginal);
        String rutaFisica = environment.getProperty("spring.storage.location");
        String rutaEstructuraArchivos;

        rutaEstructuraArchivos = getRutaEstructuraArticulo(archivoEstructuraCarpetaDao.findById(estructuraArchivoId)) + File.separator + (subcarpeta != null ? subcarpeta : "");

        File directory = new File(rutaFisica + rutaEstructuraArchivos);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(rutaFisica + rutaEstructuraArchivos + fileName);
        FileWriter fileWriter = new FileWriter(file.getPath());
        fileWriter.write(stringFile);
        fileWriter.close();

        try {
            Archivo archivo = new Archivo(nombreOriginal, fileName, estructuraArchivoId, tipoId, rutaEstructuraArchivos, publico, usuarioId, activo);

            return archivoDao.save(archivo);
        } catch (Exception ex) {
            FileSystemUtils.deleteRecursively(new File(rutaFisica + rutaEstructuraArchivos + File.separator + fileName));

            throw ex;
        }
    }

    private String getRutaEstructuraArticulo(ArchivoEstructuraCarpetaProjection aec) {
        if (aec.getArchivoEstructuraCarpeta() != null)
            return getRutaEstructuraArticulo(aec.getArchivoEstructuraCarpeta()) + File.separator + aec.getNombreCarpeta();

        return File.separator + aec.getNombreCarpeta();
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(environment.getProperty("spring.storage.location")), 1)
                    .filter(path -> !path.equals(Paths.get(environment.getProperty("spring.storage.location"))))
                    .map(Paths.get(environment.getProperty("spring.storage.location"))::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String estructuraArchivoPath, String filename) {
        return Paths.get(environment.getProperty("spring.storage.location") + estructuraArchivoPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String estructuraArchivoPath, String filename) {
        try {
            Path file = load(estructuraArchivoPath, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Resource loadMultipleAsResource(List<HashMap<String, String>> datosArchivos, String nombreArchivo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(baos);

        for (HashMap<String, String> datosArchivo : datosArchivos) {
            String estructuraArchivoPath = datosArchivo.get("estructuraArchivoPath");
            String filename = datosArchivo.get("filename");
            String nombreOriginal = datosArchivo.get("nombreOriginal");

            Path file = load(estructuraArchivoPath, filename);
            byte[] fileByteArray = FileUtils.readFileToByteArray(file.toFile());

            ZipEntry entry;

            if (nombreOriginal != null) {
                entry = new ZipEntry(nombreOriginal);
            } else {
                entry = new ZipEntry(filename);
            }

            entry.setSize(fileByteArray.length);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(fileByteArray);
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();

        File f = new File(environment.getProperty("spring.storage.location") + "/tmp");
        FileUtils.writeByteArrayToFile(f, baos.toByteArray());

        new UrlResource(f.toURI());

        Resource resource = new UrlResource(f.toURI());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("El sistema no puede leer el archivo " + nombreArchivo);
        }
    }

    /*
    @Override
    public void deleteAll() {
        //FileSystemUtils.deleteRecursively(rootLocation.toFile());
        System.out.println("delete");
    }
*/
    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(environment.getProperty("spring.storage.location")));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }


    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}



