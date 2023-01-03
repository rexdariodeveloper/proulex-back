package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoAlertaProjection;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPSolicitudPagoDao extends CrudRepository<CXPSolicitudPago, String> {

    CXPSolicitudPago findById(Integer id);

    List<CXPSolicitudPagoListadoProjection> findProjectedListadoAllByEstatusIdIn(List<Integer> estatusIds);

    CXPSolicitudPagoListadoProjection findProjectedById(Integer id);

    CXPSolicitudPagoEditarProjection findEditarProjectionById(Integer id);

    CXPSolicitudPagoAlertaProjection findProjectedAlertaById(Integer id);

    @Query(nativeQuery = true, value = "Select * from [dbo].[VW_LISTADO_SOLICITUD_PAGO]")
    List<Map<String, Object>> getListadoSolicitudes();

    @Query("" +
            "SELECT COUNT(cxpsp) \n" +
            "FROM CXPSolicitudPago cxpsp \n" +
            "INNER JOIN cxpsp.detalles cxpspd \n" +
            "WHERE \n" +
            "   cxpsp.estatusId NOT IN (" + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.BORRADA + "," +  + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA + "," + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.RECHAZADA + ") \n" +
            "   AND cxpspd.estatusId NOT IN (" + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.BORRADA + "," +  + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA + "," + ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.RECHAZADA + ") \n" +
            "   AND cxpspd.cxpFacturaId = :facturaId" +
            "")
    Integer getCountByFacturaId(@Param("facturaId") Integer facturaId);
}
