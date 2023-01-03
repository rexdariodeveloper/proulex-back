package com.pixvs.main.models.projections.ProgramaIdiomaCertificacion;

import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 25/11/2022.
 */
@Projection(types = {ProgramaIdiomaCertificacion.class})
public interface ProgramaIdiomaCertificacionComboProjection {

    Integer getId();

    //@Value("#{target.certificacion.nombreArticulo}")
    String getNombreArticulo();
}
