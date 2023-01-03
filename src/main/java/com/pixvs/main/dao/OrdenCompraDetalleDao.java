package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionadoProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 14/09/2020.
 */
public interface OrdenCompraDetalleDao extends CrudRepository<OrdenCompraDetalle, String> {

    List<OrdenCompraDetalleRelacionarProjection> findProjectedRelacionarAllByOrdenCompraId(Integer ordenCompraId);
    List<OrdenCompraDetalleRelacionarProjection> findProjectedRelacionarAllByOrdenCompraIdIn(List<Integer> ordenCompraIds);

    @Query("" +
            "SELECT DISTINCT " +
            "   ocd.id AS id, " +
            "   ocd.ordenCompraId AS ordenCompraId, " +
            "   ocd.articuloId AS articuloId, " +
            "   ocd.articulo.nombreArticulo AS articuloNombre, " +
            "   ocd.cantidad AS cantidad, " +
            "   cxpfd.cantidad AS cantidadRelacionada, " +
            "   cxpfd.iva AS iva, " +
            "   cxpfd.ivaExento AS ivaExento, " +
            "   cxpfd.ieps AS ieps, " +
            "   cxpfd.iepsCuotaFija AS iepsCuotaFija, " +
            "   oc.codigo AS codigoOC, " +
            "   oc.fechaRequerida AS fechaRequerida, " +
            "   cxpfd.descuento AS descuento, " +
            "   ocd.precio AS precio, " +
            "   cxpfd.precioUnitario AS precioUnitario, " +
            "   ocd.unidadMedida.id AS unidadMedidaId, " +
            "   ocd.unidadMedida.nombre AS unidadMedidaNombre " +
            "FROM OrdenCompraDetalle ocd " +
            "INNER JOIN ocd.ordenCompra oc " +
            "INNER JOIN ocd.recibos ocdr " +
            "INNER JOIN ocdr.cxpFacturasDetalles cxpfd " +
            "WHERE cxpfd.cxpFacturaId = :facturaId " +
            "")
    List<OrdenCompraDetalleRelacionadoProjection> findProjectedRelacionarAllByFacturaIdIn(@Param("facturaId") Integer facturaId);

}
