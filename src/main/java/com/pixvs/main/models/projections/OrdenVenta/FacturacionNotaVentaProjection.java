package com.pixvs.main.models.projections.OrdenVenta;

import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {OrdenVenta.class})
public interface FacturacionNotaVentaProjection {

    Integer getId();

    Date getFecha();

    String getCodigo();

    BigDecimal getSubtotal();

    BigDecimal getDescuento();

    BigDecimal getImpuestos();

    BigDecimal getTotal();

    Integer getSucursalId();

    Integer getEstatusId();

    String getEstatus();

    Integer getFacturaId();

    String getSede();

    String getFormaPago();
}