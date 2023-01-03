package com.pixvs.main.models.projections.Proveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoComboProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoComboProjection;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorComboVistaProjection {


    Integer getId();
    String getCodigo();
    String getNombre();
    String getRfc();
    String getDomicilio();
    String getCp();
    String getTelefono();
    Integer getDiasPlazoCredito();

    @JsonIgnore
    String getFormasPagoStr();
    default List<ProveedorFormaPagoComboProjection> /** List<ProveedorFormaPagoComboProjection> **/ getFormasPago(){
        return (List<ProveedorFormaPagoComboProjection>) JSONValue.parse(getFormasPagoStr());
    }

    @JsonIgnore
    String getMonedaStr();
    default JSONObject /** MonedaComboProjection **/ getMoneda(){
        if(getMonedaStr() != null){
            return (JSONObject) JSONValue.parse(getMonedaStr());
        }
        return null;
    }

    @JsonIgnore
    String getContactoPredeterminadoStr();
    default JSONObject /** ProveedorContactoComboProjection **/ getContactoPredeterminado(){
        if(getContactoPredeterminadoStr() != null){
            return (JSONObject) JSONValue.parse(getContactoPredeterminadoStr());
        }
        return null;
    }

}