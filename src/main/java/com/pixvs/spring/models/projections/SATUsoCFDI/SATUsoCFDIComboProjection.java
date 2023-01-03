package com.pixvs.spring.models.projections.SATUsoCFDI;

import com.pixvs.spring.models.SATUsoCFDI;
import com.pixvs.spring.models.projections.SATRegimenFiscal.SATRegimenFiscalComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(types = {SATUsoCFDI.class})
public interface SATUsoCFDIComboProjection {

    Integer getId();

    String getCodigo();

    String getDescripcion();

    boolean getFisica();

    boolean getMoral();

    boolean getActivo();

    Set<SATRegimenFiscalComboProjection> getRegimenesFiscales();

    @Value("#{ target.codigo + ' - ' + target.descripcion }")
    String getValor();
}
