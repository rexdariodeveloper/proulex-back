package com.pixvs.main.models.projections.BecaSolicitud;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.BecaSolicitud;
import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGEditarProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaSolicitud.class})
public interface BecaSolicitudEditarProjection {

    Integer getId();
    String getCodigo();
    ControlMaestroMultipleComboProjection getEstatus();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    UsuarioComboProjection getCreadoPor();
    List<BecaUDGEditarProjection> getBecas();
}
