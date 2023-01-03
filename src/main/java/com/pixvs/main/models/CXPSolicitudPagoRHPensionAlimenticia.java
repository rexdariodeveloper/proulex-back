package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosRHPensionesAlimenticias")
public class CXPSolicitudPagoRHPensionAlimenticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRHPA_CXPSolicitudPagoRhPensionAlimenticiaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId", insertable = false, updatable = false, referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId")
    private CXPSolicitudPagoRH cxpSolicitudPagoRH;

    @Column(name = "CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId", insertable = false, updatable = false)
    private Integer cpxSolicitudPagoRHId;

    @Column(name = "CPXSPRHPA_NombreBeneficiario", nullable = false)
    private String nombreBeneficiario;

    @Column(name = "CPXSPRHPA_NumeroResolucion", nullable = false)
    private Integer numeroResolucion;


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

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public Integer getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(Integer numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public CXPSolicitudPagoRH getCxpSolicitudPagoRH() {
        return cxpSolicitudPagoRH;
    }

    public void setCxpSolicitudPagoRH(CXPSolicitudPagoRH cxpSolicitudPagoRH) {
        this.cxpSolicitudPagoRH = cxpSolicitudPagoRH;
    }
}
