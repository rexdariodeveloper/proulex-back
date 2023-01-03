package com.pixvs.main.dao;

import com.pixvs.main.models.ListadoPrecio;
import com.pixvs.main.models.ListadoPrecioDetalle;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioEditarProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioListadoProjection;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ListadoPrecioDetalleDao extends CrudRepository<ListadoPrecioDetalle, String> {

    void deleteById(Integer id);
    void deleteByListadoPrecioId(Integer id);
    List<ListadoPrecioDetalleEditarProjection> findAllByListadoPrecioId(Integer id);

    @Modifying
    @Query(value = "UPDATE ListadosPreciosDetalles SET LIPRED_ListadoPrecioPadreId = null WHERE LIPRED_ListadoPrecioDetalleId = :id",
            nativeQuery = true)
    int borrarHijo(@Param("id") Integer id);

    ListadoPrecioDetalleEditarProjection findProjectedByArticuloIdAndListadoPrecioId(Integer articuloId, Integer listaId);
    ListadoPrecioDetalleEditarProjection findFirstProjectedByArticuloIdAndListadoPrecioId(Integer articuloId, Integer listaId);

    @Query("" +
            "SELECT lipred.id \n" +
            "FROM ListadoPrecioDetalle lipred \n" +
            "WHERE \n" +
            "   lipred.listadoPrecioId = :listadoPrecioId \n" +
            "   AND lipred.articuloId IN :articulosIds \n" +
            "")
    List<Integer> getIdAllByListadoPrecioIdAndArticuloIdIn(@Param("listadoPrecioId") Integer listadoPrecioId, @Param("articulosIds") List<Integer> articulosIds);

    void deleteAllByIdIn(List<Integer> ids);
}