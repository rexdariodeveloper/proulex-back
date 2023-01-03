package com.pixvs.main.models.projections.EntidadContrato;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Entidad.EntidadComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Entidad.class})
public interface EntidadContratoEditarProjection {


    Integer getId();

    Integer getEntidadId();

    ControlMaestroMultipleComboProjection getTipoContrato();

    ArchivoProjection getDocumentoContrato();

    ArchivoProjection getAdendumContrato();

    Boolean getActivo();
}