package com.pixvs.spring.models.projections.Usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.projections.Rol.RolComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Usuario.class})
public interface UsuarioLoginProjection {

    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getCorreoElectronico();

    Integer getEstatusId();

    Integer getRolId();

    Integer getArchivoId();

    RolComboProjection getRol();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    UsuarioComboProjection getCreadoPor();

    UsuarioComboProjection getModificadoPor();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

}
