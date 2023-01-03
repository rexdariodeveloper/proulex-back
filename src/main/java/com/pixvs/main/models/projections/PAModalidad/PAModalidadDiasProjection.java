package com.pixvs.main.models.projections.PAModalidad;

import com.pixvs.main.models.PAModalidad;
import org.springframework.data.rest.core.config.Projection;

import java.util.Arrays;
import java.util.List;

@Projection(types = {PAModalidad.class})
public interface PAModalidadDiasProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    Integer getDiasPorSemana();
    Boolean getLunes();
    Boolean getMartes();
    Boolean getMiercoles();
    Boolean getJueves();
    Boolean getViernes();
    Boolean getSabado();
    Boolean getDomingo();
    String getColor();

    default List<Boolean> getDiasSemanaActivos(){
        return Arrays.asList(getDomingo(),getLunes(),getMartes(),getMiercoles(),getJueves(),getViernes(),getSabado());
    }

}
