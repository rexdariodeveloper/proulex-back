package com.pixvs.spring.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ControlesMaestros")
public class ControlMaestro {

    @Id
    @Column(name = "CMA_Nombre", nullable = false, updatable = false, insertable = true)
    private String nombre;

    @Column(name = "CMA_Valor", nullable = false, updatable = true, insertable = true)
    private String valor;

    @Column(name = "CMA_Sistema", nullable = false, updatable = false, insertable = true)
    private Boolean sistema;

    @UpdateTimestamp
    @Column(name = "CMA_FechaModificacion", nullable = false, insertable = true, updatable = true)
    private Date fechaModificacion;

    @Column(name = "CMA_Timestamp", nullable = false, updatable = false, insertable = false)
    private String timestamp;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public Boolean getValorAsBoolean() {
        if(valor.equals("1") || valor.equals("true")){
            return true;
        }
        if(valor.equals("0") || valor.equals("false")){
            return false;
        }
        return false;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getSistema() {
        return sistema;
    }

    public void setSistema(Boolean sistema) {
        this.sistema = sistema;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
