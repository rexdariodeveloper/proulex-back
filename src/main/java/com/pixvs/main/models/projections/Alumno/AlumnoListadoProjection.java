package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import com.pixvs.spring.util.DateUtil;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoListadoProjection {

    Integer getId();
    String getCodigo();
    String getReferencia();
    String getNombre();
    String getNombreAlternativo();
    String getApellidos();
    String getEdad();
    String getCorreoElectronico();
    String getTelefono();
    String getSucursalNombre();
    Boolean getActivo();

}
