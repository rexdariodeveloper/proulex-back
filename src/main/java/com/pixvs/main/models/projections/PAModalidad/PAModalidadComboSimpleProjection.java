package com.pixvs.main.models.projections.PAModalidad;

import com.pixvs.main.models.PAModalidad;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {PAModalidad.class})
public interface PAModalidadComboSimpleProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
}
