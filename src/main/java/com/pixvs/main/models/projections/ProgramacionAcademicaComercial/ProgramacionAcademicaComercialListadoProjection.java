package com.pixvs.main.models.projections.ProgramacionAcademicaComercial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramacionAcademicaComercial.class})
public interface ProgramacionAcademicaComercialListadoProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    String getPaCiclo();

    Boolean getActivo();

}