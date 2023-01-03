package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.projections.OrdenCompra.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
public interface OrdenCompraDao extends CrudRepository<OrdenCompra, String> {

    @Query(nativeQuery = true, value = "select * from [dbo].[VW_LISTADO_PROJECTION_OC]")
    List<OrdenCompraListadoProjection> findProjectedListadoAllBy();
    @Query(nativeQuery = true, value = "select * from [dbo].[VW_LISTADO_PROJECTION_OC] WHERE estatusId IN :estatusIds")
    List<OrdenCompraListadoProjection> findProjectedListadoAllByEstatusIdIn(@Param("estatusIds") List<Integer> estatusIds);
    @Query(nativeQuery = true, value = "select * from [dbo].[VW_LISTADO_PROJECTION_OC] WHERE estatusId IN :estatusIds AND recepcionArticulosAlmacenId IN :recepcionArticulosAlmacenId")
    List<OrdenCompraListadoProjection> findProjectedListadoAllByEstatusIdInAndRecepcionArticulosAlmacenIdIn(@Param("estatusIds") List<Integer> estatusIds, @Param("recepcionArticulosAlmacenId") List<Integer> recepcionArticulosAlmacenId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_LISTADO_PROJECTION_OC] \n" +
            "WHERE \n" +
            "   (:codigo IS NOT NULL AND codigo LIKE '%' + CAST(:codigo as varchar) + '%') \n" +
            "   OR (:proveedorNombre IS NOT NULL AND proveedorNombre LIKE '%' + CAST(:proveedorNombre as varchar) + '%') \n" +
            "   OR (:proveedorRfc IS NOT NULL AND proveedorRfc LIKE '%' + CAST(:proveedorRfc as varchar) + '%') \n" +
            "   OR (:fechaOC IS NOT NULL AND FORMAT(fechaOC,'dd/MM/yyyy') LIKE '%' + CAST(:fechaOC as varchar) + '%') \n" +
            "   OR (:fechaModificacion IS NOT NULL AND FORMAT(fechaModificacion,'dd/MM/yyyy') LIKE '%' + CAST(:fechaModificacion as varchar) + '%') \n" +
            "   OR (:estatusValor IS NOT NULL AND estatusValor LIKE '%' + CAST(:estatusValor as varchar) + '%') \n" +
            "   OR (:codigo IS NULL AND :proveedorNombre IS NULL AND :proveedorRfc IS NULL AND :fechaOC IS NULL AND :fechaModificacion IS NULL AND :estatusValor IS NULL) \n" +
            "")
    List<OrdenCompraListadoProjection> findProjectedListadoAllByFiltrado(@Param("codigo") String codigo, @Param("proveedorNombre") String proveedorNombre, @Param("proveedorRfc") String proveedorRfc, @Param("fechaOC") String fechaOC, @Param("fechaModificacion") String fechaModificacion, @Param("estatusValor") String estatusValor);

    List<OrdenCompraComboProjection> findProjectedComboAllBy();

    OrdenCompraEditarProjection findProjectedEditarById(Integer id);

    OrdenCompraRecibirProjection findProjectedRecibirById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_DEVOLVER_PROJECTION_OrdenCompra] WHERE id = :id")
    OrdenCompraDevolverProjection findProjectedDevolverById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getOrdenesCompraPendientesRelacionar](:proveedorId) WHERE montoPendienteRelacionar > 0")
    List<OrdenCompraRelacionarProjection> fnGetOrdenesCompraPendientesRelacionar(@Param("proveedorId") Integer proveedorId);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getOrdenesCompraPendientesRelacionarPorCXPFactura](:cxpFacturaId)")
    List<OrdenCompraRelacionarProjection> fnGetOrdenesCompraPendientesRelacionarPorCXPFactura(@Param("cxpFacturaId") Integer cxpFacturaId);

    OrdenCompra findById(Integer id);

    @Query(" \n" +
            "    SELECT SUM(cantidad) \n" +
            "    FROM OrdenCompraDetalle \n" +
            "    WHERE ordenCompraId = :id \n" +
            "")
    BigDecimal getCantidad(@Param("id") Integer id);

    @Query(" \n" +
            "    SELECT SUM(cantidadRecibo) \n" +
            "    FROM OrdenCompraRecibo \n" +
            "    WHERE ordenCompraId = :id \n" +
            "")
    BigDecimal getCantidadRecibida(@Param("id") Integer id);

    @Query(" \n" +
            "    SELECT id \n" +
            "    FROM OrdenCompra \n" +
            "    WHERE proveedorId IN :proveedorIds \n" +
            "")
    List<Integer> getIdsByProveedorIdIn(@Param("proveedorIds") List<Integer> proveedorIds);

    @Query(nativeQuery = true, value = "Select * from [dbo].[VW_REPORTE_ORDENES_COMPRA] where " +
            "(:proveedores = 1 OR PRO_ProveedorId IN(:proveedoresIds) ) AND " +
            "( :codigo IS NULL OR OC_Codigo = :codigo) AND " +
            "( :articulos = 1 OR ART_ArticuloId IN(:articulosIds)) AND "+
            "( :almacenes = 1 OR ALM_AlmacenId IN(:almacenesIds)) AND " +
            "( :fechaDesde IS NULL OR OC_FechaOC >= CAST (:fechaDesde AS DATE) ) AND " +
            "( :fechaHasta IS NULL OR OC_FechaOC <= CAST (:fechaHasta AS DATE) ) AND " +
            "( :estatus = 1 OR CMM_ControlId IN(:estatusIds)) AND " +
            "( :monedas = 1 OR OC_MON_MonedaId IN(:monedasIds))")
    List<Map<String, Object>> getReporte(@Param("proveedores") Integer proveedores, @Param("proveedoresIds") List<Integer> proveedoresIds,
                                         @Param("codigo") String codigo,
                                         @Param("articulos") Integer articulos, @Param("articulosIds") List<Integer> articulosIds,
                                         @Param("almacenes") Integer almacenes, @Param("almacenesIds") List<Integer> almacenesIds,
                                         @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta,
                                         @Param("estatus") Integer estatus, @Param("estatusIds") List<Integer> estatusIds,
                                         @Param("monedas") Integer monedas, @Param("monedasIds") List<Integer> monedasIds);

    @Query(nativeQuery = true, value = "Select * from [dbo].[VW_REPORTE_HISTORIAL_COMPRA] where " +
            "(:proveedores = 1 OR PRO_ProveedorId IN(:proveedoresIds) ) AND " +
            "( :codigo IS NULL OR OC_Codigo = :codigo) AND " +
            "( :articulos = 1 OR ART_ArticuloId IN(:articulosIds)) AND "+
            "( :almacenes = 1 OR ALM_AlmacenId IN(:almacenesIds)) AND " +
            "( :fechaDesde IS NULL OR OC_FechaOC >= CAST (:fechaDesde AS DATE) ) AND " +
            "( :fechaHasta IS NULL OR OC_FechaOC <= CAST (:fechaHasta AS DATE) ) AND " +
            "( :estatus = 1 OR estatusId IN(:estatusIds)) AND " +
            "( :monedas = 1 OR OC_MON_MonedaId IN(:monedasIds))")
    List<Map<String, Object>> getHistorial(@Param("proveedores") Integer proveedores, @Param("proveedoresIds") List<Integer> proveedoresIds,
                                         @Param("codigo") String codigo,
                                         @Param("articulos") Integer articulos, @Param("articulosIds") List<Integer> articulosIds,
                                         @Param("almacenes") Integer almacenes, @Param("almacenesIds") List<Integer> almacenesIds,
                                         @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta,
                                         @Param("estatus") Integer estatus, @Param("estatusIds") List<Integer> estatusIds,
                                         @Param("monedas") Integer monedas, @Param("monedasIds") List<Integer> monedasIds);

    @Query(value =
            "SELECT facturaId,\n" +
            "       factura,\n" +
            "       fecha,\n" +
            "       termino,\n" +
            "       vencimiento,\n" +
            "       monto,\n" +
            "       pagado,\n" +
            "       restante,\n" +
            "       proveedorId,\n" +
            "       proveedorNombre,\n" +
            "       CASE WHEN diasVencido > 0 AND restante > 0 THEN diasVencido ELSE 0 END AS dias\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT facturaId,\n" +
            "           factura,\n" +
            "           fecha,\n" +
            "           termino,\n" +
            "           vencimiento,\n" +
            "           monto,\n" +
            "           pagado,\n" +
            "           restante,\n" +
            "           proveedorId,\n" +
            "           proveedorNombre,\n" +
            "           DATEDIFF(DAY, vencimiento, CAST(COALESCE(:fecha, GETDATE()) AS DATE)) diasVencido,\n" +
            "           diasCredito\n" +
            "    FROM VW_ESTADO_CUENTA_PROVEEDOR\n" +
            ") AS todo\n" +
            "WHERE(COALESCE(:proveedoresIds, 0) = 0 OR proveedorId IN(:proveedoresIds))\n" +
            "       AND (COALESCE(:facturasIds, 0) = 0 OR facturaId IN(:facturasIds))\n" +
            "       AND 1 =\n" +
            "           CASE WHEN :estatus = 0 THEN 1 ELSE\n" +
            "           CASE WHEN :estatus = 1 AND (restante <= 0 OR diasVencido <= diasCredito) THEN 1 ELSE\n" +
            "           CASE WHEN :estatus = 2 AND restante > 0 AND diasVencido > diasCredito THEN 1 ELSE\n" +
            "           0 END END END\n" +
            "ORDER BY fecha,\n" +
            "         vencimiento,\n" +
            "         factura\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<Map<String, Object>> getEstadoCuenta(@Param("proveedoresIds") List<Integer> proveedoresIds,
                                              @Param("facturasIds") List<Integer> facturasIds,
                                              @Param("fecha") Date fecha,
                                              @Param("estatus") Integer estatus);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_REPORTE_OC_RECIBOS_PENDIENTES] WHERE " +
            "CAST(fechaOC AS DATE) BETWEEN CAST(:fechaInicio AS DATE) AND CAST(:fechaFin AS DATE) " +
            "AND (:codigo IS NULL OR codigoOC = :codigo) " +
            "AND (:articulo IS NULL OR articuloId = :articulo) " +
            "AND (:proveedor IS NULL OR proveedorId = :proveedor) " +
            "AND (:almacen IS NULL OR almacenId = :almacen)")
    List<Map<String, Object>> getRecibosPendientes(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
                                                   @Param("codigo") String codigo, @Param("articulo") Integer articulo,
                                                   @Param("proveedor") Integer proveedor, @Param("almacen") Integer almacen);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_REPORTE_OC_RECIBOS] WHERE " +
            "CAST(fechaOC AS DATE) BETWEEN CAST(:fechaInicio AS DATE) AND CAST(:fechaFin AS DATE) " +
            "AND (:codigo IS NULL OR codigoOC = :codigo) " +
            "AND (:articulo IS NULL OR articuloId = :articulo) " +
            "AND (:proveedor IS NULL OR proveedorId = :proveedor) " +
            "AND (:almacen IS NULL OR almacenId = :almacen)")
    List<Map<String, Object>> getRecibos(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
                                         @Param("codigo") String codigo, @Param("articulo") Integer articulo,
                                         @Param("proveedor") Integer proveedor, @Param("almacen") Integer almacen);

    /**
     * Cuenta el numero de registros de la OC que no estan completados
     * @param recibida Integer, verifica si compara con la cantidad recibida, en caso contrario, pagado
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM( \n" +
            "SELECT \n" +
            "   OCD_ART_ArticuloId, \n" +
            "   CXPFD_CXPFacturadetalleId, \n" +
            "   OCR_OCD_OrdenCompraDetalleId, \n" +
            "   COALESCE(OCD_Cantidad, 0) AS cantidad, \n" +
            "   COALESCE(SUM(OCR_CantidadRecibo), 0) AS cantidadRecibida, \n" +
            "   COALESCE(SUM(CXPFD_Cantidad), 0) AS cantidadFactura \n" +
            " FROM OrdenesCompraDetalles \n" +
            " LEFT JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId \n" +
            " LEFT JOIN CXPFacturasDetalles ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId \n" +
            " WHERE OCR_OC_OrdenCompraId = :IdOC \n" +
            " GROUP BY OCR_OCD_OrdenCompraDetalleId, CXPFD_CXPFacturadetalleId, OCD_ART_ArticuloId, OCD_Cantidad \n" +
            ") AS resultados \n" +
            "WHERE cantidad > (CASE WHEN :recibida = 0 THEN cantidadRecibida ELSE cantidadFactura END)"
    )
    Integer getNoCompletados(@Param("IdOC") Integer IdOC, @Param("recibida") Integer recibida);


    /**
     * Cuenta el numero de registros de la OC que no estan completados
     * @param IdOC Id de la OC
     * @return
     */
    @Query(nativeQuery = true, value = "" +
            "SELECT " +
            "   COUNT(*) \n" +
            " FROM CXPFacturas \n" +
            "   INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId \n" +
            "   INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId \n" +
            "   INNER JOIN OrdenesCompraDetalles ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId \n" +
            "WHERE \n" +
            "   CXPF_CMM_EstatusId NOT IN( 2000114, 2000118) AND \n"+
            "   OCD_OC_OrdenCompraId = :IdOC\n" +
            ";"
    )
    Integer getNoPagadas(@Param("IdOC") Integer IdOC);

}