package com.pixvs.main.models.projections.ProgramaIdiomaSucursal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.ProgramaIdiomaSucursal;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaSucursal.class})
public interface ProgramaIdiomaSucursalEditarProjection {


    Integer getId();
    Integer getProgramaIdiomaId();
    SucursalComboProjection getSucursal();


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    //List<ProgramaIdioma> getIdiomas();
}