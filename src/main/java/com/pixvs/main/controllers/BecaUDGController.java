package com.pixvs.main.controllers;

import com.pixvs.main.dao.BecaUDGDao;
import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGAplicadaProjection;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGConsultaProjection;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 14/08/2021.
 */
@RestController
@RequestMapping("/api/v1/becas-udg")
public class BecaUDGController {

    @Autowired
    private BecaUDGDao becaUDGDao;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody BecaUDG becaUDG, ServletRequest req) throws Exception {

        if(becaUDG.getId() != null){
            return new JsonResponse(null,"",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        becaUDG.setProgramaIdiomaId(becaUDG.getProgramaIdioma().getId());
        becaUDG.setPaModalidadId(becaUDG.getPaModalidad().getId());
        becaUDG.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);
        becaUDG.setTipoId(becaUDG.getTipo().getId());

        becaUDGDao.save(becaUDG);

        return new JsonResponse(null,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cancelar/{becaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cancelar(@PathVariable Integer becaId, ServletRequest req) throws Exception {

        BecaUDG becaUDG = becaUDGDao.findById(becaId);

        if(becaUDG.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR){
            return new JsonResponse(null,"",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        becaUDG.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);
        becaUDGDao.save(becaUDG);

        return new JsonResponse(null,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/beca/{becaCodigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getBeca(@PathVariable String becaCodigo, ServletRequest req) throws Exception {

        BecaUDGConsultaProjection becaUDG = becaUDGDao.findProjectedConsultaByCodigoBeca(becaCodigo);

        return new JsonResponse(becaUDG,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/becas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getBecas(@RequestBody HashMap<String,String> requestBody, ServletRequest req) throws Exception {

        String fechaInicioStr = requestBody.get("fechaInicio");
        String fechaFinStr = requestBody.get("fechaFin");
        String codigoBeca = requestBody.get("codigoBeca");

        Date fechaInicio = DateUtil.parse(fechaInicioStr + " 00:00:00.0000000");
        Date fechaFin = DateUtil.parse(fechaFinStr + " 00:00:00.0000000");

        List<BecaUDGAplicadaProjection> becas;

        if(codigoBeca != null){
            becas = becaUDGDao.findProjectedAplicadaByCodigoBecaLike(codigoBeca);
        }else{
            becas = becaUDGDao.findProjectedAplicadaByFechaAplicacionGreaterThanEqualAndFechaAplicacionLessThan(fechaInicio,fechaFin);
        }

        return new JsonResponse(becas,"",JsonResponse.STATUS_OK);
    }

}
