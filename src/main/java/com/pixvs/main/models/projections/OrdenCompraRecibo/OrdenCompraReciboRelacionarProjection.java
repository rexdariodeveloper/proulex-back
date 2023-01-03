package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleRelacionadaProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 21/08/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboRelacionarProjection {

    Integer getId();
    BigDecimal getCantidadRecibo();
    BigDecimal getCantidadPendienteRelacionar();

}
