package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleRelacionadaProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCompletoProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 10/09/2020.
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleRelacionarProjection {

    Integer getId();
    Integer getOrdenCompraId();
    ArticuloComboProjection getArticulo();
    BigDecimal getCantidad();
    default BigDecimal getCantidadRecibida(){
        BigDecimal cantidadRecibida = BigDecimal.ZERO;
        for(OrdenCompraReciboCompletoProjection recibo : getRecibos()){
            cantidadRecibida = cantidadRecibida.add(recibo.getCantidadRecibo().multiply(getFactorConversion()));
        }
        return cantidadRecibida;
    }
    default BigDecimal getCantidadRelacionada(){
        BigDecimal cantidadRelacionada = BigDecimal.ZERO;
        for(OrdenCompraReciboCompletoProjection recibo : getRecibos()){
            for (CXPFacturaDetalleRelacionadaProjection cxpFacturaDetalle : recibo.getCxpFacturasDetalles()){
                if(cxpFacturaDetalle.getEstatusCXPFacturaId() != ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO && cxpFacturaDetalle.getEstatusCXPFacturaId() != ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA) {
                    cantidadRelacionada = cantidadRelacionada.add(cxpFacturaDetalle.getCantidad());
                }
            }
        }
        return cantidadRelacionada;
    }
    @Value("#{target.iva == null ? null : (target.iva*100)}")
    BigDecimal getIva();
    Boolean getIvaExento();
    @Value("#{target.ieps== null ? null : (target.ieps*100)}")
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    @Value("#{target.ordenCompra.codigo}")
    String getCodigoOC();
    @Value("#{target.ordenCompra.fechaRequerida}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();
    BigDecimal getDescuento();
    BigDecimal getPrecio();
    UnidadMedidaComboProjection getUnidadMedida();

    String getComentarios();
    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.comentarios}")
    String getComentariosPartida();
    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.imagenArticulo}")
    ArchivoProjection getImagenArticulo();

    @JsonIgnore
    List<OrdenCompraReciboCompletoProjection> getRecibos();
    @JsonIgnore
    BigDecimal getFactorConversion();

}
