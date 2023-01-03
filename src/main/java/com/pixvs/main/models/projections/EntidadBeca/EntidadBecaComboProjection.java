package com.pixvs.main.models.projections.EntidadBeca;

import com.pixvs.main.models.EntidadBeca;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EntidadBeca.class})
public interface EntidadBecaComboProjection {

    Integer getId();

    String getCodigo();

    String getNombre();
}