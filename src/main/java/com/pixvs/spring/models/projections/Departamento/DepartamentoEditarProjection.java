package com.pixvs.spring.models.projections.Departamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.projections.Puesto.PuestoComboContratosProjection;
import com.pixvs.spring.models.Departamento;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 */
@Projection(types = {Departamento.class})
public interface DepartamentoEditarProjection {


    Integer getId();

    Boolean getActivo();

    Integer getDepartamentoPadreId();

    String getPrefijo();

    String getNombre();

    Integer getResponsableId();

    Boolean getAutoriza();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    Integer getNumeroVacantes();

    String getPropositoPuesto();

    String getObservaciones();

    Integer getPuestoId();

    PuestoComboContratosProjection getPuesto();

}