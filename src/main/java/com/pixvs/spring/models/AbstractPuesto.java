package com.pixvs.spring.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractPuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUE_PuestoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PUE_Codigo", length = 5, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @Column(name = "PUE_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PUE_Descripcion", length = 150, nullable = false, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "PUE_Proposito", length = 150, nullable = false, insertable = true, updatable = true)
    private String proposito;

    @Column(name = "PUE_Observaciones", length = 150, nullable = false, insertable = true, updatable = true)
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "PUE_CMM_EstadoPuestoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estadoPuesto;

    @Column(name = "PUE_CMM_EstadoPuestoId", nullable = false, insertable = true, updatable = true)
    private Integer estadoPuestoId;

    @CreationTimestamp
    @Column(name = "PUE_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PUE_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PUE_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PUE_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "PUE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PUE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ControlMaestroMultiple getEstadoPuesto() {
        return estadoPuesto;
    }

    public void setEstadoPuesto(ControlMaestroMultiple estadoPuesto) {
        this.estadoPuesto = estadoPuesto;
    }

    public Integer getEstadoPuestoId() {
        return estadoPuestoId;
    }

    public void setEstadoPuestoId(Integer estadoPuestoId) {
        this.estadoPuestoId = estadoPuestoId;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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
}
