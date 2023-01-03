package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoListadoProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHComboProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPSolicitudPagoRHDao extends CrudRepository<CXPSolicitudPagoRH, String> {

    //CXPSolicitudPagoRH findById(Integer id);

    //List<CXPSolicitudPagoRHListadoProjection> findProjectedListadoAllByEstatusIdIn(List<Integer> estatusIds);

    //CXPSolicitudPagoRHListadoProjection findProjectedById(Integer id);

    //CXPSolicitudPagoRHEditarProjection findByFacturaId(Integer facturaId);

    @Modifying
    @Query(value = "UPDATE CXPSolicitudesPagosRH SET CPXSPRH_CMM_EstatusId = :estatus WHERE CPXSPRH_CXPSolicitudPagoRhId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("estatus") Integer estatus);

    CXPSolicitudPagoRHEditarProjection findEditarProjectionById(Integer id);

    @Query(nativeQuery = true, value = "SELECT id, codigo, fechaCreacion, nombre, tipoPago, monto, estatus, usuarioId, usuarioCreador, sedeId FROM [dbo].[VW_LISTADO_SOLICITUDES_PAGO_RH] ORDER BY codigo DESC")
    List<CXPSolicitudPagoRHListadoProjection> getListadoSolicitudes();
    @Query(nativeQuery = true, value = "SELECT id, codigo, fechaCreacion, nombre, tipoPago, monto, estatus, usuarioId, usuarioCreador, sedeId FROM [dbo].[VW_LISTADO_SOLICITUDES_PAGO_RH] WHERE sedeId IN :sedeIds ORDER BY codigo DESC")
    List<CXPSolicitudPagoRHListadoProjection> getListadoSolicitudes(@Param("sedeIds") List<Integer> sedeIds);
    @Query(nativeQuery = true, value = "SELECT id, codigo, fechaCreacion, nombre, tipoPago, monto, estatus, usuarioId, usuarioCreador, sedeId FROM [dbo].[VW_LISTADO_SOLICITUDES_PAGO_RH] WHERE sedeId IN :sedeIds AND usuarioId IN :usuariosIds ORDER BY codigo DESC")
    List<CXPSolicitudPagoRHListadoProjection> getListadoSolicitudes(@Param("sedeIds") List<Integer> sedeIds,@Param("usuariosIds") List<Integer> usuariosIds);

    @Query(value = "Select TOP 1 Rh.* from CXPSolicitudesPagosRH Rh\n" +
            "INNER JOIN CXPSolicitudesPagosDetalles SPD on SPD.CXPSD_CXPF_CXPFacturaId = Rh.CPXSPRH_CXPF_CXPFacturaId\n" +
            "WHERE RH.CPXSPRH_CXPSolicitudPagoRhId= :id",
            nativeQuery = true)
    CXPSolicitudPagoRHEditarProjection buscarPagosYaProgramados(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "Select CPXSPRH_CXPSolicitudPagoRhId from CXPSolicitudesPagosRH where CPXSPRH_CXPF_CXPFacturaId=:idFactura")
    Integer getIdByFactura(@Param("idFactura") Integer idFactura);

}
