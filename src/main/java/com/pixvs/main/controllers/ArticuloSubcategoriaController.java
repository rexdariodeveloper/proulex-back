package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloCategoriaDao;
import com.pixvs.main.dao.ArticuloSubcategoriaDao;
import com.pixvs.main.models.ArticuloSubcategoria;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
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
@RequestMapping("/api/v1/articulos-subcategorias")
public class ArticuloSubcategoriaController {

    @Autowired
    private ArticuloSubcategoriaDao articuloSubcategoriaDao;
    @Autowired
    private ArticuloCategoriaDao articuloCategoriaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<ArticuloSubcategoriaListadoProjection> subcategorias = articuloSubcategoriaDao.findProjectedListadoAllByActivoTrue();

        List<ArticuloCategoriaComboProjection> categorias = articuloCategoriaDao.findProjectedComboAllByActivoTrue();

        JSONObject listados = new JSONObject();
        listados.put("categoria",categorias);

        JSONObject jsonData = new JSONObject();
        jsonData.put("datos",subcategorias);
        jsonData.put("listados",listados);

        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listado/combo/{categoriaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable(required = false) Integer categoriaId) throws SQLException {

        List<ArticuloSubcategoriaComboProjection> subcategorias = articuloSubcategoriaDao.findProjectedComboAllByCategoriaIdAndActivoTrue(categoriaId);

        return new JsonResponse(subcategorias, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ArticuloSubcategoria subcategoria, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (subcategoria.getId() == null) {
            subcategoria.setCreadoPorId(idUsuario);
            subcategoria.setActivo(true);
        } else {
            ArticuloSubcategoria objetoActual = articuloSubcategoriaDao.findById(subcategoria.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), subcategoria.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            subcategoria.setModificadoPorId(idUsuario);
            subcategoria.setActivo(objetoActual.getActivo());
            if (subcategoria.getImg64() != null && subcategoria.getArchivoId() != null) {
                fileSystemStorageService.borrarArchivo(subcategoria.getArchivoId());
            }
        }

        subcategoria.setCategoriaId(subcategoria.getCategoria().getId());

        if (subcategoria.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(subcategoria.getImg64(), idUsuario, 12, null, true, true);
            subcategoria.setArchivoId(archivo.getId());
        }

        subcategoria = articuloSubcategoriaDao.save(subcategoria);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idArticuloSubcategoria}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idArticuloSubcategoria) throws SQLException {

        int actualizado = articuloSubcategoriaDao.actualizarActivo(hashId.decode(idArticuloSubcategoria), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
