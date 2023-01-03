package com.pixvs.main.controllers;

import com.pixvs.main.dao.ArticuloFamiliaDao;
import com.pixvs.main.dao.DeduccionPercepcionDao;
import com.pixvs.main.dao.TabuladorDao;
import com.pixvs.main.models.ArticuloFamilia;
import com.pixvs.main.models.DeduccionPercepcion;
import com.pixvs.main.models.Tabulador;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaListadoProjection;
import com.pixvs.main.models.projections.DeduccionPercepcion.DeduccionPercepcionEditarProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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
 * Created by Angel Daniel Hernández Silva on 23/06/2020.
 */
@RestController
@RequestMapping("/api/v1/deducciones-percepciones")
public class DeduccionPercepcionController {

    @Autowired
    private DeduccionPercepcionDao deduccionPercepcionDao;

    @Autowired
    private TabuladorDao tabuladorDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<DeduccionPercepcionEditarProjection> deduccionesPercepciones = deduccionPercepcionDao.findAllByActivoIsTrue();
        List<TabuladorComboProjection> tabuladores = tabuladorDao.findComboAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> tipos = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_DEDPER_Tipo.NOMBRE);
        JSONObject listados = new JSONObject();
        listados.put("tabulador",tabuladores);
        listados.put("tipo",tipos);

        JSONObject jsonData = new JSONObject();
        jsonData.put("datos",deduccionesPercepciones);
        jsonData.put("listados",listados);
        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody DeduccionPercepcion deduccionPercepcion, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (deduccionPercepcion.getId() == null) {
            deduccionPercepcion.setCreadoPorId(idUsuario);
            deduccionPercepcion.setActivo(true);
        } else {
            deduccionPercepcion.setCreadoPorId(idUsuario);
            deduccionPercepcion.setModificadoPorId(idUsuario);
        }
        if(deduccionPercepcion.getTabulador()!=null){
            deduccionPercepcion.setTabuladorId(deduccionPercepcion.getTabulador().getId());
            deduccionPercepcion.setTabulador(null);
        }
        if(deduccionPercepcion.getTipo()!=null){
            deduccionPercepcion.setTipoId(deduccionPercepcion.getTipo().getId());
            deduccionPercepcion.setTipo(null);
        }

        deduccionPercepcion = deduccionPercepcionDao.save(deduccionPercepcion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idDeduccionPercepcion}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idDeduccionPercepcion) throws SQLException {

        int actualizado = deduccionPercepcionDao.actualizarActivo(hashId.decode(idDeduccionPercepcion), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
