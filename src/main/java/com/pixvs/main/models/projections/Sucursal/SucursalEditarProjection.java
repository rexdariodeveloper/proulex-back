package com.pixvs.main.models.projections.Sucursal;

import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaComboProjection;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import com.pixvs.main.models.projections.MedioPagoPV.MedioPagoPVComboProjection;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelEditarProjection;
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
@Projection(types = {Sucursal.class})
public interface SucursalEditarProjection {

    Integer getId();

    String getCodigoSucursal();

    String getNombre();

    String getPrefijo();

    String getSerie();

    Integer getResponsableId();

    UsuarioComboProjection getResponsable();

    UsuarioComboProjection getCoordinador();

    String getDomicilio();

    String getColonia();

    Integer getPaisId();

    PaisComboProjection getPais();

    Integer getEstadoId();

    EstadoComboProjection getEstado();

    String getCiudad();

    String getCp();

    String getTelefono();

    String getExtension();

    BigDecimal getPorcentajeComision();

    BigDecimal getPresupuestoSemanal();

    Boolean getPredeterminada();

    Boolean getActivo();

    Date getFechaModificacion();

    Boolean getMostrarRed();

    String getNombreRed();

    String getContraseniaRed();

    ControlMaestroMultipleComboProjection getTipoSucursal();

    Boolean getLunes();

    Boolean getMartes();

    Boolean getMiercoles();

    Boolean getJueves();

    Boolean getViernes();

    Boolean getSabado();

    Boolean getDomingo();

    Boolean getPlantelesBandera();

    ListadoPrecioComboProjection getListadoPrecio();

    CuentaBancariaComboProjection getCuentaBancaria();

    int getTipoFacturaGlobalId();

    ControlMaestroMultipleComboProjection getTipoFacturaGlobal();

    List<MedioPagoPVComboProjection> getMediosPagoPV();

    List<AlmacenComboProjection> getAlmacenesHijos();

    List<SucursalPlantelEditarProjection> getPlanteles();
}