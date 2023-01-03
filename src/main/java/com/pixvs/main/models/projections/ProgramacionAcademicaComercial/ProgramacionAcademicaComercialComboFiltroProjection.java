package com.pixvs.main.models.projections.ProgramacionAcademicaComercial;

import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetallesComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {ProgramacionAcademicaComercial.class})
public interface ProgramacionAcademicaComercialComboFiltroProjection {
    Integer getId();
    String getCodigo();
    String getNombre();
}