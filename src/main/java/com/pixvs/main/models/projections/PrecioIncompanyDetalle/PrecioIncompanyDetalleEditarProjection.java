package com.pixvs.main.models.projections.PrecioIncompanyDetalle;

import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.PrecioIncompanyDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PrecioIncompanyDetalle.class})
public interface PrecioIncompanyDetalleEditarProjection {


    Integer getId();

    Integer getPrecioIncompanyId();

    ControlMaestroMultipleComboProjection getZona();

    BigDecimal getPrecioVenta();

    BigDecimal getPorcentajeTransporte();

    ControlMaestroMultipleComboProjection getIdioma();

    ProgramaComboProjection getPrograma();

    PAModalidadComboProjection getModalidad();

    PAModalidadHorarioComboSimpleProjection getHorario();

    Boolean getActivo();

}