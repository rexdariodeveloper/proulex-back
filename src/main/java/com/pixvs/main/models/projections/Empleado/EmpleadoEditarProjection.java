package com.pixvs.main.models.projections.Empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
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
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Empleado.class})
public interface EmpleadoEditarProjection {


    Integer getId();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    ControlMaestroMultipleComboProjection getEstadoCivil();

    ControlMaestroMultipleComboProjection getGenero();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaNacimiento();

    PaisComboProjection getPaisNacimiento();

    EstadoComboProjection getEstadoNacimiento();

    String getRfc();

    String getCurp();

    String getCorreoElectronico();

    Integer getFotoId();

    String getCodigoEmpleado();

    DepartamentoComboProjection getDepartamento();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaAlta();

    ControlMaestroMultipleComboProjection getTipoEmpleado();

    SucursalComboProjection getSucursal();

    String getDomicilio();

    String getColonia();

    String getCp();

    PaisComboProjection getPais();

    EstadoComboProjection getEstado();

    String getMunicipio();

    ControlMaestroMultipleComboProjection getGradoEstudios();

    ControlMaestroMultipleComboProjection getNacionalidad();

    List<EmpleadoDatoSaludEditarProjection> getDatosSalud();

    UsuarioEditarProjection getUsuario();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaUltimaModificacion();

    List<EmpleadoContactoEditarProjection> getContactos();

    List<EmpleadoCursoEditarProjection> getCursos();

    List<EmpleadoCategoriaEditarProjection> getCategorias();

    List<EmpleadoBeneficiarioEditarProjection> getBeneficiarios();

    Integer getEstatusId();

    String getTelefonoContacto();

    String getTelefonoMovil();

    String getTelefonoTrabajo();

    String getTelefonoTrabajoExtension();

    String getTelefonoMensajeriaInstantanea();

    List<EmpleadoDocumentoEditarProjection> getListaEmpleadoDocumento();

    EmpleadoContratoEditarProjection getEmpleadoContrato();

    List<EmpleadoHorarioEditarProjection> getListaEmpleadoHorario();
}