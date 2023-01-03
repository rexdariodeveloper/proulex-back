package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Entity
@Table(name = "Alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALU_AlumnoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ALU_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @Column(name = "ALU_Nombre", length = 50, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "ALU_PrimerApellido", length = 50, nullable = false, insertable = true, updatable = true)
    private String primerApellido;

    @Column(name = "ALU_SegundoApellido", length = 50, nullable = true, insertable = true, updatable = true)
    private String segundoApellido;

    @OneToOne
    @JoinColumn(name = "ALU_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "ALU_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @Column(name = "ALU_CodigoUDG", length = 150, nullable = true, insertable = true, updatable = true)
    private String codigoUDG;

    @Column(name = "ALU_CodigoUDGAlterno", length = 150, nullable = true, insertable = true, updatable = true)
    private String codigoUDGAlterno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALU_FechaNacimiento", nullable = true, insertable = true, updatable = true)
    private Date fechaNacimiento;

    @OneToOne
    @JoinColumn(name = "ALU_PAI_PaisNacimientoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais paisNacimiento;

    @Column(name = "ALU_PAI_PaisNacimientoId", nullable = true, insertable = true, updatable = true)
    private Integer paisNacimientoId;

    @OneToOne
    @JoinColumn(name = "ALU_EST_EstadoNacimientoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estadoNacimiento;

    @Column(name = "ALU_EST_EstadoNacimientoId", nullable = true, insertable = true, updatable = true)
    private Integer estadoNacimientoId;

    @Column(name = "ALU_CiudadNacimiento", nullable = true, insertable = true, updatable = true)
    private String ciudadNacimiento;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_GeneroId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple genero;

    @Column(name = "ALU_CMM_GeneroId", nullable = false, insertable = true, updatable = true)
    private Integer generoId;

    @Column(name = "ALU_CURP", length = 30, nullable = true, insertable = true, updatable = true)
    private String curp;

    @Column(name = "ALU_AlumnoJOBS", nullable = false, insertable = true, updatable = true)
    private Boolean alumnoJOBS;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_ProgramaJOBSId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple programaJOBS;

    @Column(name = "ALU_CMM_ProgramaJOBSId", nullable = true, insertable = true, updatable = true)
    private Integer programaJOBSId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_CentroUniversitarioJOBSId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple centroUniversitarioJOBS;

    @Column(name = "ALU_CMM_CentroUniversitarioJOBSId", nullable = true, insertable = true, updatable = true)
    private Integer centroUniversitarioJOBSId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_PreparatoriaJOBSId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple preparatoriaJOBS;

    @Column(name = "ALU_CMM_PreparatoriaJOBSId", nullable = true, insertable = true, updatable = true)
    private Integer preparatoriaJOBSId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_CarreraJOBSId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple carreraJOBS;

    @Column(name = "ALU_CMM_CarreraJOBSId", nullable = true, insertable = true, updatable = true)
    private Integer carreraJOBSId;

    @Column(name = "ALU_BachilleratoTecnologico", length = 255, nullable = true, insertable = true, updatable = true)
    private String bachilleratoTecnologico;

    @OneToOne
    @JoinColumn(name = "ALU_ARC_FotoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo foto;

    @Column(name = "ALU_ARC_FotoId", nullable = true, insertable = true, updatable = true)
    private Integer fotoId;

    @Column(name = "ALU_Domicilio", length = 250, nullable = true, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "ALU_Colonia", length = 250, nullable = true, insertable = true, updatable = true)
    private String colonia;

    @Column(name = "ALU_CP", length = 10, nullable = true, insertable = true, updatable = true)
    private String cp;

    @OneToOne
    @JoinColumn(name = "ALU_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "ALU_PAI_PaisId", nullable = true, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "ALU_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "ALU_EST_EstadoId", nullable = true, insertable = true, updatable = true)
    private Integer estadoId;

    @Column(name = "ALU_Ciudad", length = 100, nullable = true, insertable = true, updatable = true)
    private String ciudad;

    @Column(name = "ALU_CorreoElectronico", length = 50, nullable = true, insertable = true, updatable = true)
    private String correoElectronico;

    @Column(name = "ALU_TelefonoFijo", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoFijo;

    @Column(name = "ALU_TelefonoMovil", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMovil;

    @Column(name = "ALU_TelefonoTrabajo", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajo;

    @Column(name = "ALU_TelefonoTrabajoExtension", length = 10, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajoExtension;

    @Column(name = "ALU_TelefonoMensajeriaInstantanea", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMensajeriaInstantanea;

    @Column(name = "ALU_CodigoAlumnoUDG", length = 15, nullable = true, insertable = true, updatable = true)
    private String codigoAlumnoUDG;

    @Column(name = "ALU_Grupo", length = 5, nullable = true, insertable = true, updatable = true)
    private String grupo;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_GradoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple grado;

    @Column(name = "ALU_CMM_GradoId", nullable = true, insertable = true, updatable = true)
    private Integer gradoId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_TurnoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple turno;

    @Column(name = "ALU_CMM_TurnoId", nullable = true, insertable = true, updatable = true)
    private Integer turnoId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_TipoAlumnoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoAlumno;

    @Column(name = "ALU_CMM_TipoAlumnoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoAlumnoId;

    @Column(name = "ALU_EmpresaOEscuela", length = 255, nullable = true, insertable = true, updatable = true)
    private String empresaOEscuela;

    @OneToOne
    @JoinColumn(name = "ALU_MUN_MunicipioNacimientoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MUN_MunicipioId")
    private Municipio municipioNacimiento;

    @Column(name = "ALU_MUN_MunicipioNacimientoId", nullable = true, insertable = true, updatable = true)
    private Integer municipioNacimientoId;

    @OneToOne
    @JoinColumn(name = "ALU_MUN_MunicipioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MUN_MunicipioId")
    private Municipio municipio;

    @Column(name = "ALU_MUN_MunicipioId", nullable = true, insertable = true, updatable = true)
    private Integer municipioId;

    @Column(name = "ALU_ProblemaSaludOLimitante", length = 255, nullable = true, insertable = true, updatable = true)
    private String problemaSaludOLimitante;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_MedioEnteradoProulex", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple medioEnteradoProulex;

    @Column(name = "ALU_CMM_MedioEnteradoProulex", nullable = true, insertable = true, updatable = true)
    private Integer medioEnteradoProulexId;

    @OneToOne
    @JoinColumn(name = "ALU_CMM_RazonEleccionProulex", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple razonEleccionProulex;

    @Column(name = "ALU_CMM_RazonEleccionProulex", nullable = true, insertable = true, updatable = true)
    private Integer razonEleccionProulexId;

    @Column(name = "ALU_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "ALU_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ALU_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ALU_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALU_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "ALU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ALU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="ALUC_ALU_AlumnoId", referencedColumnName = "ALU_AlumnoId", nullable = false, insertable = true, updatable = true)
    private List<AlumnoContacto> contactos;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="ADF_ALU_AlumnoId", referencedColumnName = "ALU_AlumnoId", nullable = false)
    private List<AlumnoDatosFacturacion> facturacion;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ALUEC_ALU_AlumnoId", referencedColumnName = "ALU_AlumnoId", nullable = false, insertable = false, updatable = false)
    private List<AlumnoExamenCertificacion> examenesCertificaciones;

    @Column(name = "ALU_Folio", nullable = true, insertable = true, updatable = true)
    private String folio;

    @Column(name = "ALU_Dependencia", nullable = true, insertable = true, updatable = true)
    private String dependencia;

    @Column(name = "ALU_Referencia", nullable = true, insertable = true, updatable = true)
    private String referencia;

    @Column(name = "ALU_CLE_UsuarioId", nullable = true, insertable = true, updatable = true)
    private Integer usuarioCleId;

    public Integer getUsuarioCleId() {
        return usuarioCleId;
    }

    public void setUsuarioCleId(Integer usuarioCleId) {
        this.usuarioCleId = usuarioCleId;
    }

    @Transient
    private String img64;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Pais getPaisNacimiento() { return paisNacimiento; }
    public void setPaisNacimiento(Pais paisNacimiento) { this.paisNacimiento = paisNacimiento; }

    public Integer getPaisNacimientoId() { return paisNacimientoId; }
    public void setPaisNacimientoId(Integer paisNacimientoId) { this.paisNacimientoId = paisNacimientoId; }

    public Estado getEstadoNacimiento() { return estadoNacimiento; }
    public void setEstadoNacimiento(Estado estadoNacimiento) { this.estadoNacimiento = estadoNacimiento; }

    public Integer getEstadoNacimientoId() { return estadoNacimientoId; }
    public void setEstadoNacimientoId(Integer estadoNacimientoId) { this.estadoNacimientoId = estadoNacimientoId; }

    public String getCiudadNacimiento() { return ciudadNacimiento; }
    public void setCiudadNacimiento(String ciudadNacimiento) { this.ciudadNacimiento = ciudadNacimiento; }

    public ControlMaestroMultiple getGenero() { return genero; }
    public void setGenero(ControlMaestroMultiple genero) { this.genero = genero; }

    public Integer getGeneroId() { return generoId; }
    public void setGeneroId(Integer generoId) { this.generoId = generoId; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public Archivo getFoto() { return foto; }
    public void setFoto(Archivo foto) { this.foto = foto; }

    public Integer getFotoId() { return fotoId; }
    public void setFotoId(Integer fotoId) { this.fotoId = fotoId; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }

    public Integer getPaisId() { return paisId; }
    public void setPaisId(Integer paisId) { this.paisId = paisId; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Integer getEstadoId() { return estadoId; }
    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefonoFijo() { return telefonoFijo; }
    public void setTelefonoFijo(String telefonoFijo) { this.telefonoFijo = telefonoFijo; }

    public String getTelefonoMovil() { return telefonoMovil; }
    public void setTelefonoMovil(String telefonoMovil) { this.telefonoMovil = telefonoMovil; }

    public String getTelefonoTrabajo() { return telefonoTrabajo; }
    public void setTelefonoTrabajo(String telefonoTrabajo) { this.telefonoTrabajo = telefonoTrabajo; }

    public String getTelefonoTrabajoExtension() { return telefonoTrabajoExtension; }
    public void setTelefonoTrabajoExtension(String telefonoTrabajoExtension) { this.telefonoTrabajoExtension = telefonoTrabajoExtension; }

    public String getTelefonoMensajeriaInstantanea() { return telefonoMensajeriaInstantanea; }
    public void setTelefonoMensajeriaInstantanea(String telefonoMensajeriaInstantanea) { this.telefonoMensajeriaInstantanea =  telefonoMensajeriaInstantanea; }

    public String getCodigoAlumnoUDG() { return codigoAlumnoUDG; }
    public void setCodigoAlumnoUDG(String codigoAlumnoUDG) { this.codigoAlumnoUDG = codigoAlumnoUDG; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public ControlMaestroMultiple getGrado() { return grado; }
    public void setGrado(ControlMaestroMultiple grado) { this.grado = grado; }

    public Integer getGradoId() { return gradoId; }
    public void setGradoId(Integer gradoId) { this.gradoId = gradoId; }

    public ControlMaestroMultiple getTurno() { return turno; }
    public void setTurno(ControlMaestroMultiple turno) { this.turno = turno; }

    public Integer getTurnoId() { return turnoId; }
    public void setTurnoId(Integer turnoId) { this.turnoId = turnoId; }

    public ControlMaestroMultiple getTipoAlumno() { return tipoAlumno; }
    public void setTipoAlumno(ControlMaestroMultiple tipoAlumno) { this.tipoAlumno = tipoAlumno; }

    public Integer getTipoAlumnoId() { return tipoAlumnoId; }
    public void setTipoAlumnoId(Integer tipoAlumnoId) { this.tipoAlumnoId = tipoAlumnoId; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public List<AlumnoContacto> getContactos() { return contactos; }
    public void setContactos(List<AlumnoContacto> contactos) { this.contactos = contactos; }

    public List<AlumnoDatosFacturacion> getFacturacion() { return facturacion; }
    public void setFacturacion(List<AlumnoDatosFacturacion> facturacion) { this.facturacion = facturacion; }

    public String getImg64() { return img64; }
    public void setImg64(String img64) { this.img64 = img64; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Integer getSucursalId() { return sucursalId; }
    public void setSucursalId(Integer sucursalId) { this.sucursalId = sucursalId; }

    public String getCodigoUDG() { return codigoUDG; }
    public void setCodigoUDG(String codigoUDG) { this.codigoUDG = codigoUDG; }

    public Boolean getAlumnoJOBS() { return alumnoJOBS; }
    public void setAlumnoJOBS(Boolean alumnoJOBS) { this.alumnoJOBS = alumnoJOBS; }

    public ControlMaestroMultiple getProgramaJOBS() { return programaJOBS; }
    public void setProgramaJOBS(ControlMaestroMultiple programaJOBS) { this.programaJOBS = programaJOBS; }

    public Integer getProgramaJOBSId() { return programaJOBSId; }
    public void setProgramaJOBSId(Integer programaJOBSId) { this.programaJOBSId = programaJOBSId; }

    public ControlMaestroMultiple getCentroUniversitarioJOBS() { return centroUniversitarioJOBS; }
    public void setCentroUniversitarioJOBS(ControlMaestroMultiple centroUniversitarioJOBS) { this.centroUniversitarioJOBS = centroUniversitarioJOBS; }

    public Integer getCentroUniversitarioJOBSId() { return centroUniversitarioJOBSId; }
    public void setCentroUniversitarioJOBSId(Integer centroUniversitarioJOBSId) { this.centroUniversitarioJOBSId = centroUniversitarioJOBSId; }

    public ControlMaestroMultiple getPreparatoriaJOBS() { return preparatoriaJOBS; }
    public void setPreparatoriaJOBS(ControlMaestroMultiple preparatoriaJOBS) { this.preparatoriaJOBS = preparatoriaJOBS; }

    public Integer getPreparatoriaJOBSId() { return preparatoriaJOBSId; }
    public void setPreparatoriaJOBSId(Integer preparatoriaJOBSId) { this.preparatoriaJOBSId = preparatoriaJOBSId; }

    public ControlMaestroMultiple getCarreraJOBS() { return carreraJOBS; }
    public void setCarreraJOBS(ControlMaestroMultiple carreraJOBS) { this.carreraJOBS = carreraJOBS; }

    public Integer getCarreraJOBSId() { return carreraJOBSId; }
    public void setCarreraJOBSId(Integer carreraJOBSId) { this.carreraJOBSId = carreraJOBSId; }

    public String getBachilleratoTecnologico() { return bachilleratoTecnologico; }
    public void setBachilleratoTecnologico(String bachilleratoTecnologico) { this.bachilleratoTecnologico = bachilleratoTecnologico; }

    public String getEmpresaOEscuela() {
        return empresaOEscuela;
    }

    public void setEmpresaOEscuela(String empresaOEscuela) {
        this.empresaOEscuela = empresaOEscuela;
    }

    public Municipio getMunicipioNacimiento() {
        return municipioNacimiento;
    }

    public void setMunicipioNacimiento(Municipio municipioNacimiento) {
        this.municipioNacimiento = municipioNacimiento;
    }

    public Integer getMunicipioNacimientoId() {
        return municipioNacimientoId;
    }

    public void setMunicipioNacimientoId(Integer municipioNacimientoId) {
        this.municipioNacimientoId = municipioNacimientoId;
    }

    public String getProblemaSaludOLimitante() {
        return problemaSaludOLimitante;
    }

    public void setProblemaSaludOLimitante(String problemaSaludOLimitante) {
        this.problemaSaludOLimitante = problemaSaludOLimitante;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public ControlMaestroMultiple getMedioEnteradoProulex() {
        return medioEnteradoProulex;
    }

    public void setMedioEnteradoProulex(ControlMaestroMultiple medioEnteradoProulex) {
        this.medioEnteradoProulex = medioEnteradoProulex;
    }

    public Integer getMedioEnteradoProulexId() {
        return medioEnteradoProulexId;
    }

    public void setMedioEnteradoProulexId(Integer medioEnteradoProulexId) {
        this.medioEnteradoProulexId = medioEnteradoProulexId;
    }

    public ControlMaestroMultiple getRazonEleccionProulex() {
        return razonEleccionProulex;
    }

    public void setRazonEleccionProulex(ControlMaestroMultiple razonEleccionProulex) {
        this.razonEleccionProulex = razonEleccionProulex;
    }

    public Integer getRazonEleccionProulexId() {
        return razonEleccionProulexId;
    }

    public void setRazonEleccionProulexId(Integer razonEleccionProulexId) {
        this.razonEleccionProulexId = razonEleccionProulexId;
    }

    public List<AlumnoExamenCertificacion> getExamenesCertificaciones() {
        return examenesCertificaciones;
    }

    public void setExamenesCertificaciones(List<AlumnoExamenCertificacion> examenesCertificaciones) {
        this.examenesCertificaciones = examenesCertificaciones;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigoUDGAlterno() {
        return codigoUDGAlterno;
    }

    public void setCodigoUDGAlterno(String codigoUDGAlterno) {
        this.codigoUDGAlterno = codigoUDGAlterno;
    }
}
