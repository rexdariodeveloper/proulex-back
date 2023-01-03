package com.pixvs.main.models;

import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelComboProjection;
import com.pixvs.spring.models.ControlMaestroMultiple;

import java.util.Date;
import java.util.List;

public class JsonGruposMultiple {

    private Integer total;
    private PAModalidadHorarioComboProjection[] modalidadHorario;
    private Integer nivel;
    private SucursalPlantelComboProjection[] sucursalPlantel;
    private ControlMaestroMultiple tipoGrupo;
    private Date fechaInicio;
    private List<ProgramaGrupoIncompanyHorario> horarios;

}
