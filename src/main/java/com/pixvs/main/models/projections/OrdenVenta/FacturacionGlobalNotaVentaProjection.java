package com.pixvs.main.models.projections.OrdenVenta;

import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {OrdenVenta.class})
public interface FacturacionGlobalNotaVentaProjection {

    Integer getId();

    BigDecimal getCantidad();

    String getUnidad();

    String getClaveProdServ();

    String getNoIdentificacion();

    String getDescripcion();

    BigDecimal getValorUnitario();

    BigDecimal getSubtotal();

    BigDecimal getDescuento();

    BigDecimal getImpuestos();

    BigDecimal getTotal();

    Integer getSucursalId();

    Integer getEstatusId();

    Integer getFacturaId();
}
