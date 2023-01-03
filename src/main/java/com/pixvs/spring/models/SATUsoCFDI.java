package com.pixvs.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SATUsosCFDI")
public class SATUsoCFDI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UCFDI_UsoCFDIId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "UCFDI_Codigo", nullable = false)
    private String codigo;

    @Column(name = "UCFDI_Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "UCFDI_Fisica", nullable = false)
    private boolean fisica;

    @Column(name = "UCFDI_Moral", nullable = false)
    private boolean moral;

    @Column(name = "UCFDI_Activo", nullable = false)
    private boolean activo;

    @ManyToMany
    @JoinTable(name = "SATUsosCFDIRegimenesFiscales", joinColumns = {@JoinColumn(name = "UCFDIRF_UCFDI_UsoCFDIId")}, inverseJoinColumns = {@JoinColumn(name = "UCFDIRF_RF_RegimenFiscalId")})
    private Set<SATRegimenFiscal> regimenesFiscales;

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

    public Set<SATRegimenFiscal> getRegimenesFiscales() {
        return regimenesFiscales;
    }

    public void setRegimenesFiscales(Set<SATRegimenFiscal> regimenesFiscales) {
        this.regimenesFiscales = regimenesFiscales;
    }
}
