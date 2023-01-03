package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale;

import com.pixvs.main.models.ProgramaIdiomaCertificacionVale;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rene Carrillo on 08/12/2020.
 */
@Projection(types = {ProgramaIdiomaCertificacionVale.class})
public interface ProgramaIdiomaCertificacionValeListadoProjection {
    Integer getId();

    String getCodigo();

    String getAlumno();

    String getSede();

    String getCurso();

    Integer getNivel();

    String getCertificacion();

    String getDescuento();

    String getVigencia();

    BigDecimal getCostoFinal();

    String getEstatus();

    default List<Object> getMenuAcciones() {
        JSONArray facturasJson = new JSONArray();

        if(!getEstatus().equals("Cancelado") || !getEstatus().equals("Vencido")){
            if(getEstatus().equals("No generado")){
                facturasJson.add(JSONValue.parse("{              \"id\": 1,              \"nombre\": \"Generar vale de calificación\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 2,              \"nombre\": \"Enviar vale de certificación\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 4,              \"nombre\": \"Historial académico\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 6,              \"nombre\": \"Borrar vale de certificación\"          }"));
            }

            if(getEstatus().equals("Generado")){
                facturasJson.add(JSONValue.parse("{              \"id\": 3,              \"nombre\": \"Reenviar vale de certificación\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 4,              \"nombre\": \"Historial académico\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 5,              \"nombre\": \"Imprimir vale de certificación\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 7,              \"nombre\": \"Cancelar vale de certificación\"          }"));
            }

            if(getEstatus().equals("Aplicado")){
                facturasJson.add(JSONValue.parse("{              \"id\": 4,              \"nombre\": \"Historial académico\"          }"));
                facturasJson.add(JSONValue.parse("{              \"id\": 5,              \"nombre\": \"Imprimir vale de certificación\"          }"));
            }
        }

        return (List<Object>) JSONValue.parse(facturasJson.toString());
    }
}
