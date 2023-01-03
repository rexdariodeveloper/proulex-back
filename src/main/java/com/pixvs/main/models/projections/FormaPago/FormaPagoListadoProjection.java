package com.pixvs.main.models.projections.FormaPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.FormaPago;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {FormaPago.class})
public interface FormaPagoListadoProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    Integer getArchivoId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
