package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.ProgramaGrupoIncompanyGrupo;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyListadoProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyGrupo.ProgramaGrupoIncompanyGrupoEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoIncompanyGrupoDao extends CrudRepository<ProgramaGrupoIncompanyGrupo, String> {
    ProgramaGrupoIncompanyGrupo findById(Integer id);
    ProgramaGrupoIncompanyGrupoEditarProjection findAllById(Integer id);

}