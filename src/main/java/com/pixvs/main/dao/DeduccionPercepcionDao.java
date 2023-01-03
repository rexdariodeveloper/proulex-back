package com.pixvs.main.dao;

import com.pixvs.main.models.Alumno;
import com.pixvs.main.models.DeduccionPercepcion;
import com.pixvs.main.models.projections.Alumno.*;
import com.pixvs.main.models.projections.DeduccionPercepcion.DeduccionComboProjection;
import com.pixvs.main.models.projections.DeduccionPercepcion.DeduccionPercepcionEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 31/05/2021.
 */
public interface DeduccionPercepcionDao extends CrudRepository<DeduccionPercepcion, String> {

    // Modelo
    DeduccionPercepcion findById(Integer id);

    // ListadoProjection
    List<DeduccionPercepcionEditarProjection> findAllByActivoIsTrue();

    List<DeduccionComboProjection> findAllByTipoId(Integer id);
    List<DeduccionComboProjection> findAllByTipoIdAndActivoIsTrue(Integer id);

    @Modifying
    @Query(value = "UPDATE DeduccionesPercepciones SET DEDPER_Activo = :activo WHERE DEDPER_DeduccionPercepcionId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
