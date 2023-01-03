package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.pixvs.main.models.OrdenCompraRecibo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 27/08/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboDocumentosProjection {

    Integer getId();
    Set<ArchivoProjection> getEvidencia();
    ArchivoProjection getFacturaXML();
    ArchivoProjection getFacturaPDF();

}
