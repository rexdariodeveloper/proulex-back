package com.pixvs.main.models.projections.Sucursal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Sucursal;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Projection(types = {Sucursal.class})
public interface SucursalListadoProjection {


    Integer getId();
    String getCodigoSucursal();
    String getNombre();
    UsuarioComboProjection getResponsable();
    BigDecimal getPorcentajeComision();
    String getTelefono();
    Boolean getActivo();

}