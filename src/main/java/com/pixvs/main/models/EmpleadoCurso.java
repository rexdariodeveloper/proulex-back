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
@Table(name = "EmpleadosCursos")
public class EmpleadoCurso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPCU_EmpleadoCursoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPCU_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoId;

    //Idioma
    @OneToOne
    @JoinColumn(name = "EMPCU_CMM_IdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple idioma;

    @Column(name = "EMPCU_CMM_IdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer idiomaId;

    //Programa
    @OneToOne
    @JoinColumn(name = "EMPCU_PROG_ProgramaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "EMPCU_PROG_ProgramaId", nullable = false, insertable = true, updatable = true)
    private Integer programaId;

    @Column(name = "EMPCU_Comentarios", nullable = false, insertable = true, updatable = true)
    private String comentarios;

    @Column(name = "EMPCU_Activo", nullable = false, insertable = true, updatable = true)
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

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
