package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRA_TransferenciaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TRA_Codigo", nullable = false, updatable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "TRA_Fecha", nullable = false, updatable = false)
    private Date fecha;

    @Column(name = "TRA_LOC_LocalidadOrigenId", nullable = false, updatable = false)
    private int localidadOrigenId;

    @OneToOne
    @JoinColumn(name = "TRA_LOC_LocalidadOrigenId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidadOrigen;

    @Column(name = "TRA_LOC_LocalidadDestinoId", nullable = false, updatable = false)
    private int localidadDestinoId;

    @OneToOne
    @JoinColumn(name = "TRA_LOC_LocalidadDestinoId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidadDestino;

    @Column(name = "TRA_Comentario")
    private String comentario;

    @Column(name = "TRA_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToOne
    @JoinColumn(name = "TRA_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatus;

    @Column(name = "TRA_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @OneToOne
    @JoinColumn(name = "TRA_USU_CreadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario creadoPor;

    @Column(name = "TRA_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "TRA_USU_ModificadoPorId", referencedColumnName = "USU_UsuarioId", insertable = false, updatable = false)
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TRA_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TRA_FechaModificacion")
    private Date fechaModificacion;

    @Transient
    List<TransferenciaDetalle> transferenciaDetalles;

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

    public int getLocalidadOrigenId() {
        return localidadOrigenId;
    }

    public void setLocalidadOrigenId(int localidadOrigenId) {
        this.localidadOrigenId = localidadOrigenId;
    }

    public Localidad getLocalidadOrigen() {
        return localidadOrigen;
    }

    public void setLocalidadOrigen(Localidad localidadOrigen) {
        this.localidadOrigen = localidadOrigen;
    }

    public int getLocalidadDestinoId() {
        return localidadDestinoId;
    }

    public void setLocalidadDestinoId(int localidadDestinoId) {
        this.localidadDestinoId = localidadDestinoId;
    }

    public Localidad getLocalidadDestino() {
        return localidadDestino;
    }

    public void setLocalidadDestino(Localidad localidadDestino) {
        this.localidadDestino = localidadDestino;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public List<TransferenciaDetalle> getTransferenciaDetalles() {
        return transferenciaDetalles;
    }

    public void setTransferenciaDetalles(List<TransferenciaDetalle> transferenciaDetalles) {
        this.transferenciaDetalles = transferenciaDetalles;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
