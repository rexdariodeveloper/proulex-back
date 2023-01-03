package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AlumnosAsistencias")
public class AlumnoAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AA_AlumnoAsistenciaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "AA_ALU_AlumnoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "AA_ALU_AlumnoId", nullable = false, insertable = true, updatable = true)
    private Integer alumnoId;

    @OneToOne
    @JoinColumn(name = "AA_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGRU_GrupoId")
    private ProgramaGrupo grupo;

    @Column(name = "AA_PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private Integer grupoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "AA_Fecha", nullable = false, insertable = true, updatable = true)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "AA_CMM_TipoAsistenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoAsistencia;

    @Column(name = "AA_CMM_TipoAsistenciaId", nullable = false, insertable = true, updatable = true)
    private Integer tipoAsistenciaId;

    @Column(name = "AA_Comentario", nullable = true, insertable = true, updatable = true, length = 280)
    private String comentario;

    @Column(name = "AA_MinutosRetardo", nullable = true, insertable = true, updatable = true)
    private Integer minutosRetardo;

    @Column(name = "AA_MotivoJustificante", nullable = true, insertable = true, updatable = true, length = 280)
    private String motivoJustificante;

    @CreationTimestamp
    @Column(name = "AA_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "AA_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "AA_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "AA_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "AA_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "AA_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    /** Setters & Getters**/
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }

    public Integer getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Integer alumnoId) { this.alumnoId = alumnoId; }

    public ProgramaGrupo getGrupo() { return grupo; }
    public void setGrupo(ProgramaGrupo grupo) { this.grupo = grupo; }

    public Integer getGrupoId() { return grupoId; }
    public void setGrupoId(Integer grupoId) { this.grupoId = grupoId; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public ControlMaestroMultiple getTipoAsistencia() { return tipoAsistencia; }
    public void setTipoAsistencia(ControlMaestroMultiple tipoAsistencia) { this.tipoAsistencia = tipoAsistencia; }

    public Integer getTipoAsistenciaId() { return tipoAsistenciaId; }
    public void setTipoAsistenciaId(Integer tipoAsistenciaId) { this.tipoAsistenciaId = tipoAsistenciaId; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Integer getMinutosRetardo() { return minutosRetardo; }
    public void setMinutosRetardo(Integer minutosRetardo) { this.minutosRetardo = minutosRetardo; }

    public String getMotivoJustificante() { return motivoJustificante; }
    public void setMotivoJustificante(String motivoJustificante) { this.motivoJustificante = motivoJustificante; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }
}
