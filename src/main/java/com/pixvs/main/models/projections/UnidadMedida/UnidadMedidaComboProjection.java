package com.pixvs.main.models.projections.UnidadMedida;

import com.pixvs.main.models.UnidadMedida;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 09/07/2020.
 */
@Projection(types = {UnidadMedida.class})
public interface UnidadMedidaComboProjection {

    Integer getId();
    String getNombre();
    Integer getDecimales();
    String getClaveSAT();
}
