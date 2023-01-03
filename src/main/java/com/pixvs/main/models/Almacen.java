package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Estado;
import com.pixvs.spring.models.Pais;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "Almacenes")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALM_AlmacenId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ALM_CodigoAlmacen", nullable = false, insertable = true, updatable = true)
    private String codigoAlmacen;

    @Column(name = "ALM_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "ALM_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "ALM_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @OneToOne
    @JoinColumn(name = "ALM_USU_ResponsableId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario responsable;

    @Column(name = "ALM_USU_ResponsableId", nullable = false, insertable = true, updatable = true)
    private Integer responsableId;

    @Column(name = "ALM_MismaDireccionSucursal", nullable = false, insertable = true, updatable = true)
    private Boolean mismaDireccionSucursal;

    @Column(name = "ALM_MismaDireccionCliente", nullable = false, insertable = true, updatable = true)
    private Boolean mismaDireccionCliente;

    @Column(name = "ALM_Domicilio", nullable = true, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "ALM_Colonia", nullable = true, insertable = true, updatable = true)
    private String colonia;

    @OneToOne
    @JoinColumn(name = "ALM_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "ALM_PAI_PaisId", nullable = true, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "ALM_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "ALM_EST_EstadoId", nullable = true, insertable = true, updatable = true)
    private Integer estadoId;

    @Column(name = "ALM_Ciudad", nullable = true, insertable = true, updatable = true)
    private String ciudad;

    @Column(name = "ALM_CP", nullable = true, insertable = true, updatable = true)
    private String cp;

    @Column(name = "ALM_Telefono", nullable = true, insertable = true, updatable = true)
    private String telefono;

    @Column(name = "ALM_Extension", nullable = true, insertable = true, updatable = true)
    private String extension;

    @Column(name = "ALM_Predeterminado", nullable = false, insertable = true, updatable = true)
    private Boolean predeterminado;

    @Column(name = "ALM_EsCEDI", nullable = true, insertable = true, updatable = true)
    private Boolean esCedi;

    @Column(name = "ALM_LocalidadesBandera", nullable = false)
    private boolean localidadesBandera;

    @Column(name = "ALM_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "ALM_CMM_TipoAlmacenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoAlmacen;

    @Column(name = "ALM_CMM_TipoAlmacenId", nullable = true, insertable = true, updatable = true)
    private Integer tipoAlmacenId;

    @OneToOne
    @JoinColumn(name = "ALM_CLI_ClienteId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "ALM_CLI_ClienteId", nullable = true, insertable = false, updatable = false)
    private Integer clienteId;

    @CreationTimestamp
    @Column(name = "ALM_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "ALM_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ALM_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ALM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "ALM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ALM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @ManyToMany
    @JoinTable(name = "UsuariosAlmacenes", joinColumns = @JoinColumn(name = "USUA_ALM_AlmacenId", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "USUA_USU_UsuarioId", insertable = false, updatable = false))
    private Set<Usuario> usuariosPermisos;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "LOC_ALM_AlmacenId", referencedColumnName = "ALM_AlmacenId", nullable = false, insertable = true, updatable = true)
    private List<Localidad> localidades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public Boolean getMismaDireccionSucursal() {
        return mismaDireccionSucursal;
    }

    public void setMismaDireccionSucursal(Boolean mismaDireccionSucursal) {
        this.mismaDireccionSucursal = mismaDireccionSucursal;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(Boolean predeterminado) {
        this.predeterminado = predeterminado;
    }

    public Boolean getEsCedi() {
        return esCedi;
    }

    public void setEsCedi(Boolean esCedi) {
        this.esCedi = esCedi;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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


    public ControlMaestroMultiple getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(ControlMaestroMultiple tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public Integer getTipoAlmacenId() {
        return tipoAlmacenId;
    }

    public void setTipoAlmacenId(Integer tipoAlmacenId) {
        this.tipoAlmacenId = tipoAlmacenId;
    }

    public Set<Usuario> getUsuariosPermisos() {
        return usuariosPermisos;
    }

    public void setUsuariosPermisos(Set<Usuario> usuariosPermisos) {
        this.usuariosPermisos = usuariosPermisos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Boolean getMismaDireccionCliente() {
        return mismaDireccionCliente;
    }

    public void setMismaDireccionCliente(Boolean mismaDireccionCliente) {
        this.mismaDireccionCliente = mismaDireccionCliente;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }

    public boolean isLocalidadesBandera() {
        return localidadesBandera;
    }

    public void setLocalidadesBandera(boolean localidadesBandera) {
        this.localidadesBandera = localidadesBandera;
    }
}
