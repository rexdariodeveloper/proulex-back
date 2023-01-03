package com.pixvs.main.models.projections.BecaUDG;

import com.pixvs.main.models.BecaUDG;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {BecaUDG.class})
public interface BecaUDGReporteFiltrosProjection {

    Integer getSedeId();

    Integer getClienteId();

    boolean isCienPorciento();

    String getNombre();
}