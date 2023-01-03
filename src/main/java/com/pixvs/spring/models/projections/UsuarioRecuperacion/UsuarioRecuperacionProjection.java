package com.pixvs.spring.models.projections.UsuarioRecuperacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.UsuarioRecuperacion;
import com.pixvs.spring.models.projections.Usuario.UsuarioListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


@Projection(types = {UsuarioRecuperacion.class})
public interface UsuarioRecuperacionProjection {

    Integer getId();

    UsuarioListadoProjection getUsuario();

    String getToken();

    Integer getEstatus();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaSolicitud();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaExpiracion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaUltimaModificacion();

}
