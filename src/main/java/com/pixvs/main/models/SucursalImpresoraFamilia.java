package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="SucursalImpresoraFamilia")
public class SucursalImpresoraFamilia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIMF_SucursalImpresoraFamiliaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SIMF_SucursalId", nullable = false)
    private Integer sucursalId;

    @Column(name = "SIMF_FamiliaId", nullable = false, updatable = false)
    private Integer familiaId;

    @OneToOne
    @JoinColumn(name = "SIMF_FamiliaId", referencedColumnName = "AFAM_FamiliaId", insertable = false, updatable = false)
    private ArticuloFamilia familia;

    @Column(name = "SIMF_CMM_TipoImpresora", nullable = false, insertable = true, updatable = true)
    private Integer tipoImpresoraId;

    @OneToOne
    @JoinColumn(name = "SIMF_CMM_TipoImpresora", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple  tipoImpresora;

    @Column(name = "SIMF_IP", length = 15, nullable = true, insertable = true, updatable = true)
    private String ip;

    @Column(name = "SIMF_Activo", nullable = false,insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "SIMF_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "SIMF_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "SIMF_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "SIMF_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "SIMF_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "SIMF_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;


    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Integer getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(Integer familiaId) {
        this.familiaId = familiaId;
    }

    public ArticuloFamilia getFamilia() {
        return familia;
    }

    public void setFamilia(ArticuloFamilia familia) {
        this.familia = familia;
    }

    public Integer getTipoImpresoraId() {
        return tipoImpresoraId;
    }

    public void setTipoImpresoraId(Integer tipoImpresoraId) {
        this.tipoImpresoraId = tipoImpresoraId;
    }

    public ControlMaestroMultiple getTipoImpresora() {
        return tipoImpresora;
    }

    public void setTipoImpresora(ControlMaestroMultiple tipoImpresora) {
        this.tipoImpresora = tipoImpresora;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
