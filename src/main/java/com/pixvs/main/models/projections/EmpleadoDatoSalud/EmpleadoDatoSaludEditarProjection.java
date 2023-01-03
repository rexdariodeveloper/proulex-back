package com.pixvs.main.models.projections.EmpleadoDatoSalud;

import com.pixvs.main.models.EmpleadoDatoSalud;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 22/03/2022.
 */
@Projection(types = {EmpleadoDatoSalud.class})
public interface EmpleadoDatoSaludEditarProjection {
    Integer getId();

    Integer getEmpleadoId();

    String getAlergias();

    String getPadecimientos();

    String getInformacionAdicional();

    ControlMaestroMultipleComboProjection getTipoSangre();
}
