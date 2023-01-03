package com.pixvs.main.models.projections.OrdenVentaDetalle;

import com.pixvs.main.models.OrdenVentaDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {OrdenVentaDetalle.class})
public interface FacturacionOrdenVentaDetalleProjection {

    Integer getId();

    int getOrdenVentaId();

    int getArticuloId();

    int getUnidadMedidaId();

    BigDecimal getFactorConversion();

    BigDecimal getCantidad();

    BigDecimal getPrecio();

    BigDecimal getDescuento();

    BigDecimal getIva();

    Boolean getIvaExento();

    BigDecimal getIeps();

    BigDecimal getIepsCuotaFija();

    Integer getDetallePadreId();
}
