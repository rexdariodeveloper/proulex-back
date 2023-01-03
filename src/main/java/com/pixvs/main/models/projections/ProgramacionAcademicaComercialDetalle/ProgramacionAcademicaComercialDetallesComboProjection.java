package com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramacionAcademicaComercialDetalle.class})
public interface ProgramacionAcademicaComercialDetallesComboProjection {


    Integer getId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaFin();
}