package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 14/07/2021.
 */
@Entity
@Table(name = "Inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INS_InscripcionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "INS_Codigo", length = 30, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle ordenVentaDetalle;

    @Column(name = "INS_OVD_OrdenVentaDetalleId", nullable = false, insertable = true, updatable = true)
    private Integer ordenVentaDetalleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_ALU_AlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "INS_ALU_AlumnoId", nullable = false, insertable = true, updatable = true)
    private Integer alumnoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROGRU_GrupoId")
    private ProgramaGrupo grupo;

    @Column(name = "INS_PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private Integer grupoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_BECU_BecaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "BECU_BecaId")
    private BecaUDG becaUDG;

    @Column(name = "INS_BECU_BecaId", nullable = true, insertable = true, updatable = true)
    private Integer becaUDGId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "INS_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @Column(name = "INS_EntregaLibrosPendiente", nullable = false, insertable = true, updatable = true)
    private Boolean entregaLibrosPendiente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_InstitucionAcademicaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple institucionAcademica;

    @Column(name = "INS_CMM_InstitucionAcademicaId", nullable = true, insertable = true, updatable = true)
    private Integer institucionAcademicaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_CarreraId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple carrera;

    @Column(name = "INS_CMM_CarreraId", nullable = true, insertable = true, updatable = true)
    private Integer carreraId;

    @Column(name = "INS_Carrera", length = 255, nullable = true, insertable = true, updatable = true)
    private String carreraTexto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_TurnoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple turno;

    @Column(name = "INS_CMM_TurnoId", nullable = true, insertable = true, updatable = true)
    private Integer turnoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_GradoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple grado;

    @Column(name = "INS_CMM_GradoId", nullable = true, insertable = true, updatable = true)
    private Integer gradoId;

    @Column(name = "INS_Grupo", length = 5, nullable = true, insertable = true, updatable = true)
    private String grupoTexto;

    @CreationTimestamp
    @Column(name = "INS_FechaCreacion", nullable = false, insertable = true, updatable = true)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "INS_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "INS_USU_CreadoPorId", nullable = true, insertable = true, updatable = true)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "INS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CMM_InscripcionOrigenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple origen;

    @Column(name = "INS_CMM_InscripcionOrigenId", nullable = true, insertable = true, updatable = false)
    private Integer origenId;

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

    public ProgramaGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(ProgramaGrupo grupo) {
        this.grupo = grupo;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
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

    public BecaUDG getBecaUDG() {
        return becaUDG;
    }

    public void setBecaUDG(BecaUDG becaUDG) {
        this.becaUDG = becaUDG;
    }

    public Integer getBecaUDGId() {
        return becaUDGId;
    }

    public void setBecaUDGId(Integer becaUDGId) {
        this.becaUDGId = becaUDGId;
    }

    public Boolean getEntregaLibrosPendiente() {
        return entregaLibrosPendiente;
    }

    public void setEntregaLibrosPendiente(Boolean entregaLibrosPendiente) {
        this.entregaLibrosPendiente = entregaLibrosPendiente;
    }

    public ControlMaestroMultiple getInstitucionAcademica() { return institucionAcademica; }
    public void setInstitucionAcademica(ControlMaestroMultiple institucionAcademica) { this.institucionAcademica = institucionAcademica; }

    public Integer getInstitucionAcademicaId() { return institucionAcademicaId; }
    public void setInstitucionAcademicaId(Integer institucionAcademicaId) { this.institucionAcademicaId = institucionAcademicaId; }

    public ControlMaestroMultiple getCarrera() { return carrera; }
    public void setCarrera(ControlMaestroMultiple carrera) { this.carrera = carrera; }

    public Integer getCarreraId() { return carreraId; }
    public void setCarreraId(Integer carreraId) { this.carreraId = carreraId; }

    public String getCarreraTexto() { return carreraTexto; }
    public void setCarreraTexto(String carreraTexto) { this.carreraTexto = carreraTexto; }

    public ControlMaestroMultiple getTurno() { return turno; }
    public void setTurno(ControlMaestroMultiple turno) { this.turno = turno; }

    public Integer getTurnoId() { return turnoId; }
    public void setTurnoId(Integer turnoId) { this.turnoId = turnoId; }

    public ControlMaestroMultiple getGrado() { return grado; }
    public void setGrado(ControlMaestroMultiple grado) { this.grado = grado; }

    public Integer getGradoId() { return gradoId; }
    public void setGradoId(Integer gradoId) { this.gradoId = gradoId; }

    public String getGrupoTexto() { return grupoTexto; }
    public void setGrupoTexto(String grupoTexto) { this.grupoTexto = grupoTexto; }

    public ControlMaestroMultiple getOrigen() { return origen; }
    public void setOrigen(ControlMaestroMultiple origen) { this.origen = origen; }

    public Integer getOrigenId() { return origenId; }
    public void setOrigenId(Integer origenId) { this.origenId = origenId; }
}
