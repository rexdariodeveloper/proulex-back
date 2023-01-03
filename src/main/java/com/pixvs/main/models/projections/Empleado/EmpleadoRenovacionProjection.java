package com.pixvs.main.models.projections.Empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.projections.EmpleadoBeneficiario.EmpleadoBeneficiarioEditarProjection;
import com.pixvs.main.models.projections.EmpleadoCategoria.EmpleadoCategoriaEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoCurso.EmpleadoCursoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoDatoSalud.EmpleadoDatoSaludEditarProjection;
import com.pixvs.main.models.projections.EmpleadoDocumento.EmpleadoDocumentoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoHorario.EmpleadoHorarioEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;

import java.util.Date;
import java.util.List;

public interface EmpleadoRenovacionProjection {
    Integer getId();
}
