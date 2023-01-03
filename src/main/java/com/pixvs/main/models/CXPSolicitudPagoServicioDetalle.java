package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosServiciosDetalles")
public class CXPSolicitudPagoServicioDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPSPSD_CXPSolicitudPagoServicioDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPSPS_CXPSolicitudPagoServicioId")
    private CXPSolicitudPagoServicio cxpSolicitudPagoServicio;

    @Column(name = "CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId", nullable = false, insertable = false, updatable = false)
    private Integer cxpSolicitudPagoServicioId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPSD_CXPF_CXPFacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPF_CXPFacturaId")
    private CXPFactura cxpFactura;

    @Column(name = "CXPSPSD_CXPF_CXPFacturaId", nullable = false, insertable = true, updatable = false)
    private Integer cxpFacturaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPSD_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPSPSD_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPSPSD_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPSD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPSPSD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CXPSolicitudPagoServicio getCxpSolicitudPagoServicio() {
        return cxpSolicitudPagoServicio;
    }

    public void setCxpSolicitudPagoServicio(CXPSolicitudPagoServicio cxpSolicitudPagoServicio) {
        this.cxpSolicitudPagoServicio = cxpSolicitudPagoServicio;
    }

    public Integer getCxpSolicitudPagoServicioId() {
        return cxpSolicitudPagoServicioId;
    }

    public void setCxpSolicitudPagoServicioId(Integer cxpSolicitudPagoServicioId) {
        this.cxpSolicitudPagoServicioId = cxpSolicitudPagoServicioId;
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

}
