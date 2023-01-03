package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenCompra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraComboProjection {


    Integer getId();

    String getCodigo();

}