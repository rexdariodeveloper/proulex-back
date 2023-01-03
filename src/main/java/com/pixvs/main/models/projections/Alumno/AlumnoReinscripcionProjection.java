package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 16/07/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoReinscripcionProjection {

    Integer getId();
    String getCodigo();
    Integer getBecaId();
    String getBecaCodigo();
    String getCodigoUDG();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getCurso();
    String getModalidad();
    String getHorario();
    Integer getNivelReinscripcion();
    BigDecimal getCalificacion();
    BigDecimal getCalificacionMinima();
    Boolean getLimiteFaltasExcedido();
    Integer getIdiomaId();
    Integer getProgramaId();
    Integer getModalidadId();
    Integer getHorarioId();
    Integer getSucursalId();
    Integer getArticuloId();
    Integer getNumeroGrupo();
    Integer getGrupoReinscripcionId();
    String getGrupoReinscripcionCodigo();
    Boolean getAprobado();

}
