package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenVentaDetalle;
import com.pixvs.main.models.projections.OrdenVentaDetalle.OrdenVentaDetalleHistorialPVResumenProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 10/09/2021.
 */
public interface OrdenVentaDetalleDao extends CrudRepository<OrdenVentaDetalle, String> {

    // HistorialPVResumen
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM VW_HistorialPVResumen_OrdenesVentaDetalles \n" +
            "WHERE ordenVentaId = :ordenVentaId \n" +
            "")
    List<OrdenVentaDetalleHistorialPVResumenProjection> findProjectedHistorialPVResumenByOrdenVentaId(@Param("ordenVentaId") Integer ordenVentaId);

    // BigDecimal
    @Query("" +
            "SELECT SUM(ovd.precioSinConvertir) \n" +
            "FROM OrdenVentaDetalle ovd \n" +
            "WHERE ovd.id = :ordenVentaDetalleId OR ovd.detallePadreId = :ordenVentaDetalleId \n" +
            "")
    BigDecimal getPrecioUnitarioAcumuladoSinConvertir(@Param("ordenVentaDetalleId") Integer ordenVentaDetalleId);
    @Query("" +
            "SELECT SUM(ovd.precio) \n" +
            "FROM OrdenVentaDetalle ovd \n" +
            "WHERE ovd.id = :ordenVentaDetalleId OR ovd.detallePadreId = :ordenVentaDetalleId \n" +
            "")
    BigDecimal getPrecioUnitarioAcumuladoConvertido(@Param("ordenVentaDetalleId") Integer ordenVentaDetalleId);

    // Boolean
    @Query("" +
            "SELECT CASE WHEN ovcd.id IS NOT NULL THEN true ELSE false END \n" +
            "FROM OrdenVentaDetalle ovd \n" +
            "LEFT JOIN OrdenVentaCancelacionDetalle ovcd ON ovcd.ordenVentaDetalleId = ovd.id \n" +
            "WHERE ovd.id = :id \n" +
            "")
    Boolean isOrdenVentaDetalleById(@Param("id") Integer id);

    // Integer
    @Query("" +
            "SELECT ovd.id \n" +
            "FROM OrdenVentaDetalle ovd \n" +
            "INNER JOIN OrdenVentaCancelacionDetalle ovcd ON ovcd.ordenVentaDetalleId = ovd.id \n" +
            "WHERE ovd.ordenVentaId = :ordenVentaId \n" +
            "")
    List<Integer> getIdAllByOrdenVentaIdAndCancelado(@Param("ordenVentaId") Integer ordenVentaId);

    List<OrdenVentaDetalle> findAllByDetallePadreId(Integer padreId);

    OrdenVentaDetalle findById(Integer id);
}
