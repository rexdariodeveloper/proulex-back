package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "Empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_EmpleadoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMP_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "EMP_PrimerApellido", length = 100, nullable = false, insertable = true, updatable = true)
    private String primerApellido;

    @Column(name = "EMP_SegundoApellido", length = 100, nullable = false, insertable = true, updatable = true)
    private String segundoApellido;

    //Estado civil
    @OneToOne
    @JoinColumn(name = "EMP_CMM_EstadoCivilId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estadoCivil;

    @Column(name = "EMP_CMM_EstadoCivilId", nullable = false, insertable = true, updatable = false)
    private Integer estadoCivilId;

    //Genero
    @OneToOne
    @JoinColumn(name = "EMP_CMM_GeneroId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple genero;

    @Column(name = "EMP_CMM_GeneroId", nullable = false, insertable = true, updatable = false)
    private Integer generoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMP_FechaNacimiento", nullable = false)
    private Date fechaNacimiento;

    @OneToOne
    @JoinColumn(name = "EMP_PAI_PaisNacimientoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais paisNacimiento;

    @Column(name = "EMP_PAI_PaisNacimientoId", nullable = false, insertable = true, updatable = true)
    private Integer paisNacimientoId;

    @OneToOne
    @JoinColumn(name = "EMP_EST_EstadoNacimientoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estadoNacimiento;

    @Column(name = "EMP_EST_EstadoNacimientoId", nullable = false, insertable = true, updatable = false)
    private Integer estadoNacimientoId;

    @Column(name = "EMP_RFC", length = 20, nullable = false, insertable = true, updatable = true)
    private String rfc;

    @Column(name = "EMP_CURP", length = 30, nullable = false, insertable = true, updatable = true)
    private String curp;

    @Column(name = "EMP_CorreoElectronico", length = 50, nullable = false, insertable = true, updatable = true)
    private String correoElectronico;

    @OneToOne
    @JoinColumn(name = "EMP_ARC_FotoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo foto;

    @Column(name = "EMP_ARC_FotoId", nullable = true, insertable = true, updatable = true)
    private Integer fotoId;

    @Column(name = "EMP_CodigoEmpleado", length = 10, nullable = false, insertable = true, updatable = true)
    private String codigoEmpleado;

    @OneToOne
    @JoinColumn(name = "EMP_DEP_DepartamentoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "DEP_DepartamentoId")
    private Departamento departamento;

    @Column(name = "EMP_DEP_DepartamentoId", nullable = true, insertable = true, updatable = false)
    private Integer departamentoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMP_FechaAlta", nullable = true)
    private Date fechaAlta;

    @OneToOne
    @JoinColumn(name = "EMP_CMM_TipoEmpleadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoEmpleado;

    @Column(name = "EMP_CMM_TipoEmpleadoId", nullable = true, insertable = true)
    private Integer tipoEmpleadoId;

    @OneToOne
    @JoinColumn(name = "EMP_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "EMP_SUC_SucursalId", nullable = true, insertable = true)
    private Integer sucursalId;

    @Column(name = "EMP_Domicilio", length = 200, nullable = false, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "EMP_Colonia", length = 100, insertable = true, updatable = true)
    private String colonia;

    @Column(name = "EMP_CP", length = 5, nullable = false, insertable = true, updatable = true)
    private String cp;

    @OneToOne
    @JoinColumn(name = "EMP_PAI_PaisId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "EMP_PAI_PaisId", nullable = false, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "EMP_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "EMP_EST_EstadoId", nullable = true, insertable = true, updatable = true)
    private Integer estadoId;

    @Column(name = "EMP_Municipio", length = 100, nullable = false, insertable = true, updatable = true)
    private String municipio;

    @OneToOne
    @JoinColumn(name = "EMP_CMM_GradoEstudiosId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple gradoEstudios;

    @Column(name = "EMP_CMM_GradoEstudiosId", nullable = true, insertable = true)
    private Integer gradoEstudiosId;

    @OneToOne
    @JoinColumn(name = "EMP_CMM_NacionalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple nacionalidad;

    @Column(name = "EMP_CMM_NacionalidadId", nullable = true, insertable = true)
    private Integer nacionalidadId;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPDS_EMP_EmpleadoId", nullable = false, insertable = true, updatable = true, referencedColumnName = "EMP_EmpleadoId")
    private List<EmpleadoDatoSalud> datosSalud;

    @OneToOne
    @JoinColumn(name = "EMP_USU_UsuarioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario usuario;

    @Column(name = "EMP_USU_UsuarioId", nullable = true, insertable = true, updatable = true)
    private Integer usuarioId;

    @CreationTimestamp
    @Column(name = "EMP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "EMP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "EMP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "EMP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "EMP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMP_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EC_EMP_EmpleadoId", referencedColumnName = "EMP_EmpleadoId", nullable = false, insertable = true, updatable = true)
    private List<EmpleadoContacto> contactos;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPCU_EMP_EmpleadoId", referencedColumnName = "EMP_EmpleadoId", nullable = false, insertable = true, updatable = true)
    private List<EmpleadoCurso> cursos;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPCA_EMP_EmpleadoId", referencedColumnName = "EMP_EmpleadoId", nullable = false, insertable = true, updatable = true)
    private List<EmpleadoCategoria> categorias;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPBE_EMP_EmpleadoId", referencedColumnName = "EMP_EmpleadoId", nullable = false, insertable = true, updatable = true)
    private List<EmpleadoBeneficiario> beneficiarios;

    @Column(name = "EMP_CMM_EstatusId", nullable = false)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "EMP_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "EMP_TelefonoContacto", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoContacto;

    @Column(name = "EMP_TelefonoMovil", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMovil;

    @Column(name = "EMP_TelefonoTrabajo", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajo;

    @Column(name = "EMP_TelefonoTrabajoExtension", length = 10, nullable = true, insertable = true, updatable = true)
    private String telefonoTrabajoExtension;

    @Column(name = "EMP_TelefonoMensajeriaInstantanea", length = 50, nullable = true, insertable = true, updatable = true)
    private String telefonoMensajeriaInstantanea;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPD_EMP_EmpleadoId", nullable = false, insertable = true, updatable = true, referencedColumnName = "EMP_EmpleadoId")
    private List<EmpleadoDocumento> listaEmpleadoDocumento;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "EMP_EmpleadoId", referencedColumnName = "EMPCO_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private EmpleadoContrato empleadoContrato;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "EMPH_EMP_EmpleadoId", nullable = false, insertable = true, updatable = true, referencedColumnName = "EMP_EmpleadoId")
    private List<EmpleadoHorario> listaEmpleadoHorario;

    @Transient
    private String img64;

    @Transient
    private Boolean actualizarDatos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public ControlMaestroMultiple getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(ControlMaestroMultiple estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Integer getEstadoCivilId() {
        return estadoCivilId;
    }

    public void setEstadoCivilId(Integer estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    public ControlMaestroMultiple getGenero() {
        return genero;
    }

    public void setGenero(ControlMaestroMultiple genero) {
        this.genero = genero;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Pais getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(Pais paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public Integer getPaisNacimientoId() {
        return paisNacimientoId;
    }

    public void setPaisNacimientoId(Integer paisNacimientoId) {
        this.paisNacimientoId = paisNacimientoId;
    }

    public Estado getEstadoNacimiento() {
        return estadoNacimiento;
    }

    public void setEstadoNacimiento(Estado estadoNacimiento) {
        this.estadoNacimiento = estadoNacimiento;
    }

    public Integer getEstadoNacimientoId() {
        return estadoNacimientoId;
    }

    public void setEstadoNacimientoId(Integer estadoNacimientoId) {
        this.estadoNacimientoId = estadoNacimientoId;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Archivo getFoto() {
        return foto;
    }

    public void setFoto(Archivo foto) {
        this.foto = foto;
    }

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(Integer fotoId) {
        this.fotoId = fotoId;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Integer getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Integer departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public ControlMaestroMultiple getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(ControlMaestroMultiple tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public Integer getTipoEmpleadoId() {
        return tipoEmpleadoId;
    }

    public void setTipoEmpleadoId(Integer tipoEmpleadoId) {
        this.tipoEmpleadoId = tipoEmpleadoId;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public ControlMaestroMultiple getGradoEstudios() {
        return gradoEstudios;
    }

    public void setGradoEstudios(ControlMaestroMultiple gradoEstudios) {
        this.gradoEstudios = gradoEstudios;
    }

    public Integer getGradoEstudiosId() {
        return gradoEstudiosId;
    }

    public void setGradoEstudiosId(Integer gradoEstudiosId) {
        this.gradoEstudiosId = gradoEstudiosId;
    }

    public ControlMaestroMultiple getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(ControlMaestroMultiple nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Integer getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(Integer nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }

    public List<EmpleadoDatoSalud> getDatosSalud() {
        return datosSalud;
    }

    public void setDatosSalud(List<EmpleadoDatoSalud> datosSalud) {
        this.datosSalud = datosSalud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public List<EmpleadoContacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<EmpleadoContacto> contactos) {
        this.contactos = contactos;
    }

    public List<EmpleadoCurso> getCursos() {
        return cursos;
    }

    public void setCursos(List<EmpleadoCurso> cursos) {
        this.cursos = cursos;
    }

    public List<EmpleadoCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<EmpleadoCategoria> categorias) {
        this.categorias = categorias;
    }

    public List<EmpleadoBeneficiario> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(List<EmpleadoBeneficiario> beneficiarios) {
        this.beneficiarios = beneficiarios;
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

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoTrabajoExtension() {
        return telefonoTrabajoExtension;
    }

    public void setTelefonoTrabajoExtension(String telefonoTrabajoExtension) {
        this.telefonoTrabajoExtension = telefonoTrabajoExtension;
    }

    public String getTelefonoMensajeriaInstantanea() {
        return telefonoMensajeriaInstantanea;
    }

    public void setTelefonoMensajeriaInstantanea(String telefonoMensajeriaInstantanea) {
        this.telefonoMensajeriaInstantanea = telefonoMensajeriaInstantanea;
    }

    public List<EmpleadoDocumento> getListaEmpleadoDocumento() {
        return listaEmpleadoDocumento;
    }

    public void setListaEmpleadoDocumento(List<EmpleadoDocumento> listaEmpleadoDocumento) {
        this.listaEmpleadoDocumento = listaEmpleadoDocumento;
    }

    public EmpleadoContrato getEmpleadoContrato() {
        return empleadoContrato;
    }

    public void setEmpleadoContrato(EmpleadoContrato empleadoContrato) {
        this.empleadoContrato = empleadoContrato;
    }

    public List<EmpleadoHorario> getListaEmpleadoHorario() {
        return listaEmpleadoHorario;
    }

    public void setListaEmpleadoHorario(List<EmpleadoHorario> listaEmpleadoHorario) {
        this.listaEmpleadoHorario = listaEmpleadoHorario;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public Boolean getActualizarDatos() {
        return actualizarDatos;
    }

    public void setActualizarDatos(Boolean actualizarDatos) {
        this.actualizarDatos = actualizarDatos;
    }
}
