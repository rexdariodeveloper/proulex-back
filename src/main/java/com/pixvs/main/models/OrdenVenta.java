package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 05/07/2021.
 */
@Entity
@Table(name = "OrdenesVenta")
public class OrdenVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OV_OrdenVentaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "OV_Codigo", length = 150, nullable = false, insertable = true, updatable = true)
    private String codigo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "OV_SUC_SucursalId", nullable = false, insertable = true, updatable = true)
    private Integer sucursalId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_CLI_ClienteId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "OV_CLI_ClienteId", nullable = true, insertable = true, updatable = true)
    private Integer clienteId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "OV_FechaOV", nullable = false, insertable = true, updatable = true)
    private Date fechaOV;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "OV_FechaRequerida", nullable = false, insertable = true, updatable = true)
    private Date fechaRequerida;

    @Column(name = "OV_DireccionOV", length = 150, nullable = true, insertable = true, updatable = true)
    private String direccionOV;

    @Column(name = "OV_EnviarA", length = 150, nullable = true, insertable = true, updatable = true)
    private String enviarA;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_MON_MonedaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "OV_MON_MonedaId", nullable = false, insertable = true, updatable = true)
    private Integer monedaId;

    @Column(name = "OV_DiazCredito", nullable = false, insertable = true, updatable = true)
    private Integer diazCredito;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_MPPV_MedioPagoPVId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MPPV_MedioPagoPVId")
    private MedioPagoPV medioPagoPV;

    @Column(name = "OV_MPPV_MedioPagoPVId", nullable = true, insertable = true, updatable = true)
    private Integer medioPagoPVId;

    @Column(name = "OV_ReferenciaPago", length = 50, nullable = true, insertable = true, updatable = true)
    private String referenciaPago;

    @Column(name = "OV_Comentario", length = 3000, nullable = true, insertable = true, updatable = true)
    private String comentario;

    @Column(name = "OV_LigaCentroPagos", length = 500, nullable = true, insertable = true, updatable = true)
    private String ligaCentroPagos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "OV_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_SCC_SucursalCorteCajaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SCC_SucursalCorteCajaId")
    private SucursalCorteCaja sucursalCorteCaja;

    @Column(name = "OV_SCC_SucursalCorteCajaId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalCorteCajaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_CMM_MetodoPago", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple metodoPago;

    @Column(name = "OV_CMM_MetodoPago", nullable = true, insertable = true, updatable = true)
    private Integer metodoPagoId;

    @Column(name = "OV_TipoCambio", nullable = false, insertable = true, updatable = true)
    private BigDecimal tipoCambio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_MON_MonedaSinConvertirId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda monedaSinConvertir;

    @Column(name = "OV_MON_MonedaSinConvertirId", nullable = false, insertable = true, updatable = true)
    private Integer monedaSinConvertirId;

    @CreationTimestamp
    @Column(name = "OV_FechaCreacion", nullable = false, insertable = true, updatable = true)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "OV_USU_CreadoPorId", nullable = true, insertable = true, updatable = true)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "OV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "OV_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "OV_CXCF_FacturaId")
    private Integer facturaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OV_CXCF_FacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private CXCFactura factura;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="OVD_OV_OrdenVentaId", referencedColumnName = "OV_OrdenVentaId", nullable = false, insertable = true, updatable = true)
    private List<OrdenVentaDetalle> detalles;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "OV_FechaPago", nullable = true, insertable = true, updatable = true)
    private Date fechaPago;

    @Transient
    private Integer plantelId;
    @Transient
    private Integer listaPreciosId;
    @Transient
    private String correoElectronico;
    @Transient
    private BigDecimal monto;
    @Transient
    private Boolean marcarEntregaPendienteInscripciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFechaOV() {
        return fechaOV;
    }

    public void setFechaOV(Date fechaOV) {
        this.fechaOV = fechaOV;
    }

    public Date getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(Date fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public String getDireccionOV() {
        return direccionOV;
    }

    public void setDireccionOV(String direccionOV) {
        this.direccionOV = direccionOV;
    }

    public String getEnviarA() {
        return enviarA;
    }

    public void setEnviarA(String enviarA) {
        this.enviarA = enviarA;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public Integer getDiazCredito() {
        return diazCredito;
    }

    public void setDiazCredito(Integer diazCredito) {
        this.diazCredito = diazCredito;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
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

    public List<OrdenVentaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenVentaDetalle> detalles) {
        this.detalles = detalles;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public MedioPagoPV getMedioPagoPV() {
        return medioPagoPV;
    }

    public void setMedioPagoPV(MedioPagoPV medioPagoPV) {
        this.medioPagoPV = medioPagoPV;
    }

    public Integer getMedioPagoPVId() {
        return medioPagoPVId;
    }

    public void setMedioPagoPVId(Integer medioPagoPVId) {
        this.medioPagoPVId = medioPagoPVId;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public Integer getPlantelId() {
        return plantelId;
    }

    public void setPlantelId(Integer plantelId) {
        this.plantelId = plantelId;
    }

    public Integer getListaPreciosId() {
        return listaPreciosId;
    }

    public void setListaPreciosId(Integer listaPreciosId) {
        this.listaPreciosId = listaPreciosId;
    }

    public String getLigaCentroPagos() {
        return ligaCentroPagos;
    }

    public void setLigaCentroPagos(String ligaCentroPagos) {
        this.ligaCentroPagos = ligaCentroPagos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }


    public SucursalCorteCaja getSucursalCorteCaja() {
        return sucursalCorteCaja;
    }

    public void setSucursalCorteCaja(SucursalCorteCaja sucursalCorteCaja) {
        this.sucursalCorteCaja = sucursalCorteCaja;
    }

    public Integer getSucursalCorteCajaId() {
        return sucursalCorteCajaId;
    }

    public void setSucursalCorteCajaId(Integer sucursalCorteCajaId) {
        this.sucursalCorteCajaId = sucursalCorteCajaId;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public CXCFactura getFactura() {
        return factura;
    }

    public void setFactura(CXCFactura factura) {
        this.factura = factura;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Boolean getMarcarEntregaPendienteInscripciones() {
        return marcarEntregaPendienteInscripciones;
    }

    public void setMarcarEntregaPendienteInscripciones(Boolean marcarEntregaPendienteInscripciones) {
        this.marcarEntregaPendienteInscripciones = marcarEntregaPendienteInscripciones;
    }

    public ControlMaestroMultiple getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(ControlMaestroMultiple metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Moneda getMonedaSinConvertir() {
        return monedaSinConvertir;
    }

    public void setMonedaSinConvertir(Moneda monedaSinConvertir) {
        this.monedaSinConvertir = monedaSinConvertir;
    }

    public Integer getMonedaSinConvertirId() {
        return monedaSinConvertirId;
    }

    public void setMonedaSinConvertirId(Integer monedaSinConvertirId) {
        this.monedaSinConvertirId = monedaSinConvertirId;
    }
}
