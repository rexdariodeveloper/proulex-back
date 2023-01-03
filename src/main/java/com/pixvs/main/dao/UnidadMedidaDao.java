package com.pixvs.main.dao;

import com.pixvs.main.models.UnidadMedida;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadMedidaDao extends CrudRepository<UnidadMedida, String> {

    UnidadMedida findById(Integer id);

    List<UnidadMedidaListadoProjection> findProjectedListadoAllByActivoTrue();

    List<UnidadMedidaComboProjection> findProjectedComboAllByActivoTrue();
    List<UnidadMedidaComboProjection> findProjectedComboAllByActivoTrueAndIdIn(List<Integer> ids);

    @Modifying
    @Query(value = "UPDATE UnidadesMedidas SET UM_Activo = :activo WHERE UM_UnidadMedidaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
