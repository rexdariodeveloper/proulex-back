package com.pixvs.main.dao;

import com.pixvs.main.models.CXPPago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPPagoDao extends CrudRepository<CXPPago, String> {

    @Query(value = "SELECT fechaRegistro, sede, proveedor, numeroDocumento, monto, moneda, fechaVencimiento, fechaPago, " +
            "formaPago, cuenta, codigo, ordenCompraId, solicitud, solicitudId, identificacionPago, comprobanteId, pdfId, xmlId  " +
            "FROM VW_REPORTE_PAGO_PROVEEDORES WHERE " +
            "CAST(fechaPago AS DATE) BETWEEN CAST(:fechaInicio AS DATE) AND CAST(:fechaFin AS DATE) AND "+
            "COALESCE(:sedes, CONCAT(\'|\',CAST(sedeId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(sedeId AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:proveedores, CONCAT('|',CAST(proveedorId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(proveedorId AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:documento, numeroDocumento) = numeroDocumento AND "+
            "COALESCE(:moneda, monedaId) = monedaId AND "+
            "COALESCE(:formaPago, CONCAT('|',CAST(formaPagoId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(formaPagoId AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:cuenta, cuentaId) = cuentaId "+
            "ORDER BY fechaPago", nativeQuery = true)
    List<Map<String, Object> > findAllQueryReporteBy(
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin,
            @Param("sedes") String sedes,
            @Param("proveedores") String proveedores,
            @Param("documento") String documento,
            @Param("moneda") Integer moneda,
            @Param("formaPago") String formaPago,
            @Param("cuenta") Integer cuenta
    );

    @Query(value = "SELECT * FROM VW_REPORTE_CXP WHERE " +
            "CAST(fechaRegistro AS DATE) <= CAST(:fechaFin AS DATE) AND "+
            "COALESCE(:sedes, CONCAT(\'|\',CAST(idSede AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(idSede AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:proveedores, CONCAT('|',CAST(idProveedor AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(idProveedor AS NVARCHAR(10)),'|%') AND "+
            "COALESCE(:documento, numeroDocumento) = numeroDocumento AND "+
            "COALESCE(:moneda, idMoneda) = idMoneda "+
            "ORDER BY fechaRegistro", nativeQuery = true)
    List<Map<String, Object> > getReporteCXP(
            @Param("fechaFin") String fechaFin,
            @Param("sedes") String sedes,
            @Param("proveedores") String proveedores,
            @Param("documento") String documento,
            @Param("moneda") Integer moneda
    );
}
