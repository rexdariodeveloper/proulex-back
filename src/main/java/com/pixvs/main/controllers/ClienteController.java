package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteEditarProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
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

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private ClienteCuentaBancariaDao cuentaBancariaDao;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private FormaPagoDao formaPagoDao;

    @Autowired
    private BancoDao bancoDao;

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private ListadoPrecioDao listadoPrecioDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private LocalidadController localidadController;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private ClienteRemisionDao clienteRemisionDao;

    @Autowired
    private PADescuentoDao paDescuentoDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private SATRegimenFiscalDao regimenFiscalDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedores() throws SQLException {

        return new JsonResponse(clienteDao.findClienteListadoProjectionAllByIdNotNull(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Cliente cliente, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        boolean predeterminadoAsignado = false;

        for (ClienteContacto contacto : cliente.getContactos()) {
            if (contacto.isPredeterminado() && predeterminadoAsignado) {
                return new JsonResponse(null, "Solo se permite un contacto predeterminado", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }

            predeterminadoAsignado = contacto.isPredeterminado();
        }

        for (ClienteCuentaBancaria cuentaBancaria : cliente.getCuentasBancarias()) {
            if (!cuentaBancaria.isActivo()) {
                cuentaBancariaDao.deleteById(cuentaBancaria.getId());
            }
        }

        cliente.setActivo(true);

        if (cliente.getId() == null) {
            cliente.setCreadoPorId(usuarioId);
            cliente.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("CLI"));
        } else {
            Cliente objetoActual = clienteDao.findClienteById(cliente.getId());

            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), cliente.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }

            cliente.setModificadoPorId(usuarioId);
        }

        for(Almacen almacen : cliente.getAlmacenesConsignacion()){
            if(almacen.getId() == null){
                almacen.setCreadoPorId(usuarioId);
                almacen.setTipoAlmacenId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen.NORMAL);
            }else{
                Almacen objetoActual = almacenDao.findById(almacen.getId());
                almacen.setTipoAlmacenId(objetoActual.getTipoAlmacenId());
                almacen.setUsuariosPermisos(objetoActual.getUsuariosPermisos());
            }
            almacen.setSucursalId(almacen.getSucursal().getId());
            almacen.setResponsableId(almacen.getResponsable().getId());
            almacen.setPaisId(almacen.getPais().getId());
            almacen.setEstadoId(almacen.getEstado().getId());
            almacen.setLocalidadesBandera(false);
        }

        for (ClienteDatosFacturacion facturacion : cliente.getFacturacion()) {
            facturacion.getDatosFacturacion().setTipoPersonaId(facturacion.getDatosFacturacion().getTipoPersona().getId());
            facturacion.getDatosFacturacion().setPaisId(facturacion.getDatosFacturacion().getPais() != null ? facturacion.getDatosFacturacion().getPais().getId() : null);
            facturacion.getDatosFacturacion().setEstadoId(facturacion.getDatosFacturacion().getEstado() != null ? facturacion.getDatosFacturacion().getEstado().getId() : null);

            if (facturacion.getDatosFacturacion().getMunicipio() != null) {
                facturacion.getDatosFacturacion().setMunicipioId(facturacion.getDatosFacturacion().getMunicipio().getId());
                facturacion.getDatosFacturacion().setCiudad(null);
            } else {
                facturacion.getDatosFacturacion().setMunicipioId(null);
            }

            facturacion.getDatosFacturacion().setRegimenFiscalId(facturacion.getDatosFacturacion().getRegimenFiscal() != null ? facturacion.getDatosFacturacion().getRegimenFiscal().getId() : null);
        }

        cliente = clienteDao.save(cliente);

        for(Almacen almacen : cliente.getAlmacenesConsignacion()){
            Localidad localidadGeneral = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacen.getId());
            if(localidadGeneral == null){
                String codigoLocalidad = almacen.getCodigoAlmacen() + "-LG";
 
                localidadGeneral = new Localidad();
                localidadGeneral.setCodigoLocalidad(codigoLocalidad);
                localidadGeneral.setAlmacenId(almacen.getId());
                localidadGeneral.setActivo(true);
                localidadGeneral.setCreadoPorId(usuarioId);
                localidadGeneral.setLocalidadGeneral(true);
                localidadGeneral.setNombre(localidadGeneral.getCodigoLocalidad());
                //localidadController.guardar(localidadGeneral,req);
                try {
                    almacen.getLocalidades().add(localidadGeneral);
                }
                catch (Exception e){
                    almacen.setLocalidades(new ArrayList<>());
                    almacen.getLocalidades().add(localidadGeneral);
                }
                almacenDao.save(almacen);
                String prefijo = "IF-" + almacen.getCodigoAlmacen();
                String nombre = "Inventario Físico " + almacen.getNombre();
 
                autonumericoService.create(prefijo, nombre);
            }
        }

        return new JsonResponse(null, "Cliente Guardado con el codigo: " + cliente.getCodigo(), JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idCliente}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idCliente) throws SQLException {

        return new JsonResponse(clienteDao.findClienteById(idCliente), null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idCliente}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idCliente) throws SQLException {
        List<PADescuentoListadoProjection> descuentos = paDescuentoDao.findAllByClienteId(hashId.decode(idCliente));
        for(PADescuentoListadoProjection descuento: descuentos){
            paDescuentoDao.actualizarActivo(descuento.getId(),false);
        }
        return new JsonResponse(clienteDao.actualizarActivo(hashId.decode(idCliente), false), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();
        ClienteEditarProjection cliente = null;
        Boolean permiteDesactivarConsignacion = true;

        List<EstadoComboProjection> estados = estadoDao.findProjectedComboAllByPaisId(1);

        if (id != null) {
            cliente = clienteDao.findClienteEditarProjectionById(id);
            List<ClienteRemision> remisionesPendientes = clienteRemisionDao.findAllByClienteIdAndEstatusIdIn(
                    id,
                    Arrays.asList(
                            com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.ENVIADA,
                            com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.FACTURADA_PARCIAL,
                            com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.DEVUELTA_PARCIAL
                    )
            );
            if(remisionesPendientes.size() > 0){
                permiteDesactivarConsignacion = false;
            }

            if(cliente.getPais() != null) {
                estados = estadoDao.findProjectedComboAllByPaisId(cliente.getPais().getId());
            }
        }

        List<PaisComboProjection> paises = paisDao.findProjectedComboAllBy();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();
        List<FormaPagoComboProjection> formasPago = formaPagoDao.findProjectedComboAllByActivoTrue();
        List<BancoComboProjection> bancos = bancoDao.findProjectedComboAllByActivoTrue();
        List<ListadoPrecioComboProjection> listadosPrecios = listadoPrecioDao.findAllByActivoIsTrueOrderByCodigo();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        List<UsuarioComboProjection> usuariosResponsables = usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);

        json.put("cliente", cliente);
        json.put("paises", paises);
        json.put("estados", estados);
        json.put("estadosMexico", estadoDao.findProjectedComboAllByPaisId(1));
        json.put("monedas", monedas);
        json.put("formasPago", formasPago);
        json.put("bancos", bancos);
        json.put("listados",listadosPrecios);
        json.put("sucursales", sucursales);
        json.put("usuariosResponsables", usuariosResponsables);
        json.put("permiteDesactivarConsignacion", permiteDesactivarConsignacion);
        json.put("tiposPersona", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_RFC_TipoPersona.NOMBRE));
        json.put("listRegimenFiscal", regimenFiscalDao.findAllComboProjectedByActivoTrue());
        json.put("rfcExtranjero", "XEXX010101000");

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "SELECT * from [VW_LISTADO_CLIENTES]";
        String[] alColumnas = new String[]{"Activo", "Código", "Nombre", "RFC", "Fecha Creación"};

        excelController.downloadXlsx(response, "Clientes", query, alColumnas, null, "Clientes");
    }

    @RequestMapping(value = "/verificarRfc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse verificarRfc(@RequestBody HashMap<String, Object> body) throws SQLException {
        List<Cliente> clientesMismoRfc = clienteDao.getClientesByRfc((String) body.get("rfc"), body.get("id") == null ? 0 : (Integer) body.get("id"));

        if (clientesMismoRfc.size() == 1) {
            HashMap<String, Object> json = new HashMap<>();
            json.put("nombre", clientesMismoRfc.get(0).getNombre());
            json.put("codigo", clientesMismoRfc.get(0).getCodigo());

            return new JsonResponse(json, "Un cliente");
        }

        if (clientesMismoRfc.size() > 1) {
            return new JsonResponse(null, "Mas de un cliente");
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }
}