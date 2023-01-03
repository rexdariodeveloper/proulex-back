package com.pixvs.main.models.projections.EmpleadoContrato;

import com.pixvs.main.models.EmpleadoContrato;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 11/08/2022.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoOpenOfficeProjection {

    Integer getId();

    Integer getEmpleadoId();

    String getEntidad();

    String getTipoContrato();

    String getFolioContrato();

    String getNombre();

    String getNacionalidad();

    String getFechaNacimiento();

    String getGenero();

    String getEstadoCivil();

    String getGradoEstudio();

    String getCurp();

    String getRfc();

    String getDomicilio();

    String getPuesto();

    String getPropositoPuesto();

    String getIngresosAdicionales();

    String getSueldo();

    String getFechaInicio();

    String getFechaFin();

    String getNombreFooter();

    String getVigencia();

    String getCargaHoraria();

    String getNombreCoordinador();

    String getNombreJefeUnidad();

    String getNombreDirector();

}
