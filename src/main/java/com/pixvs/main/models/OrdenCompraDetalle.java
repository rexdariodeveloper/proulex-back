package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@Entity
@Table(name = "OrdenesCompraDetalles")
public class OrdenCompraDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OCD_OrdenCompraDetalleId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_OC_OrdenCompraId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OC_OrdenCompraId")
	private OrdenCompra ordenCompra;

	@Column(name = "OCD_OC_OrdenCompraId", nullable = false, insertable = false, updatable = false)
	private Integer ordenCompraId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_ART_ArticuloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
	private Articulo articulo;

	@Column(name = "OCD_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
	private Integer articuloId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_UM_UnidadMedidaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
	private UnidadMedida unidadMedida;

	@Column(name = "OCD_UM_UnidadMedidaId", nullable = false, insertable = true, updatable = true)
	private Integer unidadMedidaId;

	@Column(name = "OCD_FactorConversion", nullable = false, insertable = true, updatable = true)
	private BigDecimal factorConversion;

	@Column(name = "OCD_Cantidad", nullable = false, insertable = true, updatable = true)
	private BigDecimal cantidad;

	@Column(name = "OCD_Precio", nullable = false, insertable = true, updatable = true)
	private BigDecimal precio;

	@Column(name = "OCD_Descuento", nullable = false, insertable = true, updatable = true)
	private BigDecimal descuento;

	@Column(name = "OCD_IVA", nullable = true, insertable = true, updatable = true)
	private BigDecimal iva;

	@Column(name = "OCD_IVAExento", nullable = true, insertable = true, updatable = true)
	private Boolean ivaExento;

	@Column(name = "OCD_IEPS", nullable = true, insertable = true, updatable = true)
	private BigDecimal ieps;

	@Column(name = "OCD_IEPSCuotaFija", nullable = true, insertable = true, updatable = true)
	private BigDecimal iepsCuotaFija;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_REQP_RequisicionpartidaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "REQP_RequisicionPartidaId")
	private RequisicionPartida requisicionPartida;

	@Column(name = "OCD_REQP_RequisicionpartidaId", nullable = true, insertable = true, updatable = true)
	private Integer requisicionPartidaId;

	@Column(name = "OCD_CuentaCompras", length = 18, nullable = true, insertable = true, updatable = true)
	private String cuentaCompras;

	@Column(name = "OCD_Comentarios", length = 255, nullable = true, insertable = true, updatable = true)
	private String comentarios;


	@CreationTimestamp
	@Column(name = "OCD_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "OCD_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "OCD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "OCD_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="OCR_OCD_OrdenCompraDetalleId", referencedColumnName = "OCD_OrdenCompraDetalleId", nullable = false, insertable = false, updatable = false)
	private List<OrdenCompraRecibo> recibos;


	@Transient
	private String articuloMisc;

	@Transient
	private BigDecimal subtotal;

	@Transient
	private BigDecimal impuesto;

	@Transient
	private BigDecimal cantidadPendiente;

	@Transient
	private BigDecimal cantidadRecibida;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdenCompraId() {
		return ordenCompraId;
	}

	public void setOrdenCompraId(Integer ordenCompraId) {
		this.ordenCompraId = ordenCompraId;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Integer getArticuloId() {
		return articuloId;
	}

	public void setArticuloId(Integer articuloId) {
		this.articuloId = articuloId;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Integer getUnidadMedidaId() {
		return unidadMedidaId;
	}

	public void setUnidadMedidaId(Integer unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}

	public BigDecimal getFactorConversion() {
		return factorConversion;
	}

	public void setFactorConversion(BigDecimal factorConversion) {
		this.factorConversion = factorConversion;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Boolean getIvaExento() {
		return ivaExento;
	}

	public void setIvaExento(Boolean ivaExento) {
		this.ivaExento = ivaExento;
	}

	public BigDecimal getIeps() {
		return ieps;
	}

	public void setIeps(BigDecimal ieps) {
		this.ieps = ieps;
	}

	public BigDecimal getIepsCuotaFija() {
		return iepsCuotaFija;
	}

	public void setIepsCuotaFija(BigDecimal iepsCuotaFija) {
		this.iepsCuotaFija = iepsCuotaFija;
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public String getArticuloMisc() {
		return articuloMisc;
	}

	public void setArticuloMisc(String articuloMisc) {
		this.articuloMisc = articuloMisc;
	}

	public List<OrdenCompraRecibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<OrdenCompraRecibo> recibos) {
		this.recibos = recibos;
	}

	public BigDecimal getCantidadPendiente() {
		return cantidadPendiente;
	}

	public void setCantidadPendiente(BigDecimal cantidadPendiente) {
		this.cantidadPendiente = cantidadPendiente;
	}

	public BigDecimal getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(BigDecimal cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public RequisicionPartida getRequisicionPartida() {
		return requisicionPartida;
	}

	public void setRequisicionPartida(RequisicionPartida requisicionPartida) {	this.requisicionPartida = requisicionPartida;	}

	public Integer getRequisicionPartidaId() {
		return requisicionPartidaId;
	}

	public void setRequisicionPartidaId(Integer requisicionPartidaId) {
		this.requisicionPartidaId = requisicionPartidaId;
	}

	public String getCuentaCompras() {
		return cuentaCompras;
	}

	public void setCuentaCompras(String cuentaCompras) {
		this.cuentaCompras = cuentaCompras;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
