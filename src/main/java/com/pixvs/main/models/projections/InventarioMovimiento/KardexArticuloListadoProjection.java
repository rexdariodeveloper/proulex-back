package com.pixvs.main.models.projections.InventarioMovimiento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface KardexArticuloListadoProjection {

    String getArticulo();

    String getAlmacenLocalidad();

    BigDecimal getExistenciaAnterior();

    BigDecimal getCantidad();

    BigDecimal getEntrada();

    BigDecimal getSalida();

    BigDecimal getTotal();

    BigDecimal getCosto();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();

    String getReferencia();

    String getTipoMovimiento();

    String getRazon();
    
    String getUsuario();
}
