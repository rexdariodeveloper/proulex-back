package com.pixvs.main.models.projections.Proveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Proveedor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorListadoProjection {


    Integer getId();

    Boolean getActivo();

    String getCodigo();

    String getNombre();

    String getRfc();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

}