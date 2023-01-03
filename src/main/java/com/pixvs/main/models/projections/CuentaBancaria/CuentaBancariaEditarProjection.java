package com.pixvs.main.models.projections.CuentaBancaria;

import com.pixvs.main.models.CuentaBancaria;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {CuentaBancaria.class})
public interface CuentaBancariaEditarProjection {

    Integer getId();
    String getCodigo();
    String getDescripcion();
    MonedaComboProjection getMoneda();
    Integer getMonedaId();
    BancoComboProjection getBanco();
    Integer getBancoId();
    String getConvenio();
    String getClabe();
    boolean isActivo();
    Integer getCreadoPorId();
    Integer getModificadoPorId();
    Date getFechaCreacion();
    Date getFechaModificacion();

}
