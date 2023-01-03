package com.pixvs.main.models.projections.ClienteDatosFacturacion;

import com.pixvs.main.models.ClienteDatosFacturacion;
import com.pixvs.main.models.projections.DatosFacturacion.DatosFacturacionEditarProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ClienteDatosFacturacion.class})
public interface ClienteDatosFacturacionEditarProjection {

    Integer getId();

    int getClienteId();

    int getDatosFacturacionId();

    DatosFacturacionEditarProjection getDatosFacturacion();

    boolean isPredeterminado();
}