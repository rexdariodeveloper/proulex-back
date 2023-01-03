package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyClasesCanceladas")
public class ProgramaGrupoIncompanyClaseCancelada {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCCL_ProgramaIncompanyClaseCanceladaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCCL_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private Integer grupoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCCL_FechaCancelar", nullable = false, insertable = true, updatable = true)
    private Date fechaCancelar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Date getFechaCancelar() {
        return fechaCancelar;
    }

    public void setFechaCancelar(Date fechaCancelar) {
        this.fechaCancelar = fechaCancelar;
    }
}
