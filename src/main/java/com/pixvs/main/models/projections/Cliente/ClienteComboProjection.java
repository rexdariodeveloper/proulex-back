package com.pixvs.main.models.projections.Cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Cliente;
import com.pixvs.main.models.projections.ClienteContacto.ClienteContactoEditarProjection;
import com.pixvs.main.models.projections.ClienteCuentaBancaria.ClienteCuentaBancariaEditarProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Cliente.class})
public interface ClienteComboProjection {

    Integer getId();
    @Value("#{target.codigo + '-' +target.nombre }")
    String getNombre();
    String getRFC();

}