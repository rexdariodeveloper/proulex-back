package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 05/07/2021.
 */
@Entity
@Table(name = "OrdenesVentaDetalles")
public class OrdenVentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_OV_OrdenVentaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OV_OrdenVentaId")
    private OrdenVenta ordenVenta;

    @Column(name = "OVD_OV_OrdenVentaId", nullable = false, insertable = false, updatable = false)
    private Integer ordenVentaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "OVD_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_UM_UnidadMedidaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
    private UnidadMedida unidadMedida;

    @Column(name = "OVD_UM_UnidadMedidaId", nullable = false, insertable = true, updatable = true)
    private Integer unidadMedidaId;

    @Column(name = "OVD_FactorConversion", nullable = false, insertable = true, updatable = true)
    private BigDecimal factorConversion;

    @Column(name = "OVD_Cantidad", nullable = false, insertable = true, updatable = true)
    private BigDecimal cantidad;

    @Column(name = "OVD_Precio", nullable = false, insertable = true, updatable = true)
    private BigDecimal precio;

    @Column(name = "OVD_Descuento", nullable = true, insertable = true, updatable = true)
    private BigDecimal descuento;

    @Column(name = "OVD_IVA", nullable = true, insertable = true, updatable = true)
    private BigDecimal iva;

    @Column(name = "OVD_IVAExento", nullable = true, insertable = true, updatable = true)
    private Boolean ivaExento;

    @Column(name = "OVD_IEPS", nullable = true, insertable = true, updatable = true)
    private BigDecimal ieps;

    @Column(name = "OVD_IEPSCuotaFija", nullable = true, insertable = true, updatable = true)
    private BigDecimal iepsCuotaFija;

    @Column(name = "OVD_CuentaOV", length = 18, nullable = true, insertable = true, updatable = true)
    private String cuentaOV;

    @Column(name = "OVD_Comentarios", length = 255, nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @Column(name = "OVD_PrecioSinConvertir", nullable = false, insertable = true, updatable = true)
    private BigDecimal precioSinConvertir;

    @Column(name = "OVD_DescuentoSinConvertir", nullable = true, insertable = true, updatable = true)
    private BigDecimal descuentoSinConvertir;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_OVD_DetallePadreId", nullable = true, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle detallePadre;

    @Column(name = "OVD_OVD_DetallePadreId", nullable = true, insertable = true, updatable = false)
    private Integer detallePadreId;


    @CreationTimestamp
    @Column(name = "OVD_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "OVD_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "OVD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "OVD_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Transient
    String descripcion;
    @Transient
    Integer alumnoId;
    @Transient
    String nombreAlumno;
    @Transient
    BigDecimal montoSubtotal;
    @Transient
    BigDecimal montoIva;
    @Transient
    BigDecimal montoIeps;
    @Transient
    BigDecimal total;
    @Transient
    Integer idTmp;
    @Transient
    Integer grupoId;
    @Transient
    Integer localidadId;
    @Transient
    Boolean noAplicaDescuentos; // Utilizada en el PV cuando se quiere ignorar el detalle en el proceso de descuentos

    // propiedades usadas para inscripciones de alumnos sin grupo
    @Transient
    Integer sucursalId;
    @Transient
    Integer programaId;
    @Transient
    Integer idiomaId;
    @Transient
    Integer modalidadId;
    @Transient
    Integer tipoGrupoId;
    @Transient
    Integer horarioId;
    @Transient
    Integer nivel;
    @Transient
    Integer numeroGrupo;
    @Transient
    String comentarioReinscripcion;

    // propiedades usadas para becas sindicato
    @Transient
    Integer becaUDGId;

    // Propiedades usadas para cambio de grupo
    @Transient
    Boolean cambioGrupo;
    @Transient
    Integer nuevoGrupoId;
    
    // Propiedades usadas para procesar vales de certificación
    @Transient
    Integer programaIdiomaCertificacionValeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenVenta getOrdenVenta() {
        return ordenVenta;
    }

    public void setOrdenVenta(OrdenVenta ordenVenta) {
        this.ordenVenta = ordenVenta;
    }

    public Integer getOrdenVentaId() {
        return ordenVentaId;
    }

    public void setOrdenVentaId(Integer ordenVentaId) {
        this.ordenVentaId = ordenVentaId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Integer unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(BigDecimal factorConversion) {
        this.factorConversion = factorConversion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Boolean getIvaExento() {
        return ivaExento;
    }

    public void setIvaExento(Boolean ivaExento) {
        this.ivaExento = ivaExento;
    }

    public BigDecimal getIeps() {
        return ieps;
    }

    public void setIeps(BigDecimal ieps) {
        this.ieps = ieps;
    }

    public BigDecimal getIepsCuotaFija() {
        return iepsCuotaFija;
    }

    public void setIepsCuotaFija(BigDecimal iepsCuotaFija) {
        this.iepsCuotaFija = iepsCuotaFija;
    }

    public String getCuentaOV() {
        return cuentaOV;
    }

    public void setCuentaOV(String cuentaOV) {
        this.cuentaOV = cuentaOV;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getIdTmp() {
        return idTmp;
    }

    public void setIdTmp(Integer idTmp) {
        this.idTmp = idTmp;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public BigDecimal getMontoSubtotal() {
        return montoSubtotal;
    }

    public void setMontoSubtotal(BigDecimal montoSubtotal) {
        this.montoSubtotal = montoSubtotal;
    }

    public BigDecimal getMontoIva() {
        return montoIva;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public BigDecimal getMontoIeps() {
        return montoIeps;
    }

    public void setMontoIeps(BigDecimal montoIeps) {
        this.montoIeps = montoIeps;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public Integer getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public Integer getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Integer horarioId) {
        this.horarioId = horarioId;
    }

    public Integer getNumeroGrupo() {
        return numeroGrupo;
    }

    public void setNumeroGrupo(Integer numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public String getComentarioReinscripcion() {
        return comentarioReinscripcion;
    }

    public void setComentarioReinscripcion(String comentarioReinscripcion) {
        this.comentarioReinscripcion = comentarioReinscripcion;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
    }

    public Integer getBecaUDGId() {
        return becaUDGId;
    }

    public void setBecaUDGId(Integer becaUDGId) {
        this.becaUDGId = becaUDGId;
    }

    public OrdenVentaDetalle getDetallePadre() {
        return detallePadre;
    }

    public void setDetallePadre(OrdenVentaDetalle detallePadre) {
        this.detallePadre = detallePadre;
    }

    public Integer getDetallePadreId() {
        return detallePadreId;
    }

    public void setDetallePadreId(Integer detallePadreId) {
        this.detallePadreId = detallePadreId;
    }

    public Boolean getNoAplicaDescuentos() {
        return noAplicaDescuentos;
    }

    public void setNoAplicaDescuentos(Boolean noAplicaDescuentos) {
        this.noAplicaDescuentos = noAplicaDescuentos;
    }

    public Boolean getCambioGrupo() {
        return cambioGrupo;
    }

    public void setCambioGrupo(Boolean cambioGrupo) {
        this.cambioGrupo = cambioGrupo;
    }

    public Integer getNuevoGrupoId() {
        return nuevoGrupoId;
    }

    public void setNuevoGrupoId(Integer nuevoGrupoId) {
        this.nuevoGrupoId = nuevoGrupoId;
    }

    public Integer getTipoGrupoId() {
        return tipoGrupoId;
    }

    public void setTipoGrupoId(Integer tipoGrupoId) {
        this.tipoGrupoId = tipoGrupoId;
    }

    public BigDecimal getPrecioSinConvertir() {
        return precioSinConvertir;
    }

    public void setPrecioSinConvertir(BigDecimal precioSinConvertir) {
        this.precioSinConvertir = precioSinConvertir;
    }

    public BigDecimal getDescuentoSinConvertir() {
        return descuentoSinConvertir;
    }

    public void setDescuentoSinConvertir(BigDecimal descuentoSinConvertir) {
        this.descuentoSinConvertir = descuentoSinConvertir;
    }

    public Integer getProgramaIdiomaCertificacionValeId() {
        return programaIdiomaCertificacionValeId;
    }

    public void setProgramaIdiomaCertificacionValeId(Integer programaIdiomaCertificacionValeId) {
        this.programaIdiomaCertificacionValeId = programaIdiomaCertificacionValeId;
    }
}
