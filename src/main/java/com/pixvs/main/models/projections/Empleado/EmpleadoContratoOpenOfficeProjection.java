package com.pixvs.main.models.projections.Empleado;

import java.math.BigDecimal;

public interface EmpleadoContratoOpenOfficeProjection {
    Integer getId();
    Integer getIdEmpleado();
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
    BigDecimal getIngresosAdicionales();
    String getSueldo();
    String getFechaInicio();
    String getFechaFin();
    String getNombreFooter();
    String getVigencia();
    //String getResponsabilidades();
    String getCargaHoraria();
    String getNombreCoordinador();
    String getNombreJefeUnidad();
    String getNombreDirector();
}
