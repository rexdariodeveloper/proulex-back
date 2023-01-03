package com.pixvs.main.models.projections.PAModalidad;

import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAModalidad;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {PAModalidad.class})
public interface PAModalidadComboProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    List<PAModalidadHorarioComboProjection> getHorarios();
    BigDecimal getHorasPorDia();
    Boolean getLunes();
    Boolean getMartes();
    Boolean getMiercoles();
    Boolean getJueves();
    Boolean getViernes();
    Boolean getSabado();
    Boolean getDomingo();
    String getColor();
}
