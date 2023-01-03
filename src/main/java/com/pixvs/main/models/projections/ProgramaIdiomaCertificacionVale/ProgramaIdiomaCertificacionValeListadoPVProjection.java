package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale;

import java.math.BigDecimal;

import org.springframework.data.rest.core.config.Projection;

import com.pixvs.main.models.ProgramaIdiomaCertificacionVale;

/**
 * Created by Ángel Daniel Hernández Silva on 20/12/2020.
 */
@Projection(types = {ProgramaIdiomaCertificacionVale.class})
public interface ProgramaIdiomaCertificacionValeListadoPVProjection {

    Integer getId();
    String getAlumnoCodigo();
    String getAlumnoPrimerApellido();
    String getAlumnoSegundoApellido();
    String getAlumnoNombre();
    String getCurso();
    String getCertificacion();
    BigDecimal getDescuento();
    
}
