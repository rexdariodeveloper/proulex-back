package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorEditarProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/tabuladores")
public class TabuladorController {

    @Autowired
    private TabuladorDao tabuladorDao;
    @Autowired
    private TabuladorDetalleDao tabuladorDetalleDao;
    @Autowired
    private TabuladorCursoDao tabuladorCursoDao;
    @Autowired
    private PAProfesorCategoriaDao paProfesorCategoriaDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTabuladores() throws SQLException {

        List<TabuladorListadoProjection> tabuladores = tabuladorDao.findAllByActivoIsTrue();

        return new JsonResponse(tabuladores, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Tabulador tabulador, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<Integer> idEliminar = new ArrayList<>();
        if(tabulador.getId() == null){
            tabulador.setCreadoPorId(idUsuario);
        }else {
            tabulador.setModificadoPorId(idUsuario);
        }
        if(tabulador.getPagoDiasFestivos() != null && tabulador.getPagoDiasFestivos()){
            TabuladorComboProjection temp = tabuladorDao.findByPagoDiasFestivosIsTrue();
            if(temp != null && !temp.getId().equals(tabulador.getId())){
                return new JsonResponse(null,"El tabulador "+temp.getCodigo()+" ya se encuentra configurado como tabulador de pago de días festivos",JsonResponse.STATUS_ERROR);
            }
        }
        List<TabuladorDetalle> eliminados = new ArrayList<>();
        for(TabuladorDetalle detalle : tabulador.getDetalles()){
            if(detalle.getId() == null && tabulador.getId() != null){
                TabuladorDetalle tabuladorDetalle = tabuladorDetalleDao.findByTabuladorIdAndProfesorCategoriaIdAndActivoIsTrue(tabulador.getId(),detalle.getProfesorCategoria().getId());
                if(tabuladorDetalle != null){
                    detalle.setId(tabuladorDetalle.getId());
                    //tabuladorDetalleDao.delete(tabuladorDetalle);
                }
            }
            if(detalle.getProfesorCategoria()!=null){
                detalle.setProfesorCategoriaId(detalle.getProfesorCategoria().getId());
                //detalle.setProfesorCategoria(null);
            }
            if(!detalle.getActivo()){
                eliminados.add(detalle);
            }
        }

        for(TabuladorCurso curso: tabulador.getCursos()){
            if(curso.getPrograma()!=null){
                curso.setProgramaId(curso.getPrograma().getId());
                curso.setPrograma(null);
            }
            if(curso.getProgramaIdioma()!=null){
                curso.setProgramaIdiomaId(curso.getProgramaIdioma().getId());
                curso.setProgramaIdioma(null);
            }
            if(curso.getModalidad()!=null){
                curso.setModalidadId(curso.getModalidad().getId());
                curso.setModalidad(null);
            }
            if(curso.getModalidadHorario()!=null){
                curso.setModalidadHorarioId(curso.getModalidadHorario().getId());
                curso.setModalidadHorario(null);
            }
            if(curso.getActivo() == null){
                curso.setActivo(true);
            }
            if(!curso.getActivo() && curso.getId() != null){
                idEliminar.add(curso.getId());
            }
        }
        for(Integer id: idEliminar){
            tabulador.getCursos().removeIf(obj -> obj.getId() == id);
            tabuladorCursoDao.deleteById(id);
        }
        tabuladorCursoDao.findProjectByTabuladorId(tabulador.getId());
        List<TabuladorCurso> cursos = tabulador.getCursos();
        tabulador.setCursos(cursos);

        if(tabulador.getActualizarGrupos() != null && tabulador.getActualizarGrupos()){
            for(TabuladorCurso curso: tabulador.getCursos()){
                for(TabuladorDetalle detalle : tabulador.getDetalles()){
                    List<ProgramaGrupo> grupos = programaGrupoDao.findAllByProgramaIdiomaIdAndPaModalidadIdAndModalidadHorarioIdAndCategoriaProfesorAndEstatusId(curso.getProgramaIdiomaId(),curso.getModalidadId(),curso.getModalidadHorarioId(),detalle.getProfesorCategoria().getCategoria(), ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
                    for(ProgramaGrupo grupo: grupos){
                        grupo.setCategoriaProfesor(detalle.getProfesorCategoria().getCategoria());
                        grupo.setSueldoProfesor(detalle.getSueldo());
                        programaGrupoDao.save(grupo);
                    }
                }
            }
        }


        tabulador = tabuladorDao.save(tabulador);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idTabulador}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idEmpleado) throws SQLException {

        TabuladorEditarProjection tabulador = tabuladorDao.findProjectedEditarById(idEmpleado);

        return new JsonResponse(tabulador, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idEmpleado}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idEmpleado) throws SQLException {

        int actualizado = tabuladorDao.actualizarActivo(hashId.decode(idEmpleado), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        TabuladorEditarProjection tabulador = null;
        if (id != null) {
            tabulador = tabuladorDao.findProjectedEditarById(id);
        }

        List<PAProfesorComboProjection> categoriasProfesores = paProfesorCategoriaDao.findProjectedComboAllByActivoTrue();
        List<ProgramaComboProjection> programas = programaDao.findComboListadoAllByActivoIsTrue();
        HashMap<String, Object> json = new HashMap<>();

        json.put("tabulador",tabulador);
        json.put("categoriasProfesores", categoriasProfesores);
        json.put("programas",programas);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "EXEC sp_getListadoTabuladores";
        String columnas = "Código,Descripción,Pago de dias festivos";
        List<PAProfesorComboProjection> categoriasProfesores = paProfesorCategoriaDao.findProjectedComboAllByActivoTrue();
        for(PAProfesorComboProjection categoria:  categoriasProfesores){
            columnas +=","+categoria.getCategoria();
        }
        String[] alColumnas = columnas.split(",");

        excelController.downloadXlsx(response, "Tabuladores", query, alColumnas, null,"tabuladores");
    }

}

