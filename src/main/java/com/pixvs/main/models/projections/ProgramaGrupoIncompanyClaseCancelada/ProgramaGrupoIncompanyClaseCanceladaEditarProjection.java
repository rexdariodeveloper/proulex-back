package com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseCancelada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupoIncompanyClaseCancelada;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyClaseCancelada.class})
public interface ProgramaGrupoIncompanyClaseCanceladaEditarProjection {


    Integer getId();

    Integer getGrupoId();

    Date getFechaCancelar();

}