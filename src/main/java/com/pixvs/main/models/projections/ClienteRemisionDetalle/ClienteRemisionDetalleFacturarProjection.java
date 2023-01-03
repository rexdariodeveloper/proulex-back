package com.pixvs.main.models.projections.ClienteRemisionDetalle;

import com.pixvs.main.models.ClienteRemisionDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@Projection(types = {ClienteRemisionDetalle.class})
public interface ClienteRemisionDetalleFacturarProjection {

    Integer getId();
    Integer getClienteRemisionId();
    String getCodigoRemision();
    String getArticuloCodigo();
    String getArticuloNombre();
    String getUnidadMedidaNombre();
    BigDecimal getCantidad();
    BigDecimal getPrecioUnitario();

}
