package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoCapturaProjection {

    Integer getId();
    String  getCodigo();
    String  getProgramacion();
    String  getSucursal();
    String  getPlantel();
    String  getNombreProfesor();
    String  getNombreSuplente();
    String  getHorario();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();
    String getProgramaCodigo();
    String getIdioma();
    String getModalidad();
    String getNivel();
    String getGrupo();
    Integer getCupo();
    Integer getInscritos();
    String  getColorModalidad();
    String  getColorPrimario();
    String  getColorSecundario();
    //Boolean getActivo();
}
