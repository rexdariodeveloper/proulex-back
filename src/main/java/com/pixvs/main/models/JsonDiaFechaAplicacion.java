package com.pixvs.main.models;

import java.util.Date;
import java.util.List;

public class JsonDiaFechaAplicacion {

    private Integer id;
    private Integer programaIdiomaId;
    private Integer modalidadId;
    private Integer nivel;
    private Date fechaInicio;
    private List<ProgramaGrupoIncompanyHorario> horarios;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public List<ProgramaGrupoIncompanyHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<ProgramaGrupoIncompanyHorario> horarios) {
        this.horarios = horarios;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}
