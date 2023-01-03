package com.pixvs.main.controllers;

import com.pixvs.main.dao.EntidadBecaDao;
import com.pixvs.main.dao.ListadoPrecioDao;
import com.pixvs.main.models.EntidadBeca;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/entidades-becas")
public class EntidadBecaController {
    @Autowired

    private EntidadBecaDao entidadBecaDao;

    @Autowired
    private ListadoPrecioDao listadoPrecioDao;

    @Autowired
    private HashId hashId;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws Exception {
        JSONObject listados = new JSONObject();
        listados.put("listadoPrecio", listadoPrecioDao.findAllByActivoIsTrueOrderByCodigo());

        JSONObject jsonData = new JSONObject();
        jsonData.put("datos", entidadBecaDao.findAllByActivoIsTrueOrderByCodigo());
        jsonData.put("listados", listados);

        return new JsonResponse(jsonData, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody EntidadBeca entidadBeca, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (entidadBecaDao.existsByIdNotAndCodigoAndActivoIsTrue(entidadBeca.getId() != null ? entidadBeca.getId() : -1, entidadBeca.getCodigo())) {
            return new JsonResponse(null, "Ya existe una entidad con el mismo código.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        if (entidadBeca.getId() == null) {
            entidadBeca.setCreadoPorId(usuarioId);
            entidadBeca.setActivo(true);
        } else {
            entidadBeca.setModificadoPorId(usuarioId);
            entidadBeca.setActivo(entidadBeca.getActivo());
        }

        entidadBeca.setListadoPrecioId(entidadBeca.getListadoPrecio() != null ? entidadBeca.getListadoPrecio().getId() : null);

        return new JsonResponse(entidadBecaDao.save(entidadBeca) != null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws Exception {
        return new JsonResponse(entidadBecaDao.actualizarActivo(hashId.decode(id), false), null, JsonResponse.STATUS_OK);
    }
}