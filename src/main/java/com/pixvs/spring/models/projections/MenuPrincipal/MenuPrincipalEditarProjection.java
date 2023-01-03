package com.pixvs.spring.models.projections.MenuPrincipal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.MenuPrincipal;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {MenuPrincipal.class})
public interface MenuPrincipalEditarProjection {

    Integer getNodoId();

    String getEtiqueta();

    Integer getTipoId();

    ControlMaestroMultipleComboProjection getTipo();

    Integer getOrden();

    Integer getNodoPadreId();

    MenuPrincipalComboProjection getNodoPadre();

    Boolean getAdmitePermiso();

    Integer getSistemaAccesoId();

    ControlMaestroMultipleComboProjection getSistemaAcceso();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    String getUrl();

    String getIcono();

    Boolean getPersonalizado();
}
