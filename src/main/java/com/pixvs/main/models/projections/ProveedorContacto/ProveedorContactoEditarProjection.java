package com.pixvs.main.models.projections.ProveedorContacto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Projection(types = {ProveedorContacto.class})
public interface ProveedorContactoEditarProjection {


    Integer getId();

    Boolean getActivo();

    Integer getProveedorId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getDepartamento();

    String getTelefono();

    String getExtension();

    String getCorreoElectronico();

    String getHorarioAtencion();

    ControlMaestroMultipleComboProjection getTipoContacto();

    Boolean getPredeterminado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}