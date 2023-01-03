package com.pixvs.main.dao;

import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFactura.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
public interface CXCFacturaDao extends CrudRepository<CXCFactura, String> {

    // Modelo
    CXCFactura findById(Integer id);

    // ListadoProjection
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCFacturas]")
    List<CXCFacturaListadoProjection> findProjectedListadoAllBy();

    // EditarProjection
    CXCFacturaEditarProjection findProjectedEditarById(Integer id);

    NotaVentaCXCFacturaEditarProjection findNotaVentaCXCFacturaEditarProjectionById(Integer id);

    CXCFacturaDescargarProjection findCXCFacturaDescargarProjectionById(Integer id);

    CXCFacturaMiscelaneaEditarProjection findCXCFacturaMiscelaneaEditarProjectionById(Integer id);

    List<CXCFacturaPagoProjection> findAllCXCFacturaPagoProjectionByIdIn(List<Integer> ids);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCFacturasNotasVenta] ORDER BY Fecha DESC")
    List<ListadoFacturasNotaVentaCXCFacturaProjection> findListadoFacturasNotaVentaProjected();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCFacturasGlobales] ORDER BY Fecha DESC")
    List<ListadoFacturasGlobalesCXCFacturaProjection> findListadoFacturasGlobalesProjected();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCFacturasMiscelaneas] ORDER BY Fecha DESC")
    List<ListadoFacturasMiscelaneaCXCFacturaProjection> findListadoFacturasMiscelaneaProjected();

    CFDIFacturaProjection findCFDIFacturaProjectedById(Integer id);

    CFDIFacturaProjection findCFDIFacturaProjectedBySucursalIdAndFolio(int sucursalId, String folio);

    CFDIFacturaRelacionadaProjection findCFDIFacturaRelacionadaProjectedById(Integer id);

    CFDIFacturaPagoProjection findCFDIFacturaPagoProjectedById(Integer id);

    @Query(value =
            "SELECT * \n" +
            "FROM VW_RPT_ReporteFacturas \n" +
            "WHERE (COALESCE(:listaSedeId, 0) = 0 OR SucursalId IN (:listaSedeId)) \n" +
            "       AND CONVERT(DATE, FechaFactura) BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31') \n" +
            "       AND (COALESCE(:listaReceptor, '') = '' OR :listaReceptor LIKE '%|' + Receptor + '|%') \n" +
            "       AND (COALESCE(:notaVenta, '') = '' OR TodasOV LIKE '%' + :notaVenta + '%') \n " +
            "       AND (COALESCE(:listaEstatusId, 0) = 0 OR EstatusId IN (:listaEstatusId)) \n" +
            "ORDER BY Sede, FolioFactura \n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteFacturasProjection> vw_rpt_reporteFacturas(@Param("listaSedeId") List<Integer> listaSedeId,
                                                           @Param("fechaInicio") String fechaInicio,
                                                           @Param("fechaFin") String fechaFin,
                                                           @Param("listaReceptor") String listaReceptor,
                                                           @Param("notaVenta") String notaVenta,
                                                           @Param("listaEstatusId") List<Integer> listaEstatusId);

    @Query(nativeQuery = true, value = "SELECT * FROM VW_Listado_CXCFacturas_Autofactura WHERE Receptor = :rfc")
    List<ListadoAutofacturacionFacturasProjection> findListadoAutofacturasByRFC(@Param("rfc") String rfc);
}
