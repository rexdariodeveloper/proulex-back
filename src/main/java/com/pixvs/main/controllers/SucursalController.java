package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalEditarProjection;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.PaisDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Autonumerico;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private SucursalImpresoraFamiliaDao impresorasDao;
    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private SucursalFormasPagoDao sucursalFormasPagoDao;
    @Autowired
    private FormaPagoDao formasPagoDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private ListadoPrecioDao listadoPrecioDao;
    @Autowired
    private MedioPagoPVDao medioPagoPVDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;
    @Autowired
    private CuentaBancariaDao cuentaBancariaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private AutonumericoService autonumericoService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSucursales() throws Exception {
        return new JsonResponse(sucursalDao.findProjectedListadoAllByOrderByCodigoSucursal(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        if (id != null) {
            datos.put("sucursal", sucursalDao.findProjectedEditarById(id));
            datos.put("formasPagoSucursal", sucursalFormasPagoDao.findAllProjectedBySucursalId(id));
        }

        datos.put("paises", paisDao.findProjectedComboAllBy());
        datos.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
        datos.put("tiposSucursal", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_SUC_TipoSucursal.NOMBRE));
        datos.put("mediosPagoPV", medioPagoPVDao.findProjectedComboAllByActivoTrue());
        datos.put("almacenes", almacenDao.findProjectedComboAllByActivoTrueAndSucursalId(id));
        datos.put("cuentasBancarias", cuentaBancariaDao.findProjectedComboAllByActivoTrue());
        datos.put("listado", listadoPrecioDao.findAllByActivoIsTrueOrderByCodigo());
        datos.put("impresoras", impresorasDao.findAllProjectedBySucursalId(id));
        datos.put("tipoImpresoras", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IMP_TipoImpresion.NOMBRE));
        datos.put("familias", articuloFamiliaDao.findProjectedComboAllByActivoTrue());
        datos.put("formasPago", formasPagoDao.findProjectedComboAllByActivoTrue());
        datos.put("permisoCMA", controlMaestroDao.findCMByNombre("CMA_FuncionalidadesPV"));
        datos.put("tiposFacturaGlobal", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor("CMM_SUC_TipoFacturaGlobal"));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Sucursal sucursal, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(sucursal.getPlanteles() == null){
            sucursal.setPlanteles(new ArrayList<>());
        }
        if(sucursal.getPlantelesBandera() == null){
            sucursal.setPlantelesBandera(false);
        }
        if(sucursal.getAlmacenes() == null){
            sucursal.setAlmacenes(new ArrayList<>());
        }

        if(sucursal.getPredeterminada()){
            Sucursal sucursalMatriz;
            if(sucursal.getId() != null){
                sucursalMatriz = sucursalDao.findByIdNotAndPredeterminadaTrue(sucursal.getId());
            }else{
                sucursalMatriz = sucursalDao.findByPredeterminadaTrue();
            }
            if(sucursalMatriz != null){
                return new JsonResponse("", "Ya existe una sucursal matriz", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
        }

        if(sucursal.getResponsable() != null){
            sucursal.setResponsableId(sucursal.getResponsable().getId());
        }else{
            sucursal.setResponsableId(null);
        }

        if(sucursal.getCoordinador() != null){
            sucursal.setCoordinadorId(sucursal.getCoordinador().getId());
            sucursal.setCoordinador(null);
        }

        if(sucursal.getListadoPrecio()!=null){
            sucursal.setListadoPrecioId(sucursal.getListadoPrecio().getId());
        }else {
            sucursal.setListadoPrecio(null);
        }

        if(sucursal.getTipoSucursal() != null){
            sucursal.setTipoSucursalId(sucursal.getTipoSucursal().getId());
        }else{
            sucursal.setTipoSucursalId(null);
        }

        if(sucursal.getPais() != null){
            sucursal.setPaisId(sucursal.getPais().getId());
        }else{
            sucursal.setPaisId(null);
        }
        if(sucursal.getEstado() != null){
            sucursal.setEstadoId(sucursal.getEstado().getId());
            sucursal.setEstado(null);
        }else{
            sucursal.setEstadoId(null);
        }

        if(sucursal.getCuentaBancaria() != null){
            sucursal.setCuentaBancariaId(sucursal.getCuentaBancaria().getId());
        }else{
            sucursal.setCuentaBancariaId(null);
        }

        sucursal.setTipoFacturaGlobalId(sucursal.getTipoFacturaGlobal() != null ? sucursal.getTipoFacturaGlobal().getId() : null);

        for(SucursalPlantel plantel: sucursal.getPlanteles()){
            if(plantel.getId() == null){
                plantel.setCreadoPorId(idUsuario);
            }else {
                plantel.setModificadoPorId(idUsuario);
            }
            if(plantel.getResponsable()!=null){
                plantel.setResponsableId(plantel.getResponsable().getId());
                plantel.setResponsable(null);
            }
            if(plantel.getAlmacen()!=null){
                plantel.setAlmacenId(plantel.getAlmacen().getId());
                plantel.setAlmacen(null);
            }
            if(plantel.getLocalidad()!=null){
                plantel.setLocalidadId(plantel.getLocalidad().getId());
                plantel.setLocalidad(null);
            }
            if(plantel.getPais()!=null){
                plantel.setPaisId(plantel.getPais().getId());
                plantel.setPais(null);
            }
            if(plantel.getEstado()!=null){
                plantel.setEstadoId(plantel.getEstado().getId());
                plantel.setEstado(null);
            }
        }

        if (sucursal.getId() == null) {
            sucursal.setCreadoPorId(idUsuario);

            // Autonumerico de venta
            String ovPrefijo = "OV" + sucursal.getPrefijo();
            String ovNombre = "Orden venta " + sucursal.getNombre();
            autonumericoService.create(ovPrefijo, ovNombre);
            // Autonumerico de Devolución
            String dvPrefijo = "DV" + sucursal.getPrefijo();
            String dvNombre = "Orden devolución " + sucursal.getNombre();
            autonumericoService.create(dvPrefijo, dvNombre);
            // Autonumerico de Degustación
            String degPrefijo = "DEG" + sucursal.getPrefijo();
            String degNombre = "Orden degustación " + sucursal.getNombre();
            autonumericoService.create(degPrefijo, degNombre);

        } else {
            Sucursal objetoActual =sucursalDao.findById(sucursal.getId().intValue());
            try{
                //concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),sucursal.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            sucursal.setModificadoPorId(idUsuario);
        }
        /*if(sucursal.getAlmacenesHijos()!=null && sucursal.getAlmacenesHijos().size()>0){
            for(Almacen almacen: sucursal.getAlmacenesHijos()){
                almacen
            }
        }*/
        /*
        // hice esto ya que me mandaba error al guardar
        List<Almacen> almacenes = new ArrayList<>();;
        sucursal.setAlmacenesHijos( almacenes );
        */

        List<SucursalImpresoraFamilia> impresoras = sucursal.getImpresoras();
        List<SucursalFormasPago> formasPago = sucursal.getFormasPago();
        List<Almacen> almacenes = sucursal.getAlmacenes();

        sucursal = sucursalDao.save(sucursal);

        ControlMaestro funcionalidadPV= controlMaestroDao.findCMByNombre("CMA_FuncionalidadesPV");
        if(funcionalidadPV.getValorAsBoolean()) {

            for (SucursalImpresoraFamilia impresora : impresoras) {
                impresora.setSucursalId(sucursal.getId());
                if (impresora.getId() == null) {
                    impresora.setCreadoPorId(idUsuario);
                }
                impresora.setModificadoPorId(idUsuario);
                impresora = impresorasDao.save(impresora);
            }

            for (SucursalFormasPago formaPago : formasPago) {
                formaPago.setSucursalId(sucursal.getId());
                if (formaPago.getId() == null) {
                    formaPago.setCreadoPorId(idUsuario);
                }
                formaPago.setModificadoPorId(idUsuario);
                formaPago = sucursalFormasPagoDao.save(formaPago);
            }
        }

        for(Almacen almacen: almacenes){
            almacen = almacenDao.findById(almacen.getId());
            almacen.setSucursalId(sucursal.getId());
            if (almacen.getId() == null) {
                almacen.setCreadoPorId(idUsuario);
            }
            almacen.setModificadoPorId(idUsuario);
            almacen = almacenDao.save(almacen);
        }

        Autonumerico autonumericoFacturas = autonumericoService.buscaAutonumericoPorReferenciaIdYNombre(sucursal.getId(), "CXCFactura");

        if (autonumericoFacturas == null) {
            autonumericoFacturas = new Autonumerico();

            autonumericoFacturas.setSiguiente(1);
            autonumericoFacturas.setDigitos(6);
            autonumericoFacturas.setActivo(true);
            autonumericoFacturas.setReferenciaId(sucursal.getId());
        }

        autonumericoFacturas.setNombre("CXCFactura " + sucursal.getNombre());
        autonumericoFacturas.setPrefijo(sucursal.getPrefijo());

        Autonumerico autonumericoPagos = autonumericoService.buscaAutonumericoPorReferenciaIdYNombre(sucursal.getId(), "CXCPago");

        if (autonumericoPagos == null) {
            autonumericoPagos = new Autonumerico();

            autonumericoPagos.setSiguiente(1);
            autonumericoPagos.setDigitos(6);
            autonumericoPagos.setActivo(true);
            autonumericoPagos.setReferenciaId(sucursal.getId());
        }

        autonumericoPagos.setNombre("CXCPago " + sucursal.getNombre());
        autonumericoPagos.setPrefijo(sucursal.getPrefijo());

        autonumericoService.save(Arrays.asList(autonumericoFacturas, autonumericoPagos));

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idSucursal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idSucursal) throws SQLException {

        SucursalEditarProjection sucursal = sucursalDao.findProjectedEditarById(idSucursal);

        return new JsonResponse(sucursal, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuarioById(@PathVariable Integer idUsuario) throws SQLException {

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);

        return new JsonResponse(sucursales, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idSucursal}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idSucursal) throws SQLException {

        int actualizado = sucursalDao.actualizarActivo(hashId.decode(idSucursal), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoSucursalesById(@PathVariable(required = false) Integer id) throws SQLException {

        SucursalEditarProjection sucursal = null;
        if (id != null) {
            sucursal = sucursalDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("sucursal", sucursal);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_SUCURSALES]";
        String[] alColumnas = new String[]{"Código","Nombre","Responsable","% Comisión","Teléfono","Activo"};

        excelController.downloadXlsx(response, "sucursales", query, alColumnas, null,"Sucursales");
    }

    @RequestMapping(value = "/getPlanteles/{idSucursal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPlanteles(@PathVariable Integer idSucursal) throws SQLException {

        List<SucursalPlantelComboProjection> planteles = sucursalPlantelDao.findAllBySucursalId(idSucursal);

        return new JsonResponse(planteles, null, JsonResponse.STATUS_OK);
    }

}

