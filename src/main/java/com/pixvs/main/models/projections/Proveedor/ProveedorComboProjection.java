package com.pixvs.main.models.projections.Proveedor;

import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 02/02/2021.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorComboProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    String getRfc();
    String getDomicilio();
    String getCp();
    String getTelefono();
    Integer getDiasPlazoCredito();
    MonedaComboProjection getMoneda();

}
