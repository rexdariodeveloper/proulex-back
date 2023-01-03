package com.pixvs.spring.models.projections.RolMenu;

import com.pixvs.spring.models.RolMenu;
import com.pixvs.spring.models.projections.MenuPrincipal.MenuPrincipalComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "rolMenuComboProjection", types = {RolMenu.class})
public interface RolMenuComboProjection {

    Integer getId();

    MenuPrincipalComboProjection getNodo();

}
