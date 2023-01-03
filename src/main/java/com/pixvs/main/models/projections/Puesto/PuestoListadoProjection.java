package com.pixvs.main.models.projections.Puesto;

import com.pixvs.main.models.Puesto;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Benjamin Osorio on 26/09/2022.
 */
@Projection(types = {Puesto.class})
public interface PuestoListadoProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    String getDescripcion();

    String getProposito();

    String getObservaciones();

    ControlMaestroMultipleComboProjection getEstadoPuesto();
}
