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
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Entity
@Table(name = "ProgramacionAcademicaComercial")
public class ProgramacionAcademicaComercial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAC_ProgramacionAcademicaComercialId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "PAC_Activo", nullable = false, insertable = true, updatable = false)
	private Boolean activo;

	@Column(name = "PAC_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
	private String codigo;

	@Column(name = "PAC_Nombre", length = 150, nullable = false, insertable = true, updatable = true)
	private String nombre;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAC_PACIC_CicloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PACIC_CicloId")
	private PACiclo paCiclo;

	@Column(name = "PAC_PACIC_CicloId", nullable = false, insertable = true, updatable = true)
	private Integer paCicloId;

	@CreationTimestamp
	@Column(name = "PAC_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "PAC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "PAC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne
	@JoinColumn(name = "PAC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "PAC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "PAC_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="PACD_PAC_ProgramacionAcademicaComercialId", referencedColumnName = "PAC_ProgramacionAcademicaComercialId", nullable = false, insertable = true, updatable = true)
	@OrderBy("PACD_PAMOD_ModalidadId, PACD_FechaInicio ASC")
	private List<ProgramacionAcademicaComercialDetalle> detalles;

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

	public PACiclo getPaCiclo() {
		return paCiclo;
	}

	public void setPaCiclo(PACiclo paCiclo) {
		this.paCiclo = paCiclo;
	}

	public Integer getPaCicloId() {
		return paCicloId;
	}

	public void setPaCicloId(Integer paCicloId) {
		this.paCicloId = paCicloId;
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

	public List<ProgramacionAcademicaComercialDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<ProgramacionAcademicaComercialDetalle> detalles) {
		this.detalles = detalles;
	}
}
