package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoExamenDetalle;
import com.pixvs.main.models.projections.ProgramaGrupoExamenDetalle.ListadoProgramaGrupoExamenDetalleProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Rene Carrillo on 07/10/2022.
 */
public interface ProgramaGrupoExamenDetalleDao extends CrudRepository<ProgramaGrupoExamenDetalle, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LT_ProgramaGrupoExamenDetalle] WHERE GrupoId = :grupoId")
    List<ListadoProgramaGrupoExamenDetalleProjection> findListadoProgramaGrupoExamenDetalleProjection(@Param("grupoId") Integer grupoId);
}
