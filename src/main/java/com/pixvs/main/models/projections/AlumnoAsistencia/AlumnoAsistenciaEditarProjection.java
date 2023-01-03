package com.pixvs.main.models.projections.AlumnoAsistencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.AlumnoAsistencia;
import com.pixvs.main.models.projections.Alumno.AlumnoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {AlumnoAsistencia.class})
public interface AlumnoAsistenciaEditarProjection {

    Integer getId();
    AlumnoProjection getAlumno();
    Integer getAlumnoId();
    //ProgramaGrupoProfesoresProjection getGrupo();
    Integer getGrupoId();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFecha();
    ControlMaestroMultipleComboProjection getTipoAsistencia();
    Integer getTipoAsistenciaId();
    String getComentario();
    Integer getMinutosRetardo();
    String getMotivoJustificante();
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    //Date getFechaCreacion();
    //Integer getCreadoPorId();
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    //Date getFechaModificacion();
    //Integer getModificadoPorId();
}
