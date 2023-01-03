package com.pixvs.spring.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "AlertasDetalles")
public class AlertaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALD_AlertaDetalleId", updatable = false)
    private Integer id;

    @Column(name = "ALD_ALE_AlertaId", nullable = false, updatable = false)
    private Integer alertaId;


    @Column(name = "ALD_CMM_EstatusId", nullable = true, updatable = true)// ALD_CMM_EstatusDetalleId
    private Integer estatusDetalleId;

    @OneToOne
    @JoinColumn(name = "ALD_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatusDetalle;

    @OneToOne
    @JoinColumn(name = "ALD_ALE_AlertaId", referencedColumnName = "ALE_AlertaId", insertable = false, updatable = false)
    private Alerta alerta;


    @Column(name = "ALD_USU_UsuarioId", nullable = false, updatable = true)
    private Integer usuarioId;

    @Column(name = "ALD_FechaAtendido", nullable = true)
    private Timestamp fechaAtendido;

    @Column(name = "ALD_Archivado", nullable = false)
    private Boolean archivado;


    @Column(name = "ALD_Mostrar", nullable = false)
    private Boolean mostrar;

    @Column(name = "ALD_Visto", nullable = false)
    private Boolean visto;


    @Column(name = "ALD_Comentario", nullable = true)
    private String comentario;

    @CreationTimestamp
    @Column(name = "ALD_FechaCreacion", nullable = false, updatable = false)
    private Timestamp fechaCreacion;


    @Column(name = "ALD_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    @Column(name = "ALD_CMM_TipoAlertaId", nullable = false, updatable = false)
    private Integer tipoAlertaId;


    @Column(name = "ALD_FechaUltimaModificacion", nullable = true)
    private Timestamp fechaUltimaModificacion;


    @Column(name = "ALD_USU_ModificadoPorId", nullable = true)
    private Integer modificadoPorId;

    @Column(name = "ALD_Timestamp", nullable = true, insertable = false, updatable = false)
    private String timestamp;

    @OneToOne
    @JoinColumn(name = "ALD_ACE_AlertaConfiguracionEtapaId", referencedColumnName = "ACE_AlertaConfiguracionEtapaId", insertable = false, updatable = false)
    private AlertaConfigEtapa etapa;

    @Column(name = "ALD_ACE_AlertaConfiguracionEtapaId", nullable = true, updatable = false)
    private Integer etapaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Integer etapaId) {
        this.etapaId = etapaId;
    }

    public Integer getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(Integer alertaId) {
        this.alertaId = alertaId;
    }

    public Integer getEstatusDetalleId() {   return estatusDetalleId;    }

    public void setEstatusDetalleId(Integer estatusDetalleId) {
        this.estatusDetalleId = estatusDetalleId;
    }

    public ControlMaestroMultiple getEstatusDetalle() {
        return estatusDetalle;
    }

    public void setEstatusDetalle(ControlMaestroMultiple estatusDetalle) {
        this.estatusDetalle = estatusDetalle;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Timestamp getFechaAtendido() {
        return fechaAtendido;
    }

    public void setFechaAtendido(Timestamp fechaAtendido) {
        this.fechaAtendido = fechaAtendido;
    }

    public Boolean getArchivado() {
        return archivado;
    }

    public void setArchivado(Boolean archivado) {
        this.archivado = archivado;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Timestamp getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Timestamp fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTipoAlertaId() {
        return tipoAlertaId;
    }

    public void setTipoAlertaId(Integer tipoAlertaId) {
        this.tipoAlertaId = tipoAlertaId;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public AlertaConfigEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(AlertaConfigEtapa etapa) {
        this.etapa = etapa;
    }
}
