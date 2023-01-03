package com.pixvs.main.dao;

import com.pixvs.main.models.SolicitudRenovacionContratacion;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacion.SolicitudRenovacionContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacion.SolicitudRenovacionContratacionListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SolicitudRenovacionContratacionDao extends CrudRepository<SolicitudRenovacionContratacion, String> {
    SolicitudRenovacionContratacionEditarProjection findProjectedEditarById(Integer solicitudNuevaContratacionId);
    SolicitudRenovacionContratacion findById(Integer solicitudNuevaContratacionId);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_Renovacion]")
    List<SolicitudRenovacionContratacionListadoProjection> findProjectedListadoAllBy();

    // ListadoProjection
    @Query(value = "SELECT e \n" +
            "FROM Empleado e \n" +
            "WHERE e.id IN ( \n" +
            "   SELECT ec.empleadoId \n" +
            "   FROM EmpleadoContrato ec \n" +
            "   WHERE ec.estatusId = 2000950 \n" +
            "       AND ec.fechaInicio >= :fechaInicio \n" +
            "       AND ec.fechaFin <= :fechaFin) \n" +
            "   AND e.estatusId = 2000950")
    List<EmpleadoEditarProjection> getRenovacionesConFiltros(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
