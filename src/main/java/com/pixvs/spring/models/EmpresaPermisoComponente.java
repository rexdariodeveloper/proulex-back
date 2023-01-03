package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "EmpresasPermisosComponentes")
public class EmpresaPermisoComponente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPC_EmpresaPermisoComponenteId", updatable = false)
    private Integer id;

    @Column(name = "EMPC_EM_EmpresaId", nullable = false)
    private int empresaId;

    @Column(name = "EMPC_MP_NodoId", nullable = false)
    private Integer nodoId;

    @Column(name = "EMPC_CMM_TipoPermisoId", nullable = false)
    private int tipoPermisoId;

    @Column(name = "EMPC_Componente", nullable = false)
    private String componente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getNodoId() {
        return nodoId;
    }

    public void setNodoId(Integer nodoId) {
        this.nodoId = nodoId;
    }

    public int getTipoPermisoId() {
        return tipoPermisoId;
    }

    public void setTipoPermisoId(int tipoPermisoId) {
        this.tipoPermisoId = tipoPermisoId;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }
}