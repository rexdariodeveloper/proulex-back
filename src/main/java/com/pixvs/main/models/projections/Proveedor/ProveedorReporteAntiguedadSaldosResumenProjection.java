package com.pixvs.main.models.projections.Proveedor;

import com.pixvs.main.models.Proveedor;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {Proveedor.class})
public interface ProveedorReporteAntiguedadSaldosResumenProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    BigDecimal getMontoRegistro();
    BigDecimal getMontoActual();
    BigDecimal getPorVencer();
    BigDecimal getMontoP1();
    BigDecimal getMontoP2();
    BigDecimal getMontoP3();
    BigDecimal getMontoP4();
    BigDecimal getMontoProgramado();

}
