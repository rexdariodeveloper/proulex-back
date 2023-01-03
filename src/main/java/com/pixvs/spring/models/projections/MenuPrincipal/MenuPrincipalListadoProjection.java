package com.pixvs.spring.models.projections.MenuPrincipal;

import com.pixvs.spring.models.MenuPrincipal;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {MenuPrincipal.class})
public interface MenuPrincipalListadoProjection {

    Integer getId();

    String getTitle();

    String getType();

    String getUrl();

    String getIcon();

    String getTranslate();

    Integer getNodoPadreId();

    List<MenuPrincipalListadoProjection> getChildren();

}
