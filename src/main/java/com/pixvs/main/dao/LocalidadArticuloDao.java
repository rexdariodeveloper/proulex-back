package com.pixvs.main.dao;

import com.pixvs.main.models.LocalidadArticulo;
import com.pixvs.main.models.projections.Almacen.AlmacenListadoProjection;
import com.pixvs.main.models.projections.LocalidadArticulo.LocalidadArticuloProjection;
import com.pixvs.main.models.projections.PedidoReciboDetalle.PedidoMovimientoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.Almacen;

import java.util.List;

public interface LocalidadArticuloDao  extends CrudRepository<LocalidadArticulo, Integer> {

    LocalidadArticulo findByArticuloIdAndLocalidadId(Integer articuloId, Integer localidadId);

    @Query("SELECT la " +
            "FROM LocalidadArticulo la \n" +
            "INNER JOIN la.localidad l \n" +
            "WHERE l.almacenId = :almacenId")
    List<LocalidadArticuloProjection> findLocalidadArticuloByalmacenId(@Param("almacenId") Integer almacenId);//findLocalidadArticuloByAlmacen(@Param("almacen") Integer almacen);

    @Query("SELECT la " +
            "FROM LocalidadArticulo la \n" +
            "INNER JOIN la.localidad l \n" +
            "WHERE l.almacenId = :almacenId" +
            " AND la.articuloId = :articuloId")
    LocalidadArticuloProjection findLocalidadArticuloByalmacenIdAnd(@Param("almacenId") Integer almacenId, @Param("articuloId") Integer articuloId);//findLocalidadArticuloByAlmacen(@Param("almacen") Integer almacen);

    List<LocalidadArticuloProjection> findAllProjectedBy();

}
