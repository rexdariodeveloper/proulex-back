package com.pixvs.main.models.projections.Puesto;

import com.pixvs.main.models.Puesto;
import com.pixvs.main.models.projections.PuestoHabilidadResponsabilidad.PuestoHabilidadResponsabilidadComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Benjamin Osorio on 26/09/2022.
 */
@Projection(types = {Puesto.class})
public interface PuestoComboContratosProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    List<PuestoHabilidadResponsabilidadComboProjection> getHabilidadesResponsabilidades();

}
