package com.pixvs.main.models.projections.ProgramaIdiomaCertificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaCertificacion.class})
public interface ProgramaIdiomaCertificacionEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaId();

    String getNivel();

    ArticuloComboProjection getCertificacion();

    BigDecimal getPrecio();

    Boolean getBorrado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}