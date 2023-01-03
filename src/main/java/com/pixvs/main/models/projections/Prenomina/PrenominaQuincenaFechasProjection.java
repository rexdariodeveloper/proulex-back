package com.pixvs.main.models.projections.Prenomina;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Programa;
import org.springframework.data.rest.core.config.Projection;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Programa.class})
public interface PrenominaQuincenaFechasProjection {
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaInicio();
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaFin();
}