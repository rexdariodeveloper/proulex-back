package com.pixvs.main.models.projections.Cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Cliente;
import com.pixvs.main.models.projections.Almacen.AlmacenEditarProjection;
import com.pixvs.main.models.projections.ClienteContacto.ClienteContactoEditarProjection;
import com.pixvs.main.models.projections.ClienteCuentaBancaria.ClienteCuentaBancariaEditarProjection;
import com.pixvs.main.models.projections.ClienteDatosFacturacion.ClienteDatosFacturacionEditarProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Cliente.class})
public interface ClienteEditarProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    String getRFC();

    String getRazonSocial();

    String getDomicilio();

    String getColonia();

    int getPaisId();

    PaisComboProjection getPais();

    int getEstadoId();

    EstadoComboProjection getEstado();

    ListadoPrecioComboProjection getListadoPrecio();

    String getCiudad();

    String getCp();

    String getTelefono();

    String getExtension();

    String getCorreoElectronico();

    String getPaginaWeb();

    int getFormaPagoId();

    FormaPagoComboProjection getFormaPago();

    String getComentarios();

    Integer getMonedaId();

    MonedaComboProjection getMoneda();

    String getCuentaCXC();

    String getMontoCredito();

    String getDiasCobro();

    boolean isActivo();

    Boolean getConsignacion();

    List<ClienteContactoEditarProjection> getContactos();

    List<ClienteCuentaBancariaEditarProjection> getCuentasBancarias();

    List<AlmacenEditarProjection> getAlmacenesConsignacion();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ClienteDatosFacturacionEditarProjection> getFacturacion();
}