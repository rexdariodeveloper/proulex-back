package com.pixvs.main.dao;

import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.CXPPago;
import com.pixvs.main.models.projections.Reporte.ReporteConfirmingProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportesDao extends CrudRepository<CXPPago, String> {

    @Query(value = "SELECT nombreSucursal, nombreProveedor, codigo, fechaRegistro, folio, fechaFactura, diasCredito, " +
            "ultimaFechaPago, subtotal, descuento, IVA iva, IEPS ieps, retencion, total FROM [dbo].[VW_RPT_Confirming] WHERE " +
            "CAST(fechaRegistro AS DATE) BETWEEN CAST(:fechaInicio AS DATE) AND CAST(:fechaFin AS DATE) AND "+
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:proveedores, CONCAT('|',CAST(proveedorId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(proveedorId AS NVARCHAR(10)),'|%') "+
            "ORDER BY codigo", nativeQuery = true)
    List<ReporteConfirmingProjection> reporteConfirming(@Param("fechaInicio") String fechaInicio,
                                                        @Param("fechaFin") String fechaFin,
                                                        @Param("sedes") String sedes,
                                                        @Param("proveedores") String proveedores);
}
