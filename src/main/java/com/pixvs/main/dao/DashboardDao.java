package com.pixvs.main.dao;

import com.pixvs.main.models.CXPPago;
import com.pixvs.main.models.projections.Dashboard.*;
import com.pixvs.main.models.projections.Reporte.ReporteConfirmingProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardDao extends CrudRepository<CXPPago, String> {

    @Query(value = "SELECT nombreSucursal, nombreProveedor, codigo, fechaRegistro, folio, fechaFactura, diasCredito, " +
            "ultimaFechaPago, subtotal, descuento, IVA iva, IEPS ieps, retencion, total FROM [dbo].[VW_RPT_Confirming] WHERE " +
            "CAST(fechaRegistro AS DATE) BETWEEN CAST(:fechaInicio AS DATE) AND CAST(:fechaFin AS DATE) AND "+
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:proveedores, CONCAT('|',CAST(proveedorId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(proveedorId AS NVARCHAR(10)),'|%') AND "+
            ":usuario = creadoPorId "+
            "ORDER BY codigo", nativeQuery = true)
    List<ReporteConfirmingProjection> reporteConfirming(@Param("fechaInicio") String fechaInicio,
                                                        @Param("fechaFin") String fechaFin,
                                                        @Param("sedes") String sedes,
                                                        @Param("proveedores") String proveedores,
                                                        @Param("usuario") Integer usuario);

    @Query(value = "select año anio, mes, SUM(ingresos) value from VW_CONDENSADO_SIAP_INGRESOS group by año, mes", nativeQuery = true)
    List<DashboardBannerProjection> getBannerData();

    @Query(value = "select horario, idioma, SUM(ingresos) value from VW_CONDENSADO_SIAP_INGRESOS where " +
            "año = :anio and " +
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') and " +
            "COALESCE(:mes, mes) = mes "+
            "group by horario, idioma", nativeQuery = true)
    List<DashboardHorarioIdiomaProjection> getHorarioIdiomaData(@Param("anio") String anio, @Param("mes") String mes, @Param("sedes") String sedes);

    @Query(value = "select programa name, SUM(ingresos) value from VW_CONDENSADO_SIAP_INGRESOS where " +
            "año = :anio and " +
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') and " +
            "COALESCE(:mes, mes) = mes "+
            "group by programa", nativeQuery = true)
    List<DashboardProgramaProjection> getPrograma(@Param("anio") String anio, @Param("mes") String mes, @Param("sedes") String sedes);

    @Query(value = "select COUNT(*) cantidad, descuentoDescripcion descripcion, SUM(descuentoImporte) monto from VW_CONDENSADO_SIAP_INGRESOS where " +
            "año = :anio and " +
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') and " +
            "COALESCE(:mes, mes) = mes "+
            "group by descuentoDescripcion", nativeQuery = true)
    List<DashboardDescuentoProjection> getDescuentos(@Param("anio") String anio, @Param("mes") String mes, @Param("sedes") String sedes);

    @Query(value = "select tipoSucursal, nombreSucursal, SUM(ingresos) value from VW_CONDENSADO_SIAP_INGRESOS where " +
            "año = :anio and " +
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sucursalId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sucursalId AS NVARCHAR(10)),'|%') and " +
            "COALESCE(:mes, mes) = mes "+
            "group by tipoSucursal, nombreSucursal", nativeQuery = true)
    List<DashboardSedeProjection> getSede(@Param("anio") String anio, @Param("mes") String mes, @Param("sedes") String sedes);
}
