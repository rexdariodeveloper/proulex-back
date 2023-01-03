package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 05/04/2022.
 */
@Entity
@Table(name = "EmpleadosContratos")
public class EmpleadoContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPCO_EmpleadoContratoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPCO_EMP_EmpleadoId", nullable = true, insertable = true, updatable = false)
    private Integer empleadoId;

    @OneToOne
    @JoinColumn(name = "EMPCO_EMP_EmpleadoId", referencedColumnName = "EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Empleado empleado;

    @Column(name = "EMPCO_Codigo", length = 50, nullable = false, insertable = true, updatable = false)
    private String codigo;

    // Justificacion
    @Column(name = "EMPCO_CMM_JustificacionId", nullable = false, insertable = true, updatable = true)
    private Integer justificacionId;

    @OneToOne
    @JoinColumn(name = "EMPCO_CMM_JustificacionId", referencedColumnName = "CMM_ControlId", nullable = true, insertable = false, updatable = false)
    private ControlMaestroMultiple justificacion;

    // Tipo de Contrato
    @Column(name = "EMPCO_CMM_TipoContratoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoContratoId;

    @OneToOne
    @JoinColumn(name = "EMPCO_CMM_TipoContratoId", referencedColumnName = "CMM_ControlId", nullable = true, insertable = false, updatable = false)
    private ControlMaestroMultiple tipoContrato;

    @Column(name = "EMPCO_PUE_PuestoId", nullable = false, insertable = true, updatable = true)
    private Integer puestoId;

    @OneToOne
    @JoinColumn(name = "EMPCO_PUE_PuestoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PUE_PuestoId")
    private Puesto puesto;

    @Column(name = "EMPCO_IngresosAdicionales", length = 200, nullable = false, insertable = true, updatable = true)
    private String ingresosAdicionales;

    @Column(name = "EMPCO_SueldoMensual", nullable = false, insertable = true, updatable = true)
    private BigDecimal sueldoMensual;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaFin", nullable = false)
    private Date fechaFin;

    // Tipo de Horario
    @Column(name = "EMPCO_CMM_TipoHorarioId", nullable = false, insertable = true, updatable = true)
    private Integer tipoHorarioId;

    @OneToOne
    @JoinColumn(name = "EMPCO_CMM_TipoHorarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoHorario;

    @Column(name = "EMPCO_CantidadHoraSemana", nullable = true, insertable = true, updatable = true)
    private Integer cantidadHoraSemana;

    @Column(name = "EMPCO_Domicilio", length = 255, nullable = false, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "EMPCO_CP", length = 10, nullable = false, insertable = true, updatable = true)
    private String cp;

    @Column(name = "EMPCO_Colonia", length = 100, nullable = false, insertable = true, updatable = true)
    private String colonia;

    // Pais
    @Column(name = "EMPCO_PAI_PaisId", nullable = false, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "EMPCO_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    // Estado
    @Column(name = "EMPCO_EST_EstadoId", nullable = false, insertable = true, updatable = true)
    private Integer estadoId;

    @OneToOne
    @JoinColumn(name = "EMPCO_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    // Municipio
    @Column(name = "EMPCO_MUN_MunicipioId", nullable = false, insertable = true, updatable = true)
    private Integer municipioId;

    @OneToOne
    @JoinColumn(name = "EMPCO_MUN_MunicipioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MUN_MunicipioId")
    private Municipio municipio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaContrato", nullable = false, insertable = true, updatable = false)
    private Date fechaContrato;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaCancelacion", nullable = true, insertable = true, updatable = false)
    private Date fechaCancelacion;

    @Column(name = "EMPCO_MotivoCancelacion", length = 1000, nullable = true, insertable = true, updatable = true)
    private String motivoCancelacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaImpresion", nullable = true, insertable = true, updatable = true)
    private Date fechaImpresion;

    @Column(name = "EMPCO_EMP_ImpresoPor", nullable = true, insertable = true, updatable = true)
    private Integer impresoPorId;

    @OneToOne
    @JoinColumn(name = "EMPCO_EMP_ImpresoPor", referencedColumnName = "EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Empleado impresoPor;

    @Column(name = "EMPCO_ReferenciaId", nullable = true, insertable = true, updatable = true)
    private Integer refrenciaId;

    @Column(name = "EMPCO_CadenaDigest", length = 1000, nullable = true, insertable = true, updatable = true)
    private String cadenaDigest;

    @Column(name = "EMPCO_CadenaFirma", length = 1000, nullable = true, insertable = true, updatable = true)
    private String cadenaFirma;

    // Estatus
    @Column(name = "EMPCO_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "EMPCO_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "EMPCO_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "EMPCO_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "EMPCO_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "EMPCO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @Column(name = "EMPCO_PropositoPuesto", length = 2000, nullable = true, insertable = true, updatable = true)
    private String propositoPuesto;

    @OneToOne
    @JoinColumn(name = "EMPCO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPCORES_EMPCO_EmpleadoContratoId", referencedColumnName = "EMPCO_EmpleadoContratoId", nullable = true)
    private List<EmpleadoContratoResponsabilidades> empleadoContratoResponsabilidades;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMPCO_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getJustificacionId() {
        return justificacionId;
    }

    public void setJustificacionId(Integer justificacionId) {
        this.justificacionId = justificacionId;
    }

    public ControlMaestroMultiple getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(ControlMaestroMultiple justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getTipoContratoId() {
        return tipoContratoId;
    }

    public void setTipoContratoId(Integer tipoContratoId) {
        this.tipoContratoId = tipoContratoId;
    }

    public ControlMaestroMultiple getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(ControlMaestroMultiple tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Integer getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(Integer puestoId) {
        this.puestoId = puestoId;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public String getIngresosAdicionales() {
        return ingresosAdicionales;
    }

    public void setIngresosAdicionales(String ingresosAdicionales) {
        this.ingresosAdicionales = ingresosAdicionales;
    }

    public BigDecimal getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(BigDecimal sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
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

    public Integer getTipoHorarioId() {
        return tipoHorarioId;
    }

    public void setTipoHorarioId(Integer tipoHorarioId) {
        this.tipoHorarioId = tipoHorarioId;
    }

    public ControlMaestroMultiple getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(ControlMaestroMultiple tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public Integer getCantidadHoraSemana() {
        return cantidadHoraSemana;
    }

    public void setCantidadHoraSemana(Integer cantidadHoraSemana) {
        this.cantidadHoraSemana = cantidadHoraSemana;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public Integer getImpresoPorId() {
        return impresoPorId;
    }

    public void setImpresoPorId(Integer impresoPorId) {
        this.impresoPorId = impresoPorId;
    }

    public Empleado getImpresoPor() {
        return impresoPor;
    }

    public void setImpresoPor(Empleado impresoPor) {
        this.impresoPor = impresoPor;
    }

    public Integer getRefrenciaId() {
        return refrenciaId;
    }

    public void setRefrenciaId(Integer refrenciaId) {
        this.refrenciaId = refrenciaId;
    }

    public String getCadenaDigest() {
        return cadenaDigest;
    }

    public void setCadenaDigest(String cadenaDigest) {
        this.cadenaDigest = cadenaDigest;
    }

    public String getCadenaFirma() {
        return cadenaFirma;
    }

    public void setCadenaFirma(String cadenaFirma) {
        this.cadenaFirma = cadenaFirma;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public List<EmpleadoContratoResponsabilidades> getEmpleadoContratoResponsabilidades() {
        return empleadoContratoResponsabilidades;
    }

    public void setEmpleadoContratoResponsabilidades(List<EmpleadoContratoResponsabilidades> empleadoContratoResponsabilidades) {
        this.empleadoContratoResponsabilidades = empleadoContratoResponsabilidades;
    }
}
