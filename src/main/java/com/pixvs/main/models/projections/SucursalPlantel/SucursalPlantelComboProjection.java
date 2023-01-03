package com.pixvs.main.models.projections.SucursalPlantel;

import com.pixvs.main.models.SucursalPlantel;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Projection(types = {SucursalPlantel.class})
public interface SucursalPlantelComboProjection {

    Integer getId();

    String getCodigoSucursal();

    Integer getSucursalId();

    @Value("#{(target.codigoSucursal+' - '+target.nombre) }")
    String getNombre();
}