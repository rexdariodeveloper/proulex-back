package com.pixvs.main.models.projections.SolicitudBajaContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudBajaContratacion;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoEmpleadoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 31/08/2022.
 */
@Projection(types = {SolicitudBajaContratacion.class})
public interface SolicitudBajaContratacionEditarProjection {
    Integer getId();

    String getCodigo();

    Integer getEmpleadoContratoId();

    EmpleadoContratoEmpleadoProjection getEmpleadoContrato();

    ControlMaestroMultipleComboProjection getTipoMotivo();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaSeparacion();

    String getComentario();

    Integer getEstatusId();
}
