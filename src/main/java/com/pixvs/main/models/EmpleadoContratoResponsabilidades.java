package com.pixvs.main.models;

import javax.persistence.*;

/**
 * Created by Benjamin Osorio on 03/10/2022.
 */
@Entity
@Table(name = "EmpleadosContratosResponsabilidad")
public class EmpleadoContratoResponsabilidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPCORES_EmpleadosContratosResponsabilidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPCORES_EMPCO_EmpleadoContratoId", nullable = true, insertable = true, updatable = false)
    private Integer empleadoContratoId;

    @Column(name = "EMPCORES_Descripcion", length = 250, nullable = false, insertable = true, updatable = false)
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoContratoId() {
        return empleadoContratoId;
    }

    public void setEmpleadoContratoId(Integer empleadoContratoId) {
        this.empleadoContratoId = empleadoContratoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
