package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuentoDetalle;

import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuentoDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 29/11/2022.
 */
@Projection(types = {ProgramaIdiomaCertificacionDescuentoDetalle.class})
public interface ProgramaIdiomaCertificacionDescuentoDetalleEditarProjection {
    Integer getId();

    Integer getProgramaIdiomaCertificacionDescuentoId();

    Integer getNumeroNivel();

    BigDecimal getPorcentajeDescuento();

    Boolean getActivo();
}
