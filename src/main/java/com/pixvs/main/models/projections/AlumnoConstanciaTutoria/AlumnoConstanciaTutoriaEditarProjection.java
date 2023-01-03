package com.pixvs.main.models.projections.AlumnoConstanciaTutoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.AlumnoContacto;
import com.pixvs.main.models.projections.Alumno.AlumnoComboProjection;
import com.pixvs.main.models.projections.Alumno.AlumnoEditarProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Projection(types = {AlumnoContacto.class})
public interface AlumnoConstanciaTutoriaEditarProjection {

    Integer getId();
    AlumnoComboProjection getAlumno();
    ArticuloComboProjection getArticulo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
