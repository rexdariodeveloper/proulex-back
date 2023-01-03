package com.pixvs.main.dao;

import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PACicloDao extends CrudRepository<PACiclo, String> {

    PACiclo findById(Integer id);

    List<PACicloListadoProjection> findProjectedListadoAllByActivoTrue();
    List<PACicloComboProjection> findProjectedComboAllByActivoTrue();

    List<PACicloFechaProjection> findProjectedFechaAllByActivoTrue();

    @Query(value = "select distinct ciclo.id as id, ciclo.codigo as codigo, ciclo.nombre as nombre from ProgramaGrupo pg " +
            "inner join pg.paCiclo ciclo " +
            "where (pg.sucursalId = :sedeId or pg.sucursalPlantelId = :plantelId)")
    List<PACicloComboProjection> findProjectedComboAllBySucursalId(@Param("sedeId") Integer sedeId, @Param("plantelId") Integer plantelId);

    @Query(value = "select distinct ciclo.id as id, ciclo.codigo as codigo, ciclo.nombre as nombre from ProgramaGrupo pg " +
            "inner join pg.paCiclo ciclo " +
            "where (pg.sucursalId = :sedeId or pg.sucursalPlantelId in (:planteles))")
    List<PACicloComboProjection> findProjectedComboAllBySucursalIdIn(@Param("sedeId") Integer sedeId, @Param("planteles") List<Integer> planteles);

    @Modifying
    @Query(value = "UPDATE PACiclo SET PACIC_Activo = :activo WHERE PACIC_CicloId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
