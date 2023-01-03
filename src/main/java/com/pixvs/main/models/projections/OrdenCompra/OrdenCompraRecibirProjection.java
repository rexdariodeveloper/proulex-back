package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRecibirProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 21/08/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraRecibirProjection {

    Integer getId();
    String getCodigo();
    ProveedorComboProjection getProveedor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<OrdenCompraDetalleRecibirProjection> getDetalles();

}
