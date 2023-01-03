package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosRHRetiroCajaAhorro")
public class CXPSolicitudPagoRHRetiroCajaAhorro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRHRCA_CXPSolicitudPagoRhRetiroCajaAhorroId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId", insertable = false, updatable = false, referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId")
    private CXPSolicitudPagoRH cxpSolicitudPagoRH;

    @Column(name = "CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId", insertable = false, updatable = false)
    private Integer cpxSolicitudPagoRHId;

    @OneToOne
    @JoinColumn(name = "CPXSPRHID_CMM_TipoRetiroId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRetiro;

    @Column(name = "CPXSPRHID_CMM_TipoRetiroId", nullable = false, insertable = true, updatable = false)
    private Integer tipoRetiroId;

    @Column(name = "CPXSPRHID_AhorroAcumulado", nullable = false, insertable = true, updatable = true)
    private BigDecimal ahorroAcumulado;

    @Column(name = "CPXSPRHID_CantidadRetirar", nullable = false, insertable = true, updatable = true)
    private BigDecimal cantidadRetirar;

    @Column(name = "CPXSPRHID_MotivoRetiro", nullable = false)
    private String motivoRetiro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCpxSolicitudPagoRhId() {
        return cpxSolicitudPagoRHId;
    }

    public void setCpxSolicitudPagoRhId(Integer cpxSolicitudPagoRhId) {
        this.cpxSolicitudPagoRHId = cpxSolicitudPagoRhId;
    }

    public ControlMaestroMultiple getTipoRetiro() {
        return tipoRetiro;
    }

    public void setTipoRetiro(ControlMaestroMultiple tipoRetiro) {
        this.tipoRetiro = tipoRetiro;
    }

    public Integer getTipoRetiroId() {
        return tipoRetiroId;
    }

    public void setTipoRetiroId(Integer tipoRetiroId) {
        this.tipoRetiroId = tipoRetiroId;
    }

    public BigDecimal getAhorroAcumulado() {
        return ahorroAcumulado;
    }

    public void setAhorroAcumulado(BigDecimal ahorroAcumulado) {
        this.ahorroAcumulado = ahorroAcumulado;
    }

    public BigDecimal getCantidadRetirar() {
        return cantidadRetirar;
    }

    public void setCantidadRetirar(BigDecimal cantidadRetirar) {
        this.cantidadRetirar = cantidadRetirar;
    }

    public String getMotivoRetiro() {
        return motivoRetiro;
    }

    public void setMotivoRetiro(String motivoRetiro) {
        this.motivoRetiro = motivoRetiro;
    }

    public CXPSolicitudPagoRH getCxpSolicitudPagoRh() {
        return cxpSolicitudPagoRH;
    }

    public void setCxpSolicitudPagoRh(CXPSolicitudPagoRH cxpSolicitudPagoRh) {
        this.cxpSolicitudPagoRH = cxpSolicitudPagoRh;
    }
}
