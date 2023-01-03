package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.*;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
public interface ProgramacionAcademicaComercialDao extends CrudRepository<ProgramacionAcademicaComercial, String> {

    // Modelo
    ProgramacionAcademicaComercial findById(Integer id);
    ProgramacionAcademicaComercial findByCodigo(String codigo);
    @Query("" +
            "SELECT pac \n" +
            "FROM ProgramacionAcademicaComercial pac \n" +
            "WHERE pac.codigo LIKE :codigoLike \n" +
            "ORDER BY pac.codigo DESC")
    List<ProgramacionAcademicaComercial> findByCodigoLike(@Param("codigoLike") String codigoLike);

    // ListadoProjection
    @Query("" +
            "SELECT \n" +
            "   pac.id AS id, \n" +
            "   pac.codigo AS codigo, \n" +
            "   pac.nombre AS nombre, \n" +
            "   MIN(pacd.fechaInicio) AS fechaInicio, \n" +
            "   MAX(pacd.fechaInicio) AS fechaFin, \n" +
            "   pac.paCiclo.codigo AS paCiclo, \n" +
            "   pac.activo AS activo \n" +
            "FROM ProgramacionAcademicaComercial pac \n" +
            "INNER JOIN pac.detalles pacd \n" +
            "GROUP BY pac.id, pac.codigo, pac.nombre, pac.paCiclo.codigo, pac.activo")
    List<ProgramacionAcademicaComercialListadoProjection> findProjectedListadoAllBy();

    // ComboProjection
    @Query(value = "Select pac from ProgramacionAcademicaComercial pac\n" +
            "inner join pac.detalles pacd \n" +
            "inner join pacd.programas pacdp \n" +
            "WHERE pacdp.id=:idPrograma")
    List<ProgramacionAcademicaComercialComboProjection> findProjectedComboAllByProgramaId(@Param("idPrograma") Integer idPrograma);

    // ComboProjection
    @Query(value = "Select pac from ProgramacionAcademicaComercial pac\n" +
            "inner join pac.detalles pacd \n" +
            "inner join pacd.programas pacdp \n" +
            "inner join pacdp.idiomas cursos\n" +
            "inner join cursos.sucursales cursuc \n" +
            "WHERE cursuc.sucursalId = :idSucursal")
    List<ProgramacionAcademicaComercialComboProjection> findProjectedComboAllBySucursalId(@Param("idSucursal") Integer idPrograma);

    @Query(value = "select distinct pa.id as id, pa.nombre as nombre, pa.codigo as codigo from ProgramaGrupo grupo " +
            "inner join grupo.programacionAcademicaComercial pa " +
            "where (grupo.sucursalId = :sedeId or grupo.sucursalPlantelId = :plantelId)" +
            "")
    List<ProgramacionAcademicaComercialComboFiltroProjection> findProjectedComboAllBySedeIdOrPlantelId(@Param("sedeId") Integer sedeId, @Param("plantelId") Integer plantelId);

    @Query(value = "select distinct pa.id as id, pa.nombre as nombre, pa.codigo as codigo from ProgramaGrupo grupo " +
            "inner join grupo.programacionAcademicaComercial pa " +
            "where (grupo.sucursalId = :sedeId or grupo.sucursalPlantelId in (:planteles))" +
            "")
    List<ProgramacionAcademicaComercialComboFiltroProjection> findProjectedComboAllBySedeIdOrPlantelIdIn(@Param("sedeId") Integer sedeId, @Param("planteles") List<Integer> planteles);

    @Query(value = "SELECT pac FROM ProgramacionAcademicaComercial pac " +
            "INNER JOIN pac.detalles pacd " +
            "INNER JOIN pacd.programas pacdp " +
            "INNER JOIN pacdp.idiomas cursos " +
            "INNER JOIN cursos.sucursales cursuc " +
            "WHERE cursuc.sucursalId IN(:sucursales)")
    List<ProgramacionAcademicaComercialComboFiltroProjection> findProjectedComboAllBySucursalIdIn(@Param("sucursales") List<Integer> sucursales);

    @Query(value = "Select pac.id as id, concat(pac.codigo,'-',pac.nombre) as nombre,pacd as detalles from ProgramacionAcademicaComercial pac\n" +
            "inner join pac.detalles pacd \n" +
            "inner join pacd.programas pacdp \n" +
            "WHERE pacdp.id=:idPrograma and pacd.paModalidadId=:idModalidad and pacd.idiomaId=:idIdioma \n"+
            "ORDER BY pacd.fechaInicio desc")
    List<ProgramacionAcademicaComercialComboProjection> findProjectedComboAllByProgramaIdAndModalidad(@Param("idPrograma") Integer idPrograma, @Param("idModalidad") Integer idModalidad, @Param("idIdioma") Integer idIdioma);

    @Query(value = "Select PAC_ProgramacionAcademicaComercialId as id,PAC_Codigo as codigo,\n" +
            "PAC_Nombre as nombre\n" +
            "from ProgramacionAcademicaComercial pac\n" +
            "INNER JOIN ProgramacionAcademicaComercialDetalles pacd on PACD_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId\n" +
            "INNER JOIN ProgramacionAcademicaComercialDetallesProgramas on PACDP_PACD_ProgramacionAcademicaComercialDetalleId = PACD_ProgramacionAcademicaComercialDetalleId\n" +
            "INNER JOIN Programas on PACD_PROG_ProgramaId = PROG_ProgramaId\n" +
            "WHERE PROG_ProgramaId = :idPrograma AND PACD_PAMOD_ModalidadId = :idModalidad AND PACD_CMM_IdiomaId=:idIdioma\n" +
            "ORDER BY PACD_FechaInicio", nativeQuery = true)
    List<ProgramacionAcademicaComercialComboProjection> findProjectedComboAllByProgramaIdAndModalidadPrueba(@Param("idPrograma") Integer idPrograma, @Param("idModalidad") Integer idModalidad, @Param("idIdioma") Integer idIdioma);


    @Query(value = "" +
            "SELECT pac \n" +
            "FROM ProgramacionAcademicaComercial pac \n" +
            "LEFT JOIN ProgramaMeta pm ON pm.programacionAcademicaComercialId = pac.id AND pm.activo = true \n" +
            "WHERE pac.activo = true AND pm IS NULL")
    List<ProgramacionAcademicaComercialComboProjection> findProjectedComboAllByActivoTrueAndNotProgramaMeta();

    // EditarProjection
    ProgramacionAcademicaComercialEditarProjection findProjectedEditarById(Integer id);

    // CursoProjection
    List<ProgramacionAcademicaComercialCursoProjection> findProjectedCursoAllBy();



}