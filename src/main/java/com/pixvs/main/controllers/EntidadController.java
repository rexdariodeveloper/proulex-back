package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Entidad.EntidadComboProjection;
import com.pixvs.main.models.projections.Entidad.EntidadEditarProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.EstadoDao;
import com.pixvs.spring.dao.PaisDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestros;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/entidades")
public class EntidadController {

    @Autowired
    private EntidadDao entidadDao;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private EstadoDao estadoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProveedores() throws SQLException {
        return new JsonResponse(entidadDao.findListadoAllByActivoIsTrue(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Entidad entidad, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        if(entidad.getId() == null){
            entidad.setActivo(true);
            entidad.setCreadoPorId(usuarioId);
        }else {
            entidad.setModificadoPorId(usuarioId);
            entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
        }
        if(entidad.getCoordinador() != null){
            entidad.setCoordinadorId(entidad.getCoordinador().getId());
            entidad.setCoordinador(null);
        }
        if(entidad.getDirector() != null){
            entidad.setDirectorId(entidad.getDirector().getId());
            entidad.setDirector(null);
        }
        if(entidad.getEntidadIndependiente() != null){
            entidad.setEntidadIndependienteId(entidad.getEntidadIndependiente().getId());
            entidad.setEntidadIndependiente(null);
        }
        if(entidad.getEstado() != null){
            entidad.setEstadoId(entidad.getEstado().getId());
            entidad.setEstado(null);
        }
        if(entidad.getPais() != null){
            entidad.setPaisId(entidad.getPais().getId());
            entidad.setPais(null);
        }
        if(entidad.getJefeUnidadAF() != null){
            entidad.setJefeUnidadAFId(entidad.getJefeUnidadAF().getId());
            entidad.setJefeUnidadAF(null);
        }
        if(entidad.getContratos() != null && entidad.getContratos().size() > 0){
            for(EntidadContrato contrato: entidad.getContratos()){
                if(contrato.getTipoContrato() != null){
                    contrato.setTipoContratoId(contrato.getTipoContrato().getId());
                    contrato.setTipoContrato(null);
                }
                if(contrato.getDocumentoContrato() != null){
                    contrato.setDocumentoContratoId(contrato.getDocumentoContrato().getId());
                    contrato.setDocumentoContrato(null);
                }
                if(contrato.getAdendumContrato() != null){
                    contrato.setAdendumContratoId(contrato.getAdendumContrato().getId());
                    contrato.setAdendumContrato(null);
                }
            }
        }
        entidadDao.save(entidad);
        return new JsonResponse(null, "Registro guardado", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idEntidad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idEntidad) throws SQLException {
        return new JsonResponse(entidadDao.findEditarFindById(idEntidad), null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idEntidad}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idEntidad) throws SQLException {
        return new JsonResponse(entidadDao.actualizarActivo(hashId.decode(idEntidad), false), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();
        EntidadEditarProjection entidad = null;
        List<EntidadComboProjection> entidadesDependientes = null;
        if (id != null) {
            entidadesDependientes = entidadDao.findComboAllByActivoIsTrue().stream()
                    .filter(p -> p.getId() != id).collect(Collectors.toList());
            entidad = entidadDao.findEditarFindById(id);
            json.put("entidad", entidad);
        }else {
            entidadesDependientes = entidadDao.findComboAllByActivoIsTrue();
        }

        List<PaisComboProjection> paises = paisDao.findProjectedComboAllBy();
        List<EmpleadoComboProjection> empleados = empleadoDao.findAllByEstatusIdNotIn(Arrays.asList(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO));
        List<ControlMaestroMultipleComboProjection> tiposContratos = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ENT_TipoContrato.NOMBRE);

        json.put("entidadesDependientes", entidadesDependientes);
        json.put("paises", paises);
        json.put("empleados", empleados);
        json.put("tiposContratos", tiposContratos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "SELECT * from [VW_LISTADO_CLIENTES]";
        String[] alColumnas = new String[]{"Activo", "Código", "Nombre", "RFC", "Fecha Creación"};

        excelController.downloadXlsx(response, "Clientes", query, alColumnas, null, "Clientes");
    }

}