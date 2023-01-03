package com.pixvs.main.models;

import javax.persistence.*;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PADescuentosUsuariosAutorizados")
public class PADescuentoUsuarioAutorizado {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PADESUA_DescuentoArticuloId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PADESUA_PADESC_DescuentoId", nullable = true, insertable = false, updatable = false)
    private Integer descuentoId;

    @OneToOne
    @JoinColumn(name = "PADESUA_USU_UsuarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario usuario;

    @Column(name = "PADESUA_USU_UsuarioId", nullable = true, insertable = true, updatable = true)
    private Integer usuarioId;

    @OneToOne
    @JoinColumn(name = "PADESUA_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PADESUA_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @Column(name = "PADESUA_Activo", nullable = false, insertable = true, updatable = true)
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
