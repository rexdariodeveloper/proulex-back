package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 14/08/2021.
 */
@Entity
@Table(name = "BecasSolicitudes")
public class BecaSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BECS_BecaSolicitudId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "BECS_Codigo", length = 20, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECS_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "BECS_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "BECS_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "BECS_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "BECS_USU_CreadoPorId", updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECS_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "BECS_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECS_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_BECS_BecaSolicitudId", referencedColumnName = "BECS_BecaSolicitudId", nullable = false)
    private List<BecaUDG> becas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public List<BecaUDG> getBecas() {
        return becas;
    }

    public void setBecas(List<BecaUDG> becas) {
        this.becas = becas;
    }
}
