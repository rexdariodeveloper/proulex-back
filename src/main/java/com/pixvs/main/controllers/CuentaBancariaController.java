package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.CuentaBancaria;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorEditarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorListadoProjection;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cuentas-bancarias")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaDao cuentaBancariaDao;

    @Autowired
    private BancoDao bancoDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCuentasBancarias() throws SQLException {

        List<Map<String, Object>> cuentas = cuentaBancariaDao.getListado();

        return new JsonResponse(cuentas, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CuentaBancaria cuenta, ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(cuenta.getId() != null){
            CuentaBancaria actual = cuentaBancariaDao.findById(cuenta.getId());
            try{
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(),actual.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            actual.setModificadoPorId(idUsuario);
        } else {
            cuenta.setActivo(true);
            cuenta.setCreadoPorId(idUsuario);
        }

        cuentaBancariaDao.save(cuenta);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idCuenta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idCuenta) throws SQLException {

        CuentaBancariaEditarProjection cuenta = cuentaBancariaDao.findProjectedById(idCuenta);

        return new JsonResponse(cuenta, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idCuenta}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse changeStatus(@PathVariable String idCuenta, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer id = hashId.decode(idCuenta);

        Integer estatus = cuentaBancariaDao.actualizarEstatus(id, false, idUsuario);

        return new JsonResponse(estatus, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/detalle/", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {
        CuentaBancariaEditarProjection cuenta = null;
        if ( id != null )
            cuenta = cuentaBancariaDao.findProjectedById(id);

        List<BancoComboProjection> bancos   = bancoDao.findProjectedComboAllByActivoTrue();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();

        HashMap<String, Object> json = new HashMap<>();

        json.put("cuenta", cuenta);
        json.put("bancos", bancos);
        json.put("monedas", monedas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_CUENTAS_BANCARIAS]";
        String[] alColumnas = new String[]{"Código", "Descripción", "Moneda", "Banco", "Activo", "Fecha Creación"};

        excelController.downloadXlsx(response, "cuentas-bancarias", query, alColumnas, null,"Cuentas bancarias");
    }


}

