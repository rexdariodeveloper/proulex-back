package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SRV_ServicioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SRV_Concepto", length = 50, nullable = false, insertable = true, updatable = true)
    private String concepto;

    @Column(name = "SRV_Descripcion", length = 250, nullable = true, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "SRV_CMM_TipoServicioId", nullable = false, insertable = true, updatable = true)
    private Integer tipoServicioId;

    @OneToOne
    @JoinColumn(name = "SRV_CMM_TipoServicioId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoServicio;

    @Column(name = "SRV_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "SRV_ART_ArticuloId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "SRV_RequiereXML", nullable = false, insertable = true, updatable = true)
    private Boolean requiereXML;

    @Column(name = "SRV_RequierePDF", nullable = false, insertable = true, updatable = true)
    private Boolean requierePDF;

    @Column(name = "SRV_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "SRV_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "SRV_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "SRV_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "SRV_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "SRV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "SRV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getTipoServicioId() { return tipoServicioId; }
    public void setTipoServicioId(Integer tipoServicioId) { this.tipoServicioId = tipoServicioId; }

    public ControlMaestroMultiple getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(ControlMaestroMultiple tipoServicio) { this.tipoServicio = tipoServicio; }

    public Integer getArticuloId() { return articuloId; }
    public void setArticuloId(Integer articuloId) { this.articuloId = articuloId; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Boolean getRequiereXML() { return requiereXML; }
    public void setRequiereXML(Boolean requiereXML) { this.requiereXML = requiereXML; }

    public Boolean getRequierePDF() { return requierePDF; }
    public void setRequierePDF(Boolean requierePDF) { this.requierePDF = requierePDF; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getCreadoPorId() { return creadoPorId; }
    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Date getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Integer getModificadoPorId() { return modificadoPorId; }
    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Usuario getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(Usuario modificadoPor) { this.modificadoPor = modificadoPor; }
}
