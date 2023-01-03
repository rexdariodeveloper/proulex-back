package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALE_AlertaId", updatable = false)
    private Integer id;


    @Column(name = "ALE_ALC_AlertaCId", nullable = false, updatable = true)
    private Integer alertaCId;


    @Column(name = "ALE_ReferenciaProcesoId", nullable = false, updatable = true)
    private Integer referenciaProcesoId;

    @Column(name = "ALE_CodigoTramite", nullable = true)
    private String codigoTramite;


    @Column(name = "ALE_CMM_EstatusId", nullable = false)
    private Integer estatusAlertaId;

    @OneToOne
    @JoinColumn(name = "ALE_ACE_AlertaConfiguracionEtapaId", referencedColumnName = "ACE_AlertaConfiguracionEtapaId", insertable = false, updatable = false)
    private AlertaConfigEtapa alertaConfigEtapa;

    @Column(name = "ALE_ACE_AlertaConfiguracionEtapaId", nullable = false, updatable = true)
    private Integer alertaConfigEtapaId;

    @OneToOne
    @JoinColumn(name = "ALE_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatusAlerta;


    @Column(name = "ALE_TextoRepresentativo", nullable = true)
    private String textoRepresentativo;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALE_FechaInicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "ALE_Origen")
    private String origen;


    @Column(name = "ALE_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ALE_USU_CreadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario creadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALE_FechaUltimaModificacion", nullable = true)
    private Date fechaUltimaModificacion;

    @Column(name = "ALE_USU_ModificadoPorId", nullable = true)
    private Integer modificadoPorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALE_FechaAutorizacion", nullable = true)
    private Date fechaAutorizacion;

    @OneToOne
    @JoinColumn(name="ALE_ALC_AlertaCId", referencedColumnName = "ALC_AlertaCId",insertable=false, updatable = false)
    private AlertaConfig config;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="ALD_ALE_AlertaId", referencedColumnName = "ALE_AlertaId", nullable = false, insertable = false, updatable = false)
    private List<AlertaDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlertaCId() {
        return alertaCId;
    }

    public void setAlertaCId(Integer alertaCId) {
        this.alertaCId = alertaCId;
    }

    public Integer getReferenciaProcesoId() {
        return referenciaProcesoId;
    }

    public void setReferenciaProcesoId(Integer referenciaProcesoId) {
        this.referenciaProcesoId = referenciaProcesoId;
    }

    public String getCodigoTramite() {
        return codigoTramite;
    }

    public void setCodigoTramite(String codigoTramite) {
        this.codigoTramite = codigoTramite;
    }

    public Integer getEstatusAlertaId() {
        return estatusAlertaId;
    }

    public void setEstatusAlertaId(Integer estatusAlertaId) {
        this.estatusAlertaId = estatusAlertaId;
    }

    public ControlMaestroMultiple getEstatusAlerta() {
        return estatusAlerta;
    }

    public void setEstatusAlerta(ControlMaestroMultiple estatusAlerta) {
        this.estatusAlerta = estatusAlerta;
    }

    public String getTextoRepresentativo() {
        return textoRepresentativo;
    }

    public void setTextoRepresentativo(String textoRepresentativo) {
        this.textoRepresentativo = textoRepresentativo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public List<AlertaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<AlertaDetalle> detalles) {
        this.detalles = detalles;
    }

    public AlertaConfig getConfig() {
        return config;
    }

    public void setConfig(AlertaConfig config) {
        this.config = config;
    }

    public Integer getAlertaConfigEtapaId() {
        return alertaConfigEtapaId;
    }

    public void setAlertaConfigEtapaId(Integer alertaConfigEtapaId) {
        this.alertaConfigEtapaId = alertaConfigEtapaId;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public AlertaConfigEtapa getAlertaConfigEtapa() {
        return alertaConfigEtapa;
    }

    public void setAlertaConfigEtapa(AlertaConfigEtapa alertaConfigEtapa) {
        this.alertaConfigEtapa = alertaConfigEtapa;
    }
}
