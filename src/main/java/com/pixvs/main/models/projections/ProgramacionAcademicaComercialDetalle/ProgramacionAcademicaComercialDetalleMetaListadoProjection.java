package com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramacionAcademicaComercialDetalle;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaCalcularDiasProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 10/05/2021.
 */
@Projection(types = {ProgramacionAcademicaComercialDetalle.class})
public interface ProgramacionAcademicaComercialDetalleMetaListadoProjection {

    Integer getPaModalidadId();
    String getPaModalidadNombre();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();
    Integer getProgramaId();
    String getProgramaNombre();

}
