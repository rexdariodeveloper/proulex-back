package com.pixvs.main.models.projections.FormaPago;

import com.pixvs.main.models.FormaPago;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {FormaPago.class})
public interface FormaPagoComboProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    @Value("#{target.codigo + ' - ' + target.nombre}")
    String getValor();
}
