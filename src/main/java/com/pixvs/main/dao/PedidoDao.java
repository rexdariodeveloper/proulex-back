package com.pixvs.main.dao;

import com.pixvs.main.models.Pedido;
import com.pixvs.main.models.projections.Pedido.PedidoEditarProjection;
import com.pixvs.main.models.projections.Pedido.PedidoListadoProjection;
import com.pixvs.main.models.projections.Pedido.PedidoListadoRecibirProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PedidoDao extends CrudRepository<Pedido, Integer> {

    Pedido save(Pedido pedido);

    PedidoEditarProjection findPedidoById(Integer id);

    List<PedidoListadoProjection> findAllPedidoBy();

    List<PedidoListadoProjection> findAllPedidoByOrderByFechaDesc();

    @Query(value = "SELECT p FROM " +
            "Pedido p " +
            "INNER JOIN p.localidadOrigen l " +
            "INNER JOIN l.almacen a " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "u.id = :usuarioId " +
            "ORDER BY p.fechaCreacion DESC")
    List<PedidoListadoProjection> findAllPedidosByPermiso(@Param("usuarioId") Integer usuarioId);

    List<PedidoListadoProjection> findAllPedidoByEstatusIdIn(List<Integer> estatus);

    @Query(value = "select p from " +
            "Pedido  p " +
            "inner join p.detalles d "+
            "inner join p.localidadCEDIS l "+
            "inner join l.almacen a "+
            "inner join a.usuariosPermisos u "+
            "where " +
            "d.cantidadPedida <> d.cantidadSurtida and " +
            "p.estatusId in (:estatus) and " +
            "u.id = :usuarioId")
    List<PedidoListadoProjection> getPedidosPorSurtir(@Param("estatus") List<Integer> estatus, @Param("usuarioId") Integer usuarioId);

    @Query(nativeQuery = true, value = "SELECT * FROM VW_LISTADO_RECIBIR_PROJECTION_Pedido WHERE estatusId IN(:estatus) AND usuarioId = :usuarioId ORDER BY id DESC")
    List<PedidoListadoRecibirProjection> getPedidosPorRecibir(@Param("estatus") List<Integer> estatus, @Param("usuarioId") Integer usuarioId);

    @Query(value = "SELECT * FROM VW_LISTADO_ARTICULOS_COMBO WHERE localidadId = :id", nativeQuery = true)
    List<Map<String,Object> > getListadoArticulos(@Param("id") Integer id);

    @Query(value = "SELECT * FROM VW_LISTADO_ARTICULOS_TEMPORADA", nativeQuery = true)
    List<Map<String,Object> > getArticulosTemporada();

    @Query(value = "SELECT * FROM VW_LISTADO_ARTICULOS_EXISTENCIAS WHERE localidadId = :id", nativeQuery = true)
    List<Map<String,Object> > getArticulosExistencia(@Param("id") Integer id);
    @Query(value = " \n" +
            "SELECT \n" +
            "   '{ \n" +
            "       ' + STRING_AGG(CAST(('\"' + CAST(articuloId AS varchar) + '\": ' + CAST(existencia AS varchar)) AS VARCHAR(MAX)),',') + ' \n" +
            "   }' \n" +
            "FROM VW_LISTADO_ARTICULOS_PAQUETES_EXISTENCIAS \n" +
            "WHERE localidadId = :id", nativeQuery = true)
    String getArticulosPaquetesExistenciaAsJson(@Param("id") Integer id);

    @Query(value = "SELECT * FROM VW_LISTADO_ARTICULOS_EXISTENCIAS_ALMACEN WHERE almacenId = :id", nativeQuery = true)
    List<Map<String,Object> > getArticulosExistenciaAlmacen(@Param("id") Integer id);

    @Query(value = " \n" +
            "SELECT \n" +
            "   '{ \n" +
            "       ' + STRING_AGG(CAST(('\"' + CAST(articuloId AS varchar) + '\": ' + CAST(existencia AS varchar)) AS VARCHAR(MAX)),',') + ' \n" +
            "   }' \n" +
            "FROM VW_LISTADO_ARTICULOS_PAQUETES_EXISTENCIAS_ALMACEN \n" +
            "WHERE almacenId = :id", nativeQuery = true)
    String getArticulosPaquetesExistenciaAlmacenAsJson(@Param("id") Integer id);

    @Query(value = "SELECT dbo.fn_getTipoSurtimiento(:codigo)", nativeQuery = true)
    Boolean getTipoSurtimiento(@Param("codigo") String codigo);

    @Query(value = "SELECT dbo.fn_canClosePedido(:codigo)", nativeQuery = true)
    Boolean canClosePedido(@Param("codigo") String codigo);

    @Query(value = "SELECT * FROM InventariosMovimientos WHERE IM_LOC_LocalidadId = :localidadId AND IM_Referencia = :referencia", nativeQuery = true)
    List<Map<String,Object> > getMovimientos(@Param("localidadId") Integer localidadId, @Param("referencia") String referencia);

    @Query(value = "SELECT IM_ART_ArticuloId articuloId, SUM(IM_Cantidad) cantidad, string_agg(IM_Razon, '|') razon " +
            "FROM InventariosMovimientos WHERE IM_LOC_LocalidadId = :localidadId AND IM_Referencia = :referencia " +
            "GROUP BY IM_ART_ArticuloId HAVING SUM(IM_Cantidad) > 0", nativeQuery = true)
    List<Map<String,Object> > getMovimientosAgrupados(@Param("localidadId") Integer localidadId, @Param("referencia") String referencia);

    @Query(value = "SELECT " +
            "IM_ART_ArticuloId articuloId, " +
            "IM_Cantidad cantidad, " +
            "IM_Razon razon, " +
            "IM_FechaCreacion fecha, " +
            "CONCAT(COALESCE(USU_Nombre,''),' ',COALESCE(USU_PrimerApellido,''),' ',COALESCE(USU_SegundoApellido,''),' ') usuario " +
            "FROM " +
            "InventariosMovimientos INNER JOIN " +
            "Usuarios on IM_USU_CreadoPorId = USU_UsuarioId " +
            "WHERE " +
            "IM_LOC_LocalidadId = :localidadId " +
            "AND IM_Referencia = :referencia " +
            "AND IM_Cantidad > 0", nativeQuery = true)
    List<Map<String,Object> > getMovimientosTodos(@Param("localidadId") Integer localidadId, @Param("referencia") String referencia);

    @Modifying
    @Query(value = "UPDATE Pedidos SET PED_Estatus = :estatus WHERE PED_PedidoId = :id", nativeQuery = true)
    int actualizarEstatus(@Param("id") Integer id, @Param("estatus") int estatus);

    @Query("SELECT p FROM Pedido p " +
            "WHERE (:fechaCreacionDesde IS NULL OR CAST(p.fecha AS date) >= :fechaCreacionDesde) " +
            "AND (:fechaCreacionHasta IS NULL OR CAST(p.fecha AS date) <= :fechaCreacionHasta) " +
            "AND (:allEstatus = 1 OR p.estatusId IN (:estatus))" +
            "ORDER BY p.fecha DESC")
    List<PedidoListadoProjection> findAllQueryProjectedBy(@Param("fechaCreacionDesde") Date fechaCreacion, @Param("fechaCreacionHasta") Date fechaCreacionHasta, @Param("allEstatus") int allEstatus, @Param("estatus") List<Integer> estatus);

    @Query("SELECT p FROM Pedido p " +
            "WHERE (:fechaCreacionDesde IS NULL OR CAST(p.fecha AS date) >= :fechaCreacionDesde) " +
            "AND (:fechaCreacionHasta IS NULL OR CAST(p.fecha AS date) <= :fechaCreacionHasta) " +
            "AND p.estatusId IN (:estatus)" +
            "ORDER BY p.fecha DESC")
    List<PedidoListadoProjection> findAllQueryEstatusBy(@Param("fechaCreacionDesde") Date fechaCreacion, @Param("fechaCreacionHasta") Date fechaCreacionHasta, @Param("estatus") List<Integer> estatus);

    @Query(value = "SELECT * FROM VW_REPORTE_PEDIDOS WHERE " +
            "(:fechaDesde IS NULL OR fechaPedido >= CAST (:fechaDesde AS DATE) ) AND " +
            "(:fechaHasta IS NULL OR fechaPedido <= CAST (:fechaHasta AS DATE) ) AND " +
            "(:codigo IS NULL OR pedido = :codigo) AND " +
            "(:allEstatus = 1 OR estatusId IN (:estatus)) AND " +
            "(:allAlmacenes = 1 OR almacenId IN (:almacenes)) AND " +
            "(:allLocalidades = 1 OR origenId IN (:localidades)) " +
            "ORDER BY pedido", nativeQuery = true)
    List<Map<String, Object> > findAllQueryReporteBy(
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta,
            @Param("codigo") String codigo,
            @Param("allEstatus") int allEstatus,
            @Param("estatus") List<Integer> estatus,
            @Param("allAlmacenes") int allAlmacenes,
            @Param("almacenes") List<Integer> almacenes,
            @Param("allLocalidades") int allLocalidades,
            @Param("localidades") List<Integer> localidades);


    @Query(value = " \n" +
            "SELECT \n" +
            "   '{ \n" +
            "       ' + STRING_AGG(CAST(('\"' + CAST(articuloId AS varchar) + '\": ' + jsonCantidadesLocalidades) AS VARCHAR(MAX)),',') + ' \n" +
            "   }' \n" +
            "FROM( \n" +
            "   SELECT \n" +
            "       '{ \n" +
            "           ' + STRING_AGG(CAST(('\"' + CAST(localidadId AS varchar) + '\": ' + CAST(existenciaLocalidad AS varchar)) AS VARCHAR(MAX)),',') + ' \n" +
            "       }' AS jsonCantidadesLocalidades, \n" +
            "       articuloId, \n" +
            "       almacenId \n" +
            "   FROM VW_ARTICULOS_EXISTENCIAS \n" +
            "   GROUP BY articuloId, almacenId \n" +
            ") A WHERE almacenId = :almacenId", nativeQuery = true)
    String getArticulosExistenciasJsonMapLocalidadesByAlmacenId(@Param("almacenId") Integer almacenId);
}