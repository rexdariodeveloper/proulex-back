package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "CXCFacturasDetalles")
public class CXCFacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXCFD_FacturadetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXCFD_CXCF_FacturaId", nullable = false, insertable = false, updatable = false)
    private int facturaId;

    @OneToOne
    @JoinColumn(name = "CXCFD_CXCF_FacturaId", insertable = false, updatable = false, referencedColumnName = "CXCF_FacturaId")
    private CXCFactura factura;

    @Column(name = "CXCFD_ClaveProdServ", nullable = false)
    private String claveProdServ;

    @Column(name = "CXCFD_NoIdentificacion")
    private String noIdentificacion;

    @Column(name = "CXCFD_Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "CXCFD_UM_UnidadMedidaId", nullable = false)
    private int unidadMedidaId;

    @OneToOne
    @JoinColumn(name = "CXCFD_UM_UnidadMedidaId", insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
    private UnidadMedida unidadMedida;

    @Column(name = "CXCFD_Cantidad", nullable = false)
    private BigDecimal cantidad;

    @Column(name = "CXCFD_ValorUnitario", nullable = false)
    private BigDecimal valorUnitario;

    @Column(name = "CXCFD_Importe", nullable = false)
    private BigDecimal importe;

    @Column(name = "CXCFD_Descuento")
    private BigDecimal descuento;

    @Column(name = "CXCFD_ReferenciaId")
    private Integer referenciaId;

    @Column(name = "CXCFD_CMM_ObjetoImpuestoId", nullable = true)
    private Integer objetoImpuestoId;

    @OneToOne
    @JoinColumn(name = "CXCFD_CMM_ObjetoImpuestoId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple objetoImpuesto;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "CXCFDI_CXCFD_FacturaDetalleId", referencedColumnName = "CXCFD_FacturadetalleId", nullable = false)
    private List<CXCFacturaDetalleImpuesto> impuestos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public CXCFactura getFactura() {
        return factura;
    }

    public void setFactura(CXCFactura factura) {
        this.factura = factura;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(int unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
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

    public List<CXCFacturaDetalleImpuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<CXCFacturaDetalleImpuesto> impuestos) {
        this.impuestos = impuestos;
    }
}
