package com.pixvs.spring.models.projections.RolMenu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.RolMenu;
import com.pixvs.spring.models.projections.MenuPrincipal.MenuPrincipalComboProjection;
import com.pixvs.spring.models.projections.MenuPrincipal.MenuPrincipalListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {RolMenu.class})
public interface RolMenuProjection {

    Integer getId();

    Integer getNodoId();

    MenuPrincipalListadoProjection getNodo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
