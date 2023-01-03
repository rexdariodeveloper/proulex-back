package com.pixvs.main.models.projections.ProgramaIdiomaDescuentoDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import com.pixvs.main.models.ProgramaIdiomaDescuentoDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaDescuentoDetalle.class})
public interface ProgramaIdiomaDescuentoDetalleEditarProjection {


    Integer getId();

    Integer getDescuentoDetalleId();

    ProgramaIdiomaComboProjection getProgramaIdioma();
}