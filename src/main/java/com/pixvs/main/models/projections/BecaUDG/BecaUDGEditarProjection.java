package com.pixvs.main.models.projections.BecaUDG;

import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.EntidadBeca.EntidadBecaComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaUDG.class})
public interface BecaUDGEditarProjection {

    Integer getId();
    String getCodigoBeca();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    BigDecimal getDescuento();
    ProgramaIdiomaComboProjection getProgramaIdioma();
    Integer getNivel();
    PAModalidadComboSimpleProjection getPaModalidad();
    Date getFechaAlta();
    ControlMaestroMultipleComboProjection getEstatus();
    ControlMaestroMultipleComboProjection getTipo();
    ControlMaestroMultipleComboProjection getEntidad();
    String getCodigoAlumno();
    Integer getSolicitudId();
    String getComentarios();
    Date getFechaExpiracion();
    EntidadBecaComboProjection getEntidadBeca();
}
