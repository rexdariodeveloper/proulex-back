package com.pixvs.main.controllers;

import com.pixvs.main.dao.FormaPagoDao;
import com.pixvs.main.dao.MonedaDao;
import com.pixvs.main.dao.ProveedorDao;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorEditarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorListadoProjection;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.EstadoDao;
import com.pixvs.spring.dao.PaisDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private SATService satService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private FormaPagoDao formaPagoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedores() throws SQLException {

        List<ProveedorListadoProjection> proveedores = proveedorDao.findProjectedListadoAllByTipoProveedorIdIsNot(2000367);

        return new JsonResponse(proveedores, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Proveedor proveedor, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(proveedor.getTipoProveedor() != null){
            proveedor.setTipoProveedorId(proveedor.getTipoProveedor().getId());
        }else{
            proveedor.setTipoProveedorId(null);
        }
        if(proveedor.getPais() != null){
            proveedor.setPaisId(proveedor.getPais().getId());
        }else{
            proveedor.setPaisId(null);
        }
        if(proveedor.getEstado() != null){
            proveedor.setEstadoId(proveedor.getEstado().getId());
        }else{
            proveedor.setEstadoId(null);
        }
        if(proveedor.getMoneda() != null){
            proveedor.setMonedaId(proveedor.getMoneda().getId());
        }else{
            proveedor.setMonedaId(null);
        }

        boolean predeterminadoAsignado = false;
        for(ProveedorContacto contacto : proveedor.getContactos()){
            if(contacto.getPredeterminado() && predeterminadoAsignado){
                return new JsonResponse(null,"Solo se permite un contacto predeterminado",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
            predeterminadoAsignado = contacto.getPredeterminado();
            if(contacto.getTipoContacto() != null){
                contacto.setTipoContactoId(contacto.getTipoContacto().getId());
            }else{
                contacto.setTipoContactoId(null);
            }
            if(contacto.getActivo() == null){
                contacto.setActivo(true);
            }
        }

        predeterminadoAsignado = false;
        for(ProveedorFormaPago formaPago : proveedor.getFormasPago()){
            if(formaPago.getPredeterminado() && predeterminadoAsignado){
                return new JsonResponse(null,"Solo se permite una forma de pago predeterminada",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
            predeterminadoAsignado = formaPago.getPredeterminado();
            if(formaPago.getFormaPago() != null){
                formaPago.setFormaPagoId(formaPago.getFormaPago().getId());
            }else{
                formaPago.setFormaPagoId(null);
            }
            if(formaPago.getMoneda() != null){
                formaPago.setMonedaId(formaPago.getMoneda().getId());
            }else{
                formaPago.setMonedaId(null);
            }
            if(formaPago.getActivo() == null){
                formaPago.setActivo(true);
            }
        }

        if (proveedor.getId() == null) {
            if(satService.proveedorEnListaNegra(proveedor.getRfc())){
                return new JsonResponse(null, "Proveedor en lista negra", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            proveedor.setCreadoPorId(idUsuario);
            proveedor.setActivo(true);
            proveedor.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PRO"));
        } else {
            Proveedor objetoActual =proveedorDao.findById(proveedor.getId().intValue());
			try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),proveedor.getFechaModificacion());
			}catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
			}
            proveedor.setModificadoPorId(idUsuario);
        }

        proveedor = proveedorDao.save(proveedor);

        return new JsonResponse(null, "Proveedor Guardado con el código: "+proveedor.getCodigo(), JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idProveedor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idProveedor) throws SQLException {

        ProveedorEditarProjection proveedor = proveedorDao.findProjectedEditarById(idProveedor);

        return new JsonResponse(proveedor, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idProveedor}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idProveedor) throws SQLException {

        int actualizado = proveedorDao.actualizarActivo(hashId.decode(idProveedor), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        ProveedorEditarProjection proveedor = null;
        List<EstadoComboProjection> estados = new ArrayList<>();

        if (id != null) {
            proveedor = proveedorDao.findProjectedEditarById(id);
            estados = estadoDao.findProjectedComboAllByPaisId(proveedor.getPais().getId());
        }

        List<ControlMaestroMultipleComboProjection> tiposProveedor = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_PRO_TipoProveedor.NOMBRE);
        List<PaisComboProjection> paises = paisDao.findProjectedComboAllBy();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();
        List<DepartamentoComboProjection> departamentos = departamentoDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> tiposContacto = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_PROC_TipoContacto.NOMBRE);
        List<FormaPagoComboProjection> formasPago = formaPagoDao.findProjectedComboAllByActivoTrue();
        // List<ControlMaestroMultipleComboProjection> formasPago = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CXPP_FormaPago.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("proveedor", proveedor);
        json.put("tiposProveedor", tiposProveedor);
        json.put("paises", paises);
        json.put("estados", estados);
        json.put("monedas", monedas);
        json.put("departamentos", departamentos);
        json.put("tiposContacto", tiposContacto);
        json.put("formasPago", formasPago);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_PROVEEDORES]";
        String[] alColumnas = new String[]{"Activo", "Código", "Nombre", "RFC", "Fecha Creación"};

        excelController.downloadXlsx(response, "proveedores", query, alColumnas, null,"Proveedores");
    }

    @RequestMapping(value = "/verificarRfc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse verificarRfc(@RequestBody HashMap<String,Object> body) throws SQLException {
        List<Proveedor> proveedoresMismoRfc = proveedorDao.getProovedoresByRfc((String)body.get("rfc"),body.get("id") == null? 0:(Integer)body.get("id"));

        if(proveedoresMismoRfc.size() == 1 ){
            HashMap<String, Object> json = new HashMap<>();
            json.put("nombre",proveedoresMismoRfc.get(0).getNombre());
            json.put("codigo",proveedoresMismoRfc.get(0).getCodigo());
            return  new JsonResponse(json,"Un proveedor");
        }
        if(proveedoresMismoRfc.size() >1){
            return new JsonResponse(null,"Mas de un proveedor");
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


}

