package com.pixvs.main.dao;

import com.pixvs.main.models.PADescuentoArticulo;
import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.projections.PADescuentoArticulo.PADescuentoArticuloEditarProjection;
import com.pixvs.main.models.projections.PADescuentoDetalle.PADescuentoDetalleEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DescuentoArticulosDao extends CrudRepository<PADescuentoArticulo, String> {
    @Query(value = "Select PADESCA_DescuentoArticuloId as id,PADESCA_PADESC_DescuentoId as descuentoId from PADescuentos\n" +
            "INNER JOIN PADescuentosArticulos on PADESCA_PADESC_DescuentoId = PADESC_DescuentoId\n" +
            "INNER JOIN Articulos on ART_ArticuloId = PADESCA_ART_ArticuloId\n" +
            "WHERE ART_ArticuloId= :articuloId", nativeQuery = true)
    List<PADescuentoArticuloEditarProjection> findPADescuentoArticuloByArticuloId(@Param("articuloId") Integer articuloId);

    void deleteById(Integer id);

    /*@Modifying
    @Query(value = "UPDATE PADescuentos SET PADESC_Activo = :activo WHERE PADESC_DescuentoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);*/
}
