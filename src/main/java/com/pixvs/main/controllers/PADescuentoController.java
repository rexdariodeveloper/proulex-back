package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoEditarProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorEditarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/descuentos")
public class PADescuentoController {

    @Autowired
    private PADescuentoDao paDescuentoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramaIdiomaDescuentoDetalleDao programaIdiomaDescuentoDetalleDao;
    @Autowired
    private PADescuentoDetalleDao paDescuentoDetalleDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private PADescuentoSucursalDao paDescuentoSucursalDao;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedores() throws SQLException {
        List<PADescuentoListadoProjection> descuentos = paDescuentoDao.findAllByView();
        return new JsonResponse(descuentos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PADescuento descuento, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (descuento.getId() == null) {
            descuento.setCreadoPorId(idUsuario);
            descuento.setActivo(true);
            descuento.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("DESC"));
        } else {
            descuento.setModificadoPorId(idUsuario);
            limpiarSucursales(descuento.getId());
        }

        if(descuento.getTipo() != null){
            descuento.setTipoId(descuento.getTipo().getId());
            descuento.setTipo(null);
        }

        if(descuento.getCliente() != null){
            descuento.setClienteId(descuento.getCliente().getId());
            descuento.setCliente(null);
        }

        for(PADescuentoDetalle detalle: descuento.getDetalles()){
            if(detalle.getBorrado()== null || !detalle.getBorrado()) {
                if (detalle.getPaModalidad() != null) {
                    detalle.setPaModalidadId(detalle.getPaModalidad().getId());
                    detalle.setPaModalidad(null);
                }
                if (detalle.getPaModalidadHorario() != null) {
                    detalle.setPaModalidadHorarioId(detalle.getPaModalidadHorario().getId());
                    detalle.setPaModalidadHorario(null);
                }
                if (detalle.getPrograma() != null) {
                    detalle.setProgramaId(detalle.getPrograma().getId());
                    detalle.setPrograma(null);
                }
                for (ProgramaIdiomaDescuentoDetalle descuentoCurso : detalle.getCursos()) {
                    if (descuentoCurso.getProgramaIdioma() != null) {
                        descuentoCurso.setProgramaIdiomaId(descuentoCurso.getProgramaIdioma().getId());
                        descuentoCurso.setProgramaIdioma(null);
                    }
                }
            }else if(detalle.getBorrado() != null && detalle.getBorrado() && detalle.getId()!=null){
                programaIdiomaDescuentoDetalleDao.deleteByDescuentoDetalleId(detalle.getId());
                paDescuentoDetalleDao.deleteById(detalle.getId());
            }
        }

        List<PADescuentoDetalle> temp = new ArrayList<>();

        for(PADescuentoDetalle detalle: descuento.getDetalles()) {
            if(detalle.getBorrado() != null && detalle.getBorrado()) {
                temp.add(detalle);
            }
        }
        descuento.getDetalles().removeAll(temp);

        if(descuento.getArticulos() != null) {
            for (PADescuentoArticulo articulo : descuento.getArticulos()) {
                if (articulo.getArticulo() != null) {
                    articulo.setArticuloId(articulo.getArticulo().getId());
                    articulo.setArticulo(null);
                }
                if (articulo.getActivo() == null) {
                    articulo.setActivo(true);
                }
            }
        }

        if(descuento.getUsuariosAutorizados() != null) {
            for (PADescuentoUsuarioAutorizado usuario : descuento.getUsuariosAutorizados()) {
                if (usuario.getUsuario() != null) {
                    usuario.setUsuarioId(usuario.getUsuario().getId());
                    usuario.setUsuario(null);
                }
                if(usuario.getSucursal() != null){
                    usuario.setSucursalId(usuario.getSucursal().getId());
                    usuario.setSucursal(null);
                }
                if (usuario.getActivo() == null) {
                    usuario.setActivo(true);
                }
            }
        }

        for(PADescuentoSucursal sucursal: descuento.getSucursales()){
            if(sucursal.getSucursal() != null){
                sucursal.setSucursalId(sucursal.getSucursal().getId());
                sucursal.setSucursal(null);
            }
        }


        descuento = paDescuentoDao.save(descuento);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idDescuento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idDescuento) throws SQLException {

        PADescuentoEditarProjection descuento = paDescuentoDao.findProjectedEditarById(idDescuento);

        return new JsonResponse(descuento, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idDescuento}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idDescuento) throws SQLException {

        int actualizado = paDescuentoDao.actualizarActivo(hashId.decode(idDescuento), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(ServletRequest req, @PathVariable(required = false) Integer id) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        PADescuentoEditarProjection descuento = null;
        if (id != null) {
            descuento = paDescuentoDao.findProjectedEditarById(id);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            if(descuento.getFechaFin() != null && date.after(descuento.getFechaFin())){
                PADescuento temp = paDescuentoDao.findById(descuento.getId());
                for(PADescuentoArticulo articulo : temp.getArticulos()){
                    ArticuloComboProjection tempArticulo = articuloDao.findProjectedComboById(articulo.getArticuloId());
                    if(tempArticulo == null || !tempArticulo.getActivo()){
                        articulo.setActivo(false);
                    }
                }
                temp.setActivo(false);
                paDescuentoDao.save(temp);
                descuento = paDescuentoDao.findProjectedEditarById(id);
            }
        }
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();
        List<PAModalidadComboProjection> modalidades = paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre();
        List<ClienteComboProjection> clientes = clienteDao.findAllByActivoIsTrue();
        List<ControlMaestroMultipleComboProjection> tiposDescuentos = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PADES_Tipo.NOMBRE);
        List<ArticuloFamiliaComboProjection> familias = articuloFamiliaDao.findProjectedComboAllByActivoTrue();
        List<UsuarioComboProjection> usuarios = usuarioDao.findProjectedComboAllByEstatusId(1000001);
        HashMap<String, Object> json = new HashMap<>();

        json.put("descuento", descuento);
        json.put("sucursales", sucursales);
        json.put("modalidades",modalidades);
        json.put("usuarios",usuarios);
        json.put("familias",familias);
        json.put("tiposDescuentos",tiposDescuentos);
        json.put("clientes",clientes);
        json.put("programas", programas);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_DESCUENTOS_EXCEL]";
        String[] alColumnas = new String[]{"Código", "Concepto", "Porcentaje", "Fecha Inicio","Fecha Fin","Estatus"};

        excelController.downloadXlsx(response, "descuentos", query, alColumnas, null,"Descuentos");
    }

    public void limpiarSucursales(Integer idDescuento){
        paDescuentoSucursalDao.deleteByDescuentoId(idDescuento);
    }
}

