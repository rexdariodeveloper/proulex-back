package com.pixvs.main.models.projections.OrdenVentaCancelacion;

import com.pixvs.main.models.OrdenVentaCancelacion;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 25/03/2022.
 */
@Projection(types = {OrdenVentaCancelacion.class})
public interface OrdenVentaCancelacionListadoProjection {

    Integer getId();

    String getTipoMovimiento();

    String getCodigo();

    String getOrdenVenta();

    String getSucursal();

    Date getFechaCancelacion();

    BigDecimal getImporteReembolsar();

    String getCreadoPor();

    String getEstatus();

    Integer getArchivos();
}