package com.pixvs.spring.models.projections.Usuario;

import com.pixvs.main.models.Usuario;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Usuario.class})
public interface UsuarioLoginVerificarProjection {

    Integer getId();

    String getCorreoElectronico();

    String getContrasenia();

    Integer getEstatusId();


}
