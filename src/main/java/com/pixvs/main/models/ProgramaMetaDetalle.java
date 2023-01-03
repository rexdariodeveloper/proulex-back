package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 */
@Entity
@Table(name = "ProgramasMetasDetalles")
public class ProgramaMetaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMD_ProgramaMetaDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "PMD_PM_ProgramaMetaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PM_ProgramaMetaId")
    private ProgramaMeta programaMeta;

    @Column(name = "PMD_PM_ProgramaMetaId", nullable = false, insertable = false, updatable = false)
    private Integer programaMetaId;

    @OneToOne
    @JoinColumn(name = "PMD_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PMD_SUC_SucursalId", nullable = false, insertable = true, updatable = true)
    private Integer sucursalId;

    @OneToOne
    @JoinColumn(name = "PMD_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad paModalidad;

    @Column(name = "PMD_PAMOD_ModalidadId", nullable = false, insertable = true, updatable = true)
    private Integer paModalidadId;

    @Column(name = "PMD_FechaInicio", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fechaInicio;

    @Column(name = "PMD_Meta", nullable = false, insertable = true, updatable = true)
    private Integer meta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProgramaMeta getProgramaMeta() {
        return programaMeta;
    }

    public void setProgramaMeta(ProgramaMeta programaMeta) {
        this.programaMeta = programaMeta;
    }

    public Integer getProgramaMetaId() {
        return programaMetaId;
    }

    public void setProgramaMetaId(Integer programaMetaId) {
        this.programaMetaId = programaMetaId;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }
}
