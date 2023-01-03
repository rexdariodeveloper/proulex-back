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
@Table(name = "Pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PED_PedidoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PED_Codigo", nullable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PED_Fecha", nullable = false)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "PED_LOC_LocalidadOrigenId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidadOrigen;

    @Column(name = "PED_LOC_LocalidadOrigenId", nullable = false)
    private int localidadOrigenId;

    @OneToOne
    @JoinColumn(name = "PED_LOC_LocalidadCEDISId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidadCEDIS;

    @Column(name = "PED_LOC_LocalidadCEDISId", nullable = false)
    private int localidadCEDISId;

    @Column(name = "PED_Comentario")
    private String comentario;

    @OneToOne
    @JoinColumn(name = "PED_CMM_EstatusId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple estatus;

    @Column(name = "PED_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToOne
    @JoinColumn(name = "PED_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PED_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @OneToOne
    @JoinColumn(name = "PED_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PED_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PED_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PED_FechaModificacion")
    private Date fechaModificacion;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PEDD_PED_PedidoId", referencedColumnName = "PED_PedidoId", nullable = false)
    private List<PedidoDetalle> detalles = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Localidad getLocalidadOrigen() { return localidadOrigen; }
    public void setLocalidadOrigen(Localidad localidadOrigen) { this.localidadOrigen = localidadOrigen; }

    public int getLocalidadOrigenId() { return localidadOrigenId; }
    public void setLocalidadOrigenId(int localidadOrigenId) { this.localidadOrigenId = localidadOrigenId; }

    public Localidad getLocalidadCEDIS() { return localidadCEDIS; }
    public void setLocalidadCEDIS(Localidad localidadCEDIS) { this.localidadCEDIS = localidadCEDIS; }

    public int getLocalidadCEDISId() { return localidadCEDISId; }
    public void setLocalidadCEDISId(int localidadCEDISId) { this.localidadCEDISId = localidadCEDISId; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public ControlMaestroMultiple getEstatus() { return estatus; }
    public void setEstatus(ControlMaestroMultiple estatus) { this.estatus = estatus; }

    public int getEstatusId() { return estatusId; }
    public void setEstatusId(int estatusId) { this.estatusId = estatusId; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public int getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(int creadoPorId) { this.creadoPorId = creadoPorId; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public List<PedidoDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<PedidoDetalle> detalles) { this.detalles = detalles; }
}
