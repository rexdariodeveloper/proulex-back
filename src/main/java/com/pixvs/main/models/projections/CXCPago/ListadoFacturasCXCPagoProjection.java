package com.pixvs.main.models.projections.CXCPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCPago;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {CXCPago.class})
public interface ListadoFacturasCXCPagoProjection {

    boolean isSeleccionado();

    Integer getFacturaId();

    Integer getSedeId();

    String getSede();

    String getFolio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFecha();

    int getNoParcialidad();

    BigDecimal getMonto();

    BigDecimal getSaldo();

    BigDecimal getMontoPago();

    String getMetodoPago();

    Integer getAlumnoClienteId();

    Integer getDatosFacturacionId();
}