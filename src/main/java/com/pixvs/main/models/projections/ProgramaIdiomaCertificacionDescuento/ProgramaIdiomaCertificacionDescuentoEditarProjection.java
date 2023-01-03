package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento;

import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuento;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuentoDetalle.ProgramaIdiomaCertificacionDescuentoDetalleEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Rene Carrillo on 25/11/2022.
 */
@Projection(types = {ProgramaIdiomaCertificacionDescuento.class})
public interface ProgramaIdiomaCertificacionDescuentoEditarProjection {
    Integer getId();

    ProgramaIdiomaCertificacionEditarProjection getProgramaIdiomaCertificacion();

    Integer getEstatusId();

    List<ProgramaIdiomaCertificacionDescuentoDetalleEditarProjection> getListaDescuento();
}
