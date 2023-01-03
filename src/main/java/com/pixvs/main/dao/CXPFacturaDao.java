package com.pixvs.main.dao;

import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.CXPFactura.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 08/09/2020.
 */
public interface CXPFacturaDao extends CrudRepository<CXPFactura, String> {

    CXPFactura findById(Integer id);
    List<CXPFactura> findByIdIn(List<Integer> ids);

    CXPFacturaEditarProjection findProjectedEditarById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_PROJECTION_CXPFactura] WHERE estatusId = :estatusId")
    List<CXPFacturaListadoProjection> findProjectedListadoAllByEstatusId(@Param("estatusId") Integer estatusId);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_PROJECTION_CXPFactura] WHERE estatusId = :estatusId AND proveedorId IN :proveedorIds AND (:fechaRegistroInicio IS NULL OR fechaRegistro >= :fechaRegistroInicio) AND (:fechaRegistroFin IS NULL OR fechaRegistro <= :fechaRegistroFin)")
    List<CXPFacturaListadoProjection> findProjectedListadoAllByEstatusIdAndProveedorIdInAndFechaRegistroGreaterThanAndFechaRegistroLessThan(@Param("estatusId") Integer estatusId, @Param("proveedorIds") List<Integer> proveedorIds, @Param("fechaRegistroInicio") Date fechaRegistroInicio, @Param("fechaRegistroFin") Date fechaRegistroFin);

//    CXPFacturaPagoProveedoresProjection findProjectedPagoProveedoresById(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_PAGO_PROVEEDORES_PROJECTION_CXPFactura] WHERE id = :id")
    CXPFacturaPagoProveedoresProjection findProjectedPagoProveedoresById(@Param("id") Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_PAGO_PROVEEDORES_PROJECTION_CXPFactura] WHERE id IN :ids")
    List<CXPFacturaPagoProveedoresProjection> findProjectedPagoProveedoresAllByIdIn(@Param("ids") List<Integer> ids);

    List<CXPFacturaComboProjection> findProjectedComboAllByEstatusIdIn(List<Integer> estatusIds);

    @Query(nativeQuery = true, value = "SELECT id, proveedorId, codigoRegistro, montoRegistro, saldo, montoProgramado, fechaRegistro, ordenCompraTexto, folioSolicitudPagoServicio, evidenciaStr, evidenciaRh, facturaPDFStr, facturaXMLStr, cxpSolicitudesPagosServiciosStr, diasCredito, fechaModificacion, codigoMoneda, sucursal FROM [dbo].[VW_PROGRAMACION_PAGO_PROJECTION_CXPFactura] WHERE saldo > 0")
    List<CXPFacturaProgramacionPagoProjection> findProjectedProgramacionPagoAllBy();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_PROGRAMACION_PAGO_PROJECTION_CXPFactura] WHERE saldo > 0")
    List<CXPFacturaProgramacionPagoBeneficiarioProjection> findProjectedProgramacionPagoBeneficiarioAllBy();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_PROGRAMACION_PAGO_PROJECTION_CXPFactura] WHERE " +
            "saldo > 0 AND " +
            "(:allProveedores = 1 OR proveedorId IN (:proveedores)) AND " +
            "(:documento IS NULL OR codigoRegistro = :documento) "
            )
    List<CXPFacturaProgramacionPagoBeneficiarioProjection> findProjectedProgramacionPagoAllByFiltros(@Param("allProveedores") Integer allProveedores, @Param("proveedores") List<Integer> proveedores, @Param("documento") String documento);

    CXPFacturaVerSolicitudPagoProjection findProjectedVerSolicitudPagoById(Integer id);

    Boolean existsByUuidAndEstatusIdNotIn(String uuid, List<Integer> estatusIds);
    Boolean existsByUuidAndEstatusIdNotInAndIdNot(String uuid, List<Integer> estatusIds, Integer id);

    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "   '{' + STRING_AGG(CAST(id AS varchar(20)) + ': \"' + folio + '\"',', ') + '}' \n" +
            "FROM( \n" +
            "   SELECT \n" +
            "       id, \n" +
            "       (CASE WHEN COUNT(codigo) > 1 THEN 'Múltiple' ELSE MAX(codigo) END) AS folio \n" +
            "   FROM VW_FOLIO_PROJECTION_CXPFactura \n" +
            "   WHERE id IN :cxpFacturasIds \n" +
            "   GROUP BY id \n" +
            ") Folios" +
            "")
    String getFoliosMapJson(@Param("cxpFacturasIds") List<Integer> cxpFacturasIds);

    @Query("" +
            "SELECT SUM(cxpspd.montoProgramado) \n" +
            "FROM CXPFactura cxpf \n" +
            "INNER JOIN CXPSolicitudPagoDetalle cxpspd ON cxpspd.cxpFacturaId = cxpf.id \n" +
            "WHERE cxpspd.estatusId IN (2000161,2000165) AND cxpf.id = :facturaId \n" +
            "GROUP BY cxpf.id")
    BigDecimal getMontoProgramado(@Param("facturaId") Integer facturaId);

    @Query("" +
            "SELECT SUM(cxppd.montoAplicado) \n" +
            "FROM CXPFactura cxpf \n" +
            "INNER JOIN cxpf.cxpPagosDetalles cxppd \n" +
            "WHERE cxppd.cxpPago.estatusId <> 2000173 AND cxpf.id = :facturaId \n" +
            "GROUP BY cxpf.id")
    BigDecimal getMontoPagado(@Param("facturaId") Integer facturaId);


    /** Obtener las facturas que se encuentan en CXPFactura
     * @param idFactura
     * @return id´a de las OC
     */
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "    DISTINCT(OCR_OC_OrdenCompraId) AS id \n" +
            "FROM CXPFacturas \n" +
            " INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId \n " +
            " INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId \n " +
            " WHERE CXPF_CXPFacturaId = :idFactura " +
            "")
    List<Integer> getOrdenesCompa(@Param("idFactura") Integer idFactura);

    /** Obtener los ID de las facturas que se pueden cancelar,
     * @param idSolicitud ID de la factura
     * @return total de las CXPSolicitudesPagos que estan en proceso de pago, por lo que no se podra cancelar
     */
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "\tcount(*) AS total\n" +
            " FROM CXPSolicitudesPagosServicios AS SPS \n" +
            " INNER JOIN CXPSolicitudesPagosServiciosDetalles AS SPSD ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId \n" +
            " INNER JOIN CXPSolicitudesPagosDetalles AS SPD ON  SPSD.CXPSPSD_CXPF_CXPFacturaId = SPD.CXPSD_CXPF_CXPFacturaId \n" +
            " INNER JOIN CXPSolicitudesPagos AS SP ON SPD.CXPSD_CXPS_CXPSolicitudPagoId = SP.CXPS_CXPSolicitudPagoId \n" +
            " WHERE \n" +
            " CXPSPS_CXPSolicitudPagoServicioId = :idSolicitud \n" +
            " AND CXPSD_CMM_EstatusId NOT IN(2000163, 2000164, 2000166) \n" +
            "")
    Integer getSolicitudCancelable(@Param("idSolicitud") Integer idSolicitud);

    /** Obtener los beneficiarios de una lista de facturas,
     * @param idFactura ID de la factura
     * @return total de las CXPSolicitudesPagos que estan en proceso de pago, por lo que no se podra cancelar
     */
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "   CXPF_CXPFacturaId as Id, \n" +
            "   COALESCE(CPXSPRHPA_NombreBeneficiario, CONCAT(EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido)) AS beneficiario \n" +
            "FROM CXPFacturas \n" +
            "INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId\n" +
            "INNER JOIN Empleados ON CPXSPRH_EMP_EmpleadoId = EMP_EmpleadoId\n" +
            "LEFT JOIN CXPSolicitudesPagosRHPensionesAlimenticias ON CPXSPRH_CXPSolicitudPagoRhId = CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId\n" +
            "WHERE CXPFacturas.CXPF_CXPFacturaId IN :ids " +
            "")
    List<CXPFacturaBeneficiarioProjection> getBeneficiarios(@Param("ids") List<Integer> id);

    @Query(nativeQuery = true, value =
            "SELECT xml.ARC_ArchivoId AS XMLId,\n" +
            "       pdf.ARC_ArchivoId AS PDFId,\n" +
            "       CONVERT(VARCHAR(4), YEAR(CXPF_FechaRegistro)) + '/' + RIGHT('0' + CONVERT(VARCHAR(2), MONTH(CXPF_FechaRegistro)), 2) + '/' + PRO_RFC + '/' + CONVERT(VARCHAR(100), ISNULL(CXPF_UUID, ISNULL(SUBSTRING(xml.ARC_NombreFisico, 0, CHARINDEX('.', xml.ARC_NombreFisico)), SUBSTRING(pdf.ARC_NombreFisico, 0, CHARINDEX('.', pdf.ARC_NombreFisico))))) AS PathArchivo\n" +
            "FROM CXPFacturas\n" +
            "     INNER JOIN Proveedores ON CXPF_PRO_ProveedorId = PRO_ProveedorId\n" +
            "     LEFT JOIN Archivos AS pdf ON CXPF_ARC_FacturaPDFId = pdf.ARC_ArchivoId AND pdf.ARC_Activo = 1\n" +
            "     LEFT JOIN Archivos AS xml ON CXPF_ARC_FacturaXMLId = xml.ARC_ArchivoId AND xml.ARC_Activo = 1\n" +
            "WHERE CXPF_CMM_EstatusId NOT IN(2000111, 2000114) -- Borrado ni Cancelada\n" +
            "     AND (pdf.ARC_ArchivoId IS NOT NULL\n" +
            "          OR xml.ARC_ArchivoId IS NOT NULL)\n" +
            "ORDER BY pathArchivo")
    List<CXPFacturaDescargarProjection> getFacturasDescargar();

    @Query(nativeQuery = true, value =
            "SELECT *\n" +
            "FROM VW_ReporteFacturasCXP\n" +
            "WHERE fecha BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31')\n" +
            "       AND (COALESCE(:proveedores, '') = '' OR :proveedores LIKE CONCAT('%|', proveedorId, '|%')) \n" +
            "ORDER BY proveedor,\n" +
            "         fecha\n" +
            "OPTION(RECOMPILE)")
    List<CXPFacturaReporteProjection> findCXPFacturaReporteProjected(@Param("proveedores") String proveedores,
                                                                     @Param("fechaInicio") String fechaInicio,
                                                                     @Param("fechaFin") String fechaFin);

    @Query(nativeQuery = true, value = "SELECT * FROM VW_ReporteFacturasCXP WHERE id = :id OPTION(RECOMPILE)")
    CXPFacturaReporteProjection findCXPFacturaReporteProjectedById(@Param("id") Integer id);
}