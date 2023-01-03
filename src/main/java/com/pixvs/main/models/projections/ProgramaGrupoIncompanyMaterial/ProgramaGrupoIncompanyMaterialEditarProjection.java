package com.pixvs.main.models.projections.ProgramaGrupoIncompanyMaterial;

import com.pixvs.main.models.ProgramaGrupoIncompanyClaseCancelada;
import com.pixvs.main.models.ProgramaGrupoIncompanyMaterial;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyMaterial.class})
public interface ProgramaGrupoIncompanyMaterialEditarProjection {


    Integer getId();

    Integer getGrupoId();

    ArticuloComboProjection getArticulo();
}