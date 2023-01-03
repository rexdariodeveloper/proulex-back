package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CXCPagosDetalles")
public class CXCPagoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXCPD_PagoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXCPD_CXCP_PagoId", insertable = false, updatable = false, nullable = false)
    private Integer pagoId;

    @OneToOne
    @JoinColumn(name = "CXCPD_CXCP_PagoId", insertable = false, updatable = false, referencedColumnName = "CXCP_PagoId")
    private CXCPago pago;

    @Column(name = "CXCPD_CXCF_DoctoRelacionadoId", nullable = false)
    private int doctoRelacionadoId;

    @OneToOne
    @JoinColumn(name = "CXCPD_CXCF_DoctoRelacionadoId", insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private CXCFactura doctoRelacionado;

    @Column(name = "CXCPD_NoParcialidad", nullable = false)
    private int noParcialidad;

    @Column(name = "CXCPD_ImporteSaldoAnterior", nullable = false)
    private BigDecimal importeSaldoAnterior;

    @Column(name = "CXCPD_ImportePagado", nullable = false)
    private BigDecimal importePagado;

    @Column(name = "CXCPD_ImporteSaldoInsoluto", nullable = false)
    private BigDecimal importeSaldoInsoluto;

    @Column(name = "CXCPD_CMM_ObjetoImpuestoId")
    private Integer objetoImpuestoId;

    @OneToOne
    @JoinColumn(name = "CXCPD_CMM_ObjetoImpuestoId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple objetoImpuesto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public CXCPago getPago() {
        return pago;
    }

    public void setPago(CXCPago pago) {
        this.pago = pago;
    }

    public int getDoctoRelacionadoId() {
        return doctoRelacionadoId;
    }

    public void setDoctoRelacionadoId(int doctoRelacionadoId) {
        this.doctoRelacionadoId = doctoRelacionadoId;
    }

    public CXCFactura getDoctoRelacionado() {
        return doctoRelacionado;
    }

    public void setDoctoRelacionado(CXCFactura doctoRelacionado) {
        this.doctoRelacionado = doctoRelacionado;
    }

    public int getNoParcialidad() {
        return noParcialidad;
    }

    public void setNoParcialidad(int numeroParcialidad) {
        this.noParcialidad = numeroParcialidad;
    }

    public BigDecimal getImporteSaldoAnterior() {
        return importeSaldoAnterior;
    }

    public void setImporteSaldoAnterior(BigDecimal importeSaldoAnterior) {
        this.importeSaldoAnterior = importeSaldoAnterior;
    }

    public BigDecimal getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(BigDecimal importePagado) {
        this.importePagado = importePagado;
    }

    public BigDecimal getImporteSaldoInsoluto() {
        return importeSaldoInsoluto;
    }

    public void setImporteSaldoInsoluto(BigDecimal importeSaldoInsoluto) {
        this.importeSaldoInsoluto = importeSaldoInsoluto;
    }

    public Integer getObjetoImpuestoId() {
        return objetoImpuestoId;
    }

    public void setObjetoImpuestoId(Integer objetoImpuestoId) {
        this.objetoImpuestoId = objetoImpuestoId;
    }

    public ControlMaestroMultiple getObjetoImpuesto() {
        return objetoImpuesto;
    }

    public void setObjetoImpuesto(ControlMaestroMultiple objetoImpuesto) {
        this.objetoImpuesto = objetoImpuesto;
    }
}