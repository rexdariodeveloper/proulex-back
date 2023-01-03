package com.pixvs.main.models.projections.Programa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Programa.class})
public interface ProgramaListadoProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    @Value("#{(target.idiomas.size() ) }")
    Integer getIdiomasSize();

    Boolean getActivo();

}