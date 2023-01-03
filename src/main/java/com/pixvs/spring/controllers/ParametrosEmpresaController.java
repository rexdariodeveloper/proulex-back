package com.pixvs.spring.controllers;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboral.EmpresaDiaNoLaboralEditarProjection;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboralFijo.EmpresaDiaNoLaboralFijoEditarProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/v1/parametros-empresa")
public class ParametrosEmpresaController {

    @Autowired
    private Environment environment;

    @Autowired
    private EmpresaDiaNoLaboralDao empresaDiaNoLaboralDao;

    @Autowired
    private EmpresaDiaNoLaboralFijoDao empresaDiaNoLaboralFijoDao;

    @Autowired
    private ControlMaestroDao cmDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private SATRegimenFiscalDao regimenFiscalDao;

    @Transactional
    @RequestMapping(value = "/listados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getMenu(ServletRequest req) throws  SQLException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        JSONObject json = new JSONObject();
        ControlMaestro nombreEmpresa = cmDao.findCMByNombre("CM_NOMBRE_EMPRESA");
        ControlMaestro rfcEmpresa = cmDao.findCMByNombre("CM_RFC_EMPRESA");
        ControlMaestro precioTotal = cmDao.findCMByNombre("CM_CALCULO_PRECIO_TOTAL");
        ControlMaestro precioUnitario = cmDao.findCMByNombre("CM_CALCULO_PRECIO_UNITARIO");
        ControlMaestro plazoDiasReinscripcion = cmDao.findCMByNombre("CMA_Inscripciones_PlazoDiasReinscripcion");
        ControlMaestro sumaDiasFechaFin = cmDao.findCMByNombre("CM_SUMA_DIAS_FECHA_FIN");
        ControlMaestro regimenFiscalCM = cmDao.findCMByNombre("CM_REGIMEN_FISCAL");
        ControlMaestro directorGeneralCM = cmDao.findCMByNombre("CM_DIRECTOR_GENERAL");
        ControlMaestro directorRecursosHumanosCM = cmDao.findCMByNombre("CM_DIRECTOR_RECURSOS_HUMANOS");
        List<EmpresaDiaNoLaboralEditarProjection> diasNoLaborales = empresaDiaNoLaboralDao.findAllByActivoIsTrue();
        List<EmpresaDiaNoLaboralFijoEditarProjection> diasNoLaboralesFijos = empresaDiaNoLaboralFijoDao.findAllByActivoIsTrue();
        List<UsuarioComboProjection> responsables = usuarioDao.findResponsablesDepartamentos();

        if(regimenFiscalCM.getValor().length() != 0){
            json.put("regimenFiscal", regimenFiscalDao.findComboProjectedByCodigoAndActivoTrue(regimenFiscalCM.getValor()));
        }

        if(directorGeneralCM.getValor().length() != 0){
            json.put("directorGeneral",usuarioDao.findResponsableDepartamentoByIdAndNombre(directorGeneralCM.getValor()));
        }

        if(directorRecursosHumanosCM.getValor().length() != 0){
            json.put("directorRecursosHumanos",usuarioDao.findResponsableDepartamentoByIdAndNombre(directorRecursosHumanosCM.getValor()));
        }

        String paisIdString = cmDao.findCMByNombre("CM_EMPRESA_PAIS").getValor();
        int paisId = Integer.valueOf(paisIdString == null || paisIdString.trim().compareTo("") == 0 ? "0" : paisIdString.trim());

        json.put("razonSocial", cmDao.findCMByNombre("CM_RAZON_SOCIAL"));
        json.put("nombre",nombreEmpresa);
        json.put("rfc",rfcEmpresa);
        json.put("diasNoLaborales",diasNoLaborales);
        json.put("diasNoLaboralesFijos",diasNoLaboralesFijos);
        json.put("precioTotal",precioTotal);
        json.put("precioUnitario",precioUnitario);
        json.put("plazoDiasReinscripcion",plazoDiasReinscripcion);
        json.put("sumaDiasFechaFin",sumaDiasFechaFin);
        json.put("responsables",responsables);
        json.put("listRegimenFiscal", regimenFiscalDao.findAllComboProjectedByActivoTrue());
        json.put("paises", paisDao.findProjectedComboAllBy());
        json.put("estados", estadoDao.findProjectedComboAllByPaisId(paisId));
        json.put("datosFiscales", getDatosFiscales(req));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDiasNoLaborales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDiasNoLaborales(ServletRequest req) throws  SQLException {
        JSONObject json = new JSONObject();
        List<EmpresaDiaNoLaboralEditarProjection> diasNoLaborales = empresaDiaNoLaboralDao.findAllByActivoIsTrue();
        List<EmpresaDiaNoLaboralFijoEditarProjection> diasNoLaboralesFijos = empresaDiaNoLaboralFijoDao.findAllByActivoIsTrue();

        json.put("diasNoLaborales",diasNoLaborales);
        json.put("diasNoLaboralesFijos",diasNoLaboralesFijos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ParametrosEmpresa parametrosEmpresa, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        for(EmpresaDiaNoLaboral diaNoLaboral: parametrosEmpresa.getDiasNoLaborales()){
            if(diaNoLaboral.getId() == null){
                diaNoLaboral.setCreadoPorId(idUsuario);
                diaNoLaboral.setFechaCreacion(new Date(System.currentTimeMillis()));
                diaNoLaboral.setActivo(true);
            }else {
                diaNoLaboral.setFechaModificacion(new Date(System.currentTimeMillis()));
            }
            empresaDiaNoLaboralDao.save(diaNoLaboral);
        }

        for(EmpresaDiaNoLaboralFijo diaNoLaboralFijo: parametrosEmpresa.getDiasNoLaboralesFijos()){
            if(diaNoLaboralFijo.getId() == null){
                diaNoLaboralFijo.setCreadoPorId(idUsuario);
                diaNoLaboralFijo.setFechaCreacion(new Date(System.currentTimeMillis()));
                diaNoLaboralFijo.setActivo(true);
            }else {
                diaNoLaboralFijo.setFechaModificacion(new Date(System.currentTimeMillis()));
            }
            empresaDiaNoLaboralFijoDao.save(diaNoLaboralFijo);
        }


        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/razonSocial", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarRazonSocial(@RequestBody String razonSocial, ServletRequest req) throws Exception {
        ControlMaestro razonSocialCMM = cmDao.findCMByNombre("CM_RAZON_SOCIAL");
        razonSocialCMM.setValor(razonSocial);
        cmDao.save(razonSocialCMM);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/nombre", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarNombre(@RequestBody String nombre, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro nombreEmpresa = cmDao.findCMByNombre("CM_NOMBRE_EMPRESA");
        nombreEmpresa.setValor(nombre);
        cmDao.save(nombreEmpresa);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/rfc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarRfc(@RequestBody String rfc, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro rfcEmpresa = cmDao.findCMByNombre("CM_RFC_EMPRESA");
        rfcEmpresa.setValor(rfc);
        cmDao.save(rfcEmpresa);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/precio", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarPrecioBanderas(@RequestBody HashMap<String,String> body, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro precioTotal = cmDao.findCMByNombre("CM_CALCULO_PRECIO_TOTAL");
        ControlMaestro precioUnitario = cmDao.findCMByNombre("CM_CALCULO_PRECIO_UNITARIO");
        precioTotal.setValor(body.get("precioTotal"));
        precioUnitario.setValor(body.get("precioUnitario"));
        cmDao.save(precioTotal);
        cmDao.save(precioUnitario);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/sumaDiasFechaFin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse sumaDiasFechaFin(@RequestBody String dias, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro sumaDias = cmDao.findCMByNombre("CM_SUMA_DIAS_FECHA_FIN");
        sumaDias.setValor(dias);
        cmDao.save(sumaDias);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/plazoDiasReinscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarPlazoDiasReinscripcion(@RequestBody String plazoDiasReinscripcion, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro plazoDiasReinscripcionEmpresa = cmDao.findCMByNombre("CMA_Inscripciones_PlazoDiasReinscripcion");
        plazoDiasReinscripcionEmpresa.setValor(plazoDiasReinscripcion);
        cmDao.save(plazoDiasReinscripcionEmpresa);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/directorGeneral", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarDirectorGeneral(@RequestBody String directorGeneral, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro directorGeneralCM = cmDao.findCMByNombre("CM_DIRECTOR_GENERAL");
        directorGeneralCM.setValor(directorGeneral);
        cmDao.save(directorGeneralCM);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/directorRecursosHumanos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarDirectorRecursosHumanos(@RequestBody String directorRecursosHumanos, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        ControlMaestro directorRecursosHumanosCM = cmDao.findCMByNombre("CM_DIRECTOR_RECURSOS_HUMANOS");
        directorRecursosHumanosCM.setValor(directorRecursosHumanos);
        cmDao.save(directorRecursosHumanosCM);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/regimenFiscal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarRegimenFiscal(@RequestBody String regimenFiscal, ServletRequest req) throws Exception {
        ControlMaestro regimenFiscalCM = cmDao.findCMByNombre("CM_REGIMEN_FISCAL");

        regimenFiscalCM.setValor(regimenFiscal);

        cmDao.save(regimenFiscalCM);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/datosFiscales", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosFiscales(ServletRequest req) throws  SQLException {
        JSONObject json = new JSONObject();

        String paisIdString = cmDao.findCMByNombre("CM_EMPRESA_PAIS").getValor();
        int paisId = Integer.valueOf(paisIdString == null || paisIdString.trim().compareTo("") == 0 ? "0" : paisIdString.trim());

        String estadoIdString = cmDao.findCMByNombre("CM_EMPRESA_ESTADO").getValor();
        int estadoId = Integer.valueOf(estadoIdString == null || estadoIdString.trim().compareTo("") == 0 ? "0" : estadoIdString.trim());

        json.put("domicilio", cmDao.findCMByNombre("CM_EMPRESA_DOMICILIO").getValor());
        json.put("colonia", cmDao.findCMByNombre("CM_EMPRESA_COLONIA").getValor());
        json.put("cp", cmDao.findCMByNombre("CM_EMPRESA_CP").getValor());
        json.put("pais", paisDao.findProjectedComboById(paisId));
        json.put("estado", estadoDao.findProjectedComboById(estadoId));
        json.put("ciudad", cmDao.findCMByNombre("CM_EMPRESA_CIUDAD").getValor());
        json.put("telefono", cmDao.findCMByNombre("CM_EMPRESA_TELEFONO").getValor());
        json.put("extension", cmDao.findCMByNombre("CM_EMPRESA_EXTENSION").getValor());
        json.put("correoElectronico", cmDao.findCMByNombre("CM_EMPRESA_CORREO_ELECTRONICO").getValor());
        json.put("paginaWeb", cmDao.findCMByNombre("CM_EMPRESA_PAGINA_WEB").getValor());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/datosFiscales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarDatosDomicilioFiscal(@RequestBody HashMap<String, String> datos, ServletRequest req) throws Exception {
        ControlMaestro domicilio = cmDao.findCMByNombre("CM_EMPRESA_DOMICILIO");
        ControlMaestro colonia = cmDao.findCMByNombre("CM_EMPRESA_COLONIA");
        ControlMaestro cp = cmDao.findCMByNombre("CM_EMPRESA_CP");
        ControlMaestro pais = cmDao.findCMByNombre("CM_EMPRESA_PAIS");
        ControlMaestro estado = cmDao.findCMByNombre("CM_EMPRESA_ESTADO");
        ControlMaestro ciudad = cmDao.findCMByNombre("CM_EMPRESA_CIUDAD");
        ControlMaestro telefono = cmDao.findCMByNombre("CM_EMPRESA_TELEFONO");
        ControlMaestro extension = cmDao.findCMByNombre("CM_EMPRESA_EXTENSION");
        ControlMaestro correoElectronico = cmDao.findCMByNombre("CM_EMPRESA_CORREO_ELECTRONICO");
        ControlMaestro paginaWeb = cmDao.findCMByNombre("CM_EMPRESA_PAGINA_WEB");

        domicilio.setValor(datos.get("domicilio"));
        colonia.setValor(datos.get("colonia"));
        cp.setValor(datos.get("cp"));
        pais.setValor(datos.get("pais"));
        estado.setValor(datos.get("estado"));
        ciudad.setValor(datos.get("ciudad"));
        telefono.setValor(datos.get("telefono"));
        extension.setValor(datos.get("extension"));
        correoElectronico.setValor(datos.get("correoElectronico"));
        paginaWeb.setValor(datos.get("paginaWeb"));

        cmDao.saveAll(Arrays.asList(domicilio, colonia, cp, pais, estado, ciudad, telefono, extension, correoElectronico, paginaWeb));

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }
}
