package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.pixvs.main.models.OrdenCompraRecibo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 15/09/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboCXPFacturaListadoProjection {

    Integer getId();
    String getProveedorNombre();
    String getProveedorRfc();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();
    Date getFechaRegistro();
    Date getFechaVencimiento();
    String getOrdenCompraTexto();
    String getSedeNombre();
    String getCodigoRecibo();
    Date getFechaReciboRegistro();
    Boolean getRelacionada();

}
