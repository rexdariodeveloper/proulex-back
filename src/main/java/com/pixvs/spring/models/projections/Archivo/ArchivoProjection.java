package com.pixvs.spring.models.projections.Archivo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Archivo.class})
public interface ArchivoProjection {

    Integer getId();

    String getNombreOriginal();

    ControlMaestroMultipleComboProjection getTipo();

    String getNombreFisico();

    String getRutaFisica();

    Boolean getPublico();

    Boolean getActivo();

    UsuarioComboProjection getCreadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaCreacion();

}
