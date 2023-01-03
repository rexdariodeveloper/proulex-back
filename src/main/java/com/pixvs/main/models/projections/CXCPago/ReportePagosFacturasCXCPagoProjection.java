package com.pixvs.main.models.projections.CXCPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCPago;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {CXCPago.class})
public interface ReportePagosFacturasCXCPagoProjection {

    Integer getId();

    Integer getPagoId();

    Integer getFacturaId();

    Integer getSedeId();

    String getSede();

    String getNumeroFactura();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFactura();

    BigDecimal getMontoFactura();

    BigDecimal getImportePagado();

    BigDecimal getSaldo();

    String getMetodoPago();
}