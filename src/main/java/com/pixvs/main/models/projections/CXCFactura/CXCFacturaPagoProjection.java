package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {CXCFactura.class})
public interface CXCFacturaPagoProjection {

    Integer getId();

    String getSerie();

    String getFolio();

    String getUuid();
}