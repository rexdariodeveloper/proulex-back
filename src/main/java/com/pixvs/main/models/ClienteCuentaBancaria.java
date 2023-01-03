package com.pixvs.main.models;

import javax.persistence.*;

@Entity
@Table(name = "ClientesCuentasBancarias")
public class ClienteCuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLICB_ClienteCuentaBancariaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CLICB_CLI_ClienteId", insertable = false, updatable = false, nullable = false)
    private int clienteId;

    @OneToOne
    @JoinColumn(name = "CLICB_CLI_ClienteId", insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "CLICB_BAN_BancoId", nullable = false)
    private int bancoId;

    @OneToOne
    @JoinColumn(name = "CLICB_BAN_BancoId", insertable = false, updatable = false, referencedColumnName = "BAN_BancoId")
    private Banco banco;

    @Column(name = "CLICB_Cuenta", nullable = false)
    private String cuenta;

    @Column(name = "CLICB_Activo", nullable = false)
    private boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getBancoId() {
        return bancoId;
    }

    public void setBancoId(int bancoId) {
        this.bancoId = bancoId;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
