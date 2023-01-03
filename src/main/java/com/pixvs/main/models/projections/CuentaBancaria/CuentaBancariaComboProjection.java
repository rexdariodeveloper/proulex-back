package com.pixvs.main.models.projections.CuentaBancaria;

import com.pixvs.main.models.CuentaBancaria;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CuentaBancaria.class})
public interface CuentaBancariaComboProjection {

    Integer getId();

    String getCodigo();

    String getDescripcion();

    Integer getMonedaId();

    Integer getBancoId();

    BancoComboProjection getBanco();

    String getClabe();
}