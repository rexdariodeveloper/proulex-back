package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AlumnosConstanciasTutorias")
public class AlumnoConstanciaTutoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALUCT_AlumnoConstanciaTutoriaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ALUCT_ALU_AlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "ALUCT_ALU_AlumnoId", nullable = false)
    private Integer alumnoId;

    @OneToOne
    @JoinColumn(name = "ALUCT_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "ALUCT_ART_ArticuloId", nullable = false)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "ALUCT_OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle ordenVentaDetalle;

    @Column(name = "ALUCT_OVD_OrdenVentaDetalleId", nullable = false)
    private Integer ordenVentaDetalleId;

    @Column(name = "ALUCT_Activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "ALUCT_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ALUCT_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ALUCT_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ALUCT_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALUCT_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALUCT_FechaUltimaModificacion", insertable = false)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
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

    public OrdenVentaDetalle getOrdenVentaDetalle() {
        return ordenVentaDetalle;
    }

    public void setOrdenVentaDetalle(OrdenVentaDetalle ordenVentaDetalle) {
        this.ordenVentaDetalle = ordenVentaDetalle;
    }

    public Integer getOrdenVentaDetalleId() {
        return ordenVentaDetalleId;
    }

    public void setOrdenVentaDetalleId(Integer ordenVentaDetalleId) {
        this.ordenVentaDetalleId = ordenVentaDetalleId;
    }
}
