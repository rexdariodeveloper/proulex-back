package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasCertificacion")
public class ProgramaIdiomaCertificacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIC_ProgramaIdiomaCertificacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIC_PROGI_ProgramaIdiomaId", insertable = false, updatable = false)
    private Integer programaIdiomaId;

    @Column(name = "PROGIC_Nivel", length = 10, insertable = true, updatable = true)
    private String nivel;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIC_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo certificacion;

    @Column(name = "PROGIC_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer certificacionId;

    @Column(name = "PROGIC_Precio", insertable = true, updatable = true)
    private BigDecimal precio;

    @Column(name = "PROGIC_Borrado", nullable = false, insertable = true, updatable = true)
    private Boolean borrado;

    @OneToOne
    @JoinColumn(name = "PROGIC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGIC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGIC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public Articulo getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(Articulo certificacion) {
        this.certificacion = certificacion;
    }

    public Integer getCertificacionId() {
        return certificacionId;
    }

    public void setCertificacionId(Integer certificacionId) {
        this.certificacionId = certificacionId;
    }
}
