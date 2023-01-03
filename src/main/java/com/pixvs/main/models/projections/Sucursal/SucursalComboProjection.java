package com.pixvs.main.models.projections.Sucursal;

import com.pixvs.main.models.Sucursal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Projection(types = {Sucursal.class})
public interface SucursalComboProjection {

    Integer getId();

    String getNombre();

    String getPrefijo();

    String getCodigoSucursal();

    int getTipoFacturaGlobalId();

    @Value("#{target.codigoSucursal + ' - ' + target.nombre}")
    String getValor();
}