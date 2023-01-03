package com.pixvs.main.models.projections.EmpleadoBeneficiario;

import com.pixvs.main.models.EmpleadoBeneficiario;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoBeneficiario.class})
public interface EmpleadoBeneficiarioEditarProjection {


    Integer getId();

    Integer getEmpleadoId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    ControlMaestroMultipleComboProjection getParentesco();

    Integer getPorcentaje();
}