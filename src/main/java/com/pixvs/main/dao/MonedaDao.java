package com.pixvs.main.dao;

import com.pixvs.main.models.Moneda;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonedaDao extends CrudRepository<Moneda, String> {

    Moneda findById(Integer id);
    Moneda findByPredeterminadaTrue();
    Moneda findByIdNotAndPredeterminadaTrue(Integer id);
    Moneda findByCodigo(String codigo);

    List<MonedaListadoProjection> findProjectedListadoAllByActivoTrue();

    List<MonedaComboProjection> findProjectedComboAllByActivoTrue();
    MonedaComboProjection findProjectedComboByPredeterminadaTrue();

    @Modifying
    @Query(value = "UPDATE Monedas SET MON_Activo = :activo WHERE MON_MonedaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
