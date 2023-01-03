package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaRelacionadaDetalleProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXCFactura.class})
public interface CFDIFacturaRelacionadaProjection {

    Integer getId();

    Date getFecha();

    String getSerie();

    String getFolio();

    String getReceptorRFC();

    String getReceptorNombre();

    String getUuid();

    Integer getFacturaRelacionadaId();

    Integer getTipoRelacionId();

    ControlMaestroMultipleComboSimpleProjection getTipoRelacion();

    List<CFDIFacturaRelacionadaDetalleProjection> getDetalles();
}