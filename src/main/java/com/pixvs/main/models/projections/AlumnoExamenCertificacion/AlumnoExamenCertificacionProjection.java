package com.pixvs.main.models.projections.AlumnoExamenCertificacion;

import com.pixvs.main.models.AlumnoExamenCertificacion;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {AlumnoExamenCertificacion.class})
public interface AlumnoExamenCertificacionProjection {

    Integer getId();
    Date getFecha();
    String getCodigo();
    String getAlumno();
    String getArticulo();
    Integer getNivel();
    BigDecimal getCalificacion();

    Integer getAlumnoId();
    Integer getTipoId();
    Integer getEstatusId();

}
