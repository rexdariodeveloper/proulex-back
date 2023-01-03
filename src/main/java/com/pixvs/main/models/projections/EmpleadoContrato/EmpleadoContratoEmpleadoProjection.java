package com.pixvs.main.models.projections.EmpleadoContrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.EmpleadoContrato;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboResponsabilidadProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import mx.bigdata.sat.common.ine.schema.INE;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Rene Carrillo on 30/08/2022.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoEmpleadoProjection {
    Integer getId();

    Integer getEmpleadoId();

    EmpleadoEditarProjection getEmpleado();

    String getCodigo();

    Integer getJustificacionId();

    Integer getTipoContratoId();

    ControlMaestroMultipleComboProjection getTipoContrato();

    Integer getPuestoId();

    String getIngresosAdicionales();

    BigDecimal getSueldoMensual();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    Integer getTipoHorarioId();

    Integer getCantidadHoraSemana();

    String getDomicilio();

    String getCp();

    String getColonia();

    Integer getPaisId();

    Integer getEstadoId();

    Integer getMunicipioId();

    Integer getEstatusId();
}
