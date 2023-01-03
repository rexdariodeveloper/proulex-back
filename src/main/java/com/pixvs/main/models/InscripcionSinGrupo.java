package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 10/08/2021.
 */
@Entity
@Table(name = "InscripcionesSinGrupo")
public class InscripcionSinGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSSG_InscripcionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle ordenVentaDetalle;

    @Column(name = "INSSG_OVD_OrdenVentaDetalleId", nullable = false, insertable = true, updatable = true)
    private Integer ordenVentaDetalleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_ALU_AlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALU_AlumnoId")
    private Alumno alumno;

    @Column(name = "INSSG_ALU_AlumnoId", nullable = false, insertable = true, updatable = true)
    private Integer alumnoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "INSSG_SUC_SucursalId", nullable = false, insertable = true, updatable = true)
    private Integer sucursalId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_PROG_ProgramaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "INSSG_PROG_ProgramaId", nullable = false, insertable = true, updatable = true)
    private Integer programaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_CMM_IdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple idioma;

    @Column(name = "INSSG_CMM_IdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer idiomaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad paModalidad;

    @Column(name = "INSSG_PAMOD_ModalidadId", nullable = false, insertable = true, updatable = true)
    private Integer paModalidadId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_PAMODH_PAModalidadHorarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMODH_PAModalidadHorarioId")
    private PAModalidadHorario paModalidadHorario;

    @Column(name = "INSSG_PAMODH_PAModalidadHorarioId", nullable = true, insertable = true, updatable = true)
    private Integer paModalidadHorarioId;

    @Column(name = "INSSG_Nivel", nullable = false, insertable = true, updatable = true)
    private Integer nivel;

    @Column(name = "INSSG_Grupo", nullable = true, insertable = true, updatable = true)
    private Integer grupo;

    @Column(name = "INSSG_Comentario", length = 255, nullable = true, insertable = true, updatable = true)
    private String comentario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "INSSG_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @Column(name = "INSSG_EntregaLibrosPendiente", nullable = false, insertable = true, updatable = true)
    private Boolean entregaLibrosPendiente;

    @Column(name = "INSSG_BECU_BecaId", nullable = true, insertable = true, updatable = false)
    private Integer becaUDGId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_CMM_TipoGrupoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoGrupo;

    @Column(name = "INSSG_CMM_TipoGrupoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoGrupoId;

    @CreationTimestamp
    @Column(name = "INSSG_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "INSSG_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "INSSG_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSSG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "INSSG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public ControlMaestroMultiple getIdioma() {
        return idioma;
    }

    public void setIdioma(ControlMaestroMultiple idioma) {
        this.idioma = idioma;
    }

    public Integer getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }

    public PAModalidad getPaModalidad() {
        return paModalidad;
    }

    public void setPaModalidad(PAModalidad paModalidad) {
        this.paModalidad = paModalidad;
    }

    public Integer getPaModalidadId() {
        return paModalidadId;
    }

    public void setPaModalidadId(Integer paModalidadId) {
        this.paModalidadId = paModalidadId;
    }

    public PAModalidadHorario getPaModalidadHorario() {
        return paModalidadHorario;
    }

    public void setPaModalidadHorario(PAModalidadHorario paModalidadHorario) {
        this.paModalidadHorario = paModalidadHorario;
    }

    public Integer getPaModalidadHorarioId() {
        return paModalidadHorarioId;
    }

    public void setPaModalidadHorarioId(Integer paModalidadHorarioId) {
        this.paModalidadHorarioId = paModalidadHorarioId;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public Boolean getEntregaLibrosPendiente() {
        return entregaLibrosPendiente;
    }

    public void setEntregaLibrosPendiente(Boolean entregaLibrosPendiente) {
        this.entregaLibrosPendiente = entregaLibrosPendiente;
    }

    public Integer getBecaUDGId() {
        return becaUDGId;
    }

    public void setBecaUDGId(Integer becaUDGId) {
        this.becaUDGId = becaUDGId;
    }

    public ControlMaestroMultiple getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(ControlMaestroMultiple tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public Integer getTipoGrupoId() {
        return tipoGrupoId;
    }

    public void setTipoGrupoId(Integer tipoGrupoId) {
        this.tipoGrupoId = tipoGrupoId;
    }
}
