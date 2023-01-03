package com.pixvs.main.models.projections.AlumnoContacto;

import com.pixvs.main.models.AlumnoContacto;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Projection(types = {AlumnoContacto.class})
public interface AlumnoContactoEditarProjection {

    Integer getId();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    ControlMaestroMultipleComboProjection getParentesco();
    String getTelefonoFijo();
    String getTelefonoMovil();
    String getTelefonoTrabajo();
    String getTelefonoTrabajoExtension();
    String getTelefonoMensajeriaInstantanea();
    String getCorreoElectronico();

}
