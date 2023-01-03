package com.pixvs.spring.models.projections.ControlMaestroMultiple;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {ControlMaestroMultiple.class})
public interface ControlMaestroMultipleComboProjection {

    Integer getId();

    String getControl();

    String getValor();

    String getReferencia();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    ControlMaestroMultipleComboProjection getCmmReferencia();

}
