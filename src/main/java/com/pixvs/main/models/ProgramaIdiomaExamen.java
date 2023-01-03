package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasExamenes")
public class ProgramaIdiomaExamen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIE_ProgramaIdiomaExamenId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIE_PROGIN_ProgramaIdiomaNivelId", nullable = true, insertable = false, updatable = false)
    private Integer programaIdiomaNivelId;

    @Column(name = "PROGIE_Nombre",length = 100,nullable = true, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PROGIE_Porcentaje", nullable = true, insertable = true, updatable = true)
    private BigDecimal porcentaje;

    @Column(name = "PROGIE_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "PROGIE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGIE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGIE_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "PROGIE_Orden", nullable = true, insertable = true, updatable = true)
    private Integer orden;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGIED_PROGIE_ProgramaIdiomaExamenId", referencedColumnName = "PROGIE_ProgramaIdiomaExamenId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaExamenDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaNivelId() {
        return programaIdiomaNivelId;
    }

    public void setProgramaIdiomaNivelId(Integer programaIdiomaNivelId) {
        this.programaIdiomaNivelId = programaIdiomaNivelId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public List<ProgramaIdiomaExamenDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ProgramaIdiomaExamenDetalle> detalles) {
        this.detalles = detalles;
    }
}
