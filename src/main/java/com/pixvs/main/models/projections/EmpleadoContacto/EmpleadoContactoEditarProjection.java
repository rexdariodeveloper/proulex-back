package com.pixvs.main.models.projections.EmpleadoContacto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoEditarProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoContacto.class})
public interface EmpleadoContactoEditarProjection {


    Integer getId();

    Integer getEmpleadoId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getParentesco();

    String getTelefono();

    String getMovil();

    String getCorreoElectronico();

    Boolean getBorrado();

}