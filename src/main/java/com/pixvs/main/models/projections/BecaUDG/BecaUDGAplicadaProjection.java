package com.pixvs.main.models.projections.BecaUDG;

import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSimpleProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaUDG.class})
public interface BecaUDGAplicadaProjection {

    Integer getId();
    String getSede();
    Integer getNivel();
    String getHorario();
    String getGrupo();
    String getCurso();
    Date getFechaInicio();
    Date getFechaFin();
    Date getFechaAplicacion();
    String getFolio();
//    BigDecimal getCalificacion();
//    Date getFechaCalificacion();

}
