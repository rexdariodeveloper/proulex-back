package com.pixvs.spring.controllers;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ControlMaestroMultipleService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cmm")
public class ControlMaestroMultipleController {

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private ControlMaestroMultipleService cmmService;

    @RequestMapping(value = "/control/{control}", method = RequestMethod.GET)
    public JsonResponse getControl(@PathVariable String control) {

        List<ControlMaestroMultipleComboProjection> controles = cmmDao.findAllByControlAndActivoIsTrueOrderBySistemaDescValorAsc(control);
        List<ControlMaestroMultipleComboProjection> controlesReferencia = new ArrayList<>();

        String controlReferencia = cmmDao.getCMMReferencia(control);
        if(controlReferencia != null){
            controlesReferencia = cmmDao.findAllByControlAndActivoIsTrueOrderBySistemaDescValorAsc(controlReferencia);
        }

        JSONObject listados = new JSONObject();
        listados.put("cmmReferencia",controlesReferencia);

        HashMap<String,Object> responseBody = new HashMap<>();
        responseBody.put("datos",controles);
        responseBody.put("listados",listados);

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ControlMaestroMultiple cmm, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer idReturn;

        if (cmm.getId() == null) {
//            cmm.setId(cmmDao.getSiguienteCMMId(false));
            cmm.setCreadoPorId(idUsuario);
            cmm.setActivo(true);
            if(cmm.getReferencia() == null){
                cmm.setReferencia("");
            }
            if(cmm.getCmmReferencia() != null){
                cmm.setCmmReferenciaId(cmm.getCmmReferencia().getId());
            }else{
                cmm.setCmmReferenciaId(null);
            }
            idReturn = cmmService.spInsertCMM(cmm);
        } else {
            idReturn = cmm.getId();
            ControlMaestroMultiple objetoActual = cmmDao.findCMMById(cmm.getId());
            if(objetoActual.isSistema()){
                return new JsonResponse(null, "El registro es de sistema, no se puede editar", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), cmm.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            if(cmm.getCmmReferencia() != null){
                cmm.setCmmReferenciaId(cmm.getCmmReferencia().getId());
            }else{
                cmm.setCmmReferenciaId(null);
            }
            cmm.setModificadoPorId(idUsuario);
            cmm.setActivo(objetoActual.isActivo());
            cmm = cmmDao.save(cmm);
        }

        return new JsonResponse(idReturn, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idCMM}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idCMM) throws SQLException {

        ControlMaestroMultiple objetoActual = cmmDao.findCMMById(hashId.decode(idCMM));
        if(objetoActual.isSistema()){
            return new JsonResponse(null, "El registro es de sistema, no se puede editar", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        int actualizado = cmmDao.actualizarActivo(objetoActual.getId(), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }
}
