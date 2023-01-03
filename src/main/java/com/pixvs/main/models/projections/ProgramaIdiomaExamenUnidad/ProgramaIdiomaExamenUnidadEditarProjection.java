package com.pixvs.main.models.projections.ProgramaIdiomaExamenUnidad;

import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial.ProgramaIdiomaLibroMaterialEditarProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaExamenModalidad.class})
public interface ProgramaIdiomaExamenUnidadEditarProjection {


    Integer getId();

    Integer getExamenDetalleId();

    ProgramaIdiomaLibroMaterialEditarProjection getLibroMaterial();

    String getDescripcion();
}