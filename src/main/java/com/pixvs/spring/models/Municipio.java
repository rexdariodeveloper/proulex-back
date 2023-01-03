package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "Municipios")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MUN_MunicipioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "MUN_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "MUN_EST_EstadoId", nullable = false, insertable = false, updatable = false)
    private Integer estadoId;

    @Column(name = "MUN_Nombre", nullable = false, insertable = false, updatable = false)
    private String nombre;

    @Column(name = "MUN_ClaveMunicipio", nullable = true, insertable = false, updatable = false)
    private String claveMunicipio;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Integer getEstadoId() { return estadoId; }
    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getClaveMunicipio() { return claveMunicipio; }
    public void setClaveMunicipio(String claveMunicipio) { this.claveMunicipio = claveMunicipio; }
}
