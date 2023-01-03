package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Archivos")
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARC_ArchivoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ARC_NombreOriginal", nullable = false, updatable = false)
    private String nombreOriginal;

    @Column(name = "ARC_NombreFisico", nullable = false, updatable = false)
    private String nombreFisico;

    @OneToOne
    @JoinColumn(name = "ARC_AEC_EstructuraId", referencedColumnName = "AEC_EstructuraId", insertable = false, updatable = false)
    private ArchivoEstructuraCarpeta archivoEstructuraCarpeta;

    @Column(name = "ARC_AEC_EstructuraId", nullable = false, insertable = true, updatable = false)
    private Integer estructuraId;

    @Column(name = "ARC_CMM_TipoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoId;

    @OneToOne
    @JoinColumn(name = "ARC_CMM_TipoId", referencedColumnName = "CMM_ControlId", nullable = true, insertable = false, updatable = false)
    private ControlMaestroMultiple tipo;


    @Column(name = "ARC_RutaFisica", nullable = false, updatable = false)
    private String rutaFisica;

    @Column(name = "ARC_Publico", nullable = false)
    private Boolean publico;

    @Column(name = "ARC_Activo", nullable = false)
    private Boolean activo;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "ARC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "ARC_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ARC_USU_CreadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario creadoPor;

    public Archivo() {
    }

    public Archivo(String nombreOriginal, String nombreFisico, Integer estructuraId, Integer tipoId, String rutaFisica, Boolean publico, Integer creadoPorId, Boolean activo) {
        this.nombreOriginal = nombreOriginal;
        this.nombreFisico = nombreFisico;
        this.estructuraId = estructuraId;
        this.tipoId = tipoId;
        this.rutaFisica = rutaFisica;
        this.publico = publico;
        this.creadoPorId = creadoPorId;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getNombreFisico() {
        return nombreFisico;
    }

    public void setNombreFisico(String nombreFisico) {
        this.nombreFisico = nombreFisico;
    }

    public ArchivoEstructuraCarpeta getArchivoEstructuraCarpeta() {
        return archivoEstructuraCarpeta;
    }

    public void setArchivoEstructuraCarpeta(ArchivoEstructuraCarpeta archivoEstructuraCarpeta) {
        this.archivoEstructuraCarpeta = archivoEstructuraCarpeta;
    }

    public Integer getEstructuraId() {
        return estructuraId;
    }

    public void setEstructuraId(Integer estructuraId) {
        this.estructuraId = estructuraId;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public ControlMaestroMultiple getTipo() {
        return tipo;
    }

    public void setTipo(ControlMaestroMultiple tipo) {
        this.tipo = tipo;
    }

    public String getRutaFisica() {
        return rutaFisica;
    }

    public void setRutaFisica(String rutaFisica) {
        this.rutaFisica = rutaFisica;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
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

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }
}
