package com.pixvs.spring.dao;

import com.pixvs.spring.models.Alerta;
import com.pixvs.spring.models.AlertaDetalle;
import com.pixvs.spring.models.projections.Alerta.AlertaDetalleListProjection;
import com.pixvs.spring.models.projections.Alerta.AlertaProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface AlertasDao extends CrudRepository<Alerta, Integer> {

    @Query("SELECT ad FROM AlertaDetalle ad" +
            "   INNER JOIN ad.alerta a" +
            "   WHERE ad.usuarioId = :idUSuario " +
            "   AND ad.tipoAlertaId = 1000172" +
            "   AND ad.estatusDetalleId = 1000151" +
            "   ORDER BY ad.fechaCreacion DESC")
    List<AlertaDetalleListProjection> findAllAlertasByUsuario(@Param("idUSuario") Integer idUSuario);


    @Query("SELECT ad FROM AlertaDetalle ad" +
            "   INNER JOIN ad.alerta a" +
            "   WHERE ad.usuarioId = :idUSuario " +
            "   AND ad.tipoAlertaId = 1000171" +
            "   AND ad.visto = 0" +
            "   ORDER BY ad.fechaCreacion DESC")
    List<AlertaDetalleListProjection> findAllNotificacionesByUsuario(@Param("idUSuario") Integer idUSuario, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM Alertas\n" +
            "INNER JOIN AlertasDetalles ON ALD_ALE_AlertaId = ALE_AlertaId\n" +
            "WHERE ALE_ReferenciaProcesoId = :idProceso \n" +
            "and ALD_Archivado = 0")
    List<Alerta> findAllByProcesoSinArchivarId(@Param("idProceso") Integer idProceso);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM Alertas\n" +
            "INNER JOIN AlertasDetalles ON ALD_ALE_AlertaId = ALE_AlertaId\n" +
            "WHERE ALE_ReferenciaProcesoId = :idProceso \n")
    List<Alerta> findAllByProcesoId(@Param("idProceso") Integer idProceso);

    //AlertasProjection findAllByReferenciaProcesoIdAndCodigoTramiteAndEstatusAlertaId(@Param("idProceso") Integer idProceso, @Param("codigoTramite") String codigo, @Param("idProceso") Integer idEstatusAlerta);
    List<AlertaProjection> findAllProjectedByEstatusAlertaIdInAndCreadoPorId(List<Integer> estatusIds,Integer usuarioId);
    List<AlertaProjection> findAllProjectedByIdIn(List<Integer> Ids);
    //List<DetallesAlerta> findAllBy();

    AlertaDetalle findAllProjectedById(@Param("idalerta") Integer idalerta);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM Alertas\n" +
            "INNER JOIN AlertasDetalles ON ALD_ALE_AlertaId = ALE_AlertaId\n" +
            "WHERE ALD_USU_UsuarioId = :idUSuario \n" +
            "and ALD_Archivado = 0")
    List<AlertaProjection> findAllByUsuario2(@Param("idUSuario") Integer idUSuario);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE AlertasDetalles set ALD_Visto = 1 WHERE ALD_AlertaDetalleId = :idDetalle")
    @Transactional
    void actualizaVistoDetalle(@Param("idDetalle") int idDetalle);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE AlertasDetalles set ALD_Visto = 1 , ALD_Archivado = 1 WHERE ALD_AlertaDetalleId = :idDetalle")
    @Transactional
    void actualizaArchivaDetalle(@Param("idDetalle") int idDetalle);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Alertas\n" +
            "SET ALE_FechaAutorizacion = GETDATE(),\n" +
            "ALE_CMM_EstatusAlertaId = :idEstatus ,\n" +
            "ALE_FechaUltimaModificacion = GETDATE(),\n" +
            "ALE_USU_ModificadoPorId = :idUsuario \n" +
            "\n" +
            "WHERE ALE_AlertaId = (select ALE_AlertaId \n" +
            "\t\t\t\t\t\tfrom Alertas \n" +
            "\t\t\t\t\t\tinner join AlertasDetalles on ALD_ALE_AlertaId = ALE_AlertaId\n" +
            "\t\t\t\t\t\twhere ALD_USU_UsuarioId = :idUsuario \n" +
            "\t\t\t\t\t\tand ALE_ReferenciaProcesoId =  :idReferencia" +
            "and ALE_ACE_AlertaConfiguracionEtapaId=ALD_ACE_AlertaConfiguracionEtapaId)")
    @Transactional
    void actualizaEstatusAlerta(@Param("idEstatus") int idEstatus, @Param("idReferencia") int idReferencia, @Param("idUsuario") int idUsuario);


    @Query("SELECT a FROM Alerta a" +
            "   INNER JOIN a.config ac" +
            "   WHERE a.referenciaProcesoId = :idProceso " +
            "   AND ac.tipoMovimientoId = :tipoMovimietnoId ")
    AlertaProjection findProjectedByProcesoIdAndTipoMovimimentoId(@Param("idProceso") Integer idProceso, @Param("tipoMovimietnoId") Integer tipoMovimietnoId);



    @Modifying
    @Query(nativeQuery = true, value = "UPDATE AlertasDetalles\n" +
            "SET ALD_CMM_EstatusDetalleId = :idEstatus,\n" +
            "ALD_Archivado = 1,\n" +
            "ALD_FechaUltimaModificacion = GETDATE(),\n" +
            "ALD_Mostrar = 0,\n" +
            "ALD_Comentario = :comentario,\n" +
            "ALD_USU_ModificadoPorId = :idUsuario\n" +
            "\n" +
            "WHERE ALD_USU_UsuarioId = :idUsuario AND ALD_CMM_TipoAlertaId=1000511 /*alertas*/ and ALD_ALE_AlertaId = (select ALE_AlertaId \n" +
            "\t\t\t\t\t\tfrom Alertas \n" +
            "\t\t\t\t\t\tinner join AlertasDetalles on ALD_ALE_AlertaId = ALE_AlertaId\n" +
            "\t\t\t\t\t\tinner join AlertasConfig on ALE_ALC_AlertaCId = ALC_AlertaCId\n" +
            "\t\t\t\t\t\twhere ALD_USU_UsuarioId = :idUsuario \n" +
            "\t\t\t\t\t\tand ALC_CMM_TipoConfigAlertaId = 1000503 /*solo autorizaciones*/  \n" +
            "\t\t\t\t\t\tand ALE_ReferenciaProcesoId = :idReferencia" +
            " and ALE_ACE_AlertaConfiguracionEtapaId=ALD_ACE_AlertaConfiguracionEtapaId)")
    @Transactional
    void actualizaEstatusAlertaDetalle(@Param("idEstatus") int idEstatus, @Param("idReferencia") int idReferencia, @Param("idUsuario") int idUsuario, @Param("comentario") String comentario);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE SolicitudesPagos \n" +
            "set SPA_CMM_EstatusId = :idEstatus ,\n" +
            "SPA_USU_ModificadoPorId = :idUsuario ,\n" +
            "SPA_FechaUltimaModificacion = GETDATE()\n" +
            "where SPA_SolicitudPagoId = :idSolicitud")
    @Transactional
    void actualizaEstatusAlertaSolicitudPago(@Param("idEstatus") int idEstatus, @Param("idSolicitud") int idSolicitud, @Param("idUsuario") int idUsuario);


    Alerta findAllById(@Param("idAlerta") Integer idAlerta);

    //Alerta findAllByReferenciaProcesoId(@Param("idProceso") Integer idProceso);
    AlertaProjection findAllByReferenciaProcesoId(@Param("idProceso") Integer idProceso);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE AlertasDetalles SET ALD_Comentario=:motivo, ALD_FechaUltimaModificacion=:fecha WHERE ALD_ALE_AlertaId = :idAlerta ")
    @Transactional
    void agregacomentarioalerta(@Param("motivo") String motivo, @Param("idAlerta") int idAlerta,@Param("fecha") Timestamp fecha);

    Alerta findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(Integer configId, Integer referenciaId, Integer estatusId);
    List<Alerta> findByAlertaCIdAndReferenciaProcesoIdOrderByFechaInicioDesc(Integer configId, Integer referenciaId);
    Optional<Alerta> findById(Integer id);

    @Query(nativeQuery = true, value = "EXECUTE [dbo].[sp_ActualizaProcedimientoAlerta] :alertaConfigId\n" +
            "  ,:referencia_proceso_id\n" +
            "  ,:valorEstatus")
    @Transactional
    void getProcedimiento(@Param("alertaConfigId") Integer alertaConfigId, @Param("referencia_proceso_id") int referencia_proceso_id,@Param("valorEstatus") Integer valorEstatus);

}
