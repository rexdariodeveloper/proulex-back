package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "TabuladoresCursos")
public class TabuladorCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TABC_TabuladorCursoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TABC_TAB_TabuladorId", nullable = false, insertable = false, updatable = false)
    private Integer tabuladorId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABC_PROG_ProgramaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "TABC_PROG_ProgramaId", nullable = false, insertable = true, updatable = true)
    private Integer programaId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABC_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "TABC_PAMOD_ModalidadId", nullable = false, insertable = true, updatable = true)
    private Integer modalidadId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABC_PAMODH_PAModalidadHorarioId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMODH_PAModalidadHorarioId")
    private PAModalidadHorario modalidadHorario;

    @Column(name = "TABC_PAMODH_PAModalidadHorarioId", nullable = false, insertable = true, updatable = true)
    private Integer modalidadHorarioId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABC_PROGI_ProgramaIdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma programaIdioma;

    @Column(name = "TABC_PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer programaIdiomaId;

    @Column(name = "TABC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTabuladorId() {
        return tabuladorId;
    }

    public void setTabuladorId(Integer tabuladorId) {
        this.tabuladorId = tabuladorId;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public PAModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(PAModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public PAModalidadHorario getModalidadHorario() {
        return modalidadHorario;
    }

    public void setModalidadHorario(PAModalidadHorario modalidadHorario) {
        this.modalidadHorario = modalidadHorario;
    }

    public Integer getModalidadHorarioId() {
        return modalidadHorarioId;
    }

    public void setModalidadHorarioId(Integer modalidadHorarioId) {
        this.modalidadHorarioId = modalidadHorarioId;
    }

    public ProgramaIdioma getProgramaIdioma() {
        return programaIdioma;
    }

    public void setProgramaIdioma(ProgramaIdioma programaIdioma) {
        this.programaIdioma = programaIdioma;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
