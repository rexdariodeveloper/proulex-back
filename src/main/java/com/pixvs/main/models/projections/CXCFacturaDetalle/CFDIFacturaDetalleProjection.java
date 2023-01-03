package com.pixvs.main.models.projections.CXCFacturaDetalle;

import com.pixvs.main.models.CXCFacturaDetalle;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {CXCFacturaDetalle.class})
public interface CFDIFacturaDetalleProjection {

    Integer getId();

    int getFacturaId();

    String getClaveProdServ();

    String getNoIdentificacion();

    String getDescripcion();

    UnidadMedidaComboProjection getUnidadMedida();

    BigDecimal getCantidad();

    BigDecimal getValorUnitario();

    BigDecimal getImporte();

    BigDecimal getDescuento();

    ControlMaestroMultipleComboSimpleProjection getObjetoImpuesto();

    List<CFDIFacturaDetalleImpuestoProjection> getImpuestos();
}