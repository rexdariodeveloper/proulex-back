package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Entity
@Table(name = "ProgramasIdiomasLibrosMaterialesReglas")
public class ProgramaIdiomaLibroMaterialRegla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGILMR_ProgramaIdiomaLibroMaterialReglaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId", nullable = false, insertable = false, updatable = false)
    private Integer programaLibroMateriallId;

    //Plataforma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGILMR_CMM_CarreraId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple carrera;

    @Column(name = "PROGILMR_CMM_CarreraId", nullable = false, insertable = true, updatable = true)
    private Integer carreraId;

    @Column(name = "PROGILMR_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaLibroMateriallId() {
        return programaLibroMateriallId;
    }

    public void setProgramaLibroMateriallId(Integer programaLibroMateriallId) {
        this.programaLibroMateriallId = programaLibroMateriallId;
    }

    public ControlMaestroMultiple getCarrera() {
        return carrera;
    }

    public void setCarrera(ControlMaestroMultiple carrera) {
        this.carrera = carrera;
    }

    public Integer getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Integer carreraId) {
        this.carreraId = carreraId;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }
}
