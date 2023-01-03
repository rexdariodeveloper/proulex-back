package com.pixvs.main.models.projections.RequisicionPartida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.RequisicionPartida;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 22/10/2020.
 */
@Projection(types = {RequisicionPartida.class})
public interface RequisicionPartidaEditarProjection {

    Integer getId();
    Integer getNumeroPartida();
    ArticuloComboProjection getArticulo();
    UnidadMedidaComboProjection getUnidadMedida();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();
    BigDecimal getCantidadRequerida();
    ControlMaestroMultipleComboProjection getEstadoPartida();
    String getComentarios();
    ArchivoProjection getImagenArticulo();
    Integer getImagenArticuloId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}