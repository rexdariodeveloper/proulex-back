package com.pixvs.main.models.projections.ControlMaestroMultiple;

import com.pixvs.main.models.ControlMaestroMultipleDatosAdicionales;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {ControlMaestroMultipleDatosAdicionales.class})
public interface ControlMaestroMultipleComboResponsablesProjection {

    Integer getId();
    String getControl();
    String getValor();
    String getReferencia();
    Date getFechaModificacion();
    ControlMaestroMultipleComboProjection getCmmReferencia();
    List<UsuarioComboProjection> getResponsables();

}
