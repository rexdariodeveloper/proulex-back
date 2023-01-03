package com.pixvs.main.models.projections.Banco;

import com.pixvs.main.models.Banco;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Banco.class})
public interface BancoListadoProjection {

    Integer getId();

    String getCodigo();

    String getRfc();

    String getNombre();

    String getRazonSocial();

    boolean getActivo();
}
