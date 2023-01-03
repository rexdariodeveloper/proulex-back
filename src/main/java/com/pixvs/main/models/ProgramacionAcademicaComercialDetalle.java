package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Entity
@Table(name = "ProgramacionAcademicaComercialDetalles")
public class ProgramacionAcademicaComercialDetalle {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PACD_ProgramacionAcademicaComercialDetalleId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "PACD_PAC_ProgramacionAcademicaComercialId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAC_ProgramacionAcademicaComercialId")
	private ProgramacionAcademicaComercial programacionAcademicaComercial;

	@Column(name = "PACD_PAC_ProgramacionAcademicaComercialId", nullable = false, insertable = false, updatable = false)
	private Integer programacionAcademicaComercialId;

	@OneToOne
	@JoinColumn(name = "PACD_CMM_IdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple idioma;

	@Column(name = "PACD_CMM_IdiomaId", nullable = false, insertable = true, updatable = true)
	private Integer idiomaId;

	@OneToOne
	@JoinColumn(name = "PACD_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
	private PAModalidad paModalidad;

	@Column(name = "PACD_PAMOD_ModalidadId", nullable = false, insertable = true, updatable = true)
	private Integer paModalidadId;

	@Column(name = "PACD_FechaInicio", nullable = false, insertable = true, updatable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaInicio;

	@Column(name = "PACD_FechaFin", nullable = false, insertable = true, updatable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaFin;

	@Column(name = "PACD_NumeroClases", nullable = false, insertable = true, updatable = true)
	private Integer numeroClases;

	@Column(name = "PACD_Comentarios", length = 255, nullable = true, insertable = true, updatable = true)
	private String comentarios;

	@ManyToMany
	@JoinTable(name = "ProgramacionAcademicaComercialDetallesProgramas", joinColumns = {@JoinColumn(name = "PACDP_PACD_ProgramacionAcademicaComercialDetalleId")}, inverseJoinColumns = {@JoinColumn(name = "PACD_PROG_ProgramaId")})
	private Set<Programa> programas = new HashSet<>();


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProgramacionAcademicaComercial getProgramacionAcademicaComercial() {
		return programacionAcademicaComercial;
	}

	public void setProgramacionAcademicaComercial(ProgramacionAcademicaComercial programacionAcademicaComercial) {
		this.programacionAcademicaComercial = programacionAcademicaComercial;
	}

	public Integer getProgramacionAcademicaComercialId() {
		return programacionAcademicaComercialId;
	}

	public void setProgramacionAcademicaComercialId(Integer programacionAcademicaComercialId) {
		this.programacionAcademicaComercialId = programacionAcademicaComercialId;
	}

	public PAModalidad getPaModalidad() {
		return paModalidad;
	}

	public void setPaModalidad(PAModalidad modalidad) {
		this.paModalidad = modalidad;
	}

	public Integer getPaModalidadId() {
		return paModalidadId;
	}

	public void setPaModalidadId(Integer modalidadId) {
		this.paModalidadId = modalidadId;
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

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Set<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(Set<Programa> programas) {
		this.programas = programas;
	}

	public ControlMaestroMultiple getIdioma() {
		return idioma;
	}

	public void setIdioma(ControlMaestroMultiple idioma) {
		this.idioma = idioma;
	}

	public Integer getIdiomaId() {
		return idiomaId;
	}

	public void setIdiomaId(Integer idiomaId) {
		this.idiomaId = idiomaId;
	}

	public Integer getNumeroClases() {
		return numeroClases;
	}

	public void setNumeroClases(Integer numeroClases) {
		this.numeroClases = numeroClases;
	}
}
