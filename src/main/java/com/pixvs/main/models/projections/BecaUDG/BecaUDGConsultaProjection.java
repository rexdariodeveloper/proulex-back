package com.pixvs.main.models.projections.BecaUDG;

import com.pixvs.main.models.BecaUDG;
import com.pixvs.main.models.projections.EntidadBeca.EntidadBecaComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSimpleProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaUDG.class})
public interface BecaUDGConsultaProjection {

    Integer getId();
    String getCodigoBeca();
    String getCodigoEmpleado();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getParentesco();
    BigDecimal getDescuento();
    ProgramaIdiomaComboSimpleProjection getProgramaIdioma();
    Integer getNivel();
    PAModalidadComboProjection getPaModalidad();
    String getFirmaDigital();
    Date getFechaAlta();
    ControlMaestroMultipleComboSimpleProjection getEstatus();
    ControlMaestroMultipleComboSimpleProjection getTipo();
    Date getFechaExpiracion();
    EntidadBecaComboProjection getEntidadBeca();
}
