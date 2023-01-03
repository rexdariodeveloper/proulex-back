package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenVenta;
import com.pixvs.main.models.projections.OrdenVenta.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 12/07/2021.
 */
public interface OrdenVentaDao extends CrudRepository<OrdenVenta, String> {

    // Modelo
    OrdenVenta findById(Integer id);
    OrdenVenta findByCodigo(String codigo);

    OrdenVentaCancelacionProjection findOrdenVentaCancelacionProjectedByCodigo(String codigo);

    @Query("SELECT ov.codigo FROM OrdenVenta ov WHERE ov.id = :id")
    String findCodigoById(@Param("id") Integer id);

    @Query("" +
            "SELECT DISTINCT ov \n" +
            "FROM OrdenVenta ov \n" +
            "INNER JOIN ov.detalles ovd \n" +
            "WHERE ovd.id = :detalleId \n" +
            "")
    OrdenVenta findByDetalleId(@Param("detalleId") Integer detalleId);

    List<OrdenVenta> findAllByFacturaId(Integer facturaId);

    // HistorialPVProjection
    @Query(nativeQuery = true, value = "\n" +
            "SELECT * FROM [dbo].[VW_HistorialPV_OrdenesVenta] \n" +
            "WHERE \n" +
            "   usuarioId = :usuarioId \n" +
            "   AND CAST(fecha AS DATE) = CAST(:fecha AS DATE) \n" +
            "   AND CONCAT(codigo,estatus) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<OrdenVentaHistorialPVProjection> findProjectedHistorialPVByFechaAndFiltro(@Param("usuarioId") Integer usuarioId, @Param("fecha") String fecha, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);
    @Query(nativeQuery = true, value = "\n" +
            "SELECT * FROM [dbo].[VW_HistorialPV_OrdenesVenta] \n" +
            "WHERE \n" +
            "   sucursalId IN :sucursalesIds \n" +
            "   AND CAST(fecha AS DATE) = CAST(:fecha AS DATE) \n" +
            "   AND CONCAT(codigo,estatus) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<OrdenVentaHistorialPVProjection> findProjectedHistorialPVByFechaAndFiltro(@Param("sucursalesIds") List<Integer> sucursalesIds, @Param("fecha") String fecha, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

    // HistorialPVResumenProjection
    @Query(nativeQuery = true, value = "\n" +
            "SELECT\n" +
            "   id,\n" +
            "   codigo,\n" +
            "   CONCAT(USU_Nombre,' ' + USU_PrimerApellido,' ' + USU_SegundoApellido) AS usuario,\n" +
            "   fechaCreacion AS fecha,\n" +
            "   montoTotal,\n" +
            "   CONCAT(MPPV_Nombre,'-' + referenciaPago) AS medioPago\n" +
            "FROM VW_OrdenesVenta\n" +
            "INNER JOIN Usuarios ON USU_UsuarioId = COALESCE(modificadoPorId,creadoPorId)\n" +
            "INNER JOIN MediosPagoPV ON MPPV_MedioPagoPVId = medioPagoPVId \n" +
            "WHERE id = :id \n" +
            "")
    OrdenVentaHistorialPVResumenProjection findProjectedHistorialPVResumenById(@Param("id") Integer id);

    // FacturacionNotaVentaProjection
    @Query(nativeQuery = true, value = "SELECT * FROM fn_getDatosFacturacionOV(:sucursalId, :codigoOV)")
    FacturacionNotaVentaProjection findFacturacionNotaVentaProjectedBySucursalIdAndCodigo(@Param("sucursalId") int sucursalId, @Param("codigoOV") String codigoOV);

    @Query(nativeQuery = true, value =
            "SELECT datos.*\n" +
            "FROM OrdenesVenta\n" +
            "     CROSS APPLY dbo.fn_getDatosFacturacionOV(OV_SUC_SucursalId, OV_Codigo) AS datos\n" +
            "WHERE OV_CXCF_FacturaId = :facturaId\n" +
            "ORDER BY Fecha, Codigo")
    List<FacturacionNotaVentaProjection> findAllFacturacionOrdenVentaProjectedByFacturaId(@Param("facturaId") int facturaId);

    // FacturacionGlobalNotaVentaProjection
    @Query(nativeQuery = true, value =
            "SELECT ov.*\n" +
            "FROM Sucursales\n" +
            "     CROSS APPLY fn_getDatosFacturacionGlobalOV(SUC_SucursalId, :fechaInicio, :fechaFin) AS ov\n" +
            "WHERE SUC_SucursalId IN(:sucursalId)\n" +
            "      AND (COALESCE(:plantelId, 0) = 0 OR ISNULL(PlantelId, -1) IN (:plantelId))\n" +
            "      AND EstatusId = :estatusId\n" +
            "      AND (COALESCE(:usuarioId, 0) = 0 OR UsuarioId IN (:usuarioId))\n" +
            "ORDER BY NoIdentificacion\n" +
            "OPTION(RECOMPILE)")
    List<FacturacionGlobalNotaVentaProjection> findDatosFacturacionGlobalNotaVentaProjected(
            @Param("sucursalId") List<Integer> sucursalId,
            @Param("plantelId") List<Integer> plantelId,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin,
            @Param("estatusId") int estatusId,
            @Param("usuarioId") List<Integer> usuarioId
    );

    @Query(nativeQuery = true, value = "SELECT * FROM fn_getDatosFacturacionGlobalOV(NULL, NULL, NULL) " +
            "WHERE FacturaId = :facturaId ORDER BY NoIdentificacion")
    List<FacturacionGlobalNotaVentaProjection> findFacturacionGlobalNotaVentaProjectedByFacturaId(@Param("facturaId") int facturaId);

    // FacturacionGlobalImpuestosNotaVentaProjection
    @Query(nativeQuery = true, value =
            "SELECT impuestos.*\n" +
            "FROM Sucursales\n" +
            "     CROSS APPLY fn_getDatosFacturacionGlobalOV(SUC_SucursalId, :fechaInicio, :fechaFin) AS ov\n" +
            "     CROSS APPLY fn_getDatosFacturacionGlobalImpuestos(ov.Id) AS impuestos\n" +
            "WHERE SUC_SucursalId IN(:sucursalId)\n" +
            "      AND (COALESCE(:plantelId, 0) = 0 OR ISNULL(PlantelId, -1) IN (:plantelId))\n" +
            "      AND EstatusId = :estatusId\n" +
            "      AND (COALESCE(:usuarioId, 0) = 0 OR UsuarioId IN (:usuarioId))\n" +
            "ORDER BY ov.NoIdentificacion,\n" +
            "         impuestos.Clave DESC,\n" +
            "         impuestos.TipoFactor,\n" +
            "         impuestos.Importe\n" +
            "OPTION(RECOMPILE)")
    List<FacturacionGlobalImpuestosNotaVentaProjection> findImpuestosFacturacionGlobalNotaVentaProjected(
            @Param("sucursalId") List<Integer> sucursalId,
            @Param("plantelId") List<Integer> plantelId,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin,
            @Param("estatusId") int estatusId,
            @Param("usuarioId") List<Integer> usuarioId
    );

    @Query(nativeQuery = true, value =
            "SELECT impuestos.*\n" +
            "FROM fn_getDatosFacturacionGlobalOV(NULL, NULL, NULL) AS ov\n" +
            "     CROSS APPLY fn_getDatosFacturacionGlobalImpuestos(ov.Id) AS impuestos\n" +
            "WHERE FacturaId = :facturaId\n" +
            "ORDER BY ov.NoIdentificacion,\n" +
            "         impuestos.Clave DESC,\n" +
            "         impuestos.TipoFactor,\n" +
            "         impuestos.Importe")
    List<FacturacionGlobalImpuestosNotaVentaProjection> findImpuestosFacturacionGlobalNotaVentaProjectedByFacturaId(@Param("facturaId") int facturaId);

    // String
    @Query(nativeQuery = true, value = "SELECT [dbo].[fn_getSiguienteCodigoOV](:prefijo)")
    String getSiguienteCodigoOV(@Param("prefijo") String prefijo);

    @Query(value =
            "SELECT * \n" +
            "FROM VW_RPT_VENTAS\n" +
            "WHERE detalleId = :detalleId\n", nativeQuery = true)
    OrdenVentaReporteProjection findOVReporteProjectedById(@Param("detalleId") Integer detalleId);

    @Query(value =
            "SELECT *\n" +
            "FROM VW_RPT_VENTAS\n" +
            "WHERE (COALESCE(:sedesId, 0) = 0 OR sucursalId IN(:sedesId))\n" +
            "       AND fechaOV BETWEEN ISNULL(CONVERT(NVARCHAR(4000), :fechaInicio), '1900-01-01') AND ISNULL(CONVERT(NVARCHAR(4000), :fechaFin), '2100-12-31')\n" +
            "       AND CASE WHEN :ordenVenta IS NULL THEN 1 ELSE CASE WHEN notaVenta = :ordenVenta THEN 1 ELSE 0 END END = 1\n" +
            "ORDER BY ordenVentaId, cancelacionId\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<OrdenVentaReporteProjection> findReporteVentas(@Param("fechaInicio") String fechaInicio,
                                                        @Param("fechaFin") String fechaFin,
                                                        @Param("sedesId") List<Integer> sedesId,
                                                        @Param("ordenVenta") String ordenVenta);

    @Query(nativeQuery = true, value = "SELECT * FROM fn_getDatosFacturacionOV(:sedeId, :folio) WHERE id = :webId")
    FacturacionNotaVentaProjection getAutofacturaOV(@Param("sedeId") Integer sedeId,
                                                    @Param("folio") String folio,
                                                    @Param("webId") Integer webId);

    @Query(nativeQuery = true, value =
            "SELECT datos.*,\n" +
            "       SUC_Nombre AS Sede,\n" +
            "       MPPV_Nombre AS FormaPago\n" +
            "FROM OrdenesVenta\n" +
            "     CROSS APPLY dbo.fn_getDatosFacturacionOV(OV_SUC_SucursalId, OV_Codigo) AS datos\n" +
            "     INNER JOIN Sucursales ON OV_SUC_SucursalId = SUC_SucursalId\n" +
            "     INNER JOIN MediosPagoPV ON OV_MPPV_MedioPagoPVId = MPPV_MedioPagoPVId\n" +
            "WHERE OV_OrdenVentaId = :ovId")
    FacturacionNotaVentaProjection getAutofacturaOV(@Param("ovId") Integer ovId);

    @Query(nativeQuery = true, value = "SELECT dbo.validarAutofacturaDiasFacturarOV(:ovId)")
    Boolean validarAutofacturaDiasFacturarOV(@Param("ovId") Integer ovId);
}
