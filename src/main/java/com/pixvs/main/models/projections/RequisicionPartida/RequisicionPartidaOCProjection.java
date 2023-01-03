package com.pixvs.main.models.projections.RequisicionPartida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.RequisicionPartida;
import com.pixvs.main.models.projections.Articulo.ArticuloPrecargarProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionConvertirListadoProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 28/10/2020.
 */
@Projection(types = {RequisicionPartida.class})
public interface RequisicionPartidaOCProjection {

    Integer getId();
    String getComentarios();

}
