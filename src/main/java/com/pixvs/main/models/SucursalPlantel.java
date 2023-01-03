package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "SucursalesPlanteles")
public class SucursalPlantel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SP_SucursalPlantelId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "SP_SUC_SucursalId", nullable = false, insertable = false, updatable = false)
	private Integer sucursalId;

	@Column(name = "SP_Codigo", length = 3, nullable = false, insertable = true, updatable = true)
	private String codigoSucursal;

	@Column(name = "SP_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
	private String nombre;

	@OneToOne
	@JoinColumn(name = "SP_USU_ResponsableId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario responsable;

	@Column(name = "SP_USU_ResponsableId", nullable = false, insertable = true, updatable = true)
	private Integer responsableId;

	@OneToOne
	@JoinColumn(name = "SP_ALM_AlmacenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
	private Almacen almacen;

	@Column(name = "SP_ALM_AlmacenId", nullable = false, insertable = true, updatable = true)
	private Integer almacenId;

	@OneToOne
	@JoinColumn(name = "SP_LOC_LocalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "LOC_LocalidadId")
	private Localidad localidad;

	@Column(name = "SP_LOC_LocalidadId", nullable = false, insertable = true, updatable = true)
	private Integer localidadId;

	@Column(name = "SP_Direccion", length = 100, nullable = false, insertable = true, updatable = true)
	private String direccion;

	@Column(name = "SP_CP", length = 5, nullable = false, insertable = true, updatable = true)
	private String cp;

	@Column(name = "SP_Colonia", length = 50, nullable = false, insertable = true, updatable = true)
	private String colonia;

	@OneToOne
	@JoinColumn(name = "SP_PAI_PaisId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PAI_PaisId")
	private Pais pais;

	@Column(name = "SP_PAI_PaisId", nullable = false, insertable = true, updatable = true)
	private Integer paisId;

	@OneToOne
	@JoinColumn(name = "SP_EST_EstadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EST_EstadoId")
	private Estado estado;

	@Column(name = "SP_EST_EstadoId", nullable = false, insertable = true, updatable = true)
	private Integer estadoId;

	@Column(name = "SP_Municipio", length = 50, nullable = false, insertable = true, updatable = true)
	private String municipio;

	@Column(name = "SP_CorreoElectronico", length = 50, nullable = false, insertable = true, updatable = true)
	private String correoElectronico;

	@Column(name = "SP_TelefonoFijo", length = 50, nullable = true, insertable = true, updatable = true)
	private String telefonoFijo;

	@Column(name = "SP_TelefonoMovil", length = 50, nullable = true, insertable = true, updatable = true)
	private String telefonoMovil;

	@Column(name = "SP_TelefonoTrabajo", length = 50, nullable = true, insertable = true, updatable = true)
	private String telefonoTrabajo;

	@Column(name = "SP_TelefonoTrabajoExtension", length = 10, nullable = true, insertable = true, updatable = true)
	private String telefonoTrabajoExtension;

	@Column(name = "SP_TelefonoMensajeriaInstantanea", length = 50, nullable = true, insertable = true, updatable = true)
	private String telefonoMensajeriaInstantanea;

	@Column(name = "SP_Activo", nullable = false, insertable = true, updatable = true)
	private Boolean activo;

	@CreationTimestamp
	@Column(name = "SP_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "SP_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "SP_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
	private Integer creadoPorId;

	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "SP_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToOne
	@JoinColumn(name = "SP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "SP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

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

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public Integer getAlmacenId() {
		return almacenId;
	}

	public void setAlmacenId(Integer almacenId) {
		this.almacenId = almacenId;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Integer getLocalidadId() {
		return localidadId;
	}

	public void setLocalidadId(Integer localidadId) {
		this.localidadId = localidadId;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
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

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
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

	public Integer getSucursalId() {
		return sucursalId;
	}

	public void setSucursalId(Integer sucursalId) {
		this.sucursalId = sucursalId;
	}
}
