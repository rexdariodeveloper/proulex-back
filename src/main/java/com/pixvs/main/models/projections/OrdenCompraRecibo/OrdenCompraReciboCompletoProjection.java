package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleRelacionadaProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRelacionarProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 21/08/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboCompletoProjection {

    Integer getId();
    Integer getOrdenCompraDetalleId();
    BigDecimal getCantidadRecibo();
    Date getFechaRecibo();
    Integer getLocalidadId();
    UsuarioComboProjection getCreadoPor();

    @Value("#{target.ordenCompraDetalle.articulo}")
    ArticuloComboProjection getArticulo();
    @Value("#{target.ordenCompraDetalle.unidadMedida}")
    UnidadMedidaComboProjection getUnidadMedida();
    @Value("#{target.ordenCompraDetalle.cantidad}")
    BigDecimal getCantidadRequerida();
    @Value("#{target.localidad.almacen}")
    AlmacenComboProjection getAlmacen();

    default BigDecimal getCantidadPendienteDevolver(){
        BigDecimal cantidad = getCantidadRecibo();
        // Si es una devolución no hay nada que devolver
        if(cantidad.compareTo(BigDecimal.ZERO) < 0){
            return BigDecimal.ZERO;
        }
        // Se resta lo ya devuelto
        for(OrdenCompraReciboDevolucionProjection devolucion : getDevoluciones()){
            cantidad = cantidad.add(devolucion.getCantidadRecibo());
        }
        // Se resta lo ya relacionado
        for(CXPFacturaDetalleRelacionadaProjection cxpFacturaDetalle : getCxpFacturasDetalles()){
            cantidad = cantidad.subtract(cxpFacturaDetalle.getCantidad());
        }
        return cantidad;
    }

    List<OrdenCompraReciboDevolucionProjection> getDevoluciones();
    List<CXPFacturaDetalleRelacionadaProjection> getCxpFacturasDetalles();

    OrdenCompraRelacionarProjection getOrdenCompra();

    List<ArchivoProjection> getEvidencia();
    ArchivoProjection getFacturaPDF();
    ArchivoProjection getFacturaXML();

}
