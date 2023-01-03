package com.pixvs.spring.models.projections.RolMenu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.RolMenu;
import com.pixvs.spring.models.projections.MenuPrincipal.MenuPrincipalComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {RolMenu.class})
public interface RolMenuEditarProjection {

    Integer getId();

    Integer getRolId();

    Integer getNodoId();

    Boolean getCrear();

    Boolean getModificar();

    Boolean getEliminar();

}
