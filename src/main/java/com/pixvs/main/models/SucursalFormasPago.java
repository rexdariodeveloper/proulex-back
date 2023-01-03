package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Benjamin Osorio
 */

@Entity
@Table(name="SucursalFormasPago")
public class SucursalFormasPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SFP_SucursalFormaPagoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SFP_SucursalId", nullable = false)
    private Integer sucursalId;

    @Column(name = "SFP_FP_FormaPagoId", nullable = false)
    private Integer formaPagoId;

    @Column(name = "SFP_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @Column(name = "SFP_UsarEnPV", nullable = false, insertable = true, updatable = true)
    private Boolean usarEnPV;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="SFP_FechaCreacion", nullable=false, insertable=true, updatable=false)
    private Date fechaCreacion;

    @Column(name = "SFP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column( name="SFP_FechaModificacion", nullable=true, insertable=false, updatable=true)
    private Date fechaModificacion;

    @Column(name = "SFP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getSucursalId() { return sucursalId; }

    public void setSucursalId(Integer sucursalId) { this.sucursalId = sucursalId; }

    public Integer getFormaPagoId() { return formaPagoId; }

    public void setFormaPagoId(Integer formaPagoId) { this.formaPagoId = formaPagoId; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public Date getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getCreadoPorId() { return creadoPorId; }

    public void setCreadoPorId(Integer creadoPorId) { this.creadoPorId = creadoPorId; }

    public Date getFechaModificacion() { return fechaModificacion; }

    public void setFechaModificacion(Date fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Integer getModificadoPorId() { return modificadoPorId; }

    public void setModificadoPorId(Integer modificadoPorId) { this.modificadoPorId = modificadoPorId; }

    public Boolean getUsarEnPV() { return usarEnPV; }

    public void setUsarEnPV(Boolean usarEnPV) { this.usarEnPV = usarEnPV; }
}
