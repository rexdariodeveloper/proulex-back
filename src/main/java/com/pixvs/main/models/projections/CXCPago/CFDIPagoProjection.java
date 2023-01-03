package com.pixvs.main.models.projections.CXCPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCPago;
import com.pixvs.main.models.projections.CXCPagoDetalle.CFDIPagoDetalleProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXCPago.class})
public interface CFDIPagoProjection {

    Integer getId();

    Integer getFacturaId();

    String getVersion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFecha();

    int getFormaPagoId();

    FormaPagoComboProjection getFormaPago();

    int getMonedaId();

    MonedaComboProjection getMoneda();

    BigDecimal getTipoCambio();

    String getNoOperacion();

    String getCuentaOrdenante();

    String getCuentaOrdenanteEmisorRFC();

    String getCuentaOrdenanteNombreBanco();

    String getCuentaBeneficiario();

    String getCuentaBeneficiarioEmisorRFC();

    List<CFDIPagoDetalleProjection> getDetalles();
}