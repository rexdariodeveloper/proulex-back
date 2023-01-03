package com.pixvs.main.models.projections.CXPFactura;

import com.pixvs.main.models.CXPFactura;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 16/10/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaComboProjection {

    Integer getId();
    String getCodigoRegistro();

}
