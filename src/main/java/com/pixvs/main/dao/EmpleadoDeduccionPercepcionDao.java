package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoDeduccionPercepcion;
import com.pixvs.main.models.projections.EmpleadoDeduccionPercepcion.EmpleadoDeduccionPercepcionEditarProjection;
import com.pixvs.main.models.projections.EmpleadoDeduccionPercepcion.EmpleadoDeduccionPercepcionListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface EmpleadoDeduccionPercepcionDao extends CrudRepository<EmpleadoDeduccionPercepcion, String> {

    List<EmpleadoDeduccionPercepcionListadoProjection> findProjectedListadoAllByActivoIsTrueOrderByCodigo();

    /*@Query(nativeQuery = true, value = "SELECT * FROM Empleados WHERE EMP_Activo = 1")
    List<EmpleadoComboProjection> findAllBy();*/

    EmpleadoDeduccionPercepcionEditarProjection findProjectedEditarById(Integer id);

    EmpleadoDeduccionPercepcion findById(Integer id);

    @Modifying
    @Query(value = "UPDATE EmpleadosDeduccionesPercepciones SET EDP_Activo = :activo WHERE EDP_EmpleadoDeduccionPercepcionId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);


    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/


}