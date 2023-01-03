package com.pixvs.main.models.projections.Alumno;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Alumno;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoListadoGrupoProjection {

    Integer getId();
    String getCodigo();
    String getCodigoUDG();
    String getNombre();
    String getCodigoAlumnoUDG();
    String getPrimerApellido();
    String getSegundoApellido();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaNacimiento();
    String getCorreoElectronico();
    String getTelefonoFijo();
    ControlMaestroMultipleComboProjection getCentroUniversitarioJOBS();
    ControlMaestroMultipleComboProjection getPreparatoriaJOBS();
    ControlMaestroMultipleComboProjection getCarreraJOBS();
    String getBachilleratoTecnologico();
    Integer getFotoId();
    Boolean getActivo();

}
