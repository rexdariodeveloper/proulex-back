package com.pixvs.main.models.projections.EmpleadoDeduccionPercepcionDocumento;

import com.pixvs.main.models.CXPSolicitudPagoRHBecarioDocumento;
import com.pixvs.main.models.EmpleadoDeduccionPercepcionDocumento;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {EmpleadoDeduccionPercepcionDocumento.class})
public interface EmpleadoDeduccionPercepcionDocumentoEditarProjection {

    Integer getId();
    Integer getEmpleadoDeduccionpercepcionId();
    ArchivoProjection getArchivo();
}
