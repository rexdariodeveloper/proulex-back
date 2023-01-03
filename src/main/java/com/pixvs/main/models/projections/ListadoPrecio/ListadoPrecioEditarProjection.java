package com.pixvs.main.models.projections.ListadoPrecio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ListadoPrecio;
import com.pixvs.main.models.ListadoPrecioDetalle;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ListadoPrecio.class})
public interface ListadoPrecioEditarProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    Boolean getIndeterminado();

    MonedaComboProjection getMoneda();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ListadoPrecioDetalleEditarProjection> getDetalles();
}