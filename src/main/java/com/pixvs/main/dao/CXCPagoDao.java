package com.pixvs.main.dao;

import com.pixvs.main.models.CXCPago;
import com.pixvs.main.models.projections.CXCPago.ListadoFacturasCXCPagoProjection;
import com.pixvs.main.models.projections.CXCPago.ListadoSaldosCXCPagoProjection;
import com.pixvs.main.models.projections.CXCPago.ReportePagosFacturasCXCPagoProjection;
import com.pixvs.main.models.projections.CXCPago.ReportePagosCXCPagoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CXCPagoDao extends CrudRepository<CXCPago, String> {

    CXCPago findById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCPagosSaldos] ORDER BY AlumnoClienteId, Codigo")
    List<ListadoSaldosCXCPagoProjection> findListadoSaldosProjected();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCPagosSaldos] WHERE AlumnoClienteId = :alumnoClienteId")
    ListadoSaldosCXCPagoProjection findSaldoProjectedByAlumnoClienteId(@Param("alumnoClienteId") int alumnoClienteId);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCPagosFacturas] ORDER BY Fecha, Folio")
    List<ListadoFacturasCXCPagoProjection> findListadoFacturasProjected();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_Listado_CXCPagosFacturas] WHERE AlumnoClienteId = :alumnoClienteId ORDER BY Fecha, Folio")
    List<ListadoFacturasCXCPagoProjection> findListadoFacturasProjectedByAlumnoClienteId(@Param("alumnoClienteId") int alumnoClienteId);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT\n" +
            "       pagos.*\n" +
            "FROM VW_ReporteCXCPagos AS pagos\n" +
            "     INNER JOIN VW_ReporteCXCPagosDetalles AS facturas ON pagos.id = facturas.pagoId\n" +
            "WHERE CONVERT(DATE, fechaRegistro) BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31')\n" +
            "     AND (COALESCE(:sedeId, 0) = 0 OR facturas.sedeId IN(:sedeId))\n" +
            "     AND (COALESCE(:listaClientes, '') = '' OR :listaClientes LIKE CONCAT('%|', alumnoClienteId, '|%'))\n" +
            "     AND (COALESCE(:numeroDocumento, 0) = 0 OR TRIM(UPPER(pagos.numeroOperacion)) LIKE TRIM(UPPER(CONCAT('%', :numeroDocumento, '%'))))\n" +
            "     AND (COALESCE(:monedaId, 0) = 0 OR pagos.monedaId = :monedaId)\n" +
            "     AND (COALESCE(:formaPagoId, 0) = 0 OR pagos.formaPagoId IN(:formaPagoId))\n" +
            "     AND (COALESCE(:cuentaId, 0) = 0 OR pagos.cuentaId = :cuentaId)\n" +
            "ORDER BY fechaRegistro,\n" +
            "         cliente\n" +
            "OPTION (RECOMPILE)")
    List<ReportePagosCXCPagoProjection> findReportePagos(@Param("fechaInicio") String fechaInicio,
                                                         @Param("fechaFin") String fechaFin,
                                                         @Param("sedeId") List<Integer> sedeId,
                                                         @Param("listaClientes") String listaClientes,
                                                         @Param("numeroDocumento") String numeroDocumento,
                                                         @Param("monedaId") Integer monedaId,
                                                         @Param("formaPagoId") List<Integer> formaPagoId,
                                                         @Param("cuentaId") Integer cuentaId);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT\n" +
            "       facturas.*\n" +
            "FROM VW_ReporteCXCPagos AS pagos\n" +
            "     INNER JOIN VW_ReporteCXCPagosDetalles AS facturas ON pagos.id = facturas.pagoId\n" +
            "WHERE CONVERT(DATE, fechaRegistro) BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31')\n" +
            "     AND (COALESCE(:sedeId, 0) = 0 OR facturas.sedeId IN(:sedeId))\n" +
            "     AND (COALESCE(:listaClientes, '') = '' OR :listaClientes LIKE CONCAT('%|', alumnoClienteId, '|%'))\n" +
            "     AND (COALESCE(:numeroDocumento, 0) = 0 OR TRIM(UPPER(pagos.numeroOperacion)) LIKE TRIM(UPPER(CONCAT('%', :numeroDocumento, '%'))))\n" +
            "     AND (COALESCE(:monedaId, 0) = 0 OR pagos.monedaId = :monedaId)\n" +
            "     AND (COALESCE(:formaPagoId, 0) = 0 OR pagos.formaPagoId IN(:formaPagoId))\n" +
            "     AND (COALESCE(:cuentaId, 0) = 0 OR pagos.cuentaId = :cuentaId)\n" +
            "ORDER BY facturas.sede,\n" +
            "         fechaFactura\n" +
            "OPTION (RECOMPILE)")
    List<ReportePagosFacturasCXCPagoProjection> findReportePagosFacturas(@Param("fechaInicio") String fechaInicio,
                                                                         @Param("fechaFin") String fechaFin,
                                                                         @Param("sedeId") List<Integer> sedeId,
                                                                         @Param("listaClientes") String listaClientes,
                                                                         @Param("numeroDocumento") String numeroDocumento,
                                                                         @Param("monedaId") Integer monedaId,
                                                                         @Param("formaPagoId") List<Integer> formaPagoId,
                                                                         @Param("cuentaId") Integer cuentaId);

    @Query(nativeQuery = true, value = "SELECT * FROM VW_ReporteCXCPagos WHERE id = :id")
    ReportePagosCXCPagoProjection findReportePagoById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM VW_ReporteCXCPagosDetalles WHERE id = :id")
    ReportePagosFacturasCXCPagoProjection findReportePagosFacturaById(@Param("id") Integer id);
}