package com.pixvs.main.dao;

import com.pixvs.main.models.Requisicion;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Requisicion.RequisicionConvertirListadoProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionEditarProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionListadoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 */
public interface RequisicionDao extends CrudRepository<Requisicion, String> {

    @Query("" +
            "SELECT DISTINCT \n" +
            "   r.id as id, r.codigo as codigo, r.fecha as fecha, \n" +
            "   r.departamento as departamento, r.almacen as almacen, r.almacen.sucursal as sucursal, \n" +
            "   r.creadoPor as creadoPor, r.estadoRequisicion as estadoRequisicion \n" +
            "FROM Requisicion r \n" +
            "INNER JOIN r.almacen.usuariosPermisos up \n" +
            "WHERE \n" +
            "   r.estadoRequisicionId != " + ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.BORRADA + " \n" +
            "   AND ( \n" +
            "       r.creadoPorId = :usuarioId \n" +
            "       OR up.id = :usuarioId \n" +
            ") \n" +
            "ORDER BY r.id DESC \n" +
            "")
    List<RequisicionListadoProjection> findProjectedListadoAllByUsuarioId(@Param("usuarioId") Integer usuarioId);

    RequisicionEditarProjection findProjectedEditarById(Integer id);

    @Query("" +
            "SELECT r \n" +
            "FROM Requisicion r \n" +
            "WHERE \n" +
            "   estadoRequisicionId = " + ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO + " \n" +
            "   AND ( \n" +
            "       :fecha1 IS NULL \n" +
            "       OR fecha >= :fecha1" +
            "   ) AND ( \n" +
            "       :fecha2 IS NULL \n" +
            "       OR fecha <= :fecha2" +
            "   ) AND ( \n" +
            "       :codigo IS NULL \n" +
            "       OR codigo <= :codigo" +
            "   ) AND ( \n" +
            "       almacen.sucursalId IN :sucursalesIds" +
            "   ) AND ( \n" +
            "       departamentoId IN :departamentosIds" +
            "   ) \n" +
            "")
    List<RequisicionConvertirListadoProjection> findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndSucursalesIdsAndDepartamentosIds(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("codigo") String codigo, @Param("sucursalesIds") List<Integer> sucursalesIds, @Param("departamentosIds") List<Integer> departamentosIds);
    @Query("" +
            "SELECT r \n" +
            "FROM Requisicion r \n" +
            "WHERE \n" +
            "   estadoRequisicionId = " + ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO + " \n" +
            "   AND ( \n" +
            "       :fecha1 IS NULL \n" +
            "       OR fecha >= :fecha1" +
            "   ) AND ( \n" +
            "       :fecha2 IS NULL \n" +
            "       OR fecha <= :fecha2" +
            "   ) AND ( \n" +
            "       :codigo IS NULL \n" +
            "       OR codigo <= :codigo" +
            "   ) AND ( \n" +
            "       almacen.sucursalId IN :sucursalesIds" +
            "   ) \n" +
            "")
    List<RequisicionConvertirListadoProjection> findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndSucursalesIds(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("codigo") String codigo, @Param("sucursalesIds") List<Integer> sucursalesIds);
    @Query("" +
            "SELECT r \n" +
            "FROM Requisicion r \n" +
            "WHERE \n" +
            "   estadoRequisicionId = " + ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO + " \n" +
            "   AND ( \n" +
            "       :fecha1 IS NULL \n" +
            "       OR fecha >= :fecha1" +
            "   ) AND ( \n" +
            "       :fecha2 IS NULL \n" +
            "       OR fecha <= :fecha2" +
            "   ) AND ( \n" +
            "       :codigo IS NULL \n" +
            "       OR codigo <= :codigo" +
            "   ) AND ( \n" +
            "       departamentoId IN :departamentosIds" +
            "   ) \n" +
            "")
    List<RequisicionConvertirListadoProjection> findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndDepartamentosIds(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("codigo") String codigo, @Param("departamentosIds") List<Integer> departamentosIds);
    @Query("" +
            "SELECT r \n" +
            "FROM Requisicion r \n" +
            "WHERE \n" +
            "   estadoRequisicionId = " + ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO + " \n" +
            "   AND ( \n" +
            "       :fecha1 IS NULL \n" +
            "       OR fecha >= :fecha1" +
            "   ) AND ( \n" +
            "       :fecha2 IS NULL \n" +
            "       OR fecha <= :fecha2" +
            "   ) AND ( \n" +
            "       :codigo IS NULL \n" +
            "       OR codigo <= :codigo" +
            "   ) \n" +
            "")
    List<RequisicionConvertirListadoProjection> findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLike(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("codigo") String codigo);

    Requisicion findById(Integer id);
    @Query("" +
            "SELECT r \n" +
            "FROM Requisicion r \n" +
            "INNER JOIN r.partidas p \n" +
            "WHERE p.id = :requisicionPartidaId" +
            "")
    Requisicion findByPartidaId(@Param("requisicionPartidaId") Integer requisicionPartidaId);
}