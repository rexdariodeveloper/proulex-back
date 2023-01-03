package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PreciosIncompanySucursales")
public class PrecioIncompanySucursal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREINCS_PrecioIncompanySucursalId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PREINCS_PREINC_PrecioIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer precioIncompanyId;

    //Sucursal
    @OneToOne
    @JoinColumn(name = "PREINCD_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PREINCD_SUC_SucursalId", nullable = false, insertable = true, updatable = true)
    private Integer sucursalId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecioIncompanyId() {
        return precioIncompanyId;
    }

    public void setPrecioIncompanyId(Integer precioIncompanyId) {
        this.precioIncompanyId = precioIncompanyId;
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
}
