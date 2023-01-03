package com.pixvs.main.models.projections.ProgramaIdiomaExamenModalidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdioma;
import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial.ProgramaIdiomaLibroMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaSucursal.ProgramaIdiomaSucursalEditarProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaExamenModalidad.class})
public interface ProgramaIdiomaExamenModalidadEditarProjection {


    Integer getId();

    Integer getExamenDetalleId();

    PAModalidadComboSimpleProjection getModalidad();

    String getDias();

}