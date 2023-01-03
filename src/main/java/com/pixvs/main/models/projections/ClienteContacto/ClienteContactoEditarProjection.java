package com.pixvs.main.models.projections.ClienteContacto;

import com.pixvs.main.models.ClienteContacto;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ClienteContacto.class})
public interface ClienteContactoEditarProjection {

    Integer getId();

    Integer getClienteId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getDepartamento();

    String getTelefono();

    String getExtension();

    String getCorreoElectronico();

    String getHorarioAtencion();

    boolean isPredeterminado();

    boolean isActivo();
}