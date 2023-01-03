package com.pixvs.main.dao;

import com.pixvs.main.models.AlumnoExamenCalificacion;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionResumenProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlumnoExamenCalificacionDao extends CrudRepository<AlumnoExamenCalificacion, String> {

    AlumnoExamenCalificacion findById(Integer id);

    List<AlumnoExamenCalificacionProjection> findAllProjectedByGrupoId(Integer grupoId);
    List<AlumnoExamenCalificacionProjection> findAllByGrupoIdAndAlumnoId(Integer grupoId, Integer alumnoId);

    AlumnoExamenCalificacion findByAlumnoIdAndProgramaIdiomaExamenDetalleId(Integer alumnoId, Integer detalleId);

    @Query(value = "SELECT alumnoId, grupoId, calificacion " +
            "FROM [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION] " +
            "WHERE alumnoId = :alumnoId " +
            "AND grupoId = :grupoId", nativeQuery = true)
    AlumnoExamenCalificacionResumenProjection getCalificacionFinalByAlumnoIdAndGrupoId(@Param("alumnoId") Integer alumnoId, @Param("grupoId") Integer grupoId);

    void deleteAllByAlumnoIdAndGrupoId(Integer alumnoId, Integer grupoId);

}
