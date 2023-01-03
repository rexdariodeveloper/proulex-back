package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPagoServicio;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicio.CXPSolicitudPagoServicioEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicio.CXPSolicitudPagoServicioListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CXPSolicitudPagoServicioDao extends CrudRepository<CXPSolicitudPagoServicio, String> {

    CXPSolicitudPagoServicio findById(Integer id);
    @Query("" +
            "SELECT cxpsps " +
            "FROM CXPSolicitudPagoServicio cxpsps " +
            "INNER JOIN cxpsps.detalles d " +
            "WHERE d.cxpFacturaId = :cxpFacturaId" +
            "")
    List<CXPSolicitudPagoServicio> findByCxpFacturaId(@Param("cxpFacturaId") Integer cxpFacturaId);

    CXPSolicitudPagoServicioEditarProjection findEditarProjectionById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_SOLICITUD_PAGO] ORDER BY codigoSolicitud DESC")
    List<CXPSolicitudPagoServicioListadoProjection> getListadoSolicitudes();
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_SOLICITUD_PAGO] WHERE sedeId IN :sedeIds ORDER BY codigoSolicitud DESC")
    List<CXPSolicitudPagoServicioListadoProjection> getListadoSolicitudes(@Param("sedeIds") List<Integer> sedeIds);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_SOLICITUD_PAGO] WHERE sedeId IN :sedeIds AND usuarioId IN :usuariosIds ORDER BY codigoSolicitud DESC")
    List<CXPSolicitudPagoServicioListadoProjection> getListadoSolicitudes(@Param("sedeIds") List<Integer> sedeIds,@Param("usuariosIds") List<Integer> usuariosIds);

}
