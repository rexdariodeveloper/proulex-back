package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 23/11/2021.
 */
@Entity
@Table(name = "AlumnosExamenesCertificaciones")
public class AlumnoExamenCertificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALUEC_AlumnoExamenCertificacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ALUEC_ALU_AlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "ALUEC_ALU_AlumnoId", nullable = false, insertable = true, updatable = false)
    private Integer alumnoId;

    @OneToOne
    @JoinColumn(name = "ALUEC_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "ALUEC_ART_ArticuloId", nullable = false, insertable = true, updatable = false)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "ALUEC_OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle ordenVentaDetalle;

    @Column(name = "ALUEC_OVD_OrdenVentaDetalleId", nullable = false, insertable = true, updatable = false)
    private Integer ordenVentaDetalleId;

    @OneToOne
    @JoinColumn(name = "ALUEC_CMM_TipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipo;

    @Column(name = "ALUEC_CMM_TipoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoId;

    @OneToOne
    @JoinColumn(name = "ALUEC_PROGI_ProgramaIdiomaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma curso;

    @Column(name = "ALUEC_PROGI_ProgramaIdiomaId", nullable = true, insertable = true, updatable = true)
    private Integer cursoId;

    @OneToOne
    @JoinColumn(name = "ALUEC_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "ALUEC_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @Column(name = "ALUEC_Calificacion", nullable = true, insertable = true, updatable = true)
    private BigDecimal calificacion;

    @Column(name = "ALUEC_Nivel", nullable = true, insertable = true, updatable = true)
    private Integer nivel;

    @OneToOne
    @JoinColumn(name = "ALUEC_PICV_ProgramaIdiomaCertificacionValeId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PICV_ProgramaIdiomaCertificacionValeId")
    private ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale;

    @Column(name = "ALUEC_PICV_ProgramaIdiomaCertificacionValeId", nullable = true, insertable = true, updatable = true)
    private Integer programaIdiomaCertificacionValeId;

    @CreationTimestamp
    @Column(name = "ALUEC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALUEC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }

    public Integer getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Integer alumnoId) { this.alumnoId = alumnoId; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Integer getArticuloId() { return articuloId; }
    public void setArticuloId(Integer articuloId) { this.articuloId = articuloId; }

    public OrdenVentaDetalle getOrdenVentaDetalle() { return ordenVentaDetalle; }
    public void setOrdenVentaDetalle(OrdenVentaDetalle ordenVentaDetalle) { this.ordenVentaDetalle = ordenVentaDetalle; }

    public Integer getOrdenVentaDetalleId() { return ordenVentaDetalleId; }
    public void setOrdenVentaDetalleId(Integer ordenVentaDetalleId) { this.ordenVentaDetalleId = ordenVentaDetalleId; }

    public ControlMaestroMultiple getTipo() { return tipo; }
    public void setTipo(ControlMaestroMultiple tipo) { this.tipo = tipo; }

    public Integer getTipoId() { return tipoId; }
    public void setTipoId(Integer tipoId) { this.tipoId = tipoId; }

    public ProgramaIdioma getCurso() { return curso; }
    public void setCurso(ProgramaIdioma curso) { this.curso = curso; }

    public Integer getCursoId() { return cursoId; }
    public void setCursoId(Integer cursoId) { this.cursoId = cursoId; }

    public ControlMaestroMultiple getEstatus() { return estatus; }
    public void setEstatus(ControlMaestroMultiple estatus) { this.estatus = estatus; }

    public Integer getEstatusId() { return estatusId; }
    public void setEstatusId(Integer estatusId) { this.estatusId = estatusId; }

    public BigDecimal getCalificacion() { return calificacion; }
    public void setCalificacion(BigDecimal calificacion) { this.calificacion = calificacion; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public ProgramaIdiomaCertificacionVale getProgramaIdiomaCertificacionVale() { return programaIdiomaCertificacionVale; }
    public void setProgramaIdiomaCertificacionVale(ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale) { this.programaIdiomaCertificacionVale = programaIdiomaCertificacionVale; }

    public Integer getProgramaIdiomaCertificacionValeId() { return programaIdiomaCertificacionValeId; }
    public void setProgramaIdiomaCertificacionValeId(Integer programaIdiomaCertificacionValeId) { this.programaIdiomaCertificacionValeId = programaIdiomaCertificacionValeId; }

}
