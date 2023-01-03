package com.pixvs.main.models.projections.CXPFactura;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.CXPFactura;
import com.pixvs.main.models.projections.CXPFacturaDetalle.CXPFacturaDetalleEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRelacionarProjection;
import com.pixvs.main.models.projections.OrdenCompraDetalle.OrdenCompraDetalleRelacionarProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorRelacionarProjection;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/09/2020.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaEditarProjection {

    Integer getId();
    String getCodigoRegistro();
    ControlMaestroMultipleComboProjection getTipoRegistro();
    ProveedorRelacionarProjection getProveedor();
    Date getFechaRegistro();
    Date getFechaReciboRegistro();
    MonedaComboProjection getMoneda();
    @Value("#{target.moneda.codigo}")
    String getMonedaCodigo();
    BigDecimal getParidadOficial();
    BigDecimal getParidadUsuario();
    BigDecimal getMontoRegistro();
    Date getFechaPago();
    String getComentarios();
    ControlMaestroMultipleComboProjection getTipoPago();
    String getUuid();
    ControlMaestroMultipleComboProjection getEstatus();
    Integer getDiasCredito();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCancelacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    ArchivoProjection getFacturaPDF();
    Integer getFacturaPDFId();
    ArchivoProjection getFacturaXML();
    Integer getFacturaXMLId();
    default List<CXPFacturaDetalleEditarProjection> getRetenciones(){
        List<CXPFacturaDetalleEditarProjection> retenciones = new ArrayList<>();

        for(CXPFacturaDetalleEditarProjection detalle : getDetalles()){
            if(detalle.getTipoRetencion() != null){
                retenciones.add(detalle);
            }
        }

        return retenciones;
    }

    List<CXPFacturaDetalleEditarProjection> getDetalles();

}
