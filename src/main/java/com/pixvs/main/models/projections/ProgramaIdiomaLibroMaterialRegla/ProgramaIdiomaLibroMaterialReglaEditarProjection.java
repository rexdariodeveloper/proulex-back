package com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterialRegla;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterialRegla;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaLibroMaterialRegla.class})
public interface ProgramaIdiomaLibroMaterialReglaEditarProjection {


    Integer getId();

    Integer getProgramaLibroMateriallId();

    ControlMaestroMultipleComboProjection getCarrera();
}