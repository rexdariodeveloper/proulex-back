package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {Alumno.class})
public interface AlumnoPagoProjection {

    String getCodigo();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    String getPlantel();
    String getClave();
    String getGrupo();
    String getHorario();
    String getSede();
    String getEscuela();
    String getReferencia();
    String getPoliza();
    String getPrecio();
    String getEstatus();
}
