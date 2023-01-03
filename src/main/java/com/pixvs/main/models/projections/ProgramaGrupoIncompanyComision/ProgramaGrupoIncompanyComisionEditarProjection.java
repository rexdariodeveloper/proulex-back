package com.pixvs.main.models.projections.ProgramaGrupoIncompanyComision;

import com.pixvs.main.models.ProgramaGrupoIncompanyClaseCancelada;
import com.pixvs.main.models.ProgramaGrupoIncompanyComision;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 09/11/2022.
 */
@Projection(types = {ProgramaGrupoIncompanyComision.class})
public interface ProgramaGrupoIncompanyComisionEditarProjection {
    Integer getId();
    Integer getGrupoId();
    Integer getUsuarioId();
    UsuarioComboProjection getUsuario();
    BigDecimal getPorcentaje();
    BigDecimal getMontoComision();
    Boolean getActivo();
}
