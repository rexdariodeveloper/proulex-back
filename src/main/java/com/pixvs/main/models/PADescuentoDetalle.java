package com.pixvs.main.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PADescuentosDetalles")
public class PADescuentoDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PADESCD_DescuentoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PADESCD_PADESC_DescuentoId", nullable = true, insertable = false, updatable = false)
    private Integer descuentoDetalleId;

    @OneToOne
    @JoinColumn(name = "PADESCD_PROG_ProgramaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "PADESCD_PROG_ProgramaId", nullable = true, insertable = true, updatable = true)
    private Integer programaId;

    @OneToOne
    @JoinColumn(name = "PADESCD_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad paModalidad;

    @Column(name = "PADESCD_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer paModalidadId;

    @OneToOne
    @JoinColumn(name = "PADESCD_PAMODH_PAModalidadHorarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMODH_PAModalidadHorarioId")
    private PAModalidadHorario paModalidadHorario;

    @Column(name = "PADESCD_PAMODH_PAModalidadHorarioId", nullable = true, insertable = true, updatable = true)
    private Integer paModalidadHorarioId;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PIDD_PADESCD_DescuentoDetalleId", referencedColumnName = "PADESCD_DescuentoDetalleId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaDescuentoDetalle> cursos;

    @Transient
    private Boolean borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescuentoDetalleId() {
        return descuentoDetalleId;
    }

    public void setDescuentoDetalleId(Integer descuentoDetalleId) {
        this.descuentoDetalleId = descuentoDetalleId;
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

    public PAModalidad getPaModalidad() {
        return paModalidad;
    }

    public void setPaModalidad(PAModalidad paModalidad) {
        this.paModalidad = paModalidad;
    }

    public Integer getPaModalidadId() {
        return paModalidadId;
    }

    public void setPaModalidadId(Integer paModalidadId) {
        this.paModalidadId = paModalidadId;
    }

    public PAModalidadHorario getPaModalidadHorario() {
        return paModalidadHorario;
    }

    public void setPaModalidadHorario(PAModalidadHorario paModalidadHorario) {
        this.paModalidadHorario = paModalidadHorario;
    }

    public Integer getPaModalidadHorarioId() {
        return paModalidadHorarioId;
    }

    public void setPaModalidadHorarioId(Integer paModalidadHorarioId) {
        this.paModalidadHorarioId = paModalidadHorarioId;
    }

    public List<ProgramaIdiomaDescuentoDetalle> getCursos() {
        return cursos;
    }

    public void setCursos(List<ProgramaIdiomaDescuentoDetalle> cursos) {
        this.cursos = cursos;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }
}
