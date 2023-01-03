package com.pixvs.main.models.projections.CXPFactura;

import com.pixvs.main.models.CXPFactura;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CXPFactura.class})
public interface CXPFacturaDescargarProjection {

    Integer getXMLId();

    Integer getPDFId();

    String getPathArchivo();
}