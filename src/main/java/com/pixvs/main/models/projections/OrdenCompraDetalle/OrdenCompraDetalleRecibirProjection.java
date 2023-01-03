package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCompletoProjection;
import com.pixvs.main.models.projections.RequisicionPartida.RequisicionPartidaConvertirListadoProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 21/08/2020.
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleRecibirProjection {

    Integer getId();
    ArticuloComboProjection getArticulo();
    UnidadMedidaComboProjection getUnidadMedida();
    BigDecimal getCantidad();
    default BigDecimal getCantidadPendiente(){
        BigDecimal cantidadPendiente = getCantidad();
        for(OrdenCompraReciboCompletoProjection recibo : getRecibos()){
            cantidadPendiente = cantidadPendiente.subtract(recibo.getCantidadRecibo());
        }
        return cantidadPendiente;
    }

    @JsonIgnore
    List<OrdenCompraReciboCompletoProjection> getRecibos();

    RequisicionPartidaConvertirListadoProjection getRequisicionPartida();

    String getComentarios();

}
