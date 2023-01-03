package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdioma;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaListadoProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    Integer getHorasTotales();

    Integer getNumeroNiveles();

    Integer getModalidadesSize();

    Integer getSucursalesSize();

    Boolean getActivo();

}