package com.pixvs.main.models.projections.CXPSolicitudPagoServicioDetalle;

import com.pixvs.main.models.CXPSolicitudPagoServicioDetalle;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaPagoProveedoresProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorPagoProveedoresProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CXPSolicitudPagoServicioDetalle.class})
public interface CXPSolicitudPagoServicioDetalleListadoProjection {

    Integer getId();
    @Value("#{target.cxpFactura.proveedor}")
    ProveedorPagoProveedoresProjection getProveedor();
    CXPFacturaPagoProveedoresProjection getCxpFactura();
    ControlMaestroMultipleComboProjection getEstatus();

}
