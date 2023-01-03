package com.pixvs.main.dao;

import com.pixvs.main.models.AlumnoExamenCertificacion;
import com.pixvs.main.models.projections.AlumnoExamenCertificacion.AlumnoExamenCertificacionEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCertificacion.AlumnoExamenCertificacionProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 23/11/2021.
 */
public interface AlumnoExamenCertificacionDao extends CrudRepository<AlumnoExamenCertificacion, String> {

    // Modelo
    AlumnoExamenCertificacion findById(Integer id);
    AlumnoExamenCertificacion findByAlumnoIdAndEstatusIdAndTipoId(Integer alumnoId, Integer estatusId, Integer tipoId);
    @Query("" +
            "SELECT aluec \n" +
            "FROM AlumnoExamenCertificacion aluec \n" +
            "INNER JOIN ProgramaIdioma progi ON progi.id = aluec.cursoId \n" +
            "WHERE \n" +
            "   aluec.tipoId = :tipoId \n" +
            "   AND aluec.alumnoId = :alumnoId \n" +
            "   AND progi.programaId = :programaId \n" +
            "   AND progi.idiomaId = :idiomaId \n" +
            "")
    List<AlumnoExamenCertificacion> findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaId(@Param("tipoId") Integer tipoId, @Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);
    @Query("" +
            "SELECT aluec \n" +
            "FROM AlumnoExamenCertificacion aluec \n" +
            "INNER JOIN ProgramaIdiomaCertificacion progic ON progic.certificacionId = aluec.articuloId \n" +
            "INNER JOIN ProgramaIdioma progi ON progi.id = progic.programaIdiomaId \n" +
            "WHERE \n" +
            "   aluec.tipoId = :tipoId \n" +
            "   AND aluec.alumnoId = :alumnoId \n" +
            "   AND aluec.cursoId IS NULL \n" +
            "   AND progi.programaId = :programaId \n" +
            "   AND progi.idiomaId = :idiomaId \n" +
            "")
    List<AlumnoExamenCertificacion> findAllByTipoIdAndAlumnoIdAndProgramaIdAndIdiomaIdAndNoRelacionado(@Param("tipoId") Integer tipoId, @Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);

    // AlumnoExamenCertificacionProjection
    @Query(value = "SELECT * " +
            "FROM [dbo].[VW_ALUMNOS_EXAMENES_CERTIFICACIONES] " +
            "WHERE (:codigo IS NULL OR codigoAlumno = :codigo) " +
            "AND (:tipoId IS NULL OR tipoId = :tipoId) " +
            "AND (:estatusId IS NULL OR estatusId = :estatusId) ", nativeQuery = true)
    List<AlumnoExamenCertificacionProjection> getListadoExamenesCertificaciones(@Param("codigo") String codigo,
                                                                             @Param("tipoId") Integer tipoId,
                                                                             @Param("estatusId") Integer estatusId);

    AlumnoExamenCertificacionEditarProjection findProjectedEditarById(Integer id);
}
