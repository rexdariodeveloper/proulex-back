package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Puesto;
import com.pixvs.main.models.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 */
@Entity
@Table(name = "Departamentos")
public class Departamento {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEP_DepartamentoId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "DEP_DEP_DepartamentoPadreId", nullable = true, insertable = false, updatable = false, referencedColumnName = "DEP_DepartamentoId")
	private Departamento departamentoPadre;

	@Column(name = "DEP_DEP_DepartamentoPadreId", nullable = true, insertable = true, updatable = true)
	private Integer departamentoPadreId;

	@Column(name = "DEP_Prefijo", length = 20, nullable = false, insertable = true, updatable = true)
	private String prefijo;

	@Column(name = "DEP_Nombre", length = 150, nullable = false, insertable = true, updatable = true)
	private String nombre;

	@OneToOne
	@JoinColumn(name = "DEP_USU_ResponsableId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario responsable;

	@Column(name = "DEP_USU_ResponsableId", nullable = false, insertable = true, updatable = true)
	private Integer responsableId;

	@Column(name = "DEP_Autoriza", nullable = false, insertable = true, updatable = true)
	private Boolean autoriza;

	@Column(name = "DEP_Activo", nullable = false, insertable = true, updatable = true)
	private Boolean activo;


	@CreationTimestamp
	@Column(name = "DEP_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "DEP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "DEP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne
	@JoinColumn(name = "DEP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "DEP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@Column(name = "DEP_NumeroVacantes", nullable = true, insertable = true, updatable = true)
	private Integer numeroVacantes;

	@Column(name = "DEP_PropositoPuesto", length = 250, nullable = true, insertable = true, updatable = true)
	private String propositoPuesto;

	@Column(name = "DEP_Observaciones", length = 1000, nullable = true, insertable = true, updatable = true)
	private String observaciones;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "DEP_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="DEP_DEP_DepartamentoPadreId", referencedColumnName = "DEP_DepartamentoId", nullable = false, insertable = false, updatable = false)
	private List<Departamento> departamentos;

	@ManyToMany
	@JoinTable(name = "UsuariosDepartamentos", joinColumns = @JoinColumn(name = "USUD_DEP_DepartamentoId"), inverseJoinColumns = @JoinColumn(name = "USUD_USU_UsuarioId"))
	private Set<Usuario> usuariosPermisos;

	@OneToOne
	@JoinColumn(name = "DEP_PUE_PuestoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PUE_PuestoId")
	private Puesto puesto;

	@Column(name = "DEP_PUE_PuestoId", nullable = true, insertable = true, updatable = true)
	private Integer puestoId;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="DEPREHA_DEP_DepartamentoId", referencedColumnName = "DEP_DepartamentoId", nullable = true, insertable = true, updatable = true)
	private List<DepartamentoResponsabilidadHabilidad> responsabilidadHabilidad;

	@Transient
	private Integer[] responsabilidadesHabilidadesEliminar;

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

	public Integer getDepartamentoPadreId() {
	return departamentoPadreId;
	}

	public void setDepartamentoPadreId(Integer departamentoPadreId) {
		this.departamentoPadreId = departamentoPadreId;
	}

	public String getPrefijo() {
	return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getNombre() {
	return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getResponsableId() {
	return responsableId;
	}

	public void setResponsableId(Integer responsableId) {
		this.responsableId = responsableId;
	}

	public Boolean getAutoriza() {
	return autoriza;
	}

	public void setAutoriza(Boolean autoriza) {
		this.autoriza = autoriza;
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
        creadoPorId = creadoPorId;
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

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public Departamento getDepartamentoPadre() {
		return departamentoPadre;
	}

	public void setDepartamentoPadre(Departamento departamentoPadre) {
		this.departamentoPadre = departamentoPadre;
	}

	public Set<Usuario> getUsuariosPermisos() {
		return usuariosPermisos;
	}

	public void setUsuariosPermisos(Set<Usuario> usuariosPermisos) {
		this.usuariosPermisos = usuariosPermisos;
	}

	public List<DepartamentoResponsabilidadHabilidad> getResponsabilidadHabilidad() {
		return responsabilidadHabilidad;
	}

	public void setResponsabilidadHabilidad(List<DepartamentoResponsabilidadHabilidad> responsabilidadHabilidad) {
		this.responsabilidadHabilidad = responsabilidadHabilidad;
	}

	public Integer getNumeroVacantes() { return numeroVacantes; }

	public void setNumeroVacantes(Integer numeroVacantes) { this.numeroVacantes = numeroVacantes; }

	public String getPropositoPuesto() { return propositoPuesto; }

	public void setPropositoPuesto(String propositoPuesto) { this.propositoPuesto = propositoPuesto; }

	public String getObservaciones() { return observaciones; }

	public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

	public Integer getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(Integer puestoId) {
		this.puestoId = puestoId;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Integer[] getResponsabilidadesHabilidadesEliminar() {
		return responsabilidadesHabilidadesEliminar;
	}

	public void setResponsabilidadesHabilidadesEliminar(Integer[] responsabilidadesHabilidadesEliminar) {
		this.responsabilidadesHabilidadesEliminar = responsabilidadesHabilidadesEliminar;
	}
}
