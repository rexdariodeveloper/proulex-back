package com.pixvs.main.models.projections.ProgramacionAcademicaComercial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercial;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetalleEditarProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramacionAcademicaComercial.class})
public interface ProgramacionAcademicaComercialEditarProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    PACicloFechaProjection getPaCiclo();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ProgramacionAcademicaComercialDetalleEditarProjection> getDetalles();

}