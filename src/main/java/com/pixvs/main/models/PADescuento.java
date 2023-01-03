package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PADescuentos")
public class PADescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PADESC_DescuentoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PADESC_Codigo", nullable = false, length = 100)
    private String codigo;

    @Column(name = "PADESC_Concepto", nullable = false, length = 100)
    private String concepto;

    @Column(name = "PADESC_PorcentajeDescuento", nullable = false)
    private Integer porcentajeDescuento;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PADESC_FechaInicio")
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PADESC_FechaFin")
    private Date fechaFin;

    @Column(name = "PADESC_DescuentoRelacionadoCliente", nullable = false, insertable = true, updatable = true)
    private Boolean descuentoRelacionadoCliente;

    @Column(name = "PADESC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "PADESC_CLI_ClienteId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "PADESC_CLI_ClienteId", nullable = true, insertable = true, updatable = true)
    private Integer clienteId;

    @Column(name = "PADESC_PrioridadEvaluacion", nullable = true, insertable = true, updatable = true)
    private Integer prioridadEvaluacion;

    @OneToOne
    @JoinColumn(name = "PADESC_CMM_TipoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipo;

    @Column(name = "PADESC_CMM_TipoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoId;

    @CreationTimestamp
    @Column(name = "PADESC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PADESC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PADESC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PADESC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PADESC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PADESC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PADESCD_PADESC_DescuentoId", referencedColumnName = "PADESC_DescuentoId", nullable = false, insertable = true, updatable = true)
    private List<PADescuentoDetalle> detalles;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PADESCA_PADESC_DescuentoId", referencedColumnName = "PADESC_DescuentoId", nullable = false, insertable = true, updatable = true)
    private List<PADescuentoArticulo> articulos;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PADESCS_PADESC_DescuentoId", referencedColumnName = "PADESC_DescuentoId", nullable = false, insertable = true, updatable = true)
    private List<PADescuentoSucursal> sucursales;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PADESUA_PADESC_DescuentoId", referencedColumnName = "PADESC_DescuentoId", nullable = false, insertable = true, updatable = true)
    private List<PADescuentoUsuarioAutorizado> usuariosAutorizados;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
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

    public List<PADescuentoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PADescuentoDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getDescuentoRelacionadoCliente() {
        return descuentoRelacionadoCliente;
    }

    public void setDescuentoRelacionadoCliente(Boolean descuentoRelacionadoCliente) {
        this.descuentoRelacionadoCliente = descuentoRelacionadoCliente;
    }

    public List<PADescuentoArticulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<PADescuentoArticulo> articulos) {
        this.articulos = articulos;
    }

    public List<PADescuentoSucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<PADescuentoSucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<PADescuentoUsuarioAutorizado> getUsuariosAutorizados() {
        return usuariosAutorizados;
    }

    public void setUsuariosAutorizados(List<PADescuentoUsuarioAutorizado> usuariosAutorizados) {
        this.usuariosAutorizados = usuariosAutorizados;
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

    public Integer getPrioridadEvaluacion() {
        return prioridadEvaluacion;
    }

    public void setPrioridadEvaluacion(Integer prioridadEvaluacion) {
        this.prioridadEvaluacion = prioridadEvaluacion;
    }

    public ControlMaestroMultiple getTipo() {
        return tipo;
    }

    public void setTipo(ControlMaestroMultiple tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }
}
