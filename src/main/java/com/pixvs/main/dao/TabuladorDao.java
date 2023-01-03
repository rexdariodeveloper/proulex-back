package com.pixvs.main.dao;

import com.pixvs.main.models.Tabulador;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorEditarProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface TabuladorDao extends CrudRepository<Tabulador, String> {

    List<TabuladorListadoProjection> findAllByActivoIsTrue();
    List<TabuladorComboProjection> findComboAllByActivoIsTrue();
    TabuladorEditarProjection findProjectedEditarById(Integer id);
    TabuladorComboProjection findByPagoDiasFestivosIsTrue();

    @Modifying
    @Query(value = "UPDATE Tabuladores SET TAB_Activo = :activo WHERE TAB_TabuladorId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
