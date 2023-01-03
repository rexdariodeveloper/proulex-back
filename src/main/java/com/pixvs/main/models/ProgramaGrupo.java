package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGrupos")
public class ProgramaGrupo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGRU_PGINCG_ProgramaIncompanyId", nullable = true, insertable = false, updatable = false)
    private Integer grupoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "PROGRU_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_PROGI_ProgramaIdiomaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma programaIdioma;

    @Column(name = "PROGRU_PROGI_ProgramaIdiomaId", nullable = true, insertable = true, updatable = true)
    private Integer programaIdiomaId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_PAMOD_ModalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad paModalidad;

    @Column(name = "PROGRU_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer paModalidadId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_PAC_ProgramacionAcademicaComercialId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAC_ProgramacionAcademicaComercialId")
    private ProgramacionAcademicaComercial programacionAcademicaComercial;

    @Column(name = "PROGRU_PAC_ProgramacionAcademicaComercialId", nullable = true, insertable = true, updatable = true)
    private Integer programacionAcademicaComercialId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_FechaFin", nullable = false)
    private Date fechaFin;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_FechaFinInscripciones")
    private Date fechaFinInscripciones;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_FechaFinInscripcionesBecas")
    private Date fechaFinInscripcionesBecas;

    @Column(name = "PROGRU_Nivel", nullable = true, insertable = true, updatable = true)
    private Integer nivel;

    @Column(name = "PROGRU_Grupo", nullable = true, insertable = true, updatable = true)
    private Integer grupo;

    @Column(name = "PROGRU_CategoriaProfesor", nullable = true, insertable = true, updatable = true)
    private String categoriaProfesor;

    @Column(name = "PROGRU_SueldoProfesor", nullable = true, insertable = true, updatable = true)
    private BigDecimal sueldoProfesor;

    @Column(name = "PROGRU_Aula", nullable = true, insertable = true, updatable = true)
    private String aula;

    @Column(name = "PROGRU_Comentarios", nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @Column(name = "PROGRU_Alias", nullable = true, insertable = true, updatable = true)
    private String alias;

    @Column(name = "PROGRU_Nombre", nullable = true, insertable = true, updatable = true)
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_CMM_PlataformaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple plataforma;

    @Column(name = "PROGRU_CMM_PlataformaId", nullable = true, insertable = true, updatable = true)
    private Integer plataformaId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_SP_SucursalPlantelId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SP_SucursalPlantelId")
    private SucursalPlantel sucursalPlantel;

    @Column(name = "PROGRU_SP_SucursalPlantelId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalPlantelId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_PAMODH_PAModalidadHorarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAMODH_PAModalidadHorarioId")
    private PAModalidadHorario modalidadHorario;

    @Column(name = "PROGRU_PAMODH_PAModalidadHorarioId", nullable = true, insertable = true, updatable = true)
    private Integer modalidadHorarioId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_PACIC_CicloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PACIC_CicloId")
    private PACiclo paCiclo;

    @Column(name = "PROGRU_PACIC_CicloId", nullable = true, insertable = true, updatable = true)
    private Integer paCicloId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_CMM_TipoGrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoGrupo;

    @Column(name = "PROGRU_CMM_TipoGrupoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoGrupoId;

    @Column(name = "PROGRU_Cupo", nullable = true, insertable = true, updatable = true)
    private Integer cupo;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRU_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PROGRU_EMP_EmpleadoId", nullable = true, insertable = true, updatable = true)
    private Integer empleadoId;

    @Column(name = "PROGRU_Multisede", nullable = false, insertable = true, updatable = true)
    private Boolean multisede;

    @Column(name = "PROGRU_CalificacionMinima", nullable = true, insertable = true, updatable = true)
    private Integer calificacionMinima;

    @Column(name = "PROGRU_FaltasPermitidas", nullable = true, insertable = true, updatable = true)
    private BigDecimal faltasPermitidas;

    @Column(name = "PROGRU_GrupoReferenciaId")
    private Integer grupoReferenciaId;

    @OneToOne
    @JoinColumn(name = "PROGRU_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "PROGRU_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @Column(name = "PROGRU_FechaCancelacion", nullable = true, insertable = false, updatable = true)
    private Date fechaCancelacion;

    @CreationTimestamp
    @Column(name = "PROGRU_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PROGRU_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PROGRU_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PROGRU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PROGRU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGRULC_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoListadoClase> listadoClases;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGRUP_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private List<ProgramaGrupoProfesor> profesoresTitulares;

    @Transient
    private String nombreSucursal;

    @Column(name = "PROGRU_Codigo", nullable = false, insertable = true, updatable = false)
    private String codigoGrupo;

    //Datos Incompany
    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCH_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyHorario> horarios;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCE_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyCriterioEvaluacion> criteriosEvaluacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCL_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyClaseCancelada> clasesCanceladas;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCCR_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyClaseReprogramada> clasesReprogramadas;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PGINCM_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyMaterial> materiales;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROGRUE_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false)
    private List<ProgramaGrupoEvidencia> evidencias;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PGINCC_PROGRU_GrupoId", referencedColumnName = "PROGRU_GrupoId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaGrupoIncompanyComision> listaComision;

    @Transient
    private Empleado nuevoProfesorTitular;

    @Transient
    private String horario;

    @Transient
    private String codigo;

    @OneToOne
    @JoinColumn(name = "PROGRU_PREINC_PrecioIncompanyId", insertable = false, updatable = false, referencedColumnName = "PREINC_PrecioIncompanyId")
    private PrecioIncompany precioIncompany;

    @Column(name = "PROGRU_PREINC_PrecioIncompanyId")
    private Integer precioIncompanyId;

    @Column(name = "PROGRU_PrecioVentaGrupo", nullable = true, insertable = true, updatable = true)
    private BigDecimal precioVentaGrupo;

    @Column(name = "PROGRU_PrecioVentaCurso", nullable = true, insertable = true, updatable = true)
    private BigDecimal precioVentaCurso;

    @Column(name = "PROGRU_PrecioVentaLibro", nullable = true, insertable = true, updatable = true)
    private BigDecimal precioVentaLibro;

    @Column(name = "PROGRU_PrecioVentaCertificacion", nullable = true, insertable = true, updatable = true)
    private BigDecimal precioVentaCertificacion;

    @Column(name = "PROGRU_PorcentajeComision", nullable = true, insertable = true, updatable = true)
    private BigDecimal porcentajeComision;

    @Column(name = "PROGRU_PorcentajeApoyoTransporte", nullable = true, insertable = true, updatable = true)
    private BigDecimal porcentajeApoyoTransporte;

    @Column(name = "PROGRU_KilometrosDistancia", nullable = true, insertable = true, updatable = true)
    private BigDecimal kilometrosDistancia;

    @Column(name = "PROGRU_PrecioMaterial", nullable = true, insertable = true, updatable = true)
    private BigDecimal precioMaterial;

    @Transient
    private Boolean mostrarBtnCambioProfesor;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRU_CLE_FechaUltimaActualizacion", nullable = true, insertable = true, updatable = true)
    private Date fechaUltimaActualizacion;

    @Column(name = "PROGRU_CLE_GrupoProfesorId", nullable = true, insertable = true, updatable = true)
    private Integer grupoProfesorId;

    @Column(name = "PROGRU_CLE_GrupoEstudiantesId", nullable = true, insertable = true, updatable = true)
    private Integer grupoEstudiantesId;

    @Column(name = "PROGRU_ClientePrecioVentaCurso", nullable = true, insertable = true, updatable = true)
    private BigDecimal clientePrecioVentaCurso;

    @Column(name = "PROGRU_ClientePrecioVentaLibro", nullable = true, insertable = true, updatable = true)
    private BigDecimal clientePrecioVentaLibro;

    @Column(name = "PROGRU_ClientePrecioVentaCertificacion", nullable = true, insertable = true, updatable = true)
    private BigDecimal clientePrecioVentaCertificacion;

    public Integer getGrupoProfesorId() {
        return grupoProfesorId;
    }

    public void setGrupoProfesorId(Integer grupoProfesorId) {
        this.grupoProfesorId = grupoProfesorId;
    }

    public Integer getGrupoEstudiantesId() {
        return grupoEstudiantesId;
    }

    public void setGrupoEstudiantesId(Integer grupoEstudiantesId) {
        this.grupoEstudiantesId = grupoEstudiantesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
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

    public PAModalidad getPaModalidad() {
        return paModalidad;
    }

    public void setPaModalidad(PAModalidad paModalidad) {
        this.paModalidad = paModalidad;
    }

    public Integer getPaModalidadId() {
        return paModalidadId;
    }

    public void setPaModalidadId(Integer paModalidadId) {
        this.paModalidadId = paModalidadId;
    }

    public ProgramacionAcademicaComercial getProgramacionAcademicaComercial() {
        return programacionAcademicaComercial;
    }

    public void setProgramacionAcademicaComercial(ProgramacionAcademicaComercial programacionAcademicaComercial) {
        this.programacionAcademicaComercial = programacionAcademicaComercial;
    }

    public Integer getProgramacionAcademicaComercialId() {
        return programacionAcademicaComercialId;
    }

    public void setProgramacionAcademicaComercialId(Integer programacionAcademicaComercialId) {
        this.programacionAcademicaComercialId = programacionAcademicaComercialId;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
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

    public PAModalidadHorario getModalidadHorario() {
        return modalidadHorario;
    }

    public void setModalidadHorario(PAModalidadHorario modalidadHorario) {
        this.modalidadHorario = modalidadHorario;
    }

    public Integer getModalidadHorarioId() {
        return modalidadHorarioId;
    }

    public void setModalidadHorarioId(Integer modalidadHorarioId) {
        this.modalidadHorarioId = modalidadHorarioId;
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

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
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

    public List<ProgramaGrupoListadoClase> getListadoClases() {
        return listadoClases;
    }

    public void setListadoClases(List<ProgramaGrupoListadoClase> listadoClases) {
        this.listadoClases = listadoClases;
    }

    public SucursalPlantel getSucursalPlantel() {
        return sucursalPlantel;
    }

    public void setSucursalPlantel(SucursalPlantel sucursalPlantel) {
        this.sucursalPlantel = sucursalPlantel;
    }

    public Integer getSucursalPlantelId() {
        return sucursalPlantelId;
    }

    public void setSucursalPlantelId(Integer sucursalPlantelId) {
        this.sucursalPlantelId = sucursalPlantelId;
    }

    public Boolean getMultisede() {
        return multisede;
    }

    public void setMultisede(Boolean multisede) {
        this.multisede = multisede;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public PACiclo getPaCiclo() {
        return paCiclo;
    }

    public void setPaCiclo(PACiclo paCiclo) {
        this.paCiclo = paCiclo;
    }

    public Integer getPaCicloId() {
        return paCicloId;
    }

    public void setPaCicloId(Integer paCicloId) {
        this.paCicloId = paCicloId;
    }

    public Integer getCalificacionMinima() {
        return calificacionMinima;
    }

    public void setCalificacionMinima(Integer calificacionMinima) {
        this.calificacionMinima = calificacionMinima;
    }

    public BigDecimal getFaltasPermitidas() {
        return faltasPermitidas;
    }

    public void setFaltasPermitidas(BigDecimal faltasPermitidas) {
        this.faltasPermitidas = faltasPermitidas;
    }

    public String getCategoriaProfesor() {
        return categoriaProfesor;
    }

    public void setCategoriaProfesor(String categoriaProfesor) {
        this.categoriaProfesor = categoriaProfesor;
    }

    public BigDecimal getSueldoProfesor() {
        return sueldoProfesor;
    }

    public void setSueldoProfesor(BigDecimal sueldoProfesor) {
        this.sueldoProfesor = sueldoProfesor;
    }

    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public List<ProgramaGrupoProfesor> getProfesoresTitulares() {
        return profesoresTitulares;
    }

    public void setProfesoresTitulares(List<ProgramaGrupoProfesor> profesoresTitulares) {
        this.profesoresTitulares = profesoresTitulares;
    }

    public Empleado getNuevoProfesorTitular() {
        return nuevoProfesorTitular;
    }

    public void setNuevoProfesorTitular(Empleado nuevoProfesorTitular) {
        this.nuevoProfesorTitular = nuevoProfesorTitular;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Date getFechaFinInscripciones() {
        return fechaFinInscripciones;
    }

    public void setFechaFinInscripciones(Date fechaFinInscripciones) {
        this.fechaFinInscripciones = fechaFinInscripciones;
    }

    public Date getFechaFinInscripcionesBecas() {
        return fechaFinInscripcionesBecas;
    }

    public void setFechaFinInscripcionesBecas(Date fechaFinInscripcionesBecas) {
        this.fechaFinInscripcionesBecas = fechaFinInscripcionesBecas;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<ProgramaGrupoIncompanyHorario> getHorarios() {
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getGrupoReferenciaId() {
        return grupoReferenciaId;
    }

    public void setGrupoReferenciaId(Integer grupoReferenciaId) {
        this.grupoReferenciaId = grupoReferenciaId;
    }

    public PrecioIncompany getPrecioIncompany() {
        return precioIncompany;
    }

    public void setPrecioIncompany(PrecioIncompany precioIncompany) {
        this.precioIncompany = precioIncompany;
    }

    public Integer getPrecioIncompanyId() {
        return precioIncompanyId;
    }

    public void setPrecioIncompanyId(Integer precioIncompanyId) {
        this.precioIncompanyId = precioIncompanyId;
    }

    public BigDecimal getPrecioVentaGrupo() {
        return precioVentaGrupo;
    }

    public void setPrecioVentaGrupo(BigDecimal precioVentaGrupo) {
        this.precioVentaGrupo = precioVentaGrupo;
    }

    public BigDecimal getPrecioVentaCurso() {
        return precioVentaCurso;
    }

    public void setPrecioVentaCurso(BigDecimal precioVentaCurso) {
        this.precioVentaCurso = precioVentaCurso;
    }

    public BigDecimal getPrecioVentaLibro() {
        return precioVentaLibro;
    }

    public void setPrecioVentaLibro(BigDecimal precioVentaLibro) {
        this.precioVentaLibro = precioVentaLibro;
    }

    public BigDecimal getPrecioVentaCertificacion() {
        return precioVentaCertificacion;
    }

    public void setPrecioVentaCertificacion(BigDecimal precioVentaCertificacion) {
        this.precioVentaCertificacion = precioVentaCertificacion;
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public BigDecimal getPorcentajeApoyoTransporte() {
        return porcentajeApoyoTransporte;
    }

    public void setPorcentajeApoyoTransporte(BigDecimal porcentajeApoyoTransporte) {
        this.porcentajeApoyoTransporte = porcentajeApoyoTransporte;
    }

    public BigDecimal getKilometrosDistancia() {
        return kilometrosDistancia;
    }

    public void setKilometrosDistancia(BigDecimal kilometrosDistancia) {
        this.kilometrosDistancia = kilometrosDistancia;
    }

    public Boolean getMostrarBtnCambioProfesor() {
        return mostrarBtnCambioProfesor;
    }

    public void setMostrarBtnCambioProfesor(Boolean mostrarBtnCambioProfesor) {
        this.mostrarBtnCambioProfesor = mostrarBtnCambioProfesor;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public List<ProgramaGrupoEvidencia> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<ProgramaGrupoEvidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public BigDecimal getPrecioMaterial() {
        return precioMaterial;
    }

    public void setPrecioMaterial(BigDecimal precioMaterial) {
        this.precioMaterial = precioMaterial;
    }

    public BigDecimal getClientePrecioVentaCurso() {
        return clientePrecioVentaCurso;
    }

    public void setClientePrecioVentaCurso(BigDecimal clientePrecioVentaCurso) {
        this.clientePrecioVentaCurso = clientePrecioVentaCurso;
    }

    public BigDecimal getClientePrecioVentaLibro() {
        return clientePrecioVentaLibro;
    }

    public void setClientePrecioVentaLibro(BigDecimal clientePrecioVentaLibro) {
        this.clientePrecioVentaLibro = clientePrecioVentaLibro;
    }

    public BigDecimal getClientePrecioVentaCertificacion() {
        return clientePrecioVentaCertificacion;
    }

    public void setClientePrecioVentaCertificacion(BigDecimal clientePrecioVentaCertificacion) {
        this.clientePrecioVentaCertificacion = clientePrecioVentaCertificacion;
    }

    public List<ProgramaGrupoIncompanyComision> getListaComision() {
        return listaComision;
    }

    public void setListaComision(List<ProgramaGrupoIncompanyComision> listaComision) {
        this.listaComision = listaComision;
    }
}
