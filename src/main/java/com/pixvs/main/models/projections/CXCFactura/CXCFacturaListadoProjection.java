package com.pixvs.main.models.projections.CXCFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@Projection(types = {CXCFactura.class})
public interface CXCFacturaListadoProjection {

    Integer getId();
    String getCodigoRegistro();
    String getClienteNombre();
    String getClienteRFC();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFecha();
    String getMonedaNombre();
    BigDecimal getMonto();

}
