package com.pixvs.main.services;

import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.PAModalidadHorarioDao;
import com.pixvs.main.dao.ProgramaDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.PAModalidad;
import com.pixvs.main.models.PAModalidadHorario;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.Sucursal;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import jdk.nashorn.internal.parser.JSONParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class CentroPagosServiceImpl implements CentroPagosService {

    private String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQcm91bGV4Iiwic3ViIjoidS8xIiwiaWQiOjEsImNvcnJlbyI6InBpeHZzLnNlcnZlckBnbWFpbC5jb20iLCJpZFJvbCI6MSwiaWF0IjoxNTkwMDAxMTM5fQ.H7vy9_7cGZGqwZYehOl1jVtxwRrwA6pPmb_GQEduyNE";

    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private PAModalidadHorarioDao paModalidadHorarioDao;

    public String generarLiga(
            String codigoOV,
            String correoElectronico,
            Integer sucursalId,
            BigDecimal montoPago,
            JSONArray etiquetas
    ) throws Exception {

        montoPago = montoPago.setScale(2,BigDecimal.ROUND_DOWN);

        Sucursal sucursal = sucursalDao.findById(sucursalId);

        String urlService = "https://api.centropagos.fiid.mx/api/v1/pagos/save";
        URL url = new URL(urlService);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + bearerToken);
        con.setDoOutput(true);
        con.setDoInput(true);
        String jsonInputString = "" +
                "{" +
                "  \"id\": null," +
                "  \"folio\": \"" + codigoOV + "\",\n" +
                "  \"codigoAlumno\": \"" + codigoOV + "\",\n" +
                "  \"correoElectronico\": \"" + correoElectronico + "\",\n" +
                "  \"correoElectronicoConfirmacion\": \"" + correoElectronico + "\",\n" +
                "  \"sedePrefijo\": \"" + sucursal.getPrefijo() + "\",\n" +
                "  \"origen\": {\n" +
                "       \"id\": 2000082\n" +
                "  },\n" +
                "  \"montoPago\": " + montoPago + ",\n" +
                "  \"montoPagoConfirmacion\": " + montoPago + ",\n" +
                "  \"centroPagosEtiquetas\": " + etiquetas.toString() + "\n" +
                "}";
        OutputStream os = con.getOutputStream();
        byte[] input = jsonInputString.getBytes("utf-8");
        os.write(input, 0, input.length);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = (JSONObject) JSONValue.parse(response.toString());

        return ((JSONObject)jsonResponse.get("data")).getAsString("lineaPago");
    }


    public JSONArray crearEtiquetasInscripcion(
            Integer sucursalId,
            Integer idiomaId,
            Integer programaId,
            Integer modalidadId,
            Integer horarioId,
            Integer nivel,
            BigDecimal monto,
            String codigoAlumno,
            String nombre,
            String primerApellido,
            String segundoApellido
    ){

        JSONArray etiquetas = new JSONArray();

        etiquetas.appendElement(getEtiquetaSede(sucursalId));
        etiquetas.appendElement(getEtiquetaIdioma(idiomaId));
        etiquetas.appendElement(getEtiquetaPrograma(programaId));
        etiquetas.appendElement(getEtiquetaModalidad(modalidadId));
        etiquetas.appendElement(getEtiquetaHorario(horarioId));
        etiquetas.appendElement(getEtiquetaNivel(nivel));
        etiquetas.appendElement(getEtiquetaMonto(monto));
        etiquetas.appendElement(getEtiquetaCodigoAlumno(codigoAlumno));
        String nombreCompleto = nombre + " " + primerApellido;
        if(segundoApellido != null){
            nombreCompleto += " " + segundoApellido;
        }
        etiquetas.appendElement(getEtiquetaNombre(nombreCompleto));

        return etiquetas;

    }

    public JSONArray crearEtiquetasVentaLibro(
            Integer sucursalId,
            Integer idiomaId,
            BigDecimal monto,
            String codigoLibro,
            String nombreLibro,
            BigDecimal cantidad
    ){

        JSONArray etiquetas = new JSONArray();

        etiquetas.appendElement(getEtiquetaSede(sucursalId));
        etiquetas.appendElement(getEtiquetaIdioma(idiomaId));
        etiquetas.appendElement(getEtiquetaMonto(monto));
        etiquetas.appendElement(getEtiquetaCodigoLibro(codigoLibro));
        etiquetas.appendElement(getEtiquetaNombreLibro(nombreLibro));
        etiquetas.appendElement(getEtiquetaCantidad(cantidad));

        return etiquetas;

    }

    public JSONArray crearEtiquetasVentaCertificacion(
            Integer sucursalId,
            BigDecimal monto,
            String codigoAlumno,
            String nombre,
            String primerApellido,
            String segundoApellido,
            String nombreArticulo,
            BigDecimal cantidad
    ){

        JSONArray etiquetas = new JSONArray();

        etiquetas.appendElement(getEtiquetaSede(sucursalId));
        etiquetas.appendElement(getEtiquetaMonto(monto));
        etiquetas.appendElement(getEtiquetaCodigoAlumno(codigoAlumno));
        String nombreCompleto = nombre + " " + primerApellido;
        if(segundoApellido != null){
            nombreCompleto += " " + segundoApellido;
        }
        etiquetas.appendElement(getEtiquetaNombre(nombreCompleto));
        etiquetas.appendElement(getEtiquetaNombreArticulo(nombreArticulo));
        etiquetas.appendElement(getEtiquetaCantidad(cantidad));

        return etiquetas;

    }

    public JSONArray crearEtiquetasMultiplesArticulos(
            Integer sucursalId,
            List<String> productos,
            BigDecimal monto
    ){

        JSONArray etiquetas = new JSONArray();

        etiquetas.appendElement(getEtiquetaSede(sucursalId));
        etiquetas.appendElement(getEtiquetaProductos(productos));
        etiquetas.appendElement(getEtiquetaMonto(monto));

        return etiquetas;

    }

    public JSONArray crearEtiquetasExamenUbicacion(
            Integer sucursalId,
            Integer idiomaId,
            Integer programaId,
            String codigoArticulo,
            String nombreArticulo,
            BigDecimal monto,
            BigDecimal cantidad
    ){

        JSONArray etiquetas = new JSONArray();

        etiquetas.appendElement(getEtiquetaSede(sucursalId));
        etiquetas.appendElement(getEtiquetaIdioma(idiomaId));
        etiquetas.appendElement(getEtiquetaPrograma(programaId));
        etiquetas.appendElement(getEtiquetaCodigoArticulo(codigoArticulo));
        etiquetas.appendElement(getEtiquetaNombreArticulo(nombreArticulo));
        etiquetas.appendElement(getEtiquetaMonto(monto));
        etiquetas.appendElement(getEtiquetaCantidad(cantidad));

        return etiquetas;

    }

    private JSONObject generarEtiqueta(String etiqueta, String valor){

        JSONObject etiquetaSede = new JSONObject();
        etiquetaSede.put("etiqueta",etiqueta);
        etiquetaSede.put("valor",valor);

        return etiquetaSede;

    }

    private JSONObject generarEtiqueta(String etiqueta, Integer valor){

        JSONObject etiquetaSede = new JSONObject();
        etiquetaSede.put("etiqueta",etiqueta);
        etiquetaSede.put("valor",valor);

        return etiquetaSede;

    }

    private JSONObject generarEtiqueta(String etiqueta, BigDecimal valor){

        JSONObject etiquetaSede = new JSONObject();
        etiquetaSede.put("etiqueta",etiqueta);
        etiquetaSede.put("valor",valor);

        return etiquetaSede;

    }

    private JSONObject getEtiquetaSede(Integer sucursalId){

        Sucursal sucursal = sucursalDao.findById(sucursalId);

        return generarEtiqueta("SEDE",sucursal.getNombre());

    }

    private JSONObject getEtiquetaIdioma(Integer idiomaId){

        ControlMaestroMultiple idioma = controlMaestroMultipleDao.findCMMById(idiomaId);

        return generarEtiqueta("IDIOMA",idioma.getValor());

    }

    private JSONObject getEtiquetaPrograma(Integer programaId){

        Programa programa = programaDao.findById(programaId);

        return generarEtiqueta("PROGRAMA",programa.getNombre());

    }

    private JSONObject getEtiquetaModalidad(Integer modalidadId){

        PAModalidad paModalidad = paModalidadDao.findById(modalidadId);

        return generarEtiqueta("MODALIDAD",paModalidad.getNombre());

    }

    private JSONObject getEtiquetaHorario(Integer horarioId){

        PAModalidadHorario paModalidadHorario = paModalidadHorarioDao.findById(horarioId);

        return generarEtiqueta("HORARIO",paModalidadHorario.getNombre());

    }

    private JSONObject getEtiquetaNivel(Integer nivel){

        return generarEtiqueta("NIVEL",nivel);

    }

    private JSONObject getEtiquetaMonto(BigDecimal monto){

        monto = monto.setScale(2,BigDecimal.ROUND_DOWN);

        return generarEtiqueta("MONTO",monto);

    }

    private JSONObject getEtiquetaCodigoAlumno(String codigoAlumno){

        return generarEtiqueta("CÓDIGO DE ALUMNO",codigoAlumno);

    }

    private JSONObject getEtiquetaNombre(String nombre){

        return generarEtiqueta("NOMBRE",nombre);

    }

    private JSONObject getEtiquetaCodigoLibro(String codigoLibro){

        return generarEtiqueta("CÓDIGO DEL LIBRO",codigoLibro);

    }

    private JSONObject getEtiquetaNombreLibro(String nombreLibro){

        return generarEtiqueta("NOMBRE DEL LIBRO",nombreLibro);

    }


    private JSONObject getEtiquetaCantidad(BigDecimal cantidad){

        cantidad = cantidad.setScale(2,BigDecimal.ROUND_DOWN);

        return generarEtiqueta("CANTIDAD",cantidad);

    }

    private JSONObject getEtiquetaProductos(List<String> productos){

        String productosStr = null;
        for(String producto : productos){
            if(productosStr == null){
                productosStr = producto;
            }else{
                productosStr += "\\n" + producto;
            }
        }

        if(productosStr.length() > 200){
            productosStr = productosStr.substring(0,200);
        }
        return generarEtiqueta("PRODUCTOS",productosStr);

    }

    private JSONObject getEtiquetaCodigoArticulo(String codigoArticulo){

        return generarEtiqueta("CÓDIGO DEL ARTÍCULO",codigoArticulo);

    }

    private JSONObject getEtiquetaNombreArticulo(String nombreArticulo){

        return generarEtiqueta("NOMBRE DEL ARTÍCULO",nombreArticulo);

    }

}
