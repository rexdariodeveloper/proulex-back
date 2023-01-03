package com.pixvs.main.models.projections.PrecioIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.PrecioIncompanyDetalle;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PrecioIncompany.class})
public interface PrecioIncompanyListadoProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    @Value("#{target.estatus.valor}")
    String getEstatus();
}