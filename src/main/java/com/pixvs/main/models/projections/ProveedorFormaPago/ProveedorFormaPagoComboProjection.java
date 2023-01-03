package com.pixvs.main.models.projections.ProveedorFormaPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProveedorFormaPago;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Projection(types = {ProveedorFormaPago.class})
public interface ProveedorFormaPagoComboProjection {


    Integer getId();

    String getReferencia();

}