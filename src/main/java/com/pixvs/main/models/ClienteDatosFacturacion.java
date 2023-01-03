package com.pixvs.main.models;

import javax.persistence.*;

@Entity
@Table(name = "ClientesDatosFacturacion")
public class ClienteDatosFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDF_ClienteDatosFacturacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CDF_CLI_ClienteId", nullable = false, insertable = false, updatable = false)
    private Integer clienteId;

    @OneToOne
    @JoinColumn(name = "CDF_CLI_ClienteId", insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "CDF_DF_DatosFacturacionId", nullable = false, insertable = false, updatable = false)
    private Integer datosFacturacionId;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "CDF_DF_DatosFacturacionId", referencedColumnName = "DF_DatosFacturacionId", nullable = false)
    private DatosFacturacion datosFacturacion;

    @Column(name = "CDF_Predeterminado", nullable = false)
    private boolean predeterminado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getDatosFacturacionId() {
        return datosFacturacionId;
    }

    public void setDatosFacturacionId(Integer datosFacturacionId) {
        this.datosFacturacionId = datosFacturacionId;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}