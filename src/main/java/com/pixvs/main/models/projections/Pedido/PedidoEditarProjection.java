package com.pixvs.main.models.projections.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Pedido;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.PedidoDetalle.PedidoDetalleEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Pedido.class})
public interface PedidoEditarProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    LocalidadComboProjection getLocalidadOrigen();
    int getLocalidadOrigenId();
    LocalidadComboProjection getLocalidadCEDIS();
    int getLocalidadCEDISId();
    String getComentario();
    ControlMaestroMultipleComboProjection getEstatus();
    int getEstatusId();
    int getCreadoPorId();
    Integer getModificadoPorId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<PedidoDetalleEditarProjection> getDetalles();

}