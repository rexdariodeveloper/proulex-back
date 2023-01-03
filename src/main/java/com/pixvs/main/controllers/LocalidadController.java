package com.pixvs.main.controllers;

import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
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
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@RestController
@RequestMapping("/api/v1/localidades")
public class LocalidadController {

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all/{almacenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTodo(@PathVariable(required = false) Integer almacenId) throws SQLException {

        List<LocalidadListadoProjection> localidades;
        if(almacenId != null){
            localidades = localidadDao.findProjectedListadoAllByAlmacenId(almacenId);
        }else{
            localidades = localidadDao.findProjectedListadoAllBy();
        }

        JSONObject jsonData = new JSONObject();
        jsonData.put("datos",localidades);

        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getCombosByAlmecen/{almacenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombosByAlmecen(@PathVariable(required = false) Integer almacenId) throws SQLException {

        List<LocalidadComboProjection> localidades = localidadDao.findComboProjectedListadoAllByAlmacenIdAndActivoTrue(almacenId);
        JSONObject jsonData = new JSONObject();
        jsonData.put("datos",localidades);

        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Localidad localidad, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (localidad.getId() == null) {
            localidad.setCreadoPorId(idUsuario);
            if(localidad.getActivo() == null){
                localidad.setActivo(true);
            }
        } else {
            Localidad objetoActual = localidadDao.findById(localidad.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), localidad.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            localidad.setModificadoPorId(idUsuario);
        }

        Localidad localidadGeneralAlmacen = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(localidad.getAlmacenId());
        if(localidadGeneralAlmacen == null){
            localidad.setLocalidadGeneral(true);
            localidad.setNombre(localidad.getCodigoLocalidad());
        }else{
            localidad.setLocalidadGeneral(false);
        }

        localidad = localidadDao.save(localidad);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }
}
