package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCompletoProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 24/08/2020.
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleDevolverProjection {

    Integer getId();
    ArticuloComboProjection getArticulo();
    UnidadMedidaComboProjection getUnidadMedida();
    BigDecimal getCantidad();
    List<OrdenCompraReciboCompletoProjection> getRecibosPendientes();

}
