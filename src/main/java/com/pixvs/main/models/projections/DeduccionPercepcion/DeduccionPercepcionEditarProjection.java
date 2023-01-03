package com.pixvs.main.models.projections.DeduccionPercepcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.DeduccionPercepcion;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.projections.EmpleadoCategoria.EmpleadoCategoriaEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoCurso.EmpleadoCursoEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.Tabulador.TabuladorComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {DeduccionPercepcion.class})
public interface DeduccionPercepcionEditarProjection {


    Integer getId();

    String getCodigo();

    ControlMaestroMultipleComboProjection getTipo();

    String getConcepto();

    TabuladorComboProjection getTabulador();

    BigDecimal getPorcentaje();

    Boolean getActivo();
}