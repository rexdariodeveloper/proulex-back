package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "Estados")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EST_EstadoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "EST_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "EST_PAI_PaisId", nullable = false, insertable = false, updatable = false)
    private Integer paisId;

    @Column(name = "EST_Nombre", nullable = false, insertable = false, updatable = false)
    private String nombre;

    @Column(name = "EST_ClaveEntidad", nullable = true, insertable = false, updatable = false)
    private String claveEntidad;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    public Integer getPaisId() { return paisId; }
    public void setPaisId(Integer paisId) { this.paisId = paisId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getClaveEntidad() { return claveEntidad; }
    public void setClaveEntidad(String claveEntidad) { this.claveEntidad = claveEntidad; }
}
