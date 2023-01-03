package com.pixvs.main.models.projections.CXPSolicitudPagoServicio;

import com.pixvs.main.models.CXPSolicitudPagoServicio;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicioDetalle.CXPSolicitudPagoServicioDetalleEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPagoServicio.class})
public interface CXPSolicitudPagoServicioEditarProjection {

    Integer getId();
    String getCodigoSolicitudPagoServicio();
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
    String getComentarios();
    List<CXPSolicitudPagoServicioDetalleEditarProjection> getDetalles();

    default ArchivoProjection getFacturaPDF(){
        for (CXPSolicitudPagoServicioDetalleEditarProjection detalle : getDetalles()){
            return detalle.getCxpFactura().getFacturaPDF();
        }
        return null;
    }
    default ArchivoProjection getFacturaXML(){
        for (CXPSolicitudPagoServicioDetalleEditarProjection detalle : getDetalles()){
            return detalle.getCxpFactura().getFacturaXML();
        }
        return null;
    }

}
