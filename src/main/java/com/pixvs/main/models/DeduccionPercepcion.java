package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DeduccionesPercepciones")
public class DeduccionPercepcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEDPER_DeduccionPercepcionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "DEDPER_Codigo",length = 1, nullable = false)
    private String codigo;

    //Estado civil
    @OneToOne
    @JoinColumn(name = "DEDPER_CMM_TipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipo;

    @Column(name = "DEDPER_CMM_TipoId", nullable = false)
    private Integer tipoId;

    @Column(name = "DEDPER_Concepto",length = 50, nullable = false)
    private String concepto;

    //Estado civil
    @OneToOne
    @JoinColumn(name = "DEDPER_TAB_TabuladorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "TAB_TabuladorId")
    private Tabulador tabulador;

    @Column(name = "DEDPER_TAB_TabuladorId", nullable = false)
    private Integer tabuladorId;

    @Column(name = "DEDPER_Porcentaje", nullable = false)
    private BigDecimal porcentaje;

    @Column(name = "DEDPER_Activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "DEDPER_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "DEDPER_USU_CreadoPorId")
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "DEDPER_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "DEDPER_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "DEDPER_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "DEDPER_FechaUltimaModificacion")
    private Date fechaModificacion;


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

    public ControlMaestroMultiple getTipo() {
        return tipo;
    }

    public void setTipo(ControlMaestroMultiple tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Tabulador getTabulador() {
        return tabulador;
    }

    public void setTabulador(Tabulador tabulador) {
        this.tabulador = tabulador;
    }

    public Integer getTabuladorId() {
        return tabuladorId;
    }

    public void setTabuladorId(Integer tabuladorId) {
        this.tabuladorId = tabuladorId;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
