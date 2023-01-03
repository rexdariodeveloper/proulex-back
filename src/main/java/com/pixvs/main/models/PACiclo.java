package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import net.minidev.json.JSONArray;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PACiclos")
public class PACiclo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "PACIC_CicloId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column( name="PACIC_Codigo", length = 10,  nullable=false, insertable=true, updatable=true)
    private String codigo;

    @Column( name="PACIC_Nombre", length = 255, nullable=false, insertable=true, updatable=true)
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PACIC_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PACIC_FechaFin", nullable = false)
    private Date fechaFin;

    @Column( name="PACIC_Activo", nullable=false, insertable=true, updatable=true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "PACIC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column( name="PACIC_USU_CreadoPorId", nullable=false, insertable=true, updatable=false)
    private Integer creadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="PACIC_FechaCreacion", nullable=false, insertable=true, updatable=false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PACIC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column( name="PACIC_USU_ModificadoPorId", nullable=true, insertable=false, updatable=true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="PACIC_FechaModificacion", nullable=true, insertable=false, updatable=true)
    private Date fechaModificacion;

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

}
