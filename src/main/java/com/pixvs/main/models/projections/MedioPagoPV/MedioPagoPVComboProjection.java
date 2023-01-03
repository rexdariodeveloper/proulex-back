package com.pixvs.main.models.projections.MedioPagoPV;

import com.pixvs.main.models.MedioPagoPV;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 15/07/2021.
 */
@Projection(types = {MedioPagoPV.class})
public interface MedioPagoPVComboProjection {

    Integer getId();
    String getCodigo();
    String getNombre();

}
