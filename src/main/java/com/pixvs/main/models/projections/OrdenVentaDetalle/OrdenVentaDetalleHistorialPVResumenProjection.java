package com.pixvs.main.models.projections.OrdenVentaDetalle;

import com.pixvs.main.models.OrdenVentaDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 01/10/2021.
 */
@Projection(types = {OrdenVentaDetalle.class})
public interface OrdenVentaDetalleHistorialPVResumenProjection {

    Integer getId();
    String getConceptoLinea1();
    String getConceptoLinea2();
    BigDecimal getCantidad();
    BigDecimal getPrecio();
    BigDecimal getDescuento();
    BigDecimal getMontoTotal();
    Boolean getEsExamenUbicacion();
    Boolean getEsInscripcion();

}
