package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 07/07/2021.
 */
@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoCardProjection {

    Integer getId();
    String getNombre();
    Integer getNumeroGrupo();
    Date getFechaInicio();
    Date getFechaFin();
    String getHorario();
    String getColor();
    Integer getArticuloId();
    Integer getNivel();
    Boolean getPermiteInscripcion();
    Boolean getEsMultisede();
    Integer getSucursalId();
    String getSucursalNombre();
    Integer getCupoDisponible();
    BigDecimal getPrecioVentaInCompany();
    Integer getTipoGrupoId();
    String getTipoGrupo();

}
