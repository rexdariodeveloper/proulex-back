package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboProjection;
import com.pixvs.spring.util.DateUtil;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {CXPFactura.class})
public interface CXPFacturaAlertaCXPSPProjection {

    Integer getId();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();
    Date getFechaRegistro();
    default Date getFechaVencimiento(){
        return DateUtil.fechaSumar(getFechaRegistro(),getDiasCredito());
    }
    ProveedorComboProjection getProveedor();

    @JsonIgnore
    Integer getDiasCredito();

}
