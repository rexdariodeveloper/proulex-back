package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoContrato;
import com.pixvs.main.models.projections.EmpleadoContrato.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Rene Carrillo on 05/04/2022.
 */
public interface EmpleadoContratoDao extends CrudRepository<EmpleadoContrato, String> {
    EmpleadoContratoEditarProjection findProjectedEditarById(Integer empleadoContratoId);

    EmpleadoContratoEditarProjection findByEmpleadoId(Integer empleadoId);

    EmpleadoContratoEditarProjection findByEmpleadoIdAndEstatusId(Integer empleadoId, Integer estatusId);

    EmpleadoContratoEditarProjection findByIdAndEstatusId(Integer id, Integer estatusId);

    List<EmpleadoContratoEmpleadoProjection> findEmpleadoContratoEditarProjectionByEmpleadoIdAndEstatusId(Integer empleadoId, Integer estatusId);

    List<EmpleadoContratoEmpleadoProjection> findEmpleadoContratoEditarProjectionByIdAndEstatusId(Integer id, Integer estatusId);

    List<EmpleadoContratoComboEmpleadoProjection> findEmpleadoContratoComboEmpleadoProjectionByEstatusId(Integer estatusId);

    List<EmpleadoContratoComboEmpleadoProjection> findEmpleadoContratoComboEmpleadoProjectionByEmpleadoIdAndEstatusId(Integer empleadoId, Integer estatusId);

    EmpleadoContratoOfficeProjection findProjectedById(Integer idContrato);

    @Query("\n" +
            "SELECT \n" +
            "   empc \n" +
            "FROM EmpleadoContrato empc \n" +
            "   INNER JOIN Empleado emp ON emp.id = empc.empleadoId \n" +
            "   LEFT JOIN EntidadContrato entc ON entc.tipoContratoId = empc.tipoContratoId\n" +
            "WHERE \n" +
            "   YEAR(empc.fechaInicio) = :anio \n" +
            "   AND (:todosEmpleados = 1 OR emp.id IN(:idsEmpleado)) \n" +
            "")
    List<EmpleadoContratoOfficeProjection> findAllProjectedListadoContratos(@Param("anio") Integer anio, @Param("todosEmpleados") Integer todosEmpleados, @Param("idsEmpleado") String idsEmpleado);

    @Query(nativeQuery = true, value = "exec [dbo].[sp_getContratosListado] @entidadId = :entidadId, @anio = :anio, @todosEmpleados = :todosEmpleados, @idsEmpleados = :idsEmpleado")
    List<EmpladoContratoListadoProjection> findAllProjectedListadoContratosEntidad(@Param("entidadId") Integer entidadId, @Param("anio") Integer anio, @Param("todosEmpleados") Integer todosEmpleados, @Param("idsEmpleado") String idsEmpleado);

    //EmpleadoContratoOpenOfficeProjection
    @Query(nativeQuery = true, value = "exec [dbo].[sp_getContratoEmpleado] @idContrato = :idContrato, @idEntidad = :idEntidad")
    EmpleadoContratoOpenOfficeProjection findComboContratoOdtById(@Param("idContrato") Integer idContrato, @Param("idEntidad") Integer idEntidad);


}
