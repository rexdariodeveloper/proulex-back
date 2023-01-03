package com.pixvs.main.models.projections.Empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.EmpleadoBeneficiario.EmpleadoBeneficiarioContratoProjection;
import com.pixvs.main.models.projections.Entidad.EntidadComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisNacionalidadProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Benjamin Osorio 2021/12/03
 * */
@Projection(types = {Empleado.class})
public interface EmpleadoContratoProjection {

    Integer getId();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    String getCodigoEmpleado();

    String getTelefono();

    PaisNacionalidadProjection getPaisNacimiento();

    //@JsonFormat(pattern = "dd 'de' MMMM 'de' YYYY", timezone = "America/Mexico_City")
    Date getFechaNacimiento();

    ControlMaestroMultipleComboProjection getGenero();

    ControlMaestroMultipleComboProjection getEstadoCivil();

    @Value("#{target.domicilio + ', ' +target.colonia + ', ' + target.municipio +   ', '+ target.estado.nombre }")
    String getDomicilio();

    String getRfc();

    String getCurp();

    //Integer getEntidadId();

    List<EmpleadoBeneficiarioContratoProjection> getBeneficiarios();

    ControlMaestroMultipleComboProjection getNacionalidad();

    ControlMaestroMultiple getGradoEstudios();

    //EntidadComboProjection getEntidad();

}
