package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USU_UsuarioId", insertable = false, updatable = false)
    private Integer id;

    @Column(name = "USU_Nombre", length = 200, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "USU_PrimerApellido", nullable = false, insertable = true, updatable = true)
    private String primerApellido;

    @Column(name = "USU_SegundoApellido", nullable = true, insertable = true, updatable = true)
    private String segundoApellido;

    @Column(name = "USU_CorreoElectronico", nullable = false, unique = true, insertable = true, updatable = true)
    private String correoElectronico;

    @Column(name = "USU_Contrasenia", nullable = false, insertable = true, updatable = false)
    private String contrasenia;

    @Column(name = "USU_FechaUltimaSesion", nullable = true, insertable = true, updatable = false)
    private Date fechaUltimaSesion;

    @Column(name = "USU_CMM_EstatusId", nullable = false, insertable = false, updatable = false)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "USU_CMM_EstatusId", referencedColumnName = "CMM_ControlId", nullable = false, insertable = true, updatable = true)
    private ControlMaestroMultiple estatus;

    @Column(name = "USU_ROL_RolId", nullable = false, insertable = false, updatable = false)
    private Integer rolId;

    @OneToOne
    @JoinColumn(name = "USU_ROL_RolId", referencedColumnName = "ROL_RolId", nullable = false, insertable = true, updatable = true)
    private Rol rol;

    @CreationTimestamp
    @Column(name = "USU_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "USU_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "USU_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer usuarioCreadoPorId;

    @OneToOne
    @JoinColumn(name = "USU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "USU_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer usuarioModificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "USU_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;


    @Column(name = "USU_ARC_ArchivoId", nullable = true, insertable = true, updatable = true)
    private Integer archivoId;

    @OneToOne
    @JoinColumn(name = "USU_ARC_ArchivoId", referencedColumnName = "ARC_ArchivoId", nullable = true, insertable = false, updatable = false)
    private Archivo archivo;

    @Transient
    private String img64;

    @Column(name = "USU_Codigo", length = 10, nullable = true, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "USU_FechaVencimiento", nullable = true, insertable = true, updatable = true)
    private Date vencimiento;

    @Column(name = "USU_CMM_TipoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoId;

    @Column(name = "USU_PAI_PaisId", nullable = true, insertable = false, updatable = false)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "USU_PAI_PaisId", referencedColumnName = "PAI_PaisId", nullable = true, insertable = true, updatable = true)
    private Pais pais;

    @Column(name = "USU_EST_EstadoId", nullable = true, insertable = false, updatable = false)
    private Integer estadoId;

    @OneToOne
    @JoinColumn(name = "USU_EST_EstadoId", referencedColumnName = "EST_EstadoId", nullable = true, insertable = true, updatable = true)
    private Estado estado;

    @Column(name = "USU_MUN_MunicipioId", nullable = true, insertable = false, updatable = false)
    private Integer municipioId;

    @OneToOne
    @JoinColumn(name = "USU_MUN_MunicipioId", referencedColumnName = "MUN_MunicipioId", nullable = true, insertable = true, updatable = true)
    private Municipio municipio;

    @Transient
    private String nombreCompleto;

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

    public String getNombreCompleto() {
        return nombre + ' ' + primerApellido + (segundoApellido == null ? "" : " " + segundoApellido);
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFechaUltimaSesion() {
        return fechaUltimaSesion;
    }

    public void setFechaUltimaSesion(Date fechaUltimaSesion) {
        this.fechaUltimaSesion = fechaUltimaSesion;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public Integer getUsuarioCreadoPorId() {
        return usuarioCreadoPorId;
    }

    public void setUsuarioCreadoPorId(Integer usuarioCreadoPorId) {
        this.usuarioCreadoPorId = usuarioCreadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getUsuarioModificadoPorId() {
        return usuarioModificadoPorId;
    }

    public void setUsuarioModificadoPorId(Integer usuarioModificadoPorId) {
        this.usuarioModificadoPorId = usuarioModificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Integer archivoId) {
        this.archivoId = archivoId;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Integer getTipoId() { return tipoId; }

    public void setTipoId(Integer tipoId) { this.tipoId = tipoId; }

    public Integer getPaisId() { return paisId; }
    public void setPaisId(Integer paisId) { this.paisId = paisId; }

    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }

    public Integer getEstadoId() { return estadoId; }
    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Integer getMunicipioId() { return municipioId; }
    public void setMunicipioId(Integer municipioId) { this.municipioId = municipioId; }

    public Municipio getMunicipio() { return municipio; }
    public void setMunicipio(Municipio municipio) { this.municipio = municipio; }
}

