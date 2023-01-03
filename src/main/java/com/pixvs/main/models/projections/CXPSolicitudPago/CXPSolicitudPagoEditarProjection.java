package com.pixvs.main.models.projections.CXPSolicitudPago;

import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPago.class})
public interface CXPSolicitudPagoEditarProjection {

    Integer getId();
    String getCodigoSolicitud();
    ControlMaestroMultipleComboProjection getEstatus();
    Integer getEstatusId();
    Date getFechaCreacion();
    UsuarioComboProjection getCreadoPor();
    Integer getCreadoPorId();
    UsuarioComboProjection getModificadoPor();
    Integer getModificadoPorId();
    Date getFechaModificacion();
    SucursalComboProjection getSucursal();
    Integer getSucursalId();
    List<CXPSolicitudPagoDetalleEditarProjection> getDetalles();

}
