package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 07/05/2021.
 */
@Entity
@Table(name = "ProgramasMetas")
public class ProgramaMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PM_ProgramaMetaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PM_Activo", nullable = false)
    private Boolean activo;

    @Column(name = "PM_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(name = "PM_PAC_ProgramacionAcademicaComercialId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAC_ProgramacionAcademicaComercialId")
    private ProgramacionAcademicaComercial programacionAcademicaComercial;

    @Column(name = "PM_PAC_ProgramacionAcademicaComercialId", nullable = false, insertable = true, updatable = true)
    private Integer programacionAcademicaComercialId;

    @CreationTimestamp
    @Column(name = "PM_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PM_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PM_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="PMD_PM_ProgramaMetaId", referencedColumnName = "PM_ProgramaMetaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaMetaDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ProgramacionAcademicaComercial getProgramacionAcademicaComercial() {
        return programacionAcademicaComercial;
    }

    public void setProgramacionAcademicaComercial(ProgramacionAcademicaComercial programacionAcademicaComercial) {
        this.programacionAcademicaComercial = programacionAcademicaComercial;
    }

    public Integer getProgramacionAcademicaComercialId() {
        return programacionAcademicaComercialId;
    }

    public void setProgramacionAcademicaComercialId(Integer programacionAcademicaComercialId) {
        this.programacionAcademicaComercialId = programacionAcademicaComercialId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<ProgramaMetaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ProgramaMetaDetalle> detalles) {
        this.detalles = detalles;
    }
}
