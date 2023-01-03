package com.pixvs.main.models.projections.PrecioIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.projections.PrecioIncompanyDetalle.PrecioIncompanyDetalleEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 10/11/2022.
 */
@Projection(types = {PrecioIncompany.class})
public interface PrecioIncompanyComisionProjection {
    Integer getId();

    String getNombre();

    List<PrecioIncompanyDetalleEditarProjection> getDetalles();
}
