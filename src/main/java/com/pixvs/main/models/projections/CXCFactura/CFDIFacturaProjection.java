package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.SATMes.SATMesComboProjection;
import com.pixvs.spring.models.projections.SATPeriodicidad.SATPeriodicidadComboProjection;
import com.pixvs.spring.models.projections.SATRegimenFiscal.SATRegimenFiscalComboProjection;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXCFactura.class})
public interface CFDIFacturaProjection {

    Integer getId();

    String getVersion();

    Date getFecha();

    String getSerie();

    String getFolio();

    FormaPagoComboProjection getFormaPago();

    Integer getDiasCredito();

    String getCondicionesPago();

    MonedaComboProjection getMoneda();

    BigDecimal getTipoCambio();

    ControlMaestroMultipleComboSimpleProjection getMetodoPago();

    String getEmisorCP();

    String getEmisorRFC();

    String getEmisorRazonSocial();

    SATRegimenFiscalComboProjection getEmisorRegimenFiscal();

    String getReceptorCP();

    String getReceptorRFC();

    String getReceptorNombre();

    SATRegimenFiscalComboProjection getReceptorRegimenFiscal();

    SATUsoCFDIComboProjection getReceptorUsoCFDI();

    String getUuid();

    SATPeriodicidadComboProjection getPeriodicidad();

    SATMesComboProjection getMes();

    Integer getAnio();

    int getTipoRegistroId();

    Integer getFacturaRelacionadaId();

    List<CFDIFacturaRelacionadaProjection> getFacturasRelacionadas();

    Integer getTipoRelacionId();

    ControlMaestroMultipleComboSimpleProjection getTipoRelacion();

    int getEstatusId();

    List<CFDIFacturaDetalleProjection> getDetalles();
}