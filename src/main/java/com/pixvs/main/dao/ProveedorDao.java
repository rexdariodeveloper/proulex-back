package com.pixvs.main.dao;

import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Proveedor.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProveedorDao extends CrudRepository<Proveedor, String> {

    List<ProveedorListadoProjection> findProjectedListadoAllByTipoProveedorIdIsNot(Integer tipoProveedorId);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor] WHERE activo = 1 ORDER BY TRIM(nombre)")
    List<ProveedorComboVistaProjection> findProjectedComboVistaAllByActivoTrue();

    ProveedorEditarProjection findProjectedEditarById(Integer id);

    //List<ProveedorEditarProjection> findProjectedEditarByRfc(String rfc);

    Proveedor findById(Integer id);

    ProveedorEditarProjection findByNombre(String nombre);


    @Modifying
    @Query(value = "UPDATE Proveedores SET PRO_Activo = :activo WHERE PRO_ProveedorId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    ProveedorComboProjection findProjectedComboById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboVistaProjection> findProjectedComboVistaAllBy();
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor] WHERE id = :id")
    List<ProveedorComboVistaProjection> findProjectedComboVistaById(@Param("id") Integer id);

    List<ProveedorRelacionarProjection> findProjectedRelacionarAllBy();

    @Query("" +
            "SELECT DISTINCT \n" +
            "   p.id AS id, \n" +
            "   p.nombre AS nombre, \n" +
            "   p.codigo AS codigo, \n" +
            "   p.rfc AS rfc \n" +
            "FROM Proveedor p \n" +
            "INNER JOIN CXPFactura cxpf ON cxpf.proveedorId = p.id AND cxpf.estatusId IN (" + ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO + "," + ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_PARCIAL + ") \n" +
            "")
    List<ProveedorProgramacionPagoProjection> findProjectedProgramacionPagoAllBy();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_reporteAntiguedadSaldosDetalle](:proveedoresIds,:facturasIds,:monedaId,:diasAgrupados,:mostrarVencidos,:mostrarPorVencer)")
    List<ProveedorReporteAntiguedadSaldosDetalleProjection> getReporteAntiguedadSaldosDetalle(@Param("proveedoresIds") String proveedoresIds, @Param("facturasIds") String facturasIds, @Param("monedaId") Integer monedaId, @Param("diasAgrupados") Integer diasAgrupados, @Param("mostrarVencidos") Boolean mostrarVencidos, @Param("mostrarPorVencer") Boolean mostrarPorVencer);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_reporteAntiguedadSaldosResumen](:proveedoresIds,:facturasIds,:monedaId,:diasAgrupados,:mostrarVencidos,:mostrarPorVencer)")
    List<ProveedorReporteAntiguedadSaldosResumenProjection> getReporteAntiguedadSaldosResumen(@Param("proveedoresIds") String proveedoresIds, @Param("facturasIds") String facturasIds, @Param("monedaId") Integer monedaId, @Param("diasAgrupados") Integer diasAgrupados, @Param("mostrarVencidos") Boolean mostrarVencidos, @Param("mostrarPorVencer") Boolean mostrarPorVencer);

    @Query(nativeQuery = true, value = "Select * from Proveedores where PRO_RFC = :rfc AND PRO_ProveedorId!=:id AND PRO_Activo=1")
    List<Proveedor> getProovedoresByRfc(@Param("rfc") String rfc, @Param("id") Integer id);

}