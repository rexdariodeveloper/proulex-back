package com.pixvs.main.models.projections.BecaUDG;

import com.pixvs.main.models.BecaUDG;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 24/08/2021.
 */
@Projection(types = {BecaUDG.class})
public interface BecaUDGListadoProjection {

    Integer getId();
    String getCodigoProulex();
    String getCodigoBeca();
    String getCodigoEmpleado();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    BigDecimal getDescuento();
    String getCurso();
    Integer getNivel();
    String getModalidad();
    Integer getAlumnoId();
    String getEntidadBeca();

}
