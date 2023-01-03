package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Temporadas")
public class Temporada {

    /** Properties **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEM_TemporadaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TEM_Nombre", nullable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "TEM_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "TEM_FechaFin", nullable = false)
    private Date fechaFin;

    @Column(name = "TEM_Activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "TEM_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "TEM_USU_CreadoPorId", nullable = false, updatable = false)
    private int creadoPorId;

    @OneToOne
    @JoinColumn(name = "TEM_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "TEM_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TEM_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "TEM_FechaModificacion")
    private Date fechaModificacion;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "TEMD_TEM_TemporadaId", referencedColumnName = "TEM_TemporadaId", nullable = false)
    private List<TemporadaDetalle> detalles = new ArrayList<>();

    /** Methods **/

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

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

    public List<TemporadaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<TemporadaDetalle> detalles) { this.detalles = detalles; }
}
