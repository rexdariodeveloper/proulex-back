package com.pixvs.spring.models.projections.Departamento;

import com.pixvs.spring.models.Departamento;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 */
@Projection(types = {Departamento.class})
public interface DepartamentoComboProjection {


    Integer getId();

    String getPrefijo();

    String getNombre();

}