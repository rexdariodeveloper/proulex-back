package com.pixvs.main.models.projections.ProgramacionAcademicaComercial;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetallesComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramacionAcademicaComercial.class})
public interface ProgramacionAcademicaComercialComboProjection {


    Integer getId();
    //@Value("#{target.codigo + '-' +target.nombre }")
    String getNombre();
    List<ProgramacionAcademicaComercialDetallesComboProjection> getDetalles();
}