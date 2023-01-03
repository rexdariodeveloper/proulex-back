package com.pixvs.main.models.projections.PADescuentoUsuarioAutorizado;

import com.pixvs.main.models.PADescuentoSucursal;
import com.pixvs.main.models.PADescuentoUsuarioAutorizado;
import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuentoUsuarioAutorizado.class})
public interface PADescuentoUsuarioAutorizadoEditarProjection {


    Integer getId();

    Integer getDescuentoId();

    UsuarioComboProjection getUsuario();

    SucursalComboProjection getSucursal();

    Boolean getActivo();
}