package com.pixvs.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SATRegimenesFiscales")
public class SATRegimenFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RF_RegimenFiscalId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "RF_Codigo", nullable = false, insertable = false, updatable = false)
    private String codigo;

    @Column(name = "RF_Descripcion", nullable = false, insertable = false, updatable = false)
    private String descripcion;

    @Column(name = "RF_Fisica", nullable = false, insertable = false, updatable = false)
    private boolean fisica;

    @Column(name = "RF_Moral", nullable = false, insertable = false, updatable = false)
    private boolean moral;

    @Column(name = "RF_Activo", nullable = false, insertable = false, updatable = false)
    private boolean activo;

    @ManyToMany
    @JoinTable(name = "SATUsosCFDIRegimenesFiscales", joinColumns = {@JoinColumn(name = "UCFDIRF_UCFDI_UsoCFDIId")}, inverseJoinColumns = {@JoinColumn(name = "UCFDIRF_RF_RegimenFiscalId")})
    private Set<SATUsoCFDI> usosCFDI;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isFisica() {
        return fisica;
    }

    public void setFisica(boolean fisica) {
        this.fisica = fisica;
    }

    public boolean isMoral() {
        return moral;
    }

    public void setMoral(boolean moral) {
        this.moral = moral;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<SATUsoCFDI> getUsosCFDI() {
        return usosCFDI;
    }

    public void setUsosCFDI(Set<SATUsoCFDI> usosCFDI) {
        this.usosCFDI = usosCFDI;
    }
}
