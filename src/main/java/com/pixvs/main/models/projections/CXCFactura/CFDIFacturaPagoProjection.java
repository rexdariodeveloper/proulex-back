package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CFDIFacturaDetalleProjection;
import com.pixvs.main.models.projections.CXCPago.CFDIPagoProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.SATRegimenFiscal.SATRegimenFiscalComboProjection;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXCFactura.class})
public interface CFDIFacturaPagoProjection {

    Integer getId();

    String getVersion();

    Date getFecha();

    String getSerie();

    String getFolio();

    MonedaComboProjection getMoneda();

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

    int getTipoRegistroId();

    int getEstatusId();

    List<CFDIFacturaDetalleProjection> getDetalles();

    CFDIPagoProjection getPago();
}