package com.pixvs.main.models.projections.OrdenVenta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenVenta;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.util.StringCheck;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {OrdenVenta.class})
public interface OrdenVentaReporteProjection {

    Integer getDetalleId();

    Integer getOrdenVentaId();

    String getNotaVenta();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaOV();

    Integer getSucursalId();

    String getSede();

    Integer getInscripcionId();

    String getInscripcion();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInscripcion();

    String getNombreArticulo();

    Integer getAlumnoId();

    String getCodigoAlumno();

    String getNombre();

    Integer getGrupoId();

    String getCodigoGrupo();

    String getGrupoSucursal();

    String getCurso();

    String getModalidad();

    String getNivel();

    String getIdioma();

    String getHorario();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    BigDecimal getSubTotal();

    BigDecimal getDescuento();

    BigDecimal getImpuestos();

    BigDecimal getTotal();

    BigDecimal getPorcentajeBeca();

    String getLigaCentrosPagos();

    String getEstatusOV();

    Integer getCancelacionId();

    String getEstatusInscripcion();

    String getEntregaLibro();

    default List<ArchivoProjection> getArchivos() {
        JSONArray json = new JSONArray();

        json.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"Nota de venta\"          }"));

        if (getGrupoId() != null) {
            json.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"Boleta de calificaciones\"          }"));
        }

        if (!StringCheck.isNullorEmpty(getCodigoAlumno())) {
            json.add(JSONValue.parse("{              \"id\": 3,              \"nombreOriginal\": \"Historial académico\"          }"));
        }

        json.add(JSONValue.parse("{              \"id\": 4,              \"nombreOriginal\": \"Copiar liga de pagos\",              \"rutaFisica\": \"" + (!StringCheck.isNullorEmpty(getLigaCentrosPagos()) ? getLigaCentrosPagos() : "") + "\"          }"));

        if (getCancelacionId() != null) {
            json.add(JSONValue.parse("{              \"id\": 5,              \"nombreOriginal\": \"Solicitud de reembolso\"          }"));
        }

        return (List<ArchivoProjection>) JSONValue.parse(json.toString());
    }
}