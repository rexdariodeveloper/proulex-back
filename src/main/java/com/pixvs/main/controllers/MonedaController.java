package com.pixvs.main.controllers;

import com.pixvs.main.dao.MonedaDao;
import com.pixvs.main.models.Moneda;
import com.pixvs.main.models.projections.Moneda.MonedaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
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
 * Created by Angel Daniel Hernández Silva on 26/06/2020.
 */
@RestController
@RequestMapping("/api/v1/monedas")
public class MonedaController {

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<MonedaListadoProjection> unidadesMedida = monedaDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(unidadesMedida, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Moneda moneda, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (moneda.getId() == null) {
            moneda.setCreadoPorId(idUsuario);
            moneda.setActivo(true);

            if(moneda.getPredeterminada() != null && moneda.getPredeterminada()){
                Moneda predeterminada = monedaDao.findByPredeterminadaTrue();
                if(predeterminada != null){
                    return new JsonResponse("", "Ya existe una moneda marcada como predeterminada", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }
        } else {
            if(moneda.getPredeterminada() != null && moneda.getPredeterminada()){
                Moneda predeterminada = monedaDao.findByIdNotAndPredeterminadaTrue(moneda.getId());
                if(predeterminada != null){
                    return new JsonResponse("", "Ya existe una moneda marcada como predeterminada", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
            }
            Moneda objetoActual = monedaDao.findById(moneda.getId());
            if(objetoActual.getSistema()){
                return new JsonResponse("", "El registro no se puede editar debido a que es de sistema", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), moneda.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            moneda.setModificadoPorId(idUsuario);
            moneda.setActivo(objetoActual.getActivo());
        }

        if(moneda.getPredeterminada() == null){
            moneda.setPredeterminada(false);
        }
        moneda = monedaDao.save(moneda);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idMoneda}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idMoneda) throws SQLException {

        Integer id = hashId.decode(idMoneda);

        Moneda moneda = monedaDao.findById(id);

        if(moneda.getSistema()){
            return new JsonResponse(null, "El registro es de sistema, no es posible borrarlo", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        int actualizado = monedaDao.actualizarActivo(id, false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
