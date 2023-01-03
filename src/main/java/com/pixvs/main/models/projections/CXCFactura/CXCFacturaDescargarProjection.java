package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CXCFactura.class})
public interface CXCFacturaDescargarProjection {

    Integer getId();

    String getVersion();

    String getSerie();

    String getFolio();

    String getXml();

    int getEstatusId();
}