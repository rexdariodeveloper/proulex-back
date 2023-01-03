package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 25/11/2022.
 */
@Entity
@Table(name = "ProgramasIdiomasCertificacionesDescuentosDetalles")
public class ProgramaIdiomaCertificacionDescuentoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICDD_ProgramaIdiomaCertificacionDescuentoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId", nullable = true, insertable = false, updatable = false)
    private Integer programaIdiomaCertificacionDescuentoId;

    @Column(name = "PICDD_NumeroNivel", nullable = false, insertable = true, updatable = true)
    private Integer numeroNivel;

    @Column(name = "PICDD_PorcentajeDescuento", nullable = false, insertable = true, updatable = true)
    private BigDecimal porcentajeDescuento;

    @Column(name = "PICDD_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaCertificacionDescuentoId() {
        return programaIdiomaCertificacionDescuentoId;
    }

    public void setProgramaIdiomaCertificacionDescuentoId(Integer programaIdiomaCertificacionDescuentoId) {
        this.programaIdiomaCertificacionDescuentoId = programaIdiomaCertificacionDescuentoId;
    }

    public Integer getNumeroNivel() {
        return numeroNivel;
    }

    public void setNumeroNivel(Integer numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
