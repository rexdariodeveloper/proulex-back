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
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Entity
@Table(name = "Sucursales")
public class Sucursal {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUC_SucursalId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "SUC_CodigoSucursal", length = 10, nullable = false, insertable = true, updatable = true)
	private String codigoSucursal;

	@Column(name = "SUC_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
	private String nombre;

	@Column(name = "SUC_Prefijo", length = 4, nullable = false, insertable = true, updatable = true)
	private String prefijo;

	@Column(name = "SUC_Serie", nullable = false)
	private String serie;

	@Column(name = "SUC_USU_ResponsableId", nullable = false, insertable = true, updatable = true)
	private Integer responsableId;

	@OneToOne
	@JoinColumn(name = "SUC_USU_ResponsableId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario responsable;

	@Column(name = "SUC_USU_CoordinadorId", nullable = true, insertable = true, updatable = true)
	private Integer coordinadorId;

	@OneToOne
	@JoinColumn(name = "SUC_USU_CoordinadorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario coordinador;

	@Column(name = "SUC_Domicilio", length = 250, nullable = false, insertable = true, updatable = true)
	private String domicilio;

	@Column(name = "SUC_Colonia", length = 250, nullable = false, insertable = true, updatable = true)
	private String colonia;

	@Column(name = "SUC_PAI_PaisId", nullable = false, insertable = true, updatable = true)
	private Integer paisId;

	@OneToOne
	@JoinColumn(name = "SUC_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
	private Pais pais;

	@Column(name = "SUC_EST_EstadoId", nullable = true, insertable = true, updatable = true)
	private Integer estadoId;

	@OneToOne
	@JoinColumn(name = "SUC_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
	private Estado estado;

	@Column(name = "SUC_Ciudad", length = 100, nullable = false, insertable = true, updatable = true)
	private String ciudad;

	@Column(name = "SUC_CP", length = 10, nullable = false, insertable = true, updatable = true)
	private String cp;

	@Column(name = "SUC_Telefono", length = 25, nullable = false, insertable = true, updatable = true)
	private String telefono;

	@Column(name = "SUC_Extension", length = 10, nullable = true, insertable = true, updatable = true)
	private String extension;

	@Column(name = "SUC_PorcentajeComision", nullable = true, insertable = true, updatable = true)
	private BigDecimal porcentajeComision;

	@Column(name = "SUC_PresupuestoSemanal", nullable = true, insertable = true, updatable = true)
	private BigDecimal presupuestoSemanal;

	@Column(name = "SUC_Predeterminada", nullable = false, insertable = true, updatable = true)
	private Boolean predeterminada;

	@Column(name = "SUC_Activo", nullable = false, insertable = true, updatable = true)
	private Boolean activo;

	@Column(name = "SUC_PlantelesBandera", nullable = false, insertable = true, updatable = true)
	private Boolean plantelesBandera;

	@OneToOne
	@JoinColumn(name = "SUC_CMM_TipoSucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipoSucursal;

	@Column(name = "SUC_CMM_TipoSucursalId", nullable = false, insertable = true, updatable = true)
	private Integer tipoSucursalId;

	@OneToOne
	@JoinColumn(name = "SUC_LIPRE_ListadoPrecioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "LIPRE_ListadoPrecioId")
	private ListadoPrecio listadoPrecio;

	@Column(name = "SUC_LIPRE_ListadoPrecioId", nullable = true, insertable = true, updatable = true)
	private Integer listadoPrecioId;

	@OneToOne
	@JoinColumn(name = "SUC_BAC_CuentaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "BAC_CuentaId")
	private CuentaBancaria cuentaBancaria;

	@Column(name = "SUC_BAC_CuentaId", nullable = true, insertable = true, updatable = true)
	private Integer cuentaBancariaId;

	@CreationTimestamp
	@Column(name = "SUC_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "SUC_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "SUC_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
	private Integer creadoPorId;

	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "SUC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToOne
	@JoinColumn(name = "SUC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "SUC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@Column(name = "SUC_MostrarRed", nullable = false, insertable = true, updatable = true)
	private Boolean mostrarRed;

	@Column(name = "SUC_NombreRed", length = 100, nullable = false, insertable = true, updatable = true)
	private String nombreRed;

	@Column(name = "SUC_ContraseniaRed", length = 100, nullable = false, insertable = true, updatable = true)
	private String contraseniaRed;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="ALM_SUC_SucursalId", referencedColumnName = "SUC_SucursalId", nullable = false, insertable = false, updatable = false)
	private List<Almacen> almacenesHijos;

	@Column(name = "SUC_Lunes", insertable = true, updatable = true)
	private Boolean lunes;

	@Column(name = "SUC_Martes", insertable = true, updatable = true)
	private Boolean martes;

	@Column(name = "SUC_Miercoles", insertable = true, updatable = true)
	private Boolean miercoles;

	@Column(name = "SUC_Jueves", insertable = true, updatable = true)
	private Boolean jueves;

	@Column(name = "SUC_Viernes", insertable = true, updatable = true)
	private Boolean viernes;

	@Column(name = "SUC_Sabado", insertable = true, updatable = true)
	private Boolean sabado;

	@Column(name = "SUC_Domingo", insertable = true, updatable = true)
	private Boolean domingo;

	@Column(name = "SUC_CMM_TipoFacturaGlobalId", nullable = false)
	private int tipoFacturaGlobalId;

	@OneToOne
	@JoinColumn(name = "SUC_CMM_TipoFacturaGlobalId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipoFacturaGlobal;

	@ManyToMany
	@JoinTable(name = "SucursalesMediosPagoPV", joinColumns = {@JoinColumn(name = "SUCMPPV_SUC_SucursalId")}, inverseJoinColumns = {@JoinColumn(name = "SUCMPPV_MPPV_MedioPagoPVId")})
	private Set<MedioPagoPV> mediosPagoPV = new HashSet<>();

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="SP_SUC_SucursalId", referencedColumnName = "SUC_SucursalId", nullable = false, insertable = true, updatable = true)
	private List<SucursalPlantel> planteles;

	@Transient
	private List<Almacen> almacenes = new LinkedList<>();

	@Transient
	private List<SucursalImpresoraFamilia> impresoras = new LinkedList<>();

	@Transient
	private List<SucursalFormasPago> formasPago = new LinkedList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoSucursal() {
		return codigoSucursal;
	}

	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Integer getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Integer responsableId) {
		this.responsableId = responsableId;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
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

	public BigDecimal getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(BigDecimal porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public BigDecimal getPresupuestoSemanal() {
		return presupuestoSemanal;
	}

	public void setPresupuestoSemanal(BigDecimal presupuestoSemanal) {
		this.presupuestoSemanal = presupuestoSemanal;
	}

	public Boolean getPredeterminada() {
		return predeterminada;
	}

	public void setPredeterminada(Boolean predeterminada) {
		this.predeterminada = predeterminada;
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

	public List<Almacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(List<Almacen> almacenes) {
		this.almacenes = almacenes;
	}

	public Boolean getMostrarRed() { return mostrarRed; }

	public void setMostrarRed(Boolean mostrarRed) { this.mostrarRed = mostrarRed; }

	public String getNombreRed() { return nombreRed; }

	public void setNombreRed(String nombreRed) { this.nombreRed = nombreRed; }

	public String getContraseniaRed() { return contraseniaRed; }

	public void setContraseniaRed(String contraseniaRed) { this.contraseniaRed = contraseniaRed; }

	public ControlMaestroMultiple getTipoSucursal() {
		return tipoSucursal;
	}

	public void setTipoSucursal(ControlMaestroMultiple tipoSucursal) {
		this.tipoSucursal = tipoSucursal;
	}

	public Integer getTipoSucursalId() {
		return tipoSucursalId;
	}

	public void setTipoSucursalId(Integer tipoSucursalId) {
		this.tipoSucursalId = tipoSucursalId;
	}

	public List<Almacen> getAlmacenesHijos() {
		return almacenesHijos;
	}

	public void setAlmacenesHijos(List<Almacen> almacenesHijos) {
		this.almacenesHijos.clear();
		if(almacenesHijos != null)
			this.almacenesHijos.addAll(almacenesHijos);
	}

	public List<SucursalImpresoraFamilia> getImpresoras() {
		return impresoras;
	}

	public void setImpresoras(List<SucursalImpresoraFamilia> impresoras) {
		this.impresoras = impresoras;
	}

	public List<SucursalFormasPago> getFormasPago() { return formasPago; }

	public void setFormasPago(List<SucursalFormasPago> formasPago) { this.formasPago = formasPago; }

	public Boolean getLunes() {
		return lunes;
	}

	public void setLunes(Boolean lunes) {
		this.lunes = lunes;
	}

	public Boolean getMartes() {
		return martes;
	}

	public void setMartes(Boolean martes) {
		this.martes = martes;
	}

	public Boolean getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(Boolean miercoles) {
		this.miercoles = miercoles;
	}

	public Boolean getJueves() {
		return jueves;
	}

	public void setJueves(Boolean jueves) {
		this.jueves = jueves;
	}

	public Boolean getViernes() {
		return viernes;
	}

	public void setViernes(Boolean viernes) {
		this.viernes = viernes;
	}

	public Boolean getSabado() {
		return sabado;
	}

	public void setSabado(Boolean sabado) {
		this.sabado = sabado;
	}

	public Boolean getDomingo() {
		return domingo;
	}

	public void setDomingo(Boolean domingo) {
		this.domingo = domingo;
	}

	public int getTipoFacturaGlobalId() {
		return tipoFacturaGlobalId;
	}

	public void setTipoFacturaGlobalId(int tipoFacturaGlobalId) {
		this.tipoFacturaGlobalId = tipoFacturaGlobalId;
	}

	public ControlMaestroMultiple getTipoFacturaGlobal() {
		return tipoFacturaGlobal;
	}

	public void setTipoFacturaGlobal(ControlMaestroMultiple tipoFacturaGlobal) {
		this.tipoFacturaGlobal = tipoFacturaGlobal;
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

	public Set<MedioPagoPV> getMediosPagoPV() {
		return mediosPagoPV;
	}

	public void setMediosPagoPV(Set<MedioPagoPV> mediosPagoPV) {
		this.mediosPagoPV = mediosPagoPV;
	}

	public List<SucursalPlantel> getPlanteles() {
		return planteles;
	}

	public void setPlanteles(List<SucursalPlantel> planteles) {
		this.planteles = planteles;
	}

	public Boolean getPlantelesBandera() {
		return plantelesBandera;
	}

	public void setPlantelesBandera(Boolean plantelesBandera) {
		this.plantelesBandera = plantelesBandera;
	}

	public CuentaBancaria getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Integer getCuentaBancariaId() {
		return cuentaBancariaId;
	}

	public void setCuentaBancariaId(Integer cuentaBancariaId) {
		this.cuentaBancariaId = cuentaBancariaId;
	}

	public Integer getCoordinadorId() {
		return coordinadorId;
	}

	public void setCoordinadorId(Integer coordinadorId) {
		this.coordinadorId = coordinadorId;
	}

	public Usuario getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}
}
