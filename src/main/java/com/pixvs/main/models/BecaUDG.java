package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 14/08/2021.
 */
@Entity
@Table(name = "BecasUDG")
public class BecaUDG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BECU_BecaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "BECU_CodigoBeca", length = 20, nullable = false, insertable = true, updatable = false)
    private String codigoBeca;

    @Column(name = "BECU_CodigoEmpleado", length = 20, nullable = false, insertable = true, updatable = false)
    private String codigoEmpleado;

    @Column(name = "BECU_Nombre", length = 50, nullable = false, insertable = true, updatable = false)
    private String nombre;

    @Column(name = "BECU_PrimerApellido", length = 50, nullable = false, insertable = true, updatable = false)
    private String primerApellido;

    @Column(name = "BECU_SegundoApellido", length = 50, nullable = true, insertable = true, updatable = false)
    private String segundoApellido;

    @Column(name = "BECU_Parentesco", length = 50, nullable = true, insertable = true, updatable = false)
    private String parentesco;

    @Column(name = "BECU_Descuento", nullable = false, insertable = true, updatable = false)
    private BigDecimal descuento;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_PROGI_ProgramaIdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROGI_ProgramaIdiomaId")
    private ProgramaIdioma programaIdioma;

    @Column(name = "BECU_PROGI_ProgramaIdiomaId", nullable = false, insertable = true, updatable = false)
    private Integer programaIdiomaId;

    @Column(name = "BECU_Nivel", nullable = false, insertable = true, updatable = false)
    private Integer nivel;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad paModalidad;

    @Column(name = "BECU_PAMOD_ModalidadId", nullable = false, insertable = true, updatable = false)
    private Integer paModalidadId;

    @Column(name = "BECU_FirmaDigital", length = 255, nullable = true, insertable = true, updatable = false)
    private String firmaDigital;

    @Column(name = "BECU_FechaAlta", nullable = false, insertable = true, updatable = false)
    private Date fechaAlta;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "BECU_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_CMM_TipoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipo;

    @Column(name = "BECU_CMM_TipoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_CMM_EntidadId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple entidad;

    @Column(name = "BECU_CMM_EntidadId", insertable = true, updatable = false)
    private Integer entidadId;

    @Column(name = "BECU_CodigoAlumno", length = 20, nullable = true, insertable = true, updatable = false)
    private String codigoAlumno;

    @Column(name = "BECU_BECS_BecaSolicitudId", insertable = false, updatable = false, nullable = true)
    private Integer solicitudId;

    @Column(name = "BECU_Comentarios", length = 500, nullable = true, insertable = true, updatable = false)
    private String comentarios;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "BECU_FechaExpiracion", nullable = true, insertable = true, updatable = true)
    private Date fechaExpiracion;

    @Column(name = "BECU_SIAPId", nullable = true, insertable = true, updatable = true)
    private Integer siapId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "BECU_ENBE_EntidadBecaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ENBE_EntidadBecaId")
    private EntidadBeca entidadBeca;

    @Column(name = "BECU_ENBE_EntidadBecaId", nullable = true, insertable = true, updatable = true)
    private Integer entidadBecaId;

    @Transient
    private Alumno alumnoTemp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBeca() { return codigoBeca; }

    public void setCodigoBeca(String codigoBeca) {
        this.codigoBeca = codigoBeca;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public ProgramaIdioma getProgramaIdioma() {
        return programaIdioma;
    }

    public void setProgramaIdioma(ProgramaIdioma programaIdioma) {
        this.programaIdioma = programaIdioma;
    }

    public Integer getProgramaIdiomaId() {
        return programaIdiomaId;
    }

    public void setProgramaIdiomaId(Integer programaIdiomaId) {
        this.programaIdiomaId = programaIdiomaId;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public PAModalidad getPaModalidad() {
        return paModalidad;
    }

    public void setPaModalidad(PAModalidad paModalidad) {
        this.paModalidad = paModalidad;
    }

    public Integer getPaModalidadId() {
        return paModalidadId;
    }

    public void setPaModalidadId(Integer paModalidadId) {
        this.paModalidadId = paModalidadId;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
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

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public Integer getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Integer solicitudId) {
        this.solicitudId = solicitudId;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Alumno getAlumnoTemp() {
        return alumnoTemp;
    }

    public void setAlumnoTemp(Alumno alumnoTemp) {
        this.alumnoTemp = alumnoTemp;
    }

    public ControlMaestroMultiple getEntidad() {
        return entidad;
    }

    public void setEntidad(ControlMaestroMultiple entidad) {
        this.entidad = entidad;
    }

    public Integer getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Integer entidadId) {
        this.entidadId = entidadId;
    }

    public Date getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(Date fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }

    public Integer getSiapId() { return siapId; }
    public void setSiapId(Integer siapId) { this.siapId = siapId; }

    public EntidadBeca getEntidadBeca() { return entidadBeca; }
    public void setEntidadBeca(EntidadBeca entidadBeca) { this.entidadBeca = entidadBeca; }

    public Integer getEntidadBecaId() { return entidadBecaId; }
    public void setEntidadBecaId(Integer entidadBecaId) { this.entidadBecaId = entidadBecaId; }
}
