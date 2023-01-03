package com.pixvs.main.models.projections.Moneda;

import com.pixvs.main.models.Moneda;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 12/08/2020.
 */
@Projection(types = {Moneda.class})
public interface MonedaComboProjection {

    Integer getId();

    String getNombre();

    String getCodigo();

    String getSimbolo();

    boolean getPredeterminada();
}
