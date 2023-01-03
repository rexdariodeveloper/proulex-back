package com.pixvs.main.models.projections.Inscripcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.util.StringCheck;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReporteInscripcionesProjection {

    Integer getSedeInscripcionId();

    String getSedeInscripcion();

    Integer getInscripcionId();

    String getInscripcion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInscripcion();

    Integer getNotaVentaId();

    String getNotaVenta();

    String getEstatusNotaVenta();

    String getLigaPago();

    String getPrimerApellido();

    String getSegundoApellido();

    String getNombre();

    Integer getAlumnoId();

    String getAlumno();

    Integer getSedeGrupoId();

    String getSedeGrupo();

    Integer getGrupoId();

    String getGrupo();

    String getTipoGrupo();

    String getCurso();

    Integer getModalidadId();

    String getModalidad();

    String getNivel();

    String getIdioma();

    String getHorario();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    BigDecimal getPorcentajeDescuento();

    BigDecimal getSubtotal();

    BigDecimal getDescuento();

    BigDecimal getImpuestos();

    BigDecimal getTotal();

    String getEntidadBeca();

    BigDecimal getPorcentajeBeca();

    Integer getEstatusId();

    String getEstatus();

    Integer getCicloId();

    Integer getPaId();

    String getTipoInscripcion();

    String getEstatusInscripcion();

    default List<ArchivoProjection> getArchivos() {
        JSONArray facturasJson = new JSONArray();

        if (!StringCheck.isNullorEmpty(getNotaVenta())) {
            facturasJson.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"Nota de venta\"          }"));
        }

        facturasJson.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"Boleta de calificaciones\"          }"));
        facturasJson.add(JSONValue.parse("{              \"id\": 3,              \"nombreOriginal\": \"Historial académico\"          }"));

        if (!StringCheck.isNullorEmpty(getLigaPago())) {
            facturasJson.add(JSONValue.parse("{              \"id\": 4,              \"nombreOriginal\": \"Copiar liga de pagos\",              \"rutaFisica\": \"" + getLigaPago() + "\"          }"));
        }

        return (List<ArchivoProjection>) JSONValue.parse(facturasJson.toString());
    }
}
