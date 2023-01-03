package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Departamento;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Entity
@Table(name = "ProveedoresContactos")
public class ProveedorContacto {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROC_ProveedorContactoId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "PROC_Activo", nullable = false, insertable = true, updatable = false)
	private Boolean activo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROC_PRO_ProveedorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PRO_ProveedorId")
	private Proveedor proveedor;

	@Column(name = "PROC_PRO_ProveedorId", nullable = false, insertable = false, updatable = false)
	private Integer proveedorId;


	@Column(name = "PROC_Nombre", length = 100, nullable = false, insertable = true, updatable = true)
	private String nombre;

	@Column(name = "PROC_PrimerApellido", length = 50, nullable = false, insertable = true, updatable = true)
	private String primerApellido;

	@Column(name = "PROC_SegundoApellido", length = 50, nullable = false, insertable = true, updatable = true)
	private String segundoApellido;

	@Column(name = "PROC_Departamento", length = 250, nullable = false, insertable = true, updatable = true)
	private String departamento;

	@Column(name = "PROC_Telefono", length = 25, nullable = false, insertable = true, updatable = true)
	private String telefono;

	@Column(name = "PROC_Extension", length = 3, nullable = true, insertable = true, updatable = true)
	private String extension;

	@Column(name = "PROC_CorreoElectronico", length = 50, nullable = false, insertable = true, updatable = true)
	private String correoElectronico;

	@Column(name = "PROC_HorarioAtencion", length = 250, nullable = false, insertable = true, updatable = true)
	private String horarioAtencion;

	@OneToOne
	@JoinColumn(name = "PROC_CMM_TipoContactoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
	private ControlMaestroMultiple tipoContacto;

	@Column(name = "PROC_CMM_TipoContactoId", nullable = false, insertable = true, updatable = true)
	private Integer tipoContactoId;

	@Column(name = "PROC_Predeterminado", nullable = false, insertable = true, updatable = true)
	private Boolean predeterminado;


	@CreationTimestamp
	@Column(name = "PROC_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "PROC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "PROC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne
	@JoinColumn(name = "PROC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "PROC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "PROC_FechaModificacion", nullable = true, insertable = false, updatable = true)
	private Date fechaModificacion;

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

	public Integer getProveedorId() {
	return proveedorId;
	}

	public void setProveedorId(Integer proveedorId) {
		this.proveedorId = proveedorId;
	}

	public String getNombre() {
	return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
	return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
	return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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

	public String getCorreoElectronico() {
	return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getHorarioAtencion() {
	return horarioAtencion;
	}

	public void setHorarioAtencion(String horarioAtencion) {
		this.horarioAtencion = horarioAtencion;
	}

	public Integer getTipoContactoId() {
	return tipoContactoId;
	}

	public void setTipoContactoId(Integer tipoContactoId) {
		this.tipoContactoId = tipoContactoId;
	}

	public Boolean getPredeterminado() {
	return predeterminado;
	}

	public void setPredeterminado(Boolean predeterminado) {
		this.predeterminado = predeterminado;
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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public ControlMaestroMultiple getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(ControlMaestroMultiple tipoContacto) {
		this.tipoContacto = tipoContacto;
	}
}
