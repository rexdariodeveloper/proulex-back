package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "InventariosMovimientos")
public class InventarioMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IM_InventarioMovimientoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "IM_ART_ArticuloId", nullable = false, insertable = true, updatable = false)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "IM_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "IM_LOC_LocalidadId", nullable = false, insertable = true, updatable = false)
    private Integer localidadId;

    @OneToOne
    @JoinColumn(name = "IM_LOC_LocalidadId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidad;

    @Column(name = "IM_UM_UnidadMedidaId", nullable = false, insertable = true, updatable = false)
    private Integer unidadMedidaId;

    @Column(name = "IM_Cantidad", nullable = false, insertable = true, updatable = false)
    private BigDecimal cantidad;

    @Column(name = "IM_CMM_TipoCostoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoCostoId;

    @OneToOne
    @JoinColumn(name = "IM_CMM_TipoCostoId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple tipoCosto;

    @Column(name = "IM_CostoUnitario", nullable = false, insertable = true, updatable = false)
    private BigDecimal costoUnitario;

    @Column(name = "IM_PrecioUnitario", nullable = true, insertable = true, updatable = false)
    private BigDecimal precioUnitario;

    @Column(name = "IM_CMM_TipoMovimientoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoMovimientoId;

    @OneToOne
    @JoinColumn(name = "IM_CMM_TipoMovimientoId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple tipoMovimiento;

    @Column(name = "IM_Razon", length = 1000, nullable = false, insertable = true, updatable = false)
    private String razon;

    @Column(name = "IM_Referencia", length = 150, nullable = false, insertable = true, updatable = false)
    private String referencia;

    @Column(name = "IM_ReferenciaMovtoId", nullable = true, insertable = true, updatable = false)
    private Integer referenciaMovimientoId;

    @CreationTimestamp
    @Column(name = "IM_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "IM_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "IM_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "IM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "IM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "IM_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Integer getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Integer unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getTipoCostoId() {
        return tipoCostoId;
    }

    public void setTipoCostoId(Integer tipoCostoId) {
        this.tipoCostoId = tipoCostoId;
    }

    public ControlMaestroMultiple getTipoCosto() {
        return tipoCosto;
    }

    public void setTipoCosto(ControlMaestroMultiple tipoCosto) {
        this.tipoCosto = tipoCosto;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public ControlMaestroMultiple getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(ControlMaestroMultiple tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getReferenciaMovimientoId() {
        return referenciaMovimientoId;
    }

    public void setReferenciaMovimientoId(Integer referenciaMovimientoId) {
        this.referenciaMovimientoId = referenciaMovimientoId;
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
}
