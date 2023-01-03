package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaCertificacionVale;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Rene Carrillo on 08/12/2020.
 */
@Projection(types = {ProgramaIdiomaCertificacionVale.class})
public interface ProgramaIdiomaCertificacionValeGeneradoProjection {
    Integer getId();

    Integer getProgramaIdiomaCertificacionDescuentoId();

    BigDecimal getPorcentajeDescuento();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getVigencia();

    BigDecimal getCostoUltimo();

    BigDecimal getCostoFinal();
}
