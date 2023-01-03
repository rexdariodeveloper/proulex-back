package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.Workshop.WorkshopListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workshop")
public class WorkshopController {

    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PAActividadEvaluacionDao paActividadEvaluacionDao;
    @Autowired
    private ArticuloDao articuloDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getWorkshops() throws SQLException {
        List<WorkshopListadoProjection> workshops = programaIdiomaDao.findListadoWorkshopsOrderByCodigo();
        return new JsonResponse(workshops, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoWorkshopsById(@PathVariable(required = false) Integer id) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();

        if (id != null)
            json.put("workshop", programaIdiomaDao.findProjectedEditarById(id));
        json.put("tipos", controlMaestroMultipleDao.findAllByControl("CMM_WKS_TipoWorkshop"));
        json.put("programas", programaDao.findComboListadoAllByActivoIsTrue());
        json.put("idiomas", controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE));
        json.put("modalidades", paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre());
        json.put("plataformas", controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_Plataforma.NOMBRE));
        json.put("sucursales", sucursalDao.findProjectedComboAllByActivoTrue());
        json.put("unidadesMedidas", unidadMedidaDao.findProjectedComboAllByActivoTrue());
        json.put("objetosImpuestoSAT", controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_ObjetoImp"));
        json.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndFamiliaIdAndTipoArticuloIdNot(17,4));
        json.put("test", paActividadEvaluacionDao.findProjectedComboAllByActivoTrue());
        json.put("testFormat", controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_PROGI_TestFormat.NOMBRE));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaIdioma workshop, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Programa programa = programaDao.findById(workshop.getProgramaId());
        List<ProgramaIdioma> workshops = programa.getIdiomas();
        List<Articulo> articulos = new ArrayList<>();
        if (workshop.getId() == null){
            workshop.setActivo(true);
            for (ProgramaIdiomaModalidad programaIdiomaModalidad : workshop.getModalidades()){
                PAModalidad modalidad = programaIdiomaModalidad.getModalidad();
                programaIdiomaModalidad.setModalidadId(modalidad.getId());
                String codigo = workshop.getCodigo() + " " + modalidad.getCodigo();
                String nombre = workshop.getNombre() + " " + modalidad.getNombre();
                Articulo articulo = new Articulo();
                articulo.setCodigoArticulo(codigo);
                articulo.setNombreArticulo(nombre);
                articulo.setDescripcion(workshop.getDescripcion());
                articulo.setClaveProductoSAT(workshop.getClave());
                articulo.setDescripcionCorta("");
                articulo.setPermitirCambioAlmacen(false);
                articulo.setPlaneacionTemporadas(false);
                articulo.setFamiliaId(9);
                articulo.setCategoriaId(37);
                articulo.setTipoArticuloId(5);
                articulo.setUnidadMedidaInventarioId(1);
                articulo.setTipoCostoId(2000041);
                articulo.setIva(BigDecimal.valueOf(workshop.getIva() / Double.valueOf(100)));
                articulo.setIeps(BigDecimal.valueOf(workshop.getIeps() / Double.valueOf(100)));
                articulo.setCostoUltimo(BigDecimal.ZERO);
                articulo.setCostoPromedio(BigDecimal.ZERO);
                articulo.setCostoEstandar(BigDecimal.ZERO);
                articulo.setActivo(true);
                articulo.setInventariable(true);
                articulo.setArticuloParaVenta(true);
                articulo.setProgramaIdiomaId(workshop.getId());
                articulo.setModalidadId(modalidad.getId());
                articulo.setObjetoImpuestoId(workshop.getObjetoImpuesto() == null ? null : workshop.getObjetoImpuesto().getId());
                articulos.add(articulo);
            }

        } else {
            workshop.setModificadoPorId(usuarioId);
            for (ProgramaIdiomaModalidad programaIdiomaModalidad : workshop.getModalidades()){
                PAModalidad modalidad = programaIdiomaModalidad.getModalidad();
                programaIdiomaModalidad.setModalidadId(modalidad.getId());
                String codigo = workshop.getCodigo() + " " + modalidad.getCodigo();
                Articulo articulo = articuloDao.findByCodigoArticulo(codigo);
                articulo.setIva(BigDecimal.valueOf(workshop.getIva() / Double.valueOf(100)));
                articulo.setIeps(BigDecimal.valueOf(workshop.getIeps() / Double.valueOf(100)));
                articulo.setObjetoImpuestoId(workshop.getObjetoImpuesto() == null ? null : workshop.getObjetoImpuesto().getId());
                articulos.add(articulo);
            }
        }

        for(ProgramaIdiomaLibroMaterial libroMaterial : workshop.getLibrosMateriales()){
            libroMaterial.setArticuloId(libroMaterial.getArticulo().getId());
        }

        for (ProgramaIdiomaNivel programaIdiomaNivel : workshop.getNiveles()){
            for (ProgramaIdiomaExamen programaIdiomaExamen : programaIdiomaNivel.getExamenes()){
                for (ProgramaIdiomaExamenDetalle programaIdiomaExamenDetalle : programaIdiomaExamen.getDetalles()){
                    programaIdiomaExamenDetalle.setActividadEvaluacionId(programaIdiomaExamenDetalle.getActividadEvaluacion().getId());
                    programaIdiomaExamenDetalle.setTestId(programaIdiomaExamenDetalle.getTest().getId());
                    for (ProgramaIdiomaExamenModalidad programaIdiomaExamenModalidad : programaIdiomaExamenDetalle.getModalidades()){
                        programaIdiomaExamenModalidad.setModalidadlId(programaIdiomaExamenModalidad.getModalidad().getId());
                    }
                    programaIdiomaExamenDetalle.setUnidades(new ArrayList<>());
                    for (ProgramaIdiomaExamenUnidad programaIdiomaExamenUnidad : programaIdiomaExamenDetalle.getUnidades()){
                        if (programaIdiomaExamenUnidad.getLibroMaterial() != null) {
                            programaIdiomaExamenUnidad.setLibroMaterialId(findIdLibroByNivelAndArticulo(programaIdiomaExamenUnidad.getLibroMaterial().getNivel(),programaIdiomaExamenUnidad.getLibroMaterial().getArticulo().getId(),workshop.getLibrosMateriales()));
                            programaIdiomaExamenUnidad.setLibroMaterial(null);
                        }
                    }
                }
            }
        }

        for (ProgramaIdiomaSucursal programaIdiomaSucursal : workshop.getSucursales()){
            programaIdiomaSucursal.setSucursalId(programaIdiomaSucursal.getSucursal().getId());
        }

        if (workshop.getId() != null){
            for (ProgramaIdioma programaIdioma : workshops){
                if (programaIdioma.getId() == workshop.getId())
                    programaIdioma = workshop;
            }
        } else
            workshops.add(workshop);

        programa.setIdiomas(workshops);
        programaDao.save(programa);
        workshop = programaIdiomaDao.findByCodigo(workshop.getCodigo());
        for (Articulo articulo : articulos){
            articulo.setProgramaIdiomaId(workshop.getId());
            articulo.setPedirCantidadPV(false);
        }
        articuloDao.saveAll(articulos);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    public Integer findIdLibroByNivelAndArticulo(Integer nivel, Integer articulo, List<ProgramaIdiomaLibroMaterial> libros){
        for(ProgramaIdiomaLibroMaterial libro: libros){
            if(libro.getNivel().equals(nivel) && libro.getArticuloId().equals(articulo)){
                return libro.getId();
            }
        }
        return null;
    }
}