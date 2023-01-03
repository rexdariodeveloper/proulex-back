package com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadDiasProjection;
import com.pixvs.main.models.projections.Programa.ProgramaCalcularDiasProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramacionAcademicaComercialDetalle.class})
public interface ProgramacionAcademicaComercialDetalleEditarProjection {


    Integer getId();

    ControlMaestroMultipleComboProjection getIdioma();

    PAModalidadDiasProjection getPaModalidad();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    Integer getNumeroClases();

    String getComentarios();

    List<ProgramaCalcularDiasProjection> getProgramas();

}