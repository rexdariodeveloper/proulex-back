package com.pixvs.main.models.projections.Proveedor;

import com.pixvs.main.models.Proveedor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Proveedor.class})
public interface ProveedorRelacionarProjection {

    Integer getId();
    String getNombre();
    String getCodigo();
    String getRfc();

    @Value("#{target.diasPlazoCredito}")
    Integer getDiasVencimiento();

}
