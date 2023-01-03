package com.pixvs.main.models.projections.Usuario;

import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {Usuario.class})
public interface UsuarioDatosAdicionalesProjection {

    Integer getId();
    List<AlmacenComboProjection> getAlmacenes();
    List<DepartamentoComboProjection> getDepartamentosPermisos();

}
