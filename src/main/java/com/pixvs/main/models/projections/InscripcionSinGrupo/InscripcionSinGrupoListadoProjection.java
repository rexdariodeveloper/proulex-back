package com.pixvs.main.models.projections.InscripcionSinGrupo;

import com.pixvs.main.models.InscripcionSinGrupo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 07/08/2021.
 */
@Projection(types = {InscripcionSinGrupo.class})
public interface InscripcionSinGrupoListadoProjection {

    Integer getId();
    String getSede();
    String getAlumnoId();
    String getOrdenVentaDetalleId();
    String getAlumnoCodigo();
    String getAlumnoCodigoUDG();
    String getAlumnoNombre();
    String getAlumnoPrimerApellido();
    String getAlumnoSegundoApellido();
    String getCurso();
    Integer getNivel();
    String getPaModalidadNombre();
    BigDecimal getMontoPago();
    String getMedioPago();
    Integer getEstatusId();
    String getEstatus();
    String getOvCodigo();

}
