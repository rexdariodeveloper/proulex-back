package com.pixvs.spring.models.projections.Usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleListadoProjection;
import com.pixvs.spring.models.projections.Rol.RolComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


@Projection(types = {Usuario.class})
public interface UsuarioListadoProjection {

    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getCorreoElectronico();

    RolComboProjection getRol();

    ControlMaestroMultipleListadoProjection getEstatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    Integer getEstatusId();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

}
