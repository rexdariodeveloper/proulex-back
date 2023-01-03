package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 25/06/2020.
 */
@Entity
@Table(name = "ArticulosSubcategorias")
public class ArticuloSubcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASC_SubcategoriaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ASC_ACAT_CategoriaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ACAT_CategoriaId")
    private ArticuloCategoria categoria;

    @Column(name = "ASC_ACAT_CategoriaId", nullable = true, insertable = true, updatable = true)
    private Integer categoriaId;

    @Column(name = "ASC_Prefijo", nullable = false, length = 10, insertable = true, updatable = true)
    private String prefijo;

    @Column(name = "ASC_Nombre", nullable = false, length = 50, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "ASC_Descripcion", nullable = false, length = 255, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "ASC_Activo", nullable = false, insertable = false, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "ASC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ASC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ASC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ASC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ASC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ASC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "ASC_ARC_ImagenId", nullable = true, insertable = true, updatable = true)
    private Integer archivoId;

    @OneToOne
    @JoinColumn(name = "ASC_ARC_ImagenId", referencedColumnName = "ARC_ArchivoId", nullable = true, insertable = false, updatable = false)
    private Archivo archivo;

    @Transient
    private String img64;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArticuloCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ArticuloCategoria categoria) {
        this.categoria = categoria;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Integer getArchivoId() { return archivoId; }
    public void setArchivoId(Integer archivoId) { this.archivoId = archivoId; }

    public Archivo getArchivo() { return archivo; }
    public void setArchivo(Archivo archivo) { this.archivo = archivo; }

    public String getImg64() { return img64; }
    public void setImg64(String img64) { this.img64 = img64; }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }
}
