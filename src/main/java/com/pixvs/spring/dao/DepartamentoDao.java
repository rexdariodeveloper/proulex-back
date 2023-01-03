package com.pixvs.spring.dao;

import com.pixvs.spring.models.Departamento;
import com.pixvs.spring.models.projections.Departamento.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 */
public interface DepartamentoDao extends CrudRepository<Departamento, String> {

    List<DepartamentoListadoProjection> findProjectedListadoAllBy();

    List<DepartamentoComboProjection> findProjectedComboAllByActivoTrue();

    @Query("" +
            "SELECT d \n" +
            "FROM Departamento d \n" +
            "INNER JOIN d.usuariosPermisos u \n" +
            "WHERE d.activo = true AND u.id = :usuarioId \n" +
            "ORDER BY d.nombre \n" +
            "")
    List<DepartamentoComboProjection> findProjectedComboAllByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    DepartamentoEditarProjection findProjectedEditarById(Integer id);

    List<DepartamentoNodoProjection> findProjectedNodoAllByActivoTrueAndDepartamentoPadreIdIsNull();
    List<DepartamentoNodoSelectedProjection> findProjectedNodoSelectedAllByActivoTrueAndAutorizaTrueAndDepartamentoPadreIdIsNull();

    Departamento findById(Integer id);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM [dbo].[fn_departamentosDescendientes](:raizId) ORDER BY DEP_FechaCreacion DESC")
    List<Departamento> findAllFnDepartamentosDescendientes(@Param("raizId") Integer raizId);


    @Modifying
    @Query(value = "UPDATE Departamentos SET DEP_Activo = :activo WHERE DEP_DepartamentoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    Integer countByPrefijo(String prefijo);
    Integer countByIdNotAndPrefijo(Integer id, String prefijo);

    void deleteById(Integer id);

    @Query(value = "SELECT * FROM fn_getDatosAdicionalesUsuarioDepartamentos(:usuarioId) ORDER BY Orden", nativeQuery = true)
    List<DepartamentoDatosAdicionalesEmpleadoProjection> findProjectedDatosAdicionalesByUsuarioId(@Param("usuarioId") Integer usuarioId);

    List<DepartamentoComboResponsabilidadProjection> findProjectedComboResponsabilidadAllByActivoTrue();
}