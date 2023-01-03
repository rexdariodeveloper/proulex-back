package com.pixvs.main.models.projections.Programa;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Programa.class})
public interface ProgramaComboProjection {


    Integer getId();
    @Value("#{target.codigo + '-' +target.nombre }")
    String getNombre();
    String getCodigo();
    Boolean getAcademy();
}