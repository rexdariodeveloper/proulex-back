package com.pixvs.main.controllers;

import com.pixvs.main.dao.PACicloDao;
import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaDao;
import com.pixvs.main.dao.ProgramacionAcademicaComercialDao;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadDiasProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialEditarProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.EmpresaDiaNoLaboralDao;
import com.pixvs.spring.dao.EmpresaDiaNoLaboralFijoDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboral.EmpresaDiaNoLaboralEditarProjection;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboralFijo.EmpresaDiaNoLaboralFijoEditarProjection;
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
import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@RestController
@RequestMapping("/api/v1/programacion-academica-comercial")
public class ProgramacionAcademicaComercialController {

    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private PACicloDao paCicloDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private EmpresaDiaNoLaboralDao empresaDiaNoLaboralDao;
    @Autowired
    private EmpresaDiaNoLaboralFijoDao empresaDiaNoLaboralFijoDao;
    @Autowired
    private ProgramaDao programaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramacionAcademicaComercial() throws SQLException {

        List<ProgramacionAcademicaComercialListadoProjection> programacionAcademicaComercial = programacionAcademicaComercialDao.findProjectedListadoAllBy();

        return new JsonResponse(programacionAcademicaComercial, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramacionAcademicaComercial programacionAcademicaComercial, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        programacionAcademicaComercial.setPaCicloId(programacionAcademicaComercial.getPaCiclo().getId());
        for(ProgramacionAcademicaComercialDetalle detalle : programacionAcademicaComercial.getDetalles()){
            detalle.setIdiomaId(detalle.getIdioma().getId());
            detalle.setPaModalidadId(detalle.getPaModalidad().getId());
        }

        if (programacionAcademicaComercial.getId() == null) {
            programacionAcademicaComercial.setCreadoPorId(idUsuario);
            programacionAcademicaComercial.setActivo(true);

            PACiclo paCiclo = paCicloDao.findById(programacionAcademicaComercial.getPaCicloId());
            Integer autonumerico = 0;

            List<ProgramacionAcademicaComercial> programacionesAnterioresCiclo = programacionAcademicaComercialDao.findByCodigoLike("PA" + paCiclo.getCodigo() + "-%");
            if(programacionesAnterioresCiclo.size() > 0){
                autonumerico = Integer.parseInt(programacionesAnterioresCiclo .get(0).getCodigo().substring(programacionesAnterioresCiclo .get(0).getCodigo().lastIndexOf("-")+1));
            }

            autonumerico++;
            programacionAcademicaComercial.setCodigo("PA" + paCiclo.getCodigo() + "-" + autonumerico.toString());
        } else {
            ProgramacionAcademicaComercial objetoActual = programacionAcademicaComercialDao.findById(programacionAcademicaComercial.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), programacionAcademicaComercial.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            programacionAcademicaComercial.setModificadoPorId(idUsuario);
        }

        programacionAcademicaComercial = programacionAcademicaComercialDao.save(programacionAcademicaComercial);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idProgramacionAcademicaComercial}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idProgramacionAcademicaComercial) throws SQLException {

        ProgramacionAcademicaComercialEditarProjection programacionAcademicaComercial = programacionAcademicaComercialDao.findProjectedEditarById(idProgramacionAcademicaComercial);

        return new JsonResponse(programacionAcademicaComercial, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idProgramacionAcademicaComercial}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idProgramacionAcademicaComercial) throws SQLException {

        ProgramacionAcademicaComercial programacionAcademicaComercial = programacionAcademicaComercialDao.findById(hashId.decode(idProgramacionAcademicaComercial));
        programacionAcademicaComercial.setActivo(false);
        programacionAcademicaComercialDao.save(programacionAcademicaComercial);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramacionAcademicaComercialById(@PathVariable(required = false) Integer id) throws SQLException {

        List<PACicloFechaProjection> paCiclos = paCicloDao.findProjectedFechaAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<PAModalidadDiasProjection> paModalidades = paModalidadDao.findProjectedDiasAllByActivoTrueOrderByNombre();
        List<EmpresaDiaNoLaboralEditarProjection> diasNoLaborales = empresaDiaNoLaboralDao.findAllByActivoIsTrue();
        List<EmpresaDiaNoLaboralFijoEditarProjection> diasNoLaboralesFijos = empresaDiaNoLaboralFijoDao.findAllByActivoIsTrue();
        List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();

        ProgramacionAcademicaComercialEditarProjection programacionAcademicaComercial = null;
        if (id != null) {
            programacionAcademicaComercial = programacionAcademicaComercialDao.findProjectedEditarById(id);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("programacionAcademicaComercial", programacionAcademicaComercial);
        json.put("paCiclos", paCiclos);
        json.put("idiomas", idiomas);
        json.put("paModalidades", paModalidades);
        json.put("diasNoLaborales", diasNoLaborales);
        json.put("diasNoLaboralesFijos", diasNoLaboralesFijos);
        json.put("programas",programas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_PROGRAMACION_ACADEMICA_COMERCIAL]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Fecha Inicio", "Fecha Fin","Ciclo","Estatus"};

        excelController.downloadXlsx(response, "programacionAcademicaComercial", query, alColumnas, null,"Programación académica comercial");
    }


}

