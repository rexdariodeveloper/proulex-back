package com.pixvs.main.models.projections.ProveedorContacto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.ProveedorContacto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Projection(types = {ProveedorContacto.class})
public interface ProveedorContactoComboProjection {


    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    @JsonIgnore
    Boolean getPredeterminado();

}