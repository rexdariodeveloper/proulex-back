package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloCategoriaDao;
import com.pixvs.main.dao.ArticuloFamiliaDao;
import com.pixvs.main.models.ArticuloCategoria;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaListadoProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 25/06/2020.
 */
@RestController
@RequestMapping("/api/v1/articulos-categorias")
public class ArticuloCategoriaController {

    @Autowired
    private ArticuloCategoriaDao articuloCategoriaDao;
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
    public JsonResponse getTodo() throws SQLException {

        List<ArticuloCategoriaListadoProjection> categorias = articuloCategoriaDao.findProjectedListadoAllByActivoTrue();

        List<ArticuloFamiliaComboProjection> familias = articuloFamiliaDao.findProjectedComboAllByActivoTrue();

        JSONObject listados = new JSONObject();
        listados.put("familia",familias);

        JSONObject jsonData = new JSONObject();
        jsonData.put("datos",categorias);
        jsonData.put("listados",listados);

        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listado/combo/{familiaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable(required = false) Integer familiaId) throws SQLException {

        List<ArticuloCategoriaComboProjection> categorias = articuloCategoriaDao.findProjectedComboAllByFamiliaIdAndActivoTrue(familiaId);

        return new JsonResponse(categorias, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ArticuloCategoria categoria, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (categoria.getId() == null) {
            categoria.setCreadoPorId(idUsuario);
            categoria.setActivo(true);
        } else {
            ArticuloCategoria objetoActual = articuloCategoriaDao.findById(categoria.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), categoria.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            categoria.setModificadoPorId(idUsuario);
            categoria.setActivo(objetoActual.getActivo());
            if (categoria.getImg64() != null && categoria.getArchivoId() != null) {
                fileSystemStorageService.borrarArchivo(categoria.getArchivoId());
            }
        }

        categoria.setFamiliaId(categoria.getFamilia().getId());

        if (categoria.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(categoria.getImg64(), idUsuario, 11, null, true, true);
            categoria.setArchivoId(archivo.getId());
        }

        categoria = articuloCategoriaDao.save(categoria);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticuloCategoria}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticuloCategoria) throws SQLException {

        int actualizado = articuloCategoriaDao.actualizarActivo(hashId.decode(idArticuloCategoria), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
