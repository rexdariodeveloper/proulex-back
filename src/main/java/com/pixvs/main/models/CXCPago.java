package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CXCPagos")
public class CXCPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXCP_PagoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXCP_CXCF_FacturaId", nullable = false, updatable = false)
    private Integer facturaId;

    @OneToOne
    @JoinColumn(name = "CXCP_CXCF_FacturaId", insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private CXCFactura factura;

    @Column(name = "CXCP_Version", nullable = false)
    private String version;

    @Column(name = "CXCP_Fecha", nullable = false)
    private Date fecha;

    @Column(name = "CXCP_FP_FormaPagoId", nullable = false)
    private int formaPagoId;

    @OneToOne
    @JoinColumn(name = "CXCP_FP_FormaPagoId", insertable = false, updatable = false, referencedColumnName = "FP_FormaPagoId")
    private FormaPago formaPago;

    @Column(name = "CXCP_MON_MonedaId", nullable = false)
    private int monedaId;

    @OneToOne
    @JoinColumn(name = "CXCP_MON_MonedaId", insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CXCP_TipoCambio", nullable = false)
    private BigDecimal tipoCambio;

    @Column(name = "CXCP_NoOperacion")
    private String noOperacion;

    @Column(name = "CXCP_CuentaOrdenante")
    private String cuentaOrdenante;

    @Column(name = "CXCP_CuentaOrdenanteEmisorRFC")
    private String cuentaOrdenanteEmisorRFC;

    @Column(name = "CXCP_CuentaOrdenanteNombreBanco")
    private String cuentaOrdenanteNombreBanco;

    @Column(name = "CXCP_BAC_CuentaBeneficiarioId")
    private Integer cuentaBeneficiarioId;

    @Column(name = "CXCP_CuentaBeneficiario")
    private String cuentaBeneficiario;

    @Column(name = "CXCP_CuentaBeneficiarioEmisorRFC")
    private String cuentaBeneficiarioEmisorRFC;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "CXCPD_CXCP_PagoId", referencedColumnName = "CXCP_PagoId", nullable = false)
    private List<CXCPagoDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(int formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public int getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(int monedaId) {
        this.monedaId = monedaId;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getNoOperacion() {
        return noOperacion;
    }

    public void setNoOperacion(String noOperacion) {
        this.noOperacion = noOperacion;
    }

    public String getCuentaOrdenante() {
        return cuentaOrdenante;
    }

    public void setCuentaOrdenante(String cuentaOrdenante) {
        this.cuentaOrdenante = cuentaOrdenante;
    }

    public String getCuentaOrdenanteEmisorRFC() {
        return cuentaOrdenanteEmisorRFC;
    }

    public void setCuentaOrdenanteEmisorRFC(String cuentaOrdenanteEmisorRFC) {
        this.cuentaOrdenanteEmisorRFC = cuentaOrdenanteEmisorRFC;
    }

    public String getCuentaOrdenanteNombreBanco() {
        return cuentaOrdenanteNombreBanco;
    }

    public void setCuentaOrdenanteNombreBanco(String cuentaOrdenanteNombreBanco) {
        this.cuentaOrdenanteNombreBanco = cuentaOrdenanteNombreBanco;
    }

    public Integer getCuentaBeneficiarioId() {
        return cuentaBeneficiarioId;
    }

    public void setCuentaBeneficiarioId(Integer cuentaBeneficiarioId) {
        this.cuentaBeneficiarioId = cuentaBeneficiarioId;
    }

    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    public String getCuentaBeneficiarioEmisorRFC() {
        return cuentaBeneficiarioEmisorRFC;
    }

    public void setCuentaBeneficiarioEmisorRFC(String cuentaBeneficiarioEmisorRFC) {
        this.cuentaBeneficiarioEmisorRFC = cuentaBeneficiarioEmisorRFC;
    }

    public List<CXCPagoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXCPagoDetalle> detalles) {
        this.detalles = detalles;
    }
}