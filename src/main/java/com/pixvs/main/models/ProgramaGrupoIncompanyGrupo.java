package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyGrupos")
public class ProgramaGrupoIncompanyGrupo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCG_PGINC_ProgramaIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer programaGrupoIncompany;

    //Idioma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_PROGI_ProgramaIdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma programaIdioma;

    @Column(name = "PGINCG_PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer programaIdiomaId;

    @Column(name = "PGINCG_Codigo", length = 100, nullable = true, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "PGINCG_Nombre", length = 100, nullable = true, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PGINCG_Nivel", nullable = true, insertable = true, updatable = true)
    private Integer nivel;

    @Column(name = "PGINCG_Alias", length = 100, nullable = true, insertable = true, updatable = true)
    private String alias;

    @Column(name = "PGINCG_Horario", nullable = true, insertable = true, updatable = true)
    private String horario;

    @Column(name = "PGINCG_Borrado", nullable = false, insertable = true, updatable = true)
    private Boolean borrado;

    //Modalidad
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "PGINCG_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer modalidadId;

    //Tipo grupo
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_CMM_TipoGrupoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoGrupo;

    @Column(name = "PGINCG_CMM_TipoGrupoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoGrupoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCG_FechaInicio", nullable = true, insertable = true, updatable = true)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCG_FechaFin", nullable = true, insertable = true, updatable = true)
    private Date fechaFin;

    @Column(name = "PGINCG_CalificacionMinima", nullable = true, insertable = true, updatable = true)
    private BigDecimal calificacionMinima;

    @Column(name = "PGINCG_FaltasPermitida", nullable = true, insertable = true, updatable = true)
    private Integer faltasPermitidas;

    @Column(name = "PGINCG_Cupo", nullable = true, insertable = true, updatable = true)
    private Integer cupo;

    //Plataforma
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_CMM_PlataformaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple plataforma;

    @Column(name = "PGINCG_CMM_PlataformaId", nullable = true, insertable = true, updatable = true)
    private Integer plataformaId;

    //Empleado
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PGINCG_EMP_EmpleadoId", nullable = true, insertable = true, updatable = true)
    private Integer empleadoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PGINCG_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PGINCG_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    /*@OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCH_PGINCG_ProgramaIncompanyGrupoId", referencedColumnName = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyHorario> horarios;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCE_PGINCG_ProgramaIncompanyGrupoId", referencedColumnName = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyCriterioEvaluacion> criteriosEvaluacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCL_PGINCG_ProgramaIncompanyGrupoId", referencedColumnName = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyClaseCancelada> clasesCanceladas;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCR_PGINCG_ProgramaIncompanyGrupoId", referencedColumnName = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyClaseReprogramada> clasesReprogramadas;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCM_PGINCG_ProgramaIncompanyGrupoId", referencedColumnName = "PGINCG_ProgramaIncompanyGrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyMaterial> materiales;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaGrupoIncompany() {
        return programaGrupoIncompany;
    }

    public void setProgramaGrupoIncompany(Integer programaGrupoIncompany) {
        this.programaGrupoIncompany = programaGrupoIncompany;
    }

    public ProgramaIdioma getProgramaIdioma() {
        return programaIdioma;
    }

    public void setProgramaIdioma(ProgramaIdioma programaIdioma) {
        this.programaIdioma = programaIdioma;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public PAModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(PAModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public ControlMaestroMultiple getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(ControlMaestroMultiple tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public Integer getTipoGrupoId() {
        return tipoGrupoId;
    }

    public void setTipoGrupoId(Integer tipoGrupoId) {
        this.tipoGrupoId = tipoGrupoId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getCalificacionMinima() {
        return calificacionMinima;
    }

    public void setCalificacionMinima(BigDecimal calificacionMinima) {
        this.calificacionMinima = calificacionMinima;
    }

    public Integer getFaltasPermitidas() {
        return faltasPermitidas;
    }

    public void setFaltasPermitidas(Integer faltasPermitidas) {
        this.faltasPermitidas = faltasPermitidas;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public ControlMaestroMultiple getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(ControlMaestroMultiple plataforma) {
        this.plataforma = plataforma;
    }

    public Integer getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(Integer plataformaId) {
        this.plataformaId = plataformaId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
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

    /*public List<ProgramaGrupoIncompanyHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<ProgramaGrupoIncompanyHorario> horarios) {
        this.horarios = horarios;
    }

    public List<ProgramaGrupoIncompanyCriterioEvaluacion> getCriteriosEvaluacion() {
        return criteriosEvaluacion;
    }

    public void setCriteriosEvaluacion(List<ProgramaGrupoIncompanyCriterioEvaluacion> criteriosEvaluacion) {
        this.criteriosEvaluacion = criteriosEvaluacion;
    }

    public List<ProgramaGrupoIncompanyClaseCancelada> getClasesCanceladas() {
        return clasesCanceladas;
    }

    public void setClasesCanceladas(List<ProgramaGrupoIncompanyClaseCancelada> clasesCanceladas) {
        this.clasesCanceladas = clasesCanceladas;
    }

    public List<ProgramaGrupoIncompanyClaseReprogramada> getClasesReprogramadas() {
        return clasesReprogramadas;
    }

    public void setClasesReprogramadas(List<ProgramaGrupoIncompanyClaseReprogramada> clasesReprogramadas) {
        this.clasesReprogramadas = clasesReprogramadas;
    }

    public List<ProgramaGrupoIncompanyMaterial> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<ProgramaGrupoIncompanyMaterial> materiales) {
        this.materiales = materiales;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }*/
}
