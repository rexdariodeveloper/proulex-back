package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PedidosRecibos")
public class PedidoRecibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PR_PedidoReciboId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PR_Codigo", nullable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PR_Fecha", nullable = false)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "PR_PED_PedidoId", referencedColumnName = "PED_PedidoId", insertable = false, updatable = false)
    private Pedido pedido;

    @Column(name = "PR_PED_PedidoId", nullable = false)
    private int pedidoId;

    @Column(name = "PR_Comentario")
    private String comentario;

    @Column(name = "PR_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToOne
    @JoinColumn(name = "PR_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatus;

    @Column(name = "PR_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @Column(name = "PR_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PR_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PR_FechaModificacion")
    private Date fechaModificacion;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PRD_PR_PedidoReciboId", referencedColumnName = "PR_PedidoReciboId", nullable = false)
    private List<PedidoReciboDetalle> detalles = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getEstatusId() { return estatusId; }
    public void setEstatusId(int estatusId) { this.estatusId = estatusId; }

    public ControlMaestroMultiple getEstatus() { return estatus; }
    public void setEstatus(ControlMaestroMultiple estatus) { this.estatus = estatus; }

    public int getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(int creadoPorId) { this.creadoPorId = creadoPorId; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public List<PedidoReciboDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<PedidoReciboDetalle> detalles) { this.detalles = detalles; }
}
