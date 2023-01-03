package com.pixvs.main.models.projections.ClienteCuentaBancaria;

import com.pixvs.main.models.ClienteCuentaBancaria;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ClienteCuentaBancaria.class})
public interface ClienteCuentaBancariaComboProjection {

    Integer getId();

    int getClienteId();

    int getBancoId();

    BancoComboProjection getBanco();

    String getCuenta();

    boolean isActivo();
}