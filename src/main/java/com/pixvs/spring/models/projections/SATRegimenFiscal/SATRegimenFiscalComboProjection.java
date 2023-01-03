package com.pixvs.spring.models.projections.SATRegimenFiscal;

import com.pixvs.spring.models.SATRegimenFiscal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SATRegimenFiscal.class})
public interface SATRegimenFiscalComboProjection {

    Integer getId();

    String getCodigo();

    String getDescripcion();

    boolean getFisica();

    boolean getMoral();

    boolean getActivo();

    @Value("#{ target.codigo + ' - ' + target.descripcion }")
    String getValor();
}
