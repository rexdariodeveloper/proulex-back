package com.pixvs.main.models.projections.CXPFactura;

import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleVerSolicitudPagoProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorRelacionarProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/09/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaVerSolicitudPagoProjection {

    Integer getId();
    ProveedorRelacionarProjection getProveedor();
    MonedaComboProjection getMoneda();
    Date getFechaRegistro();
    Date getFechaReciboRegistro();
    Date getFechaPago();
    String getCodigoRegistro();
    BigDecimal getMontoRegistro();

    ArchivoProjection getFacturaPDF();
    ArchivoProjection getFacturaXML();

    List<CXPFacturaDetalleVerSolicitudPagoProjection> getDetalles();

}
