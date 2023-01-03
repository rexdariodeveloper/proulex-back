package com.pixvs.spring.models.projections.Usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleListadoProjection;
import com.pixvs.spring.models.projections.Rol.RolComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


@Projection(types = {Usuario.class})
public interface UsuarioEditarProjection {

    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getCorreoElectronico();

    ControlMaestroMultipleListadoProjection getEstatus();

    Integer getTipoId();

    Integer getEstatusId();

    RolComboProjection getRol();

    Integer getRolId();

    Integer getArchivoId();

    Integer getUsuarioModificadoPorId();

    Integer getUsuarioCreadoPorId();

    String getCodigo();

    UsuarioComboProjection getCreadoPor();

    UsuarioComboProjection getModificadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
