package com.pixvs.main.models.projections.SucursalFormasPago;

import com.pixvs.main.models.SucursalFormasPago;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SucursalFormasPago.class})
public interface SucursalFormasPagoEditarProjection {
    Integer getId();
    Integer getSucursalId();
    Integer getFormaPagoId();
    Boolean getUsarEnPV();
    Boolean getActivo();
}
