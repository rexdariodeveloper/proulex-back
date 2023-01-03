package com.pixvs.main.models.projections.Sucursal;

import com.pixvs.main.models.Sucursal;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 04/11/2020.
 */
@Projection(types = {Sucursal.class})
public interface SucursalComboPlantelFiltrosProjection {

    Integer getId();
    String getNombre();
    String getNombreCombinado();
    Integer getSucursalPlantelId();
    String getSucursalPlantelNombre();

}
