package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "Autonumericos")
public class Autonumerico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUT_AutonumericoId", insertable = false, updatable = false)
    private Integer id;

    @Column(name = "AUT_Nombre", length = 50, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "AUT_Siguiente", nullable = false, insertable = true, updatable = true)
    private Integer siguiente;

    @Column(name = "AUT_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @Column(name = "AUT_Prefijo", nullable = false, insertable = true, updatable = true)
    private String prefijo;

    @Column(name = "AUT_Digitos", nullable = false, insertable = true, updatable = true)
    private Integer digitos;

    @Column(name = "AUT_ReferenciaId")
    private Integer referenciaId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getSiguiente() { return siguiente; }
    public void setSiguiente(Integer siguiente) { this.siguiente = siguiente; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getPrefijo() { return prefijo; }
    public void setPrefijo(String prefijo) { this.prefijo = prefijo; }

    public Integer getDigitos() { return digitos; }
    public void setDigitos(Integer digitos) { this.digitos = digitos; }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
    }
}

