package com.pixvs.main.models.projections.EmpleadoDeduccionPercepcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.EmpleadoDeduccionPercepcion;
import com.pixvs.main.models.projections.DeduccionPercepcion.DeduccionPercepcionEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.EmpleadoDeduccionPercepcionDocumento.EmpleadoDeduccionPercepcionDocumentoEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoDeduccionPercepcion.class})
public interface EmpleadoDeduccionPercepcionListadoProjection {


    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();

    @Value("#{target.empleado.nombre + ' ' + target.empleado.primerApellido +   (target.empleado.segundoApellido == null ? '' : ' ' + target.empleado.segundoApellido) }")
    String getEmpleadoNombre();

    ControlMaestroMultipleComboProjection getTipoMovimiento();

    DeduccionPercepcionEditarProjection getDeduccionPercepcion();

    BigDecimal getTotal();

    Boolean getActivo();

    SucursalComboProjection getSucursal();
}