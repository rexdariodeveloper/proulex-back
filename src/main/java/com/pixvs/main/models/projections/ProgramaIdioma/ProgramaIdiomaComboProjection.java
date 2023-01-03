package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaComboProjection {


    Integer getId();
    @Value("#{target.nombre != null ? target.nombre : (target.programa.codigo + ' ' +target.idioma.valor)}")
    String getNombre();
    Integer getProgramaId();
    Integer getIdiomaId();
    ControlMaestroMultipleComboProjection getIdioma();
    @Value("#{target.codigo != null ? target.codigo : target.programa.codigo}")
    String getCodigo();
    Integer getNumeroNiveles();
    BigDecimal getCalificacionMinima();
    BigDecimal getFaltasPermitidas();
    ControlMaestroMultipleComboProjection getPlataforma();
    @Value("#{target.programa.jobs }")
    Boolean getEsJobs();
    @Value("#{target.programa.jobsSems }")
    Boolean getEsJobsSems();
    @Value("#{target.programa.pcp }")
    Boolean getEsPcp();
    @Value("#{target.programa.academy }")
    Boolean getEsAcademy();
}