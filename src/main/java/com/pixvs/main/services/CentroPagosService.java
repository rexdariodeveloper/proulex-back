package com.pixvs.main.services;

import net.minidev.json.JSONArray;

import java.math.BigDecimal;
import java.util.List;

public interface CentroPagosService {

    String generarLiga(String codigoOV, String correoElectronico, Integer sucursalId, BigDecimal montoPago, JSONArray etiquetas) throws Exception;
    JSONArray crearEtiquetasInscripcion(Integer sucursalId, Integer idiomaId, Integer programaId, Integer modalidadId, Integer horarioId, Integer nivel, BigDecimal monto, String codigoAlumno, String nombre, String primerApellido, String segundoApellido);
    JSONArray crearEtiquetasVentaLibro(Integer sucursalId, Integer idiomaId, BigDecimal monto, String codigoLibro, String nombreLibro, BigDecimal cantidad);
    JSONArray crearEtiquetasVentaCertificacion(Integer sucursalId, BigDecimal monto, String codigoAlumno, String nombre, String primerApellido, String segundoApellido, String nombreArticulo, BigDecimal cantidad);
    JSONArray crearEtiquetasMultiplesArticulos(Integer sucursalId, List<String> productos, BigDecimal monto);
    JSONArray crearEtiquetasExamenUbicacion(Integer sucursalId, Integer idiomaId, Integer programaId, String codigoArticulo, String nombreArticulo, BigDecimal monto, BigDecimal cantidad);

}
