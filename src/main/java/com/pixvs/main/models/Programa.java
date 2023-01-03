package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Departamento;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "Programas")
public class Programa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROG_ProgramaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROG_Codigo", length = 10, nullable = false, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "PROG_Nombre", length = 30, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PROG_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @Column(name = "PROG_JOBS", nullable = true, insertable = false, updatable = false)
    private Boolean jobs;

    @Column(name = "PROG_JOBSSEMS", nullable = true, insertable = false, updatable = false)
    private Boolean jobsSems;

    @Column(name = "PROG_PCP", nullable = true, insertable = false, updatable = false)
    private Boolean pcp;

    @Column(name = "PROG_Academy", nullable = true, insertable = false, updatable = false)
    private Boolean academy;

    @OneToOne
    @JoinColumn(name = "PROG_ARC_ImagenId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo imagen;

    @Column(name = "PROG_ARC_ImagenId", updatable = false, insertable = false)
    private Integer imagenId;

    @CreationTimestamp
    @Column(name = "PROG_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROG_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PROG_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROG_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;


    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGI_PROG_ProgramaId", referencedColumnName = "PROG_ProgramaId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdioma> idiomas;

    @Transient
    private String img64;

    @Transient
    private Boolean actualizarGrupos;

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public List<ProgramaIdioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<ProgramaIdioma> idiomas) {
        this.idiomas = idiomas;
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

    public Boolean getJobs() {
        return jobs;
    }

    public void setJobs(Boolean jobs) {
        this.jobs = jobs;
    }

    public Boolean getJobsSems() {
        return jobsSems;
    }

    public void setJobsSems(Boolean jobsSems) {
        this.jobsSems = jobsSems;
    }

    public Boolean getPcp() {
        return pcp;
    }

    public void setPcp(Boolean pcp) {
        this.pcp = pcp;
    }

    public Boolean getAcademy() { return academy; }
    public void setAcademy(Boolean academy) { this.academy = academy; }

    public Boolean getActualizarGrupos() {
        return actualizarGrupos;
    }

    public void setActualizarGrupos(Boolean actualizarGrupos) {
        this.actualizarGrupos = actualizarGrupos;
    }
}
