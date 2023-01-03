package com.pixvs.main.models.projections.EmpleadoContacto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoComboProjection;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoContacto.class})
public interface EmpleadoContactoComboProjection {


    Integer getId();
    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();


}