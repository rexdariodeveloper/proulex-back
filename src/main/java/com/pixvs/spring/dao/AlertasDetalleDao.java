package com.pixvs.spring.dao;

import com.pixvs.spring.models.AlertaDetalle;
import com.pixvs.spring.models.projections.Alerta.AlertaDetalleListProjection;
import com.pixvs.spring.models.projections.AlertaDetalle.AlertaDetalleListadoProjection;
import com.pixvs.spring.models.projections.AlertaDetalle.AlertaDetalleProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface AlertasDetalleDao extends CrudRepository<AlertaDetalle, Integer> {

    List<AlertaDetalle> findAllByAlertaIdAndEtapa(Integer alertaId, Integer etapaId);

    @Query(value = "SELECT * FROM VW_LISTADO_ALERTAS WHERE " +
            "(:fechaDesde IS NULL OR CAST(fecha AS DATE) >= CAST (:fechaDesde AS DATE) ) AND " +
            "(:fechaHasta IS NULL OR CAST(fecha AS DATE) <= CAST (:fechaHasta AS DATE) ) AND " +
            "(:estatusId IS NULL OR estatusId = :estatusId) AND " +
            "(:usuarioId IS NULL OR usuarioId = :usuarioId) AND " +
            "(:sedeId IS NULL OR sedeId = :sedeId) AND " +
            "(:tipoId IS NULL OR tipoId = :tipoId) " +
            "ORDER BY cast(fecha as datetime) DESC, codigo ", nativeQuery = true)
    List<AlertaDetalleListadoProjection> findProjectedListadoAllByFiltros(
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta,
            @Param("estatusId") Integer estatusId,
            @Param("usuarioId") Integer usuarioId,
            @Param("sedeId") Integer sedeId,
            @Param("tipoId") Integer tipoId);

    @Query(value = "" +
            "SELECT * \n" +
            "FROM VW_LISTADO_ALERTAS \n" +
            "WHERE \n" +
            "   ( \n" +
            "       (tipoId = 1000171 AND visto = 0) \n" +
            "       OR (tipoId = 1000172 AND estatusId = 1000151) \n" +
            "   ) AND usuarioId = :usuarioId \n" +
            "ORDER BY cast(fecha as datetime) DESC, codigo ", nativeQuery = true)
    List<AlertaDetalleListadoProjection> findProjectedListadoAllByPendientes(@Param("usuarioId") Integer usuarioId);

    List<AlertaDetalleListProjection> findTop50ByUsuarioIdAndTipoAlertaIdAndVistoOrderByFechaCreacionDesc(Integer idUSuario, Integer tipoAlertaId, Boolean visto);

    @Modifying
    @Query(value = "UPDATE AlertasDetalles SET ALD_CMM_EstatusId = :estatus WHERE ALD_AlertaDetalleId = :id", nativeQuery = true)
    int actualizarEstatus(@Param("id") Integer id, @Param("estatus") int estatus);

}
