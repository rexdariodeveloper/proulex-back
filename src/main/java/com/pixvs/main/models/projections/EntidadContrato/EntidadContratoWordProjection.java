package com.pixvs.main.models.projections.EntidadContrato;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.EntidadContrato;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {EntidadContrato.class})
public interface EntidadContratoWordProjection {

    Integer getId();
    Integer getEntidadId();
    ArchivoProjection getDocumentoContrato();


}
