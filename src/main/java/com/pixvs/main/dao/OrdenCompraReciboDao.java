package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCXPFacturaListadoProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboCargarFacturaProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboListadoProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboRelacionarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 24/08/2020.
 */
public interface OrdenCompraReciboDao extends CrudRepository<OrdenCompraRecibo, String> {

    OrdenCompraRecibo findById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_RELACIONAR_PROJECTION_OrdenCompraRecibo] WHERE ordenCompraDetalleId = :ordenCompraDetalleId")
    List<OrdenCompraReciboRelacionarProjection> findProjectedRelacionarAllByOrdenCompraDetalleId(@Param("ordenCompraDetalleId") Integer ordenCompraDetalleId);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] ORDER BY codigoRecibo")
    List<OrdenCompraReciboCXPFacturaListadoProjection> findProjectedCXPFacturaListadoAllByOrderByCodigoRecibo();
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] WHERE id IN :ids")
    List<OrdenCompraReciboCXPFacturaListadoProjection> findProjectedCXPFacturaListadoAllByIdIn(@Param("ids") List<Integer> ids);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] \n" +
            "WHERE \n" +
            "   proveedorId IN :proveedorIds \n" +
            "   AND fechaReciboRegistro > :fechaReciboInicio \n" +
            "   AND fechaReciboRegistro < :fechaReciboFin \n" +
            "ORDER BY codigoRecibo \n" +
            "")
    List<OrdenCompraReciboCXPFacturaListadoProjection> findProjectedCXPFacturaListadoAllByProveedorIdInAndFechaReciboGreaterThanAndFechaReciboLessThanOrderByCodigoRecibo(@Param("proveedorIds") List<Integer> proveedorIds, @Param("fechaReciboInicio") Date fechaReciboInicio, @Param("fechaReciboFin") Date fechaReciboFin);

    OrdenCompraReciboCargarFacturaProjection findProjectedCargarFacturaById(Integer id);

    List<OrdenCompraReciboListadoProjection> findProjectedListadoAllByOrdenCompraIdOrderByIdAsc(Integer ordenCompraId);


}
