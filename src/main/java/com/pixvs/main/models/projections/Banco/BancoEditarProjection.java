package com.pixvs.main.models.projections.Banco;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Banco;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Banco.class})
public interface BancoEditarProjection {

    Integer getId();

    String getCodigo();

    String getRfc();

    String getNombre();

    String getRazonSocial();

    boolean getActivo();

    int getCreadoPorId();

    Integer getModificadoPorId();

    UsuarioComboProjection getModificadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
