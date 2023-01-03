package com.pixvs.main.models.projections.Transferencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;

import java.util.Date;

public interface TransferenciaListadoProjection {

    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFecha();

    String getCodigo();

    LocalidadComboProjection getLocalidadOrigen();

    LocalidadComboProjection getLocalidadDestino();

    ControlMaestroMultipleComboProjection getEstatus();

    UsuarioComboProjection getCreadoPor();

    String getComentario();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
