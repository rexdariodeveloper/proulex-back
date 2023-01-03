package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Estado;
import com.pixvs.spring.models.Pais;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_ClienteId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CLI_Codigo", nullable = false)
    private String codigo;

    @Column(name = "CLI_Nombre", nullable = false)
    private String nombre;

    @Column(name = "CLI_RFC", nullable = false)
    private String rfc;

    @Column(name = "CLI_RazonSocial", nullable = false)
    private String razonSocial;

    @Column(name = "CLI_Domicilio", nullable = false)
    private String domicilio;

    @Column(name = "CLI_Colonia", nullable = false)
    private String colonia;

    @Column(name = "CLI_PAI_PaisId", nullable = false)
    private int paisId;

    @OneToOne
    @JoinColumn(name = "CLI_PAI_PaisId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
    private Pais pais;

    @Column(name = "CLI_EST_EstadoId", nullable = false)
    private int estadoId;

    @OneToOne
    @JoinColumn(name = "CLI_EST_EstadoId", insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
    private Estado estado;

    @Column(name = "CLI_Ciudad", nullable = false)
    private String ciudad;

    @Column(name = "CLI_CP", nullable = false)
    private String cp;

    @Column(name = "CLI_Telefono", nullable = false)
    private String telefono;

    @Column(name = "CLI_Extension")	private String extension;

    @Column(name = "CLI_CorreoElectronico", nullable = false)
    private String correoElectronico;

    @Column(name = "CLI_PaginaWeb")
    private String paginaWeb;

    @Column(name = "CLI_FP_FormaPagoId", nullable = false)
    private int formaPagoId;

    @OneToOne
    @JoinColumn(name = "CLI_FP_FormaPagoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "FP_FormaPagoId")
    private FormaPago formaPago;

    @Column(name = "CLI_Comentarios")
    private String comentarios;

    @Column(name = "CLI_MON_MonedaId")
    private Integer monedaId;

    @OneToOne
    @JoinColumn(name = "CLI_MON_MonedaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CLI_CuentaCXC")
    private String cuentaCXC;

    @Column(name = "CLI_MontoCredito")
    private String montoCredito;

    @Column(name = "CLI_DiasCobro")
    private String diasCobro;

    @Column(name = "CLI_Activo")
    private boolean activo;

    @OneToOne
    @JoinColumn(name = "CLI_LIPRE_ListadoPrecioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "LIPRE_ListadoPrecioId")
    private ListadoPrecio listadoPrecio;

    @Column(name = "CLI_LIPRE_ListadoPrecioId")
    private Integer listadoPrecioId;
    @Column(name = "CLI_Consignacion", nullable = false, insertable = true, updatable = true)
    private Boolean consignacion;

    @CreationTimestamp
    @Column(name = "CLI_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CLI_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "CLI_USU_CreadoPorId", updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CLI_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CLI_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "CLI_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIC_CLI_ClienteId", referencedColumnName = "CLI_ClienteId", nullable = false)
    private List<ClienteContacto> contactos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLICB_CLI_ClienteId", referencedColumnName = "CLI_ClienteId", nullable = false)
    private List<ClienteCuentaBancaria> cuentasBancarias;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="ALM_CLI_ClienteId", referencedColumnName = "CLI_ClienteId", nullable = true, insertable = true, updatable = true)
    private List<Almacen> almacenesConsignacion;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "CDF_CLI_ClienteId", referencedColumnName = "CLI_ClienteId", nullable = false)
    private List<ClienteDatosFacturacion> facturacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRFC() {
        return rfc;
    }

    public void setRFC(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public int getPaisId() {
        return paisId;
    }

    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    public int getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(int formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public String getCuentaCXC() {
        return cuentaCXC;
    }

    public void setCuentaCXC(String cuentaCXC) {
        this.cuentaCXC = cuentaCXC;
    }

    public String getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(String montoCredito) {
        this.montoCredito = montoCredito;
    }

    public String getDiasCobro() {
        return diasCobro;
    }

    public void setDiasCobro(String diasCobro) {
        this.diasCobro = diasCobro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public List<ClienteContacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ClienteContacto> contactos) {
        this.contactos = contactos;
    }

    public List<ClienteCuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<ClienteCuentaBancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public ListadoPrecio getListadoPrecio() {
        return listadoPrecio;
    }

    public void setListadoPrecio(ListadoPrecio listadoPrecio) {
        this.listadoPrecio = listadoPrecio;
    }

    public Integer getListadoPrecioId() {
        return listadoPrecioId;
    }

    public void setListadoPrecioId(Integer listadoPrecioId) {
        this.listadoPrecioId = listadoPrecioId;
    }

    public Boolean getConsignacion() {
        return consignacion;
    }

    public void setConsignacion(Boolean consignacion) {
        this.consignacion = consignacion;
    }

    public List<Almacen> getAlmacenesConsignacion() {
        return almacenesConsignacion;
    }

    public void setAlmacenesConsignacion(List<Almacen> almacenesConsignacion) {
        this.almacenesConsignacion = almacenesConsignacion;
    }

    public List<ClienteDatosFacturacion> getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(List<ClienteDatosFacturacion> facturacion) {
        this.facturacion = facturacion;
    }
}
