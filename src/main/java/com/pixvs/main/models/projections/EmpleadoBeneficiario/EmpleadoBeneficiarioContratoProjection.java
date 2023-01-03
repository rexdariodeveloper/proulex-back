package com.pixvs.main.models.projections.EmpleadoBeneficiario;

import com.pixvs.main.models.EmpleadoBeneficiario;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Benjamin Osorio Bautista on 04/11/2020.
 */
@Projection(types = {EmpleadoBeneficiario.class})
public interface EmpleadoBeneficiarioContratoProjection {

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    ControlMaestroMultipleComboProjection getParentesco();

    Integer getPorcentaje();
}
