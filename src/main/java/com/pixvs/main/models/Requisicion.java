package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Departamento;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 */
@Entity
@Table(name = "Requisiciones")
public class Requisicion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQ_RequisicionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "REQ_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @Column(name = "REQ_Fecha", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "REQ_CMM_EstadoRequisicionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estadoRequisicion;

    @Column(name = "REQ_CMM_EstadoRequisicionId", nullable = false, insertable = true, updatable = true)
    private Integer estadoRequisicionId;

    @Column(name = "REQ_Comentarios", length = 255, nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "REQ_DEP_DepartamentoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "DEP_DepartamentoId")
    private Departamento departamento;

    @Column(name = "REQ_DEP_DepartamentoId", nullable = false, insertable = true, updatable = true)
    private Integer departamentoId;

    @OneToOne
    @JoinColumn(name = "REQ_ALM_AlmacenId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
    private Almacen almacen;

    @Column(name = "REQ_ALM_AlmacenId", nullable = false, insertable = true, updatable = true)
    private Integer almacenId;

    @OneToOne
    @JoinColumn(name = "REQ_USU_EnviadaPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario enviadaPor;

    @Column(name = "REQ_USU_EnviadaPorId", nullable = true, insertable = true, updatable = true)
    private Integer enviadaPorId;


    @CreationTimestamp
    @Column(name = "REQ_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "REQ_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "REQ_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "REQ_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "REQ_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "REQ_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="REQP_REQ_RequisicionId", referencedColumnName = "REQ_RequisicionId", nullable = false, insertable = true, updatable = true)
    private List<RequisicionPartida> partidas;

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

    public ControlMaestroMultiple getEstadoRequisicion() {
        return estadoRequisicion;
    }

    public void setEstadoRequisicion(ControlMaestroMultiple estadoRequisicion) {
        this.estadoRequisicion = estadoRequisicion;
    }

    public Integer getEstadoRequisicionId() {
        return estadoRequisicionId;
    }

    public void setEstadoRequisicionId(Integer estadoRequisicionId) {
        this.estadoRequisicionId = estadoRequisicionId;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Integer getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Integer departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Integer getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Integer almacenId) {
        this.almacenId = almacenId;
    }

    public Usuario getEnviadaPor() {
        return enviadaPor;
    }

    public void setEnviadaPor(Usuario enviadaPor) {
        this.enviadaPor = enviadaPor;
    }

    public Integer getEnviadaPorId() {
        return enviadaPorId;
    }

    public void setEnviadaPorId(Integer enviadaPorId) {
        this.enviadaPorId = enviadaPorId;
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

    public List<RequisicionPartida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<RequisicionPartida> partidas) {
        this.partidas = partidas;
    }
}
