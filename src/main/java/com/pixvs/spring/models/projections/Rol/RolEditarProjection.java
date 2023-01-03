package com.pixvs.spring.models.projections.Rol;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Rol;
import com.pixvs.spring.models.projections.RolMenu.RolMenuComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Rol.class})
public interface RolEditarProjection {

    Integer getId();

    String getNombre();

    Boolean getActivo();

    Integer getCreadoPorId();

    UsuarioComboProjection getCreadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    Integer getModificadoPorId();

    UsuarioComboProjection getModificadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
