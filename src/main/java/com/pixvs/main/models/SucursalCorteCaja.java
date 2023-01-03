package com.pixvs.main.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 03/09/2021.
 */
@Entity
@Table(name = "SucursalesCortesCajas")
public class SucursalCorteCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCC_SucursalCorteCajaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SCC_Codigo", nullable = false, insertable = true, updatable = false)
    private String codigo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCC_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "SCC_SUC_SucursalId", nullable = false, insertable = true, updatable = false)
    private Integer sucursalId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCC_SP_SucursalPlantelId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SP_SucursalPlantelId")
    private SucursalPlantel sucursalPlantel;

    @Column(name = "SCC_SP_SucursalPlantelId", nullable = true, insertable = true, updatable = false)
    private Integer sucursalPlantelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCC_USU_UsuarioAbreId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario usuarioAbre;

    @Column(name = "SCC_USU_UsuarioAbreId", nullable = false, insertable = true, updatable = false)
    private Integer usuarioAbreId;

    @CreationTimestamp
    @Column(name = "SCC_FechaInicio", nullable = false, insertable = true, updatable = false)
    private Date fechaInicio;

    @Column(name = "SCC_MontoAbrirCaja", nullable = false, insertable = true, updatable = false)
    private BigDecimal montoAbrirCaja;

    @Column(name = "SCC_FechaFin", nullable = true, insertable = false, updatable = true)
    private Date fechaFin;

    @Column(name = "SCC_MontoCerrarCaja", nullable = true, insertable = false, updatable = true)
    private BigDecimal montoCerrarCaja;

    @Column(name = "SCC_Parcial", nullable = false, insertable = false, updatable = false)
    private Boolean parcial;

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

    public SucursalPlantel getSucursalPlantel() {
        return sucursalPlantel;
    }

    public void setSucursalPlantel(SucursalPlantel sucursalPlantel) {
        this.sucursalPlantel = sucursalPlantel;
    }

    public Integer getSucursalPlantelId() {
        return sucursalPlantelId;
    }

    public void setSucursalPlantelId(Integer sucursalPlantelId) {
        this.sucursalPlantelId = sucursalPlantelId;
    }

    public Usuario getUsuarioAbre() {
        return usuarioAbre;
    }

    public void setUsuarioAbre(Usuario usuarioAbre) {
        this.usuarioAbre = usuarioAbre;
    }

    public Integer getUsuarioAbreId() {
        return usuarioAbreId;
    }

    public void setUsuarioAbreId(Integer usuarioAbreId) {
        this.usuarioAbreId = usuarioAbreId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getMontoAbrirCaja() {
        return montoAbrirCaja;
    }

    public void setMontoAbrirCaja(BigDecimal montoAbrirCaja) {
        this.montoAbrirCaja = montoAbrirCaja;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getMontoCerrarCaja() {
        return montoCerrarCaja;
    }

    public void setMontoCerrarCaja(BigDecimal montoCerrarCaja) {
        this.montoCerrarCaja = montoCerrarCaja;
    }

    public Boolean getParcial() {
        return parcial;
    }

    public void setParcial(Boolean parcial) {
        this.parcial = parcial;
    }
}
