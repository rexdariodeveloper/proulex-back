package com.pixvs.main.models.projections.PAProfesorCategoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAProfesorCategoria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {PAProfesorCategoria.class})
public interface PAProfesorCategoriaListadoProjection {

    Integer getId();
    String getCategoria();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
