package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.util.StringCheck;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoReportesPCPProjection {

    Integer getId();

    String getCodigoGrupo();

    String getGrupoNombre();

    String getPlantel();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    String getNivel();

    String getHorario();

    Integer getCupo();

    Integer getTotalInscritos();

    String getProfesor();

    default List<ArchivoProjection> getArchivos() {
        JSONArray facturasJson = new JSONArray();

        facturasJson.add(JSONValue.parse("{              \"id\": 1,              \"nombreOriginal\": \"Reporte Evidencias fotográficas\"          }"));
        facturasJson.add(JSONValue.parse("{              \"id\": 2,              \"nombreOriginal\": \"Reporte de Asistencias\"          }"));
        facturasJson.add(JSONValue.parse("{              \"id\": 3,              \"nombreOriginal\": \"Reporte de Calificaciones\"          }"));

        return (List<ArchivoProjection>) JSONValue.parse(facturasJson.toString());
    }
}