package com.pixvs.spring.models.projections.Departamento;

import com.pixvs.spring.models.Departamento;
import com.pixvs.spring.models.DepartamentoResponsabilidadHabilidad;
import com.pixvs.spring.models.projections.DepartamentoResponsabilidadHabilidad.DepartamentoResponsabilidadHabilidadProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Rene Carrillo on 31/03/2022.
 */
@Projection(types = {Departamento.class})
public interface DepartamentoComboResponsabilidadProjection {
    Integer getId();

    String getPrefijo();

    String getNombre();

    List<DepartamentoResponsabilidadHabilidadProjection> getResponsabilidadHabilidad();
}
