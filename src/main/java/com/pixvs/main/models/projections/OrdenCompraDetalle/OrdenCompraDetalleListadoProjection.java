package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenCompraDetalle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleListadoProjection {


    Integer getId();

    Integer getArticuloId();

    Integer getUnidadMedidaId();

    BigDecimal getCantidad();

    BigDecimal getPrecio();

    BigDecimal getDescuento();

    BigDecimal getIva();

    Boolean getIvaExento();

    BigDecimal getIeps();

    BigDecimal getIepsCuotaFija();

}