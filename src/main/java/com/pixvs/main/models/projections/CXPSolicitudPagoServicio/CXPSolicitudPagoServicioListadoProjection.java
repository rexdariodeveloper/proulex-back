package com.pixvs.main.models.projections.CXPSolicitudPagoServicio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPSolicitudPagoServicio;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicioDetalle.CXPSolicitudPagoServicioDetalleListadoProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPagoServicio.class})
public interface CXPSolicitudPagoServicioListadoProjection {

    Integer getId();
    String getCodigoSolicitud();
    Date getFechaSolicitud();
    Date getFechaVencimiento();
    Integer getSedeId();
    String getSede();
    String getSolicitud();
    String getServicio();
    String getUsuario();
    String getEstatus();

    @JsonIgnore
    String getFacturaPDFStr();
    @JsonIgnore
    String getFacturaXMLStr();
    default JSONArray getFacturas() { // List<ArchivoProjection>
        JSONArray facturas = new JSONArray();
        if(getFacturaPDFStr() != null){
            facturas.appendElement(JSONValue.parse(getFacturaPDFStr()));
        }
        if(getFacturaXMLStr() != null){
            facturas.appendElement(JSONValue.parse(getFacturaXMLStr()));
        }
        return facturas;
    }

    String getProveedorNombre();
    BigDecimal getMontoRegistro();

}
