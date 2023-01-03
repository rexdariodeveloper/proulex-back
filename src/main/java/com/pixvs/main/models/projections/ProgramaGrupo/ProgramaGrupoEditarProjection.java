package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoEditarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseCancelada.ProgramaGrupoIncompanyClaseCanceladaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseReprogramada.ProgramaGrupoIncompanyClaseReprogramadaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyComision.ProgramaGrupoIncompanyComisionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyHorario.ProgramaGrupoIncompanyHorarioEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyMaterial.ProgramaGrupoIncompanyMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoListadoClase.ProgramaGrupoListadoClaseEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoProfesor.ProgramaGrupoProfesorListadoGrupoProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoEditarProjection {


    Integer getId();

    @Value("#{(target.codigoGrupo ) }")
    String getCodigo();

    SucursalComboProjection getSucursal();

    ProgramaIdiomaComboProjection getProgramaIdioma();

    PAModalidadComboProjection getPaModalidad();

    ProgramacionAcademicaComercialComboProjection getProgramacionAcademicaComercial();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFinInscripciones();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFinInscripcionesBecas();

    Integer getNivel();

    Integer getGrupo();

    ControlMaestroMultipleComboProjection getPlataforma();

    PAModalidadHorarioComboProjection getModalidadHorario();

    PACicloComboProjection getPaCiclo();

    ControlMaestroMultipleComboProjection getTipoGrupo();

    Integer getCupo();

    EmpleadoComboProjection getEmpleado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    List<ProgramaGrupoListadoClaseEditarProjection> getListadoClases();

    UsuarioComboProjection getCreadoPor();

    SucursalPlantelComboProjection getSucursalPlantel();

    Boolean getMultisede();

    Integer getCalificacionMinima();

    BigDecimal getFaltasPermitidas();

    String getCategoriaProfesor();

    BigDecimal getSueldoProfesor();

    String getAula();

    String getComentarios();

    ControlMaestroMultipleComboProjection getEstatus();

    List<ProgramaGrupoProfesorListadoGrupoProjection> getProfesoresTitulares();

    List<ProgramaGrupoIncompanyHorarioEditarProjection> getHorarios();

    List<ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection> getCriteriosEvaluacion();

    List<ProgramaGrupoIncompanyClaseCanceladaEditarProjection> getClasesCanceladas();

    List<ProgramaGrupoIncompanyClaseReprogramadaEditarProjection> getClasesReprogramadas();

    List<ProgramaGrupoIncompanyMaterialEditarProjection> getMateriales();

    List<ProgramaGrupoIncompanyComisionEditarProjection> getListaComision();

    String getNombre();

    String getAlias();

    PrecioIncompanyComboProjection getPrecioIncompany();

    BigDecimal getPrecioVentaGrupo();

    BigDecimal getPrecioVentaCurso();

    BigDecimal getPrecioVentaLibro();

    BigDecimal getPrecioVentaCertificacion();

    BigDecimal getPorcentajeComision();

    BigDecimal getPorcentajeApoyoTransporte();

    BigDecimal getKilometrosDistancia();

    BigDecimal getPrecioMaterial();

    List<ProgramaGrupoEvidenciaProjection> getEvidencias();

    BigDecimal getClientePrecioVentaCurso();

    BigDecimal getClientePrecioVentaLibro();

    BigDecimal getClientePrecioVentaCertificacion();
}