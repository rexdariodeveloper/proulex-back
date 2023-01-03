package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloFamiliaDao;
import com.pixvs.main.models.ArticuloFamilia;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 23/06/2020.
 */
@RestController
@RequestMapping("/api/v1/articulos-familias")
public class ArticuloFamiliaController {

    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<ArticuloFamiliaListadoProjection> familias = articuloFamiliaDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(familias, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ArticuloFamilia familia, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (familia.getId() == null) {
            familia.setCreadoPorId(idUsuario);
            familia.setActivo(true);
        } else {
            ArticuloFamilia objetoActual = articuloFamiliaDao.findById(familia.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), familia.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            familia.setModificadoPorId(idUsuario);
            familia.setActivo(objetoActual.getActivo());
            if (familia.getImg64() != null && familia.getArchivoId() != null) {
                fileSystemStorageService.borrarArchivo(familia.getArchivoId());
            }
        }

        if (familia.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(familia.getImg64(), idUsuario, 10, null, true, true);
            familia.setArchivoId(archivo.getId());
        }

        familia = articuloFamiliaDao.save(familia);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticuloFamilia}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticuloFamilia) throws SQLException {

        int actualizado = articuloFamiliaDao.actualizarActivo(hashId.decode(idArticuloFamilia), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
