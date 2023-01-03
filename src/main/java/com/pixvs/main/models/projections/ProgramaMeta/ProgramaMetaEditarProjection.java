package com.pixvs.main.models.projections.ProgramaMeta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaMeta;
import com.pixvs.main.models.projections.ProgramaMetaDetalle.ProgramaMetaDetalleEditarProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 10/05/2021.
 */
@Projection(types = {ProgramaMeta.class})
public interface ProgramaMetaEditarProjection {

    Integer getId();
    String getCodigo();
    ProgramacionAcademicaComercialComboProjection getProgramacionAcademicaComercial();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<ProgramaMetaDetalleEditarProjection> getDetalles();

}
