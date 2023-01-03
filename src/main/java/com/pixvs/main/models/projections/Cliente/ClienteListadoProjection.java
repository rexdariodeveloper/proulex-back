package com.pixvs.main.models.projections.Cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Cliente;
import com.pixvs.main.models.Proveedor;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Cliente.class})
public interface ClienteListadoProjection {

    Integer getId();

    Boolean getActivo();

    String getCodigo();

    String getNombre();

    String getRfc();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
}