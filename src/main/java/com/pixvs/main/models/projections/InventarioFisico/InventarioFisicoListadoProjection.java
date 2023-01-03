package com.pixvs.main.models.projections.InventarioFisico;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;

import java.util.Date;

public interface InventarioFisicoListadoProjection {

    Integer getId();

    String getCodigo();

    LocalidadComboProjection getLocalidad();

    ControlMaestroMultipleComboProjection getEstatus();

    UsuarioComboProjection getCreadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFecha();

    UsuarioComboProjection getAfectadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaAfectacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
