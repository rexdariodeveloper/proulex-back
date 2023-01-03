package com.pixvs.spring.models.projections.AlertaConfigEtapaAprobadores;

import com.pixvs.spring.models.AlertaConfigEtapaAprobadores;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlertaConfigEtapaAprobadores.class})
public interface AlertaConfigEtapaAprobadoresProjection {

    Integer getId();
    Integer getOrden();
    Integer getAprobadorId();
    UsuarioComboProjection getAprobador();
    Integer getDepartamentoId();
    Boolean getActivo();
    //Integer getEtapaId();

}
