package com.pixvs.main.models.projections.SucursalImpresoraFamilia;

import com.pixvs.main.models.SucursalImpresoraFamilia;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SucursalImpresoraFamilia.class})
public interface SucursalImpresoraFamiliaComboProjection {
    Integer getId();
    int getSucursalId();
    Integer getFamiliaId();
    Integer getTipoImpresoraId();
    String getIp();
}
