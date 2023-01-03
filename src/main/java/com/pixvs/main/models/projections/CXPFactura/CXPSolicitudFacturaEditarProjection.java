package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPSolicitudFacturaDetalleEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorRelacionarProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXPFactura.class})
public interface CXPSolicitudFacturaEditarProjection {

    Integer getId();
    String getCodigoRegistro();
    ControlMaestroMultipleComboProjection getTipoRegistro();
    Integer getTipoRegistroId();
    ProveedorRelacionarProjection getProveedor();
    Integer getProveedorId();
    Date getFechaRegistro();
    Date getFechaReciboRegistro();
    MonedaComboProjection getMoneda();
    Integer getMonedaId();
    @Value("#{target.moneda.codigo}")
    String getMonedaCodigo();
    BigDecimal getParidadOficial();
    BigDecimal getParidadUsuario();
    Integer getDiasCredito();
    //String getRemitirA();
    BigDecimal getMontoRegistro();
    Date getFechaPago();
    String getComentarios();
    ControlMaestroMultipleComboProjection getTipoPago();
    Integer getTipoPagoId();
    String getUuid();
    ControlMaestroMultipleComboProjection getEstatus();
    Integer getEstatusId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    ArchivoProjection getFacturaPDF();
    Integer getFacturaPDFId();
    ArchivoProjection getFacturaXML();
    Integer getFacturaXMLId();
    List<CXPSolicitudFacturaDetalleEditarProjection> getDetalles();

}
