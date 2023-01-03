package com.pixvs.main.models.projections.CXPFacturaDetalle;

import com.pixvs.main.models.CXPFacturaDetalle;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXPFacturaDetalle.class})
public interface CXPSolicitudFacturaDetalleEditarProjection {

    Integer getId();
    Integer getNumeroLinea();
    String getDescripcion();
    BigDecimal getCantidad();
    BigDecimal getPrecioUnitario();
    BigDecimal getIva();
    Boolean getIvaExento();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    ControlMaestroMultipleComboProjection getTipoRegistro();
    Integer getTipoRegistroId();
    BigDecimal getDescuento();
    ControlMaestroMultipleComboProjection getTipoRetencion();
    Integer getTipoRetencionId();
    BigDecimal getCantidadRelacionar();
    Integer getOrdenCompraDetalleId();
    Integer getArticuloId();
    Integer getUnidadMedidaId();

}
