package com.pixvs.main.models.projections.ClienteRemision;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ClienteRemision;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.ClienteRemisionDetalle.ClienteRemisionDetalleEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 14/06/2021.
 */
@Projection(types = {ClienteRemision.class})
public interface ClienteRemisionEditarProjection {

    Integer getId();
    String getCodigo();
    ClienteComboProjection getCliente();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFecha();
    MonedaComboProjection getMoneda();
    AlmacenComboProjection getAlmacenOrigen();
    AlmacenComboProjection getAlmacenDestino();
    String getComentario();
    ControlMaestroMultipleComboProjection getEstatus();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<ClienteRemisionDetalleEditarProjection> getDetalles();

}
