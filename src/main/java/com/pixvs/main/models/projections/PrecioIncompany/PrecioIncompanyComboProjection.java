package com.pixvs.main.models.projections.PrecioIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PrecioIncompany;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PrecioIncompany.class})
public interface PrecioIncompanyComboProjection {


    Integer getId();

    @Value("#{target.codigo + ' ' + target.nombre}")
    String getNombre();
}