package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/06/2020.
 */
@Entity
@Table(name = "MenuListadosGenerales")
public class MenuListadoGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLG_ListadoGeneralNodoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "MLG_NodoPadreId", nullable = true, insertable = true, updatable = true)
    private Integer nodoPadreId;

    @Column(name = "MLG_Titulo", length = 255, nullable = false, insertable = true, updatable = true)
    private String titulo;

    @Column(name = "MLG_TituloEN", length = 255, nullable = false, insertable = true, updatable = true)
    private String tituloEN;

    @Column(name = "MLG_Activo", nullable = true, insertable = true, updatable = false)
    private Boolean activo;

    @Column(name = "MLG_Icono", length = 255, nullable = true, insertable = true, updatable = true)
    private String icono;

    @Column(name = "MLG_Orden", nullable = false, insertable = true, updatable = true)
    private Integer orden;

    @OneToOne
    @JoinColumn(name = "MLG_CMM_TipoNodoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoNodo;

    @Column(name = "MLG_CMM_TipoNodoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoNodoId;

    @Column(name = "MLG_NombreTablaCatalogo", nullable = true, insertable = true, updatable = true)
    private String nombreTablaCatalogo;

    @Column(name = "MLG_CMM_ControlCatalogo", nullable = true, insertable = true, updatable = true)
    private String cmmControlCatalogo;

    @Column(name = "MLG_PermiteBorrar", nullable = false, insertable = false, updatable = false)
    private Boolean permiteBorrar;

    @Column(name = "MLG_UrlAPI", nullable = false, insertable = false, updatable = false)
    private String urlAPI;


    @CreationTimestamp
    @Column(name = "MLG_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "MLG_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "MLG_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "MLG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "MLG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "MLG_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Transient
    private List<MenuListadoGeneral> children = new LinkedList<MenuListadoGeneral>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodoPadreId() {
        return nodoPadreId;
    }

    public void setNodoPadreId(Integer nodoPadreId) {
        this.nodoPadreId = nodoPadreId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloEN() {
        return tituloEN;
    }

    public void setTituloEN(String tituloEN) {
        this.tituloEN = tituloEN;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public ControlMaestroMultiple getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(ControlMaestroMultiple tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public Integer getTipoNodoId() {
        return tipoNodoId;
    }

    public void setTipoNodoId(Integer tipoNodoId) {
        this.tipoNodoId = tipoNodoId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<MenuListadoGeneral> getChildren() {
        return children;
    }

    public void setChildren(List<MenuListadoGeneral> children) {
        this.children = children;
    }

    public String getNombreTablaCatalogo() {
        return nombreTablaCatalogo;
    }

    public void setNombreTablaCatalogo(String nombreTablaCatalogo) {
        this.nombreTablaCatalogo = nombreTablaCatalogo;
    }

    public String getCmmControlCatalogo() {
        return cmmControlCatalogo;
    }

    public void setCmmControlCatalogo(String cmmControlCatalogo) {
        this.cmmControlCatalogo = cmmControlCatalogo;
    }

    public Boolean getPermiteBorrar() {
        return permiteBorrar;
    }

    public void setPermiteBorrar(Boolean permiteBorrar) {
        this.permiteBorrar = permiteBorrar;
    }

    public String getUrlAPI() {
        return urlAPI;
    }

    public void setUrlAPI(String urlAPI) {
        this.urlAPI = urlAPI;
    }

}
