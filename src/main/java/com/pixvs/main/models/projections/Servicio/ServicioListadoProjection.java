package com.pixvs.main.models.projections.Servicio;

import com.pixvs.main.models.Servicio;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Servicio.class})
public interface ServicioListadoProjection {

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

}
