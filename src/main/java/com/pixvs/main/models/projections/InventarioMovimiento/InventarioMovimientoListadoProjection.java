package com.pixvs.main.models.projections.InventarioMovimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;

import java.math.BigDecimal;
import java.util.Date;

public interface InventarioMovimientoListadoProjection {
    Integer getId();

    ControlMaestroMultipleComboProjection getTipoMovimiento();

    BigDecimal getCantidad();

    BigDecimal getCostoUnitario();

    String getRazon();

    String getReferencia();

    ArticuloComboProjection getArticulo();

    LocalidadComboProjection getLocalidad();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    UsuarioComboProjection getCreadoPor();
}
