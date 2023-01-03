package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompraDetalle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 25/02/20201
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleRelacionadoProjection {

    Integer getId();
    Integer getOrdenCompraId();
    Integer getArticuloId();
    String getArticuloNombre();
    BigDecimal getCantidad();
    BigDecimal getCantidadRelacionada();
    @Value("#{target.iva == null ? null : (target.iva*100)}")
    BigDecimal getIva();
    Boolean getIvaExento();
    @Value("#{target.ieps== null ? null : (target.ieps*100)}")
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    String getCodigoOC();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();
    BigDecimal getDescuento();
    BigDecimal getPrecio();
    BigDecimal getPrecioUnitario();
    Integer getUnidadMedidaId();
    String getUnidadMedidaNombre();

}
