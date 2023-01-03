package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
@Entity
@Table(name = "ArticulosSubtipos")
public class ArticuloSubtipo {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTST_ArticuloSubtipoId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "ARTST_ARTT_ArticuloTipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARTT_ArticuloTipoId")
	private ArticuloTipo articuloTipo;

	@Column(name = "ARTST_ARTT_ArticuloTipoId", nullable = false, insertable = true, updatable = true)
	private Integer articuloTipoId;

	@Column(name = "ARTST_Descripcion", length = 1000, nullable = false, insertable = true, updatable = true)
	private String descripcion;

	@Column(name = "ARTST_Activo", nullable = false, insertable = true, updatable = false)
	private Boolean activo;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ArticuloTipo getArticuloTipo() {
	return articuloTipo;
	}

	public void setArticuloTipo(ArticuloTipo articuloTipo) {
		this.articuloTipo = articuloTipo;
	}

	public Integer getArticuloTipoId() {
	return articuloTipoId;
	}

	public void setArticuloTipoId(Integer articuloTipoId) {
		this.articuloTipoId = articuloTipoId;
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
