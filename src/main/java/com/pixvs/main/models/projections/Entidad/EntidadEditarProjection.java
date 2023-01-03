package com.pixvs.main.models.projections.Entidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.EmpleadoCategoria.EmpleadoCategoriaEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoCurso.EmpleadoCursoEditarProjection;
import com.pixvs.main.models.projections.EntidadContrato.EntidadContratoEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Entidad.class})
public interface EntidadEditarProjection {


    Integer getId();

    String getCodigo();

    String getPrefijo();

    String getNombre();

    String getRazonSocial();

    String getNombreComercial();

    EmpleadoComboProjection getDirector();

    EmpleadoComboProjection getCoordinador();

    EmpleadoComboProjection getJefeUnidadAF();

    String getDomicilio();

    String getColonia();

    String getCp();

    PaisComboProjection getPais();

    EstadoComboProjection getEstado();

    String getCiudad();

    EntidadComboProjection getEntidadIndependiente();

    Boolean getActivo();

    Boolean getAplicaSedes();

    List<EntidadContratoEditarProjection> getContratos();
}