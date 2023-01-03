package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Banco;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.PrecioIncompanyDetalle;
import com.pixvs.main.models.PrecioIncompanySucursal;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Banco.BancoEditarProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyComboZonaProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyEditarProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyListadoProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyVentaProjection;
import com.pixvs.main.models.projections.PrecioIncompanySucursal.PrecioIncompanySucursalEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/precios-incompany")
public class PrecioIncompanyController {

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private PrecioIncompanyDao precioIncompanyDao;

    @Autowired
    private PAModalidadDao paModalidadDao;

    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PrecioIncompanySucursalDao precioIncompanySucursalDao;

    @Autowired
    ControlMaestroMultipleDao controlMaestroMultipleDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPreciosIncompany() throws Exception {
        List<PrecioIncompanyListadoProjection> listado = precioIncompanyDao.findAllBy();
        return new JsonResponse(listado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        PrecioIncompanyEditarProjection precio = null;
        if (id != null) {
            precio = precioIncompanyDao.findProjectionById(id);
            PrecioIncompany precioIncompany = precioIncompanyDao.findById(precio.getId());
            if(precioIncompany.getFechaFin() != null && precioIncompany.getFechaFin().before(new Date(System.currentTimeMillis())) && !precioIncompany.getIndeterminado()){
                precioIncompany.setEstatusId(ControlesMaestrosMultiples.CMM_PREINC_Estatus.INACTIVO);
                precioIncompanyDao.save(precioIncompany);
                precio = precioIncompanyDao.findProjectionById(precioIncompany.getId());
            }
        }
        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<ControlMaestroMultipleComboProjection> zonas = controlMaestroMultipleDao.findAllByControl("CMM_INC_Zona");
        List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();
        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findComboAllByActivoTrueOrderByNombre();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);

        json.put("precio", precio);
        json.put("idiomas",idiomas);
        json.put("zonas",zonas);
        json.put("programas",programas);
        json.put("modalidades",modalidades);
        json.put("sucursales",sucursales);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody PrecioIncompany precio, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (precio.getId() == null) {
            precio.setCreadoPorId(usuarioId);
            precio.setEstatusId(ControlesMaestrosMultiples.CMM_PREINC_Estatus.ACTIVO);
        } else {
            PrecioIncompanyEditarProjection objetoActual = precioIncompanyDao.findProjectionById(precio.getId());

            /*try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaUltimaModificacion(), precio.getFechaUltimaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }*/

            precio.setModificadoPorId(usuarioId);
            precio.setFechaUltimaModificacion(new Date(System.currentTimeMillis()));
            clearSucursales(precio.getId());
        }

        if(precio.getEstatus() != null){
            precio.setEstatusId(precio.getEstatus().getId());
            precio.setEstatus(null);
        }
        if(precio.getIndeterminado() == null){
            precio.setIndeterminado(false);
        }

        if(precio.getDetalles() != null && precio.getDetalles().size() >0){
            for(PrecioIncompanyDetalle detalle : precio.getDetalles()){
                if(detalle.getId() == null){
                    detalle.setActivo(true);
                }
                if(detalle.getHorario() != null){
                    detalle.setHorarioId(detalle.getHorario().getId());
                    detalle.setHorario(null);
                }
                if(detalle.getIdioma() != null){
                    detalle.setIdiomaId(detalle.getIdioma().getId());
                    detalle.setIdioma(null);
                }
                if(detalle.getModalidad() != null){
                    detalle.setModalidadId(detalle.getModalidad().getId());
                    detalle.setModalidad(null);
                }
                if(detalle.getPrograma() != null){
                    detalle.setProgramaId(detalle.getPrograma().getId());
                    detalle.setPrograma(null);
                }
                if(detalle.getZona() != null){
                    detalle.setZonaId(detalle.getZona().getId());
                    detalle.setZona(null);
                }
            }
        }

        if(precio.getSucursales() != null){
            for(PrecioIncompanySucursal sucursal : precio.getSucursales()){
                List<PrecioIncompanySucursalEditarProjection> temps = precioIncompanySucursalDao.findAllBySucursalId(sucursal.getSucursal().getId());
                if(temps != null && temps.size() >0){
                    return new JsonResponse(null,"La sucursal " + sucursal.getSucursal().getNombre() + " ya ha sido asignada.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
                if(sucursal.getSucursal() != null){
                    sucursal.setSucursalId(sucursal.getSucursal().getId());
                    sucursal.setSucursal(null);
                }
            }
        }

        precioIncompanyDao.save(precio);

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getModalidadesByProgramaAndIdioma", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getModalidadesByProgramaAndIdioma(@RequestBody JSONObject json) {
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");
        List<Integer> idiomasId = Utilidades.getListItems(json.get("idiomas"), "id");
        List<PAModalidadComboProjection> modalidades = paModalidadDao.getModalidadesByProgramaAndIdioma(programasId,idiomasId);
        return new JsonResponse(modalidades, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getDatosVenta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosVenta(@RequestBody JSONObject json) {
        Integer programaId = Integer.parseInt(json.get("programa").toString());
        Integer modalidadId = Integer.parseInt(json.get("modalidad").toString());
        Integer horarioId = Integer.parseInt(json.get("horario").toString());
        Integer idiomaId = Integer.parseInt(json.get("idioma").toString());
        Integer precioId = Integer.parseInt(json.get("precio").toString());

        PrecioIncompanyVentaProjection datos = precioIncompanyDao.findProjectedDatosVenta(programaId,modalidadId,horarioId,idiomaId,precioId);
        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    public void clearSucursales(Integer idPrecioIncompany){
        precioIncompanySucursalDao.deleteByPrecioIncompanyId(idPrecioIncompany);
    }

    @RequestMapping(value="/getComboZona", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getComboZona(@RequestBody JSONObject json) throws SQLException {
        Integer sedeId = (Integer) json.get("sedeId");
        Integer programaId = (Integer) json.get("programaId");
        Integer idiomaId = (Integer) json.get("idiomaId");
        Integer modalidadId = (Integer) json.get("modalidadId");
        Integer modalidadHorarioId = (Integer) json.get("modalidadHorarioId");

        List<PrecioIncompanyComboZonaProjection> listaZona = precioIncompanyDao.findPrecioIncompanyComboZonaProjection(sedeId, programaId, idiomaId, modalidadId, modalidadHorarioId);

        return new JsonResponse(listaZona, null, JsonResponse.STATUS_OK);
    }
}