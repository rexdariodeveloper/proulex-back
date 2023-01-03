package com.pixvs.main.models.projections.Puesto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Puesto;
import com.pixvs.main.models.projections.PuestoHabilidadResponsabilidad.PuestoHabilidadResponsabilidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Benjamin Osorio on 26/09/2022.
 */
@Projection(types = {Puesto.class})
public interface PuestoEditarProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    String getDescripcion();

    String getProposito();

    String getObservaciones();

    ControlMaestroMultipleComboProjection getEstadoPuesto();

    Integer getEstadoPuestoId();

    UsuarioComboProjection getCreadoPor();

    Integer getCreadoPorId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<PuestoHabilidadResponsabilidadComboProjection> getHabilidadesResponsabilidades();

}
