package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AlertasConfigEtapa")
public class AlertaConfigEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACE_AlertaConfiguracionEtapaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ACE_ALC_AlertaCId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALC_AlertaCId")
    private AlertaConfig alertaConfig;

    @Column(name = "ACE_ALC_AlertaCId", nullable = false, insertable = true, updatable = true)
    private Integer alertaConfigId;

    @Column(name = "ACE_Orden", nullable = false, insertable = true, updatable = true)
    private Integer orden;

    @OneToOne/* Usuario o Departamento*/
    @JoinColumn(name = "ACE_CMM_TipoAprobacion", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoAprobacion;

    @Column(name = "ACE_CMM_TipoAprobacion", nullable = true, insertable = true, updatable = true)
    private Integer tipoAprobacionId;

    @OneToOne/* Secuencial o Paralela*/
    @JoinColumn(name = "ACE_CMM_TipoOrden", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoOrden;

    @Column(name = "ACE_CMM_TipoOrden", nullable = true, insertable = true, updatable = true)
    private Integer tipoOrdenId;

    @OneToOne/* Solo va aplicar cuando es paralela */
    @JoinColumn(name = "ACE_CMM_CondicionAprobacion", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    @Where(clause = "ACE_CMM_CondicionAprobacion != 1000123")
    private ControlMaestroMultiple condicionAprobacion;

    @Column(name = "ACE_CMM_CondicionAprobacion", nullable = true, insertable = true, updatable = true)
    private Integer condicionAprobacionId;

    @Column(name = "ACE_CriteriosEconomicos", nullable = true, insertable = true, updatable = true)
    private Boolean criteriosEconomicos;

    @Column(name = "ACE_NotificarCreador", nullable = true, insertable = true, updatable = true)
    private Boolean notificarCreador;

    @Column(name = "ACE_MontoMinimo", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoMinimo;

    @Column(name = "ACE_MontoMaximo", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoMaximo;

    @OneToOne
    @JoinColumn(name = "ACE_CMM_TipoMonto", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMonto;

    @Column(name = "ACE_CMM_TipoMonto", nullable = true, insertable = true, updatable = true)
    private Integer tipoMontoId;

    @Column(name = "ACE_AutorizacionDirecta", nullable = false, insertable = true, updatable = true)
    private Boolean autorizacionDirecta;

    @OneToOne
    @JoinColumn(name = "ACE_CMM_EstatusReferenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatusReferencia;

    @Column(name = "ACE_CMM_EstatusReferenciaId", nullable = true, insertable = true, updatable = true)
    private Integer estatusReferenciaId;

    @OneToOne
    @JoinColumn(name = "ACE_CMM_TipoAlertaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoAlerta;

    @Column(name = "ACE_CMM_TipoAlertaId", nullable = false, insertable = true, updatable = true)
    private Integer tipoAlertaId;

    @Column(name = "ACE_SUC_SucursalId", insertable = true, updatable = true)
    private Integer sucursalId;

    @Column(name = "ACE_MostrarUsuario", nullable = true, insertable = true, updatable = true)
    private Boolean mostrarUsuario;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ACE_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ACE_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ACE_USU_CreadoPorId", nullable = false, insertable = true, updatable = true)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ACE_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "ACE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ACE_USU_ModificadoPorId", nullable = true, insertable = true, updatable = true)
    private Integer modificadoPorId;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="ACEA_ACE_AlertaConfiguracionEtapaId", referencedColumnName = "ACE_AlertaConfiguracionEtapaId", nullable = false)
    @Where(clause ="ACEA_Activo = 1")
    private List<AlertaConfigEtapaAprobadores> detalles;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="ACEN_ACE_AlertaConfiguracionEtapaId", referencedColumnName = "ACE_AlertaConfiguracionEtapaId", nullable = false)
    @Where(clause = "ACEN_Activo = 1")
    private List<AlertaConfigEtapaNotificadores> notificadores;

    @Column(name = "ACE_NotificacionCorreo", nullable = true, insertable = true, updatable = true)
    private Boolean notificacionCorreo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlertaConfigId() { return alertaConfigId; }
    public void setAlertaConfigId(Integer alertaConfigId) { this.alertaConfigId = alertaConfigId; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public ControlMaestroMultiple getTipoAprobacion() { return tipoAprobacion; }
    public void setTipoAprobacion(ControlMaestroMultiple tipoAprobacion) { this.tipoAprobacion = tipoAprobacion; }

    public Integer getTipoAprobacionId() { return tipoAprobacionId; }
    public void setTipoAprobacionId(Integer tipoAprobacionId) { this.tipoAprobacionId = tipoAprobacionId; }

    public ControlMaestroMultiple getTipoOrden() { return tipoOrden; }
    public void setTipoOrden(ControlMaestroMultiple tipoOrden) { this.tipoOrden = tipoOrden; }

    public Integer getTipoOrdenId() { return tipoOrdenId; }
    public void setTipoOrdenId(Integer tipoOrdenId) { this.tipoOrdenId = tipoOrdenId; }

    public ControlMaestroMultiple getCondicionAprobacion() { return condicionAprobacion; }
    public void setCondicionAprobacion(ControlMaestroMultiple condicionAprobacion) { this.condicionAprobacion = condicionAprobacion; }

    public Integer getCondicionAprobacionId() { return condicionAprobacionId; }
    public void setCondicionAprobacionId(Integer condicionAprobacionId) { this.condicionAprobacionId = condicionAprobacionId; }

    public Boolean getCriteriosEconomicos() { return criteriosEconomicos; }
    public void setCriteriosEconomicos(Boolean criteriosEconomicos) { this.criteriosEconomicos = criteriosEconomicos; }

    public Boolean getNotificarCreador() {   return notificarCreador;   }
    public void setNotificarCreador(Boolean notificarCreador) {  this.notificarCreador = notificarCreador; }

    public BigDecimal getMontoMinimo() { return montoMinimo; }
    public void setMontoMinimo(BigDecimal montoMinimo) { this.montoMinimo = montoMinimo; }

    public BigDecimal getMontoMaximo() { return montoMaximo; }
    public void setMontoMaximo(BigDecimal montoMaximo) { this.montoMaximo = montoMaximo; }

    public ControlMaestroMultiple getTipoMonto() { return tipoMonto; }
    public void setTipoMonto(ControlMaestroMultiple tipoMonto) { this.tipoMonto = tipoMonto; }

    public Integer getTipoMontoId() { return tipoMontoId; }
    public void setTipoMontoId(Integer tipoMontoId) { this.tipoMontoId = tipoMontoId; }

    public Boolean getAutorizacionDirecta() { return autorizacionDirecta; }
    public void setAutorizacionDirecta(Boolean autorizacionDirecta) { this.autorizacionDirecta = autorizacionDirecta; }

    /*public Boolean getNotificarCreador() {  return notificarCreador;   }
    public void setNotificarCreador(Boolean notificarCreador) {  this.notificarCreador = notificarCreador; }*/

    public ControlMaestroMultiple getEstatusReferencia() { return estatusReferencia; }
    public void setEstatusReferencia(ControlMaestroMultiple estatusReferencia) { this.estatusReferencia = estatusReferencia; }

    public Integer getEstatusReferenciaId() { return estatusReferenciaId; }
    public void setEstatusReferenciaId(Integer estatusReferenciaId) { this.estatusReferenciaId = estatusReferenciaId; }

    public ControlMaestroMultiple getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(ControlMaestroMultiple tipoAlerta) { this.tipoAlerta = tipoAlerta; }

    public Integer getTipoAlertaId() { return tipoAlertaId; }
    public void setTipoAlertaId(Integer tipoAlertaId) { this.tipoAlertaId = tipoAlertaId; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Integer getSucursalId() {  return sucursalId;  }
    public void setSucursalId(Integer sucursalId) { this.sucursalId = sucursalId; }

    public List<AlertaConfigEtapaAprobadores> getDetalles() { return detalles; }
    public void setDetalles(List<AlertaConfigEtapaAprobadores> detalles) { this.detalles = detalles; }

    public List<AlertaConfigEtapaNotificadores> getNotificadores() { return notificadores; }
    public void setNotificadores(List<AlertaConfigEtapaNotificadores> notificadores) { this.notificadores = notificadores;  }

    public AlertaConfig getAlertaConfig() {
        return alertaConfig;
    }

    public void setAlertaConfig(AlertaConfig alertaConfig) {
        this.alertaConfig = alertaConfig;
    }

    public Boolean getMostrarUsuario() {
        return mostrarUsuario;
    }

    public void setMostrarUsuario(Boolean mostrarUsuario) {
        this.mostrarUsuario = mostrarUsuario;
    }

    public Boolean getNotificacionCorreo() {   return notificacionCorreo;   }

    public void setNotificacionCorreo(Boolean notificacionCorreo) {  this.notificacionCorreo = notificacionCorreo; }

}

