package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.ProgramaGrupoIncompanyMaterial;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoIncompanyGrupoMaterialDao extends CrudRepository<ProgramaGrupoIncompanyMaterial, String> {
    void deleteByGrupoId(Integer grupoId);
}