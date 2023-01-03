package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PreciosIncompanyDetalles")
public class PrecioIncompanyDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREINCD_PrecioIncompanyDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PREINCD_PREINC_PrecioIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer precioIncompanyId;

    //Zona
    @OneToOne
    @JoinColumn(name = "PREINCD_CMM_ZonaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple zona;

    @Column(name = "PREINCD_CMM_ZonaId", nullable = false, insertable = true, updatable = true)
    private Integer zonaId;

    @Column(name = "PREINCD_PrecioVenta")
    private BigDecimal precioVenta;

    @Column(name = "PREINCD_PorcentajeTransporte")
    private BigDecimal porcentajeTransporte;

    //Idioma
    @OneToOne
    @JoinColumn(name = "PREINCD_CMM_IdiomaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple idioma;

    @Column(name = "PREINCD_CMM_IdiomaId", nullable = false, insertable = true, updatable = true)
    private Integer idiomaId;

    @OneToOne
    @JoinColumn(name = "PREINCD_PROG_ProgramaId",  insertable = false, updatable = false, referencedColumnName = "PROG_ProgramaId")
    private Programa programa;

    @Column(name = "PREINCD_PROG_ProgramaId")
    private Integer programaId;

    //Modalidad
    @OneToOne
    @JoinColumn(name = "PREINCD_PAMOD_ModalidadId",  insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "PREINCD_PAMOD_ModalidadId")
    private Integer modalidadId;

    //Horario
    @OneToOne
    @JoinColumn(name = "PREINCD_PAMODH_PAModalidadHorarioId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMODH_PAModalidadHorarioId")
    private PAModalidadHorario horario;

    @Column(name = "PREINCD_PAMODH_PAModalidadHorarioId", nullable = false, insertable = true, updatable = true)
    private Integer horarioId;

    @Column(name = "PREINCD_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecioIncompanyId() {
        return precioIncompanyId;
    }

    public void setPrecioIncompanyId(Integer precioIncompanyId) {
        this.precioIncompanyId = precioIncompanyId;
    }

    public ControlMaestroMultiple getZona() {
        return zona;
    }

    public void setZona(ControlMaestroMultiple zona) {
        this.zona = zona;
    }

    public Integer getZonaId() {
        return zonaId;
    }

    public void setZonaId(Integer zonaId) {
        this.zonaId = zonaId;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPorcentajeTransporte() {
        return porcentajeTransporte;
    }

    public void setPorcentajeTransporte(BigDecimal porcentajeTransporte) {
        this.porcentajeTransporte = porcentajeTransporte;
    }

    public ControlMaestroMultiple getIdioma() {
        return idioma;
    }

    public void setIdioma(ControlMaestroMultiple idioma) {
        this.idioma = idioma;
    }

    public Integer getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Integer getProgramaId() {
        return programaId;
    }

    public void setProgramaId(Integer programaId) {
        this.programaId = programaId;
    }

    public PAModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(PAModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public PAModalidadHorario getHorario() {
        return horario;
    }

    public void setHorario(PAModalidadHorario horario) {
        this.horario = horario;
    }

    public Integer getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Integer horarioId) {
        this.horarioId = horarioId;
    }
}
