package com.pixvs.main.models.projections.PACiclos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.PACiclo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {PACiclo.class})
public interface PACicloListadoProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    @Value("#{target.fechaInicio.toString().substring(0,4) + '-' + target.fechaFin.toString().substring(0,4) }")
    String getFecha();
}
