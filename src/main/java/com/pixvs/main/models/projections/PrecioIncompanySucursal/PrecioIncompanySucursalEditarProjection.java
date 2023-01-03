package com.pixvs.main.models.projections.PrecioIncompanySucursal;

import com.pixvs.main.models.PrecioIncompanyDetalle;
import com.pixvs.main.models.PrecioIncompanySucursal;
import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PrecioIncompanySucursal.class})
public interface PrecioIncompanySucursalEditarProjection {


    Integer getId();

    Integer getPrecioIncompanyId();

    SucursalComboProjection getSucursal();

}