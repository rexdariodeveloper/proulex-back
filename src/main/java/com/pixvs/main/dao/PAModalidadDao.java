package com.pixvs.main.dao;

import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAModalidad;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.main.models.projections.PAModalidad.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PAModalidadDao extends CrudRepository<PAModalidad, String> {

    // Modelo
    PAModalidad findById(Integer id);
    PAModalidad findByCodigo(String codigo);

    List<PAModalidadListadoProjection> findProjectedListadoAllByActivoTrue();
    List<PAModalidadComboProjection> findProjectedComboAllByActivoTrue();
    List<PAModalidadComboProjection> findProjectedComboAllByActivoTrueOrderByNombre();
    List<PAModalidadComboSimpleProjection> findComboAllByActivoTrueOrderByNombre();
    PAModalidad findByCodigoAndActivoTrue(String codigo);

    List<PAModalidadDiasProjection> findProjectedDiasAllByActivoTrue();
    List<PAModalidadDiasProjection> findProjectedDiasAllByActivoTrueOrderByNombre();

    PAModalidadComboSimpleProjection findComboSimpleProjectedById(Integer id);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   pamod.id AS id, \n" +
            "   pamod.codigo AS codigo, \n" +
            "   pamod.nombre AS nombre, \n" +
            "   pamod.imagenId AS imagenId \n" +
            "FROM PAModalidad pamod \n" +
            "INNER JOIN ProgramaIdiomaModalidad pid ON pid.modalidadId = pamod.id \n" +
            "INNER JOIN ProgramaIdioma pi ON pid.programaIdiomaId = pi.id \n" +
            "WHERE pamod.activo = true AND pi.idiomaId = :idiomaId AND pi.programaId = :programaId \n" +
            "")
    List<PAModalidadCardProjection> findProjectedCardAllByActivoTrueAndIdiomaIdAndProgramaId(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId);
    PAModalidadCardProjection findProjectedCardById(Integer id);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   pamod.id AS id, \n" +
            "   pamod.codigo AS codigo, \n" +
            "   pamod.nombre AS nombre \n" +
            "FROM PAModalidad pamod \n" +
            "INNER JOIN ProgramaIdiomaModalidad pid ON pid.modalidadId = pamod.id \n" +
            "INNER JOIN ProgramaIdioma pi ON pid.programaIdiomaId = pi.id \n" +
            "WHERE pamod.activo = true AND pi.idiomaId = :idiomaId AND pi.programaId = :programaId \n" +
            "")
    List<PAModalidadComboSimpleProjection> findProjectedComboSimpleAllByActivoTrueAndIdiomaIdAndProgramaId(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId);
    @Query("" +
            "SELECT DISTINCT \n" +
            "   pamod.id AS id, \n" +
            "   pamod.codigo AS codigo, \n" +
            "   pamod.nombre AS nombre \n" +
            "FROM PAModalidad pamod \n" +
            "INNER JOIN ProgramaIdiomaModalidad pid ON pid.modalidadId = pamod.id \n" +
            "INNER JOIN ProgramaIdioma pi ON pid.programaIdiomaId = pi.id \n" +
            "WHERE pamod.activo = true AND pi.id IN :programaIdiomaIds \n" +
            "")
    List<PAModalidadComboSimpleProjection> findProjectedComboSimpleAllByProgramaIdiomaIdIn(@Param("programaIdiomaIds") List<Integer> programaIdiomaIds);

    @Query(value = "select distinct modalidad.id as id, modalidad.codigo as codigo, modalidad.nombre as nombre from ProgramaGrupo grupo " +
            "inner join grupo.paModalidad modalidad " +
            "where (grupo.sucursalId = :sedeId or grupo.sucursalPlantelId = :plantelId) " +
            "and (grupo.programacionAcademicaComercialId = :paId or grupo.paCicloId = :cicloId) " +
            "")
    List<PAModalidadComboSimpleProjection> findProjectedComboSimpleAllBySedeIdAndPaId(@Param("sedeId") Integer sedeId, @Param("plantelId") Integer plantelId, @Param("paId") Integer paId, @Param("cicloId") Integer cicloId);

    @Query(value = "select distinct modalidad.id as id, modalidad.codigo as codigo, modalidad.nombre as nombre from ProgramaGrupo grupo " +
            "inner join grupo.paModalidad modalidad " +
            "where (grupo.sucursalId = :sedeId or grupo.sucursalPlantelId in (:planteles)) " +
            "and (grupo.programacionAcademicaComercialId = :paId or grupo.paCicloId = :cicloId) " +
            "")
    List<PAModalidadComboSimpleProjection> findProjectedComboSimpleAllByFiltros(@Param("sedeId") Integer sedeId, @Param("planteles") List<Integer> planteles, @Param("paId") Integer paId, @Param("cicloId") Integer cicloId);

    @Modifying
    @Query(value = "UPDATE PAModalidades SET PAMOD_Activo = :activo WHERE PAMOD_ModalidadId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    PAModalidadComboProjection findProjectedComboById(Integer id);

    List<PAModalidadComboSimpleProjection> findComboAllByActivoTrueAndIdInOrderByNombre(@Param("listaModalidadId") List<Integer> listaModalidadId);

    @Query(value =
            "Select distinct paMod from PAModalidad paMod \n" +
                    "inner join ProgramaIdiomaModalidad progim on progim.modalidadId = paMod.id " +
                    "inner join ProgramaIdioma progi on progi.id = progim.programaIdiomaId " +
                    "inner join Programa prog on prog.id = progi.programaId " +
                    "inner join ControlMaestroMultiple cmm on cmm.id = progi.idiomaId " +
                    "where prog.id IN (:programasId) AND cmm.id IN (:idiomasId) " +
                    "order by paMod.id")
    List<PAModalidadComboProjection> getModalidadesByProgramaAndIdioma(@Param("programasId") List<Integer> programasId,
                                                          @Param("idiomasId") List<Integer> idiomasId);


    @Query(value = "" +
            "SELECT DISTINCT \n" +
            "   pamod.PAMOD_ModalidadId AS id, \n" +
            "   pamod.PAMOD_Codigo AS codigo, \n" +
            "   pamod.PAMOD_Nombre AS nombre \n" +
            "FROM PAModalidades pamod \n" +
            "INNER JOIN ProgramasIdiomasModalidades pid ON pid.PROGIM_PAMOD_ModalidadId = pamod.PAMOD_ModalidadId \n" +
            "INNER JOIN ProgramasIdiomas pi ON pid.PROGIM_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId \n" +
            "WHERE pamod.PAMOD_Activo = 1 AND pi.PROGI_ProgramaIdiomaId = :idiomaId AND pi.PROGI_PROG_ProgramaId = :programaId \n" +
            "", nativeQuery = true)
    List<PAModalidadComboSimpleProjection> getListaModalidad(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId);
}
