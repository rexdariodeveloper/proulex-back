package com.pixvs.main.models.projections.Localidad;

import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Almacen.AlmacenComboResponsableProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Localidad.class})
public interface LocalidadComboProjection {

    Integer getId();

    String getCodigoLocalidad();

    String getNombre();

    Boolean getLocalidadGeneral();

    Boolean getActivo();

    AlmacenComboResponsableProjection getAlmacen();

    @Value("#{ target.almacen.nombre + (target.localidadGeneral == false ? ' - ' + target.nombre : '') }")
    String getAlmacenLocalidad();
}
