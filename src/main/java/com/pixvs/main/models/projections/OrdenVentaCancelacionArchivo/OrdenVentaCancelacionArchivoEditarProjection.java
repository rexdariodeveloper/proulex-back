package com.pixvs.main.models.projections.OrdenVentaCancelacionArchivo;

import com.pixvs.main.models.OrdenVentaCancelacionArchivo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 12/04/2022.
 */
@Projection(types = {OrdenVentaCancelacionArchivo.class})
public interface OrdenVentaCancelacionArchivoEditarProjection {

    Integer getId();
    ArchivoProjection getArchivo();
    ControlMaestroMultipleComboSimpleProjection getTipoDocumento();
    String getValor();

}
