package com.pixvs.spring.models;

import com.pixvs.main.models.Usuario;

import javax.persistence.*;

@Entity
@Table(name = "AlertasConfigEtapaNotificadores")
public class AlertaConfigEtapaNotificadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACEN_AlertaEtapaNotificadorId",nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ACEN_USU_NotificadorId", nullable = true, insertable = true, updatable = true)
    private Integer usuarioNotificacionId;

    @Column(name = "ACEN_ACE_AlertaConfiguracionEtapaId", nullable = false, insertable = false, updatable = false)
    private Integer alertaConfiguracionId;

    @Column(name = "ACEN_CMM_TipoNotificacionAlertaId", nullable = false, insertable = true, updatable = false)
    private Integer tipoNotificacionAlerta;

    @OneToOne
    @JoinColumn(name = "ACEN_USU_NotificadorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario notificador;

    @Column(name = "ACEN_Activo", nullable = true, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Boolean getActivo() { return activo;}
    public void setActivo(Boolean activo) { this.activo = activo;}

    public Integer getUsuarioNotificacionId() {  return usuarioNotificacionId;  }

    public void setUsuarioNotificacionId(Integer usuarioNotificacionId) { this.usuarioNotificacionId = usuarioNotificacionId;  }

    public Integer getAlertaConfiguracionId() { return alertaConfiguracionId; }

    public void setAlertaConfiguracionId(Integer alertaConfiguracionId) { this.alertaConfiguracionId = alertaConfiguracionId; }

    public Integer getTipoNotificacionAlerta() {  return tipoNotificacionAlerta;  }

    public void setTipoNotificacionAlerta(Integer tipoNotificacionAlerta) { this.tipoNotificacionAlerta = tipoNotificacionAlerta; }

    public Usuario getNotificador() { return notificador; }

    public void setNotificador(Usuario notificador) { this.notificador = notificador;  }
}

