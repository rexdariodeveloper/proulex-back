package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleProjection;
import com.pixvs.main.models.projections.DatosFacturacion.DatosFacturacionEditarProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXCFactura.class})
public interface CXCFacturaMiscelaneaEditarProjection {

    Integer getId();

    Date getFecha();

    String getSerie();

    String getFolio();

    Integer getFormaPagoId();

    FormaPagoComboProjection getFormaPago();

    Integer getDiasCredito();

    Integer getMonedaId();

    MonedaComboProjection getMoneda();

    Integer getMetodoPagoId();

    ControlMaestroMultipleComboSimpleProjection getMetodoPago();

    Integer getDatosFacturacionId();

    DatosFacturacionEditarProjection getDatosFacturacion();

    Integer getReceptorUsoCFDIId();

    SATUsoCFDIComboProjection getReceptorUsoCFDI();

    Integer getSucursalId();

    SucursalComboProjection getSucursal();

    String getXml();

    int getEstatusId();

    List<CFDIFacturaDetalleProjection> getDetalles();

    List<CFDIFacturaRelacionadaProjection> getFacturasRelacionadas();
}