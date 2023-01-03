package com.pixvs.main.models.projections.EmpleadoContrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.EmpleadoContrato;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Puesto.PuestoComboContratosProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboResponsabilidadProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 05/04/2022.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoEditarProjection {
    Integer getId();

    Integer getEmpleadoId();

    String getCodigo();

    ControlMaestroMultipleComboProjection getJustificacion();

    ControlMaestroMultipleComboProjection getTipoContrato();

    PuestoComboContratosProjection getPuesto();

    String getIngresosAdicionales();

    BigDecimal getSueldoMensual();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    ControlMaestroMultipleComboProjection getTipoHorario();

    Integer getCantidadHoraSemana();

    String getDomicilio();

    String getCp();

    String getColonia();

    PaisComboProjection getPais();

    EstadoComboProjection getEstado();

    MunicipioComboProjection getMunicipio();

    Integer getEstatusId();
}
