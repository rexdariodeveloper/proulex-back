package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraListadoProjection {


    Integer getId();
    String getCodigo();
    String getProveedorNombre();
    String getSedeNombre();
    String getProveedorRfc();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaOC();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    Integer getEstatusId();
    String getEstatusValor();


    BigDecimal getCantidad();

    @JsonIgnore
    String getEvidenciaStr();
    default List<ArchivoProjection> getEvidencia(){
        return (List<ArchivoProjection>)JSONValue.parse(getEvidenciaStr());
    }

    @JsonIgnore
    String getFacturasStr();
    default List<ArchivoProjection> getFacturas(){
        return (List<ArchivoProjection>)JSONValue.parse(getFacturasStr());
    }

}