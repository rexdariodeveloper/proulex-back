package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 03/12/2020.
 */
@Entity
@Table(name = "CXPSolicitudesPagosServicios")
public class CXPSolicitudPagoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPSPS_CXPSolicitudPagoServicioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXPSPS_CodigoSolicitudPagoServicio", length = 255, nullable = false, insertable = true, updatable = false)
    private String codigoSolicitudPagoServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPS_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPSPS_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "CXPSPS_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPS_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CXPSPS_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPSPS_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPS_SUC_SucursalId", nullable = true, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "CXPSPS_SUC_SucursalId", nullable = true, insertable = true, updatable = true)
    private Integer sucursalId;

    @Column(name = "CXPSPS_Comentarios", nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPSPS_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name="CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId", referencedColumnName = "CXPSPS_CXPSolicitudPagoServicioId", nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoServicioDetalle> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoSolicitudPagoServicio() {
        return codigoSolicitudPagoServicio;
    }

    public void setCodigoSolicitudPagoServicio(String codigoSolicitudPagoServicio) {
        this.codigoSolicitudPagoServicio = codigoSolicitudPagoServicio;
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

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<CXPSolicitudPagoServicioDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXPSolicitudPagoServicioDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
