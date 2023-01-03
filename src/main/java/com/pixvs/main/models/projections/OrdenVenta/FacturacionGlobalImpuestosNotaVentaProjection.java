package com.pixvs.main.models.projections.OrdenVenta;

import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {OrdenVenta.class})
public interface FacturacionGlobalImpuestosNotaVentaProjection {

    Integer getId();

    String getClave();

    String getNombre();

    String getTipoFactor();

    BigDecimal getBase();

    BigDecimal getTasaOCuota();

    BigDecimal getImporte();

    int getOrdenVentaId();
}
