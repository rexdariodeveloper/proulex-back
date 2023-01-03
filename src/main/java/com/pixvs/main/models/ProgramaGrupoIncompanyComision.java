package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Rene Carrillo on 09/11/2022.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyComisiones")
public class ProgramaGrupoIncompanyComision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCC_ProgramaGrupoIncompanyComisionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCC_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private Integer grupoId;

    @Column(name = "PGINCC_USU_UsuarioId", nullable = false, insertable = true, updatable = true)
    private Integer usuarioId;

    @OneToOne
    @JoinColumn(name = "PGINCC_USU_UsuarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario usuario;

    @Column(name = "PGINCC_Porcentaje", nullable = false, insertable = true, updatable = true)
    private BigDecimal porcentaje;

    @Column(name = "PGINCC_MontoComision", nullable = false, insertable = true, updatable = true)
    private BigDecimal montoComision;

    @Column(name = "PGINCC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "PGINCC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "PGINCC_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @Column(name = "PGINCC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PGINCC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
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

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
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
}
