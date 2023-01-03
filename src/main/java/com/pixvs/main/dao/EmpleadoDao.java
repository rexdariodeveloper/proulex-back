package com.pixvs.main.dao;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.*;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOpenOfficeProjection;
import com.pixvs.main.models.projections.Proveedor.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface EmpleadoDao extends CrudRepository<Empleado, String> {

    List<EmpleadoListadoProjection> findProjectedListadoAllByEmpleadoContratoIdNull();

    List<EmpleadoComboProjection> findAllByEstatusIdIn(List<Integer> estatusIds);

    /*@Query(nativeQuery = true, value = "SELECT * FROM Empleados WHERE EMP_Activo = 1")
    List<EmpleadoComboProjection> findAllBy();*/

    List<EmpleadoComboProjection> findAllByEstatusIdNotIn(List<Integer> estatusIds);

    List<EmpleadoComboProjection> findAllByTipoEmpleadoId(Integer tipoEmpleadoId);

    List<EmpleadoComboProjection> findAllByTipoEmpleadoIdIn(List<Integer> tipoEmpleadoId);

    List<EmpleadoEditarProjection> findAllByCorreoElectronico(String correoElectronico);

    List<EmpleadoEditarProjection> findAllByCodigoEmpleado(String codigoEmpleado);

    EmpleadoEditarProjection findProjectedEditarById(Integer id);

    EmpleadoComboProjection findComboById(Integer id);

    //List<ProveedorEditarProjection> findProjectedEditarByRfc(String rfc);

    Empleado findById(Integer id);

    Empleado findByUsuarioId(Integer usuarioId);

    @Modifying
    @Query(value = "UPDATE Empleados SET EMP_CMM_EstatusId = :estatusId WHERE EMP_EmpleadoId = :id",
            nativeQuery = true)
    int actualizarEstatusBorrado(@Param("id") Integer id, @Param("estatusId") Integer estatusId);


    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/

    List<EmpleadoComboProjection> findAllByTipoEmpleadoIdAndEstatusIdNotInOrderByPrimerApellidoAscSegundoApellidoAscNombreAsc(Integer tipoEmpleadoId, List<Integer> estatusIds);

    // ListadoProjection
    @Query(nativeQuery = true, value = "SELECT * FROM Empleados")
    List<EmpleadoEditarProjection> findAllBy();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getFormatoContrato](:empleadoContratoId, :entidadId)")
    EmpleadoContratoOpenOfficeProjection findFormatoContratoBy(@Param("empleadoContratoId") Integer empleadoContratoId, @Param("entidadId") Integer entidadId);

    EmpleadoBajaProjection findEmpleadoBajaProjectionById(Integer id);

    @Query(value = "SELECT emp FROM Empleado emp")
    List<EmpleadoComboProjection> findAllEmpleadoCombo();
    //List<EmpleadoComboProjection> findAllByActivoIsTrue();

    EmpleadoContratoProjection findComboContratoById(Integer id);



    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getListaPolizas](:idEntidad, :idEmpleado)")
    List<EmpleadoPolizaListadoProjection> findAllProjectedListadoPolizas(@Param("idEntidad") Integer idEntidad, @Param("idEmpleado") Integer idEmpleado);

    List<EmpleadoComboProjection> findProjectedListadoAllByEstatusId(Integer activo);

    @Query(nativeQuery = true, value = "exec [dbo].[sp_getEmpleadosEntidad] @entidadId = :entidadId, @todosEmpleados = :todosEmpleados, @idsEmpleados = :idsEmpleados")
    List<EmpleadoComboContratoProjection> findAllEmpleadoComboEntidades(@Param("entidadId") Integer entidadId, @Param("todosEmpleados") Integer todosEmpleados, @Param("idsEmpleados") Integer idsEmpleados);

    @Query(nativeQuery = true, value = "exec [dbo].[sp_getEmpleadosEntidad] @entidadId = :entidadId, @todosEmpleados = :todosEmpleados, @idsEmpleados = :idsEmpleados")
    EmpleadoComboContratoProjection findEmpleadoComboEntidad(@Param("entidadId") Integer entidadId, @Param("todosEmpleados") Integer todosEmpleados, @Param("idsEmpleados") String idsEmpleados);

}