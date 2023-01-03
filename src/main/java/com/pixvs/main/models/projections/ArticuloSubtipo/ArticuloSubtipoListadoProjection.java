package com.pixvs.main.models.projections.ArticuloSubtipo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ArticuloSubtipo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Projection(types = {ArticuloSubtipo.class})
public interface ArticuloSubtipoListadoProjection {


    Integer getId();

    String getDescripcion();

}