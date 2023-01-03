package com.pixvs.main.models.projections.ProgramacionAcademicaComercial;

import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetalleCursoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {ProgramacionAcademicaComercial.class})
public interface ProgramacionAcademicaComercialCursoProjection {

    Integer getId();
    String getNombre();
    PACicloFechaProjection getPaCiclo();
    List<ProgramacionAcademicaComercialDetalleCursoProjection> getDetalles();

}
