package com.pixvs.main.models.projections.DeduccionPercepcion;

import com.pixvs.main.models.DeduccionPercepcion;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {DeduccionPercepcion.class})
public interface DeduccionComboProjection {


    Integer getId();

    @Value("#{target.codigo + ' '+target.concepto}")
    String getCodigo();
    BigDecimal getPorcentaje();
}