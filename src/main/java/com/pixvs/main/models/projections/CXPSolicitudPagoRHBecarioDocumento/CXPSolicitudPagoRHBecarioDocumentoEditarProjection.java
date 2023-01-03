package com.pixvs.main.models.projections.CXPSolicitudPagoRHBecarioDocumento;

import com.pixvs.main.models.CXPSolicitudPagoRHBecarioDocumento;
import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidadDetalle;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXPSolicitudPagoRHBecarioDocumento.class})
public interface CXPSolicitudPagoRHBecarioDocumentoEditarProjection {

    Integer getId();
    Integer getCpxSolicitudPagoRhId();
    ArchivoProjection getArchivo();
}
