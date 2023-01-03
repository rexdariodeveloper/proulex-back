package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 15/09/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaListadoProjection {

    Integer getId();
    String getProveedorNombre();
    String getProveedorRfc();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();
    Date getFechaRegistro();
    Date getFechaVencimiento();
    String getOrdenCompraTexto();
    String getSedeNombre();
    String getCodigoRecibo();
    Date getFechaReciboRegistro();
    Boolean getRelacionada();

    @JsonIgnore
    String getEvidenciaStr();
    default List<ArchivoProjection> getEvidencia(){
        return (List<ArchivoProjection>) JSONValue.parse(getEvidenciaStr());
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

}
