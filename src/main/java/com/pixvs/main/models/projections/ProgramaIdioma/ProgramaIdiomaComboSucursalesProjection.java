package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaComboSucursalesProjection {


    Integer getId();
    String getNombre();
    Integer getProgramaId();
    ControlMaestroMultipleComboProjection getIdioma();
    ControlMaestroMultipleComboProjection getTipoWorkshop();
    String getCodigo();
    Integer getIdiomaId();
    Integer getNumeroNiveles();
    BigDecimal getCalificacionMinima();
    Integer getFaltasPermitidas();
    ControlMaestroMultipleComboProjection getPlataforma();
    Boolean getJobs();
    Boolean getJobsSems();
    Boolean getPcp();
    Boolean getAcademy();
}