package com.pixvs.main.models.projections.ProgramaMetaDetalle;

import com.pixvs.main.models.ProgramaMetaDetalle;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 10/05/2021.
 */
@Projection(types = {ProgramaMetaDetalle.class})
public interface ProgramaMetaDetalleProjection {

    Integer getId();
    SucursalComboProjection getSucursal();

}
