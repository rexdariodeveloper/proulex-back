package com.pixvs.main.dao;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.Programa.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaDao extends CrudRepository<Programa, String> {

    Programa findById(Integer id);
    Programa findByCodigoAndActivoIsTrue(String codigo);

    List<ProgramaListadoProjection> findProjectedListadoAllByOrderByCodigo();

    ProgramaEditarProjection findProjectedEditarById(Integer id);

    List<ProgramaComboProjection> findComboListadoAllByActivoIsTrue();

    @Query("" +
            "SELECT DISTINCT \n" +
            "   p.id AS id, \n" +
            "   p.codigo AS codigo, \n" +
            "   p.nombre AS nombre, \n" +
            "   p.imagenId AS imagenId, \n" +
            "   p.jobsSems AS jobsSems \n" +
            "FROM Programa p \n" +
            "INNER JOIN p.idiomas pi \n" +
            "WHERE" +
            "   pi.idiomaId = :idiomaId \n" +
            "   AND p.activo = true \n" +
            "   AND pi.activo = true \n" +
            "   AND (p.jobs = false OR p.jobs IS NULL) \n" +
            "   AND (p.jobsSems = false OR p.jobsSems IS NULL) \n" +
            "")
    List<ProgramaCardProjection> findProjectedCardAllByIdiomaId(@Param("idiomaId") Integer idiomaId);
    ProgramaCardProjection findProjectedCardById(Integer id);

    ProgramaEditarProjection findProjectedEditarByCodigoAndActivoIsTrue(String codigo);
    @Query("" +
            "SELECT DISTINCT \n" +
            "   p.id AS id, \n" +
            "   p.codigo AS codigo, \n" +
            "   p.nombre AS nombre, \n" +
            "   pi.horasTotales AS horasTotales, \n" +
            "   pi.numeroNiveles AS numeroNiveles \n" +
            "FROM Programa p \n" +
            "INNER JOIN p.idiomas pi \n" +
            "INNER JOIN pi.modalidades pim \n" +
            "WHERE pi.idiomaId = :idiomaId AND pim.modalidadId = :modalidadId AND p.activo = true" +
            "")
    List<ProgramaCalcularDiasProjection> findProjectedCalcularDiasAllByIdiomaIdAndModalidadId(@Param("idiomaId") Integer idiomaId, @Param("modalidadId") Integer modalidadId);


    @Modifying
    @Query(value = "UPDATE Programas SET PROG_Activo = :activo WHERE PROG_ProgramaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);


    @Query(nativeQuery = true, value = "SELECT\n" +
            "PROG_ProgramaId as id,\n" +
            "PROG_Nombre as nombre,\n" +
            "PROG_Codigo as codigo\n" +
            "FROM Programas\n" +
            "INNER JOIN ProgramasIdiomas on PROGI_PROG_ProgramaId = PROG_ProgramaId\n" +
            "WHERE PROGI_CMM_Idioma=:idiomaId")
    List<ProgramaComboProjection> findProgramasByIdioma(@Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT DISTINCT " +
            "p.id as id, " +
            "p.nombre as nombre, " +
            "p.codigo as codigo " +
            "FROM Programa p " +
            "INNER JOIN p.idiomas pi " +
            "INNER JOIN pi.sucursales suc " +
            "WHERE suc.sucursalId IN (:sedes) AND p.activo = true")
    List<ProgramaComboProjection> findProgramasBySedeIdIn(@Param("sedes") List<Integer> sedes);

    List<ProgramaComboProjection> findAllByPcpTrueAndActivoTrueOrderByCodigo();
    ProgramaComboProjection findFirstByAcademyIsTrueAndActivoIsTrue();
}