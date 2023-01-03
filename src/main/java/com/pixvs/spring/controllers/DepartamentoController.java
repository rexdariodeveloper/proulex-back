package com.pixvs.spring.controllers;

import com.pixvs.spring.dao.DepartamentoHabilidadResponsabilidadDao;
import com.pixvs.main.dao.PuestoDao;
import com.pixvs.main.models.projections.Puesto.PuestoComboContratosProjection;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Departamento;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoEditarProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoNodoProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.handler.exceptions.SqlDeleteFkException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 */
@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoDao departamentoDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private PuestoDao puestoDao;

    @Autowired
    private DepartamentoHabilidadResponsabilidadDao departamentoHabilidadResponsabilidadDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDepartamentos() throws SQLException {

        List<DepartamentoNodoProjection> departamentosTree = departamentoDao.findProjectedNodoAllByActivoTrueAndDepartamentoPadreIdIsNull();
        List<UsuarioComboProjection> usuarios = usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);
        List<DepartamentoComboProjection> departamentos = departamentoDao.findProjectedComboAllByActivoTrue();
        List<PuestoComboContratosProjection> puestos = puestoDao.findProjectedListadoComboContratoAllBy();

        HashMap<String,Object> datos = new HashMap<>();
        datos.put("departamentosTree",departamentosTree);
        datos.put("usuarios",usuarios);
        datos.put("departamentos",departamentos);
        datos.put("puestos",puestos);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Departamento departamento, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer prefijosExistentes;
        if(departamento.getId() != null){
            prefijosExistentes = departamentoDao.countByIdNotAndPrefijo(departamento.getId(),departamento.getPrefijo());
        }else{
            prefijosExistentes = departamentoDao.countByPrefijo(departamento.getPrefijo());
        }
        if(prefijosExistentes > 0){
            return new JsonResponse("", "Ya existe un departamento con el prefijo ingresado", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        if(departamento.getPuesto() != null){
            departamento.setPuestoId(departamento.getPuesto().getId());
        }else{
            departamento.setPuestoId(null);
        }

        if(departamento.getResponsable() != null){
            departamento.setResponsableId(departamento.getResponsable().getId());
        }else{
            departamento.setResponsableId(null);
        }

        if(departamento.getDepartamentoPadre() != null){
            departamento.setDepartamentoPadreId(departamento.getDepartamentoPadre().getId());
        }else{
            departamento.setDepartamentoPadreId(null);
        }

        departamento.setDepartamentoPadre(null);

        if(departamento.getAutoriza() == null){
            departamento.setAutoriza(false);
        }
        if(departamento.getActivo() == null){
            departamento.setActivo(false);
        }

        if (departamento.getId() == null) {
            departamento.setCreadoPorId(idUsuario);
            departamento.setActivo(true);
        } else {
            Departamento objetoActual =departamentoDao.findById(departamento.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),departamento.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            departamento.setModificadoPorId(idUsuario);

            if(!departamento.getActivo()){
                List<Departamento> departamentosDescendientes = departamentoDao.findAllFnDepartamentosDescendientes(departamento.getId());
                for(Departamento departamentoDescendiente : departamentosDescendientes){
                    departamentoDescendiente.setActivo(false);
                    departamentoDescendiente.setModificadoPorId(idUsuario);
                    departamentoDao.save(departamentoDescendiente);
                }
            }

        }

        if(departamento.getResponsabilidadesHabilidadesEliminar() != null && departamento.getResponsabilidadesHabilidadesEliminar().length > 0){
            departamentoHabilidadResponsabilidadDao.deleteHabilidadesResponsabilidadesWithIds(Arrays.asList(departamento.getResponsabilidadesHabilidadesEliminar()));
            //departamentoHabilidadResponsabilidadDao.deleteInBatch(Arrays.asList(departamento.getResponsabilidadesHabilidadesEliminar()));
        }

        departamento = departamentoDao.save(departamento);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idDepartamento}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idDepartamento) throws SQLException {

        DepartamentoEditarProjection departamento = departamentoDao.findProjectedEditarById(idDepartamento);

        return new JsonResponse(departamento, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delete/{idDepartamento}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idDepartamento) throws Exception {

        try{
            List<Departamento> departamentosDescendientes = departamentoDao.findAllFnDepartamentosDescendientes(hashId.decode(idDepartamento));
            for(Departamento departamentoDescendiente : departamentosDescendientes){
                departamentoDao.deleteById(departamentoDescendiente.getId());
            }
            departamentoDao.deleteById(hashId.decode(idDepartamento));
        }catch(DataIntegrityViolationException e){
            if(e.getCause() != null && e.getCause().getClass().equals(ConstraintViolationException.class) && ((ConstraintViolationException)e.getCause()).getSQLException().getErrorCode() == 547){
                throw new SqlDeleteFkException();
            }
            throw e;
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoDepartamentosById(@PathVariable(required = false) Integer id) throws SQLException {

        DepartamentoEditarProjection departamento = null;
        if (id != null) {
            departamento = departamentoDao.findProjectedEditarById(id);
        }


        HashMap<String, Object> json = new HashMap<>();

        json.put("departamento", departamento);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_DEPARTAMENTOS]";
        String[] alColumnas = new String[]{"Activo", "Prefijo", "Nombre", "Autoriza"};

        excelController.downloadXlsx(response, "departamentos", query, alColumnas, null);
    }


}

