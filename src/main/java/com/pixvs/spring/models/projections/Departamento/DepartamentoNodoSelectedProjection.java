package com.pixvs.spring.models.projections.Departamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Departamento;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 06/08/2020.
 */
@Projection(types = {Departamento.class})
public interface DepartamentoNodoSelectedProjection {

    Integer getId();
    String getPrefijo();
    String getNombre();
    UsuarioComboProjection getResponsable();
    DepartamentoComboProjection getDepartamentoPadre();
    Boolean getActivo();
    Boolean getAutoriza();
    List<UsuarioComboProjection> getUsuariosPermisos();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    @Value("#{target}")
    DepartamentoComboProjection getInfo();
    @Value("#{target.departamentos}")
    List<DepartamentoNodoSelectedProjection> getChildren();
    @Value("#{false}")
    Boolean getOcultarSeleccion();
    @Value("#{true}")
    Boolean getOcultarAcciones();
    @Value("#{false}")
    Boolean getSelected();

}
