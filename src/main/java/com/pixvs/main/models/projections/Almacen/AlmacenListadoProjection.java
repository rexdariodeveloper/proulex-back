package com.pixvs.main.models.projections.Almacen;

import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalListadoProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Almacen.class})
public interface AlmacenListadoProjection {

    Integer getId();
    String getCodigoAlmacen();
    String getNombre();
    SucursalListadoProjection getSucursal();
    UsuarioComboProjection getResponsable();
    Boolean getMismaDireccionSucursal();
    @Value("#{target.mismaDireccionSucursal ? target.sucursal.telefono : target.telefono}")
    String getTelefono();
    Boolean getActivo();

}
