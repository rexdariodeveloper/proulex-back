package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaMeta;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaEditarProjection;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 */
public interface ProgramaMetaDao extends CrudRepository<ProgramaMeta, String> {

    // Modelo
    ProgramaMeta findById(Integer id);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_ProgramasMetas] where activo = 'activo'")
    List<ProgramaMetaListadoProjection> findProjectedListadoAllBy();

    // EditarProjection
    ProgramaMetaEditarProjection findProjectedEditarById(Integer id);

}
