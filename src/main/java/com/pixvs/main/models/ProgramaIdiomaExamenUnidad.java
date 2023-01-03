package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasExamenesUnidades")
public class ProgramaIdiomaExamenUnidad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIEU_ProgramaIdiomaExamenUnidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId", nullable = true, insertable = false, updatable = false)
    private Integer examenDetalleId;

    //Idioma
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGILM_ProgramaIdiomaLibroMaterialId")
    private ProgramaIdiomaLibroMaterial libroMaterial;

    @Column(name = "PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId", nullable = true, insertable = true, updatable = true)
    private Integer libroMaterialId;

    @Column(name = "PROGIEU_Descripcion", length = 50, nullable = false, insertable = true, updatable = true)
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamenDetalleId() {
        return examenDetalleId;
    }

    public void setExamenDetalleId(Integer examenDetalleId) {
        this.examenDetalleId = examenDetalleId;
    }

    public ProgramaIdiomaLibroMaterial getLibroMaterial() {
        return libroMaterial;
    }

    public void setLibroMaterial(ProgramaIdiomaLibroMaterial libroMaterial) {
        this.libroMaterial = libroMaterial;
    }

    public Integer getLibroMaterialId() {
        return libroMaterialId;
    }

    public void setLibroMaterialId(Integer libroMaterialId) {
        this.libroMaterialId = libroMaterialId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
