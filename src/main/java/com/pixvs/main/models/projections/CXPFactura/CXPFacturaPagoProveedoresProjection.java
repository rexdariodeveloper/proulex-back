package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleDatosOCProjection;
import com.pixvs.main.models.projections.CXPPagoDetalle.CXPPagoDetalleProgramacionPagoProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaPagoProveedoresProjection {

    Integer getId();
    Integer getProveedorId();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();
    BigDecimal getSaldo();
    Date getFechaRegistro();
    default Date getFechaVencimiento(){
        Calendar c = Calendar.getInstance();
        c.setTime(getFechaRegistro());
        c.add(Calendar.DATE,getDiasCredito());
        return c.getTime();
    }
    String getOrdenCompraTexto();
    String getFolioSolicitudPagoServicio();

    @JsonIgnore
    String getEvidenciaStr();
    default List<ArchivoProjection> getEvidencia(){
        return (List<ArchivoProjection>) JSONValue.parse(getEvidenciaStr());
    }
    String getEvidenciaStrRh();
    default List<ArchivoProjection> getEvidenciaRh(){
        return (List<ArchivoProjection>) JSONValue.parse(getEvidenciaStrRh());
    }
    @JsonIgnore
    String getFacturaPDFStr();
    Integer getFacturaPDFId();
    @JsonIgnore
    String getFacturaXMLStr();
    Integer getFacturaXMLId();
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
    Integer getDiasCredito();

}
