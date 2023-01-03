package com.pixvs.main.models.projections.CXCFactura;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.DatosFacturacion.DatosFacturacionEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.SATMes.SATMesComboProjection;
import com.pixvs.spring.models.projections.SATPeriodicidad.SATPeriodicidadComboProjection;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {CXCFactura.class})
public interface NotaVentaCXCFacturaEditarProjection {

    Integer getId();

    String getVersion();

    String getSerie();

    String getFolio();

    Integer getDatosFacturacionId();

    DatosFacturacionEditarProjection getDatosFacturacion();

    Integer getReceptorUsoCFDIId();

    SATUsoCFDIComboProjection getReceptorUsoCFDI();

    Integer getSucursalId();

    SucursalComboProjection getSucursal();

    SATPeriodicidadComboProjection getPeriodicidad();

    SATMesComboProjection getMes();

    Integer getAnio();

    String getXml();

    int getEstatusId();

    List<CFDIFacturaRelacionadaProjection> getFacturasRelacionadas();
}