package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 22/10/2020.
 */
@Entity
@Table(name = "RequisicionesPartidas")
public class RequisicionPartida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQP_RequisicionpartidaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "REQP_REQ_RequisicionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "REQ_RequisicionId")
    private Requisicion requisicion;

    @Column(name = "REQP_REQ_RequisicionId", nullable = false, insertable = false, updatable = false)
    private Integer requisicionId;

    @Column(name = "REQP_NumeroPartida", nullable = false, insertable = true, updatable = true)
    private Integer numeroPartida;

    @OneToOne
    @JoinColumn(name = "REQP_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "REQP_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "REQP_UM_UnidadMedidaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "UM_UnidadMedidaId")
    private UnidadMedida unidadMedida;

    @Column(name = "REQP_UM_UnidadMedidaId", nullable = false, insertable = true, updatable = true)
    private Integer unidadMedidaId;

    @Column(name = "REQP_Comentarios", length = 255, nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @Column(name = "REQP_FechaRequerida", nullable = false, insertable = true, updatable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fechaRequerida;

    @Column(name = "REQP_CantidadRequerida", nullable = false, insertable = true, updatable = true)
    private BigDecimal cantidadRequerida;

    @OneToOne
    @JoinColumn(name = "REQP_CMM_EstadoPartidaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estadoPartida;

    @Column(name = "REQP_CMM_EstadoPartidaId", nullable = false, insertable = true, updatable = true)
    private Integer estadoPartidaId;

    @OneToOne
    @JoinColumn(name = "REQP_ARC_ImagenArticuloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo imagenArticulo;

    @Column(name = "REQP_ARC_ImagenArticuloId", nullable = true, insertable = true, updatable = true)
    private Integer imagenArticuloId;

    @OneToOne
    @JoinColumn(name = "REQP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "REQP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "REQP_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;


    @Transient
    private String img64;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Requisicion getRequisicion() {
        return requisicion;
    }

    public void setRequisicion(Requisicion requisicion) {
        this.requisicion = requisicion;
    }

    public Integer getRequisicionId() {
        return requisicionId;
    }

    public void setRequisicionId(Integer requisicionId) {
        this.requisicionId = requisicionId;
    }

    public Integer getNumeroPartida() {
        return numeroPartida;
    }

    public void setNumeroPartida(Integer numeroPartida) {
        this.numeroPartida = numeroPartida;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(Integer unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public Date getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(Date fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public BigDecimal getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(BigDecimal cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    public ControlMaestroMultiple getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(ControlMaestroMultiple estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public Integer getEstadoPartidaId() {
        return estadoPartidaId;
    }

    public void setEstadoPartidaId(Integer estadoPartidaId) {
        this.estadoPartidaId = estadoPartidaId;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Archivo getImagenArticulo() {
        return imagenArticulo;
    }

    public void setImagenArticulo(Archivo imagenArticulo) {
        this.imagenArticulo = imagenArticulo;
    }

    public Integer getImagenArticuloId() {
        return imagenArticuloId;
    }

    public void setImagenArticuloId(Integer imagenArticuloId) {
        this.imagenArticuloId = imagenArticuloId;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }
}
