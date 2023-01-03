package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rene Carrillo on 24/03/2022.
 */
@Entity
@Table(name = "EmpleadosDocumentos")
public class EmpleadoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPD_EmpleadoDocumentoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPD_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false)
    private Integer empleadoId;

    // Tipo de Documento
    @Column(name = "EMPD_CMM_TipoDocumentoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoDocumentoId;

    @OneToOne
    @JoinColumn(name = "EMPD_CMM_TipoDocumentoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoDocumento;

    // Archivo
    @Column(name = "EMPD_ARC_ArchivoId", nullable = false, insertable = true, updatable = true)
    private Integer archivoId;

    @OneToOne
    @JoinColumn(name = "EMPD_ARC_ArchivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo archivo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMPD_FechaVencimiento", nullable = true, insertable = true, updatable = true)
    private Date fechaVencimiento;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EMPD_FechaVigencia", nullable = true, insertable = true, updatable = true)
    private Date fechaVigencia;

    @Column(name = "EMPD_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "EMPD_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "EMPD_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "EMPD_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "EMPD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "EMPD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EMPD_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    // Tipo de Proceso RH
    @Column(name = "EMPD_CMM_TipoProcesoRHId", nullable = false, insertable = true, updatable = true)
    private Integer tipoProcesoRHId;

    @OneToOne
    @JoinColumn(name = "EMPD_CMM_TipoProcesoRHId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoProcesoRH;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public ControlMaestroMultiple getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(ControlMaestroMultiple tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Integer archivoId) {
        this.archivoId = archivoId;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
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

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getTipoProcesoRHId() {
        return tipoProcesoRHId;
    }

    public void setTipoProcesoRHId(Integer tipoProcesoRHId) {
        this.tipoProcesoRHId = tipoProcesoRHId;
    }

    public ControlMaestroMultiple getTipoProcesoRH() {
        return tipoProcesoRH;
    }

    public void setTipoProcesoRH(ControlMaestroMultiple tipoProcesoRH) {
        this.tipoProcesoRH = tipoProcesoRH;
    }
}
