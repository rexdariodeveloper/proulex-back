package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "EmpleadosCategorias")
public class EmpleadoCategoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPCA_EmpleadoCategoriaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPCA_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoId;

    //Idioma
    @OneToOne
    @JoinColumn(name = "EMPCA_CMM_IdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple idioma;

    @Column(name = "EMPCA_CMM_IdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer idiomaId;

    @OneToOne
    @JoinColumn(name = "EMPCA_PAPC_ProfesorCategoriaId",  insertable = false, updatable = false, referencedColumnName = "PAPC_ProfesorCategoriaId")
    private PAProfesorCategoria categoria;

    @Column(name = "EMPCA_PAPC_ProfesorCategoriaId")
    private Integer categoriaId;

    @Column(name = "EMPCA_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public ControlMaestroMultiple getIdioma() {
        return idioma;
    }

    public void setIdioma(ControlMaestroMultiple idioma) {
        this.idioma = idioma;
    }

    public Integer getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }

    public PAProfesorCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(PAProfesorCategoria categoria) {
        this.categoria = categoria;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
