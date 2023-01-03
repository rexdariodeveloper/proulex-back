package com.pixvs.main.models.projections.ProgramaGrupoIncompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyGrupo.ProgramaGrupoIncompanyGrupoEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupoIncompany.class})
public interface ProgramaGrupoIncompanyListadoProjection {


    Integer getId();

    String getCodigo();

    String getCliente();

    String getSucursal();

    String getCurso();

    Integer getGrupos();

    Boolean getActivo();
}