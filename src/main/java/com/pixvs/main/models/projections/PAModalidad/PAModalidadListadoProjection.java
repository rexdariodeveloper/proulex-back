package com.pixvs.main.models.projections.PAModalidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAModalidad;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {PAModalidad.class})
public interface PAModalidadListadoProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    BigDecimal getHorasPorDia();
    Integer getDiasPorSemana();
    Boolean getLunes();
    Boolean getMartes();
    Boolean getMiercoles();
    Boolean getJueves();
    Boolean getViernes();
    Boolean getSabado();
    Boolean getDomingo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
