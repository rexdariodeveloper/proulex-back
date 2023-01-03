package com.pixvs.main.models.projections.CXCPagoDetalle;

import com.pixvs.main.models.CXCPagoDetalle;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXCPagoDetalle.class})
public interface CFDIPagoDetalleProjection {

    Integer getId();

    Integer getPagoId();

    int getDoctoRelacionadoId();

    CFDIFacturaProjection getDoctoRelacionado();

    int getNoParcialidad();

    BigDecimal getImporteSaldoAnterior();

    BigDecimal getImportePagado();

    BigDecimal getImporteSaldoInsoluto();

    Integer getObjetoImpuestoId();

    ControlMaestroMultipleComboProjection getObjetoImpuesto();
}