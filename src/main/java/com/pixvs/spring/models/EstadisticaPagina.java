package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EstadisticasPaginas")
public class EstadisticaPagina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EP_EstadisticaPaginaId", nullable = false, updatable = false, insertable = false)
    private Integer id;

    @Column(name = "EP_URL", nullable = false)
    private String url;

    @Column(name = "EP_IP", nullable = true)
    private String ip;

    @Column(name = "EP_Dispositivo", nullable = true)
    private String dispositivo;

    @Column(name = "EP_OS", nullable = true)
    private String os;

    @Column(name = "EP_OS_Version", nullable = true)
    private String osVersion;

    @Column(name = "EP_Browser", nullable = true)
    private String browser;

    @Column(name = "EP_CMM_TipoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoId;

    @OneToOne
    @JoinColumn(name = "EP_CMM_TipoId", referencedColumnName = "CMM_ControlId", nullable = true, insertable = false, updatable = false)
    private ControlMaestroMultiple tipo;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "EP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public ControlMaestroMultiple getTipo() {
        return tipo;
    }

    public void setTipo(ControlMaestroMultiple tipo) {
        this.tipo = tipo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
