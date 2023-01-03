package com.pixvs.main.models.projections.ProgramaIdiomaModalidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.ProgramaIdiomaModalidad;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaModalidad.class})
public interface ProgramaIdiomaModalidadEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaId();

    PAModalidadComboProjection getModalidad();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    //List<ProgramaIdioma> getIdiomas();
}