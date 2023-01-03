package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraCargarReciboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 16/09/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboCargarFacturaProjection {

    Integer getId();
    OrdenCompraCargarReciboProjection getOrdenCompra();
    Integer getOrdenCompraDetalleId();
    BigDecimal getCantidadRecibo();
    default BigDecimal getCantidadDevuelta(){
        BigDecimal cantidadDevuelta = BigDecimal.ZERO;
        for(OrdenCompraReciboDevolucionProjection devolucion : getDevoluciones()){
            cantidadDevuelta = cantidadDevuelta.add(devolucion.getCantidadRecibo());
        }
        return cantidadDevuelta;
    }
    ArchivoProjection getFacturaPDF();
    ArchivoProjection getFacturaXML();

    @JsonIgnore
    List<OrdenCompraReciboDevolucionProjection> getDevoluciones();

}
