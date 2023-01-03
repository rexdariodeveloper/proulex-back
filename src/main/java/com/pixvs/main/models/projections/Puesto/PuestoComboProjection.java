package com.pixvs.main.models.projections.Puesto;

import com.pixvs.main.models.Puesto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Benjamin Osorio on 26/09/2022.
 */
@Projection(types = {Puesto.class})
public interface PuestoComboProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    @Value("#{target.codigo + ' - ' + target.nombre}")
    String getCodigoNombre();
}
