package com.pixvs.main.models.projections.DatosFacturacion;

import com.pixvs.main.models.DatosFacturacion;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DatosFacturacion.class})
public interface AutofacturaListadoDatosFacturacionProjection {

    Integer getId();

    String getNombre();

    String getRegimenFiscal();

    String getCp();

    String getDomicilio();

    String getCorreo();

    boolean isCompletar();

    default boolean isSeleccionable() {
        return !isCompletar();
    }
}