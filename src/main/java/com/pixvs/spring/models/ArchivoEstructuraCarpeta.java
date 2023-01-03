package com.pixvs.spring.models;

import org.hibernate.annotations.CreationTimestamp
        ;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ArchivosEstructuraCarpetas")
public class ArchivoEstructuraCarpeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AEC_EstructuraId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "AEC_NombreCarpeta", nullable = false, insertable = true, updatable = false)
    private String nombreCarpeta;

    @Column(name = "AEC_Descripcion", nullable = false, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "AEC_AEC_EstructuraReferenciaId", nullable = true, insertable = true, updatable = true)
    private Integer estructuraReferenciaId;

    @OneToOne
    @JoinColumn(name = "AEC_AEC_EstructuraReferenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "AEC_EstructuraId")
    private ArchivoEstructuraCarpeta archivoEstructuraCarpeta;


    @Column(name = "AEC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "AEC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "AEC_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public void setNombreCarpeta(String nombreCarpeta) {
        this.nombreCarpeta = nombreCarpeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstructuraReferenciaId() {
        return estructuraReferenciaId;
    }

    public void setEstructuraReferenciaId(Integer estructuraReferenciaId) {
        this.estructuraReferenciaId = estructuraReferenciaId;
    }

    public ArchivoEstructuraCarpeta getArchivoEstructuraCarpeta() {
        return archivoEstructuraCarpeta;
    }

    public void setArchivoEstructuraCarpeta(ArchivoEstructuraCarpeta archivoEstructuraCarpeta) {
        this.archivoEstructuraCarpeta = archivoEstructuraCarpeta;
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
}
