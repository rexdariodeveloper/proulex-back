package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.dao.DocumentosConfiguracionRHDao;
import com.pixvs.main.models.DocumentosConfiguracionRH;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.DocumentosConfiguracionRH.DocumentosConfiguracionRHEditarProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/configuracion-documentos-rh")
public class ConfiguracionDocumentosRHController {

    @Autowired
    private DocumentosConfiguracionRHDao documentosConfiguracionRHDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @RequestMapping(value = "/listados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados() throws SQLException {

        List<DocumentosConfiguracionRHEditarProjection> listaDocumentosConfiguracionRH = documentosConfiguracionRHDao.findAllBy();

        List<ControlMaestroMultipleComboProjection> listaTipoProcesoRH = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_RH_TipoProcesoRH.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoContrato = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ENT_TipoContrato.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoDocumento = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_GEN_TipoDocumento.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoOpcion = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_GEN_TipoOpcion.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoVigencia = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_GEN_TipoVigencia.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoTiempo = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_UMT_TipoTiempo.NOMBRE);


        HashMap<String, Object> json = new HashMap<>();

        json.put("listaDocumentosConfiguracionRH", listaDocumentosConfiguracionRH);
        json.put("listaTipoProcesoRH", listaTipoProcesoRH);
        json.put("listaTipoContrato", listaTipoContrato);
        json.put("listaTipoDocumento", listaTipoDocumento);
        json.put("listaTipoOpcion", listaTipoOpcion);
        json.put("listaTipoVigencia", listaTipoVigencia);
        json.put("listaTipoTiempo", listaTipoTiempo);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Map<String, Object> json, ServletRequest req) throws Exception {

        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        //Mapper Convert Object to Model
        ObjectMapper mapper = new ObjectMapper();

        DocumentosConfiguracionRH[] listaDocumentosConfiguracionRH = mapper.convertValue(json.get("listaDocumentosConfiguracionRH"), DocumentosConfiguracionRH[].class);
        for(DocumentosConfiguracionRH documentosConfiguracionRH : listaDocumentosConfiguracionRH){

            if (documentosConfiguracionRH.getId() > 0) {
                documentosConfiguracionRH.setFechaUltimaModificacion(new Date());
                documentosConfiguracionRH.setModificadoPorId(usuarioId);
            }
            else {
                documentosConfiguracionRH.setCreadoPorId(usuarioId);
                documentosConfiguracionRH.setFechaCreacion(new Date());
            }

            documentosConfiguracionRH = (DocumentosConfiguracionRH) documentosConfiguracionRHDao.save(documentosConfiguracionRH);

            if(documentosConfiguracionRH == null)
                return new JsonResponse(null,"Configuracion Documentos RH no se pudo guardar.",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listado/combo/{tipoProcesoRHId}/{tipoContratoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo(@PathVariable("tipoProcesoRHId") Integer tipoProcesoRHId, @PathVariable("tipoContratoId") Integer tipoContratoId ) {

        List<DocumentosConfiguracionRHEditarProjection> listaDocumentosConfiguracionRH = documentosConfiguracionRHDao.findAllByTipoProcesoRHIdAndTipoContratoId(tipoProcesoRHId, tipoContratoId);

        return new JsonResponse(listaDocumentosConfiguracionRH, null, JsonResponse.STATUS_OK);
    }
}
