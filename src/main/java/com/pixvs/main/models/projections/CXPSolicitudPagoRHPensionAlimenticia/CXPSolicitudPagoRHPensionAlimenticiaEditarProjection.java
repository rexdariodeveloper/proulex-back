package com.pixvs.main.models.projections.CXPSolicitudPagoRHPensionAlimenticia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidad;
import com.pixvs.main.models.CXPSolicitudPagoRHPensionAlimenticia;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidadDetalle.CXPSolicitudPagoRHIncapacidadDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPagoRHPensionAlimenticia.class})
public interface CXPSolicitudPagoRHPensionAlimenticiaEditarProjection {

    Integer getId();
    Integer getCpxSolicitudPagoRhId();
    String getNombreBeneficiario();
    Integer getNumeroResolucion();
}
