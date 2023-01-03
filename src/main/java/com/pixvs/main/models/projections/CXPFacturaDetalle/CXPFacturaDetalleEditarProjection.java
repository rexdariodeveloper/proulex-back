package com.pixvs.main.models.projections.CXPFacturaDetalle;

import com.pixvs.main.models.CXPFacturaDetalle;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCompletoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 16/09/2020.
 */
@Projection(types = {CXPFacturaDetalle.class})
public interface CXPFacturaDetalleEditarProjection {

    Integer getId();
    String getDescripcion();
    ControlMaestroMultipleComboProjection getTipoRetencion();

    BigDecimal getCantidad();
    BigDecimal getPrecioUnitario();
    BigDecimal getDescuento();
    BigDecimal getIva();
    Boolean getIvaExento();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();

    @Value("#{target.recibo == null ? null : target.recibo.ordenCompraDetalleId}")
    Integer getOrdenCompraDetalleId();

}
