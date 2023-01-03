package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagos")
public class CXPSolicitudPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPS_CXPSolicitudPagoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXPS_CodigoSolicitud", length = 255, nullable = false, insertable = true, updatable = false)
    private String codigoSolicitud;

    @OneToOne
    @JoinColumn(name = "CXPS_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPS_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "CXPS_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "CXPS_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @CreationTimestamp
    @Column(name = "CXPS_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "CXPS_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CXPS_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CXPS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPS_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name="CXPSD_CXPS_CXPSolicitudPagoId", referencedColumnName = "CXPS_CXPSolicitudPagoId", nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
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

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Integer getSucursalId() { return sucursalId; }
    public void setSucursalId(Integer sucursalId) { this.sucursalId = sucursalId; }

    public List<CXPSolicitudPagoDetalle> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<CXPSolicitudPagoDetalle> detalles) { this.detalles = detalles; }
}
