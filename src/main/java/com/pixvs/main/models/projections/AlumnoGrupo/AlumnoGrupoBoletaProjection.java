package com.pixvs.main.models.projections.AlumnoGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.AlumnoGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {AlumnoGrupo.class})
public interface AlumnoGrupoBoletaProjection {

    String getCodigo();

    String getAlumno();

    String getProgramaCorto();

    String getProgramaLargo();

    String getIdioma();

    String getModalidad();

    String getTipo();

    String getNivel();

    String getInicio();

    String getFin();

    String getHorario();

    String getInstructor();

    String getSede();

    BigDecimal getCalificacion();

    String getCalificacionLetra();

    String getSiguiente();

    String getInstitucion();

    String getCuenta();

    String getClabe();

    String getConvenio();

    String getReferencia();
}
