package com.pixvs.main.models.projections.OrdenCompra;

import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 16/09/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraCargarReciboProjection {

    Integer getId();
    ProveedorComboProjection getProveedor();

}
