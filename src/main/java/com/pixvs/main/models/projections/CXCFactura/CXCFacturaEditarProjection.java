package com.pixvs.main.models.projections.CXCFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.projections.CXCFacturaDetalle.CXCFacturaDetalleEditarProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@Projection(types = {CXCFactura.class})
public interface CXCFacturaEditarProjection {

    Integer getId();
    String getCodigoRegistro();
    ControlMaestroMultipleComboProjection getTipoRegistro();
    ClienteComboProjection getCliente();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaRegistro();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaEmbarque();
    MonedaComboProjection getMoneda();
    BigDecimal getParidadOficial();
    BigDecimal getParidadUsuario();
    BigDecimal getMontoRegistro();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaPago();
    String getComentarios();
    ControlMaestroMultipleComboProjection getTipoPago();
    ControlMaestroMultipleComboProjection getMetodoPago();
    ControlMaestroMultipleComboProjection getUsoCFDI();
    String getUuid();
    ControlMaestroMultipleComboProjection getEstatus();
    ArchivoProjection getFacturaXML();
    ArchivoProjection getFacturaPDF();
    Integer getDiasCredito();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCancelacion();
    String getMotivoCancelacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<CXCFacturaDetalleEditarProjection> getDetalles();

}
