package com.pixvs.main.models.projections.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Pedido;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Pedido.class})
public interface PedidoListadoRecibirProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    String getLocalidadOrigenNombre();
    String getLocalidadCEDISNombre();
    String getComentario();
    int getEstatusId();
    String getEstatusValor();

}
