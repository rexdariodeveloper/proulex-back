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
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
@Entity
@Table(name = "ProveedoresFormasPagos")
public class ProveedorFormaPago {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROFP_ProveedorFormaPagoId", nullable = false, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "PROFP_Activo", nullable = false, insertable = true, updatable = false)
	private Boolean activo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFP_PRO_ProveedorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PRO_ProveedorId")
	private Proveedor proveedor;

	@Column(name = "PROFP_PRO_ProveedorId", nullable = false, insertable = false, updatable = false)
	private Integer proveedorId;

	@OneToOne
	@JoinColumn(name = "PROFP_FP_FormaPagoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "FP_FormaPagoId")
	private FormaPago formaPago;

	@Column(name = "PROFP_FP_FormaPagoId", nullable = false, insertable = true, updatable = true)
	private Integer formaPagoId;

	@OneToOne
	@JoinColumn(name = "PROFP_MON_MonedaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
	private Moneda moneda;

	@Column(name = "PROFP_MON_MonedaId", nullable = false, insertable = true, updatable = true)
	private Integer monedaId;

	@Column(name = "PROFP_Banco", length = 150, nullable = true, insertable = true, updatable = true)
	private String banco;

	@Column(name = "PROFP_Referencia", length = 100, nullable = true, insertable = true, updatable = true)
	private String referencia;

	@Column(name = "PROFP_NumeroCuenta", length = 50, nullable = true, insertable = true, updatable = true)
	private String numeroCuenta;

	@Column(name = "PROFP_CuentaClabe", length = 50, nullable = true, insertable = true, updatable = true)
	private String cuentaClabe;

	@Column(name = "PROFP_BicSwift", length = 50, nullable = true, insertable = true, updatable = true)
	private String bicSwift;

	@Column(name = "PROFP_Iban", length = 50, nullable = true, insertable = true, updatable = true)
	private String iban;

	@Column(name = "PROFP_NombreTitularTarjeta", length = 250, nullable = true, insertable = true, updatable = true)
	private String nombreTitularTarjeta;

	@Column(name = "PROFP_Predeterminado", nullable = false, insertable = true, updatable = true)
	private Boolean predeterminado;


	@CreationTimestamp
	@Column(name = "PROFP_FechaCreacion", nullable = false, insertable = true, updatable = false)
	private Date fechaCreacion;

	@OneToOne
	@JoinColumn(name = "PROFP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario creadoPor;

	@Column(name = "PROFP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
	private Integer creadoPorId;

	@OneToOne
	@JoinColumn(name = "PROFP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
	private Usuario modificadoPor;

	@Column(name = "PROFP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
	private Integer modificadoPorId;

	@UpdateTimestamp
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
	@Column(name = "PROFP_FechaModificacion", nullable = true, insertable = false, updatable = true)
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

	public Integer getFormaPagoId() {
	return formaPagoId;
	}

	public void setFormaPagoId(Integer formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public Integer getMonedaId() {
	return monedaId;
	}

	public void setMonedaId(Integer monedaId) {
		this.monedaId = monedaId;
	}

	public String getBanco() {
	return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getReferencia() {
	return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumeroCuenta() {
	return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCuentaClabe() {
	return cuentaClabe;
	}

	public void setCuentaClabe(String cuentaClabe) {
		this.cuentaClabe = cuentaClabe;
	}

	public String getBicSwift() {
	return bicSwift;
	}

	public void setBicSwift(String bicSwift) {
		this.bicSwift = bicSwift;
	}

	public String getIban() {
	return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public String getNombreTitularTarjeta() {
		return nombreTitularTarjeta;
	}

	public void setNombreTitularTarjeta(String nombreTitularTarjeta) {
		this.nombreTitularTarjeta = nombreTitularTarjeta;
	}
}
