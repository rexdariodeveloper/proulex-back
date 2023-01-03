package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 26/11/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboListadoProjection {

    Integer getId();
    Date getFechaCreacion();
    @Value("#{target.cantidadRecibo >= 0 ? 'Recibo' : 'Devolución'}")
    String getTipoMovimiento();
    @Value("#{target.ordenCompraDetalle.articulo}")
    ArticuloComboProjection getArticulo();
    @Value("#{target.ordenCompraDetalle.unidadMedida}")
    UnidadMedidaComboProjection getUnidadMedida();

    @JsonIgnore
    BigDecimal getCantidadRecibo();
    default BigDecimal getCantidad(){
        return getCantidadRecibo().abs();
    }

    @Value("#{target.localidad.almacen}")
    AlmacenComboProjection getAlmacen();
    UsuarioComboProjection getCreadoPor();

    @JsonIgnore
    ArchivoProjection getFacturaPDF();
    @JsonIgnore
    ArchivoProjection getFacturaXML();
    default List<ArchivoProjection> getFacturas(){
        return Arrays.asList(getFacturaPDF(),getFacturaXML());
    }

    List<ArchivoProjection> getEvidencia();

}
