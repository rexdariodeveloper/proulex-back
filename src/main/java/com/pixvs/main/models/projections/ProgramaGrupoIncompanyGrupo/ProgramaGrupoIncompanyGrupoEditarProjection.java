package com.pixvs.main.models.projections.ProgramaGrupoIncompanyGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.ProgramaGrupoIncompanyGrupo;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseCancelada.ProgramaGrupoIncompanyClaseCanceladaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseReprogramada.ProgramaGrupoIncompanyClaseReprogramadaEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyHorario.ProgramaGrupoIncompanyHorarioEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyMaterial.ProgramaGrupoIncompanyMaterialEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupoIncompanyGrupo.class})
public interface ProgramaGrupoIncompanyGrupoEditarProjection {


    Integer getId();

    Integer getProgramaGrupoIncompany();

    ProgramaIdiomaComboProjection getProgramaIdioma();

    String getCodigo();

    String getNombre();

    Integer getNivel();

    String getAlias();

    Boolean getBorrado();

    PAModalidadComboProjection getModalidad();

    ControlMaestroMultipleComboProjection getTipoGrupo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    BigDecimal getCalificacionMinima();

    Integer getFaltasPermitidas();

    Integer getCupo();

    String getHorario();

    ControlMaestroMultipleComboProjection getPlataforma();

    EmpleadoComboProjection getEmpleado();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<ProgramaGrupoIncompanyHorarioEditarProjection> getHorarios();

    List<ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection> getCriteriosEvaluacion();

    List<ProgramaGrupoIncompanyClaseCanceladaEditarProjection> getClasesCanceladas();

    List<ProgramaGrupoIncompanyClaseReprogramadaEditarProjection> getClasesReprogramadas();

    List<ProgramaGrupoIncompanyMaterialEditarProjection> getMateriales();
}