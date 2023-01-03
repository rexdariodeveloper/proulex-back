package com.pixvs.main.models.projections.CXPPago;

import com.pixvs.main.models.CXPPago;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Projection(types = {CXPPago.class})
public interface CXPPagoProgramacionPagoProjection {

    Integer getId();
    ControlMaestroMultipleComboProjection getEstatus();

}
