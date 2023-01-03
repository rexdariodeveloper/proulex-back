package com.pixvs.main.models.projections.DatosFacturacion;

import com.pixvs.main.models.DatosFacturacion;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DatosFacturacion.class})
public interface DatosFacturacionClientesComboProjection {

    Integer getId();

    String getCodigo();

    String getNombre();
}