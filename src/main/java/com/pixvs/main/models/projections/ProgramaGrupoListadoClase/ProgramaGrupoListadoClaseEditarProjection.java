package com.pixvs.main.models.projections.ProgramaGrupoListadoClase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupoIncompanyMaterial;
import com.pixvs.main.models.ProgramaGrupoListadoClase;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoListadoClase.class})
public interface ProgramaGrupoListadoClaseEditarProjection {


    Integer getId();

    Integer getGrupoId();

    Date getFecha();

    EmpleadoComboProjection getEmpleado();

    ControlMaestroMultipleComboProjection getFormaPago();

    String getComentario();

    String getCategoriaProfesor();

    BigDecimal getSueldoProfesor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaPago();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaDeduccion();
}