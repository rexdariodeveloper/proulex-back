package com.pixvs.spring.models.projections.Rol;

import com.pixvs.spring.models.Rol;
import com.pixvs.spring.models.RolMenu;
import com.pixvs.spring.models.projections.RolMenu.RolMenuProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Rol.class})
public interface RolMenuUsuarioProjection {

    Integer getId();

    String getNombre();

    List<RolMenuProjection> getMenu();

}
