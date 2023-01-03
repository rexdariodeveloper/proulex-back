package com.pixvs.main.models.projections.ClienteRemision;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ClienteRemision;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 14/06/2021.
 */
@Projection(types = {ClienteRemision.class})
public interface ClienteRemisionListadoProjection {

    Integer getId();
    String getCodigo();
    String getClienteNombre();
    String getClienteRFC();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFecha();
    String getAlmacenOrigenNombre();
    String getAlmacenDestinoNombre();
    BigDecimal getMonto();
    String getEstatusValor();

}
