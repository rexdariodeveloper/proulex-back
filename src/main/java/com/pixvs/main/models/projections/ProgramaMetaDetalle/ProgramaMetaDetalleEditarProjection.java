package com.pixvs.main.models.projections.ProgramaMetaDetalle;

import com.pixvs.main.models.ProgramaMetaDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 14/05/2021.
 */
@Projection(types = {ProgramaMetaDetalle.class})
public interface ProgramaMetaDetalleEditarProjection {
    Integer getId();
    Integer getSucursalId();
    Integer getPaModalidadId();
    Date getFechaInicio();
    Integer getMeta();
}
