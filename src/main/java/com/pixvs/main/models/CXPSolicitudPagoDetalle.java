package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosDetalles")
public class CXPSolicitudPagoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPSD_CXPSSolicitudPagoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CXPSD_CXPS_CXPSolicitudPagoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPS_CXPSolicitudPagoId")
    private CXPSolicitudPago cxpSolicitudPago;

    @Column(name = "CXPSD_CXPS_CXPSolicitudPagoId", nullable = false, insertable = false, updatable = false)
    private Integer cxpSolicitudPagoId;

    @OneToOne
    @JoinColumn(name = "CXPSD_CXPF_CXPFacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPF_CXPFacturaId")
    private CXPFactura cxpFactura;

    @Column(name = "CXPSD_CXPF_CXPFacturaId", nullable = false, insertable = true, updatable = false)
    private Integer cxpFacturaId;

    @OneToOne
    @JoinColumn(name = "CXPSD_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPSD_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @Column(name = "CXPSD_MontoProgramado", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoProgramado;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPSD_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "CXPSD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPSD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CXPSolicitudPago getCxpSolicitudPago() {
        return cxpSolicitudPago;
    }

    public void setCxpSolicitudPago(CXPSolicitudPago cxpSolicitudPago) {
        this.cxpSolicitudPago = cxpSolicitudPago;
    }

    public Integer getCxpSolicitudPagoId() {
        return cxpSolicitudPagoId;
    }

    public void setCxpSolicitudPagoId(Integer cxpSolicitudPagoId) {
        this.cxpSolicitudPagoId = cxpSolicitudPagoId;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public BigDecimal getMontoProgramado() {
        return montoProgramado;
    }

    public void setMontoProgramado(BigDecimal montoProgramado) {
        this.montoProgramado = montoProgramado;
    }
}
