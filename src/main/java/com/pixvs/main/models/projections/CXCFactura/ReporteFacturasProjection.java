package com.pixvs.main.models.projections.CXCFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.util.StringCheck;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReporteFacturasProjection {
    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFechaFactura();

    String getSede();

    String getTipoFactura();

    String getFolioFactura();

    String getReceptor();

    String getCliente();

    BigDecimal getMontoFactura();

    String getEstatus();

    BigDecimal getTotal();

    String getNotaVenta();

    String getTodasOV();

    default List<ArchivoProjection> getArchivos() {
        JSONArray facturasJson = new JSONArray();

        facturasJson.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"XML\"          }"));
        facturasJson.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"PDF\"          }"));

        return (List<ArchivoProjection>) JSONValue.parse(facturasJson.toString());
    }
}