package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "InventariosFisicos")
public class InventarioFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IF_InventarioFisicoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "IF_Codigo", nullable = false, updatable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "IF_Fecha", nullable = false, updatable = false)
    private Date fecha;

    @Column(name = "IF_LOC_LocalidadId", nullable = false, updatable = false)
    private int localidadId;

    @OneToOne
    @JoinColumn(name = "IF_LOC_LocalidadId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidad;

    @Column(name = "IF_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToOne
    @JoinColumn(name = "IF_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatus;

    @Column(name = "IF_USU_AfectadoPorId")
    private Integer afectadoPorId;

    @OneToOne
    @JoinColumn(name = "IF_USU_AfectadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario afectadoPor;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "IF_FechaAfectacion")
    private Date fechaAfectacion;

    @Column(name = "IF_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @OneToOne
    @JoinColumn(name = "IF_USU_CreadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario creadoPor;

    @Column(name = "IF_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "IF_USU_ModificadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "IF_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "IF_FechaModificacion")
    private Date fechaModificacion;

    @Transient
    List<InventarioFisicoDetalle> inventarioFisicoDetalles;

    @Transient
    boolean afectar;

    @Transient
    long fechaCreacionTemp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(int localidadId) {
        this.localidadId = localidadId;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public int getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(int estatusId) {
        this.estatusId = estatusId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getAfectadoPorId() {
        return afectadoPorId;
    }

    public void setAfectadoPorId(Integer afectadoPorId) {
        this.afectadoPorId = afectadoPorId;
    }

    public Usuario getAfectadoPor() {
        return afectadoPor;
    }

    public void setAfectadoPor(Usuario afectadoPor) {
        this.afectadoPor = afectadoPor;
    }

    public Date getFechaAfectacion() {
        return fechaAfectacion;
    }

    public void setFechaAfectacion(Date fechaAfectacion) {
        this.fechaAfectacion = fechaAfectacion;
    }

    public int getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(int creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<InventarioFisicoDetalle> getInventarioFisicoDetalles() {
        return inventarioFisicoDetalles;
    }

    public void setInventarioFisicoDetalles(List<InventarioFisicoDetalle> inventarioFisicoDetalles) {
        this.inventarioFisicoDetalles = inventarioFisicoDetalles;
    }

    public boolean isAfectar() {
        return afectar;
    }

    public void setAfectar(boolean afectar) {
        this.afectar = afectar;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public long getFechaCreacionTemp() {
        return fechaCreacionTemp;
    }

    public void setFechaCreacionTemp(long fechaCreacionTemp) {
        this.fechaCreacionTemp = fechaCreacionTemp;
    }
}
