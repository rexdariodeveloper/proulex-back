package com.pixvs.spring.models.projections.EmpresaDiaNoLaboralFijo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.EmpresaDiaNoLaboral;
import com.pixvs.spring.models.EmpresaDiaNoLaboralFijo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpresaDiaNoLaboralFijo.class})
public interface EmpresaDiaNoLaboralFijoEditarProjection {


    Integer getId();

    Integer getDia();

    Integer getMes();

    String getDescripcion();

    Boolean getActivo();
}