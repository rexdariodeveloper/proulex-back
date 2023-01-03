package com.pixvs.main.models.projections.AlumnoDatosFacturacion;

import com.pixvs.main.models.AlumnoDatosFacturacion;
import com.pixvs.main.models.projections.DatosFacturacion.DatosFacturacionEditarProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlumnoDatosFacturacion.class})
public interface AlumnoDatosFacturacionEditarProjection {

    Integer getId();

    int getAlumnoId();

    int getDatosFacturacionId();

    DatosFacturacionEditarProjection getDatosFacturacion();

    boolean isPredeterminado();
}