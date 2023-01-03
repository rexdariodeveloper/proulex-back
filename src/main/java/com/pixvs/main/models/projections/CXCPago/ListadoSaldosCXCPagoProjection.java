package com.pixvs.main.models.projections.CXCPago;

import com.pixvs.main.models.CXCPago;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXCPago.class})
public interface ListadoSaldosCXCPagoProjection {

    Integer getAlumnoClienteId();

    String getCodigo();

    String getNombre();

    int getNoFacturas();

    BigDecimal getSaldo();
}