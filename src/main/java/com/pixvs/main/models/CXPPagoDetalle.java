package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPPagosDetalles")
public class CXPPagoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPPD_CXPPagoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CXPPD_CXPP_CXPPagoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPP_CXPPagoId")
    private CXPPago cxpPago;

    @Column(name = "CXPPD_CXPP_CXPPagoId", nullable = true, insertable = false, updatable = false)
    private Integer cxpPagoId;

    @OneToOne
    @JoinColumn(name = "CXPPD_CXPF_CXPFacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPF_CXPFacturaId")
    private CXPFactura cxpFactura;

    @Column(name = "CXPPD_CXPF_CXPFacturaId", nullable = true, insertable = true, updatable = false)
    private Integer cxpFacturaId;

    @Column(name = "CXPPD_MontoAplicado", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoAplicado;

    @Column(name = "CXPPD_Comentario", length = 250, insertable = true, updatable = true)
    private String comentario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CXPPago getCxpPago() {
        return cxpPago;
    }

    public void setCxpPago(CXPPago cxpPago) {
        this.cxpPago = cxpPago;
    }

    public Integer getCxpPagoId() {
        return cxpPagoId;
    }

    public void setCxpPagoId(Integer cxpPagoId) {
        this.cxpPagoId = cxpPagoId;
    }

    public CXPFactura getCxpFactura() {
        return cxpFactura;
    }

    public void setCxpFactura(CXPFactura cxpFactura) {
        this.cxpFactura = cxpFactura;
    }

    public Integer getCxpFacturaId() {
        return cxpFacturaId;
    }

    public void setCxpFacturaId(Integer cxpFacturaId) {
        this.cxpFacturaId = cxpFacturaId;
    }

    public BigDecimal getMontoAplicado() {
        return montoAplicado;
    }

    public void setMontoAplicado(BigDecimal montoAplicado) {
        this.montoAplicado = montoAplicado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
