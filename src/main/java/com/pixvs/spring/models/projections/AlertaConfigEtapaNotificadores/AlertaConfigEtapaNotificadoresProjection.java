package com.pixvs.spring.models.projections.AlertaConfigEtapaNotificadores;

import com.pixvs.spring.models.AlertaConfigEtapaAprobadores;
import com.pixvs.spring.models.AlertaConfigEtapaNotificadores;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlertaConfigEtapaNotificadores.class})
public interface AlertaConfigEtapaNotificadoresProjection {

    Integer getId();
    Integer getUsuarioNotificacionId();
    UsuarioComboProjection getNotificador();
    Integer getTipoNotificacionAlerta();
    Boolean getActivo();
    //Integer getEtapaId();

}
