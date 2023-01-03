package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasLibrosMateriales")
public class ProgramaIdiomaLibroMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGILM_ProgramaIdiomaLibroMaterialId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGILM_PROGI_ProgramaIdiomaId", nullable = false, insertable = false, updatable = false)
    private Integer programaIdiomaId;

    @Column(name = "PROGILM_Nivel", nullable = false, insertable = true, updatable = true)
    private Integer nivel;

    //Articulo
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGILM_ART_ArticuloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "PROGILM_ART_ArticuloId", nullable = true, insertable = true, updatable = true)
    private Integer articuloId;

    @Column(name = "PROGILM_Borrado", nullable = false, insertable = true, updatable = true)
    private Boolean borrado;

    @OneToOne
    @JoinColumn(name = "PROGILM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGILM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGILM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId", referencedColumnName = "PROGILM_ProgramaIdiomaLibroMaterialId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaLibroMaterialRegla> reglas;

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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getModalidadId() {
        return articuloId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.articuloId = modalidadId;
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

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public List<ProgramaIdiomaLibroMaterialRegla> getReglas() {
        return reglas;
    }

    public void setReglas(List<ProgramaIdiomaLibroMaterialRegla> reglas) {
        this.reglas = reglas;
    }
}
