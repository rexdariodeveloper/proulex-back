package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "DepartamentoResposabilidadHabilidad")
public class DepartamentoResponsabilidadHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPREHA_DepartamentoResposabilidadHabilidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "DEPREHA_DEP_DepartamentoId", nullable = true, insertable = true, updatable = true)
    private Integer departamentoId;

    /*@OneToOne
    @JoinColumn(name = "DEPREHA_DEP_DepartamentoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "DEP_DepartamentoId")
    private Departamento departamento;*/

    @Column(name = "DEPREHA_Descripcion", length = 250, nullable = false, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "DEPREHA_EsResponsabilidad", nullable = false, insertable = true, updatable = true)
    private Boolean esResponsabilidad;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getDepartamentoId() { return departamentoId; }

    public void setDepartamentoId(Integer departamentoId) { this.departamentoId = departamentoId; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getEsResponsabilidad() { return esResponsabilidad; }

    public void setEsResponsabilidad(Boolean esResponsabilidad) { this.esResponsabilidad = esResponsabilidad; }

    /*public Departamento getDepartamento() { return departamento; }

    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
     */
}
