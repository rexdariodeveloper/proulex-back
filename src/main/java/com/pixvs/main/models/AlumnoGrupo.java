package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "AlumnosGrupos")
public class AlumnoGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALUG_AlumnoGrupoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ALUG_ALU_AlumnoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "ALUG_ALU_AlumnoId", nullable = false, insertable = true, updatable = true)
    private Integer alumnoId;

    @OneToOne
    @JoinColumn(name = "ALUG_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGRU_GrupoId")
    private ProgramaGrupo grupo;

    @Column(name = "ALUG_PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private Integer grupoId;

    @Column(name = "ALUG_Asistencias", nullable = false, insertable = true, updatable = true)
    private Integer asistencias;

    @Column(name = "ALUG_Faltas", nullable = false, insertable = true, updatable = true)
    private Integer faltas;

    @Column(name = "ALUG_MinutosRetardo", nullable = false, insertable = true, updatable = true)
    private Integer minutosRetardo;

    @Column(name = "ALUG_CalificacionFinal", nullable = true, insertable = true, updatable = true)
    private BigDecimal calificacionFinal;

    @Column(name = "ALUG_CalificacionConvertida", nullable = true, insertable = true, updatable = true)
    private BigDecimal calificacionConvertida;

    @OneToOne
    @JoinColumn(name = "ALUG_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "ALUG_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "ALUG_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ALUG_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ALUG_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALUG_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "ALUG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ALUG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @Column(name = "ALUG_INS_InscripcionId", nullable = true, insertable = true, updatable = true)
    private Integer inscripcionId;

    @OneToOne
    @JoinColumn(name = "ALUG_INS_InscripcionId", nullable = true, insertable = false, updatable = false, referencedColumnName = "INS_InscripcionId")
    private Inscripcion inscripcion;

    /** Setters & Getters**/
    public Integer getId() {return id; }
    public void setId(Integer id) {this.id = id; }

    public Alumno getAlumno() {return alumno; }
    public void setAlumno(Alumno alumno) {this.alumno = alumno; }

    public Integer getAlumnoId() {return alumnoId; }
    public void setAlumnoId(Integer alumnoId) {this.alumnoId = alumnoId; }

    public ProgramaGrupo getGrupo() {return grupo; }
    public void setGrupo(ProgramaGrupo grupo) {this.grupo = grupo; }

    public Integer getGrupoId() {return grupoId; }
    public void setGrupoId(Integer grupoId) {this.grupoId = grupoId; }

    public Integer getAsistencias() {return asistencias; }
    public void setAsistencias(Integer asistencias) {this.asistencias = asistencias; }

    public Integer getFaltas() {return faltas; }
    public void setFaltas(Integer faltas) {this.faltas = faltas; }

    public Integer getMinutosRetardo() {return minutosRetardo; }
    public void setMinutosRetardo(Integer minutosRetardo) {this.minutosRetardo = minutosRetardo; }

    public BigDecimal getCalificacionFinal() {return calificacionFinal; }
    public void setCalificacionFinal(BigDecimal calificacionFinal) {this.calificacionFinal = calificacionFinal; }

    public BigDecimal getCalificacionConvertida() {return calificacionConvertida; }
    public void setCalificacionConvertida(BigDecimal calificacionConvertida) {this.calificacionConvertida = calificacionConvertida; }

    public ControlMaestroMultiple getEstatus() {return estatus; }
    public void setEstatus(ControlMaestroMultiple estatus) {this.estatus = estatus; }

    public Integer getEstatusId() {return estatusId; }
    public void setEstatusId(Integer estatusId) {this.estatusId = estatusId; }

    public Date getFechaCreacion() {return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) {this.fechaCreacion = fechaCreacion; }

    public Usuario getCreadoPor() {return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) {this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() {return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) {this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() {return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) {this.fechaModificacion = fechaModificacion; }

    public Usuario getModificadoPor() {return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) {this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() {return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) {this.modificadoPorId = modificadoPorId; }

    public Integer getInscripcionId() { return inscripcionId; }
    public void setInscripcionId(Integer inscripcionId) { this.inscripcionId = inscripcionId; }

    public Inscripcion getInscripcion() { return inscripcion; }
    public void setInscripcion(Inscripcion inscripcion) { this.inscripcion = inscripcion; }
}
