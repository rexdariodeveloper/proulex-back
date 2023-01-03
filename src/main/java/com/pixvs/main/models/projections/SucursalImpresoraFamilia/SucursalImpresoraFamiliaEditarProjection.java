package com.pixvs.main.models.projections.SucursalImpresoraFamilia;

import com.pixvs.main.models.ArticuloFamilia;
import com.pixvs.main.models.SucursalImpresoraFamilia;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SucursalImpresoraFamilia.class})
public interface SucursalImpresoraFamiliaEditarProjection {
    Integer getId();
    int getSucursalId();
    Integer getFamiliaId();
    ArticuloFamiliaComboProjection getFamilia();
    Integer getTipoImpresoraId();
    ControlMaestroMultipleComboProjection getTipoImpresora();
    String getIp();
    Boolean getActivo();
}
