package com.pixvs.main.models.projections.Inscripcion;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface ReporteAvanceInscripcionesProjection {

    Integer getSedeId();

    String getSede();

    Integer getInscripciones();

    BigDecimal getIngresos();

    Integer getMeta();

    Integer getAvance();

    Integer getModalidadId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
}
