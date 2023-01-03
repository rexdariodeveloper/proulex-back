package com.pixvs.main.models.projections.Proveedor;

import com.pixvs.main.models.Proveedor;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {Proveedor.class})
public interface ProveedorReporteAntiguedadSaldosDetalleProjection {

    Integer getFacturaId();
    Integer getProveedorId();
    String getProveedorCodigo();
    String getProveedorNombre();
    String getCodigoRegistro();
    Date getFechaRegistro();
    Date getFechaVencimiento();
    Integer getDiasVencimiento();
    BigDecimal getMontoRegistro();
    BigDecimal getMontoActual();
    BigDecimal getPorVencer();
    BigDecimal getMontoP1();
    BigDecimal getMontoP2();
    BigDecimal getMontoP3();
    BigDecimal getMontoP4();
    BigDecimal getMontoProgramado();
    String getCodigos();

}
