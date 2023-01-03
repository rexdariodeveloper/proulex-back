package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 11/06/2021.
 */
@Entity
@Table(name = "ClientesRemisiones")
public class ClienteRemision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIR_ClienteRemisionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CLIR_Codigo", length = 150, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(name = "CLIR_CLI_ClienteId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CLI_ClienteId")
    private Cliente cliente;

    @Column(name = "CLIR_CLI_ClienteId", nullable = false, insertable = true, updatable = true)
    private Integer clienteId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CLIR_Fecha", nullable = false, insertable = true, updatable = true)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "CLIR_MON_MonedaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CLIR_MON_MonedaId", nullable = false, insertable = true, updatable = true)
    private Integer monedaId;

    @OneToOne
    @JoinColumn(name = "CLIR_ALM_AlmacenOrigenId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
    private Almacen almacenOrigen;

    @Column(name = "CLIR_ALM_AlmacenOrigenId", nullable = false, insertable = true, updatable = true)
    private Integer almacenOrigenId;

    @OneToOne
    @JoinColumn(name = "CLIR_ALM_AlmacenDestinoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
    private Almacen almacenDestino;

    @Column(name = "CLIR_ALM_AlmacenDestinoId", nullable = false, insertable = true, updatable = true)
    private Integer almacenDestinoId;

    @Column(name = "CLIR_Comentario", length = 255, nullable = false, insertable = true, updatable = false)
    private String comentario;

    @OneToOne
    @JoinColumn(name = "CLIR_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CLIR_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "CLIR_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "CLIR_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CLIR_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CLIR_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "CLIR_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CLIR_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="CLIRD_CLIR_ClienteRemisionId", referencedColumnName = "CLIR_ClienteRemisionId", nullable = false, insertable = true, updatable = true)
    private List<ClienteRemisionDetalle> detalles;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public Almacen getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(Almacen almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public Integer getAlmacenOrigenId() {
        return almacenOrigenId;
    }

    public void setAlmacenOrigenId(Integer almacenOrigenId) {
        this.almacenOrigenId = almacenOrigenId;
    }

    public Almacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public Integer getAlmacenDestinoId() {
        return almacenDestinoId;
    }

    public void setAlmacenDestinoId(Integer almacenDestinoId) {
        this.almacenDestinoId = almacenDestinoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public List<ClienteRemisionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ClienteRemisionDetalle> detalles) {
        this.detalles = detalles;
    }
}
