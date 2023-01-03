package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.ProgramaGrupoHistorial;
import com.pixvs.main.models.ProgramaMeta;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaEditarProjection;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 */
public interface ProgramaGrupoHistorialDao extends CrudRepository<ProgramaGrupoHistorial, String> {
    ProgramaGrupoHistorial findAllByInscripcionId(Integer id);
}
