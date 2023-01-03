package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoComboProjection {


    Integer getId();
    String getCodigo();
    String getNombreGrupo();
    String getSucursalId();
    String getNombreSucursal();
    Integer getGrupoReferenciaId();
    Integer getCupoDisponible();
}