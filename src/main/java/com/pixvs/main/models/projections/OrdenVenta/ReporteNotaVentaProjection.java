package com.pixvs.main.models.projections.OrdenVenta;


import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 03/06/2022.
 */
@Projection(types = {OrdenVenta.class})
public interface ReporteNotaVentaProjection {
    String getDetalleAlumnoCodigo();

    String getDetalleAlumnoNombre();

    String getDetalleAlumnoApellidos();

    String getDetalleConceptoLinea1();
}
