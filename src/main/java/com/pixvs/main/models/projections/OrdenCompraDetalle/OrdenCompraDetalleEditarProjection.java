package com.pixvs.main.models.projections.OrdenCompraDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.RequisicionPartida.RequisicionPartidaOCProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Projection(types = {OrdenCompraDetalle.class})
public interface OrdenCompraDetalleEditarProjection {


    Integer getId();

    ArticuloComboProjection getArticulo();
    Integer getArticuloId();

    UnidadMedidaComboProjection getUnidadMedida();
    Integer getUnidadMedidaId();

    BigDecimal getFactorConversion();

    BigDecimal getCantidad();

    BigDecimal getPrecio();

    BigDecimal getDescuento();

    @Value("#{target.iva == null ? null : (target.iva * 100)}")
    BigDecimal getIva();

    Boolean getIvaExento();

    @Value("#{target.ieps == null ? null : (target.ieps * 100)}")
    BigDecimal getIeps();

    BigDecimal getIepsCuotaFija();

    String getCuentaCompras();
    String getComentarios();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.requisicion.codigo}")
    String getCodigoRequisicion();
    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.fechaRequerida}")
    Date getFechaRequerida();
    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.comentarios}")
    String getComentariosRequisicion();
    @Value("#{target.requisicionPartida == null ? null : target.requisicionPartida.imagenArticulo}")
    ArchivoProjection getImagenArticulo();

    RequisicionPartidaOCProjection getRequisicionPartida();

}