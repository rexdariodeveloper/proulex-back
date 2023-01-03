package com.pixvs.spring.models.projections.Alerta;

import com.pixvs.spring.models.Alerta;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(name = "alertaProjection", types = {Alerta.class})
public interface AlertaSimpleProjection {

    Integer getId();
    AlertaConfigProjection getConfig();
    Date getFechaAutorizacion();
    Date getFechaInicio();
    Integer getReferenciaProcesoId();
    String getCodigoTramite();
    String getTextoRepresentativo();
    String getOrigen();
    ControlMaestroMultipleComboSimpleProjection getEstatusAlerta();
    Integer getCreadoPorId();
    Date getFechaUltimaModificacion();
    UsuarioEditarProjection getCreadoPor();
    @Value("#{target.alertaConfigEtapa.mostrarUsuario}")
    Boolean getMostrarUsuario();
}
