package com.pixvs.main.controllers;

import com.pixvs.main.dao.BancoDao;
import com.pixvs.main.models.Banco;
import com.pixvs.main.models.projections.Banco.BancoEditarProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/bancos")
public class BancosController {

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private BancoDao bancoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCXCFacturas() throws Exception {
        return new JsonResponse(bancoDao.findAllBancoListadoProjectedByIdNotNullOrderByCodigo(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();

        if (id != null) {
            BancoEditarProjection banco = bancoDao.findBancoEditarProjectedById(id);

            if (banco == null) {
                return new JsonResponse(null, "El Banco no existe.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            json.put("banco", banco);
        }

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Banco banco, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (banco.getId() == null) {
            banco.setCreadoPorId(usuarioId);
        } else {
            BancoEditarProjection objetoActual = bancoDao.findBancoEditarProjectedById(banco.getId());

            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), banco.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }

            banco.setModificadoPorId(usuarioId);
            banco.setFechaModificacion(new Date(System.currentTimeMillis()));
        }

        bancoDao.save(banco);

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }
}