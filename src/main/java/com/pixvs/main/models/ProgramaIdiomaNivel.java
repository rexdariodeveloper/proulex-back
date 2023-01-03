package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasNiveles")
public class ProgramaIdiomaNivel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIN_ProgramaIdiomaNivelId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIN_PROGI_ProgramaIdiomaId", nullable = true, insertable = false, updatable = false)
    private Integer programaIdiomaId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIN_PAMOD_PAModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "PROGIN_PAMOD_PAModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer modalidadId;

    @Column(name = "PROGIN_NivelInicial", nullable = true, insertable = true, updatable = true)
    private Integer nivelInicial;

    @Column(name = "PROGIN_NivelFinal", nullable = true, insertable = true, updatable = true)
    private Integer nivelFinal;

    @Column(name = "PROGIN_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGIE_PROGIN_ProgramaIdiomaNivelId", referencedColumnName = "PROGIN_ProgramaIdiomaNivelId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaExamen> examenes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public PAModalidad getModalidad() { return modalidad; }
    public void setModalidad(PAModalidad modalidad) { this.modalidad = modalidad; }

    public Integer getModalidadId() { return modalidadId; }
    public void setModalidadId(Integer modalidadId) { this.modalidadId = modalidadId; }

    public Integer getNivelInicial() {
        return nivelInicial;
    }

    public void setNivelInicial(Integer nivelInicial) {
        this.nivelInicial = nivelInicial;
    }

    public Integer getNivelFinal() {
        return nivelFinal;
    }

    public void setNivelFinal(Integer nivelFinal) {
        this.nivelFinal = nivelFinal;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<ProgramaIdiomaExamen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<ProgramaIdiomaExamen> examenes) {
        this.examenes = examenes;
    }
}
