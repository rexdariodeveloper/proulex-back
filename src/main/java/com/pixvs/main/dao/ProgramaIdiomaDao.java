package com.pixvs.main.dao;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.*;
import com.pixvs.main.models.projections.Workshop.WorkshopListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaDao extends CrudRepository<ProgramaIdioma, String> {
    ProgramaIdiomaEditarProjection findProjectedEditarById(Integer id);
    ProgramaIdiomaEditarProjection findByProgramaIdAndIdiomaId(Integer programaId, Integer idiomaId);
    ProgramaIdioma findById(Integer id);
    ProgramaIdioma findByCodigo(String codigo);
    List<ProgramaIdiomaEditarProjection> findProjectedEditarAllByProgramaIdAndIdiomaIdAndActivoIsTrue(Integer programaId, Integer idiomaId);
    List<ProgramaIdiomaEditarProjection> findProjectedEditarAllByProgramaIdAndTipoWorkshopIdAndActivoIsTrue(Integer programaId, Integer tipoworkshopId);
    List<ProgramaIdioma> findAllByProgramaId(Integer id);
    List<ProgramaIdiomaComboProjection> findAllComboByProgramaId(Integer id);
    List<ProgramaIdiomaComboProjection> findAllByActivoIsTrue();
    List<ProgramaIdiomaComboSimpleProjection> findProjectionAllByActivoIsTrue();
    ProgramaIdiomaComboProjection findComboById(Integer id);
    //List<ProgramaIdiomaListadoProjection> findProjectedListadoAllBy();
    ProgramaIdioma findByProgramaIdAndIdiomaIdAndActivoIsTrue(Integer programaId, Integer idiomaId);
    void deleteById(Integer id);

    @Modifying
    @Query(value = "UPDATE ProgramasIdiomas SET PROGI_Activo = :activo WHERE PROGI_ProgramaIdiomaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(nativeQuery = true, value = "Select * from [dbo].[VW_LISTADO_PROGRAMAS_IDIOMAS] order by codigo")
    List<ProgramaIdiomaListadoProjection> findListadoOrderByCodigo();

    @Query(value = "Select distinct\n" +
            "progi.id as id, " +
            "concat(progi.programa.codigo,' ',progi.idioma.valor) as nombre," +
            "progi.programaId as programaId, " +
            "progi.programa.codigo as codigo," +
            "progi.numeroNiveles as numeroNiveles," +
            "progi.idiomaId as idiomaId," +
            "progi.idioma as idioma ,"+
            "tws as tipoWorkshop, "+
            "progi.programa.jobs as jobs ,"+
            "progi.programa.jobsSems as jobsSems ,"+
            "progi.programa.pcp as pcp ,"+
            "progi.programa.academy as academy, "+
            "progi.calificacionMinima as calificacionMinima," +
            "progi.faltasPermitidas as faltasPermitidas," +
            "progi.plataforma as plataforma\n" +
            "from ProgramaIdioma progi\n" +
            "INNER JOIN progi.sucursales suc on suc.programaIdiomaId = progi.id\n" +
            "INNER JOIN progi.idioma idi on idi.id = progi.idiomaId\n" +
            "LEFT JOIN progi.tipoWorkshop tws on tws.id = progi.tipoWorkshopId "+
            "WHERE suc.sucursalId= :idSucursal AND progi.activo=true")
    List<ProgramaIdiomaComboSucursalesProjection> findCursosBySucursales(@Param("idSucursal") Integer idSucursal);

    @Query(value = "" +
            "SELECT DISTINCT" +
            "   progi.id AS id, \n" +
            "   progi.programa AS programa, \n" +
            "   progi.idioma AS idioma \n" +
            "FROM ProgramaIdioma progi \n" +
            "INNER JOIN progi.sucursales progis \n" +
            "WHERE \n" +
            "   progi.activo = true \n" +
            "   AND progis.sucursalId IN :sucursalesIds \n" +
            "")
    List<ProgramaIdiomaComboSimpleProjection> findProjectedComboSimpleAllBySucursalIdIn(@Param("sucursalesIds") List<Integer> sucursalesIds);

    @Query(value = "" +
            "SELECT MAX(progi.numeroNiveles) \n" +
            "FROM ProgramaIdioma progi \n" +
            "WHERE progi.id IN :programaIdiomaIds \n" +
            "")
    Integer getMaxNivelByIdIn(@Param("programaIdiomaIds") List<Integer> programaIdiomaIds);

    @Query(value = "" +
            "SELECT curso FROM ProgramaIdioma curso " +
            "INNER JOIN curso.certificaciones ex_cert " +
            "WHERE ex_cert.certificacionId = :articuloId")
    List<ProgramaIdiomaComboProjection> findProjectedComboSimpleByExamenCertificacionId(@Param("articuloId") Integer articuloId);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CMO_ComboProgramaIdiomaDescuentoCertificacion]")
    List<ProgramaIdiomaComboDescuentoCertificacionProjection> findProgramaIdiomaComboDescuentoCertificacionProjection();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_ProgramaIdiomaDescuentoCertificacionIdProgramaIdioma] WHERE Id = :programaIdiomaId")
    ProgramaIdiomaComboDescuentoCertificacionProjection findProgramaIdiomaComboDescuentoCertificacionProjection(@Param("programaIdiomaId") Integer programaIdiomaId);

    /* Diplomados */
    @Query(nativeQuery = true, value = "Select * from [dbo].[VW_LISTADO_WORKSHOPS] order by nombre, tipo")
    List<WorkshopListadoProjection> findListadoWorkshopsOrderByCodigo();
}