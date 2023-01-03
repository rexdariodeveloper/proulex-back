package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoFechasClaseProjection {

    Integer getId();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFecha();
}
