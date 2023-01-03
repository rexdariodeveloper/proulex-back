package com.pixvs.main.models.projections.BecaSolicitud;

import com.pixvs.main.models.BecaSolicitud;
import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaSolicitud.class})
public interface BecaSolicitudListadoProjection {

    Integer getId();
    String getSolicitudBeca();
    String getEntidad();
    String getCodigoBeca();
    String getCodigoAlumno();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    String getCurso();
    String getModalidad();
    Integer getNivel();
    Integer getDescuento();
    String getEstatus();
    String getNombreCompleto();
    String getNombreCompletoInverso();
}
