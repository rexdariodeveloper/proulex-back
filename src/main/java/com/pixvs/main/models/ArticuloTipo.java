package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Entity
@Table(name = "ArticulosTipos")
public class ArticuloTipo {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTT_ArticuloTipoId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "ARTT_CMM_TipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipo;

	@Column(name = "ARTT_CMM_TipoId", nullable = false, insertable = true, updatable = true)
	private Integer tipoId;

	@Column(name = "ARTT_Descripcion", length = 1000, nullable = false, insertable = true, updatable = true)
	private String descripcion;

	@Column(name = "ARTT_Activo", nullable = false, insertable = true, updatable = false)
	private Boolean activo;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDescripcion() {
	return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getActivo() {
	return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


}
