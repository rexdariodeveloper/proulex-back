package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 10/09/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraRelacionarProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaOC();
    BigDecimal getMontoOC();
    BigDecimal getMontoPendienteRelacionar();

}
