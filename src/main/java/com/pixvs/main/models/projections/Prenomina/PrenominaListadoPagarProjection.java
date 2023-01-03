package com.pixvs.main.models.projections.Prenomina;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Programa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Programa.class})
public interface PrenominaListadoPagarProjection {


    Integer getId();
    Integer getIdEmpleado();
    String getCodigoEmpleado();
    String getSucursal();
    Integer getIdGrupo();
    String getCodigoGrupo();
    String getEmpleado();
    Integer getDeduccionPercepcionId();
    String getTabulador();
    String getNombreGrupo();
    BigDecimal getHorasPagadas();
    String getPercepcion();
    String getDeduccion();
    String getIdioma();
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaDiaFestivo();
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaDiaSuplida();
    String getCategoria();
    String getSueldoPorHora();
    Integer getIdSuplencia();
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaInicioPeriodo();
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getFechaFinPeriodo();
    BigDecimal getSueldoPorHoraDecimal();
    Integer getTipoMovimientoId();
    Integer getMovimientoReferenciaId();
    String getReferenciaProcesoTabla();
    Integer getReferenciaProcesoId();
    String getModalidad();
    Date getGrupoFechaInicio();
    Date getGrupoFechaFin();
}