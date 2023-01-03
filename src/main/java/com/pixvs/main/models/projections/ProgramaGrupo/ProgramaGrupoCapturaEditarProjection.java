package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoCapturaEditarProjection {

    Integer getId();
    String  getCodigo();
    String  getSede();
    String getIdioma();
    String getModalidad();
    String getNivel();
    String getGrupo();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();
    String  getProfesor();
    String  getHorario();
    BigDecimal getFaltasPermitidas();
    BigDecimal getFaltasDesertor();
    Boolean getIncompany();
    Boolean getJobs();
    Boolean getSems();
    Boolean getPcp();
    Integer getEstatusId();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFinTolerancia();
}
