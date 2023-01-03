package com.pixvs.main.models.projections.CXCFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCFactura;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {CXCFactura.class})
public interface ListadoFacturasNotaVentaCXCFacturaProjection {

    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFecha();

    String getSerie();

    String getFolio();

    String getCliente();

    BigDecimal getMonto();

    String getEstatus();

    String getUsuario();
}
