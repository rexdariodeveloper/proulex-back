package com.pixvs.main.models.projections.Sucursal;

import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;

public interface SucursalComboIncompanyProjection {
    Integer getId();

    String getNombre();

    String getPrefijo();

    String getCodigoSucursal();

    int getTipoFacturaGlobalId();

    @Value("#{target.codigoSucursal + ' - ' + target.nombre}")
    String getValor();

    UsuarioComboProjection getResponsable();

    UsuarioComboProjection getCoordinador();
}
