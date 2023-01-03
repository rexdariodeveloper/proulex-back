package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Departamento;
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
@Table(name = "OrdenesCompra")
public class OrdenCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OC_OrdenCompraId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "OC_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
	private String codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_PRO_ProveedorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PRO_ProveedorId")
	private Proveedor proveedor;

	@Column(name = "OC_PRO_ProveedorId", nullable = false, insertable = true, updatable = true)
	private Integer proveedorId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "OC_FechaOC", nullable = false, insertable = true, updatable = true)
	private Date fechaOC;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "OC_FechaRequerida", nullable = false, insertable = true, updatable = true)
	private Date fechaRequerida;

	@Column(name = "OC_DireccionOC", length = 150, nullable = true, insertable = true, updatable = true)
	private String direccionOC;

	@Column(name = "OC_RemitirA", length = 150, nullable = true, insertable = true, updatable = true)
	private String remitirA;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_ALM_RecepcionArticulosAlmacenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
	private Almacen recepcionArticulosAlmacen;

	@Column(name = "OC_ALM_RecepcionArticulosAlmacenId", nullable = false, insertable = true, updatable = true)
	private Integer recepcionArticulosAlmacenId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_MON_MonedaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
	private Moneda moneda;

	@Column(name = "OC_MON_MonedaId", nullable = false, insertable = true, updatable = true)
	private Integer monedaId;

	@Column(name = "OC_TerminoPago", nullable = false, insertable = true, updatable = true)
	private Integer terminoPago;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_DEP_DepartamentoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "DEP_DepartamentoId")
	private Departamento departamento;

	@Column(name = "OC_DEP_DepartamentoId", nullable = true, insertable = true, updatable = true)
	private Integer departamentoId;

	@Column(name = "OC_Descuento", nullable = true, insertable = true, updatable = true)
	private BigDecimal descuento;

	@Column(name = "OC_Comentario", length = 3000, nullable = true, insertable = true, updatable = true)
	private String comentario;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple estatus;

	@Column(name = "OC_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
	private Integer estatusId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_USU_AutorizadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario autorizadoPor;

	@Column(name = "OC_USU_AutorizadoPorId", nullable = true, insertable = true, updatable = true)
	private Integer autorizadoPorId;


	@CreationTimestamp
	@Column(name = "OC_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "OC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "OC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "OC_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="OCD_OC_OrdenCompraId", referencedColumnName = "OC_OrdenCompraId", nullable = false, insertable = true, updatable = true)
    private List<OrdenCompraDetalle> detalles;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="OCR_OC_OrdenCompraId", referencedColumnName = "OC_OrdenCompraId", nullable = false, insertable = false, updatable = false)
	private List<OrdenCompraRecibo> recibos;

	@Transient
	private Localidad localidadRecibir;

	@Transient
	private Integer facturaPDFReciboId;

	@Transient
	private Integer facturaXMLReciboId;

	@Transient
	private List<Integer> evidenciaReciboIds;

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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Integer proveedorId) {
		this.proveedorId = proveedorId;
	}

	public Date getFechaOC() {
		return fechaOC;
	}

	public void setFechaOC(Date fechaOC) {
		this.fechaOC = fechaOC;
	}

	public Date getFechaRequerida() {
		return fechaRequerida;
	}

	public void setFechaRequerida(Date fechaRequerida) {
		this.fechaRequerida = fechaRequerida;
	}

	public String getDireccionOC() {
		return direccionOC;
	}

	public void setDireccionOC(String direccionOC) {
		this.direccionOC = direccionOC;
	}

	public String getRemitirA() {
		return remitirA;
	}

	public void setRemitirA(String remitirA) {
		this.remitirA = remitirA;
	}

	public Almacen getRecepcionArticulosAlmacen() {
		return recepcionArticulosAlmacen;
	}

	public void setRecepcionArticulosAlmacen(Almacen recepcionArticulosAlmacen) {
		this.recepcionArticulosAlmacen = recepcionArticulosAlmacen;
	}

	public Integer getRecepcionArticulosAlmacenId() {
		return recepcionArticulosAlmacenId;
	}

	public void setRecepcionArticulosAlmacenId(Integer recepcionArticulosAlmacenId) {
		this.recepcionArticulosAlmacenId = recepcionArticulosAlmacenId;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Integer getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Integer monedaId) {
		this.monedaId = monedaId;
	}

	public Integer getTerminoPago() {
		return terminoPago;
	}

	public void setTerminoPago(Integer terminoPago) {
		this.terminoPago = terminoPago;
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

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public List<OrdenCompraDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<OrdenCompraDetalle> detalles) {
		this.detalles = detalles;
	}

	public Localidad getLocalidadRecibir() {
		return localidadRecibir;
	}

	public void setLocalidadRecibir(Localidad localidadRecibir) {
		this.localidadRecibir = localidadRecibir;
	}

	public List<OrdenCompraRecibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<OrdenCompraRecibo> recibos) {
		this.recibos = recibos;
	}

	public Integer getFacturaPDFReciboId() {
		return facturaPDFReciboId;
	}

	public void setFacturaPDFReciboId(Integer facturaPDFReciboId) {
		this.facturaPDFReciboId = facturaPDFReciboId;
	}

	public Integer getFacturaXMLReciboId() {
		return facturaXMLReciboId;
	}

	public void setFacturaXMLReciboId(Integer facturaXMLReciboId) {
		this.facturaXMLReciboId = facturaXMLReciboId;
	}

	public List<Integer> getEvidenciaReciboIds() {
		return evidenciaReciboIds;
	}

	public void setEvidenciaReciboIds(List<Integer> evidenciaReciboId) {
		this.evidenciaReciboIds = evidenciaReciboId;
	}

	public Usuario getAutorizadoPor() {
		return autorizadoPor;
	}

	public void setAutorizadoPor(Usuario autorizadoPor) {
		this.autorizadoPor = autorizadoPor;
	}

	public Integer getAutorizadoPorId() {
		return autorizadoPorId;
	}

	public void setAutorizadoPorId(Integer autorizadoPorId) {
		this.autorizadoPorId = autorizadoPorId;
	}
}
