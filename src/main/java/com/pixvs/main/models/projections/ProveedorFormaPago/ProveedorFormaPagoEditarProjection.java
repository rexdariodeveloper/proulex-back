package com.pixvs.main.models.projections.ProveedorFormaPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Projection(types = {ProveedorFormaPago.class})
public interface ProveedorFormaPagoEditarProjection {


    Integer getId();

    Boolean getActivo();

    Integer getProveedorId();

    FormaPagoComboProjection getFormaPago();

    MonedaComboProjection getMoneda();

    String getBanco();

    String getReferencia();

    String getNumeroCuenta();

    String getCuentaClabe();

    String getBicSwift();

    String getIban();

    String getNombreTitularTarjeta();

    Boolean getPredeterminado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}