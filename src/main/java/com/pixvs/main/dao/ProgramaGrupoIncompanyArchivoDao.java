package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoIncompanyArchivo;
import com.pixvs.main.models.ProgramaGrupoIncompanyMaterial;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoIncompanyArchivoDao extends CrudRepository<ProgramaGrupoIncompanyArchivo, String> {
    void deleteByProgramaIncompanyId(Integer incompanyGrupoId);
}