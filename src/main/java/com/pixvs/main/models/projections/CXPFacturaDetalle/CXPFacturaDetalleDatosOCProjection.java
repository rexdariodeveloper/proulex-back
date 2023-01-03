package com.pixvs.main.models.projections.CXPFacturaDetalle;

import com.pixvs.main.models.CXPFacturaDetalle;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCompletoProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 16/09/2020.
 */
@Projection(types = {CXPFacturaDetalle.class})
public interface CXPFacturaDetalleDatosOCProjection {

    Integer getId();
    OrdenCompraReciboCompletoProjection getRecibo();

}
