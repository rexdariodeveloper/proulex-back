package com.pixvs.main.models.projections.CuentaBancaria;

import com.pixvs.main.models.CuentaBancaria;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CuentaBancaria.class})
public interface CuentaBancariaListadoProjection {

    Integer getId();
    String getCodigo();
    String getDescripcion();
    MonedaComboProjection getMoneda();
    BancoComboProjection getBanco();
    boolean isActivo();

}
