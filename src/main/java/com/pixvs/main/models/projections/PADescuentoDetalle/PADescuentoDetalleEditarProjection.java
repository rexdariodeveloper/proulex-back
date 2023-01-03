package com.pixvs.main.models.projections.PADescuentoDetalle;

import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaDescuentoDetalle.ProgramaIdiomaDescuentoDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuentoDetalle.class})
public interface PADescuentoDetalleEditarProjection {


    Integer getId();

    Integer getDescuentoDetalleId();

    ProgramaComboProjection getPrograma();

    PAModalidadComboProjection getPaModalidad();

    PAModalidadHorarioComboProjection getPaModalidadHorario();

    List<ProgramaIdiomaDescuentoDetalleEditarProjection> getCursos();
}