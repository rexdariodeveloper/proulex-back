package com.pixvs.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 15/04/2020.
 */
@Entity
@Table(name = "Logs")
public class Log {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_LogId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LOG_Texto", length = 150, nullable = true, insertable = true, updatable = true)
    private String texto;

    @Column(name = "LOG_CreadorPor", length = 150, nullable = true, insertable = true, updatable = true)
    private String creadoPor;

    @OneToOne
    @JoinColumn(name = "LOG_LOGT_LogTipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "LOGT_LogTipoId")
    private LogTipo tipo;

    @Column(name = "LOG_LOGT_LogTipoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoId;

    @OneToOne
    @JoinColumn(name = "LOG_LOGP_LogProcesoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "LOGP_LogProcesoId")
    private LogProceso proceso;

    @Column(name = "LOG_LOGP_LogProcesoId", nullable = false, insertable = true, updatable = false)
    private Integer procesoId;

    @Column(name = "LOG_ReferenciaId", nullable = true, insertable = true, updatable = false)
    private Integer referenciaId;

    @Column(name = "LOG_USU_CreadorPorId", nullable = true, insertable = true, updatable = false)
    private Integer usuarioId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "LOG_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    public Log() {
    }

    public Log(String texto, Integer tipoId, Integer procesoId, Integer referenciaId) {
        this.texto = texto;
        this.tipoId = tipoId;
        this.procesoId = procesoId;
        this.referenciaId = referenciaId;
    }

    public Log(String texto, Integer tipoId, Integer procesoId, Integer referenciaId, String creadoPor) {
        this.texto = texto;
        this.tipoId = tipoId;
        this.procesoId = procesoId;
        this.referenciaId = referenciaId;
        this.creadoPor = creadoPor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public LogTipo getTipo() {
        return tipo;
    }

    public void setTipo(LogTipo tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public LogProceso getProceso() {
        return proceso;
    }

    public void setProceso(LogProceso proceso) {
        this.proceso = proceso;
    }

    public Integer getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(Integer procesoId) {
        this.procesoId = procesoId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
