package com.pixvs.main.models.projections.Proveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;

import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoEditarProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorEditarProjection {


    Integer getId();

    String getCodigo();

    ControlMaestroMultipleComboProjection getTipoProveedor();

    Boolean getActivo();

    String getNombre();

    String getRazonSocial();

    String getRfc();

    String getDomicilio();

    String getColonia();

    PaisComboProjection getPais();

    EstadoComboProjection getEstado();

    String getCiudad();

    String getCp();

    String getTelefono();

    String getExtension();

    String getCorreoElectronico();

    String getPaginaWeb();

    Integer getDiasPlazoCredito();

    BigDecimal getMontoCredito();

    String getDiasPago();

    MonedaComboProjection getMoneda();

    String getCuentaContable();

    List<ProveedorContactoEditarProjection> getContactos();

    List<ProveedorFormaPagoEditarProjection> getFormasPago();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}