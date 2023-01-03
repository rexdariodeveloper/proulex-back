package com.pixvs.main.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PADescuentosSucursales")
public class PADescuentoSucursal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PADESCS_DescuentoSucursalId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PADESCS_PADESC_DescuentoId", nullable = true, insertable = false, updatable = false)
    private Integer descuentoId;

    @OneToOne
    @JoinColumn(name = "PADESCS_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PADESCS_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @Column(name = "PADESCS_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
