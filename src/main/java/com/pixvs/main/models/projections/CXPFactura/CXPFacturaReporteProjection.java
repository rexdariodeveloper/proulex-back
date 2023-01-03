package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CXPFacturaReporteProjection {

    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFecha();

    Integer getProveedorId();

    String getProveedor();

    String getFolio();

    BigDecimal getMonto();

    Integer getMonedaId();

    String getMoneda();

    Integer getEstatusId();

    String getEstatus();

    Integer getXMLId();

    Integer getPDFId();

    String getPathArchivo();

    String getNombreArchivo();

    default List<ArchivoProjection> getArchivos() {
        JSONArray facturasJson = new JSONArray();

        facturasJson.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"XML\"          }"));
        facturasJson.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"PDF\"          }"));

        return (List<ArchivoProjection>) JSONValue.parse(facturasJson.toString());
    }
}