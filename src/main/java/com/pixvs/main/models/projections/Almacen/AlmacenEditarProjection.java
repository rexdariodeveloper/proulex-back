package com.pixvs.main.models.projections.Almacen;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadEditarProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Almacen.class})
public interface AlmacenEditarProjection {

    Integer getId();
    String getCodigoAlmacen();
    String getNombre();
    SucursalComboProjection getSucursal();
    ClienteComboProjection getCliente();
    Integer getSucursalId();
    UsuarioComboProjection getResponsable();
    Integer getResponsableId();
    Boolean getMismaDireccionSucursal();
    Boolean getMismaDireccionCliente();
    String getDomicilio();
    String getColonia();
    PaisComboProjection getPais();
    Integer getPaisId();
    EstadoComboProjection getEstado();
    Integer getEstadoId();
    String getCiudad();
    String getCp();
    String getTelefono();
    String getExtension();
    Boolean getPredeterminado();
    Boolean getEsCedi();
    boolean isLocalidadesBandera();
    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<LocalidadEditarProjection> getLocalidades();
}
