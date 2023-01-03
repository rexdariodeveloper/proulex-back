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
@Table(name = "CXPSolicitudesPagosRHIncapacidades")
public class CXPSolicitudPagoRHIncapacidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRHI_CXPSolicitudPagoRhIncapacidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId", nullable = false, insertable = false, updatable = false)
    private Integer cpxSolicitudPagoRhId;

    @Column(name = "CPXSPRHI_FolioIncapacidad", nullable = false)
    private String folioIncapacidad;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "CPXSPRHI_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "CPXSPRHI_FechaFin", nullable = false)
    private Date fechaFin;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name="CPXSPRHID_CPXSPRHI_IncapacidadId", referencedColumnName = "CPXSPRHI_CXPSolicitudPagoRhIncapacidadId", nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoRHIncapacidadDetalle> detalles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCpxSolicitudPagoRhId() {
        return cpxSolicitudPagoRhId;
    }

    public void setCpxSolicitudPagoRhId(Integer cpxSolicitudPagoRhId) {
        this.cpxSolicitudPagoRhId = cpxSolicitudPagoRhId;
    }

    public String getFolioIncapacidad() {
        return folioIncapacidad;
    }

    public void setFolioIncapacidad(String folioIncapacidad) {
        this.folioIncapacidad = folioIncapacidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<CXPSolicitudPagoRHIncapacidadDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXPSolicitudPagoRHIncapacidadDetalle> detalles) {
        this.detalles = detalles;
    }
}
