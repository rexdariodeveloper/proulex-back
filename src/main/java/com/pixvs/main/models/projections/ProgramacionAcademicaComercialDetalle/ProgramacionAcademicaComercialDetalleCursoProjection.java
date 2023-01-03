package com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {ProgramacionAcademicaComercialDetalle.class})
public interface ProgramacionAcademicaComercialDetalleCursoProjection {

    Integer getId();
    ControlMaestroMultipleComboProjection getIdioma();
    PAModalidadComboProjection getPaModalidad();
    List<ProgramaComboProjection> getProgramas();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaFin();

}
