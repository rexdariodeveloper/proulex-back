package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "AlumnosExamenesCalificaciones")
public class AlumnoExamenCalificacion {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "AEC_AlumnoExamenCalificacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column( name="AEC_ALU_AlumnoId",  nullable=false, insertable=true, updatable=true)
    private Integer alumnoId;

    @Column( name="AEC_PROGRU_GrupoId",  nullable=false, insertable=true, updatable=true)
    private Integer grupoId;

    @Column( name="AEC_PROGIED_ProgramaIdiomaExamenDetalleId",  nullable=true, insertable=true, updatable=true)
    private Integer programaIdiomaExamenDetalleId;

    @Column( name="AEC_PROGRUED_ProgramaGrupoExamenDetalleId",  nullable=false, insertable=true, updatable=true)
    private Integer programaGrupoExamenDetalleId;

    @Column( name="AEC_Puntaje",  nullable=false, insertable=true, updatable=true)
    private BigDecimal puntaje;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AEC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column( name="AEC_USU_CreadoPorId", nullable=false, insertable=true, updatable=false)
    private Integer creadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="AEC_FechaCreacion", nullable=false, insertable=true, updatable=false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AEC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column( name="AEC_USU_ModificadoPorId", nullable=true, insertable=false, updatable=true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="AEC_FechaModificacion", nullable=true, insertable=false, updatable=true)
    private Date fechaModificacion;



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Integer alumnoId) { this.alumnoId = alumnoId; }

    public Integer getGrupoId() { return grupoId; }
    public void setGrupoId(Integer grupoId) { this.grupoId = grupoId; }

    public Integer getProgramaIdiomaExamenDetalleId() { return programaIdiomaExamenDetalleId; }
    public void setProgramaIdiomaExamenDetalleId(Integer programaIdiomaExamenDetalleId) { this.programaIdiomaExamenDetalleId = programaIdiomaExamenDetalleId; }

    public Integer getProgramaGrupoExamenDetalleId() { return programaGrupoExamenDetalleId; }
    public void setProgramaGrupoExamenDetalleId(Integer programaGrupoExamenDetalleId) { this.programaGrupoExamenDetalleId = programaGrupoExamenDetalleId; }

    public BigDecimal getPuntaje() { return puntaje; }
    public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}
