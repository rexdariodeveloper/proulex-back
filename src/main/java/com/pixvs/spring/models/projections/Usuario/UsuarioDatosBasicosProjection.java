package com.pixvs.spring.models.projections.Usuario;

import com.pixvs.main.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Usuario.class})
public interface UsuarioDatosBasicosProjection {

    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    Integer getEstatusId();

    String getCorreoElectronico();

    Integer getArchivoId();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

}
