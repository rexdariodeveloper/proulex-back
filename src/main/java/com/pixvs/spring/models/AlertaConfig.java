package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "AlertasConfig")
public class AlertaConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALC_AlertaCId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ALC_Nombre", nullable = false, insertable = true, updatable = true, length = 100)
    private String nombre;

    @Column(name = "ALC_Descripcion", nullable = false, insertable = true, updatable = true, length = 255)
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "ALC_MP_NodoId", referencedColumnName = "MP_NodoId", insertable = false, updatable = false)
    private MenuPrincipal nodo;

    @Column(name = "ALC_MP_NodoId", nullable = false, insertable = true, updatable = true)
    private Integer nodoId;

    @Column(name = "ALC_CMM_TipoConfigAlertaId", nullable = false, insertable = true, updatable = true)
    private Integer tipoConfigAlertaId;

    @Column(name = "ALC_CMM_TipoMovimiento", nullable = true, insertable = true, updatable = true)
    private Integer tipoMovimientoId;

    @Column(name = "ALC_TablaReferencia", nullable = false, insertable = true, updatable = true, length = 150)
    private String tablaReferencia;

    @Column(name = "ALC_CampoId", nullable = false, insertable = true, updatable = true, length = 50)
    private String campoId;

    @Column(name = "ALC_CampoCodigo", nullable = true, insertable = true, updatable = true, length = 40)
    private String campoCodigo;

    @Column(name = "ALC_CampoEstado", nullable = false, insertable = true, updatable = true, length = 100)
    private String campoEstado;

    @Column(name = "ALC_CampoController", nullable = false, insertable = true, updatable = true, length = 100)
    private String campoController;

    @Column(name = "ALC_CampoEmpCreadoPor", nullable = true, insertable = true, updatable = true, length = 50)
    private String campoCreadoPor;

    @Column(name = "ALC_CampoEmpAutorizadoPor", nullable = true, insertable = true, updatable = true, length = 50)
    private String campoAutorizadoPor;

    @Column(name = "ALC_CampoFechaAutorizacion", nullable = true, insertable = true, updatable = true, length = 50)
    private String campoFechaAutorizacion;

    @Column(name = "ALC_CMM_EstadoAutorizado", nullable = true, insertable = true, updatable = true)
    private Integer estadoAutorizadoId;

    @Column(name = "ALC_CMM_EstadoEnProceso", nullable = true, insertable = true, updatable = true)
    private Integer estadoEnProcesoId;

    @Column(name = "ALC_CMM_EstadoRechazado", nullable = true, insertable = true, updatable = true)
    private Integer estadoRechazadoId;

    @Column(name = "ALC_CMM_EstadoEnRevision", nullable = true, insertable = true, updatable = true)
    private Integer estadoEnRevisionId;

    @Column(name = "ALC_AplicaSucursales", nullable = true, insertable = true, updatable = true)
    private Boolean aplicaSucursales;

    @Column(name = "ALC_LOGP_LogProcesoId", nullable = true, insertable = true, updatable = true)
    private Integer logProcesoId;

    @Column(name = "ALC_SPFinal", nullable = true, insertable = true, updatable = true, length = 50)
    private String spFinal;

    @Column(name = "ALC_URL_Alerta", nullable = true, insertable = false, updatable = false)
    private String urlAlerta;

    @Column(name = "ALC_URL_Notificacion", nullable = true, insertable = false, updatable = false)
    private String urlNotificacion;

    @Column(name = "ALC_URL_Documento", nullable = true, insertable = false, updatable = false)
    private String urlDocumento;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALC_FechaCreacion", nullable = false, insertable = true, updatable = true)
    private Date fechaCreacion;

    @Column(name = "ALC_USU_CreadoPorId", nullable = false, insertable = true, updatable = true)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALC_FechaUltimaModificacion", nullable = true, insertable = true, updatable = true)
    private Date fechaModificacion;

    @Column(name = "ALC_USU_ModificadoPorId", nullable = true, insertable = true, updatable = true)
    private Integer modificadoPor;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getNodoId() { return nodoId; }
    public void setNodoId(Integer nodoId) { this.nodoId = nodoId; }

    public Integer getTipoConfigAlertaId() { return tipoConfigAlertaId; }
    public void setTipoConfigAlertaId(Integer tipoConfigAlertaId) { this.tipoConfigAlertaId = tipoConfigAlertaId; }

    public Integer getTipoMovimientoId() { return tipoMovimientoId; }
    public void setTipoMovimientoId(Integer tipoMovimientoId) { this.tipoMovimientoId = tipoMovimientoId; }

    public String getTablaReferencia() { return tablaReferencia; }
    public void setTablaReferencia(String tablaReferencia) { this.tablaReferencia = tablaReferencia; }

    public String getCampoId() { return campoId; }
    public void setCampoId(String campoId) { this.campoId = campoId; }

    public String getCampoCodigo() { return campoCodigo; }
    public void setCampoCodigo(String campoCodigo) { this.campoCodigo = campoCodigo; }

    public String getCampoEstado() { return campoEstado; }
    public void setCampoEstado(String campoEstado) { this.campoEstado = campoEstado; }

    public String getCampoCreadoPor() { return campoCreadoPor; }
    public void setCampoCreadoPor(String campoCreadoPor) { this.campoCreadoPor = campoCreadoPor; }

    public String getCampoAutorizadoPor() { return campoAutorizadoPor; }
    public void setCampoAutorizadoPor(String campoAutorizadoPor) { this.campoAutorizadoPor = campoAutorizadoPor; }

    public String getCampoFechaAutorizacion() { return campoFechaAutorizacion; }
    public void setCampoFechaAutorizacion(String campoFechaAutorizacion) { this.campoFechaAutorizacion = campoFechaAutorizacion; }

    public Integer getEstadoAutorizadoId() { return estadoAutorizadoId; }
    public void setEstadoAutorizadoId(Integer estadoAutorizadoId) { this.estadoAutorizadoId = estadoAutorizadoId; }

    public Integer getEstadoEnProcesoId() { return estadoEnProcesoId; }
    public void setEstadoEnProcesoId(Integer estadoEnProcesoId) { this.estadoEnProcesoId = estadoEnProcesoId; }

    public Integer getEstadoRechazadoId() { return estadoRechazadoId; }
    public void setEstadoRechazadoId(Integer estadoRechazadoId) { this.estadoRechazadoId = estadoRechazadoId; }

    public Integer getEstadoEnRevisionId() { return estadoEnRevisionId; }
    public void setEstadoEnRevisionId(Integer estadoEnRevisionId) { this.estadoEnRevisionId = estadoEnRevisionId; }

    public String getCampoController() {   return campoController;  }

    public void setCampoController(String campoController) { this.campoController = campoController; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Integer getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Integer modificadoPor) { this.modificadoPor = modificadoPor; }

    public Boolean getAplicaSucursales() {
        return aplicaSucursales;
    }
    public void setAplicaSucursales(Boolean aplicaSucursales) {
        this.aplicaSucursales = aplicaSucursales;
    }

    public Integer getLogProcesoId() {
        return logProcesoId;
    }

    public void setLogProcesoId(Integer logProcesoId) {
        this.logProcesoId = logProcesoId;
    }

    public String getSpFinal() {
        return spFinal;
    }

    public void setSpFinal(String spFinal) {
        this.spFinal = spFinal;
    }

    public MenuPrincipal getNodo() {
        return nodo;
    }

    public void setNodo(MenuPrincipal nodo) {
        this.nodo = nodo;
    }

    public String getUrlAlerta() { return urlAlerta; }
    public void setUrlAlerta(String urlAlerta) { this.urlAlerta = urlAlerta; }

    public String getUrlNotificacion() { return urlNotificacion; }
    public void setUrlNotificacion(String urlNotificacion) { this.urlNotificacion = urlNotificacion; }

    public String getUrlDocumento() { return urlDocumento; }
    public void setUrlDocumento(String urlDocumento) { this.urlDocumento = urlDocumento; }
}

