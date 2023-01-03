package com.pixvs.main.dao;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.ListadoPrecio;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioEditarProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ListadoPrecioDao extends CrudRepository<ListadoPrecio, String> {

    //List<ListadoPrecioListadoProjection> findProjectedListadoAllBy();
    List<ListadoPrecioComboProjection> findAllByActivoIsTrueOrderByCodigo();

    ListadoPrecioEditarProjection findProjectedEditarById(Integer id);

    ListadoPrecioEditarProjection findByCodigo(String codigo);

    //List<ProveedorEditarProjection> findProjectedEditarByRfc(String rfc);

    ListadoPrecio findById(Integer id);


    @Modifying
    @Query(value = "UPDATE ListadosPrecios SET LIPRE_Activo = :activo WHERE LIPRE_ListadoPrecioId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);


    @Query(nativeQuery = true, value = "SELECT Distinct  id,codigo,nombre,fecha\n" +
            "    ,STUFF((\n" +
            "            SELECT distinct ',' + t1.asignado   --add distinct here\n" +
            "            FROM [dbo].[VW_LISTADO_PRECIO] T1\n" +
            "            WHERE T1.id = T2.id\n" +
            "            FOR XML PATH('')\n" +
            "            ), 1, 1, '') AS asignado\n" +
            "    ,activo\n" +
            "FROM [dbo].[VW_LISTADO_PRECIO] T2;")
    List<ListadoPrecioListadoProjection> findProjectedComboAllBy();

    @Query(nativeQuery = true, value = "SELECT [dbo].[getListaPreciosMapStr](:id,:sucursalId)")
    String getListaPreciosMapStr(@Param("id") Integer id, @Param("sucursalId") Integer sucursalId);

    @Query(nativeQuery = true, value = "SELECT [dbo].[getListaPreciosSinDescuentoMapStr](:id)")
    String getListaPreciosSinDescuentoMapStr(@Param("id") Integer id);

//    @Query(nativeQuery = true, value = "SELECT [dbo].[getPrecioVentaArticulo](:listaPrecioId, :articuloId)")
//    BigDecimal getPrecioVentaArticulo(@Param("listaPrecioId") Integer listaPrecioId, @Param("articuloId") Integer articuloId);


}