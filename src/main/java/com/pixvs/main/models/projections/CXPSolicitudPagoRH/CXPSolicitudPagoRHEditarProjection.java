package com.pixvs.main.models.projections.CXPSolicitudPagoRH;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.CXPSolicitudPagoRHBecarioDocumento;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHBecarioDocumento.CXPSolicitudPagoRHBecarioDocumentoEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidad.CXPSolicitudPagoRHIncapacidadEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHPensionAlimenticia.CXPSolicitudPagoRHPensionAlimenticiaEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHRetiroCajaAhorro.CXPSolicitudPagoRHRetiroCajaAhorroEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPagoRH.class})
public interface CXPSolicitudPagoRHEditarProjection {

    Integer getId();
    String getCodigo();
    EmpleadoComboProjection getEmpleado();
    SucursalComboProjection getSucursal();
    ControlMaestroMultipleComboProjection getTipoPago();
    BigDecimal getMonto();
    String getComentarios();
    ControlMaestroMultipleComboProjection getEstatus();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<CXPSolicitudPagoRHBecarioDocumentoEditarProjection> getBecarioDocumentos();
    List<CXPSolicitudPagoRHIncapacidadEditarProjection> getIncapacidad();
    List<CXPSolicitudPagoRHRetiroCajaAhorroEditarProjection> getCajaAhorro();
    //CXPFacturaEditarProjection getFactura();
    List<CXPSolicitudPagoRHPensionAlimenticiaEditarProjection> getPensionAlimenticia();
}
