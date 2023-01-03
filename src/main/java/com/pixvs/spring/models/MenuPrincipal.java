package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.projections.RolMenu.RolMenuEditarProjection;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "MenuPrincipal")
public class MenuPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MP_NodoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "MP_Titulo", nullable = false)
    private String title;

    @Column(name = "MP_TituloEN", nullable = false)
    private String titleEN;

    @Transient
    private String translate;

    @Column(name = "MP_Tipo", nullable = false)
    private String type;

    @Column(name = "MP_Orden", nullable = false)
    private Integer orden;

    @Column(name = "MP_NodoPadreId")
    private Integer nodoPadreId;

    @Column(name = "MP_Repetible", nullable = false, insertable = false, updatable = false)
    private Boolean repetible;

    @Column(name = "MP_Personalizado", nullable = false, insertable = false, updatable = false)
    private Boolean personalizado;

    @Transient
    private List<MenuPrincipal> children = new LinkedList<MenuPrincipal>();

    @Transient
    private Boolean selected = false;

    @Transient
    private Object rolMenu;

    @Transient
    private Object rolMenuPermisos;

    @Column(name = "MP_CMM_SistemaAccesoId", nullable = false)
    private Integer sistemaAccesoId;

    @Column(name = "MP_Activo")
    private Boolean activo;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "MP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "MP_URL", nullable = false, insertable = true, updatable = false)
    private String url;

    @Column(name = "MP_Icono")
    private String icon;

    @Transient
    private List<EmpresaPermisoComponente> permisosComponentes = new LinkedList<EmpresaPermisoComponente>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTranslate() {
        return "M" + id;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getNodoPadreId() {
        return nodoPadreId;
    }

    public void setNodoPadreId(Integer nodoPadreId) {
        this.nodoPadreId = nodoPadreId;
    }

    public List<MenuPrincipal> getChildren() {
        return children;
    }

    public void setChildren(List<MenuPrincipal> children) {
        this.children = children;
    }

    public Integer getSistemaAccesoId() {
        return sistemaAccesoId;
    }

    public void setSistemaAccesoId(Integer sistemaAccesoId) {
        this.sistemaAccesoId = sistemaAccesoId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Object getRolMenu() {
        return rolMenu;
    }

    public void setRolMenu(Object rolMenu) {
        this.rolMenu = rolMenu;
    }

    public Object getRolMenuPermisos() { return rolMenuPermisos; }
    public void setRolMenuPermisos(Object rolMenuPermisos) { this.rolMenuPermisos = rolMenuPermisos; }

    public Boolean getRepetible() {
        return repetible;
    }

    public void setRepetible(Boolean repetible) {
        this.repetible = repetible;
    }

    public Boolean getPersonalizado() {
        return personalizado;
    }

    public void setPersonalizado(Boolean personalizado) {
        this.personalizado = personalizado;
    }

    public List<EmpresaPermisoComponente> getPermisosComponentes() {
        return permisosComponentes;
    }

    public void setPermisosComponentes(List<EmpresaPermisoComponente> permisosComponentes) {
        this.permisosComponentes = permisosComponentes;
    }
}