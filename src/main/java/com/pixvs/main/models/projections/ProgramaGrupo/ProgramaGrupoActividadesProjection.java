package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoActividadesProjection {

    String getSucursalNombre();
    String getProgramacionCodigo();
    String getCodigoGrupo();
    String getNivel();
    String getGrupo();
    String getFechaInicio();
    String getFechaFin();
    String getModalidadNombre();
    String getProfesor();
    String getHorario();

    String  getEvaluacionNombre();
    Integer getEvaluacionPorcentaje();
    String  getActividadNombre();
    Integer getActividadPuntaje();
    String  getAlumno();

    Integer getAlumnoId();
    Integer getGrupoId();
    Integer getEvaluacionId();
    Integer getEvaluacionDetalleId();
}
