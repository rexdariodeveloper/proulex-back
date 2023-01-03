package com.pixvs.main.models.projections.CXCPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCPago;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {CXCPago.class})
public interface ReportePagosCXCPagoProjection {

    Integer getId();

    Integer getFacturaId();

    String getUuid();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRegistro();

    Integer getSedeId();

    String getSede();

    Integer getAlumnoClienteId();

    String getCliente();

    String getNumeroOperacion();

    Integer getMonedaId();

    String getMoneda();

    BigDecimal getMonto();

    String getTipoCambio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaPago();

    Integer getFormaPagoId();

    String getFormaPago();

    Integer getCuentaId();

    String getCuenta();
}