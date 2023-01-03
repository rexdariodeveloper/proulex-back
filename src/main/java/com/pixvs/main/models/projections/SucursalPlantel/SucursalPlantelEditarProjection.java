package com.pixvs.main.models.projections.SucursalPlantel;

import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.SucursalPlantel;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.MedioPagoPV.MedioPagoPVComboProjection;

import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Projection(types = {SucursalPlantel.class})
public interface SucursalPlantelEditarProjection {

    Integer getId();
    Integer getSucursalId();
    String getCodigoSucursal();
    String getNombre();
    UsuarioComboProjection getResponsable();
    AlmacenComboProjection getAlmacen();
    LocalidadComboProjection getLocalidad();
    String getDireccion();
    String getCp();
    String getColonia();
    PaisComboProjection getPais();
    EstadoComboProjection getEstado();
    String getMunicipio();
    String getCorreoElectronico();
    String getTelefonoFijo();
    String getTelefonoMovil();
    String getTelefonoTrabajo();
    String getTelefonoTrabajoExtension();
    String getTelefonoMensajeriaInstantanea();
    Boolean getActivo();
}