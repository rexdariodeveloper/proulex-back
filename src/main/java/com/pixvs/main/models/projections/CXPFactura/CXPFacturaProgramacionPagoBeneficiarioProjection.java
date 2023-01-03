package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicio.CXPSolicitudPagoServicioConfirmarInfoProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaProgramacionPagoBeneficiarioProjection {

    Integer getId();
    Integer getProveedorId();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();
    BigDecimal getMontoProgramado();
    BigDecimal getSaldo();
    Date getFechaRegistro();
    String getCodigoMoneda();
    default Date getFechaVencimiento(){
        Calendar c = Calendar.getInstance();
        c.setTime(getFechaRegistro());
        c.add(Calendar.DATE,getDiasCredito());
        return c.getTime();
    }
    String getOrdenCompraTexto();
    String getFolioSolicitudPagoServicio();
    String getComentarios();

    @JsonIgnore
    String getEvidenciaStr();
    default List<ArchivoProjection> getEvidencia(){
        return (List<ArchivoProjection>) JSONValue.parse(getEvidenciaStr());
    }
    @JsonIgnore
    String getEvidenciaRh();
    default List<ArchivoProjection> getEvidenciaDocumentosRh(){
        return (List<ArchivoProjection>) JSONValue.parse(getEvidenciaRh());
    }
    @JsonIgnore
    String getFacturaPDFStr();
    @JsonIgnore
    String getFacturaXMLStr();
    default List<ArchivoProjection> getFacturas(){
        JSONArray facturasJson = new JSONArray();
        if(getFacturaPDFStr() != null){
            JSONObject facturaPDF = (JSONObject)JSONValue.parse(getFacturaPDFStr());
            facturasJson.add(facturaPDF);
        }
        if(getFacturaXMLStr() != null){
            JSONObject facturaXML = (JSONObject)JSONValue.parse(getFacturaXMLStr());
            facturasJson.add(facturaXML);
        }
        return (List<ArchivoProjection>) JSONValue.parse(facturasJson.toString());
    }

    @JsonIgnore
    String getCxpSolicitudesPagosServiciosStr();
    default List<CXPSolicitudPagoServicioConfirmarInfoProjection> getCxpSolicitudesPagosServicios(){
        return (List<CXPSolicitudPagoServicioConfirmarInfoProjection>) JSONValue.parse(getCxpSolicitudesPagosServiciosStr());
    }

    @JsonIgnore
    Integer getDiasCredito();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    String getSucursal();

    String getBeneficiario();
}
