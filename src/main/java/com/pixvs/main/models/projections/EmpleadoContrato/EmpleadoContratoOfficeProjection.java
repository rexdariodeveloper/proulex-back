package com.pixvs.main.models.projections.EmpleadoContrato;

import com.pixvs.main.models.EmpleadoContrato;
import com.pixvs.main.models.projections.Empleado.EmpleadoInforAdicionalProjected;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Benjamin Osorio Bautista on 04/12/2021.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoOfficeProjection {

    Integer getId();

    Integer getEmpleadoId();

    ControlMaestroMultipleComboProjection getJustificacion();

    ControlMaestroMultipleComboProjection getTipoContrato();

    String getFolioContrato();

    @Value("#{target.fechaInicio}")
    Date getFechaAlta();

    Date getFechaInicio();

    Date getFechaFin();

    String getPuesto();

    String getDiasTrabajo();

    String getHorarioTrabajo();

    BigDecimal getSueldoMensual();

    BigDecimal getIngresosAdicionales();

    String getDomicilio();

    String getCp();

    String getColonia();

    String getResponsabilidades();

    EmpleadoInforAdicionalProjected getEmpleado();

    Boolean getMostrarCargaHoraria();

    Integer getCargaHoraria();

    String getPropositoSueldo();

}
