package com.pixvs.main.models.projections.PADescuentoSucursal;

import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.PADescuentoSucursal;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaDescuentoDetalle.ProgramaIdiomaDescuentoDetalleEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuentoSucursal.class})
public interface PADescuentoSucursalEditarProjection {


    Integer getId();

    Integer getDescuentoId();

    SucursalComboProjection getSucursal();

    Boolean getActivo();
}