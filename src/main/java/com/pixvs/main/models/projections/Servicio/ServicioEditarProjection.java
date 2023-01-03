package com.pixvs.main.models.projections.Servicio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Servicio;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Servicio.class})
public interface ServicioEditarProjection {

    Integer getId();
    String getConcepto();
    String getDescripcion();
    Integer getTipoServicioId();
    ControlMaestroMultipleEditarProjection getTipoServicio();
    Integer getArticuloId();
    ArticuloEditarProjection getArticulo();
    Boolean getRequiereXML();
    Boolean getRequierePDF();
    Boolean getActivo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    Integer getCreadoPorId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    Integer getModificadoPorId();

}
