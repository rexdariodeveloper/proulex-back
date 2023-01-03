package com.pixvs.main.models.projections.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Temporada;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Temporada.class})
public interface PedidoListadoProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    LocalidadComboProjection getLocalidadOrigen();
    LocalidadComboProjection getLocalidadCEDIS();
    String getComentario();
    int getEstatusId();
    ControlMaestroMultipleComboProjection getEstatus();

}