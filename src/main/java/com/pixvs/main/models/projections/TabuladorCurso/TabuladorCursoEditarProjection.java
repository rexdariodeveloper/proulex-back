package com.pixvs.main.models.projections.TabuladorCurso;

import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.main.models.TabuladorCurso;
import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {TabuladorCurso.class})
public interface TabuladorCursoEditarProjection {

    Integer getId();
    Integer getTabuladorId();
    ProgramaComboProjection getPrograma();
    PAModalidadComboSimpleProjection getModalidad();
    PAModalidadHorarioComboSimpleProjection getModalidadHorario();
    ProgramaIdiomaComboSimpleProjection getProgramaIdioma();
    Boolean getActivo();
}