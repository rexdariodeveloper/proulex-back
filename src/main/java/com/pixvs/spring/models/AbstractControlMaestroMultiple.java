package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractControlMaestroMultiple {

    @Id
    @Column(name = "CMM_ControlId", nullable = false, updatable = false, insertable = true)
    private Integer id;

    @Column(name = "CMM_Control", nullable = false)
    private String control;

    @Column(name = "CMM_Valor", nullable = false)
    private String valor;

    @Column(name = "CMM_Referencia")
    private String referencia;

    @Column(name = "CMM_Sistema")
    private boolean sistema;

    @Column(name = "CMM_Activo")
    private boolean activo;

    @Column(name = "CMM_Orden", updatable = false, insertable = false)
    private Integer orden;

    @OneToOne
    @JoinColumn(name = "CMM_ARC_ImagenId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo imagen;

    @Column(name = "CMM_ARC_ImagenId", updatable = false, insertable = false)
    private Integer imagenId;

    @OneToOne
    @JoinColumn(name = "CMM_CMM_ReferenciaId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple cmmReferencia;

    @Column(name = "CMM_CMM_ReferenciaId")
    private Integer cmmReferenciaId;

    @CreationTimestamp
    @Column(name = "CMM_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "CMM_USU_CreadoPorId")
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CMM_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @UpdateTimestamp
    @Column(name = "CMM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fechaModificacion;

    @Column(name = "CMM_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "CMM_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    public AbstractControlMaestroMultiple() {
    }

    public AbstractControlMaestroMultiple(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isSistema() {
        return sistema;
    }

    public void setSistema(boolean sistema) {
        this.sistema = sistema;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
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

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Archivo getImagen() {
        return imagen;
    }

    public void setImagen(Archivo imagen) {
        this.imagen = imagen;
    }

    public Integer getImagenId() {
        return imagenId;
    }

    public void setImagenId(Integer imagenId) {
        this.imagenId = imagenId;
    }

    public ControlMaestroMultiple getCmmReferencia() {
        return cmmReferencia;
    }

    public void setCmmReferencia(ControlMaestroMultiple cmmReferencia) {
        this.cmmReferencia = cmmReferencia;
    }

    public Integer getCmmReferenciaId() {
        return cmmReferenciaId;
    }

    public void setCmmReferenciaId(Integer cmmReferenciaId) {
        this.cmmReferenciaId = cmmReferenciaId;
    }

}
