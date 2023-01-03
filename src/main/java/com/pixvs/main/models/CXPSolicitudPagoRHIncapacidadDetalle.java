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
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosRHIncapacidadesDetalles")
public class CXPSolicitudPagoRHIncapacidadDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRHID_CXPSolicitudPagoRhIncapacidadDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;


    @Column(name = "CPXSPRHID_CPXSPRHI_IncapacidadId", nullable = false, insertable = false, updatable = false)
    private Integer incapacidadId;

    @ManyToOne
    @JoinColumn(name = "CPXSPRHID_CPXSPRHI_IncapacidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CPXSPRHI_CXPSolicitudPagoRhIncapacidadId")
    private CXPSolicitudPagoRHIncapacidad incapacidad;

    @OneToOne
    @JoinColumn(name = "CPXSPRHID_CMM_TipoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipo;

    @Column(name = "CPXSPRHID_CMM_TipoId", nullable = true, insertable = true)
    private Integer tipoId;

    @OneToOne
    @JoinColumn(name = "CPXSPRHID_CMM_TipoMovimientoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMovimiento;

    @Column(name = "CPXSPRHID_CMM_TipoMovimientoId", nullable = true, insertable = true)
    private Integer tipoMovimientoId;

    @Column(name = "CPXSPRHID_SalarioDiario", nullable = true, insertable = true, updatable = true)
    private BigDecimal salarioDiario;

    @Column(name = "CPXSPRHID_Porcentaje", nullable = true, insertable = true)
    private Integer porcentaje;

    @Column(name = "CPXSPRHID_Dias", nullable = true, insertable = true)
    private Integer dias;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIncapacidadId() {
        return incapacidadId;
    }

    public void setIncapacidadId(Integer incapacidadId) {
        this.incapacidadId = incapacidadId;
    }

    public ControlMaestroMultiple getTipo() {
        return tipo;
    }

    public void setTipo(ControlMaestroMultiple tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public ControlMaestroMultiple getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(ControlMaestroMultiple tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public CXPSolicitudPagoRHIncapacidad getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(CXPSolicitudPagoRHIncapacidad incapacidad) {
        this.incapacidad = incapacidad;
    }
}
