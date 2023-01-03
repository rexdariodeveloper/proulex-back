package com.pixvs.main.models.projections.OrdenCompra;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleDevolverProjection;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 24/08/2020.
 */
@Projection(types = {OrdenCompra.class})
public interface OrdenCompraDevolverProjection {

    Integer getId();
    String getCodigo();

    @JsonIgnore
    String getProveedorStr();
    default JSONObject getProveedor(){ // ProveedorComboVistaProjection
        return (JSONObject) JSONValue.parse(getProveedorStr());
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRequerida();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    @JsonIgnore
    String getDetallesStr();
    default List<OrdenCompraDetalleDevolverProjection> getDetalles(){
        return (List<OrdenCompraDetalleDevolverProjection>) JSONValue.parse(getDetallesStr());
    }

}
