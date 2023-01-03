package com.pixvs.main.models.projections.DatosFacturacion;

import com.pixvs.main.models.DatosFacturacion;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DatosFacturacion.class})
public interface DatosFacturacionComboProjection {

    Integer getId();

    String getRfc();

    String getCodigo();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getRazonSocial();

    Integer getRegimenFiscalId();

    Integer getTipoPersonaId();

    boolean isAlumno();

    boolean isCliente();

    String getValor();
}