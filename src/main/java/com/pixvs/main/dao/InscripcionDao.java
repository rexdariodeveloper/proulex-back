package com.pixvs.main.dao;

import com.pixvs.main.models.Inscripcion;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Inscripcion.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2021.
 */
public interface InscripcionDao extends CrudRepository<Inscripcion, String> {

    // Modelo
    Inscripcion findById(Integer id);
    List<Inscripcion> findByOrdenVentaDetalleId(Integer ordenVentaDetalleId);
    List<Inscripcion> findAllByOrdenVentaDetalleIdAndEstatusIdIn(Integer ordenVentaDetalleId, List<Integer> estatusIds);
    @Query("" +
            "SELECT ins \n" +
            "FROM Inscripcion ins \n" +
            "INNER JOIN ins.grupo pg \n" +
            "INNER JOIN pg.modalidadHorario horario \n" +
            "INNER JOIN pg.programaIdioma pi \n" +
            "INNER JOIN ProgramaGrupo pgComparar ON pgComparar.id = :grupoId \n" +
            "INNER JOIN pgComparar.modalidadHorario horarioComparar \n" +
            "INNER JOIN pgComparar.programaIdioma piComparar \n" +
            "WHERE \n" +
            "   ins.alumnoId = :alumnoId \n" +
            "   AND ins.estatusId IN (" + ControlesMaestrosMultiples.CMM_INS_Estatus.PAGADA + "," + ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO + ") \n" +
            "   AND pg.fechaFin > CURRENT_DATE() \n" +
            "   AND pg.fechaInicio = pgComparar.fechaInicio \n" +
            "   AND ( \n" +
            "       ( \n" +
            "           (CASE WHEN COALESCE(pi.programa.jobs,false) = true THEN pi.programa.jobs ELSE COALESCE(pi.programa.jobsSems,false) END) = (CASE WHEN COALESCE(piComparar.programa.jobs,false) = true THEN piComparar.programa.jobs ELSE COALESCE(piComparar.programa.jobsSems,false) END) \n" + // Validación para permitir inscripciones entre cursos jobs y normales repitiendo idioma
            "           AND pi.idiomaId = piComparar.idiomaId \n" +
            "       ) OR (\n" +
            "           horarioComparar.horaInicio >= horario.horaInicio \n" +
            "           AND horarioComparar.horaInicio < horario.horaFin \n" +
            "       )\n" +
            "       OR (\n" +
            "           horarioComparar.horaFin > horario.horaInicio \n" +
            "           AND horarioComparar.horaFin <= horario.horaFin \n" +
            "       )\n" +
            "   ) \n" +
            "")
    List<Inscripcion> findAllByInterferenciaGrupo(@Param("grupoId") Integer grupoId, @Param("alumnoId") Integer alumnoId);
    @Query("" +
            "SELECT ins \n" +
            "FROM Inscripcion ins \n" +
            "WHERE \n" +
            "   ins.alumnoId = :alumnoId \n" +
            "   AND ins.grupoId = :grupoId \n" +
            "   AND ins.estatusId NOT IN (" + ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA + ")" +
            "")
    Inscripcion findByAlumnoIdAndGrupoIdAndNotCancelada(@Param("alumnoId") Integer alumnoId, @Param("grupoId") Integer grupoId);
    @Query("" +
            "SELECT ins \n" +
            "FROM Inscripcion ins \n" +
            "WHERE \n" +
            "   ins.alumnoId = :alumnoId \n" +
            "   AND ins.grupo.programaIdioma.programaId = :programaId \n" +
            "   AND ins.grupo.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND ins.grupo.nivel = :nivel \n" +
            "   AND ins.estatusId NOT IN (" + ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA + ")" +
            "")
    List<Inscripcion> findAllByAlumnoIdAndIdiomaIdAndNivelAndNotCancelada(@Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("nivel") Integer nivel);
    @Query("" +
            "SELECT ins \n" +
            "FROM Inscripcion ins \n" +
            "WHERE \n" +
            "   ins.alumnoId = :alumnoId \n" +
            "   AND ins.grupo.programaIdioma.programaId = :programaId \n" +
            "   AND ins.grupo.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND ins.grupo.nivel = :nivel \n" +
            "   AND ins.id NOT IN :inscripcionesOmitirIds \n" +
            "   AND ins.estatusId NOT IN (" + ControlesMaestrosMultiples.CMM_INS_Estatus.CANCELADA + ")" +
            "")
    List<Inscripcion> findAllByAlumnoIdAndIdiomaIdAndNivelAndNotCancelada(@Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("nivel") Integer nivel, @Param("inscripcionesOmitirIds") List<Integer> inscripcionesOmitirIds);

    List<InscripcionProjection> findAllProjectedByGrupoIdAndEstatusIdIsNot(@Param("grupoId") Integer grupoId, @Param("estatusId") Integer estatusId);

    InscripcionProjection findProjectedById(Integer id);

    List<InscripcionListadoGrupoProjection> findListadoGrupoByGrupoIdAndEstatusIdIsNot(Integer grupoId, Integer estatusId);
    @Query("" +
            "SELECT" +
            "   ins.id AS id, \n" +
            "   ins.alumno AS alumno, \n" +
            "   ins.grupoId AS grupoId, \n" +
            "   ins.estatus AS estatus \n" +
            "FROM Inscripcion ins \n" +
            "WHERE \n" +
            "   ins.grupoId = :grupoId \n" +
            "   AND ins.estatusId <> :estatusId \n" +
            "ORDER BY ins.alumno.primerApellido, ins.alumno.segundoApellido, ins.alumno.nombre" +
            "")
    List<InscripcionListadoGrupoProjection> findListadoGrupoByGrupoIdAndEstatusIdIsNotOrderByAlumno(@Param("grupoId") Integer grupoId, @Param("estatusId") Integer estatusId);

    @Query("" +
            "SELECT" +
            "   ins.id AS id, \n" +
            "   ins.alumno AS alumno, \n" +
            "   ins.grupoId AS grupoId, \n" +
            "   ins.estatus AS estatus, \n" +
            "   ov.codigo AS codigo, \n" +
            "   ov.id AS turnoId, \n" +
            "   ov.ligaCentroPagos AS carreraTexto, \n" +
            "   ov.metodoPagoId AS gradoId \n" +
            "FROM Inscripcion ins \n" +
            " INNER JOIN OrdenVentaDetalle ovd on ovd.id = ins.ordenVentaDetalleId \n" +
            " INNER JOIN OrdenVenta ov on ov.id = ovd.ordenVentaId \n" +
            "WHERE \n" +
            "   ins.grupoId = :grupoId \n" +
            "   AND ins.estatusId <> :estatusId AND ins.estatusId <> :estatusBajaId\n" +
            "ORDER BY ins.alumno.primerApellido, ins.alumno.segundoApellido, ins.alumno.nombre" +
            "")
    List<InscripcionListadoGrupoProjection> findListadoGrupoByGrupoIdAndEstatusIdIsNotOrderByAlumnoGrupo(@Param("grupoId") Integer grupoId, @Param("estatusId") Integer estatusId, @Param("estatusBajaId") Integer estatusBajaId);

    @Query("" +
            "SELECT" +
            "   ins.id AS id, \n" +
            "   ins.alumno AS alumno, \n" +
            "   ins.grupoId AS grupoId, \n" +
            "   ins.estatus AS estatus, \n" +
            "   ov.codigo AS codigo, \n" +
            "   ov.id AS turnoId, \n" +
            "   ov.ligaCentroPagos AS carreraTexto, \n" +
            "   ov.metodoPagoId AS gradoId \n" +
            "FROM Inscripcion ins \n" +
            " INNER JOIN OrdenVentaDetalle ovd on ovd.id = ins.ordenVentaDetalleId \n" +
            " INNER JOIN OrdenVenta ov on ov.id = ovd.ordenVentaId \n" +
            "WHERE \n" +
            "   ins.grupoId = :grupoId \n" +
            "   AND ins.estatusId <> :estatusId \n" +
            "ORDER BY ins.alumno.primerApellido, ins.alumno.segundoApellido, ins.alumno.nombre" +
            "")
    List<InscripcionListadoGrupoProjection> findInscripcionListadoGrupoProjectionByGrupoIdAndEstatusIdIsNotOrderByAlumnoGrupo(@Param("grupoId") Integer grupoId, @Param("estatusId") Integer estatusId);

    List<InscripcionProjection> findAllProjectedByAlumnoIdIn(List<Integer> alumnos);

    @Query("" +
            "SELECT" +
            "   ins.id AS id, \n" +
            "   ins.codigo AS codigo, \n" +
            "   ins.ordenVentaDetalleId AS ordenVentaDetalleId, \n" +
            "   ins.alumno AS alumno, \n" +
            "   ins.grupoId AS grupoId, \n" +
            "   ins.estatus AS estatus, \n" +
            "   ins.estatusId AS estatusId \n" +
            "FROM Inscripcion ins \n" +
            " INNER JOIN Alumno alu on alu.id = ins.alumnoId \n" +
            "WHERE \n" +
            "  alu.codigo = :codigoAlumno \n" +
            "ORDER BY ins.alumno.primerApellido, ins.alumno.segundoApellido, ins.alumno.nombre" +
            "")
    List<InscripcionProjection> findListadoGrupoByCodigoAlumno(@Param("codigoAlumno") String codigoAlumno);

    Inscripcion findFirstByAlumnoIdAndEstatusIdNotInOrderByFechaCreacionDesc(Integer alumnoId, List<Integer> estatus);

    @Query(value =
            "SELECT *,\n" +
            "       CASE WHEN sedeInscripcionId = sedeGrupoId THEN 'LOCAL' ELSE CASE WHEN sedeInscripcionId IN(:sedesId) THEN 'MULTISEDE ENVÍA' ELSE 'MULTISEDE RECIBE' END END AS tipoInscripcion\n" +
            "FROM VW_RPT_INSCRIPCIONES\n" +
            "WHERE (COALESCE(:sedesId, 0) = 0 OR (sedeInscripcionId IN(:sedesId) OR sedeGrupoId IN(:sedesId)))\n" +
            "       AND (COALESCE(:fechas, '') = '' OR FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas))\n" +
            "       AND CASE WHEN :notaVenta IS NULL THEN 1 ELSE CASE WHEN notaVenta = :notaVenta THEN 1 ELSE 0 END END = 1\n" +
            "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas(alumno, :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:estatusId, 0) = 0 OR estatusId IN (:estatusId))\n" +
            "ORDER BY inscripcion\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteInscripcionesProjection> fn_reporteInscripciones(@Param("sedesId") List<Integer> sedesId,
                                                                 @Param("fechas") List<String> fechas,
                                                                 @Param("notaVenta") String notaVenta,
                                                                 @Param("alumno") String alumno,
                                                                 @Param("modalidadesId") List<Integer> modalidadesId,
                                                                 @Param("estatusId") List<Integer> estatusId);

    @Query(value =
            "SELECT *, 'LOCAL' AS tipoInscripcion\n" +
            "FROM VW_RPT_INSCRIPCIONES\n" +
            "WHERE inscripcionId = :inscripcionId\n" +
            "ORDER BY inscripcion", nativeQuery = true)
    ReporteInscripcionesProjection findInscripcionReporteProjectedById(@Param("inscripcionId") Integer inscripcionId);

    @Query(value = "SELECT ins FROM Inscripcion ins " +
            "WHERE " +
            "ins.alumnoId = :alumnoId " +
            "AND ins.grupo.programaIdioma.programaId = :programaId " +
            "AND ins.grupo.programaIdioma.idiomaId = :idiomaId " +
            "AND ins.grupo.estatusId = 2000620 " +
            "AND ins.estatusId = 2000510")
    List<Inscripcion> findAllInscripcionesVigentes(@Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT ov.sucursalId from Inscripcion ins inner join ins.ordenVentaDetalle ovd inner join ovd.ordenVenta ov where ins.id = :inscripcionId")
    Integer findSucursalIdByInscripcionId(@Param("inscripcionId") Integer inscripcionId);

    Inscripcion findByAlumnoIdAndGrupoIdAndEstatusId(Integer alumnoId, Integer grupoId, Integer estatusId);
    Inscripcion findByAlumnoIdAndGrupoIdAndEstatusIdIn(Integer alumnoId, Integer grupoId, List<Integer> estatusIds);

    @Query(value = "SELECT INS FROM Inscripcion INS " +
            "INNER JOIN ProgramaGrupo GRUPO ON GRUPO.id = INS.grupoId " +
            "INNER JOIN OrdenVentaDetalle OVD ON OVD.id = INS.ordenVentaDetalleId " +
            "INNER JOIN OrdenVenta OV ON OV.id = OVD.ordenVentaId " +
            "WHERE GRUPO.fechaFinInscripcionesBecas < GETDATE() " +
            "AND GRUPO.grupoReferenciaId IS NOT NULL " +
            "AND INS.estatusId = 2000511 " +
            "AND OV.medioPagoPVId IS NULL " +
            "AND GRUPO.sucursalId NOT IN (38, 39)")
    List<Inscripcion> findAllByFechaFinExpirada();
    List<InscripcionProjection> findAllProjectedByGrupoId(@Param("grupoId") Integer grupoId);

    @Query("" +
            "SELECT" +
            "   ins.id AS id, \n" +
            "   ov.codigo AS codigoOv, \n" +
            "   alu.codigo AS codigoAlumno \n" +
            "FROM Inscripcion ins \n" +
            "INNER JOIN OrdenVentaDetalle ovd on ovd.id = ins.ordenVentaDetalleId \n" +
            "INNER JOIN OrdenVenta ov on ov.id = ovd.ordenVentaId \n" +
            "INNER JOIN Alumno alu on alu.id = ins.alumnoId\n" +
            "WHERE \n" +
            "   ov.ligaCentroPagos is null \n" +
            "   AND ins.estatusId = "+ ControlesMaestrosMultiples.CMM_INS_Estatus.PENDIENTE_DE_PAGO +"\n" +
            "   AND ins.grupoId = :grupoId")
    List<InscripcionBorrarProjection> findListadoByGrupoIdAndNoPagadas(@Param("grupoId") Integer grupoId);

    @Modifying
    @Query(nativeQuery = true, value =
            "UPDATE alertas\n" +
            "  SET\n" +
            "      ALE_CMM_EstatusId = 1000155, -- Cancelada\n" +
            "      ALE_FechaUltimaModificacion = GETDATE()\n" +
            "FROM Alertas AS alertas\n" +
            "WHERE ALE_ALC_AlertaCId = 12 -- Inscripciones multisede\n" +
            "      AND ALE_CMM_EstatusId = 1000151 -- En Proceso\n" +
            "      AND ALE_ReferenciaProcesoId = :inscripcionId")
    int actualizarAlertasMultisede(@Param("inscripcionId") Integer inscripcionId);

    @Query(value =
            "SELECT sede,\n" +
            "       inscripciones,\n" +
            "       ingresos,\n" +
            "       meta,\n" +
            "       avance\n" +
            "FROM VW_RptAvanceInscripciones\n" +
            "WHERE (COALESCE(:sedesId, 0) = 0 OR sedeId IN(:sedesId))\n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') = :fecha\n" +
            "       AND modalidadId = :modalidadId\n" +
            "ORDER BY sede,\n" +
            "         avance\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteAvanceInscripcionesProjection> fn_reporteAvanceInscripciones(@Param("sedesId") List<Integer> sedesId,
                                                                             @Param("fecha") String fecha,
                                                                             @Param("modalidadId") Integer modalidadId);

    Inscripcion saveAndFlush(Inscripcion inscripcion);
}
