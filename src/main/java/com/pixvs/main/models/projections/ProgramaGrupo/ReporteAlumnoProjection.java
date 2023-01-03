package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {ProgramaGrupo.class})
public interface ReporteAlumnoProjection {

    Integer getInscripcionId();

    Integer getAlumnoId();

    String getAlumnoCodigo();

    String getAlumno();

    Integer getSedeInscripcionId();

    String getSedeInscripcion();

    Integer getPlantelInscripcionId();

    String getNotaVenta();

    Integer getSedeGrupoId();

    String getSedeGrupo();

    Integer getGrupoId();

    String getGrupo();

    Integer getPlantelGrupoId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    String getEstatusAlumno();

    Integer getModalidadId();

    String getTipoInscripcion();

    default List<ArchivoProjection> getArchivos() {
        JSONArray archivosJson = new JSONArray();

        archivosJson.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"Reporte de asistencias\"          }"));
        archivosJson.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"Reporte de calificaciones\"          }"));
        archivosJson.add(JSONValue.parse("{              \"id\": 3,              \"nombreOriginal\": \"Historial académico\"          }"));
        archivosJson.add(JSONValue.parse("{              \"id\": 4,              \"nombreOriginal\": \"Boleta de calificaciones\"          }"));
        archivosJson.add(JSONValue.parse("{              \"id\": 5,              \"nombreOriginal\": \"Imprimir Nota de venta\"          }"));

        return (List<ArchivoProjection>) JSONValue.parse(archivosJson.toString());
    }
}