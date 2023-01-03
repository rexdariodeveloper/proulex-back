package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Tabuladores")
public class Tabulador {

    /** Properties **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAB_TabuladorId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TAB_Codigo", nullable = false)
    private String codigo;

    @Column(name = "TAB_Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "TAB_Activo", nullable = false)
    private Boolean activo;

    @Column(name = "TAB_PagoDiasFestivos", nullable = false)
    private Boolean pagoDiasFestivos;

    @OneToOne
    @JoinColumn(name = "TAB_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "TAB_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @OneToOne
    @JoinColumn(name = "TAB_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "TAB_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TAB_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TAB_FechaModificacion")
    private Date fechaModificacion;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABD_TAB_TabuladorId", referencedColumnName = "TAB_TabuladorId", nullable = false)
    private List<TabuladorDetalle> detalles = new ArrayList<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "TABC_TAB_TabuladorId", referencedColumnName = "TAB_TabuladorId", nullable = false)
    private List<TabuladorCurso> cursos = new ArrayList<>();

    @Transient
    private Boolean actualizarGrupos;

    /** Methods **/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public int getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(int creadoPorId) {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<TabuladorDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<TabuladorDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<TabuladorCurso> getCursos() {
        return cursos;
    }

    public void setCursos(List<TabuladorCurso> cursos) {
        this.cursos = cursos;
    }

    public Boolean getPagoDiasFestivos() {
        return pagoDiasFestivos;
    }

    public void setPagoDiasFestivos(Boolean pagoDiasFestivos) {
        this.pagoDiasFestivos = pagoDiasFestivos;
    }

    public Boolean getActualizarGrupos() {
        return actualizarGrupos;
    }

    public void setActualizarGrupos(Boolean actualizarGrupos) {
        this.actualizarGrupos = actualizarGrupos;
    }
}
