package com.pixvs.spring.models.projections.ControlMaestroMultiple;

import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {ControlMaestroMultiple.class})
public interface ControlMaestroMultipleCardProjection {

    Integer getId();
    String getControl();
    String getValor();
    String getReferencia();
    Integer getOrden();
    Integer getImagenId();

}
