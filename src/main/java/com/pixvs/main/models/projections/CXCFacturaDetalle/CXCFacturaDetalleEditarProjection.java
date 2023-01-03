package com.pixvs.main.models.projections.CXCFacturaDetalle;

import com.pixvs.main.models.CXCFacturaDetalle;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@Projection(types = {CXCFacturaDetalle.class})
public interface CXCFacturaDetalleEditarProjection {

    Integer getId();
    Integer getClienteRemisionDetalleId();
    Integer getNumeroLinea();
    String getDescripcion();
    BigDecimal getCantidad();
    BigDecimal getPrecioUnitario();
    BigDecimal getIva();
    Boolean getIvaExento();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    ControlMaestroMultipleComboProjection getTipoRegistro();
    BigDecimal getDescuento();
    ControlMaestroMultipleComboProjection getTipoRetencion();
    Integer getArticuloId();
    Integer getUnidadMedidaId();

}
