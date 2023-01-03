package com.pixvs.main.models.projections.ProgramaGrupoExamenDetalle;

import com.pixvs.main.models.ProgramaGrupoExamenDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 07/10/2022.
 */
@Projection(types = {ProgramaGrupoExamenDetalle.class})
public interface ListadoProgramaGrupoExamenDetalleProjection {
    Integer getId();
    String getActividad();
    String getFormato();
    String getScore();
    BigDecimal getTiempo();
}
