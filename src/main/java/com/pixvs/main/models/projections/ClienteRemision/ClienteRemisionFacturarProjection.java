package com.pixvs.main.models.projections.ClienteRemision;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ClienteRemision;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@Projection(types = {ClienteRemision.class})
public interface ClienteRemisionFacturarProjection {

    Integer getId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    String getCodigo();
    BigDecimal getMonto();
    BigDecimal getMontoPorRelacionar();

}
