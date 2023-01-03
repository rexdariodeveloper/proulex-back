package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Estado;
import com.pixvs.spring.models.Pais;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "Proveedores")
public class Proveedor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_ProveedorId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PRO_Activo", nullable = true, insertable = true, updatable = false)
    private Boolean activo;

    @Column(name = "PRO_Codigo", length = 15, nullable = true, insertable = true, updatable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(name = "PRO_CMM_TipoProveedorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoProveedor;

    @Column(name = "PRO_CMM_TipoProveedorId", nullable = false, insertable = true, updatable = false)
    private Integer tipoProveedorId;

    @Column(name = "PRO_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PRO_RazonSocial", length = 100, nullable = true, insertable = true, updatable = true)
    private String razonSocial;

    @Column(name = "PRO_RFC", length = 20, nullable = true, insertable = true, updatable = true)
    private String rfc;

    @Column(name = "PRO_Domicilio", length = 200, nullable = true, insertable = true, updatable = true)
    private String domicilio;

    @Column(name = "PRO_Colonia", length = 100, nullable = true, insertable = true, updatable = true)
    private String colonia;

    @OneToOne
    @JoinColumn(name = "PRO_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "PRO_PAI_PaisId", nullable = true, insertable = true, updatable = true)
    private Integer paisId;

    @OneToOne
    @JoinColumn(name = "PRO_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "PRO_EST_EstadoId", nullable = true, insertable = true, updatable = true)
    private Integer estadoId;

    @Column(name = "PRO_Ciudad", length = 100, nullable = true, insertable = true, updatable = true)
    private String ciudad;

    @Column(name = "PRO_Cp", length = 5, nullable = true, insertable = true, updatable = true)
    private String cp;

    @Column(name = "PRO_Telefono", length = 25, nullable = true, insertable = true, updatable = true)
    private String telefono;

    @Column(name = "PRO_Extension", length = 3, nullable = true, insertable = true, updatable = true)
    private String extension;

    @Column(name = "PRO_CorreoElectronico", length = 50, nullable = true, insertable = true, updatable = true)
    private String correoElectronico;

    @Column(name = "PRO_PaginaWeb", length = 200, nullable = true, insertable = true, updatable = true)
    private String paginaWeb;

    @Column(name = "PRO_DiasPlazoCredito", nullable = true, insertable = true, updatable = true)
    private Integer diasPlazoCredito;

    @Column(name = "PRO_MontoCredito", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoCredito;

    @Column(name = "PRO_DiasPago", length = 150, nullable = true, insertable = true, updatable = true)
    private String diasPago;

    @OneToOne
    @JoinColumn(name = "PRO_MON_MonedaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "PRO_MON_MonedaId", nullable = true, insertable = true, updatable = true)
    private Integer monedaId;

    @Column(name = "PRO_CuentaContable", length = 18, nullable = true, insertable = true, updatable = true)
    private String cuentaContable;


    @CreationTimestamp
    @Column(name = "PRO_FechaCreacion", nullable = true, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PRO_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PRO_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PRO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PRO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PRO_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PROC_PRO_ProveedorId", referencedColumnName = "PRO_ProveedorId", nullable = false)
    private List<ProveedorContacto> contactos;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PROFP_PRO_ProveedorId", referencedColumnName = "PRO_ProveedorId", nullable = false)
    private List<ProveedorFormaPago> formasPago;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name="CXPF_PRO_ProveedorId", referencedColumnName = "PRO_ProveedorId", nullable = false, insertable = false, updatable = false)
    private List<CXPFactura> facturas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public Integer getDiasPlazoCredito() {
        return diasPlazoCredito;
    }

    public void setDiasPlazoCredito(Integer diasPlazoCredito) {
        this.diasPlazoCredito = diasPlazoCredito;
    }

    public BigDecimal getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(BigDecimal montoCredito) {
        this.montoCredito = montoCredito;
    }

    public String getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(String diasPago) {
        this.diasPago = diasPago;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
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
        creadoPorId = creadoPorId;
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

    public List<CXPFactura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<CXPFactura> facturas) {
        this.facturas = facturas;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public List<ProveedorContacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ProveedorContacto> contactos) {
        this.contactos = contactos;
    }

    public List<ProveedorFormaPago> getFormasPago() {
        return formasPago;
    }

    public void setFormasPago(List<ProveedorFormaPago> formasPago) {
        this.formasPago = formasPago;
    }

    public ControlMaestroMultiple getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(ControlMaestroMultiple tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public Integer getTipoProveedorId() {
        return tipoProveedorId;
    }

    public void setTipoProveedorId(Integer tipoProveedorId) {
        this.tipoProveedorId = tipoProveedorId;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
