package com.pixvs.main.models.projections.CXPSolicitudPagoRH;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@Projection(types = {CXPSolicitudPagoRH.class})
public interface CXPSolicitudPagoRHComboProjection {

    Integer getId();
    String getCodigo();

}
