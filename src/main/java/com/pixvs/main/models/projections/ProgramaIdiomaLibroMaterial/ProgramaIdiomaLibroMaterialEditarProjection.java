package com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterialRegla.ProgramaIdiomaLibroMaterialReglaEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaLibroMaterial.class})
public interface ProgramaIdiomaLibroMaterialEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaId();

    Integer getNivel();

    ArticuloComboProjection getArticulo();

    Boolean getBorrado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ProgramaIdiomaLibroMaterialReglaEditarProjection> getReglas();
    //List<ProgramaIdioma> getIdiomas();
}