package com.pixvs.spring.models.projections.EmpresaDiaNoLaboral;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.EmpresaDiaNoLaboral;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpresaDiaNoLaboral.class})
public interface EmpresaDiaNoLaboralEditarProjection {


    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFecha();

    String getDescripcion();

    Boolean getActivo();
}