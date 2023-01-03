package com.pixvs.main.models.projections.UnidadMedida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.UnidadMedida;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {UnidadMedida.class})
public interface UnidadMedidaListadoProjection {

    Integer getId();
    String getNombre();
    String getClave();
    String getClaveSAT();
    Integer getDecimales();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
