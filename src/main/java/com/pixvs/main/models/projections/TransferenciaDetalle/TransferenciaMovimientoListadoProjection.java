package com.pixvs.main.models.projections.TransferenciaDetalle;

import com.pixvs.main.models.Transferencia;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {Transferencia.class})
public interface TransferenciaMovimientoListadoProjection {

    Integer getId();

    Date getFecha();

    String getCodigoArticulo();

    String getNombreArticulo();

    String getUM();

    BigDecimal getCantidadEnviada();

    BigDecimal getCantidadTransferida();

    BigDecimal getCantidadDevuelta();

    BigDecimal getCantidadAjuste();

    String getAlmacenOrigen();

    String getAlmacenDestino();

    String getUsuario();
}
