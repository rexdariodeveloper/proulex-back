package com.pixvs.main.models.projections.CXPSolicitudPagoDetalle;

import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.CXPSolicitudPagoDetalle;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaEditarProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaPagoProveedoresProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorPagoProveedoresProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@Projection(types = {CXPSolicitudPagoDetalle.class})
public interface CXPSolicitudPagoDetalleListadoProjection {

    Integer getId();
    @Value("#{target.cxpFactura.proveedor}")
    ProveedorPagoProveedoresProjection getProveedor();
    Integer getCxpFacturaId();
    ControlMaestroMultipleComboProjection getEstatus();
    BigDecimal getMontoProgramado();

}
